package com.wl.redis.jedis.jedis;

import com.wl.redis.jedis.JedisApplicationTests;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.test.context.transaction.TestTransaction;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Response;
import redis.clients.jedis.Transaction;

import java.util.*;

/**
 * @author 王柳
 * @date 2020/5/18 17:12
 */
@Slf4j
public class TestJedis extends JedisApplicationTests {

    /**
     * 测试Jedis连接
     */
    @Test
    public void test01() {
        Jedis jedis = new Jedis("127.0.0.1", 6379);
        jedis.auth("ahhs2019");
        log.info("connection is OK===========>" + jedis.ping());
    }

    /**
     * 测试 一个key + 五大数据类型
     */
    @Test
    public void test02() {

        Jedis jedis = new Jedis("127.0.0.1", 6379);
        jedis.auth("ahhs2019");

        //key
        log.info("key----------------------------------------");
        Set<String> keys = jedis.keys("*");
        for (Iterator iterator = keys.iterator(); iterator.hasNext(); ) {
            String key = (String) iterator.next();
            log.info(key);
        }
        log.info("判断某个key是否存在====>" + jedis.exists("k2"));

        log.info("查看还有多少秒过期，-1表示永不过期，-2表示已过期=======>" + jedis.ttl("k1"));

        //String
        log.info("String----------------------------------------");
        log.info("append之前：" + jedis.get("k1"));
        jedis.append("k1", "myreids");
        log.info("append之后：" + jedis.get("k1"));

        // 设置k-v
        jedis.set("k4", "k4_redis");

        // 设置多个k-v
        jedis.mset("str1", "v1", "str2", "v2", "str3", "v3");
        log.info("获取多个k-v：" + jedis.mget("str1", "str2", "str3"));

        //list
        log.info("list----------------------------------------");
        // 从左边添加list
        jedis.lpush("mylist", "v1", "v2", "v3", "v4", "v5");
        // lrange 0, -1 获取list所有
        List<String> list = jedis.lrange("mylist", 0, -1);
        for (String element : list) {
            log.info(element);
        }

        //set
        log.info("set----------------------------------------");
        // 添加set
        jedis.sadd("orders", "jd001");
        jedis.sadd("orders", "jd002");
        jedis.sadd("orders", "jd003");
        Set<String> set1 = jedis.smembers("orders");
        for (Iterator iterator = set1.iterator(); iterator.hasNext(); ) {
            String string = (String) iterator.next();
            log.info(string);
        }
        // 删除集合中元素
        jedis.srem("orders", "jd002");
        log.info("获取指定key中的所有元素集合" + jedis.smembers("orders").size());

        //hash
        log.info("hash----------------------------------------");
        // 添加hash
        jedis.hset("hash1", "userName", "lisi");
        // 获取hash中的指定字段的值
        log.info(jedis.hget("hash1", "userName"));
        Map<String, String> map = new HashMap<String, String>();
        map.put("telphone", "13811814763");
        map.put("address", "atguigu");
        map.put("email", "abc@163.com");
        // 批量添加hash
        jedis.hmset("hash2", map);
        // 批量获取hash
        List<String> result = jedis.hmget("hash2", "telphone", "email");
        for (String element : result) {
            log.info(element);
        }

        //zset
        log.info("zset----------------------------------------");
        // 添加zset
        jedis.zadd("zset01", 60d, "v1");
        jedis.zadd("zset01", 70d, "v2");
        jedis.zadd("zset01", 80d, "v3");
        jedis.zadd("zset01", 90d, "v4");

        // 获取指定key中的所有元素集合
        Set<String> s1 = jedis.zrange("zset01", 0, -1);
        for (Iterator iterator = s1.iterator(); iterator.hasNext(); ) {
            String string = (String) iterator.next();
            log.info(string);
        }


    }

    /**
     * 事务提交：日常
     */
    @Test
    public void test03() {
        Jedis jedis = new Jedis("127.0.0.1", 6379);
        jedis.auth("ahhs2019");

        //监控key，如果改动了事务就被放弃
//        jedis.watch("serialNum");
//        jedis.set("serialNum", "s#####################");
//        jedis.unwatch();

        //被当作一个命令进行执行，标记一个事务的开始
        Transaction transaction = jedis.multi();
        Response<String> response = transaction.get("serialNum");
        transaction.set("serialNum", "s002");
        response = transaction.get("serialNum");
        transaction.lpush("list3", "a");
        transaction.lpush("list3", "b");
        transaction.lpush("list3", "c");

        Response<List<String>> response2 = transaction.lrange("list3",0,-1);

        // 执行事务块所有命令
        transaction.exec();
        // 取消事务
//        transaction.discard();
        log.info("serialNum***********" + response.get());
        log.info("list3***********" + response2.get());
    }


    /**
     * 通俗点讲，watch命令就是标记一个键，如果标记了一个键， 在提交事务前如果该键被别人修改过，那事务就会失败，这种情况通常可以在程序中
     * 重新再尝试一次。
     * 首先标记了键balance，然后检查余额是否足够，不足就取消标记，并不做扣减； 足够的话，就启动事务进行更新操作，
     * 如果在此期间键balance被其它人修改， 那在提交事务（执行exec）时就会报错， 程序中通常可以捕获这类错误再重新执行一次，直到成功。
     */
    /**
     * 事务提交：加锁
     */
    @Test
    public void Test04() {
        Jedis jedis = new Jedis("127.0.0.1", 6379);
        jedis.auth("ahhs2019");
        int balance;// 可用余额
        int debt;// 欠额
        int amtToSubtract = 10;// 实刷额度

        jedis.set("balance","100");
        jedis.set("debt","0");
        jedis.watch("balance");
//        jedis.set("balance","5");//此句不该出现，讲课方便。模拟其他程序已经修改了该条目
        balance = Integer.parseInt(jedis.get("balance"));
        log.info("balance===> " + balance);
        if (balance < amtToSubtract) {
            jedis.unwatch();
            log.info("modify");
        } else {
            log.info("***********transaction");
            Transaction transaction = jedis.multi();
            transaction.decrBy("balance", amtToSubtract);
            transaction.incrBy("debt", amtToSubtract);
            transaction.exec();
            balance = Integer.parseInt(jedis.get("balance"));
            debt = Integer.parseInt(jedis.get("debt"));

            log.info("*******" + balance);
            log.info("*******" + debt);
        }
    }

    /**
     * 6379,6380启动，先各自先独立
     * 主写
     * 从读
     */
    @Test
    public void Test05() throws InterruptedException {
        Jedis jedis_M = new Jedis("192.168.253.131",6379);
        jedis_M.auth("123456");
        Jedis jedis_S = new Jedis("192.168.253.131",6380);
        jedis_S.auth("123456");

        log.info(jedis_S.slaveof("192.168.253.131",6379));

        jedis_M.set("k6","v6");
        Thread.sleep(500);
        log.info(jedis_S.get("k6"));
    }
}
