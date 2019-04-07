package ucacc.demo.tcc.domain;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "product")
@Data
@Entity
public class Product {
    @Id
    @Column
    private String id;
    @Column
    private Integer storage;
    @Column
    private String status;
}
