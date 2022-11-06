package ru.akirakozov.sd.refactoring.db.query;

import ru.akirakozov.sd.refactoring.model.Product;

import java.sql.ResultSet;
import java.sql.SQLException;

public final class CountQuery extends AbstractQuery<Integer> {
    Integer count;

    @Override
    protected String query() {
        return "SELECT COUNT(*) FROM PRODUCT";
    }

    @Override
    protected void applyResultSet(ResultSet resultSet) throws SQLException {
        if (resultSet.next()) {
            count = resultSet.getInt(1);
        }
    }

    @Override
    protected Integer result() {
        return count;
    }

    public static Integer createAndExecute() throws SQLException {
        CountQuery query = new CountQuery();
        return query.execute();
    }
}
