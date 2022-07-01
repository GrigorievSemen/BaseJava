package ru.mystudies.basejava.storage;

import ru.mystudies.basejava.exception.ExistStorageException;
import ru.mystudies.basejava.exception.NotExistStorageException;
import ru.mystudies.basejava.exception.StorageException;
import ru.mystudies.basejava.model.Resume;
import ru.mystudies.basejava.storage.sql.ConnectionFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class SqlStorage implements Storage {
    protected static final Comparator<Resume> RESUME_COMPARATOR =
            Comparator.comparing(Resume::getFullName).thenComparing(Resume::getUuid);
    private final SqlExecutor sqlExecutor;

    public SqlStorage(String dbUrl, String dbUser, String dbPassword) {
        sqlExecutor = new SqlExecutor(dbUrl, dbUser, dbPassword);
    }

    @Override
    public void clear() {
        sqlExecutor.perform("DELETE FROM resume", PreparedStatement::execute);
    }

    @Override
    public Resume get(String uuid) {
        return sqlExecutor.perform("SELECT * FROM resume r WHERE r.uuid =?", ps -> {
            ps.setString(1, uuid);
            ResultSet rs = ps.executeQuery();

            if (!rs.next()) {
                throw new NotExistStorageException(uuid);
            }
            return new Resume(uuid, rs.getString("full_name"));
        });
    }

    @Override
    public void update(Resume r) {
        sqlExecutor.perform("UPDATE resume SET full_name =? WHERE uuid =?", ps -> {
            ps.setString(1, r.getFullName());
            ps.setString(2, r.getUuid());

            if (ps.executeUpdate() == 0) {
                throw new NotExistStorageException(r.getUuid());
            }
            return null;
        });
    }

    @Override
    public void save(Resume r) {
        sqlExecutor.perform("SELECT * FROM resume WHERE uuid =?", ps -> {
            ps.setString(1, r.getUuid());
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                throw new ExistStorageException(r.getUuid());
            } else {
                sqlExecutor.perform("INSERT INTO resume (uuid, full_name) VALUES (?,?)", p_s -> {
                    p_s.setString(1, r.getUuid());
                    p_s.setString(2, r.getFullName());

                    if (p_s.executeUpdate() == 0) {
                        throw new ExistStorageException(r.getUuid());
                    }
                    return null;
                });
            }
            return null;
        });
    }

    @Override
    public void delete(String uuid) {
        sqlExecutor.perform("DELETE FROM resume WHERE uuid =?", ps -> {
            ps.setString(1, uuid);

            if (ps.executeUpdate() == 0) {
                throw new NotExistStorageException(uuid);
            }
            return null;
        });
    }

    @Override
    public List<Resume> getAllSorted() {

        return sqlExecutor.perform("SELECT * FROM resume", ps -> {
            ResultSet rs = ps.executeQuery();
            List<Resume> resumes = new ArrayList<>();
            while (rs.next()) {
                resumes.add(new Resume(rs.getString("uuid"), rs.getString("full_name")));
            }
            resumes.sort(RESUME_COMPARATOR);
            return resumes;
        });
    }

    @Override
    public int size() {
        return sqlExecutor.perform("SELECT COUNT(*) FROM resume", ps -> {
            ResultSet rs = ps.executeQuery();
            if (!rs.next()) {
                return 0;
            }
            return rs.getInt(1);
        });
    }

    interface SqlWorkWithRequests<T> {
        T perform(PreparedStatement ps) throws SQLException;
    }

    private class SqlExecutor {
        public final ConnectionFactory connectionFactory;

        public SqlExecutor(String dbUrl, String dbUser, String dbPassword) {
            connectionFactory = () -> DriverManager.getConnection(dbUrl, dbUser, dbPassword);
        }

        public <T> T perform(String sql, SqlWorkWithRequests<T> sqlWorkWithRequests) {
            try (Connection conn = connectionFactory.getConnection();
                 PreparedStatement ps = conn.prepareStatement(sql)) {
                return sqlWorkWithRequests.perform(ps);
            } catch (SQLException e) {
                throw new StorageException(e);
            }
        }
    }
}



