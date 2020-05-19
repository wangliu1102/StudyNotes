package com.wl.redis.jedis.restTemplate;

import com.wl.redis.jedis.JedisApplicationTests;
import com.wl.redis.jedis.service.RedisService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author 王柳
 * @date 2020/5/18 17:12
 */
@Slf4j
public class TestRedisTemplate extends JedisApplicationTests {

    @Autowired
    RedisService redisService;

    @Test
    public void Test01() {
        redisService.set("boyfriend", "selenium");
        String str = redisService.get("boyfriend");
        log.info("girlfriend============>get: " + str);

    }

}
