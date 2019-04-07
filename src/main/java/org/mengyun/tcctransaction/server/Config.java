package org.mengyun.tcctransaction.server;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@ConfigurationProperties(prefix = "tcc")
@Data
@Configuration
public class Config {

    private Redis redis;

    private Jdbc jdbc;

    @Data
    public static class Redis {
        private String host;
        private Integer port;
        private String password;
        private Integer database;
        private Integer timeout;

        private Pool pool;

        @Data
        public static class Pool {
            private Integer maxTotal;
            private Integer maxIdle;
            private Integer minIdle;
            private Integer maxWaitMillis;
        }
    }

    @Data
    public static class Jdbc {
        private String url;

        private String username;

        private String password;
    }
}
