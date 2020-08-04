package com.atguigu.activemq.topic;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;
import java.io.IOException;

/**
 * 订阅主题的消费者
 *
 * @author 王柳
 * @date 2020/7/30 14:50
 */
public class JmsConsumer_Topic1 {
    public static final String ACTIVEMQ_URL = "tcp://192.168.1.235:61616";
    public static final String TOPIC_NAME = "topic01";


    public static void main(String[] args) throws JMSException, IOException {
        System.out.println("我是1号消费者");

        //1.创建连接工厂，按照给定的URL，采用默认的用户名密码
        ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory(ACTIVEMQ_URL);

        //2.通过连接工厂,获得connection并启动访问
        Connection connection = activeMQConnectionFactory.createConnection();
        connection.start();

        //3.创建会话session
        //两个参数transacted=事务,acknowledgeMode=确认模式(签收)
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

        //4.创建目的地(具体是队列queue还是主题topic)
        Topic topic = session.createTopic(TOPIC_NAME);

        //5.创建消息的消费者
        MessageConsumer messageConsumer = session.createConsumer(topic);

        //6.通过监听的方式消费消息
        messageConsumer.setMessageListener(message -> {
            if (message != null && message instanceof TextMessage) {
                //7.把message转换成消息发送前的类型并获取消息内容
                TextMessage textMessage = (TextMessage) message;
                try {
                    System.out.println("****消费者接收到Topic的消息:  " + textMessage.getText());
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            }
        });

        //保证控制台不关闭,阻止程序关闭
        System.in.read();

        //关闭资源
        messageConsumer.close();
        session.close();
        connection.close();
    }
}
