package com.atguigu.activemq.persistent;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * queue消息生产者-非持久化
 * 运行结果证明：当生产者成功发布消息之后，MQ服务端宕机重启，消息消费者就收不到该消息了
 *
 * @author 王柳
 * @date 2020/7/30 13:50
 */
public class NoPersistent_JmsProduce {
    private static final String ACTIVEMQ_URL = "tcp://192.168.1.235:61616";
    private static final String QUEUE_NAME = "queue01";

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
        Queue queue = session.createQueue(QUEUE_NAME);

        //5.创建消息的生产者
        MessageProducer messageProducer = session.createProducer(queue);

        // 非持久化
        messageProducer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);

        //6.通过使用消息生产者,生产三条消息,发送到MQ的队列里面
        for (int i = 0; i < 3; i++) {
            //7.创建消息
            //理解为一个字符串
            TextMessage textMessage = session.createTextMessage("msg---hello" + i);

            //8.通过messageProducer发送给MQ队列
            messageProducer.send(textMessage);
        }

        //9.关闭资源
        messageProducer.close();
        session.close();
        connection.close();

        System.out.println("****消息发布到MQ队列完成");
    }
}
