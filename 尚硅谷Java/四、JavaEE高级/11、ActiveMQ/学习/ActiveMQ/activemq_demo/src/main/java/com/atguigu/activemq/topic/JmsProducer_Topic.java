package com.atguigu.activemq.topic;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * 订阅主题的生产者
 *
 * @author 王柳
 * @date 2020/7/30 14:50
 */
public class JmsProducer_Topic {
    public static final String ACTIVEMQ_URL = "tcp://192.168.1.235:61616";
    public static final String TOPIC_NAME = "topic01";


    public static void main(String[] args) throws JMSException {
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

        //5.创建消息的生产者
        MessageProducer messageProducer = session.createProducer(topic);

        //6.通过使用消息生产者,生产三条消息,发送到MQ的队列里面
        for (int i = 0; i < 3; i++) {
            //7.通过session创建消息
            TextMessage textMessage = session.createTextMessage("TOPIC_NAME---" + i);

            //8.使用指定好目的地的消息生产者发送消息
            messageProducer.send(textMessage);
        }

        //9.关闭资源
        messageProducer.close();
        session.close();
        connection.close();

        System.out.println("****TOPIC_NAME消息发布到MQ完成");
    }
}
