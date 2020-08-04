package com.atguigu.activemq.acknowledge;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * 非事务下的生产者
 * 非事务下的消费者如何使用手动签收的方式
 *
 * @author 王柳
 * @date 2020/7/31 9:37
 */
public class NoTransaction_Jms_Producer {
    private static final String ACTIVEMQ_URL = "tcp://192.168.1.235:61616";
    private static final String ACTIVEMQ_QUEUE_NAME = "Queue-ACK-NoTransaction";

    public static void main(String[] args) throws JMSException {
        ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory(ACTIVEMQ_URL);
        Connection connection = activeMQConnectionFactory.createConnection();
        connection.start();
        // 设置非事务
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Queue queue = session.createQueue(ACTIVEMQ_QUEUE_NAME);
        MessageProducer producer = session.createProducer(queue);
        try {
            for (int i = 0; i < 3; i++) {
                TextMessage textMessage = session.createTextMessage("tx msg--" + i);
                producer.send(textMessage);
            }
            System.out.println("消息发送完成");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            producer.close();
            session.close();
            connection.close();
        }
    }

}
