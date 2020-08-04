# 一、ActiveMQ安装

## **1、官方下载**

官网地址： http://activemq.apache.org/

点击下面，开始下载。

![img](file:///D:/Documents/My Knowledge/temp/77da5ff1-2fab-4ddf-8005-54f576cff62c/128/index_files/0.22174693971202628.png)

 

## **2、安装步骤**

### （1）上传压缩包并解压

![img](file:///D:/Documents/My Knowledge/temp/77da5ff1-2fab-4ddf-8005-54f576cff62c/128/index_files/13677997-0503-4c1c-9ef6-fab248f1899f.png)

```shell
tar -xzvf apache-activemq-5.16.0-bin.tar.gz
```

### （2）在/etc/init.d/目录增加增加activemq文件

```shell
cd /etc/init.d/
vi activemq
```

注意：将下面内容全部复制。 要先安装jdk，在下面配置jdk的安装目录。

```shell
#!/bin/sh
#
# /etc/init.d/activemq
# chkconfig: 345 63 37
# description: activemq servlet container.
# processname: activemq 5.16.0
 
# Source function library.
#. /etc/init.d/functions
# source networking configuration.
#. /etc/sysconfig/network
 
export JAVA_HOME=/home/jdk/jdk1.8.0_212
export CATALINA_HOME=/home/activemq/apache-activemq-5.16.0
 
case $1 in
    start)
        sh $CATALINA_HOME/bin/activemq start
    ;;
    stop)
        sh $CATALINA_HOME/bin/activemq stop
    ;;
    status)
        sh $CATALINA_HOME/bin/activemq status
    ;;
    restart)
        sh $CATALINA_HOME/bin/activemq stop
        sleep 1
        sh $CATALINA_HOME/bin/activemq start
    ;;
 
esac
exit 0
```



### （3）对activemq文件授予权限 

```shell
chmod 777 activemq
```

### （4）设置开机启动并启动activemq

```shell
chkconfig activemq on
service activemq start
```

### （5）访问activemq管理页面地址

http://IP地址:8161/  

访问成功，ActiveMQ安装完毕。默认用户名密码为：admin/admin



### （6）其他

#### 查看activemq状态

```shell
service activemq status
```



#### 重启activemq服务 

```shell
service activemq restart
```



#### 关闭activemq服务 

```shell
service activemq stop
```



#### 设置开机启动或不启动activemq服务

```shell
chkconfig activemq on
chkconfig activemq off
```



#### 如果ip地址无法访问，请配置防火墙端口

```shell
firewall-cmd --zone=public --add-port=8161/tcp --permanent #开放8161端口，ActiveMQ采用8161端口提供管理控制台服务
firewall-cmd --zone=public --add-port=61616/tcp --permanent #开放61616端口，ActiveMQ采用61616端口提供JMS服务
firewall-cmd --reload # 开启或关闭端口需要重启，重启后配置立即生效
```

如果该服务器同时安装了RabbitMQ，那么启动时会提示端口5672被占用，进入conf目录，打开配置文件activemq.xml文件，修改5672端口为5673



#### activemq 默认只能当前服务器访问，如果需要外部访问，需要修改jetty.xml文件

```shell
 <bean id="jettyPort" class="org.apache.activemq.web.WebConsolePort" init-method="start">
             <!-- the default port number for the web console -->
        <property name="host" value="192.168.1.235"/>  // 将 127.0.0.1 修改为外网IP即可访问
        <property name="port" value="8161"/>
</bean>
```





## 3、因为主机名不符合规范导致无法启动activemq



首先翻译一下这个异常，就是：主机名中包含非法字符，那么非法字符是什么呢？是“_”下划线；

那么解决办法就很简单了，改主机名：

1、方法一使用hostnamectl命令

```shell
hostnamectl set-hostname  主机名
```



2、方法二：修改配置文件 /etc/hostname 保存退出

修改完成之后重启即可，这里我使用的是方法一：

```shell
hostnamectl set-hostname  activemq
```

重启后效果：![img](file:///D:/Documents/My Knowledge/temp/77da5ff1-2fab-4ddf-8005-54f576cff62c/128/index_files/0.6188016956154933.png)

然后启动ActiveMQ并查看状态：

![img](file:///D:/Documents/My Knowledge/temp/77da5ff1-2fab-4ddf-8005-54f576cff62c/128/index_files/0.33387210007171125.png)

![img](file:///D:/Documents/My Knowledge/temp/77da5ff1-2fab-4ddf-8005-54f576cff62c/128/index_files/0.10187061049705386.png)

来源： https://blog.csdn.net/qq_39056805/article/details/80749337



## **4、启动时指定日志输出文件（重要）**

activemq日志默认的位置是在：%activemq安装目录%/data/activemq.log

这是我们启动时指定日志输出文件：

```shell
service activemq start  >  /home/activemq/activemq.log
```

![img](file:///D:/Documents/My Knowledge/temp/77da5ff1-2fab-4ddf-8005-54f576cff62c/128/index_files/dadbcf9c-3fcc-460b-9bda-3b3e1fd400df.png)

## **5、**查看程序启动是否成功的3种方式（通用）

方式1：查看进程

```shell
ps -ef|grep activemq
```

![img](file:///D:/Documents/My Knowledge/temp/77da5ff1-2fab-4ddf-8005-54f576cff62c/128/index_files/0.8684364558438631.png)

方式2：查看端口是否被占用

```shell
netstat -anp|grep 61616
```

![img](file:///D:/Documents/My Knowledge/temp/77da5ff1-2fab-4ddf-8005-54f576cff62c/128/index_files/0.7069237512834619.png)

方式3：查看端口是否被占用

```shell
lsof -i:61616
```

![img](file:///D:/Documents/My Knowledge/temp/77da5ff1-2fab-4ddf-8005-54f576cff62c/128/index_files/0.35033704965742957.png)

 

# 二、ActiveMQ控制台

## 1、访问activemq管理页面地址：http://IP地址:8161/  

账户admin 密码admin

## 2、进入

![img](file:///D:/Documents/My Knowledge/temp/77da5ff1-2fab-4ddf-8005-54f576cff62c/128/index_files/0.3884278061799783.png)



## 3、其他

ActiveMQ采用61616端口提供JMS服务。

ActiveMQ采用8161端口提供管理控制台服务。