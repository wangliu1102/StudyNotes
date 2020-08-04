package com.atguigu.boot.activemq.consumer;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.TextMessage;

@Component
public class Topic_Consummer_Persistent {

    //需要在监听方法指定连接工厂
    @JmsListener(destination = "${myTopic}", containerFactory = "jmsListenerContainerFactory")
    public void consumer(TextMessage textMessage) throws JMSException {
        System.out.println("订阅者收到消息:    " + textMessage.getText());
    }
}
