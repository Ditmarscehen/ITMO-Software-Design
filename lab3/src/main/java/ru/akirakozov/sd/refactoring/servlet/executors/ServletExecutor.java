package ru.akirakozov.sd.refactoring.servlet.executors;

import ru.akirakozov.sd.refactoring.ResponseWriter;
import ru.akirakozov.sd.refactoring.model.Product;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public abstract sealed class ServletExecutor permits CountServletExecutor, GetProductServletExecutor, MaxServletExecutor, MinServletExecutor, SumServletExecutor, UnknownCommand {
    abstract List<String> getBody();

    public void execute(HttpServletResponse response) throws IOException {
        ResponseWriter responseWriter = new ResponseWriter(response);
        List<String> body = getBody();
        responseWriter.printHtmlBody(body);
        responseWriter.commit();
    }

    protected static String productToString(Product product){
        return product.name() + "\t" + product.price() + "</br>";
    }
}
