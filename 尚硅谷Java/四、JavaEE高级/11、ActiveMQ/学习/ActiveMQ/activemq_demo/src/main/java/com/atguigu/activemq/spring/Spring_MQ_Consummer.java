package com.atguigu.activemq.spring;

import org.apache.xbean.spring.context.ClassPathXmlApplicationContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

/**
 * Spring整合-队列消费者
 *
 * @author 王柳
 * @date 2020/7/31 14:07
 */
@Service
public class Spring_MQ_Consummer {

    @Autowired
    private JmsTemplate jmsTemplate;

    public static void main(String[] args) {
        ApplicationContext ac = new ClassPathXmlApplicationContext("spring-activemq.xml");
        Spring_MQ_Consummer sm = (Spring_MQ_Consummer) ac.getBean(Spring_MQ_Consummer.class);

        String s = (String) sm.jmsTemplate.receiveAndConvert();
        System.out.println(" *** 消费者消息" + s);
    }
}
