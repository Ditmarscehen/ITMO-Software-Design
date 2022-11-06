package ru.akirakozov.sd.refactoring.servlet;

import ru.akirakozov.sd.refactoring.db.query.GetProductsQuery;
import ru.akirakozov.sd.refactoring.model.Product;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author akirakozov
 */
public class GetProductsServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        try {
            GetProductsQuery query = new GetProductsQuery();
            List<Product> products = query.execute();

            response.getWriter().println("<html><body>");
            for (Product product : products) {
                response.getWriter().println(product.name() + "\t" + product.price() + "</br>");
            }
            response.getWriter().println("</body></html>");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        response.setContentType("text/html");
        response.setStatus(HttpServletResponse.SC_OK);
    }
}
