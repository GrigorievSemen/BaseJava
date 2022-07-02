package ru.mystudies.basejava.storage;

import ru.mystudies.basejava.exception.ExistStorageException;
import ru.mystudies.basejava.exception.NotExistStorageException;
import ru.mystudies.basejava.model.Resume;
import ru.mystudies.basejava.storage.sql.SqlExecutor;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class SqlStorage implements Storage {
    private final SqlExecutor sqlExecutor;

    public SqlStorage(String dbUrl, String dbUser, String dbPassword) throws ClassNotFoundException {
        Class.forName("org.postgresql.Driver");
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
        sqlExecutor.perform("INSERT INTO resume (uuid, full_name) VALUES (?,?)", ps -> {
            ps.setString(1, r.getUuid());
            ps.setString(2, r.getFullName());
            ps.execute();
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

        return sqlExecutor.perform("SELECT * FROM resume ORDER BY full_name, uuid", ps -> {
            ResultSet rs = ps.executeQuery();
            List<Resume> resumes = new ArrayList<>();
            while (rs.next()) {
                resumes.add(new Resume(rs.getString("uuid"), rs.getString("full_name")));
            }
            return resumes;
        });
    }

    @Override
    public int size() {
        return sqlExecutor.perform("SELECT COUNT(*) FROM resume", ps -> {
            ResultSet rs = ps.executeQuery();
            return !rs.next() ? 0 : rs.getInt(1);
        });
    }
}



