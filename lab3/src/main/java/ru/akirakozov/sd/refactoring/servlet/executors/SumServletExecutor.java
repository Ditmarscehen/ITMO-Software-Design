package ru.akirakozov.sd.refactoring.servlet.executors;

import ru.akirakozov.sd.refactoring.db.query.SumQuery;

import java.sql.SQLException;
import java.util.List;

public final class SumServletExecutor extends ServletExecutor {

    @Override
    List<String> getBody() {
        SumQuery query = new SumQuery();
        try {
            Integer sum = query.execute();
            return List.of("Summary price: ",
                    sum.toString());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
