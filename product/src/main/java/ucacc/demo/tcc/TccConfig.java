package ucacc.demo.tcc;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@ConfigurationProperties(prefix = "tcc.jdbc")
@Data
@Configuration
public class TccConfig {

    private String driverClassName;

    private String url;

    private String username;

    private String password;
}
