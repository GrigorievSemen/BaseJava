package ru.mystudies.basejava.storage.sql;

import ru.mystudies.basejava.exception.ExistStorageException;
import ru.mystudies.basejava.exception.StorageException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SqlExecutor {
    public final ConnectionFactory connectionFactory;

    public SqlExecutor(String dbUrl, String dbUser, String dbPassword) {
        connectionFactory = () -> DriverManager.getConnection(dbUrl, dbUser, dbPassword);
    }

    public <T> T perform(String sql, SqlWorkWithRequests<T> sqlWorkWithRequests) {
        try (Connection conn = connectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            return sqlWorkWithRequests.perform(ps);
        } catch (SQLException e) {
            if (e.getSQLState().equals("23505")) {
                throw new ExistStorageException(null);
            }
            throw new StorageException(e);
        }
    }
}

