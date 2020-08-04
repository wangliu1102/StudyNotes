package com.atguigu.activemq.acknowledge;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * 事务下的消费者-如何手动签收
 *
 * @author 王柳
 * @date 2020/7/31 9:40
 */
public class Jms_Transaction_ClientAck_Consumer {
    private static final String ACTIVEMQ_URL = "tcp://192.168.1.235:61616";
    private static final String ACTIVEMQ_QUEUE_NAME = "Queue-ACK-Transaction";


    public static void main(String[] args) throws JMSException {
        ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory(ACTIVEMQ_URL);
        Connection connection = activeMQConnectionFactory.createConnection();
        connection.start();
        //消费者设置了手动签收,就必须自己签收,向服务器发送我已经收到消息了
        Session session = connection.createSession(true, Session.CLIENT_ACKNOWLEDGE);
        Queue queue = session.createQueue(ACTIVEMQ_QUEUE_NAME);
        MessageConsumer messageConsumer = session.createConsumer(queue);
        messageConsumer.setMessageListener(new MessageListener() {
            @Override
            public void onMessage(Message message) {
                if (message instanceof TextMessage) {
                    TextMessage textMessage = (TextMessage) message;
                    try {
                        // 手动签收
                        textMessage.acknowledge();
                        //开启事务如果不提交,就算手动签收,也是无效的
                        session.commit();
                        System.out.println(textMessage.getText());
                    } catch (JMSException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }
}
