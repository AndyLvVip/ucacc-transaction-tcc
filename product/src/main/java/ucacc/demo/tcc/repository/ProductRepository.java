package ucacc.demo.tcc.repository;

import org.springframework.data.repository.CrudRepository;
import ucacc.demo.tcc.domain.Product;

public interface ProductRepository extends CrudRepository<Product, String> {
}
