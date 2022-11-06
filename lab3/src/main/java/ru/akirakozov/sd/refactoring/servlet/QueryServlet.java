package ru.akirakozov.sd.refactoring.servlet;

import ru.akirakozov.sd.refactoring.db.query.*;
import ru.akirakozov.sd.refactoring.model.Product;
import ru.akirakozov.sd.refactoring.util.ThrowableBiConsumer;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.*;

/**
 * @author akirakozov
 */
public class QueryServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String command = request.getParameter("command");
        if ("max".equals(command)) {
            try {
                MaxQuery query = new MaxQuery();
                execQueryAndPrint(response, query, (r, p) -> {
                    r.getWriter().println("<h1>Product with max price: </h1>");
                    printProduct(r, p);
                });
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        } else if ("min".equals(command)) {
            try {
                MinQuery query = new MinQuery();
                execQueryAndPrint(response, query, (r, p) -> {
                    r.getWriter().println("<h1>Product with min price: </h1>");
                    printProduct(r, p);
                });
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        } else if ("sum".equals(command)) {
            try {
                SumQuery query = new SumQuery();
                execQueryAndPrint(response, query, (r, c) -> {
                    r.getWriter().println("Summary price: ");
                    r.getWriter().println(c);
                });
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        } else if ("count".equals(command)) {
            try {
                CountQuery query = new CountQuery();
                execQueryAndPrint(response, query, (r, n) -> {
                    r.getWriter().println("Number of products: ");
                    r.getWriter().println(n);
                });
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        } else {
            response.getWriter().println("Unknown command: " + command);
        }

        response.setContentType("text/html");
        response.setStatus(HttpServletResponse.SC_OK);
    }

    private <T> void execQueryAndPrint(HttpServletResponse response, AbstractQuery<T> query, ThrowableBiConsumer<HttpServletResponse, T> printer) {
        try {
            T res = query.execute();
            response.getWriter().println("<html><body>");
            printer.accept(response, res);
            response.getWriter().println("</body></html>");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    private static void printProduct(HttpServletResponse response, Product product) throws IOException {
        response.getWriter().println(product.name() + "\t" + product.price() + "</br>");
    }
}
