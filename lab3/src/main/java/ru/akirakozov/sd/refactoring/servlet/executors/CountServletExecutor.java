package ru.akirakozov.sd.refactoring.servlet.executors;

import ru.akirakozov.sd.refactoring.db.query.CountQuery;

import java.sql.SQLException;
import java.util.List;

public final class CountServletExecutor extends ServletExecutor {

    @Override
    List<String> getBody() {
        CountQuery query = new CountQuery();
        try {
            Integer count = query.execute();
            return List.of("Number of products: ",
                    count.toString());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
