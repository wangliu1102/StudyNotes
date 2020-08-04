package com.atguigu.boot.activemq.consummer;


import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import javax.jms.TextMessage;

@Component
public class Queue_consummer {

    // 监听过后会随着springboot一起启动,有消息就执行加了该注解的方法
    @JmsListener(destination = "${myQueueName}")     // 注解监听
    public void receive(TextMessage textMessage) throws  Exception{
        System.out.println(" ***  消费者收到消息  ***"+textMessage.getText());
    }

}
