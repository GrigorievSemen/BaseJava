package ru.mystudies.basejava.storage;

import ru.mystudies.basejava.exception.NotExistStorageException;
import ru.mystudies.basejava.model.*;
import ru.mystudies.basejava.storage.sql.SqlHelper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class SqlStorage implements Storage {
    private final SqlHelper sqlHelper;

    public SqlStorage(String dbUrl, String dbUser, String dbPassword) throws ClassNotFoundException {
        Class.forName("org.postgresql.Driver");
        sqlHelper = new SqlHelper(dbUrl, dbUser, dbPassword);
    }

    @Override
    public void clear() {
        sqlHelper.execute("DELETE FROM resume", PreparedStatement::execute);
    }

    @Override
    public Resume get(String uuid) {
        return sqlHelper.transactionalExecute(conn -> {
            Resume resume;
            try (PreparedStatement ps = conn.prepareStatement("SELECT * FROM resume r WHERE r.uuid =?")) {
                ps.setString(1, uuid);
                ResultSet rs = ps.executeQuery();
                if (!rs.next()) {
                    throw new NotExistStorageException(uuid);
                }
                resume = new Resume(uuid, rs.getString("full_name"));
            }

            try (PreparedStatement ps = conn.prepareStatement("SELECT * FROM contact c WHERE c.resume_uuid =?")) {
                ps.setString(1, uuid);
                ResultSet rs = ps.executeQuery();

                while (rs.next()) {
                    String value = rs.getString("value");
                    if (value != null) {
                        ContactType type = ContactType.valueOf(rs.getString("type"));
                        resume.addContact(type, value);
                    }
                }
            }

            try (PreparedStatement ps = conn.prepareStatement("SELECT * FROM section s WHERE s.resume_uuid =?")) {
                ps.setString(1, uuid);
                ResultSet rs = ps.executeQuery();

                while (rs.next()) {
                    String value = rs.getString("description");
                    if (value != null) {
                        SectionType sectionType = SectionType.valueOf(rs.getString("type"));

                        if (sectionType.equals(SectionType.OBJECTIVE) || sectionType.equals(SectionType.PERSONAL)) {
                            resume.addSection(sectionType, new TextSection(value));
                        } else {
                            resume.addSection(sectionType, new ListSection(List.of(value.split("\n"))));
                        }
                    }
                }
            }
            return resume;
        });
    }

    @Override
    public void update(Resume resume) {
        sqlHelper.transactionalExecute(conn -> {
            try (PreparedStatement ps = conn.prepareStatement("UPDATE resume SET full_name =? WHERE uuid =?")) {
                ps.setString(1, resume.getFullName());
                ps.setString(2, resume.getUuid());

                if (ps.executeUpdate() == 0) {
                    throw new NotExistStorageException(resume.getUuid());
                }
            }

            deleteContacts(resume, conn);
            insertContacts(resume, conn);

            deleteSection(resume, conn);
            insertSection(resume, conn);
            return null;
        });
    }

    @Override
    public void save(Resume resume) {
        sqlHelper.transactionalExecute(conn -> {
                    try (PreparedStatement ps = conn.prepareStatement("INSERT INTO resume (uuid, full_name) VALUES (?,?)")) {
                        ps.setString(1, resume.getUuid());
                        ps.setString(2, resume.getFullName());
                        ps.execute();
                    }
                    insertContacts(resume, conn);
                    insertSection(resume, conn);
                    return null;
                }
        );
    }

    @Override
    public void delete(String uuid) {
        sqlHelper.execute("DELETE FROM resume WHERE uuid =?", ps -> {
            ps.setString(1, uuid);

            if (ps.executeUpdate() == 0) {
                throw new NotExistStorageException(uuid);
            }
            return null;
        });
    }

    @Override
    public List<Resume> getAllSorted() {
        Map<String, Resume> map = new LinkedHashMap<>();

        sqlHelper.execute("SELECT * FROM resume\n" +
                "LEFT JOIN contact ON resume.uuid = contact.resume_uuid\n" +
                "ORDER BY full_name, uuid", ps -> {
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                String uuid = rs.getString("uuid");
                String value = rs.getString("value");
                Resume resume = map.get(uuid);

                if (resume == null) {
                    String full_name = rs.getString("full_name");
                    resume = new Resume(uuid, full_name);
                    map.put(uuid, resume);
                }

                resume.addContact(ContactType.valueOf(rs.getString("type")), value);
            }
            return null;
        });

        sqlHelper.execute("SELECT * FROM section", ps1 -> {
            ResultSet rs1 = ps1.executeQuery();

            while (rs1.next()) {
                String value = rs1.getString("description");
                if (value != null) {
                    SectionType sectionType = SectionType.valueOf(rs1.getString("type"));

                    if (sectionType.equals(SectionType.OBJECTIVE) || sectionType.equals(SectionType.PERSONAL)) {
                        map.get(rs1.getString("resume_uuid")).addSection(sectionType, new TextSection(value));
                    } else {
                        map.get(rs1.getString("resume_uuid")).addSection(sectionType, new ListSection(List.of(value.split("\n"))));
                    }
                }
            }
            return null;
        });
        return new ArrayList<>(map.values());
    }

    @Override
    public int size() {
        return sqlHelper.execute("SELECT COUNT(*) FROM resume", ps -> {
            ResultSet rs = ps.executeQuery();
            return !rs.next() ? 0 : rs.getInt(1);
        });
    }

    private void insertContacts(Resume resume, Connection conn) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement("INSERT INTO contact (resume_uuid, type, value) VALUES (?,?,?)")) {
            for (Map.Entry<ContactType, String> entry : resume.getContacts().entrySet()) {
                ps.setString(1, resume.getUuid());
                ps.setString(2, entry.getKey().name());
                ps.setString(3, entry.getValue());
                ps.addBatch();
            }
            ps.executeBatch();
        }
    }

    private void deleteContacts(Resume resume, Connection conn) {
        sqlHelper.execute("DELETE  FROM contact WHERE resume_uuid=?", ps -> {
            ps.setString(1, resume.getUuid());
            ps.execute();
            return null;
        });
    }

    private void deleteSection(Resume resume, Connection conn) {
        sqlHelper.execute("DELETE  FROM section WHERE resume_uuid=?", ps -> {
            ps.setString(1, resume.getUuid());
            ps.execute();
            return null;
        });
    }

    private void insertSection(Resume resume, Connection conn) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement("INSERT INTO section (resume_uuid, type, description) VALUES (?,?,?)")) {
            for (Map.Entry<SectionType, AbstractSection> entry : resume.getSection().entrySet()) {
                ps.setString(1, resume.getUuid());
                ps.setString(2, entry.getKey().name());
                ps.setString(3, entry.getValue().getItemsString());
                ps.addBatch();
            }
            ps.executeBatch();
        }
    }
}



