package com.atguigu.activemq.redelivery;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.RedeliveryPolicy;

import javax.jms.*;
import java.io.IOException;

/**
 * 简单消息消费者-阻塞式消费者--消息重试机制
 *
 * @author 王柳
 * @date 2020/7/30 14:14
 */
public class Redelivery_JmsConsumer {
    private static final String ACTIVEMQ_URL = "tcp://192.168.1.235:61616";
    private static final String QUEUE_NAME = "redelivery01";

    public static void main(String[] args) throws JMSException, IOException {
        ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory(ACTIVEMQ_URL);
        // 修改默认参数，设置消息消费重试3次
        RedeliveryPolicy queuePolicy = new RedeliveryPolicy();
        queuePolicy.setMaximumRedeliveries(2);
        Connection connection = activeMQConnectionFactory.createConnection();
        connection.start();
        // 开启事务
        final Session session = connection.createSession(true, Session.AUTO_ACKNOWLEDGE);
        Queue queue = session.createQueue(QUEUE_NAME);
        MessageConsumer messageConsumer = session.createConsumer(queue);
        messageConsumer.setMessageListener(new MessageListener() {
            @Override
            public void onMessage(Message message) {
                if (message instanceof TextMessage) {
                    TextMessage textMessage = (TextMessage) message;
                    try {
                        System.out.println("***消费者接收到的消息:   " + textMessage.getText());
                        //session.commit();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        //关闭资源
        System.in.read();
        messageConsumer.close();
        session.close();
        connection.close();
    }

}
