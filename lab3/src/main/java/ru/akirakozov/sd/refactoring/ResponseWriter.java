package ru.akirakozov.sd.refactoring;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class ResponseWriter {
    private final HttpServletResponse response;

    public ResponseWriter(HttpServletResponse response) {
        this.response = response;
    }

    public void commit() {
        response.setContentType("text/html");
        response.setStatus(HttpServletResponse.SC_OK);
    }

    public void printHtmlBody(List<String> strings) throws IOException {
        response.getWriter().println("<html><body>");
        for (String s : strings) {
            response.getWriter().println(s);
        }
        response.getWriter().println("</body></html>");
    }
}
