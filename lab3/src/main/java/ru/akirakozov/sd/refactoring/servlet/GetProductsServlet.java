package ru.akirakozov.sd.refactoring.servlet;

import ru.akirakozov.sd.refactoring.servlet.executors.GetProductServletExecutor;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author akirakozov
 */
public class GetProductsServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        GetProductServletExecutor servletExecutor = new GetProductServletExecutor();
        try {
            servletExecutor.execute(response);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
