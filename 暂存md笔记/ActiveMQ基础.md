# **一、入门概述**



## 1.1  MQ的产品种类和对比

MQ就是消息中间件。MQ是一种理念，ActiveMQ是MQ的落地产品。不管是哪款消息中间件，都有如下一些技术维度：

![img](file:///D:/Documents/My Knowledge/temp/7b47fa5e-37c3-49e5-b14e-ebec57396664/128/index_files/1.png)

![img](file:///D:/Documents/My Knowledge/temp/7b47fa5e-37c3-49e5-b14e-ebec57396664/128/index_files/2.png)

### (1) kafka

编程语言：scala。

大数据领域的主流MQ。

 

### (2) rabbitmq

编程语言：erlang

基于erlang语言，不好修改底层，不要查找问题的原因，不建议选用。

 

### (3) rocketmq

编程语言：java

适用于大型项目。适用于集群。

 

### (4) activemq

编程语言：java

适用于中小型项目。

 

 

## 1.2  MQ的产生背景

系统之间直接调用存在的问题？

微服务架构后，链式调用是我们在写程序时候的一般流程,为了完成一个整体功能会将其拆分成多个函数(或子模块)，比如模块A调用模块B,模块B调用模块C,模块C调用模块D。但在大型分布式应用中，系统间的RPC交互繁杂，一个功能背后要调用上百个接口并非不可能，从单机架构过渡到分布式微服务架构的通例。这些架构会有哪些问题？

 

### **(1)**  **系统之间接口耦合比较严重**

每新增一个下游功能，都要对上游的相关接口进行改造；

举个例子：如果系统A要发送数据给系统B和系统C，发送给每个系统的数据可能有差异，因此系统A对要发送给每个系统的数据进行了组装，然后逐一发送；

当代码上线后又新增了一个需求：把数据也发送给D，新上了一个D系统也要接受A系统的数据，此时就需要修改A系统，让他感知到D系统的存在，同时把数据处理好再给D。在这个过程你会看到，每接入一个下游系统，都要对系统A进行代码改造，开发联调的效率很低。其整体架构如下图：

![img](file:///D:/Documents/My Knowledge/temp/7b47fa5e-37c3-49e5-b14e-ebec57396664/128/index_files/0.7181503748354237.png)

 

 

### **(2)**  **面对大流量并发时，容易被冲垮**

每个接口模块的吞吐能力是有限的，这个上限能力如果是堤坝，当大流量（洪水）来临时，容易被冲垮。

举个例子秒杀业务：上游系统发起下单购买操作，就是下单一个操作，很快就完成。然而，下游系统要完成秒杀业务后面的所有逻辑（读取订单，库存检查，库存冻结，余额检查，余额冻结，订单生产，余额扣减，库存减少，生成流水，余额解冻，库存解冻）。

 

 

### **(3)**  **等待同步存在性能问题**

RPC接口上基本都是同步调用，整体的服务性能遵循“木桶理论”，即整体系统的耗时取决于链路中最慢的那个接口。比如A调用B/C/D都是50ms，但此时B又调用了B1，花费2000ms，那么直接就拖累了整个服务性能。

![img](file:///D:/Documents/My Knowledge/temp/7b47fa5e-37c3-49e5-b14e-ebec57396664/128/index_files/0.5360755698223745.png)

 

### (4) 如何解决

根据上述的几个问题，在设计系统时可以明确要达到的目标：

1，要做到系统解耦，当新的模块接进来时，可以做到代码改动最小；能够解耦

2，设置流量缓冲池，可以让后端系统按照自身吞吐能力进行消费，不被冲垮；能削峰

3，强弱依赖梳理能将非关键调用链路的操作异步化并提升整体系统的吞吐能力；能够异步

 

 

## 1.3  MQ的主要作用

### (1) 异步

调用者无需等待。

### (2) 解耦

解决了系统之间耦合调用的问题。

### (3) 消峰

抵御洪峰流量，保护了主业务。

 

 

## 1.4  MQ的定义

面向消息的中间件（message-oriented middleware）MOM能够很好的解决以上问题。是指利用高效可靠的消息传递机制与平台无关的数据交流，并基于数据通信来进行分布式系统的集成。通过提供消息传递和消息排队模型在分布式环境下提供应用解耦，弹性伸缩，冗余存储、流量削峰，异步通信，数据同步等功能。

大致的过程是这样的：发送者把消息发送给消息服务器，消息服务器将消息存放在若干队列/主题topic中，在合适的时候，消息服务器会将消息转发给接受者。在这个过程中，发送和接收是异步的，也就是发送无需等待，而且发送者和接受者的生命周期也没有必然的关系；尤其在发布pub/订阅sub模式下，也可以完成一对多的通信，即让一个消息有多个接受者。

![img](file:///D:/Documents/My Knowledge/temp/7b47fa5e-37c3-49e5-b14e-ebec57396664/128/index_files/0.4632462065400004.png)

 

 

## 1.5  MQ的特点

### **(1)**  **采用异步处理模式**

消息发送者可以发送一个消息而无须等待响应。消息发送者将消息发送到一条虚拟的通道（主题或者队列）上；

消息接收者则订阅或者监听该通道。一条消息可能最终转发给一个或者多个消息接收者，这些消息接收者都无需对消息发送者做出同步回应。整个过程都是异步的。

案例：

也就是说，一个系统跟另一个系统之间进行通信的时候，假如系统A希望发送一个消息给系统B，让他去处理。但是系统A不关注系统B到底怎么处理或者有没有处理好，所以系统A把消息发送给MQ，然后就不管这条消息的“死活了”，接着系统B从MQ里面消费出来处理即可。至于怎么处理，是否处理完毕，什么时候处理，都是系统B的事儿，与系统A无关。



![img](file:///D:/Documents/My Knowledge/temp/7b47fa5e-37c3-49e5-b14e-ebec57396664/128/index_files/a27eda31-1122-4387-adda-6581437cf0d1.jpg)



这样的一种通信方式，就是所谓的“异步”通信方式，对于系统A来说，只需要把消息发送给MQ，然后系统B就会异步的去进行处理了，系统A不需要“同步”的等待系统B处理完成。这样的好处是什么呢？两个字：解耦



### **(2)**  **应用系统之间解耦合**

发送者和接受者不必了解对方，只需要确认消息。

发送者和接受者不必同时在线。

 

### **(3)**  **整体架构**

![img](file:///D:/Documents/My Knowledge/temp/7b47fa5e-37c3-49e5-b14e-ebec57396664/128/index_files/0.1679804641666783.png)

 

### **(4)**  **MQ**的缺点

两个系统之间不能同步调用，不能实时回复，不能响应某个调用的回复。

 

##  1.6 怎么玩

最重要的功能：实现高可用，高性能，可伸缩，易用和安全的企业级面向消息服务的系统。

异步消息的消费和处理。

控制消息的消费顺序。

可以和Spring或者SpringBoot整合简化代码。

配置集群容错的MQ集群。



# 二、ActiveMQ安装和控制台

为知笔记地址：[CentOS7安装ActiveMQ](wiz://open_document?guid=77da5ff1-2fab-4ddf-8005-54f576cff62c&kbguid=&private_kbguid=9e15e816-792d-4528-9d21-a849cc4117d5)

GitHub地址：[https://github.com/wangliu1102/StudyNotes/tree/master/%E5%B0%9A%E7%A1%85%E8%B0%B7Java/%E5%9B%9B%E3%80%81JavaEE%E9%AB%98%E7%BA%A7/11%E3%80%81ActiveMQ/%E5%AE%89%E8%A3%85](https://github.com/wangliu1102/StudyNotes/tree/master/尚硅谷Java/四、JavaEE高级/11、ActiveMQ/安装)

 

# 三、Java编码实现ActiveMQ通讯

## 3.1  pom.xml导入依赖

新建Maven工程，项目名称为activemq_demo

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>

    <groupId>com.atguigu.activemq</groupId>
    <artifactId>activemq_demo</artifactId>
    <version>1.0-SNAPSHOT</version>

    <properties>
        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
        <maven.compiler.source>1.8</maven.compiler.source>
        <maven.compiler.target>1.8</maven.compiler.target>
    </properties>

    <dependencies>

        <!--  activemq  所需要的jar 包-->
        <dependency>
            <groupId>org.apache.activemq</groupId>
            <artifactId>activemq-all</artifactId>
            <version>5.15.9</version>
        </dependency>

        <!--  activemq 和 spring 整合的基础包 -->
        <dependency>
            <groupId>org.apache.xbean</groupId>
            <artifactId>xbean-spring</artifactId>
            <version>3.16</version>
        </dependency>
    </dependencies>

</project>

```



## 3.2  JMS编码总体规范

![img](file:///D:/Documents/My Knowledge/temp/7b47fa5e-37c3-49e5-b14e-ebec57396664/128/index_files/0.4985417940470529.png)

![img](file:///D:/Documents/My Knowledge/temp/7b47fa5e-37c3-49e5-b14e-ebec57396664/128/index_files/1_3.png)

 

## 3.3  Destination简介

Destination是目的地。下面拿jvm和mq，做个对比。目的地，我们可以理解为是数据存储的地方。

![img](file:///D:/Documents/My Knowledge/temp/7b47fa5e-37c3-49e5-b14e-ebec57396664/128/index_files/1_4.png)

Destination分为两种：队列和主题。下图介绍：

![img](file:///D:/Documents/My Knowledge/temp/7b47fa5e-37c3-49e5-b14e-ebec57396664/128/index_files/1_5.png)

### （1）队列(queue)

在点对点的消息传递域中,目的地被称为队列(queue)。

点对点消息传递域的特点如下：

- 每个消息只能有一个消费者，类似于1对1的关系。好比个人快递自己领自己的。
- 消息的生产者和消费者之间没有时间上的相关性。无论消费者在生产者发送消息的时候是否处于运行状态，消费者都可以提取消息。好比我们的发送短信，发送者发送后不见得接收者会即收即看。
- 消息被消费后队列中不会再存储，所以消费者不会消费到已经被消费掉的消息。

### （2）主题(topic)

 在发布订阅消息传递域中,目的地被称为主题(topic)。

发布/订阅消息传递域的特点如下：

- 生产者将消息发布到topic中，每个消息可以有多个消费者，属于1：N的关系；
- 生产者和消费者之间有时间上的相关性。订阅某一个主题的消费者只能消费自它订阅之后发布的消息。
- 生产者生产时，topic不保存消息它是无状态的不落地，假如无人订阅就去生产，那就是一条废消息，所以，一般先启动消费者再启动生产者。



JMS规范允许客户创建持久订阅，这在一定程度上放松了时间上的相关性要求。持久订阅允许消费者消费它在未处于激活状态时发送的消息。一句话，好比我们的微信公众号订阅

 

## 3.4  队列消息生产者的入门案例

```java
package com.atguigu.activemq.queue;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * 消息生产者
 *
 * @author 王柳
 * @date 2020/7/30 13:50
 */
public class JmsProduce {
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

```



## 3.5  ActiveMQ控制台之队列

运行上面代码，控制台显示如下：

![img](file:///D:/Documents/My Knowledge/temp/7b47fa5e-37c3-49e5-b14e-ebec57396664/128/index_files/1_6.png)

![img](file:///D:/Documents/My Knowledge/temp/7b47fa5e-37c3-49e5-b14e-ebec57396664/128/index_files/9a63cbbc-d601-472e-bc05-bd666bcae26f.jpg)

总结：

当有一个消息进入这个队列时，等待消费的消息是1，进入队列的消息是1。

当消息消费后，等待消费的消息是0，进入队列的消息是1，出队列的消息是1。

当再来一条消息时，等待消费的消息是1，进入队列的消息就是2。

 

## 3.6  队列-阻塞式消费者入门案例

```java
package com.atguigu.activemq.queue;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * 简单消息消费者-阻塞式消费者
 *
 * @author 王柳
 * @date 2020/7/30 14:14
 */
public class JmsConsumer {
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

        //5.创建消息的消费者,指定消费哪一个队列里面的消息
        MessageConsumer messageConsumer = session.createConsumer(queue);

        //循环获取
        while (true) {
            //6.通过消费者调用方法获取队列里面的消息(发送的消息是什么类型,接收的时候就强转成什么类型)
            // receive() 一直阻塞，消费者一直存在
            // receive(long var1) receive(4000L)4秒过后消费者走人，消费者消失
            TextMessage textMessage = (TextMessage) messageConsumer.receive();
            if (textMessage != null) {
                System.out.println("****消费者接收到的消息:  " + textMessage.getText());
            } else {
                break;
            }
        }

        //7.关闭资源
        messageConsumer.close();
        session.close();
        connection.close();
    }
}

```

控制台显示：

![img](file:///D:/Documents/My Knowledge/temp/7b47fa5e-37c3-49e5-b14e-ebec57396664/128/index_files/0.5949866600808019.png)

 

 

## 3.7  队列-异步监听式消费者入门案例

```java
package com.atguigu.activemq.queue;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;
import java.io.IOException;

/**
 * 监听模式下的消费者-异步监听式消费者
 *
 * @author 王柳
 * @date 2020/7/30 14:14
 */
public class JmsConsumer2 {
    private static final String ACTIVEMQ_URL = "tcp://192.168.1.235:61616";
    private static final String QUEUE_NAME = "queue01";

    public static void main(String[] args) throws JMSException, IOException {
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

        //5.创建消息的消费者,指定消费哪一个队列里面的消息
        MessageConsumer messageConsumer = session.createConsumer(queue);

        //6.通过监听的方式消费消息
        /*
        异步非阻塞式方式监听器(onMessage)
        订阅者或消费者通过创建的消费者对象,给消费者注册消息监听器setMessageListener,
        当消息有消息的时候,系统会自动调用MessageListener类的onMessage方法
        我们只需要在onMessage方法内判断消息类型即可获取消息
         */
        messageConsumer.setMessageListener(new MessageListener() {
            @Override
            public void onMessage(Message message) {
                if (message != null && message instanceof TextMessage) {
                    //7.把message转换成消息发送前的类型并获取消息内容
                    TextMessage textMessage = (TextMessage) message;
                    try {
                        System.out.println("****消费者接收到的消息:  " + textMessage.getText());
                    } catch (JMSException e) {
                        e.printStackTrace();
                    }
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

```



## 3.8  队列消息（Queue）总结

### **(1)** **两种消费方式**

同步阻塞方式(receive)

订阅者或接收者抵用MessageConsumer的receive()方法来接收消息，receive方法在能接收到消息之前（或超时之前）将一直阻塞。

 

异步非阻塞方式（监听器onMessage()）

订阅者或接收者通过MessageConsumer的setMessageListener(MessageListener listener)注册一个消息监听器，当消息到达之后，系统会自动调用监听器MessageListener的onMessage(Message message)方法。

 

 

### **(2)** **队列的特点**

![img](file:///D:/Documents/My Knowledge/temp/7b47fa5e-37c3-49e5-b14e-ebec57396664/128/index_files/1_8.png)

 

 

### **(3)** **消息消费情况**

![img](file:///D:/Documents/My Knowledge/temp/7b47fa5e-37c3-49e5-b14e-ebec57396664/128/index_files/0.3635395128284823.png)

情况1：只启动消费者1。

结果：消费者1会消费所有的数据。

情况2：先启动消费者1，再启动消费者2。

结果：消费者1消费所有的数据。消费者2不会消费到消息。

情况3：生产者发布6条消息，在此之前已经启动了消费者1和消费者2。

结果：消费者1和消费者2平摊了消息。各自消费3条消息。

疑问：怎么去将消费者1和消费者2不平均分摊呢？而是按照各自的消费能力去消费。我觉得，现在activemq就是这样的机制。

 

 

## 3.9  Topic介绍、入门案例、控制台

### **(1)** **topic**介绍

在发布订阅消息传递域中，目的地被称为主题（topic）

发布/订阅消息传递域的特点如下：

（1）生产者将消息发布到topic中，每个消息可以有多个消费者，属于1：N的关系；

（2）生产者和消费者之间有时间上的相关性。订阅某一个主题的消费者只能消费自它订阅之后发布的消息。

（3）生产者生产时，topic不保存消息它是无状态的不落地，假如无人订阅就去生产，那就是一条废消息，所以，一般先启动消费者再启动生产者。

 

默认情况下如上所述，但是JMS规范允许客户创建持久订阅，这在一定程度上放松了时间上的相关性要求。持久订阅允许消费者消费它在未处于激活状态时发送的消息。一句话，好比我们的微信公众号订阅

![img](file:///D:/Documents/My Knowledge/temp/7b47fa5e-37c3-49e5-b14e-ebec57396664/128/index_files/0.612959450458028.png)

 **先启动订阅消费者再启动生产者,不然发送的消息是废消息。**

 

### (2) 生产者入门案例

```java
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

```



### (3) 消费者入门案例

```java
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

```

存在多个消费者，每个消费者都能收到，自从自己启动后所有生产的消息。

 

### **(4)** **ActiveMQ**控制台

topic有多个消费者时，消费消息的数量 ≈ 在线消费者数量*生产消息的数量。

下图展示了：我们先启动了3个消费者，再启动一个生产者，并生产了3条消息。

![img](file:///D:/Documents/My Knowledge/temp/7b47fa5e-37c3-49e5-b14e-ebec57396664/128/index_files/1_9.png)

 

 

## 3.10  tpoic和queue对比

![img](file:///D:/Documents/My Knowledge/temp/7b47fa5e-37c3-49e5-b14e-ebec57396664/128/index_files/9a65d68d-0f99-467c-80c3-6f50bc844e0f.jpg)

 

# 四、JMS规范

## 4.1  JMS是什么

### （1）什么是Java消息服务

Java消息服务指的是两个应用程序之间进行异步通信的API，它为标准协议和消息服务提供了一组通用接口，包括创建、发送、读取消息等，用于支持Java应用程序开发。在JavaEE中，当两个应用程序使用JMS进行通信时，它们之间不是直接相连的，而是通过一个共同的消息收发服务组件关联起来以达到解耦/异步削峰的效果。

![img](file:///D:/Documents/My Knowledge/temp/7b47fa5e-37c3-49e5-b14e-ebec57396664/128/index_files/4c0063c0-fe8e-4904-806d-88bd21eb35eb.jpg)

### （2）JMS组成的四大元素

![img](file:///D:/Documents/My Knowledge/temp/7b47fa5e-37c3-49e5-b14e-ebec57396664/128/index_files/1_10.png)

## 4.2  消息头

JMS的消息头有哪些属性：

- JMSDestination：消息目的地，主要是指Queue和Topic
- JMSDeliveryMode：消息持久化模式
- JMSExpiration：消息过期时间
- JMSPriority：消息的优先级
- JMSMessageID：消息的唯一标识符。后面我们会介绍如何解决幂等性。



说明： 消息的生产者可以set这些属性，消息的消费者可以get这些属性。

这些属性在send方法里面也可以设置。

```java
package com.atguigu.activemq.messageheader;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * 订阅主题的生产者-添加消息头
 *
 * @author 王柳
 * @date 2020/7/30 16:07
 */
public class MessageHeader_JmsProducer_Topic {
    public static final String ACTIVEMQ_URL = "tcp://192.168.1.235:61626";
    public static final String TOPIC_NAME = "topic01";

    public static void main(String[] args) throws Exception {
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

        for (int i = 1; i < 4; i++) {

            //7.通过session创建消息
            TextMessage textMessage = session.createTextMessage("topic_name--" + i);

            // 这里可以指定每个消息的目的地
            textMessage.setJMSDestination(topic);

              /*
            持久模式和非持久模式。
            一条持久性的消息：应该被传送“一次仅仅一次”，这就意味着如果JMS提供者出现故障，该消息并不会丢失，它会在服务器恢复之后再次传递。
            一条非持久的消息：最多会传递一次，这意味着服务器出现故障，该消息将会永远丢失。
             */
            textMessage.setJMSDeliveryMode(0);

            /*
            可以设置消息在一定时间后过期，默认是永不过期。
            消息过期时间，等于Destination的send方法中的timeToLive值加上发送时刻的GMT时间值。
            如果timeToLive值等于0，则JMSExpiration被设为0，表示该消息永不过期。
            如果发送后，在消息过期时间之后还没有被发送到目的地，则该消息被清除。
             */
            textMessage.setJMSExpiration(1000L);

            /*
            消息优先级，从0-9十个级别，0-4是普通消息5-9是加急消息。
            JMS不要求MQ严格按照这十个优先级发送消息但必须保证加急消息要先于普通消息到达。默认是4级。
             */
            textMessage.setJMSPriority(10);

            // 唯一标识每个消息的标识。MQ会给我们默认生成一个，我们也可以自己指定。
            textMessage.setJMSMessageID("ABCD");

            // 上面有些属性在send方法里也能设置
            //8.使用指定好目的地的消息生产者发送消息
            messageProducer.send(textMessage);

        }

        //9.关闭资源
        messageProducer.close();
        session.close();
        connection.close();

        System.out.println("  **** 带消息头的TOPIC_NAME消息发送到MQ完成 ****");

    }
}
```



## 4.3  消息体

![img](file:///D:/Documents/My Knowledge/temp/7b47fa5e-37c3-49e5-b14e-ebec57396664/128/index_files/ddad0842-e8ea-4d37-b9ac-a99663bfac9e.jpg)

5种消息体格式：

![img](file:///D:/Documents/My Knowledge/temp/7b47fa5e-37c3-49e5-b14e-ebec57396664/128/index_files/f4532bfb-7bfa-4a6e-9f8b-6f8770cf6304.jpg)

```java
package com.atguigu.activemq.messagebody;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * 订阅主题的生产者-设置消息体
 * 下面我们演示TextMessage和MapMessage的用法
 *
 * @author 王柳
 * @date 2020/7/30 14:50
 */
public class MessageBody_JmsProducer_Topic {
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
            //8.使用指定好目的地的消息生产者发送消息

            // 发送TextMessage消息体
            TextMessage textMessage = session.createTextMessage("TOPIC_NAME---" + i);
            messageProducer.send(textMessage);

            // 发送MapMessage  消息体。set方法: 添加，get方式：获取
            MapMessage mapMessage = session.createMapMessage();
            mapMessage.setString("name", "张三" + i);
            mapMessage.setInt("age", 18 + i);
            messageProducer.send(mapMessage);
        }

        //9.关闭资源
        messageProducer.close();
        session.close();
        connection.close();

        System.out.println("****TOPIC_NAME消息发布到MQ完成");
    }
}

```

```java
package com.atguigu.activemq.messagebody;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;
import java.io.IOException;

/**
 * 订阅主题的消费者-设置消息体
 *
 * @author 王柳
 * @date 2020/7/30 14:50
 */
public class MessageBody_JmsConsumer_Topic {
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
            // 判断消息是哪种类型之后，再强转。
            if (message != null && message instanceof TextMessage) {
                //7.把message转换成消息发送前的类型并获取消息内容
                TextMessage textMessage = (TextMessage) message;
                try {
                    System.out.println("****消费者text的消息：" + textMessage.getText());
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            }
            if (null != message && message instanceof MapMessage) {
                MapMessage mapMessage = (MapMessage) message;
                try {
                    System.out.println("****消费者的map消息：" + mapMessage.getString("name"));
                    System.out.println("****消费者的map消息：" + mapMessage.getInt("age"));
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

```



## 4.4  消息属性

如果需要除消息头字段之外的值，那么可以使用消息属性。

他是识别/去重/重点标注等操作，非常有用的方法。

他们是以属性名和属性值对的形式制定的。可以将属性设为消息头的扩展，属性指定一些消息头没有包括的附加信息，比如可以在属性里指定消息选择器。消息的属性就像可以分配给一条消息的附加消息头一样。它们允许开发者添加有关消息的不透明附加信息。它们还用于暴露消息选择器在消息过滤时使用的数据。

下图是设置消息属性的API：

![img](file:///D:/Documents/My Knowledge/temp/7b47fa5e-37c3-49e5-b14e-ebec57396664/128/index_files/1_11.png)

```java
package com.atguigu.activemq.messageattribute;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * 订阅主题的生产者-设置消费属性
 *
 * @author 王柳
 * @date 2020/7/30 14:50
 */
public class MessageAttribute_JmsProducer_Topic {
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

            // 调用Message的set*Property()方法，就能设置消息属性。根据value的数据类型的不同，有相应的API。
            textMessage.setStringProperty("From","ZhangSan@qq.com");
            textMessage.setByteProperty("Spec", (byte) 1);
            textMessage.setBooleanProperty("Invalide",true);

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

```

```java
package com.atguigu.activemq.messageattribute;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;
import java.io.IOException;

/**
 * 订阅主题的消费者-获取消费属性
 *
 * @author 王柳
 * @date 2020/7/30 14:50
 */
public class MessageAttribute_JmsConsumer_Topic {
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
                    System.out.println("消息体："+textMessage.getText());
                    System.out.println("消息属性："+textMessage.getStringProperty("From"));
                    System.out.println("消息属性："+textMessage.getByteProperty("Spec"));
                    System.out.println("消息属性："+textMessage.getBooleanProperty("Invalide"));
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

```



## 4.5  消息的持久化(Persistent)

### （1）什么是持久化消息

保证消息只被传送一次和成功使用一次。在持久性消息传送至目标时，消息服务将其放入持久性数据存储。如果消息服务由于某种原因导致失败，它可以恢复此消息并将此消息传送至相应的消费者。虽然这样增加了消息传送的开销，但却增加了可靠性。

我的理解：在消息生产者将消息成功发送给MQ消息中间件之后。无论是出现任何问题，如：MQ服务器宕机、消费者掉线等。都保证（topic要之前注册过，queue不用）消息消费者，能够成功消费消息。如果消息生产者发送消息失败了，那么消费者也不会消费到该消息。

 

### （2）queue消息非持久和持久

queue非持久，当服务器宕机，消息不存在（消息丢失了）。即便是非持久，消费者在不在线的话，消息也不会丢失，等待消费者在线，还是能够收到消息的。

queue持久化，当服务器宕机，消息依然存在。**queue****消息默认是持久化的**。

持久化消息，这是队列的默认传递模式，此模式保证这些消息只被传送一次和成功使用一次。对于这些消息，可靠性是优先考虑的因素。

可靠性的另一个重要方面是确保持久性消息传送至目标后，消息服务在向消费者传送它们之前不会丢失这些消息。

![img](file:///D:/Documents/My Knowledge/temp/7b47fa5e-37c3-49e5-b14e-ebec57396664/128/index_files/1_12.png)

```java
package com.atguigu.activemq.persistent;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * queue消息生产者-持久化
 * 运行结果证明：当生产者成功发布消息之后，MQ服务端宕机重启，消息消费者仍然能够收到该消息
 *
 * @author 王柳
 * @date 2020/7/30 13:50
 */
public class Persistent_JmsProduce {
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

        // 持久化
        messageProducer.setDeliveryMode(DeliveryMode.PERSISTENT);

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

```

```java
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

```



### （3）topic消息持久化

**topic****默认就是非持久化的**，因为生产者生产消息时，消费者也要在线，这样消费者才能消费到消息。

topic消息持久化，只要消费者向MQ服务器注册过，所有生产者发布成功的消息，该消费者都能收到，不管是MQ服务器宕机还是消费者不在线。

 

注意：

\1.  一定要先运行一次消费者，等于向MQ注册，类似我订阅了这个主题。

\2.  然后再运行生产者发送消息。

\3.  之后无论消费者是否在线，都会收到消息。如果不在线的话，下次连接的时候，会把没有收过的消息都接收过来。



生产者主要设置变动如下：

```java
        // 设置持久化topic
        messageProducer.setDeliveryMode(DeliveryMode.PERSISTENT);

        // 设置持久化topic之后再，启动连接
        connection.start();
```

```java
package com.atguigu.activemq.persistent;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * 订阅主题的生产者-持久化
 *
 * @author 王柳
 * @date 2020/7/30 14:50
 */
public class Persistent_JmsProducer_Topic {
    public static final String ACTIVEMQ_URL = "tcp://192.168.1.235:61616";
    public static final String TOPIC_NAME = "topic01";


    public static void main(String[] args) throws JMSException {
        //1.创建连接工厂，按照给定的URL，采用默认的用户名密码
        ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory(ACTIVEMQ_URL);

        //2.通过连接工厂,获得connection
        Connection connection = activeMQConnectionFactory.createConnection();

        //3.创建会话session
        //两个参数transacted=事务,acknowledgeMode=确认模式(签收)
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

        //4.创建目的地(具体是队列queue还是主题topic)
        Topic topic = session.createTopic(TOPIC_NAME);

        //5.创建消息的生产者
        MessageProducer messageProducer = session.createProducer(topic);

        // 设置持久化topic
        messageProducer.setDeliveryMode(DeliveryMode.PERSISTENT);
        // 设置持久化topic之后再，启动连接
        connection.start();

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

```

消费者主要设置变动如下：

```java
        // 设置客户端ID。向MQ服务器注册自己的名称
        connection.setClientID("marrry");
        。。。
        
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
```

```java
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

```

控制台介绍：

topic页面还是和之前的一样。另外在subscribers页面也会显示。如下：

![img](file:///D:/Documents/My Knowledge/temp/7b47fa5e-37c3-49e5-b14e-ebec57396664/128/index_files/1_14.png)

 

 

## 4.6  消息的事务性(Transaction)

![img](file:///D:/Documents/My Knowledge/temp/7b47fa5e-37c3-49e5-b14e-ebec57396664/128/index_files/1_13.png)

(1) 生产者开启事务后，执行commit方法，这批消息才真正的被提交。不执行commit方法，这批消息不会提交。执行rollback方法，之前的消息会回滚掉。生产者的事务机制，要高于签收机制，当生产者开启事务，签收机制不再重要。

 

(2) 消费者开启事务后，执行commit方法，这批消息才算真正的被消费。不执行commit方法，这些消息不会标记已消费，下次还会被消费。执行rollback方法，是不能回滚之前执行过的业务逻辑，但是能够回滚之前的消息，回滚后的消息，下次还会被消费。消费者利用commit和rollback方法，甚至能够违反一个消费者只能消费一次消息的原理。

 

(3) 问：消费者和生产者需要同时操作事务才行吗？  

答：消费者和生产者的事务，完全没有关联，各自是各自的事务。

```java
package com.atguigu.activemq.transaction;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * 生产者-事务
 *
 * @author 王柳
 * @date 2020/7/31 9:12
 */
public class Jms_TX_Producer {
    private static final String ACTIVEMQ_URL = "tcp://192.168.1.235:61616";
    private static final String ACTIVEMQ_QUEUE_NAME = "Queue-TX";

    public static void main(String[] args) throws JMSException {
        ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory(ACTIVEMQ_URL);
        Connection connection = activeMQConnectionFactory.createConnection();
        connection.start();
        //1.创建会话session，两个参数transacted=事务,acknowledgeMode=确认模式(签收)
        //设置为开启事务
        Session session = connection.createSession(true, Session.AUTO_ACKNOWLEDGE);
        Queue queue = session.createQueue(ACTIVEMQ_QUEUE_NAME);
        MessageProducer producer = session.createProducer(queue);
        try {
            for (int i = 0; i < 3; i++) {
                TextMessage textMessage = session.createTextMessage("tx msg--" + i);
                producer.send(textMessage);
                if (i == 2) {
                    // throw new RuntimeException("GG.....");
                }
            }
            // 2. 开启事务后，使用commit提交事务，这样这批消息才能真正的被提交。
            session.commit();
            System.out.println("消息发送完成");
        } catch (Exception e) {
            System.out.println("出现异常,消息回滚");
            // 3. 工作中一般，当代码出错，我们在catch代码块中回滚。这样这批发送的消息就能回滚。
            session.rollback();
        } finally {
            //4. 关闭资源
            producer.close();
            session.close();
            connection.close();
        }
    }
}

```

```java
package com.atguigu.activemq.transaction;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;
import java.io.IOException;

/**
 * 消费者-事务
 *
 * @author 王柳
 * @date 2020/7/31 9:13
 */
public class Jms_TX_Consumer {
    private static final String ACTIVEMQ_URL = "tcp://192.168.1.235:61626";
    private static final String ACTIVEMQ_QUEUE_NAME = "Queue-TX";

    public static void main(String[] args) throws JMSException, IOException {
        ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory(ACTIVEMQ_URL);
        Connection connection = activeMQConnectionFactory.createConnection();
        connection.start();
        // 创建会话session，两个参数transacted=事务,acknowledgeMode=确认模式(签收)
        // 消费者开启了事务就必须手动提交，不然会重复消费消息
        final Session session = connection.createSession(true, Session.AUTO_ACKNOWLEDGE);
        Queue queue = session.createQueue(ACTIVEMQ_QUEUE_NAME);
        MessageConsumer messageConsumer = session.createConsumer(queue);
        messageConsumer.setMessageListener(new MessageListener() {
            int a = 0;
            @Override
            public void onMessage(Message message) {
                if (message instanceof TextMessage) {
                    try {
                        TextMessage textMessage = (TextMessage) message;
                        System.out.println("***消费者接收到的消息:   " + textMessage.getText());
                        if (a == 0) {
                            System.out.println("commit");
                            session.commit();
                        }
                        if (a == 2) {
                            System.out.println("rollback");
                            session.rollback();
                        }
                        a++;
                    } catch (Exception e) {
                        System.out.println("出现异常，消费失败，放弃消费");
                        try {
                            session.rollback();
                        } catch (JMSException ex) {
                            ex.printStackTrace();
                        }
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
```

消费者的控制台输出信息。可以看出commit和rollback方法的作用。

 ![img](file:///D:/Documents/My Knowledge/temp/7b47fa5e-37c3-49e5-b14e-ebec57396664/128/index_files/e53a1cac-98d9-473f-a55e-6b95710d4ec6.png)



![img](file:///D:/Documents/My Knowledge/temp/7b47fa5e-37c3-49e5-b14e-ebec57396664/128/index_files/1f5598f8-2792-4ebf-a6e8-1c5e75a50adf.png)





## 4.7  消息的签收机制

### **（1）签收的几种方式**

①　**自动签收（Session.AUTO_ACKNOWLEDGE****）：该方式是默认的**。该种方式，无需我们程序做任何操作，框架会帮我们自动签收收到的消息。

②　手动签收（Session.CLIENT_ACKNOWLEDGE）：手动签收。该种方式，需要我们手动调用Message.acknowledge()，来签收消息。如果不签收消息，该消息会被我们反复消费，直到被签收。

③　允许重复消息（Session.DUPS_OK_ACKNOWLEDGE）：多线程或多个消费者同时消费到一个消息，因为线程不安全，可能会重复消费。该种方式很少使用到。

④　事务下的签收（Session.SESSION_TRANSACTED）：开始事务的情况下，可以使用该方式。该种方式很少使用到。

 

### **（2）事务和签收的关系**

①　在事务性会话中，当一个事务被成功提交则消息被自动签收。如果事务回滚，则消息会被再次传送。事务优先于签收，开始事务后，签收机制不再起任何作用。

②　非事务性会话中，消息何时被确认取决于创建会话时的应答模式。

③　生产者事务开启，只有commit后才能将全部消息变为已消费。

④　事务偏向生产者，签收偏向消费者。也就是说，生产者使用事务更好点，消费者使用签收机制更好点。

 

### **（3）代码演示**

####   **消息非事务模式下消费者手动签收**

```java
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

```

```java
package com.atguigu.activemq.acknowledge;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;
import java.io.IOException;

/**
 * 非事务下的消费者-如何手动签收
 *
 * @author 王柳
 * @date 2020/7/31 9:38
 */
public class NoTransaction_ClientAck_Jms_Consumer {
    private static final String ACTIVEMQ_URL = "tcp://192.168.1.235:61616";
    private static final String ACTIVEMQ_QUEUE_NAME = "Queue-ACK-NoTransaction";

    public static void main(String[] args) throws JMSException, IOException {
        ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory(ACTIVEMQ_URL);
        Connection connection = activeMQConnectionFactory.createConnection();
        connection.start();
        // 设置为非事务，手动签收模式
        Session session = connection.createSession(false, Session.CLIENT_ACKNOWLEDGE);
        Queue queue = session.createQueue(ACTIVEMQ_QUEUE_NAME);
        MessageConsumer messageConsumer = session.createConsumer(queue);
        messageConsumer.setMessageListener(new MessageListener() {
            @Override
            public void onMessage(Message message) {
                if (message instanceof TextMessage) {
                    try {
                        TextMessage textMessage = (TextMessage) message;
                        System.out.println("***消费者接收到的消息:   " + textMessage.getText());
                        /* 设置为Session.CLIENT_ACKNOWLEDGE后，要调用该方法，标志着该消息已被签收（消费）。
                            如果不调用该方法，该消息的标志还是未消费，下次启动消费者或其他消费者还会收到改消息。
                         */
                        textMessage.acknowledge();
                    } catch (Exception e) {
                        System.out.println("出现异常，消费失败，放弃消费");
                    }
                }
            }
        });
        System.in.read();
        messageConsumer.close();
        session.close();
        connection.close();
    }

}

```

![img](file:///D:/Documents/My Knowledge/temp/7b47fa5e-37c3-49e5-b14e-ebec57396664/128/index_files/061c8b86-472c-4715-a8d0-214dc9e3644f.png)

####   **消息事务模式下消费者手动签收**

```java
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

```

```java
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
//                        session.commit();
                        System.out.println(textMessage.getText());
                    } catch (JMSException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }
}

```

由于消费者开启了事务,没有提交事务(就算手动签收也没用),服务器认为,消费者没有收到消息



![img](file:///D:/Documents/My Knowledge/temp/7b47fa5e-37c3-49e5-b14e-ebec57396664/128/index_files/34ba178d-f891-4d10-8de3-ae2ba3d7e68a.png)





## 4.8  JMS的点对点总结

点对点模型是基于队列的，生产者发消息到队列，消费者从队列接收消息，队列的存在使得消息的异步传输成为可能。和我们平时给朋友发送短信类似。

- 如果在Session关闭时有部分消息己被收到但还没有被签收(acknowledged),那当消费者下次连接到相同的队列时，这些消息还会被再次接收。
- 队列可以长久地保存消息直到消费者收到消息。消费者不需要因为担心消息会丢失而时刻和队列保持激活的连接状态，充分体现了异步传输模式的优势。



 

## 4.9  JMS的发布订阅总结

### (1) JMS的发布订阅总结

JMS Pub/Sub 模型定义了如何向一个内容节点发布和订阅消息，这些节点被称作topic。

主题可以被认为是消息的传输中介，发布者（publisher）发布消息到主题，订阅者（subscribe）从主题订阅消息。

主题使得消息订阅者和消息发布者保持互相独立不需要解除即可保证消息的传送。

 

### (2) 非持久订阅

非持久订阅只有当客户端处于激活状态，也就是和MQ保持连接状态才能收发到某个主题的消息。

如果消费者处于离线状态，生产者发送的主题消息将会丢失作废，消费者永远不会收到。

  一句话：先订阅注册才能接受到发布，只给订阅者发布消息。

 

### (3) 持久订阅

客户端首先向MQ注册一个自己的身份ID识别号，当这个客户端处于离线时，生产者会为这个ID保存所有发送到主题的消息，当客户再次连接到MQ的时候，会根据消费者的ID得到所有当自己处于离线时发送到主题的消息。

非持久订阅状态下，不能恢复或重新派送一个未签收的消息。

持久订阅才能恢复或重新派送一个未签收的消息。

 

### (4) 非持久和持久化订阅如何选择

当所有的消息必须被接收，则用持久化订阅。当消息丢失能够被容忍，则用非持久订阅。

 

# 五、ActiveMQ的broker

## **5.1 broker**是什么

相当于一个ActiveMQ服务器实例。

说白了，Broker其实就是实现了用代码的形式启动ActiveMQ将MQ嵌入到Java代码中，以便随时用随时启动，在用的时候再去启动这样能节省了资源，也保证了可用性。这种方式，我们实际开发中很少采用，因为他缺少太多了东西，如：日志，数据存储等等。

 

## **5.2 启动**broker时指定配置文件

启动broker时指定配置文件，可以帮助我们在一台服务器上启动多个broker。实际工作中一般一台服务器只启动一个broker。

![img](file:///D:/Documents/My Knowledge/temp/7b47fa5e-37c3-49e5-b14e-ebec57396664/128/index_files/1_16.png)

```shell
./activemq start xbean:file:/home/activemq/apache-activemq-5.16.0/config/activemq02.xml
```



## **5.3 嵌入式的**broker启动

用ActiveMQ Broker作为独立的消息服务器来构建Java应用。

ActiveMQ也支持在vm中通信基于嵌入的broker，能够无缝的集成其他java应用。

### （1） pom.xml添加一个依赖

```xml
<dependency>
    <groupId>com.fasterxml.jackson.core</groupId>
    <artifactId>jackson-databind</artifactId>
    <version>2.10.1</version>
</dependency>

```



### （2）嵌入式broke的启动类

```java
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

```



### （3）队列验证

和Linux上的ActiveMQ是一样的,Broker相当于一个Mini版本的ActiveMQ。

![img](file:///D:/Documents/My Knowledge/temp/7b47fa5e-37c3-49e5-b14e-ebec57396664/128/index_files/4a93f8c1-4fcb-4f16-ba59-3e702d18fbd6.png)



# 六、Spring整合ActiveMQ

我个人的理解：我们之前介绍的内容也很重要，他更灵活，他支持各种自定义功能，可以满足我们工作中复杂的需求。很多activemq的功能，我们要看官方文档或者博客，这些功能大多是在上面代码的基础上修改完善的。如果非要把这些功能强行整合到spring，就有些缘木求鱼了。我认为另一种方式整合spring更好，就是将上面的类注入到Spring中，其他不变。这样既能保持原生的代码，又能集成到spring。

下面我们讲的Spring和SpringBoot整合ActiveMQ也重要，他给我们提供了一个模板，简化了代码，减少我们工作中遇到坑，能够满足开发中90%以上的功能。

 

## 6.1  pom.xml添加依赖

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>

    <groupId>com.atguigu.activemq</groupId>
    <artifactId>activemq_demo</artifactId>
    <version>1.0-SNAPSHOT</version>

    <properties>
        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
        <maven.compiler.source>1.8</maven.compiler.source>
        <maven.compiler.target>1.8</maven.compiler.target>
    </properties>

    <dependencies>
        <!--  activemq  所需要的jar 包-->
        <dependency>
            <groupId>org.apache.activemq</groupId>
            <artifactId>activemq-all</artifactId>
            <version>5.15.9</version>
        </dependency>
        <!--  activemq 和 spring 整合的基础包 -->
        <dependency>
            <groupId>org.apache.xbean</groupId>
            <artifactId>xbean-spring</artifactId>
            <version>3.16</version>
        </dependency>
        <!--   嵌入式的broker启动所需要的依赖包      -->
        <dependency>
            <groupId>com.fasterxml.jackson.core</groupId>
            <artifactId>jackson-databind</artifactId>
            <version>2.10.1</version>
        </dependency>

        <!-- activemq连接池 -->
        <dependency>
            <groupId>org.apache.activemq</groupId>
            <artifactId>activemq-pool</artifactId>
            <version>5.15.9</version>
        </dependency>
        <!-- spring支持jms的包 -->
        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-jms</artifactId>
            <version>4.3.23.RELEASE</version>
        </dependency>

        <!-- Spring核心依赖 -->
        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-core</artifactId>
            <version>4.3.23.RELEASE</version>
        </dependency>
        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-context</artifactId>
            <version>4.3.23.RELEASE</version>
        </dependency>

    </dependencies>

</project>

```



## 6.2  Spring的ActiveMQ配置文件

src/main/resources/spring-activemq.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:context="http://www.springframework.org/schema/context"
       xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd http://www.springframework.org/schema/context https://www.springframework.org/schema/context/spring-context.xsd">

    <!--  开启包的自动扫描  -->
    <context:component-scan base-package="com.atguigu.activemq"/>
    <!--  配置生产者  -->
    <bean id="connectionFactory" class="org.apache.activemq.pool.PooledConnectionFactory" destroy-method="stop">
        <property name="connectionFactory">
            <!--      真正可以生产Connection的ConnectionFactory,由对应的JMS服务商提供      -->
            <bean class="org.apache.activemq.spring.ActiveMQConnectionFactory">
                <property name="brokerURL" value="tcp://192.168.1.235:61616"/>
            </bean>
        </property>
        <property name="maxConnections" value="100"/>
    </bean>

    <!--  这个是队列目的地,点对点的Queue  -->
    <bean id="destinationQueue" class="org.apache.activemq.command.ActiveMQQueue">
        <!--    通过构造注入Queue名    -->
        <constructor-arg index="0" value="spring-active-queue"/>
    </bean>

    <!--  这个是队列目的地,  发布订阅的主题Topic-->
    <bean id="destinationTopic" class="org.apache.activemq.command.ActiveMQTopic">
        <constructor-arg index="0" value="spring-active-topic"/>
    </bean>

    <!--  Spring提供的JMS工具类,他可以进行消息发送,接收等  -->
    <bean id="jmsTemplate" class="org.springframework.jms.core.JmsTemplate">
        <!--    传入连接工厂    -->
        <property name="connectionFactory" ref="connectionFactory"/>
        <!--    传入目的地    -->
        <property name="defaultDestination" ref="destinationQueue"/>
        <!--    消息自动转换器    -->
        <property name="messageConverter">
            <bean class="org.springframework.jms.support.converter.SimpleMessageConverter"/>
        </property>
    </bean>
</beans>

```



## 6.3  队列生产者

```java
package com.atguigu.activemq.spring;

import org.apache.xbean.spring.context.ClassPathXmlApplicationContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Service;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

/**
 * Spring整合-队列生产者
 *
 * @author 王柳
 * @date 2020/7/31 14:01
 */
@Service
public class Spring_MQ_Produce {

    @Autowired
    private JmsTemplate jmsTemplate;

    public static void main(String[] args) {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("spring-activemq.xml");
        Spring_MQ_Produce springMQProducer = applicationContext.getBean(Spring_MQ_Produce.class);
        springMQProducer.jmsTemplate.send(
                new MessageCreator() {
                    @Override
                    public Message createMessage(Session session) throws JMSException {
                        return session.createTextMessage("***Spring和ActiveMQ的整合case111.....");
                    }
                }
        );
        System.out.println("********send task over");
    }
}

```



## 6.4  队列消费者

```java
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
```



## 6.5  主题生产者和消费者

生产者和消费者都可以通过jmsTemplate对象实时设置目的地等其他信息。

先启动消费者再启动生产者。

修改spring-activemq.xml：

```xml
    <!--  这个是队列目的地,点对点的Queue  -->
    <bean id="destinationQueue" class="org.apache.activemq.command.ActiveMQQueue">
        <!--    通过构造注入Queue名    -->
        <constructor-arg index="0" value="spring-active-queue"/>
    </bean>

    <!--  这个是队列目的地,  发布订阅的主题Topic-->
    <bean id="destinationTopic" class="org.apache.activemq.command.ActiveMQTopic">
        <constructor-arg index="0" value="spring-active-topic"/>
    </bean>

    <!--  Spring提供的JMS工具类,他可以进行消息发送,接收等  -->
    <bean id="jmsTemplate" class="org.springframework.jms.core.JmsTemplate">
        <!--    传入连接工厂    -->
        <property name="connectionFactory" ref="connectionFactory"/>
        <!--    传入目的地 ，修改为主题   -->
        <!--        <property name="defaultDestination" ref="destinationQueue"/>-->
        <property name="defaultDestination" ref="destinationTopic"/>
        <!--    消息自动转换器    -->
        <property name="messageConverter">
            <bean class="org.springframework.jms.support.converter.SimpleMessageConverter"/>
        </property>
    </bean>
```



## 6.6  配置消费者的监听类

类似于前面setMessageListenner实时间提供消息。

消费者配置了自动监听，就相当于在spring里面后台运行，有消息就运行我们实现监听类里面的方法。

修改spring-activemq.xml，添加如下：

```xml
    <!-- 配置消费者的监听类   -->
    <bean id="jmsContainer" class="org.springframework.jms.listener.DefaultMessageListenerContainer">
        <property name="connectionFactory" ref="connectionFactory"/>
        <property name="destination" ref="destinationTopic"/>
        <!--实现 MyMessageListener-->
        <property name="messageListener" ref="myMessageListener"/>
    </bean>
```

```java
package com.atguigu.activemq.spring;

import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

/**
 * 在Spring里面实现消费者不启动，直接通过配置监听完成，即只启动生产者即可监听到发送的消息
 */
@Component
public class MyMessageListener implements MessageListener {

    @Override
    public void onMessage(Message message) {
        if (null != message && message instanceof TextMessage) {
            TextMessage textMessage = (TextMessage) message;
            try {
                System.out.println(textMessage.getText());
            } catch (JMSException e) {
                e.printStackTrace();
            }
        }
    }
}

```

 ![img](file:///D:/Documents/My Knowledge/temp/7b47fa5e-37c3-49e5-b14e-ebec57396664/128/index_files/4f47aa97-f532-4a7c-8a1b-95f71a78862c.png)

 

# 七、 SpringBoot整合ActiveMQ

我个人不太赞成使用这种方式SpringBoot整合ActiveMQ，因为这样做会失去原生代码的部分功能和灵活性。但是工作中，这种做能够满足我们常见的需求，也方便和简化我们的代码，也为了适应工作中大家的习惯。

 

## 7.1  queue生产者

### **(1)**   **新建项目**

**工程名：boot_mq_producer**

**包名：com.atguigu.boot.activemq**

![img](file:///D:/Documents/My Knowledge/temp/7b47fa5e-37c3-49e5-b14e-ebec57396664/128/index_files/83090da2-0e5a-432a-8ad9-830d4271a880.png)

 

### **(2)**   **pom.xml**

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 https://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>
    <parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>2.3.2.RELEASE</version>
        <relativePath/> <!-- lookup parent from repository -->
    </parent>
    <groupId>com.atguigu.boot.activemq</groupId>
    <artifactId>boot_mq_producer</artifactId>
    <version>0.0.1-SNAPSHOT</version>
    <name>boot_mq_producer</name>
    <description>Demo project for Spring Boot</description>

    <properties>
        <java.version>1.8</java.version>
    </properties>

    <dependencies>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-activemq</artifactId>
            <version>2.1.5.RELEASE</version>
        </dependency>

        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>

        <dependency>
            <groupId>org.projectlombok</groupId>
            <artifactId>lombok</artifactId>
            <optional>true</optional>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <scope>test</scope>
            <exclusions>
                <exclusion>
                    <groupId>org.junit.vintage</groupId>
                    <artifactId>junit-vintage-engine</artifactId>
                </exclusion>
            </exclusions>
        </dependency>
    </dependencies>

    <build>
        <plugins>
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
            </plugin>
        </plugins>
    </build>

</project>

```

### **(3)**   **application.yml**

```yml
# web占用的端口
server:
  port: 7777

spring:
  activemq:
    # activemq的broker的url
    broker-url: tcp://192.168.1.235:61616
    # 连接activemq的broker所需的账号和密码
    user: admin
    password: admin
  jms:
    # 目的地是queue还是topic， false（默认） = queue    true =  topic
    pub-sub-domain: false

#  自定义队列名称。这只是个常量
myQueueName: boot-activemq-queue
```



### **(4)**   **配置目的地的**bean

配置目的地的bean和开启springboot的jms功能。

```java
package com.atguigu.boot.activemq.config;

import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.stereotype.Component;

@Component    // 让spring 管理的注解，相当于spring 中在xml 中写了个bean
@EnableJms     // 开启jms 适配
public class ConfigBean {

    @Value("${myQueueName}")
    private String myQueue;    // 注入配置文件中的 myqueue

    @Bean   // bean id=""  class="…"
    public ActiveMQQueue queue() {
        return new ActiveMQQueue(myQueue);
    }

}

```



### **(5)**   **队列生产者代码**

```java
package com.atguigu.boot.activemq.produce;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.jms.Queue;
import java.util.UUID;

@Component
public class Queue_Produce {

    // JMS模板
    @Autowired
    private JmsMessagingTemplate jmsMessagingTemplate;

    // 这个是我们配置的队列目的地
    @Autowired
    private Queue queue;

    // 发送消息,调用一次一个信息发出
    public void produceMessage() {
        // 一参是目的地，二参是消息的内容
        jmsMessagingTemplate.convertAndSend(queue, "****" + UUID.randomUUID().toString().substring(0, 6));
    }

    // 带定时投递的业务方法。每3秒执行一次。非必须代码，仅为演示。
    @Scheduled(fixedDelay = 3000) // 每3秒自动调用
    public void produceMessageScheduled() {
        produceMessage();
    }


}

```



### **(6)**   **主启动类（非必须，仅为演示）**

```java
package com.atguigu.boot.activemq;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

// 是否开启定时任务调度功能
@EnableScheduling
@SpringBootApplication
public class BootMqProducerApplication {

    public static void main(String[] args) {
        SpringApplication.run(BootMqProducerApplication.class, args);
    }

}

```



### **(7)**   **单元测试（非必须，仅为演示）**

```java
    @Resource    //  这个是java 的注解，而Autowried 是 spring 的
    private Queue_Produce queue_produce;

    @Test
    public void testSend() {
        queue_produce.produceMessage();
    }

```



## 7.2  queue消费者

新建项目boot_mq_consumer，设置包名com.atguigu.boot.activemq。

pom.xml和application.yml文件和前面一样，端口设为8888。

 注册一个消息监听器。项目开启后监听某个主题的消息：

```java
package com.atguigu.boot.activemq.consummer;


import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import javax.jms.TextMessage;

@Component
public class Queue_consummer {
    
    // 监听过后会随着springboot一起启动,有消息就执行加了该注解的方法
    @JmsListener(destination = "${myQueueName}")     // 注解监听
    public void receive(TextMessage textMessage) throws  Exception{
        System.out.println(" ***  消费者收到消息  ***"+textMessage.getText());
    }

}

```

![img](file:///D:/Documents/My Knowledge/temp/7b47fa5e-37c3-49e5-b14e-ebec57396664/128/index_files/e8b09f7b-4705-4afa-b702-add9c64a8324.png) ![img](file:///D:/Documents/My Knowledge/temp/7b47fa5e-37c3-49e5-b14e-ebec57396664/128/index_files/0437c5ef-2ed8-4531-94ab-33744049f8f1.png)



## 7.3  topic生产者

### **(1)**   **pom.xml**

新建项目boot_mq_topic_produce，设置包名**com.atguigu.boot.activemq**。

pom和上面一样。

### **(2)**   **application.yml**

```yml
server:
  port: 6666
spring:
  activemq:
    broker-url: tcp://192.168.1.235:61616
    user: admin
    password: admin
  jms:
    # 目的地是queue还是topic， false（默认） = queue    true =  topic
    pub-sub-domain: true

  #  自定义主题名称
myTopic: boot-activemq-topic


```

 

### **(3)**   **配置目的地的**bean和开启JMS功能

```java
package com.atguigu.boot.activemq.config;


import org.apache.activemq.command.ActiveMQTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.stereotype.Component;

import javax.jms.Topic;

@Component
@EnableJms
public class ConfigBean {

    @Value("${myTopic}")
    private String topicName;

    @Bean
    public Topic topic() {
        return new ActiveMQTopic(topicName);
    }

}

```

### **(4)**   **生产者代码**

```java
package com.atguigu.boot.activemq.produce;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.jms.Topic;
import java.util.UUID;

@Component
public class Topic_Produce {

    @Autowired
    private JmsMessagingTemplate  jmsMessagingTemplate ;

    @Autowired
    private Topic  topic ;

    @Scheduled(fixedDelay = 3000)
    public void produceTopic(){
        jmsMessagingTemplate.convertAndSend(topic,"主题消息"+ UUID.randomUUID().toString().substring(0,6));

    }
}

```



### (5) 主启动类（非必须，仅为演示）

```java
package com.atguigu.boot.activemq;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class BootMqTopicProduceApplication {

    public static void main(String[] args) {
        SpringApplication.run(BootMqTopicProduceApplication.class, args);
    }

}

```



## 7.4  topic消费者-非持久化

### **(1)**   **pom.xml**

新建项目boot_mq_topic_consumer，设置包名**com.atguigu.boot.activemq**。

pom和上面一样。

 

### **(2)**   **application.yml**

和上面一样,设置端口5566。

 

### **(3)**   **消费者代码**

注册一个消息监听器。项目开启后监听某个主题的消息：

```java
package com.atguigu.boot.activemq.consummer;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import javax.jms.TextMessage;

@Component
public class Topic_Consummer {

    @JmsListener(destination = "${myTopic}")
    public void receive(TextMessage textMessage) throws Exception {
        System.out.println("消费者受到订阅的主题：" + textMessage.getText());
    }

}

```

先启动消费者,后启动生产者

![img](file:///D:/Documents/My Knowledge/temp/7b47fa5e-37c3-49e5-b14e-ebec57396664/128/index_files/518332c7-cf45-4a49-84f5-9e9a9b41fcd4.png)



![img](file:///D:/Documents/My Knowledge/temp/7b47fa5e-37c3-49e5-b14e-ebec57396664/128/index_files/65b78225-8490-43c3-b52f-c55649f5de20.png)

## 7.5 topic消费者-持久化

### **(1)**   **pom.xml**

新建项目boot_mq_topic_consumer_persistent，设置包名**com.atguigu.boot.activemq**。

pom和上面一样。

 

### **(2)**   **application.yml**

和上面一样,设置端口5577。



### (3)   配置bean

配置文件的方式无法进行配置持久化订阅。所以需要自己去生成一个持久化订阅。

```java
package com.atguigu.boot.activemq.config;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.stereotype.Component;

import javax.jms.ConnectionFactory;

/**
 * 设置持久化订阅
 * 配置文件的方式无法进行配置持久化订阅。所以需要自己去生成一个持久化订阅
 */
@Component
@EnableJms
public class ActiveMQConfigBean {

    @Value("${spring.activemq.broker-url}")
    private String brokerUrl;

    @Value("${spring.activemq.user}")
    private String user;

    @Value("${spring.activemq.password}")
    private String password;

    public ConnectionFactory connectionFactory() {
        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory();
        connectionFactory.setBrokerURL(brokerUrl);
        connectionFactory.setUserName(user);
        connectionFactory.setPassword(password);
        return connectionFactory;
    }


    @Bean(name = "jmsListenerContainerFactory")
    public DefaultJmsListenerContainerFactory jmsListenerContainerFactory() {
        DefaultJmsListenerContainerFactory defaultJmsListenerContainerFactory = new DefaultJmsListenerContainerFactory();
        defaultJmsListenerContainerFactory.setConnectionFactory(connectionFactory());
        defaultJmsListenerContainerFactory.setSubscriptionDurable(true);
        defaultJmsListenerContainerFactory.setClientId("我是持久订阅者一号");
        return defaultJmsListenerContainerFactory;
    }
}

```



### (4)  消费者代码

```java
package com.atguigu.boot.activemq.consumer;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.TextMessage;

@Component
public class Topic_Consummer_Persistent {

    //需要在监听方法指定连接工厂
    @JmsListener(destination = "${myTopic}", containerFactory = "jmsListenerContainerFactory")
    public void consumer(TextMessage textMessage) throws JMSException {
        System.out.println("订阅者收到消息:    " + textMessage.getText());
    }
}

```

先启动消费者再启动生产者，然后消费者断掉重新再启动会发现接收到很多断掉连接时生产者发送的消息。

断掉连接：

![img](file:///D:/Documents/My Knowledge/temp/7b47fa5e-37c3-49e5-b14e-ebec57396664/128/index_files/a660a2aa-2d0e-4dd5-ada4-4b1084f5ebe9.png)

重启后：

![img](file:///D:/Documents/My Knowledge/temp/7b47fa5e-37c3-49e5-b14e-ebec57396664/128/index_files/ec383ca2-0e77-42fb-8e5d-84ce6208b5d8.png)

![img](file:///D:/Documents/My Knowledge/temp/7b47fa5e-37c3-49e5-b14e-ebec57396664/128/index_files/b6b8f20f-fcb5-4528-a2a5-8457ab325b6a.png)





# 八、ActiveMQ的传输协议

## 8.1  简介

ActiveMQ支持的client-broker通讯协议有：TVP、NIO、UDP、SSL、Http(s)、VM。其中配置Transport Connector的文件在ActiveMQ安装目录的conf/activemq.xml中的<transportConnectors>标签之内。

activemq传输协议的官方文档：http://activemq.apache.org/configuring-version-5-transports.html 

这些协议参见文件：%activeMQ安装目录%/conf/activemq.xml，下面是文件的重要的内容：

```xml
<transportConnectors>
        <transportConnector name="openwire" uri="tcp://0.0.0.0:61616?maximumConnections=1000&amp;wireFormat.maxFrameSize=104857600"/>
        <transportConnector name="amqp" uri="amqp://0.0.0.0:5672?maximumConnections=1000&amp;wireFormat.maxFrameSize=104857600"/>
        <transportConnector name="stomp" uri="stomp://0.0.0.0:61613?maximumConnections=1000&amp;wireFormat.maxFrameSize=104857600"/>
        <transportConnector name="mqtt" uri="mqtt://0.0.0.0:1884?maximumConnections=1000&amp;wireFormat.maxFrameSize=104857600"/>
        <transportConnector name="ws" uri="ws://0.0.0.0:61614?maximumConnections=1000&amp;wireFormat.maxFrameSize=104857600"/>
</transportConnectors>
```

在上文给出的配置信息中，URI描述信息的头部都是采用协议名称：例如

  描述amqp协议的监听端口时，采用的URI描述格式为“amqp://······”；

  描述Stomp协议的监听端口时，采用URI描述格式为“stomp://······”；

  唯独在进行openwire协议描述时，URI头却采用的“tcp://······”。这是因为ActiveMQ中默认的消息协议就是openwire

 

## 8.2  支持的传输协议

个人说明：除了tcp和nio协议，其他的了解就行。各种协议有各自擅长该协议的中间件，工作中一般不会使用activemq去实现这些协议。如： mqtt是物联网专用协议，采用的中间件一般是mosquito。ws是websocket的协议，是和前端对接常用的，一般在java代码中内嵌一个基站（中间件）。stomp好像是邮箱使用的协议的，各大邮箱公司都有基站（中间件）。

**注意：协议不同，我们的代码都会不同。**

![img](file:///D:/Documents/My%20Knowledge/temp/7b47fa5e-37c3-49e5-b14e-ebec57396664/128/index_files/1_19.png)

### (1) TCP协议

(1) Transmission Control Protocol(TCP)是默认的。TCP的Client监听端口61616。

(2) 在网络传输数据前，必须要先序列化数据，消息是通过一个叫wire protocol的来序列化成字节流。

  默认情况下ActiveMQ把 wire protocol 叫做OpenWire，它的目的是促使网络上的效率和数据快速交互。

(3) TCP连接的URI形式如：**tcp://HostName:port?key=value&key=value**，后面的参数是可选的。

(4) TCP传输的的优点：

> - TCP协议传输可靠性高，稳定性强；
> - 高效率：字节流方式传递，效率很高；
> - 有效性、可用性：应用广泛，支持任何平台；

(5) 关于Transport协议的可选配置参数可以参考官网 http://activemq.apache.org/tcp-transport-reference。

 

 

### (2) NIO协议

(1) New I/O API Protocol(NIO)。

(2) NIO协议和TCP协议类似，但NIO更侧重于底层的访问操作。它允许开发人员对同一资源可有更多的client调用和服务器端有更多的负载。

(3) 适合使用NIO协议的场景：

> - 可能有大量的Client去连接到Broker上，一般情况下，大量的Client去连接Broker是被操作系统的线程所限制的。因此，NIO的实现比TCP需要更少的线程去运行，所以建议使用NIO协议。
> - 可能对于Broker有一个很迟钝的网络传输，NIO比TCP提供更好的性能。

(4) NIO连接的URI形式：nio://hostname:port?key=value&key=value

(5) 关于Transport协议的可选配置参数可以参考官网http://activemq.apache.org/configuring-version-5-transports.html

![img](file:///D:/Documents/My%20Knowledge/temp/7b47fa5e-37c3-49e5-b14e-ebec57396664/128/index_files/1_54.png)

 

### (3) AMQP协议

![img](file:///D:/Documents/My%20Knowledge/temp/7b47fa5e-37c3-49e5-b14e-ebec57396664/128/index_files/1_21.png)



 

### (4) STOMP协议

![img](file:///D:/Documents/My%20Knowledge/temp/7b47fa5e-37c3-49e5-b14e-ebec57396664/128/index_files/1_22.png)

![img](file:///D:/Documents/My%20Knowledge/temp/7b47fa5e-37c3-49e5-b14e-ebec57396664/128/index_files/1_23.png)

 

 

### (5) MQTT协议

![img](file:///D:/Documents/My%20Knowledge/temp/7b47fa5e-37c3-49e5-b14e-ebec57396664/128/index_files/1_24.png)

https://github.com/fusesource/mqtt-client 

### (6) SSL协议

![img](file:///D:/Documents/My%20Knowledge/temp/7b47fa5e-37c3-49e5-b14e-ebec57396664/128/index_files/be86a8c2-6506-4cde-8617-ff59f7814874.png)

### (7) WS协议

![img](file:///D:/Documents/My%20Knowledge/temp/7b47fa5e-37c3-49e5-b14e-ebec57396664/128/index_files/b2486417-25e1-4f65-a2df-3c906a2194ae.jpg)

 

## 8.3  NIO协议案例

**ActiveMQ这些协议传输的底层默认都是使用BIO网络的IO模型。只有当我们指定使用nio才使用NIO的IO模型。**

### **(1)**   **修改配置文件**activemq.xml

![img](file:///D:/Documents/My%20Knowledge/temp/7b47fa5e-37c3-49e5-b14e-ebec57396664/128/index_files/1_25.png)

①　修改配置文件activemq.xml在 <transportConnectors>节点下添加如下内容：

​    <transportConnector name="nio" uri="nio://0.0.0.0:61618?trace=true" />

②　修改完成后重启activemq: 

​    service activemq restart

③　查看管理后台，可以看到页面多了nio

![img](file:///D:/Documents/My%20Knowledge/temp/7b47fa5e-37c3-49e5-b14e-ebec57396664/128/index_files/1_26.png)

### **(2)**    **代码**

**生产者：**

```java
package com.atguigu.activemq.protocol;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * 消息生产者-nio协议
 *
 * @author 王柳
 * @date 2020/7/30 13:50
 */
public class Nio_JmsProduce {
    private static final String ACTIVEMQ_URL = "nio://192.168.1.235:61618";
    private static final String QUEUE_NAME = "transport";

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

```

消费者：

```java
package com.atguigu.activemq.protocol;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * 简单消息消费者-阻塞式消费者--nio协议
 *
 * @author 王柳
 * @date 2020/7/30 14:14
 */
public class Nio_JmsConsumer {
    private static final String ACTIVEMQ_URL = "nio://192.168.1.235:61618";
    private static final String QUEUE_NAME = "transport";

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

        //5.创建消息的消费者,指定消费哪一个队列里面的消息
        MessageConsumer messageConsumer = session.createConsumer(queue);

        //循环获取
        while (true) {
            //6.通过消费者调用方法获取队列里面的消息(发送的消息是什么类型,接收的时候就强转成什么类型)
            // receive() 一直阻塞，消费者一直存在
            // receive(long var1) receive(4000L)4秒过后消费者走人，消费者消失
            TextMessage textMessage = (TextMessage) messageConsumer.receive();
            if (textMessage != null) {
                System.out.println("****消费者接收到的消息:  " + textMessage.getText());
            } else {
                break;
            }
        }

        //7.关闭资源
        messageConsumer.close();
        session.close();
        connection.close();
    }
}

```



## 8.4  NIO协议案例增强

### **(1)**   **目的**

上面是Openwire协议传输底层使用NIO网络IO模型。 如何**让其他协议传输底层也使用NIO网络IO模型**呢？

![img](file:///D:/Documents/My%20Knowledge/temp/7b47fa5e-37c3-49e5-b14e-ebec57396664/128/index_files/1_27.png)

![img](file:///D:/Documents/My%20Knowledge/temp/7b47fa5e-37c3-49e5-b14e-ebec57396664/128/index_files/1_28.png)

http://activemq.apache.org/auto

 

### **(2)**   **修改配置文件**activemq.xml

```xml
<transportConnectors>
    <transportConnector name="openwire" uri="tcp://0.0.0.0:61626?maximumConnections=1000&amp;wireFormat.maxFrameSize=104857600"/>
    <transportConnector name="amqp" uri="amqp://0.0.0.0:5682?maximumConnections=1000&amp;wireFormat.maxFrameSize=104857600"/>
    <transportConnector name="stomp" uri="stomp://0.0.0.0:61623?maximumConnections=1000&amp;wireFormat.maxFrameSize=104857600"/>
    <transportConnector name="mqtt" uri="mqtt://0.0.0.0:1893?maximumConnections=1000&amp;wireFormat.maxFrameSize=104857600"/>
    <transportConnector name="ws" uri="ws://0.0.0.0:61624?maximumConnections=1000&amp;wireFormat.maxFrameSize=104857600"/>
    <transportConnector name="nio" uri="nio://0.0.0.0:61618?trace=true" />
    <transportConnector name="auto+nio" uri="auto+nio://0.0.0.0:61608?maximumConnections=1000&amp;wireFormat.maxFrameSize=104857600&amp;org.apache.activemq.transport.nio.SelectorManager.corePoolSize=20&amp;org.apache.activemq.transport.nio.Se1ectorManager.maximumPoo1Size=50"/>
</transportConnectors>

```

auto  : 针对所有的协议，他会识别我们是什么协议。

nio   ：使用NIO网络IO模型。

修改配置文件后重启activemq。

 

### **(3)**   **代码**

使用nio模型的tcp协议生产者：

```java
package com.atguigu.activemq.protocol;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * 使用nio模型的tcp协议生产者-nio增强
 *
 * @author 王柳
 * @date 2020/7/30 13:50
 */
public class Nio_Increase_JmsProduce {
    private static final String ACTIVEMQ_URL = "tcp://192.168.1.235:61608";
    private static final String QUEUE_NAME = "auto-nio";

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

```

 使用nio模型的tcp协议消费者： 

```java
package com.atguigu.activemq.protocol;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * 使用nio模型的tcp协议消费者-nio增强
 *
 * @author 王柳
 * @date 2020/7/30 14:14
 */
public class Nio_Increase_JmsConsumer {
    private static final String ACTIVEMQ_URL = "tcp://192.168.1.235:61608";
    private static final String QUEUE_NAME = "auto-nio";

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

        //5.创建消息的消费者,指定消费哪一个队列里面的消息
        MessageConsumer messageConsumer = session.createConsumer(queue);

        //循环获取
        while (true) {
            //6.通过消费者调用方法获取队列里面的消息(发送的消息是什么类型,接收的时候就强转成什么类型)
            // receive() 一直阻塞，消费者一直存在
            // receive(long var1) receive(4000L)4秒过后消费者走人，消费者消失
            TextMessage textMessage = (TextMessage) messageConsumer.receive();
            if (textMessage != null) {
                System.out.println("****消费者接收到的消息:  " + textMessage.getText());
            } else {
                break;
            }
        }

        //7.关闭资源
        messageConsumer.close();
        session.close();
        connection.close();
    }
}

```



# 九、ActiveMQ的消息存储和持久化

## 9.1  介绍

### **(1)**   **此处持久化和之前的持久化的区别**

![img](file:///D:/Documents/My%20Knowledge/temp/7b47fa5e-37c3-49e5-b14e-ebec57396664/128/index_files/1_29.png)

MQ高可用：事务、可持久、签收，是属于MQ自身特性，自带的。这里的持久化是外力，是外部插件。之前讲的持久化是MQ的外在表现，现在讲的的持久是是底层实现。

 

### **(2)**   **是什么**

官网文档：http://activemq.apache.org/persistence

持久化是什么？一句话就是：ActiveMQ宕机了，消息不会丢失的机制。

说明：为了避免意外宕机以后丢失信息，需要做到重启后可以恢复消息队列，消息系统一半都会采用持久化机制。

ActiveMQ的消息持久化机制有JDBC，AMQ，KahaDB和LevelDB，无论使用哪种持久化方式，消息的存储逻辑都是一致的。

就是**在发送者将消息发送出去后，消息中心首先将消息存储到本地数据文件、内存数据库或者远程数据库等。再试图将消息发给接收者，成功则将消息从存储中删除，失败则继续尝试尝试发送。消息中心启动以后，要先检查指定的存储位置是否有未成功发送的消息，如果有，则会先把存储位置中的消息发出去**。

 

 

## 9.2  有哪些

### **(1)**   **AMQ Message Store**

基于文件的存储机制，是以前的默认机制，现在不再使用。

AMQ是一种文件存储形式，它具有写入速度快和容易恢复的特点。消息存储在一个个文件中，文件的默认大小为32M，当一个文件中的消息已经全部被消费，那么这个文件将被标识为可删除，在下一个清除阶段，这个文件被删除。AMQ适用于ActiveMQ5.3之前的版本。

 

### **(2)**   **kahaDB**

现在默认的。下面我们再详细介绍。

 

### **(3)**   **JDBC**消息存储

下面我们再详细介绍。

 

### **(4)**   **LevelDB**消息存储

**http://activemq.apache.org/leveldb-store**

过于新兴的技术，现在有些不确定。

![img](file:///D:/Documents/My%20Knowledge/temp/7b47fa5e-37c3-49e5-b14e-ebec57396664/128/index_files/1_30.png)

 

### **(5)**   **JDBC Message Store with ActiveMQ Journal**

下面我们再详细介绍。

 

 

## 9.3  kahaDB消息存储

### **(1)**   **介绍**

基于日志文件，从ActiveMQ5.4（含）开始默认的持久化插件。

官网文档：http://activemq.aache.org/kahadb

官网上还有一些其他配置参数。

配置文件activemq.xml中，如下：

```xml
<persistenceAdapter>
    <kahaDB directory="${activemq.data}/kahadb"/>
</persistenceAdapter>
```

日志文件的存储目录在：%activemq安装目录%/data/kahadb

 

### **(2)**   **说明**

![img](file:///D:/Documents/My%20Knowledge/temp/7b47fa5e-37c3-49e5-b14e-ebec57396664/128/index_files/1_31.png)



### **(3)**   **KahaDB**的存储原理

![img](file:///D:/Documents/My%20Knowledge/temp/7b47fa5e-37c3-49e5-b14e-ebec57396664/128/index_files/1_32.png)

![img](file:///D:/Documents/My%20Knowledge/temp/7b47fa5e-37c3-49e5-b14e-ebec57396664/128/index_files/1_33.png)

 

## 9.4  JDBC消息存储

http://activemq.apache.org/persistence

### （1） 设置步骤

#### **①** **原理图**

![img](file:///D:/Documents/My%20Knowledge/temp/7b47fa5e-37c3-49e5-b14e-ebec57396664/128/index_files/1_34.png)

#### **②** **添加**mysql****数据库的驱动包到****lib****文件夹

![img](file:///D:/Documents/My Knowledge/temp/7b47fa5e-37c3-49e5-b14e-ebec57396664/128/index_files/0.8820490525880204.png)

 

#### **③** **jdbcPersistenceAdapter**配置

![img](file:///D:/Documents/My%20Knowledge/temp/7b47fa5e-37c3-49e5-b14e-ebec57396664/128/index_files/1_35.png)

修改配置文件activemq.xml。将之前的替换为jdbc的配置。如下：

```xml
<!--  
<persistenceAdapter>
            <kahaDB directory="${activemq.data}/kahadb"/>
      </persistenceAdapter>
-->
<persistenceAdapter>  
      <jdbcPersistenceAdapter dataSource="#mysql-ds"/> 
</persistenceAdapter>
```

#### **④** **数据库连接池配置**

需要我们准备一个mysql数据库，并创建一个名为activemq的数据库。

![img](file:///D:/Documents/My%20Knowledge/temp/7b47fa5e-37c3-49e5-b14e-ebec57396664/128/index_files/1_36.png)

在</broker>标签和<import>标签之间插入数据库连接池配置

![img](file:///D:/Documents/My%20Knowledge/temp/7b47fa5e-37c3-49e5-b14e-ebec57396664/128/index_files/2_2.png)

具体操作如下：

```xml
    </broker>

    <bean id="mysql-ds" class="org.apache.commons.dbcp2.BasicDataSource" destroy-method="close">
        <property name="driverClassName" value="com.mysql.jdbc.Driver"/>
        <property name="url" value="jdbc:mysql://mysql数据库URL:3306/activemq?relaxAutoCommit=true"/>
        <property name="username" value="mysql数据库用户名"/>
        <property name="password" value="mysql数据库密码"/>
        <property name="poolPreparedStatements" value="true"/>
    </bean>

    <import resource="jetty.xml"/>

```

之后需要建一个数据库，名为activemq。新建的数据库要采用latin1 或者ASCII编码。https://blog.csdn.net/JeremyJiaming/article/details/88734762

默认是的dbcp数据库连接池，如果要换成其他数据库连接池，需要将该连接池jar包，也放到lib目录下。

 

#### **⑤** **建库**SQL和创表说明

重启activemq。会自动生成如下3张表。如果没有自动生成，需要我们手动执行SQL。我个人建议要自动生成，我在操作过程中查看日志文件，发现了不少问题，最终解决了这些问题后，是能够自动生成的。如果不能自动生成说明你的操作有问题。如果实在不行，下面是手动建表的SQL:

建表SQL：

```sql
-- auto-generated definition
create table ACTIVEMQ_ACKS
(
    CONTAINER     varchar(250)     not null comment '消息的Destination',
    SUB_DEST      varchar(250)     null comment '如果使用的是Static集群，这个字段会有集群其他系统的信息',
    CLIENT_ID     varchar(250)     not null comment '每个订阅者都必须有一个唯一的客户端ID用以区分',
    SUB_NAME      varchar(250)     not null comment '订阅者名称',
    SELECTOR      varchar(250)     null comment '选择器，可以选择只消费满足条件的消息，条件可以用自定义属性实现，可支持多属性AND和OR操作',
    LAST_ACKED_ID bigint           null comment '记录消费过消息的ID',
    PRIORITY      bigint default 5 not null comment '优先级，默认5',
    XID           varchar(250)     null,
    primary key (CONTAINER, CLIENT_ID, SUB_NAME, PRIORITY)
)
    comment '用于存储订阅关系。如果是持久化Topic，订阅者和服务器的订阅关系在这个表保存';

create index ACTIVEMQ_ACKS_XIDX
    on ACTIVEMQ_ACKS (XID);

 
-- auto-generated definition
create table ACTIVEMQ_LOCK
(
    ID          bigint       not null
        primary key,
    TIME        bigint       null,
    BROKER_NAME varchar(250) null
);

 
-- auto-generated definition
create table ACTIVEMQ_MSGS
(
    ID         bigint       not null
        primary key,
    CONTAINER  varchar(250) not null,
    MSGID_PROD varchar(250) null,
    MSGID_SEQ  bigint       null,
    EXPIRATION bigint       null,
    MSG        blob         null,
    PRIORITY   bigint       null,
    XID        varchar(250) null
);

create index ACTIVEMQ_MSGS_CIDX
    on ACTIVEMQ_MSGS (CONTAINER);

create index ACTIVEMQ_MSGS_EIDX
    on ACTIVEMQ_MSGS (EXPIRATION);

create index ACTIVEMQ_MSGS_MIDX
    on ACTIVEMQ_MSGS (MSGID_PROD, MSGID_SEQ);

create index ACTIVEMQ_MSGS_PIDX
    on ACTIVEMQ_MSGS (PRIORITY);

create index ACTIVEMQ_MSGS_XIDX
    on ACTIVEMQ_MSGS (XID);

```

##### ACTIVEMQ_MSGS数据表：

![img](file:///D:/Documents/My%20Knowledge/temp/7b47fa5e-37c3-49e5-b14e-ebec57396664/128/index_files/1_37.png)

 

##### **ACTIVEMQ_ACKS**数据表：

![img](file:///D:/Documents/My%20Knowledge/temp/7b47fa5e-37c3-49e5-b14e-ebec57396664/128/index_files/2_3.png)

 

##### **ACTIVEMQ_LOCK**数据表：

![img](file:///D:/Documents/My%20Knowledge/temp/7b47fa5e-37c3-49e5-b14e-ebec57396664/128/index_files/3.png)

 

 

### （2） queue验证和数据表变化

![img](file:///D:/Documents/My%20Knowledge/temp/7b47fa5e-37c3-49e5-b14e-ebec57396664/128/index_files/1_38.png)

queue模式，非持久化不会将消息持久化到数据库。

queue模式，持久化会将消息持久化数据库。

 

我们使用queue模式持久化，发布3条消息后，发现ACTIVEMQ_MSGS数据表多了3条数据。

![img](file:///D:/Documents/My%20Knowledge/temp/7b47fa5e-37c3-49e5-b14e-ebec57396664/128/index_files/2_4.png)

启动消费者，消费了所有的消息后，发现数据表的数据消失了。

![img](file:///D:/Documents/My%20Knowledge/temp/7b47fa5e-37c3-49e5-b14e-ebec57396664/128/index_files/3_2.png)

queue模式非持久化，不会持久化消息到数据表。

 

### （3） topic验证和说明

我们先启动一下持久化topic的消费者。看到ACTIVEMQ_ACKS数据表多了一条消息。

```java
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
```

ACTIVEMQ_ACKS数据表，多了一个消费者的身份信息。一条记录代表：一个持久化topic消费者

![img](file:///D:/Documents/My%20Knowledge/temp/7b47fa5e-37c3-49e5-b14e-ebec57396664/128/index_files/1_39.png)

我们启动持久化生产者发布3个数据，ACTIVEMQ_MSGS数据表新增3条数据，消费者消费所有的数据后，ACTIVEMQ_MSGS数据表的数据并没有消失。持久化topic的消息不管是否被消费，是否有消费者，产生的数据永远都存在，且只存储一条。这个是要注意的，持久化的topic大量数据后可能导致性能下降。这里就像公众号一样，消费者消费完后，消息还会保留。

![img](file:///D:/Documents/My%20Knowledge/temp/7b47fa5e-37c3-49e5-b14e-ebec57396664/128/index_files/2_5.png)

### （4） 总结

![img](file:///D:/Documents/My%20Knowledge/temp/7b47fa5e-37c3-49e5-b14e-ebec57396664/128/index_files/1_40.png) 



![img](file:///D:/Documents/My%20Knowledge/temp/7b47fa5e-37c3-49e5-b14e-ebec57396664/128/index_files/2_6.png)

![img](file:///D:/Documents/My%20Knowledge/temp/7b47fa5e-37c3-49e5-b14e-ebec57396664/128/index_files/3_3.png)





## 9.5  JDBC Message Store with ActiveMQ Journal

### **(1)**   **说明**

这种方式克服了JDBC Store的不足，JDBC每次消息过来，都需要去写库读库。ActiveMQ Journal，使用高速缓存写入技术，大大提高了性能。当消费者的速度能够及时跟上生产者消息的生产速度时，journal文件能够大大减少需要写入到DB中的消息。

举个例子：生产者生产了1000条消息，这1000条消息会保存到journal文件，如果消费者的消费速度很快的情况下，在journal文件还没有同步到DB之前，消费者已经消费了90%的以上消息，那么这个时候只需要同步剩余的10%的消息到DB。如果消费者的速度很慢，这个时候journal文件可以使消息以批量方式写到DB。

为了高性能，这种方式使用日志文件存储+数据库存储。**先将消息持久到日志文件，等待一段时间再将未消费的消息持久到数据库**。该方式要比JDBC性能要高。

 

### **(2)**   **配置**

下面是基于上面JDBC配置，再做一点修改：

![img](file:///D:/Documents/My%20Knowledge/temp/7b47fa5e-37c3-49e5-b14e-ebec57396664/128/index_files/1_41.png)

![img](file:///D:/Documents/My%20Knowledge/temp/7b47fa5e-37c3-49e5-b14e-ebec57396664/128/index_files/2_7.png)



## 9.6  总结

①　jdbc效率低，kahaDB效率高，jdbc+Journal效率较高。

②　持久化消息主要指的是：MQ所在服务器宕机了消息不会丢试的机制。  

③　持久化机制演变的过程：

​    从最初的AMQ Message Store方案到ActiveMQ V4版本退出的High Performance Journal（高性能事务支持）附件，并且同步推出了关于关系型数据库的存储方案。ActiveMQ5.3版本又推出了对KahaDB的支持（5.4版本后被作为默认的持久化方案），后来ActiveMQ 5.8版本开始支持LevelDB，到现在5.9提供了标准的Zookeeper+LevelDB集群化方案。

④　ActiveMQ消息持久化机制有：

| AMQ                      | 基于日志文件                                                 |
| ------------------------ | ------------------------------------------------------------ |
| KahaDB                   | 基于日志文件，从ActiveMQ5.4开始默认使用                      |
| JDBC                     | 基于第三方数据库                                             |
| Replicated LevelDB Store | 从5.9开始提供了LevelDB和Zookeeper的数据复制方法，用于Master-slave方式的首选数据复制方案。 |

无论使用哪种持久化方式，消息的存储逻辑都是一致的。

 

# 十、ActiveMQ多节点集群

为知笔记地址：[ActiveMQ的多节点集群](wiz://open_document?guid=8ef2d084-1501-49af-943d-c07e5f0a8782&kbguid=&private_kbguid=9e15e816-792d-4528-9d21-a849cc4117d5)

GitHub地址：[https://github.com/wangliu1102/StudyNotes/tree/master/%E5%B0%9A%E7%A1%85%E8%B0%B7Java/%E5%9B%9B%E3%80%81JavaEE%E9%AB%98%E7%BA%A7/11%E3%80%81ActiveMQ/%E5%AE%89%E8%A3%85](https://github.com/wangliu1102/StudyNotes/tree/master/尚硅谷Java/四、JavaEE高级/11、ActiveMQ/安装)



# 十一、高级特性

## 11.1  异步投递

### **(1)**   **异步投递是什么**

![img](file:///D:/Documents/My%20Knowledge/temp/7b47fa5e-37c3-49e5-b14e-ebec57396664/128/index_files/1_42.png)

自我理解：此处的异步是指生产者和broker之间发送消息的异步。不是指生产者和消费者之间异步。

官网介绍：http://activemq.apache.org/async-sends

说明：对于一个Slow Consumer,使用同步发送消息可能出成Producer堵塞等情况，慢消费者适合使用异步发送。(这句话我认为有误)

总结：

①　异步发送可以让生产者发的更快。

②　如果异步投递不需要保证消息是否发送成功，发送者的效率会有所提高。如果异步投递还需要保证消息是否成功发送，并采用了回调的方式，发送者的效率提高不多，这种就有些鸡肋。

 

### **(2)**   **代码实现**

官网上3中代码实现：

![img](file:///D:/Documents/My%20Knowledge/temp/7b47fa5e-37c3-49e5-b14e-ebec57396664/128/index_files/1_43.png)

```java
package com.atguigu.activemq.sync;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * 消息生产者-异步投递
 *
 * @author 王柳
 * @date 2020/7/30 13:50
 */
public class JmsProduce {
    // 方式1。3种方式任选一种
    private static final String ACTIVEMQ_URL = "tcp://192.168.1.235:61616?jms.useAsyncSend=true";
    private static final String QUEUE_NAME = "queue02";

    public static void main(String[] args) throws JMSException {
        //1.创建连接工厂，按照给定的URL，采用默认的用户名密码
        ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory(ACTIVEMQ_URL);
        // 方式2
//        activeMQConnectionFactory.setUseAsyncSend(true);

        //2.通过连接工厂,获得connection并启动访问
        Connection connection = activeMQConnectionFactory.createConnection();
        // 方式3
//        ((ActiveMQConnection) connection).setUseAsyncSend(true);

        connection.start();

        //3.创建会话session
        //两个参数transacted=事务,acknowledgeMode=确认模式(签收)
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

        //4.创建目的地(具体是队列queue还是主题topic)
        Queue queue = session.createQueue(QUEUE_NAME);

        //5.创建消息的生产者
        MessageProducer messageProducer = session.createProducer(queue);

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

```



### **(3)**   **异步发送如何确认发送成功**

![img](file:///D:/Documents/My%20Knowledge/temp/7b47fa5e-37c3-49e5-b14e-ebec57396664/128/index_files/1_44.png)

下面演示异步发送的回调：

```java
package com.atguigu.activemq.sync;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.ActiveMQMessageProducer;
import org.apache.activemq.AsyncCallback;

import javax.jms.*;
import java.util.UUID;

/**
 * 异步投递回调-生产者
 *
 * @author 王柳
 * @date 2020/8/3 16:16
 */
public class JmsProduce_AsyncSend {
    private static final String ACTIVEMQ_URL = "tcp://192.168.1.235:61616";
    private static final String ACTIVEMQ_QUEUE_NAME = "Queue-异步投递回调";

    public static void main(String[] args) throws JMSException {
        ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory();
        activeMQConnectionFactory.setBrokerURL(ACTIVEMQ_URL);
        //开启异步投递
        activeMQConnectionFactory.setUseAsyncSend(true);
        Connection connection = activeMQConnectionFactory.createConnection();
        connection.start();

        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Queue queue = session.createQueue(ACTIVEMQ_QUEUE_NAME);
        //向上转型到ActiveMQMessageProducer
        ActiveMQMessageProducer activeMQMessageProducer = (ActiveMQMessageProducer) session.createProducer(queue);

        for (int i = 0; i < 3; i++) {
            TextMessage textMessage = session.createTextMessage("message-" + i);
            textMessage.setJMSMessageID(UUID.randomUUID().toString() + "----orderAtguigu");
            String textMessageId = textMessage.getJMSMessageID();
            //使用ActiveMQMessageProducer的发送消息,可以创建回调
            activeMQMessageProducer.send(textMessage, new AsyncCallback() {
                @Override
                public void onSuccess() {
                    System.out.println(textMessageId + "发送成功");
                }

                @Override
                public void onException(JMSException exception) {
                    System.out.println(textMessageId + "发送失败");
                }
            });
        }
        activeMQMessageProducer.close();
        session.close();
        connection.close();
    }
}

```

控制台观察发送消息的信息：

![img](file:///D:/Documents/My%20Knowledge/temp/7b47fa5e-37c3-49e5-b14e-ebec57396664/128/index_files/1_45.png)

![img](file:///D:/Documents/My%20Knowledge/temp/7b47fa5e-37c3-49e5-b14e-ebec57396664/128/index_files/2_8.png)

 

 

## 11.2  延迟投递和定时投递

### **(1)**   **介绍**

官网文档：http://activemq.apache.org/delay-and-schedule-message-delivery.html

![img](file:///D:/Documents/My%20Knowledge/temp/7b47fa5e-37c3-49e5-b14e-ebec57396664/128/index_files/1_46.png)

![img](file:///D:/Documents/My%20Knowledge/temp/7b47fa5e-37c3-49e5-b14e-ebec57396664/128/index_files/2_9.png)

 

### **(2)**   **修改配置文件并重启**

![img](file:///D:/Documents/My%20Knowledge/temp/7b47fa5e-37c3-49e5-b14e-ebec57396664/128/index_files/1_47.png)

在activemq.xml修改添加如下：

```xml
 </bean>

    <broker xmlns="http://activemq.apache.org/schema/core" brokerName="localhost" dataDirectory="${activemq.data}"  schedulerSupport="true" >

        <destinationPolicy>
```

之后重启activemq

 

### **(3)**   **代码实现**

java代码里面封装的辅助消息类型：ScheduleMessage

生产者代码：

```java
package com.atguigu.activemq.schedule;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.ScheduledMessage;

import javax.jms.*;

/**
 * 延迟投递-生产者
 *
 * @author 王柳
 * @date 2020/8/3 16:23
 */
public class Jms_Schedule_Producer {
    private static final String ACTIVEMQ_URL = "tcp://192.168.1.235:61616";

    private static final String ACTIVEMQ_QUEUE_NAME = "Schedule01";

    public static void main(String[] args) throws JMSException {
        ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory(ACTIVEMQ_URL);
        Connection connection = activeMQConnectionFactory.createConnection();
        connection.start();
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Queue queue = session.createQueue(ACTIVEMQ_QUEUE_NAME);
        MessageProducer messageProducer = session.createProducer(queue);
        long delay = 10 * 1000;
        long period = 5 * 1000;
        int repeat = 3;
        try {
            for (int i = 0; i < 3; i++) {
                TextMessage textMessage = session.createTextMessage("tx msg--" + i);
                // 延迟的时间
                textMessage.setLongProperty(ScheduledMessage.AMQ_SCHEDULED_DELAY, delay);
                // 重复投递的时间间隔
                textMessage.setLongProperty(ScheduledMessage.AMQ_SCHEDULED_PERIOD, period);
                // 重复投递的次数
                textMessage.setIntProperty(ScheduledMessage.AMQ_SCHEDULED_REPEAT, repeat);
                // 此处的意思：该条消息，等待10秒，之后每5秒发送一次，重复发送3次。
                messageProducer.send(textMessage);
            }
            System.out.println("消息发送完成");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            messageProducer.close();
            session.close();
            connection.close();
        }
    }

}

```

消费者代码：

```java
package com.atguigu.activemq.schedule;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;
import java.io.IOException;

/**
 * 延迟投递-消费者
 *
 * @author 王柳
 * @date 2020/8/3 16:24
 */
public class Jms_Schedule_Consumer {
    private static final String ACTIVEMQ_URL = "tcp://192.168.1.235:61616";

    private static final String ACTIVEMQ_QUEUE_NAME = "Schedule01";

    public static void main(String[] args) throws JMSException, IOException {
        ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory(ACTIVEMQ_URL);
        Connection connection = activeMQConnectionFactory.createConnection();
        connection.start();
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Queue queue = session.createQueue(ACTIVEMQ_QUEUE_NAME);
        MessageConsumer messageConsumer = session.createConsumer(queue);
        messageConsumer.setMessageListener(new MessageListener() {

            @Override
            public void onMessage(Message message) {
                if (message instanceof TextMessage) {
                    try {
                        TextMessage textMessage = (TextMessage) message;
                        System.out.println("***消费者接收到的消息:   " + textMessage.getText());
                        textMessage.acknowledge();
                    } catch (Exception e) {
                        System.out.println("出现异常，消费失败，放弃消费");
                    }
                }
            }
        });
        System.in.read();
        messageConsumer.close();
        session.close();
        connection.close();
    }

}

```

 ![img](file:///D:/Documents/My%20Knowledge/temp/7b47fa5e-37c3-49e5-b14e-ebec57396664/128/index_files/2210fb01-b882-4db6-a6fe-0f26b7aac2c8.png)

## 11.3  消息消费的重试机制

### **(1)**   **是什么**

官网文档：http://activemq.apache.org/redelivery-policy

是什么： 消费者收到消息，之后出现异常了，没有告诉broker确认收到该消息，broker会尝试再将该消息发送给消费者。尝试n次，如果消费者还是没有确认收到该消息，那么该消息将被放到死信队列重，之后broker不会再将该消息发送给消费者。

 

### **(2)**   **具体哪些情况会引发消息重发**

①　Client用了transactions且在session中调用了rollback

②　Client用了transactions且在调用commit之前关闭或者没有commit

③　Client在

CLIENT_ACKNOWLEDGE的传递模式下，session中调用了recover

 

### **(3)**   **请说说消息重发时间间隔和重发次数**

间隔：1

次数：6

每秒发6次

 

### **(4)**   **有毒消息**Poison ACK

一个消息被redelivedred超过默认的最大重发次数（默认6次）时，消费的会给MQ发一个“poison ack”表示这个消息有毒，告诉broker不要再发了。这个时候broker会把这个消息放到DLQ（死信队列）。

 

### **(5)**   **属性说明**

![img](file:///D:/Documents/My%20Knowledge/temp/7b47fa5e-37c3-49e5-b14e-ebec57396664/128/index_files/1_48.png)

 

### **(6)**   **代码验证**

生产者。发送3条数据。和queue中一样，只是发消息。

```java
package com.atguigu.activemq.redelivery;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * 消息生产者-消息重试机制
 *
 * @author 王柳
 * @date 2020/7/30 13:50
 */
public class Redelivery_JmsProduce {
    private static final String ACTIVEMQ_URL = "tcp://192.168.1.235:61616";
    private static final String QUEUE_NAME = "redelivery01";

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

```

消费者。开启事务，却没有commit。重启消费者，前6次都能收到消息，到第7次，不会再收到消息。代码：

```java
package com.atguigu.activemq.redelivery;

import org.apache.activemq.ActiveMQConnectionFactory;

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

```

activemq管理后台。多了一个名为ActiveMQ.DLQ队列，里面多了3条消息。

![img](file:///D:/Documents/My%20Knowledge/temp/7b47fa5e-37c3-49e5-b14e-ebec57396664/128/index_files/1_49.png)



### **(7)**   **代码修改默认参数**

消费者。修改重试次数为3。更多的设置请参考官网文档 http://activemq.apache.org/redelivery-policy。

```java
        ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory(ACTIVEMQ_URL);
        // 修改默认参数，设置消息消费重试3次
        RedeliveryPolicy queuePolicy = new RedeliveryPolicy();
        queuePolicy.setMaximumRedeliveries(2);
        Connection connection = activeMQConnectionFactory.createConnection();
        。。。
```

 ![img](file:///D:/Documents/My%20Knowledge/temp/7b47fa5e-37c3-49e5-b14e-ebec57396664/128/index_files/2d73870a-6fea-4ec3-b6bc-e7bef15628cf.png)

### **(8)**   **整合**spring

![img](file:///D:/Documents/My%20Knowledge/temp/7b47fa5e-37c3-49e5-b14e-ebec57396664/128/index_files/1_50.png)

 

## 11.4  死信队列

承接上个标题的内容。

### **(1)**   **是什么**

官网文档： http://activemq.apache.org/redelivery-policy

死信队列：异常消息规避处理的集合，主要处理失败的消息。

![img](file:///D:/Documents/My%20Knowledge/temp/7b47fa5e-37c3-49e5-b14e-ebec57396664/128/index_files/1_51.png)

![img](file:///D:/Documents/My%20Knowledge/temp/7b47fa5e-37c3-49e5-b14e-ebec57396664/128/index_files/2_10.png)

![img](file:///D:/Documents/My%20Knowledge/temp/7b47fa5e-37c3-49e5-b14e-ebec57396664/128/index_files/3_4.png)

 

### **(2)**   **死信队列的配置（一般采用默认）**

#### **1.** **sharedDeadLetterStrategy**

不管是queue还是topic，失败的消息都放到这个队列中。下面修改activemq.xml的配置，可以修改队列的名字。

![img](file:///D:/Documents/My%20Knowledge/temp/7b47fa5e-37c3-49e5-b14e-ebec57396664/128/index_files/1_52.png)

 

#### **2.** **individualDeadLetterStrategy**

可以为queue和topic单独指定两个死信队列。还可以为某个话题，单独指定一个死信队列。

![img](file:///D:/Documents/My%20Knowledge/temp/7b47fa5e-37c3-49e5-b14e-ebec57396664/128/index_files/2_11.png)

![img](file:///D:/Documents/My%20Knowledge/temp/7b47fa5e-37c3-49e5-b14e-ebec57396664/128/index_files/3_5.png)

![img](file:///D:/Documents/My%20Knowledge/temp/7b47fa5e-37c3-49e5-b14e-ebec57396664/128/index_files/4.png)

 

#### **3.** **自动删除过期消息**

过期消息是值生产者指定的过期时间，超过这个时间的消息。

![img](file:///D:/Documents/My%20Knowledge/temp/7b47fa5e-37c3-49e5-b14e-ebec57396664/128/index_files/5.png)

 

#### **4.** **存放非持久消息到死信队列中**

![img](file:///D:/Documents/My%20Knowledge/temp/7b47fa5e-37c3-49e5-b14e-ebec57396664/128/index_files/6.png)

 

 

## 11.5  消息不被重复消费，幂等性

如何保证消息不被重复消费呢？幕等性问题你谈谈

![img](file:///D:/Documents/My%20Knowledge/temp/7b47fa5e-37c3-49e5-b14e-ebec57396664/128/index_files/1_53.png)

![img](file:///D:/Documents/My%20Knowledge/temp/7b47fa5e-37c3-49e5-b14e-ebec57396664/128/index_files/2_12.png)

幂等性如何解决，根据messageid去查这个消息是否被消费了。

 

 

 