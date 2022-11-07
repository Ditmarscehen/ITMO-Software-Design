package ru.akirakozov.sd.refactoring.db.update;

import java.sql.*;

public sealed abstract class Update permits AddProductUpdate, CreateTableUpdate {
    protected abstract String query();

    public void executeUpdate() throws SQLException {
        try (Connection c = DriverManager.getConnection("jdbc:sqlite:test.db")) {
            String sql = query();
            Statement stmt = c.createStatement();
            stmt.executeUpdate(sql);
            stmt.close();
        }
    }
}
