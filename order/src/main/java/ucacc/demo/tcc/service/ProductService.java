package ucacc.demo.tcc.service;

import org.mengyun.tcctransaction.api.Compensable;
import org.mengyun.tcctransaction.api.TransactionContext;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ucacc.demo.tcc.vo.BuyProductReqVo;

@Service
public class ProductService {

    private RestTemplate restTemplate;

    public ProductService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Compensable(confirmMethod = "buyProduct", cancelMethod = "buyProduct")
    public void buyProduct(TransactionContext transactionContext, String productId) {
        BuyProductReqVo vo = new BuyProductReqVo();
        vo.setTransactionContext(transactionContext);
        vo.setProductId(productId);

        this.restTemplate.postForEntity(
                "http://127.0.0.1:7081/product/buy"
                , vo
                , Void.class
        );
    }
}
