package ru.akirakozov.sd.refactoring.db.query;

import java.sql.*;

public sealed abstract class AbstractQuery<T> permits CountQuery, GetProductsQuery, MaxQuery, MinQuery, SumQuery {
    protected abstract String query();

    protected abstract T result();

    protected abstract void applyResultSet(ResultSet resultSet) throws SQLException;

    public T execute() throws SQLException {
        try (Connection c = DriverManager.getConnection("jdbc:sqlite:test.db")) {
            Statement stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery(query());
            applyResultSet(rs);
            rs.close();
            stmt.close();
        }

        return result();
    }
}
