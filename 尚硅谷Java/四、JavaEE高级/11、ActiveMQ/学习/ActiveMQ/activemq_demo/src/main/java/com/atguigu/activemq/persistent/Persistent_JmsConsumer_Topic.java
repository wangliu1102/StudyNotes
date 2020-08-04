package com.atguigu.activemq.persistent;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;
import java.io.IOException;

/**
 * 订阅主题的消费者--持久化
 *
 * @author 王柳
 * @date 2020/7/30 14:50
 */
public class Persistent_JmsConsumer_Topic {
    public static final String ACTIVEMQ_URL = "tcp://192.168.1.235:61616";
    public static final String TOPIC_NAME = "topic01";


    public static void main(String[] args) throws JMSException, IOException {
        System.out.println("我是1号消费者");

        //1.创建连接工厂，按照给定的URL，采用默认的用户名密码
        ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory(ACTIVEMQ_URL);

        //2.通过连接工厂,获得connection并启动访问
        Connection connection = activeMQConnectionFactory.createConnection();

        // 设置客户端ID。向MQ服务器注册自己的名称
        connection.setClientID("marrry");

        //3.创建会话session
        //两个参数transacted=事务,acknowledgeMode=确认模式(签收)
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

        //4.创建目的地(具体是队列queue还是主题topic)
        Topic topic = session.createTopic(TOPIC_NAME);

        // 创建一个topic订阅者对象。一参是topic，二参是订阅者名称
        TopicSubscriber topicSubscriber = session.createDurableSubscriber(topic,"remark...");
        // 之后再开启连接
        connection.start();

        Message message = topicSubscriber.receive();

        while (null != message){
            TextMessage textMessage = (TextMessage)message;
            System.out.println(" 收到的持久化 topic ："+textMessage.getText());
            message = topicSubscriber.receive();
        }
        session.close();
        connection.close();

    }
}
