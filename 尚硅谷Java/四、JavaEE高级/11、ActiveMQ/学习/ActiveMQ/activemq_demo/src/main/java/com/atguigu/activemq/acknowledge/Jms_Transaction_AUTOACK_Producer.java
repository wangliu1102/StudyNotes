package com.atguigu.activemq.acknowledge;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * 事务下的生产者
 * 事务下的消费者如何使用手动签收的方式
 *
 * @author 王柳
 * @date 2020/7/31 9:40
 */
public class Jms_Transaction_AUTOACK_Producer {
    private static final String ACTIVEMQ_URL = "tcp://192.168.1.235:61616";
    private static final String ACTIVEMQ_QUEUE_NAME = "Queue-ACK-Transaction";


    public static void main(String[] args) throws JMSException {
        ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory(ACTIVEMQ_URL);
        Connection connection = activeMQConnectionFactory.createConnection();
        connection.start();
        // 设置事务
        Session session = connection.createSession(true, Session.AUTO_ACKNOWLEDGE);
        Queue queue = session.createQueue(ACTIVEMQ_QUEUE_NAME);
        MessageProducer producer = session.createProducer(queue);


        for (int i = 0; i < 3; i++) {
            TextMessage textMessage = session.createTextMessage("Transaction_AUTOACK-msg:   " + i);
            producer.send(textMessage);
        }
        session.commit();
        System.out.println("发送完成");
        producer.close();
        session.close();
        connection.close();
    }
}
