package ucacc.demo.tcc.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ucacc.demo.tcc.service.ProductService;
import ucacc.demo.tcc.vo.BuyProductReqVo;

@RestController
@Slf4j
public class ProductController {
    
    private final ProductService productService;
    
    public ProductController(ProductService productService) {
        this.productService = productService;
    }
    
    @PostMapping("/product/buy")
    public void buyProduct(@RequestBody BuyProductReqVo vo) {
        log.info("recv buy product request: {}" + vo);
        this.productService.tryBuy(vo.getTransactionContext(), vo.getProductId());
    }
}
