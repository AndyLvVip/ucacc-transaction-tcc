package ucacc.demo.tcc.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import ucacc.demo.tcc.domain.Product;
import ucacc.demo.tcc.repository.ProductRepository;
import ucacc.demo.tcc.vo.BuyProductReqVo;

import java.util.Optional;
import java.util.UUID;

import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
public class ProductControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    ProductRepository productRepository;

    @Test
    public void tryBuy() throws Exception {
        Product p = new Product();
        p.setId(UUID.randomUUID().toString());
        p.setStatus("NORMAL");
        p.setStorage(10);
        doReturn(Optional.of(p)).when(productRepository).findById(p.getId());
        doReturn(p).when(productRepository).save(p);
        BuyProductReqVo vo = new BuyProductReqVo();
        vo.setProductId(p.getId());
        this.mockMvc.perform(post("/product/buy")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(new ObjectMapper().writeValueAsString(vo))
        ).andExpect(status().isOk())
        ;
    }
}
