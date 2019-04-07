package ucacc.demo.tcc.domain;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "od_order")
@Data
@Entity
public class Order {
    @Id
    @Column
    private String id;
    @Column
    private String productId;
    @Column
    private String status;
}
