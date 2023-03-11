package ru.akirakozov.sd.refactoring.servlet.executors;

import ru.akirakozov.sd.refactoring.db.query.MaxQuery;
import ru.akirakozov.sd.refactoring.model.Product;

import java.sql.SQLException;
import java.util.List;

public final class MaxServletExecutor extends ServletExecutor {

    @Override
    List<String> getBody() {
        MaxQuery query = new MaxQuery();
        try {
            Product max = query.execute();
            return List.of("<h1>Product with max price: </h1>",
                    productToString(max));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
