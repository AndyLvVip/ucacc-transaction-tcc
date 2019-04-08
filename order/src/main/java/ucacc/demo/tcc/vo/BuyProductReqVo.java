package ucacc.demo.tcc.vo;

import lombok.Data;
import org.mengyun.tcctransaction.api.TransactionContext;

@Data
public class BuyProductReqVo {
    private TransactionContext transactionContext;
    private String productId;
}
