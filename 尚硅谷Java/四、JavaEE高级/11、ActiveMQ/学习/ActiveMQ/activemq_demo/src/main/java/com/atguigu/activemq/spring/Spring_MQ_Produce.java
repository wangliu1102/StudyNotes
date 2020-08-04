package com.atguigu.activemq.spring;

import org.apache.xbean.spring.context.ClassPathXmlApplicationContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Service;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

/**
 * Spring整合-队列生产者
 *
 * @author 王柳
 * @date 2020/7/31 14:01
 */
@Service
public class Spring_MQ_Produce {

    @Autowired
    private JmsTemplate jmsTemplate;

    public static void main(String[] args) {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("spring-activemq.xml");
        Spring_MQ_Produce springMQProducer = applicationContext.getBean(Spring_MQ_Produce.class);
        springMQProducer.jmsTemplate.send(
                new MessageCreator() {
                    @Override
                    public Message createMessage(Session session) throws JMSException {
                        return session.createTextMessage("***Spring和ActiveMQ的整合case111.....");
                    }
                }
        );
        System.out.println("********send task over");
    }
}
