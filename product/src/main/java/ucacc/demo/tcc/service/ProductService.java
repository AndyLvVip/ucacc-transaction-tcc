package ucacc.demo.tcc.service;

import lombok.extern.slf4j.Slf4j;
import org.mengyun.tcctransaction.api.Compensable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ucacc.demo.tcc.domain.Product;
import ucacc.demo.tcc.repository.ProductRepository;

@Service
@Slf4j
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Transactional
    public void save(Product product) {
        this.productRepository.save(product);
    }

    @Compensable(confirmMethod = "confirmBuy", cancelMethod = "cancelBuy")
    @Transactional
    public void tryBuy(String productId) {
        log.info("try buy product: {}" + productId);
        Product product = productRepository.findById(productId).get();

        product.setStorage(product.getStorage() - 1);
        product.setStatus("BUYING");
        productRepository.save(product);
    }

    @Transactional
    public void confirmBuy(String productId) {
        log.info("confirm buy product: {}" + productId);
        Product product = productRepository.findById(productId).get();
        if("BUYING".equals(product.getStatus())) {
            product.setStatus("NORMAL");
            productRepository.save(product);
        }
    }

    @Transactional
    public void cancel(String productId) {
        log.info("cancel buy product: {}" + productId);
        Product product = productRepository.findById(productId).get();
        if("BUYING".equals(product.getStatus())) {
            product.setStatus("NORMAL");
            product.setStorage(product.getStorage() + 1);
            productRepository.save(product);
        }
    }
}
