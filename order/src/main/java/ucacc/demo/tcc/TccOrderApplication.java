package ucacc.demo.tcc;

import org.mengyun.tcctransaction.serializer.KryoPoolSerializer;
import org.mengyun.tcctransaction.spring.recover.DefaultRecoverConfig;
import org.mengyun.tcctransaction.spring.repository.SpringJdbcTransactionRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

@SpringBootApplication
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class TccOrderApplication {

    public static void main(String[] args) {
        SpringApplication.run(TccOrderApplication.class, args);
    }

    @Bean
    public DefaultRecoverConfig recoverConfig() {
        DefaultRecoverConfig recoverConfig = new DefaultRecoverConfig();
        recoverConfig.setMaxRetryCount(5);
        recoverConfig.setRecoverDuration(60);
        recoverConfig.setCronExpression("0/30 * * * * ?");
        return recoverConfig;
    }

    @Bean
    public SpringJdbcTransactionRepository transactionRepository(TccConfig config, KryoPoolSerializer objectSerializer) {
        SpringJdbcTransactionRepository transactionRepository = new SpringJdbcTransactionRepository();
        transactionRepository.setDataSource(tccDataSource(config));
        transactionRepository.setDomain("ORDER");
        transactionRepository.setTbSuffix("_ORDER");
        transactionRepository.setSerializer(objectSerializer);
        return transactionRepository;
    }

    private DataSource tccDataSource(TccConfig config) {
        DriverManagerDataSource tccDataSource = new DriverManagerDataSource();
        tccDataSource.setDriverClassName(config.getDriverClassName());
        tccDataSource.setUrl(config.getUrl());
        tccDataSource.setUsername(config.getUsername());
        tccDataSource.setPassword(config.getPassword());
        return tccDataSource;
    }

    @Bean
    public KryoPoolSerializer objectSerializer() {
        return new KryoPoolSerializer();
    }
}
