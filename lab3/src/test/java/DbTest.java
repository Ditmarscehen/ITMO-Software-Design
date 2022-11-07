import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import ru.akirakozov.sd.refactoring.db.query.*;
import ru.akirakozov.sd.refactoring.db.update.AddProductUpdate;
import ru.akirakozov.sd.refactoring.model.Product;

import java.sql.SQLException;
import java.time.Instant;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;

public class DbTest {
    @Test
    @Order(1)
    public void addProductTest() throws SQLException {
        int prevSize = GetProductsQuery.createAndExecute().size();
        List<Product> addedProducts = addProducts(3);
        List<Product> products = GetProductsQuery.createAndExecute();
        assertThat(products).containsAll(addedProducts);
        assertThat(prevSize).isEqualTo(products.size() - addedProducts.size());
    }

    @Test
    @Order(2)
    public void testCount() throws SQLException {
        int count = CountQuery.createAndExecute();
        int size = GetProductsQuery.createAndExecute().size();
        assertThat(count).isEqualTo(size);
    }

    @Test
    @Order(2)
    public void testMin() throws SQLException {
        Optional<Product> expectedMin = GetProductsQuery.createAndExecute().stream().min(Comparator.comparing(Product::price));
        Product actualMin = MinQuery.createAndExecute();
        if (expectedMin.isPresent()) {
            assertThat(expectedMin.get()).isEqualTo(actualMin);
        } else {
            assertThat(actualMin).isEqualTo(null);
        }
    }

    @Test
    @Order(3)
    public void testMax() throws SQLException {
        Optional<Product> expectedMax = GetProductsQuery.createAndExecute().stream().max(Comparator.comparing(Product::price));
        Product actualMax = MaxQuery.createAndExecute();
        if (expectedMax.isPresent()) {
            assertThat(expectedMax.get()).isEqualTo(actualMax);
        } else {
            assertThat(actualMax).isEqualTo(null);
        }
    }

    @Test
    @Order(4)
    public void testSum() throws SQLException {
        Optional<Integer> expectedSum = GetProductsQuery.createAndExecute().stream()
                .map(Product::price)
                .reduce(Integer::sum);
        Integer actualSum = SumQuery.createAndExecute();
        if (expectedSum.isPresent()) {
            assertThat(expectedSum.get()).isEqualTo(actualSum);
        } else {
            assertThat(actualSum).isEqualTo(null);
        }
    }


    private static String generateProductName() {
        return String.valueOf(Instant.now().getNano());
    }

    private List<Product> addProducts(int n) throws SQLException {
        List<Product> products = IntStream.range(1, n + 1)
                .mapToObj(i -> new Product(generateProductName(), i * 10))
                .collect(Collectors.toList());
        for (Product product : products) {
            AddProductUpdate update = new AddProductUpdate(product);
            update.executeUpdate();
        }
        return products;
    }
}
