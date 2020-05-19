package com.wl.redis.jedis.config;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import javax.annotation.Resource;

/**
 * 创建edisPool对象
 */
@Component
public class JedisPoolFactory {

    @Resource
    private JedisConfig jedisConfig;

    @Bean
    public JedisPool getJedisPool() {
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        // 控制一个pool最多有多少个状态为idle(空闲)的jedis实例；
        jedisPoolConfig.setMaxIdle(jedisConfig.getPoolMaxIdle());
        // 表示当borrow一个jedis实例时，最大的等待时间，如果超过等待时间，则直接抛JedisConnectionException；
        jedisPoolConfig.setMaxWaitMillis(jedisConfig.getPoolMaxWait());
        // 在指定时刻通过pool能够获取到的最大的连接的jedis个数
        jedisPoolConfig.setMaxTotal(jedisConfig.getPoolMaxTotal());
        JedisPool jp = new JedisPool(jedisPoolConfig, jedisConfig.getHost(), jedisConfig.getPort(),
                jedisConfig.getTimeout(), jedisConfig.getPassword(), jedisConfig.getDb());
        return jp;
    }
}
