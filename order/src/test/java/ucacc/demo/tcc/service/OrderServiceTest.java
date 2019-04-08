package ucacc.demo.tcc.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import ucacc.demo.tcc.domain.Order;
import ucacc.demo.tcc.repository.OrderRepository;

import java.util.UUID;

@SpringBootTest
@RunWith(SpringRunner.class)
@Transactional
public class OrderServiceTest {

    @Autowired
    OrderService orderService;

    @Autowired
    OrderRepository orderRepository;

    @Test
    public void create() {
        Order order = new Order();
        order.setProductId(UUID.randomUUID().toString());
        order.setStatus("DRAFT");
        orderService.save(order);
    }

    @Test
    @Rollback(false)
    public void book() {
        String productId = UUID.randomUUID().toString();
        orderService.tryBook(productId);
        orderService.confirmBook(productId);
        Order order = orderRepository.findByProductId(productId);
        System.out.println(order);
    }

    @Test
    @Rollback(false)
    public void tryBook() {
        String productId = "1080167f-ed64-420f-89a9-60e989ba05a0";
        orderService.tryBook(productId);
    }
}
