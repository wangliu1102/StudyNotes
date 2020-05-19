package com.wl.redis.jedis.jedisPool;

import com.wl.redis.jedis.JedisApplicationTests;
import com.wl.redis.jedis.service.JedisService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Response;
import redis.clients.jedis.Transaction;

import java.util.*;

/**
 * @author 王柳
 * @date 2020/5/18 17:12
 */
@Slf4j
public class TestJedisPool extends JedisApplicationTests {

    @Autowired
    JedisService jedisService;

    @Test
    public void Test01() {
        boolean isSet = jedisService.set("girlfriend", "sakura");
        log.info("girlfriend============>set: " + isSet);

    }

    @Test
    public void Test02() {
        String str = jedisService.get("girlfriend", String.class);
        log.info("girlfriend============>get: " + str);
    }
}
