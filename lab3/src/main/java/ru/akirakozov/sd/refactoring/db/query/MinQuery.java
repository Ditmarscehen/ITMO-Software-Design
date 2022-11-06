package ru.akirakozov.sd.refactoring.db.query;

import ru.akirakozov.sd.refactoring.model.Product;

import java.sql.ResultSet;
import java.sql.SQLException;

public final class MinQuery extends AbstractQuery<Product> {
    Product product;

    @Override
    protected String query() {
        return "SELECT * FROM PRODUCT ORDER BY PRICE LIMIT 1";
    }

    @Override
    protected void applyResultSet(ResultSet resultSet) throws SQLException {
        String name = resultSet.getString("name");
        int price = resultSet.getInt("price");
        product = new Product(name, price);
    }

    @Override
    protected Product result() {
        return product;
    }

    public static Product createAndExecute() throws SQLException {
        MinQuery query = new MinQuery();
        return query.execute();
    }
}
