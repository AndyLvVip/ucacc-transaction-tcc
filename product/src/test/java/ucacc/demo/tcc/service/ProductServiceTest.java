package ucacc.demo.tcc.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import ucacc.demo.tcc.domain.Product;

import java.util.UUID;

@SpringBootTest
@RunWith(SpringRunner.class)
@Transactional
public class ProductServiceTest {

    @Autowired
    ProductService productService;

    @Test
    public void save() {
        Product product = new Product();
        product.setId(UUID.randomUUID().toString());
        product.setStatus("NORMAL");
        product.setStorage(10);
        productService.save(product);
    }

    @Test
    public void tryBuy() {
        Product product = new Product();
        product.setId(UUID.randomUUID().toString());
        product.setStatus("NORMAL");
        product.setStorage(10);
        productService.save(product);

        productService.tryBuy(product.getId());
    }
}
