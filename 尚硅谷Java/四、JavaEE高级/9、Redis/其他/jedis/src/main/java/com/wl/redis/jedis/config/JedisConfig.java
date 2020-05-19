package com.wl.redis.jedis.config;

/**
 * @author 王柳
 * @date 2020/5/19 11:34
 */

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Redis的配置类
 */
@Component
@ConfigurationProperties(prefix = "redis")
@Data
public class JedisConfig {
    private String host;
    private int port;
    private String password;
    private int timeout;
    private int poolMaxTotal;
    private int poolMaxIdle;
    private int poolMaxWait;
    private int db;
}
