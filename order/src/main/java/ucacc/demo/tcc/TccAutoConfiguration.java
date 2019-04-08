package ucacc.demo.tcc;

import org.mengyun.tcctransaction.recover.TransactionRecovery;
import org.mengyun.tcctransaction.serializer.KryoPoolSerializer;
import org.mengyun.tcctransaction.spring.ConfigurableCoordinatorAspect;
import org.mengyun.tcctransaction.spring.ConfigurableTransactionAspect;
import org.mengyun.tcctransaction.spring.recover.DefaultRecoverConfig;
import org.mengyun.tcctransaction.spring.recover.RecoverScheduledJob;
import org.mengyun.tcctransaction.spring.repository.SpringJdbcTransactionRepository;
import org.mengyun.tcctransaction.spring.support.SpringBeanFactory;
import org.mengyun.tcctransaction.spring.support.SpringTransactionConfigurator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import javax.sql.DataSource;

/**
 * Created by andy.lv
 * on: 2019/4/8 13:42
 */
@Configuration
public class TccAutoConfiguration {

    @Bean
    public SpringBeanFactory springBeanFactory() {
        return new SpringBeanFactory();
    }

    @Bean(initMethod = "init")
    public SpringTransactionConfigurator transactionConfigurator() {
        return new SpringTransactionConfigurator();
    }

    @Bean(initMethod = "init")
    public ConfigurableTransactionAspect compensableTransactionAspect(SpringTransactionConfigurator transactionConfigurator) {
        ConfigurableTransactionAspect compensableTransactionAspect = new ConfigurableTransactionAspect();
        compensableTransactionAspect.setTransactionConfigurator(transactionConfigurator);
        return compensableTransactionAspect;
    }

    @Bean(initMethod = "init")
    public ConfigurableCoordinatorAspect resourceCoordinatorAspect(SpringTransactionConfigurator transactionConfigurator) {
        ConfigurableCoordinatorAspect resourceCoordinatorAspect = new ConfigurableCoordinatorAspect();
        resourceCoordinatorAspect.setTransactionConfigurator(transactionConfigurator);
        return resourceCoordinatorAspect;
    }

    @Bean
    public TransactionRecovery transactionRecovery(SpringTransactionConfigurator transactionConfigurator) {
        TransactionRecovery transactionRecovery = new TransactionRecovery();
        transactionRecovery.setTransactionConfigurator(transactionConfigurator);
        return transactionRecovery;
    }

    @Bean
    public SchedulerFactoryBean recoverScheduler() {
        return new SchedulerFactoryBean();
    }

    @Bean(initMethod = "init")
    public RecoverScheduledJob recoverScheduledJob(TransactionRecovery transactionRecovery, SchedulerFactoryBean recoverScheduler, SpringTransactionConfigurator transactionConfigurator) {
        RecoverScheduledJob recoverScheduledJob = new RecoverScheduledJob();
        recoverScheduledJob.setTransactionRecovery(transactionRecovery);
        recoverScheduledJob.setTransactionConfigurator(transactionConfigurator);
        recoverScheduledJob.setScheduler(recoverScheduler.getScheduler());
        return recoverScheduledJob;
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
        transactionRepository.setDomain("PRODUCT");
        transactionRepository.setTbSuffix("_PRODUCT");
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
