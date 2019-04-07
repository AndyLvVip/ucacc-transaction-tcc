package ucacc.demo.tcc.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ucacc.demo.tcc.domain.Product;
import ucacc.demo.tcc.repository.ProductRepository;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Transactional
    public void save(Product product) {
        this.productRepository.save(product);
    }
}
