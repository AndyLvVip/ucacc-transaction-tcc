package ucacc.demo.tcc.service;

import lombok.extern.slf4j.Slf4j;
import org.mengyun.tcctransaction.api.Compensable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ucacc.demo.tcc.domain.Order;
import ucacc.demo.tcc.repository.OrderRepository;

import java.util.UUID;

@Service
@Slf4j
public class OrderService {

    private final OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Transactional
    public void save(Order order) {
        order.setId(UUID.randomUUID().toString());
        this.orderRepository.save(order);
    }

    @Compensable(confirmMethod = "confirmBook", cancelMethod = "cancelBook")
    @Transactional
    public void tryBook(String productId) {
        log.info("try book for product: {}", productId);
        Order order = new Order();
        order.setProductId(productId);
        order.setStatus("DRAFT");
        save(order);
    }

    @Transactional
    public void confirmBook(String productId) {
        log.info("confirm book for product: {}", productId);
        Order order = orderRepository.findByProductId(productId);
        if(null == order)
            throw new RuntimeException("can not find order by productId: " + productId);
        if("DRAFT".equals(order.getStatus())) {
            order.setStatus("NORMAL");
        }
    }

    @Transactional
    public void cancelBook(String productId) {
        log.info("cancel book for product: {}", productId);
        Order order = orderRepository.findByProductId(productId);
        if(null != order)
            orderRepository.delete(order);
    }
}
