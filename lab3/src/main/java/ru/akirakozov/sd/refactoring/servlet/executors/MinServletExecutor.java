package ru.akirakozov.sd.refactoring.servlet.executors;

import ru.akirakozov.sd.refactoring.db.query.MinQuery;
import ru.akirakozov.sd.refactoring.model.Product;

import java.sql.SQLException;
import java.util.List;

public final class MinServletExecutor extends ServletExecutor {

    @Override
    List<String> getBody() {
        MinQuery query = new MinQuery();
        try {
            Product min = query.execute();
            return List.of("<h1>Product with min price: </h1>",
                    productToString(min));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
