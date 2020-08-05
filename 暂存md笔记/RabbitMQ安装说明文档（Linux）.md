## 1. 安装Socat

在线安装依赖环境：

```shell
yum install gcc

yum install socat
```



## 2. 安装Erlang

![1571887406513](file:///D:/Documents/My Knowledge/temp/94090edc-4419-4de6-ae36-8bb13c70abaf/128/index_files/1571887406513.png)

```shell

mkdir /rabbitmq && cd /rabbitmq

# 上传 erlang-22.0.7-1.el7.x86_64.rpm 安装包上传

# 安装
rpm -ivh erlang-22.0.7-1.el7.x86_64.rpm

```



## 3. 安装RabbitMQ

```shell
cd /rabbitmq

# 上传 rabbitmq-server-3.7.17-1.el7.noarch.rpm 安装包
上传

# 安装
rpm -ivh rabbitmq-server-3.7.17-1.el7.noarch.rpm

```

![1565097525717](file:///D:/Documents/My Knowledge/temp/94090edc-4419-4de6-ae36-8bb13c70abaf/128/index_files/1565097525717.png)

## 4. 开启管理界面及配置

```shell
# 开启管理界面
rabbitmq-plugins enable rabbitmq_management

# 配置远程可使用guest登录mq
cd /usr/share/doc/rabbitmq-server-3.7.17

cp rabbitmq.config.example /etc/rabbitmq/rabbitmq.config

# 修改配置文件
vi /etc/rabbitmq/rabbitmq.config
```

![1565097575497](file:///D:/Documents/My Knowledge/temp/94090edc-4419-4de6-ae36-8bb13c70abaf/128/index_files/1565097575497.png)



修改`/etc/rabbitmq/rabbitmq.config`配置文件：

![1565097801642](file:///D:/Documents/My Knowledge/temp/94090edc-4419-4de6-ae36-8bb13c70abaf/128/index_files/1565097801642.png)



![1565097904202](file:///D:/Documents/My Knowledge/temp/94090edc-4419-4de6-ae36-8bb13c70abaf/128/index_files/1565097904202.png)



## 5. 启动

```shell
centos6用这个命令：
/sbin/service rabbitmq-server restart

centos7用这个命令：
systemctl start rabbitmq-server
```



## 6. 配置虚拟主机及用户

### 6.1. 用户角色

RabbitMQ在安装好后，可以访问`http://ip地址:15672` ；其自带了guest/guest的用户名和密码；如果需要创建自定义用户；那么也可以登录管理界面后，如下操作：

![1565098043833](file:///D:/Documents/My Knowledge/temp/94090edc-4419-4de6-ae36-8bb13c70abaf/128/index_files/1565098043833.png)



![1565098315375](file:///D:/Documents/My Knowledge/temp/94090edc-4419-4de6-ae36-8bb13c70abaf/128/index_files/1565098315375.png)

**角色说明**：

1、 超级管理员(administrator)

可登陆管理控制台，可查看所有的信息，并且可以对用户，策略(policy)进行操作。

2、 监控者(monitoring)

可登陆管理控制台，同时可以查看rabbitmq节点的相关信息(进程数，内存使用情况，磁盘使用情况等)

3、 策略制定者(policymaker)

可登陆管理控制台, 同时可以对policy进行管理。但无法查看节点的相关信息(上图红框标识的部分)。

4、 普通管理者(management)

仅可登陆管理控制台，无法看到节点信息，也无法对策略进行管理。

5、 其他

无法登陆管理控制台，通常就是普通的生产者和消费者。

### 6.2. Virtual Hosts配置

像mysql拥有数据库的概念并且可以指定用户对库和表等操作的权限。RabbitMQ也有类似的权限管理；在RabbitMQ中可以虚拟消息服务器Virtual Host，每个Virtual Hosts相当于一个相对独立的RabbitMQ服务器，每个VirtualHost之间是相互隔离的。exchange、queue、message不能互通。 相当于mysql的db。Virtual Name一般以/开头。



#### 6.2.1. 创建Virtual Hosts

![1565098496482](file:///D:/Documents/My Knowledge/temp/94090edc-4419-4de6-ae36-8bb13c70abaf/128/index_files/1565098496482.png)

#### 6.2.2. 设置Virtual Hosts权限

![1565098585317](file:///D:/Documents/My Knowledge/temp/94090edc-4419-4de6-ae36-8bb13c70abaf/128/index_files/1565098585317.png)