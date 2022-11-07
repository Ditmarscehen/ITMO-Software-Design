package ru.akirakozov.sd.refactoring.db.query;

import ru.akirakozov.sd.refactoring.model.Product;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public final class GetProductsQuery extends AbstractQuery<List<Product>> {
    List<Product> products;

    @Override
    protected String query() {
        return "SELECT * FROM PRODUCT";
    }

    @Override
    protected void applyResultSet(ResultSet resultSet) throws SQLException {
        products = new ArrayList<>();
        while (resultSet.next()) {
            String name = resultSet.getString("name");
            int price = resultSet.getInt("price");
            products.add(new Product(name, price));
        }
    }

    @Override
    protected List<Product> result() {
        return products;
    }

    public static List<Product> createAndExecute() throws SQLException {
        GetProductsQuery query = new GetProductsQuery();
        return query.execute();
    }
}
