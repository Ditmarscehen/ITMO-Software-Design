import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import util.CommandsExecutor;
import ru.akirakozov.sd.refactoring.model.Product;

import java.time.Instant;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;

public class IntegrationTest {
    private final static String HOST = "localhost";
    private final static int PORT = 8081;
    CommandsExecutor commandsExecutor = new CommandsExecutor(HOST, PORT);

    @Test
    @Order(1)
    public void addProductTest() {
        int prevSize = commandsExecutor.getProducts().size();
        List<Product> addedProducts = addProducts(3);
        List<Product> products = commandsExecutor.getProducts();
        assertThat(products).containsAll(addedProducts);
        assertThat(prevSize).isEqualTo(products.size() - addedProducts.size());
    }

    @Test
    @Order(2)
    public void testCount() {
        int count = commandsExecutor.getCount();
        int size = commandsExecutor.getProducts().size();
        assertThat(count).isEqualTo(size);
    }

    @Test
    @Order(3)
    public void testMin() {
        Optional<Product> expectedMin = commandsExecutor.getProducts().stream().min(Comparator.comparing(Product::price));
        Product actualMin = commandsExecutor.getMin();
        if (expectedMin.isPresent()) {
            assertThat(expectedMin.get()).isEqualTo(actualMin);
        } else {
            assertThat(actualMin).isEqualTo(null);
        }
    }

    @Test
    @Order(4)
    public void testMax() {
        Optional<Product> expectedMax = commandsExecutor.getProducts().stream().max(Comparator.comparing(Product::price));
        Product actualMax = commandsExecutor.getMax();
        if (expectedMax.isPresent()) {
            assertThat(expectedMax.get()).isEqualTo(actualMax);
        } else {
            assertThat(actualMax).isEqualTo(null);
        }
    }

    @Test
    @Order(5)
    public void testSum() {
        Optional<Integer> expectedSum = commandsExecutor.getProducts().stream()
                .map(Product::price)
                .reduce(Integer::sum);
        Integer actualSum = commandsExecutor.getSum();
        if (expectedSum.isPresent()) {
            assertThat(expectedSum.get()).isEqualTo(actualSum);
        } else {
            assertThat(actualSum).isEqualTo(null);
        }
    }


    private static String generateProductName() {
        return String.valueOf(Instant.now().getNano());
    }

    private List<Product> addProducts(int n) {
        List<Product> products = IntStream.range(1, n + 1)
                .mapToObj(i -> new Product(generateProductName(), i * 10))
                .collect(Collectors.toList());
        products.forEach(commandsExecutor::addProduct);
        return products;
    }
}
