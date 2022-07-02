package ru.mystudies.basejava.storage.sql;

import java.sql.PreparedStatement;
import java.sql.SQLException;

 public interface SqlWorkWithRequests<T> {
    T perform(PreparedStatement ps) throws SQLException;
}

