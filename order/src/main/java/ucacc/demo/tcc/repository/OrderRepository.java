package ucacc.demo.tcc.repository;

import org.springframework.data.repository.CrudRepository;
import ucacc.demo.tcc.domain.Order;

public interface OrderRepository extends CrudRepository<Order, String> {

    Order findByProductId(String productId);
}
