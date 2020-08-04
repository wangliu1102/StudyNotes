package com.atguigu.activemq.broke;

import org.apache.activemq.broker.BrokerService;

/**
 * 嵌入式的broker启动
 *
 * @author 王柳
 * @date 2020/7/31 10:34
 */
public class EmbedBroker {

    public static void main(String[] args) throws Exception {
        //ActiveMQ也支持在vm中通信基于嵌入的broker
        BrokerService brokerService = new BrokerService();
        brokerService.setPopulateJMSXUserID(true);
        brokerService.addConnector("tcp://127.0.0.1:61616");
        brokerService.start();
    }

}
