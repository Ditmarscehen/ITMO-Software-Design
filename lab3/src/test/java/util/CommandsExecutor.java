package util;

import ru.akirakozov.sd.refactoring.model.Product;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class CommandsExecutor {
    private final String server;
    private final HttpClient httpClient;


    public CommandsExecutor(String host, int port) {
        this.server = String.format("http://%s:%s", host, port);
        this.httpClient = HttpClient.newHttpClient();
    }

    public void addProduct(Product product) {
        executeCommand(String.format("add-product?name=%s&price=%s", product.name(), product.price()));
    }

    public List<Product> getProducts() {
        String res = executeCommand("get-products");
        res = removeHtmlHeaders(res);
        List<String> productsStr = Arrays.asList(res.split("</br>\\s*"));

        return productsStr.stream().map(CommandsExecutor::parseProduct).collect(Collectors.toList());
    }

    public int getCount() {
        String res = executeQuery("count").split("Number of products:\\s*")[1];
        return Integer.parseInt(res);
    }

    public int getSum() {
        String res = executeQuery("sum").split("Summary price:\\s*")[1];
        return Integer.parseInt(res);
    }

    public Product getMin() {
        String res = executeQuery("min").split("<h1>Product with min price: </h1>\\s*")[1];
        res = res.split("</br>\\s*")[0];
        return parseProduct(res);
    }

    public Product getMax() {
        String res = executeQuery("max").split("<h1>Product with max price: </h1>\\s*")[1];
        res = res.split("</br>\\s*")[0];
        return parseProduct(res);
    }

    private String executeQuery(String command) {
        String res =  executeCommand(String.format("query?command=%s", command));
        res = removeHtmlHeaders(res);
        return res;
    }


    private static Product parseProduct(String productStr) {
        String[] parts = productStr.split("\\s+");
        return new Product(parts[0], Integer.parseInt(parts[1]));
    }

    private String executeCommand(String command) {
        HttpRequest request = HttpRequest.newBuilder(
                        URI.create(String.format("%s/%s", this.server, command)))
                .GET()
                .build();

        try {
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            return response.body();
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private static String removeHtmlHeaders(String s) {
        s = s.replaceAll("<html><body>\\s*", "");
        s = s.replaceAll("\\s*</body></html>\\s*", "");
        return s;
    }
}
