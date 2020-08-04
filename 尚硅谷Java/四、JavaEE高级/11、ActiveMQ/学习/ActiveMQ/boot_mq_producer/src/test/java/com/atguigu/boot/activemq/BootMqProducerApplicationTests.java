package com.atguigu.boot.activemq;

import com.atguigu.boot.activemq.produce.Queue_Produce;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
class BootMqProducerApplicationTests {

    @Test
    void contextLoads() {
    }

    @Resource    //  这个是java 的注解，而Autowried 是 spring 的
    private Queue_Produce queue_produce;

    @Test
    public void testSend() {
        queue_produce.produceMessage();
    }


}
