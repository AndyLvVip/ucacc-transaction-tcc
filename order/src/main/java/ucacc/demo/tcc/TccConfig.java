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

    private C3p0 c3p0;

    @Data
    public static class C3p0 {

        private Integer initialPoolSize;

        private Integer minPoolSize;

        private Integer maxPoolSize;

        private Integer acquireIncrement;

        private Integer maxIdleTime;

        private Integer checkoutTimeout;
    }
}
