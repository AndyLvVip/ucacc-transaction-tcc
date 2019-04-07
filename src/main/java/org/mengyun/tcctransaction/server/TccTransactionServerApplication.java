package org.mengyun.tcctransaction.server;

import org.mengyun.tcctransaction.server.dao.RedisTransactionDao;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

@SpringBootApplication
public class TccTransactionServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(TccTransactionServerApplication.class, args);
    }

    @Bean
    public JedisPoolConfig jedisPoolConfig(Config config) {
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxIdle(config.getRedis().getPool().getMaxTotal());
        jedisPoolConfig.setMaxIdle(config.getRedis().getPool().getMaxIdle());
        jedisPoolConfig.setMinIdle(config.getRedis().getPool().getMinIdle());
        jedisPoolConfig.setMaxWaitMillis(config.getRedis().getPool().getMaxWaitMillis());
        return jedisPoolConfig;
    }

    @Bean
    public JedisPool jedisPool(JedisPoolConfig jedisPoolConfig, Config config) {
        JedisPool jedisPool = new JedisPool(
                jedisPoolConfig
                , config.getRedis().getHost()
                , config.getRedis().getPort()
                , config.getRedis().getTimeout()
                , null
                , config.getRedis().getDatabase()
        );
        return jedisPool;
    }


    private RedisTransactionDao redisTransactionDao(JedisPool jedisPool, String flag) {
        RedisTransactionDao ordRedisOrderDao = new RedisTransactionDao();
        ordRedisOrderDao.setJedisPool(jedisPool);
        ordRedisOrderDao.setKeySuffix(flag);
        ordRedisOrderDao.setDomain(flag);
        return ordRedisOrderDao;
    }

    @Bean
    public RedisTransactionDao ordRedisOrderDao(JedisPool jedisPool) {
        return redisTransactionDao(jedisPool, "ORD");
    }

    @Bean
    public RedisTransactionDao redRedisOrderDao(JedisPool jedisPool) {
        return redisTransactionDao(jedisPool, "RED");
    }

    @Bean
    public RedisTransactionDao capRedisOrderDao(JedisPool jedisPool) {
        return redisTransactionDao(jedisPool, "CAP");
    }

}
