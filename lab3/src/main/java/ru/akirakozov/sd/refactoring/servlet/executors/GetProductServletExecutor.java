package ru.akirakozov.sd.refactoring.servlet.executors;

import ru.akirakozov.sd.refactoring.db.query.GetProductsQuery;
import ru.akirakozov.sd.refactoring.model.Product;

import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

public final class GetProductServletExecutor extends ServletExecutor {

    @Override
    List<String> getBody() {
        GetProductsQuery query = new GetProductsQuery();
        try {
            List<Product> products = query.execute();
            return products.stream()
                    .map(ServletExecutor::productToString)
                    .collect(Collectors.toList());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
