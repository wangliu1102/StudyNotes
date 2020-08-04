# 一、是什么

基于zookeeper和LevelDB搭建ActiveMQ集群。集群仅提供主备方式的高可用集群功能，避免单点故障。

# 二、zookeeper+replicated-leveldb-store的主从集群

## 1、三种集群方式对比

![img](file:///D:/Documents/My Knowledge/temp/8ef2d084-1501-49af-943d-c07e5f0a8782/128/index_files/5b35319a-ca6d-4069-ab2c-e29078b7286f.png)

![img](file:///D:/Documents/My Knowledge/temp/8ef2d084-1501-49af-943d-c07e5f0a8782/128/index_files/8547df28-588b-4937-bda6-7c5f98b51d71.png)

## 2、ZK+Replicated LevelDB Store

### （1）ShareFileSystem

![img](file:///D:/Documents/My Knowledge/temp/8ef2d084-1501-49af-943d-c07e5f0a8782/128/index_files/a2985ba9-3925-4b8f-925d-c47be7bc447a.png)

### （2）是什么

官网：http://activemq.apache.org/replicated-leveldb-store

从ActiveMQ5.9开始，ActiveMQ的集群实现方式取消了传统的Masster-Slave方式。增加了基于Zookeeper+LevelDB的Master-Slave实现方式，从5.9版本后也是官网的推荐。

基于Zookeeper和LevelDB搭建ActiveMQ集群，集群仅提供主备方式的高可用集群功能，避免单点故障。

### （3）官网集群原理

![img](file:///D:/Documents/My Knowledge/temp/8ef2d084-1501-49af-943d-c07e5f0a8782/128/index_files/bff73d3e-6584-45a6-80f4-ec3ab4946cba.jpg)

![img](file:///D:/Documents/My Knowledge/temp/8ef2d084-1501-49af-943d-c07e5f0a8782/128/index_files/bd983040-aa96-4f1d-9e42-00d74d468cf0.png)

### （4）部署规划和步骤

#### 1.环境和版本

![img](file:///D:/Documents/My Knowledge/temp/8ef2d084-1501-49af-943d-c07e5f0a8782/128/index_files/c50c5e09-1ee7-4a23-8704-996a4ad924fe.png)

#### 2.关闭防火墙并保证各个服务器能够ping通

#### 3.要求具备zk集群并可以成功启动

配置集群教程：

```shell
1,创建我们自己的文件夹存放Zookeeper
mkdir /my_zookeeper
 
2,下载Zookeeper
wget -P /my_zookeeper/ https://mirrors.tuna.tsinghua.edu.cn/apache/zookeeper/zookeeper-3.5.6/apache-zookeeper-3.5.6-bin.tar.gz
 
3,解压
tar -zxvf apache-zookeeper-3.5.6-bin.tar.g
 
4,修改配置文件
文件名必须叫这个zoo.cfg
cp zoo_sample.cfg zoo.cfg
设置一下自定义的数据文件夹(注意要手动创建这个目录,后面会用到),,注意最后一定要有/结尾,没有/会报错这是坑
dataDir=/my_zookeeper/apache-zookeeper-3.5.6-bin/data/
在zoo.cfg件最后面追加集群服务器
server.1=192.168.10.130:2888:3888
server.2=192.168.10.132:2888:3888
server.3=192.168.10.133:2888:3888
 
server.1=leantaot-zk-01:2888:3888
1是一个数字，标识这个是第几号服务器
leantaot-zk-01是这个服务器的IP地址（或者是与IP地址做了映射的主机名）
2888第一个端口用来集群成员的信息交换，标识这个服务器与集群中的leader服务器交换信息的端口
3888是在leader挂掉时专门用来进行选举leader所用的端口
 
6.把刚刚配置好的Zookeeper整个文件夹远程推送到其他服务器的/my_zookeeper文件夹内
scp -r /my_zookeeper/ root@192.168.10.132:/
scp -r /my_zookeeper/ root@192.168.10.133:/ 
 
7.创建myid文件， id 与 zoo.cfg 中的序号对应
在192.168.10.130机器上执行
echo 1 > /my_zookeeper/apache-zookeeper-3.5.6-bin/data/zookeeper_server.pid
echo 1 > /my_zookeeper/apache-zookeeper-3.5.6-bin/data/myid
在192.168.10.132机器上执行
echo 2 > /my_zookeeper/apache-zookeeper-3.5.6-bin/data/zookeeper_server.pid
echo 2 > /my_zookeeper/apache-zookeeper-3.5.6-bin/data/myid
在192.168.10.133机器上执行
echo 3 > /my_zookeeper/apache-zookeeper-3.5.6-bin/data/zookeeper_server.pid
echo 3 > /my_zookeeper/apache-zookeeper-3.5.6-bin/data/myid
 
8.bin目录下启动Zookeeper做测试
 ./zkServer.sh start
输出
ZooKeeper JMX enabled by default
Using config: /my_zookeeper/apache-zookeeper-3.5.6-bin/bin/../conf/zoo.cfg
Starting zookeeper ... already running as process 1.
 
```



#### 4.集群部署规划列表

![img](file:///D:/Documents/My Knowledge/temp/8ef2d084-1501-49af-943d-c07e5f0a8782/128/index_files/b2badc8c-4b74-4035-90d8-885dc493fd73.png)

#### 5.创建3台集群目录

就是一台电脑复制三份ActiveMQ。

![img](file:///D:/Documents/My Knowledge/temp/8ef2d084-1501-49af-943d-c07e5f0a8782/128/index_files/0544042c-c40a-46d1-8584-c0bbc98af62c.png)

#### 6.修改管理控制台端口

就是ActiveMQ后台管理页面的访问端口。

![img](file:///D:/Documents/My Knowledge/temp/8ef2d084-1501-49af-943d-c07e5f0a8782/128/index_files/ec2d27e1-4430-4816-8011-e0eb627b6503.jpg)

按照集群规划修改node02和node03：

![img](file:///D:/Documents/My Knowledge/temp/8ef2d084-1501-49af-943d-c07e5f0a8782/128/index_files/1c1824f2-d03f-4240-b24d-6aa8912b1871.png)

#### 7.hostname名字映射

如果不映射只需要把mq配置文件的hostname改成当前主机ip。

![img](file:///D:/Documents/My Knowledge/temp/8ef2d084-1501-49af-943d-c07e5f0a8782/128/index_files/a0c3234f-340b-45fa-bb30-4984b8e16e46.png)

#### 8.ActiveMQ集群配置

配置文件里面的BrokerName要全部一致。

![img](file:///D:/Documents/My Knowledge/temp/8ef2d084-1501-49af-943d-c07e5f0a8782/128/index_files/0f0c0d1c-5b05-4957-8859-d5d818f62e8c.png)

持久化配置(必须)，依次修改三个节点下的配置文件activemq.xml，注意bind的端口不一样：

![img](file:///D:/Documents/My Knowledge/temp/8ef2d084-1501-49af-943d-c07e5f0a8782/128/index_files/49e0345b-0f0c-40c0-9093-a254266a0db6.jpg)

#### 9.修改各个节点的消息端口

![img](file:///D:/Documents/My Knowledge/temp/8ef2d084-1501-49af-943d-c07e5f0a8782/128/index_files/85837acd-7cc6-4512-9890-17979669c1f5.png)

按照集群规划修改node02和node03：

![img](file:///D:/Documents/My Knowledge/temp/8ef2d084-1501-49af-943d-c07e5f0a8782/128/index_files/e8b27374-dc21-4094-8bc0-a82331bbf886.jpg)

#### 10.按顺序启动3个ActiveMQ节点

到这步前提是zk集群已经成功启动运行。**先启动Zk再启动ActiveMQ。**

**![img](file:///D:/Documents/My Knowledge/temp/8ef2d084-1501-49af-943d-c07e5f0a8782/128/index_files/7060c3a3-6f0a-4c76-807b-c29d958d2a64.png)
**

amq_batch.sh内容如下:

![img](file:///D:/Documents/My Knowledge/temp/8ef2d084-1501-49af-943d-c07e5f0a8782/128/index_files/c651abe2-ce2b-4dd4-82e9-0ac1e7bf1ea3.jpg)


#### **11.zk集群节点状态说明**

3台Zk连接任意一台验证三台ActiveMQ是否注册上了Zookeeper。使用zkCli.sh连接一台Zookeeper：

![img](file:///D:/Documents/My Knowledge/temp/8ef2d084-1501-49af-943d-c07e5f0a8782/128/index_files/cd1108b5-03cb-4194-9f43-044ead4afbe8.png)


**查看Master：**

![img](file:///D:/Documents/My Knowledge/temp/8ef2d084-1501-49af-943d-c07e5f0a8782/128/index_files/a4a99e94-0f1e-474e-aee3-249c20dbcf87.png)


### （5）集群可用性测试

集群需要使用(failover:(tcp://192.168.10.130:61616,tcp://192.168.10.132:61616,tcp://192.168.10.133:61616))配置多个ActiveMQ

![img](file:///D:/Documents/My Knowledge/temp/8ef2d084-1501-49af-943d-c07e5f0a8782/128/index_files/9ddb4706-137d-4cca-ae67-5505f2bb11b6.png)

![img](file:///D:/Documents/My Knowledge/temp/8ef2d084-1501-49af-943d-c07e5f0a8782/128/index_files/7a864c84-583e-48d9-bb18-6a3cc45f6975.jpg)