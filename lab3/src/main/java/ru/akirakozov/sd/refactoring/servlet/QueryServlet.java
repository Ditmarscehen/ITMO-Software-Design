package ru.akirakozov.sd.refactoring.servlet;

import ru.akirakozov.sd.refactoring.servlet.executors.*;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author akirakozov
 */
public class QueryServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)  {
        String command = request.getParameter("command");
        ServletExecutor servletExecutor =
                switch (command) {
                    case "max" -> new MaxServletExecutor();
                    case "min" -> new MinServletExecutor();
                    case "sum" -> new SumServletExecutor();
                    case "count" -> new CountServletExecutor();
                    default -> new UnknownCommand(command);
                };
        try {
            servletExecutor.execute(response);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
