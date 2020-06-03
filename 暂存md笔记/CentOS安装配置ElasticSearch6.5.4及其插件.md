# 一、安装jdk1.8

为知笔记地址：[Linux 下安装JDK1.8/切换使用两个版本的JDK](wiz://open_document?guid=b3ac50bc-be46-4613-a029-9f29468c303d&kbguid=&private_kbguid=9e15e816-792d-4528-9d21-a849cc4117d5)

GitHub地址：[https://github.com/wangliu1102/StudyNotes/tree/master/Linux/%E5%AE%89%E8%A3%85](https://github.com/wangliu1102/StudyNotes/tree/master/Linux/安装)

# 二、安装配置ElasticSearch

## 1、下载elasticsearch6.5.4

官网地址：https://www.elastic.co/cn/downloads/past-releases#elasticsearch（对应版本）

百度云：

> > 链接：https://pan.baidu.com/s/1gLVO1KTOyulsDmRjJ0pI0Q 
>
> > 提取码：lbys

## 2、安装步骤(版本与下载版本不一致，忽略即可，步骤是对的)

### （1）上传安装包到服务器

使用WinSCP上传ElasticSearch相关安装包到服务器。这里Elastic Stack目录包含空格，安装ik分词器会报错。修改目录为ElasticStack。下面所有关于这个目录的命令可以把空格去掉。

![img](file:///D:/Documents/My Knowledge/temp/f166f7be-1579-464a-9d6d-519c6fe778b0/128/index_files/2c3ab0e6-07b6-4d9a-9676-0393e6f44dc8.png)

![img](file:///D:/Documents/My Knowledge/temp/f166f7be-1579-464a-9d6d-519c6fe778b0/128/index_files/02472ccd-0830-468b-be1e-19f83bf601d5.png)

![img](file:///D:/Documents/My Knowledge/temp/f166f7be-1579-464a-9d6d-519c6fe778b0/128/index_files/7f5633ac-49be-43b8-b790-b5f0ac6f70ec.png)

### （2）解压elasticsearch-6.5.4.tar.gz

```shell
cd /home/elasticsearch6.5.4/ElasticStack/
tar -zxvf elasticsearch-6.5.4.tar.gz
```

![img](file:///D:/Documents/My Knowledge/temp/f166f7be-1579-464a-9d6d-519c6fe778b0/128/index_files/f2e66353-bb55-4d12-9fb5-b29072f53635.png)

### （3）在bin目录下，用./elasticsearch启动一下

```shell
cd elasticsearch-6.5.4/bin/
./elasticsearch
```

![img](file:///D:/Documents/My Knowledge/temp/f166f7be-1579-464a-9d6d-519c6fe778b0/128/index_files/48f4ad17-545f-4a28-ba39-b5b256d63396.png)

会发现报错，错误的内容是： can not run elasticsearch as root，也就是说不能使用root用户去启动elasticsearch，因为elasticsearch内置的安全性。

解决方式有两种：

```shell
 ./elasticsearch -Des.insecure.allow.root=true
 
 或者
 
 修改bin/elasticsearch,加上ES_JAVA_OPTS属性：ES_JAVA_OPTS="-Des.insecure.allow.root=true"
```

我们使用第一种：./elasticsearch -Des.insecure.allow.root=true

![img](file:///D:/Documents/My Knowledge/temp/f166f7be-1579-464a-9d6d-519c6fe778b0/128/index_files/d34beff8-c100-49af-a73c-e2f0b986f720.png)

此处又会报错：D is not a recognized option

![img](file:///D:/Documents/My Knowledge/temp/f166f7be-1579-464a-9d6d-519c6fe778b0/128/index_files/e9169618-a64a-43ff-bb0a-60aec53c8283.jpg)

## 3、创建用户组和用户

这里是建议单独创建一个用户用于elasticsearch。

```shell
groupadd eszu #创建一个用户组
useradd es -g eszu -p 123456 #在这个用户组下创建一个用户，并且密码为123456
```

给解压后的es目录授权：

```shell
chown -R es:eszu /home/elasticsearch6.5.4/ElasticStack/elasticsearch-6.5.4
# -R  不仅显示指定目录下的文件和子目录信息，而且还递归地显示子目录下的文件和子目录信息，也就是说elasticsearch-6.3.2目录下的所有文件都属于eszu组下的es用户
```

root用户切换到es用户：

```shell
su es
cd /home/elasticsearch6.5.4/ElasticStack/elasticsearch-6.5.4/bin
./elasticsearch
```

centos7.6无警告，centos6.8出现如下警告，但是无error，正常现象。

![img](file:///D:/Documents/My Knowledge/temp/f166f7be-1579-464a-9d6d-519c6fe778b0/128/index_files/8cec274f-5aea-4052-b161-2c956942d64e.png)

用终端验证：

```shell
curl 127.0.0.1:9200
```

![img](file:///D:/Documents/My Knowledge/temp/f166f7be-1579-464a-9d6d-519c6fe778b0/128/index_files/5d37f9a1-3940-49a6-9230-eb7c8c039ea2.png)

## 4、设置通过主机访问ElasticSearch

### （1）通过ifconfig查询到自己的ip地址

```shell
ifconfig 或 ip addr
```

![img](file:///D:/Documents/My Knowledge/temp/f166f7be-1579-464a-9d6d-519c6fe778b0/128/index_files/aa51ac8d-fc15-4f77-808b-67d30a4989d1.png)

### （2）编辑elasticsearch.yml配置文件

```shell
cd /home/elasticsearch6.5.4/ElasticStack/elasticsearch-6.5.4/config
```

![img](file:///D:/Documents/My Knowledge/temp/f166f7be-1579-464a-9d6d-519c6fe778b0/128/index_files/b364094b-f0c5-401a-8015-b0157699e324.png)

 

```shell
vim elasticsearch.yml

# 输入i，进入插入（编辑）模式。

# 将network.host的注释去掉，ip地址改成自己的linux的虚拟机ip地址

# http.post的注释去掉，换成9200端口

# ESC
# ：wq!保存退出
```



![img](file:///D:/Documents/My Knowledge/temp/f166f7be-1579-464a-9d6d-519c6fe778b0/128/index_files/6d980483-fd16-415a-ba7a-7956d7b25281.png)

再次进入 elasticsearch安装目录的bin目录下 ， ./elasticsearch  启动elasticsearch，报错了。

```shell
su es
cd /home/elasticsearch6.5.4/ElasticStack/elasticsearch-6.5.4/bin
 ./elasticsearch
```

centos6.8会报如下4个错：

![img](file:///D:/Documents/My Knowledge/temp/f166f7be-1579-464a-9d6d-519c6fe778b0/128/index_files/20180822175841178.png)

centos7.6会报如下3个错：

![img](file:///D:/Documents/My Knowledge/temp/f166f7be-1579-464a-9d6d-519c6fe778b0/128/index_files/d82fff99-4b0d-468c-a2d0-f373399b56a3.png)

### （3）解决报错

#### ① **报错：max file descriptors [ 4096 ] for elasticsearch process 15 too low, increase to at least [ 65536 ]**

```shell
su root
cd /etc/security/
```

需要改下面两个，不过一个是文件，一个是目录.

![img](file:///D:/Documents/My Knowledge/temp/f166f7be-1579-464a-9d6d-519c6fe778b0/128/index_files/e6dae4cb-7983-4ccd-ace8-7aec298d618c.png)

用 vim limits.conf 进入limits.conf进行编辑，修改如下,然后保存退出：

```shell
es soft nofile 65536
es hard nofile 65536
es soft nproc 4096
es hard nproc 4096

# 或

* soft nofile 65536
* hard nofile 131072
* soft nproc 2048
* hard nproc 4096
```

![img](file:///D:/Documents/My Knowledge/temp/f166f7be-1579-464a-9d6d-519c6fe778b0/128/index_files/391bb8dc-6387-4a3f-878b-2a206ee07b0f.png)

#### ② 报错：max number of threads [ 1024 ] for user [ es ] 15 too low, increase to at least [ 4096 ]

进入limits.d目录下，修改90-nproc.conf 文件（centos6.8是90-nproc.conf ，centos7.6是200-nproc.conf ）：

```shell
cd limits.d/
```

![img](file:///D:/Documents/My Knowledge/temp/f166f7be-1579-464a-9d6d-519c6fe778b0/128/index_files/994ca21f-73b9-4c3b-87d0-0f2db944d1e0.png)

```shell
vim 20-nproc.conf
```

![img](file:///D:/Documents/My Knowledge/temp/f166f7be-1579-464a-9d6d-519c6fe778b0/128/index_files/09da7edd-9cfb-4a56-835f-9acfdff28c5f.png)

```shell
# 或者 
* soft nproc 4096
```

保存退出。

#### ③ 报错：max virtual mefnory areas vm.max_ map_count [ 65530 ] 15 too low, increase toa t least [ 262144 ]

退回到etc目录下：

```shell
cd /etc
vim sysctl.conf

#在配置文件最后一行加上：
vm.max_map_count=655360
```

![img](file:///D:/Documents/My Knowledge/temp/f166f7be-1579-464a-9d6d-519c6fe778b0/128/index_files/7f3110b8-3f55-4fa3-bbf3-453724073569.png)

保存退出。

```shell
#配置生效
sysctl -p 
```



#### ④ 报错：system call filters failed to install; check the logs and fix your configuration or disable system call filters at your own risk

问题原因：因为Centos6不支持SecComp，而ES6.32默认bootstrap.system_call_filter为true进行检测，所以导致检测失败，失败后直接导致ES不能启动。

解决方法：在elasticsearch.yml中配置bootstrap.system_call_filter为false，注意要在Memory下面:

```shell
bootstrap.system_call_filter: false
```



![img](file:///D:/Documents/My Knowledge/temp/f166f7be-1579-464a-9d6d-519c6fe778b0/128/index_files/20180822181813783.png)

![img](file:///D:/Documents/My Knowledge/temp/f166f7be-1579-464a-9d6d-519c6fe778b0/128/index_files/20180822181734719.png)

保存退出。



### （4）验证

reboot重启linux系统。

再次进入 elasticsearch安装目录的bin目录下， ./elasticsearch  启动elasticsearch。

```shell
su es
cd /home/elasticsearch6.5.4/ElasticStack/elasticsearch-6.5.4/bin
 ./elasticsearch
```



打开linux系统，使用root用户，关闭防火墙或者开放端口9200和9300。

CentOS6.8防火墙配置：

```shell
# 查看防火墙状态
service iptables status

# 停止防火墙(立即生效，开机重启,会重新打开) 
service iptables stop

# 永久关闭防火墙（关机重启才会生效） 
chkconfig iptables off

或者
vim /etc/sysconfig/iptables
# 加入如下代码，比着两葫芦画瓢 :)
-A INPUT -m state --state NEW -m tcp -p tcp --dport 9200 -j ACCEPT
-A INPUT -m state --state NEW -m tcp -p tcp --dport 9300 -j ACCEPT

# 保存退出后重启防火墙
service iptables restart

```

```shell
firewall-cmd --list-all #查看所有
firewall-cmd --list-ports #查看所有开放的端口

# 关闭防火墙
systemctl stop firewalld
# 禁止firewall开机启动
systemctl disable firewalld

或者

# 设置开放的端口号
firewall-cmd --zone=public --add-port=9200/tcp --permanent 
firewall-cmd --zone=public --add-port=9300/tcp --permanent 

# 开启或关闭端口需要重启，重启后配置立即生效
firewall-cmd --reload 
```

在主机windows系统的浏览器去访问：

![img](file:///D:/Documents/My Knowledge/temp/f166f7be-1579-464a-9d6d-519c6fe778b0/128/index_files/82459ef5-82e0-4e44-8103-35f68547e80f.png)

# 三、linux中设置脚本实现elasticsearch自启动

① 在/etc/init.d目录下创建elasticsearch文件：

```shell
cd /etc/init.d
vim elasticsearch
```

脚本如下：

```shell
#!/bin/sh
#chkconfig: 2345 80 05
#description: elasticsearch
 
export JAVA_HOME=/usr/java/jdk1.8.0_212
export JAVA_BIN=/usr/java/jdk1.8.0_212/bin
export PATH=$PATH:$JAVA_HOME/bin
export CLASSPATH=.:$JAVA_HOME/lib/dt.jar:$JAVA_HOME/lib/tools.jar
export JAVA_HOME JAVA_BIN PATH CLASSPATH
 
case "$1" in
start)
su es<<!
cd /home/elasticsearch6.5.4/ElasticStack/elasticsearch-6.5.4
./bin/elasticsearch -d
!
echo "elasticsearch startup"
;;
stop)
es_pid=`ps aux|grep elasticsearch | grep -v 'grep elasticsearch' | awk '{print $2}' | sed -n '1p'`
kill -9 $es_pid
echo "elasticsearch stopped"
;;
restart)
es_pid=`ps aux|grep elasticsearch | grep -v 'grep elasticsearch' | awk '{print $2}'| sed -n '1p' `
kill -9 $es_pid
echo "elasticsearch stopped"
su es<<!
cd /home/elasticsearch6.5.4/ElasticStack/elasticsearch-6.5.4
./bin/elasticsearch -d
!
echo "elasticsearch startup"
;;
*)
echo "start|stop|restart"
;;
esac
exit $?
```

修改你自定义的elasticsearch安装目录路径以及JDK的安装目录，还有非root账户的配置。

```shell
export JAVA_HOME=/usr/java/jdk1.8.0_212 # jdk安装目录
export JAVA_BIN=/usr/java/jdk1.8.0_212/bin # jdk安装目录

su es<<! #非root账户
cd /home/elasticsearch6.5.4/ElasticStack/elasticsearch-6.5.4 #elasticsearch安装目录路径
```

② 保存退出，赋予该脚本执行权限

```shell
chmod +x elasticsearch 
```

③ 将elasticsearch添加到开机启动任务

```shell
chkconfig --add elasticsearch
```



# 四、插件安装

## 1、elasticsearch-head插件安装

elasticsearch-head 是一个与Elastic集群（Cluster）相交互的Web前台。

elasticsearch-head的主要作用：

- 它展现ES集群的拓扑结构，并且可以通过它来进行索引（Index）和节点（Node）级别的操作；
- 它提供一组针对集群的查询API，并将结果以json和表格形式返回；
- 它提供一些快捷菜单，用以展现集群的各种状态。

head提供了4种安装方式：

-   源码安装，通过npm run start启动（不推荐），这里介绍了这种方式的安装。
-   通过docker安装（推荐）。
-   通过chrome插件安装（推荐），步骤（2）下载安装elasticsearch-head 中有介绍。
-   通过ES的plugin方式安装（不推荐）。

通过docker安装：

```shell
#拉取镜像
docker pull mobz/elasticsearch-head:5
#创建容器
docker create --name elasticsearch-head -p 9100:9100 mobz/elasticsearch-head:5
#启动容器
docker start elasticsearch-head

# 注意：
# 由于前后端分离开发，所以会存在跨域问题，需要在服务端做CORS的配置，如下：
# vim elasticsearch.yml
# http.cors.enabled: true http.cors.allow-origin: "*"
# 通过chrome插件的方式安装不存在该问题。
```



### （1）安装node.js

elasticsearch-head 需要安装node环境。

#### ① 下载node.js

官网下载：https://nodejs.org/en/download/

百度云下载：

​     链接：https://pan.baidu.com/s/10-pQaQ2VtuwEefy1fhOLMg 

​    提取码：mdhf

#### ② 解压缩文件包

```shell
tar xvf node-v10.15.0-linux-x64.tar.xz
```

#### ③ node 环境配置

```shell
vim /etc/profile

#在最下面加入
# node
export NODE_HOME=/home/elasticsearch6.5.4/node-v10.15.0-linux-x64 #解压缩后文件夹路径
export PATH=$PATH:$NODE_HOME/bin  
export NODE_PATH=$NODE_HOME/lib/node_modules

# 使配置文件生效
source /etc/profile   

#  查看 Node.js 是否安装成功
node -v
```

### （2）下载安装elasticsearch-head

安装elasticsearch-head需要外网环境。如果服务器没有外网，可以在本机谷歌浏览器插件安装，然后在本机谷歌浏览器中访问：

![img](file:///D:/Documents/My Knowledge/temp/f166f7be-1579-464a-9d6d-519c6fe778b0/128/index_files/defea06d-1d59-415d-81ae-2c1d26c76a8b.jpg)

![img](file:///D:/Documents/My Knowledge/temp/f166f7be-1579-464a-9d6d-519c6fe778b0/128/index_files/e9d69965-8dba-448c-bd0e-85aca737a3eb.png)

官方 GitHub 地址：https://github.com/mobz/elasticsearch-head

百度云下载：

​    链接：https://pan.baidu.com/s/1a3g4a2jFC_w6Uyvrj8xuCQ 

​    提取码：rvhx

下载成功后，将elasticsearch-head-master.zip压缩包拷贝到linux上，使用如下命令解压到指定目录下：

```shell
yum install -y unzip zip
unzip -d /home/elasticsearch6.5.4/elasticsearch-head-master.zip
```

然后cd 进入解压的head目录下：

```shell
cd /home/elasticsearch6.5.4/elasticsearch-head-master
#注意需要有node环境，如果没有，安装node环境就好了
npm install
```

### **（3）修改elasticsearch-head/Gruntfile.js**

找到下面connect属性，新增hostname: ‘*’

```shell
connect: {
   server: {
      options: {
          hostname: '*',
          port: 9100,
          base: '.',
          keepalive: true
      }
}
```



### **（4）修改elasticsearch配置文件使其允许跨域**

```shell
# 进入config目录
cd /home/elasticsearch6.5.4/ElasticStack/elasticsearch-6.5.4/config

#编辑elasticsearch.yml文件
vim elasticsearch.yml        
#添加如下内容
http.cors.enabled: true
http.cors.allow-origin: "*"
```



![img](file:///D:/Documents/My Knowledge/temp/f166f7be-1579-464a-9d6d-519c6fe778b0/128/index_files/1e01ca14-3219-4772-aa39-e84024d76356.png)

重启elasticsearch。 然后就可以执行elasticsearch-head了。进入elasticsearch-head目录，执行命令：

```shell
/etc/init.d/elasticsearch start

cd /home/elasticsearch6.5.4/elasticsearch-head-master
npm run start
```

### （5）验证

防火墙关闭或者开启9100端口。

CentOS6.8防火墙配置：

```shell
# 查看防火墙状态
service iptables status

# 停止防火墙(立即生效，开机重启,会重新打开) 
service iptables stop

# 永久关闭防火墙（关机重启才会生效） 
chkconfig iptables off

或者
vim /etc/sysconfig/iptables
# 加入如下代码，比着两葫芦画瓢 :)
-A INPUT -m state --state NEW -m tcp -p tcp --dport 9100 -j ACCEPT

# 保存退出后重启防火墙
service iptables restart

```



```shell
firewall-cmd --list-all #查看所有
firewall-cmd --list-ports #查看所有开放的端口

# 关闭防火墙
systemctl stop firewalld
# 禁止firewall开机启动
systemctl disable firewalld

或者

# 设置开放的端口号
firewall-cmd --zone=public --add-port=9100/tcp --permanent 

# 开启或关闭端口需要重启，重启后配置立即生效
firewall-cmd --reload 
```

打开浏览器，输入网址（虚拟机ip:9100）进入即可。

![img](file:///D:/Documents/My Knowledge/temp/f166f7be-1579-464a-9d6d-519c6fe778b0/128/index_files/88ee85ea-a23d-435a-92fb-b22dafa36c04.png)

### （6）elasticsearch-head插件后台运行命令

```shell
# cd 到 elasticsearch-head安装目录
cd /home/elasticsearch6.5.4/elasticsearch-head-master

# 执行
nohup npm start &
```

### **（7）解决安装报错**

#### **① npm istall 报错**：phantomjs-prebuilt@2.1.16 install: `node install.js`![img](file:///D:/Documents/My Knowledge/temp/f166f7be-1579-464a-9d6d-519c6fe778b0/128/index_files/f74f14c9-9c20-411f-8e27-ca276d387bdb.png)解决：

 

```shell
[root@localhost elasticsearch-head-master]# npm install phantomjs-prebuilt@2.1.16 --ignore-scripts
```

#### **② npm run start** **会出现缺失node module错误**

![img](file:///D:/Documents/My Knowledge/temp/f166f7be-1579-464a-9d6d-519c6fe778b0/128/index_files/b4e3fb9e-4dd7-486b-a9e1-566ce5eeee50.png)

需要安装这些缺失的node modules, 注意需要回到elasticsearch_head目录下安装:

```shell
[root@localhost elasticsearch-head-master]# npm install grunt-contrib-clean grunt-contrib-concat grunt-contrib-watch grunt-contrib-connect grunt-contrib-copy grunt-contrib-jasmine
```

## 2、kibana插件安装

Kibana是一个针对Elasticsearch的开源分析及可视化平台，用来搜索、查看交互存储在Elasticsearch索引中的数据。使用Kibana，可以通过各种图表进行高级数据分析及展示，是对elasticsearch搜索引擎进行有效管理的工具；

### （1）下载kibana压缩包

官网下载：https://www.elastic.co/cn/downloads/past-releases#kibana （找到与es对应版本）

百度云下载：

​    链接：https://pan.baidu.com/s/15aQvA47i47K-wttBTraI8w 

​    提取码：rlk8

### （2）解压

```shell
cd /home/elasticsearch6.5.4/ElasticStack/
tar -zxvf kibana-6.5.4-linux-x86_64.tar.gz
```

### （3）修改配置文件

找到config目录下的kibana.yml文件，然后进行配置，具体的参考配置如下，为了方便下面的配置只设置了本机地址和es访问连接地址，有其他需求的话可继续配置：

```shell
cd /home/elasticsearch6.5.4/ElasticStack/kibana-6.5.4-linux-x86_64/config/
vim kibana.yml
```

![img](file:///D:/Documents/My Knowledge/temp/f166f7be-1579-464a-9d6d-519c6fe778b0/128/index_files/9495b71a-26d9-4d52-8da3-21c2eb1198ef.png)

以上文件配置完了之后，那么就可以去启动kibana了，进入bin目录，执行命令语句： ./kibana & 后台启动；

```shell
cd /home/elasticsearch6.5.4/ElasticStack/kibana-6.5.4-linux-x86_64/bin/
./kibana &
```

启动之后，怎么查看kibana的进程呢，使用传统的 ps aux|grep kibana 命令是无法查看kibana的进程的，需要执行命令语句：

```shell
# 安装psmisc，不然报bash: fuser: 未找到命令
yum install psmisc

fuser -n tcp 5601
```

查看到kibana的进程之后，说明启动成功了，如果想要杀死进程，可直接执行 

```shell
 kill -9 进程号 
```



### （4）验证

防火墙关闭或者开启5601端口。

CentOS6.8防火墙配置：

```shell
# 查看防火墙状态
service iptables status

# 停止防火墙(立即生效，开机重启,会重新打开) 
service iptables stop

# 永久关闭防火墙（关机重启才会生效） 
chkconfig iptables off

或者
vim /etc/sysconfig/iptables
# 加入如下代码，比着两葫芦画瓢 :)
-A INPUT -m state --state NEW -m tcp -p tcp --dport 5601 -j ACCEPT

# 保存退出后重启防火墙
service iptables restart

```



```shell
firewall-cmd --list-all #查看所有
firewall-cmd --list-ports #查看所有开放的端口

# 关闭防火墙
systemctl stop firewalld
# 禁止firewall开机启动
systemctl disable firewalld

或者

# 设置开放的端口号
firewall-cmd --zone=public --add-port=5601/tcp --permanent 

# 开启或关闭端口需要重启，重启后配置立即生效
firewall-cmd --reload 
```

打开浏览器，输入网址（虚拟机ip:5601）进入即可。

![img](file:///D:/Documents/My Knowledge/temp/f166f7be-1579-464a-9d6d-519c6fe778b0/128/index_files/1f65ee77-bf3f-4023-9a2a-4701924a4c4e.png)

![img](file:///D:/Documents/My Knowledge/temp/f166f7be-1579-464a-9d6d-519c6fe778b0/128/index_files/6cd83add-77ce-4f75-bf99-b95fc51b16d4.png)

### （5）**kibana插件后台运行**

cd 到kibana安装目录下的bin目录中

```shell
./kibana &
```



注意：这时加上了&虽然执行了后台启动，但是还是有日志打印出来，使用ctrl+c可以退出。 

但是如果直接关闭了Xshell,这时服务也会停止，访问http://192.168.1.212:5601就失败了。

解决方法： 

执行了命令后，不使用ctrl+c去退出日志， 而是使用

```shell
exit;
```

这样即使关闭了shell窗口kibana服务也不会挂了。

## 3、ik分词插件安装

IK Analyzer是一个开源的，基于Java语言开发的轻量级的中文分词工具包。

### （1）下载压缩包

官网下载：https://github.com/medcl/elasticsearch-analysis-ik/releases （找到与es对应版本）

百度云下载：

​    链接：https://pan.baidu.com/s/1KgInCjY7x91OtJdJttsLNA 

​    提取码：32zb

### （2）解压zip文件到es安装目录下的plugins下

```shell
cd /home/elasticsearch6.5.4/ElasticStack/elasticsearch-6.5.4/plugins/
mkdir ik
unzip -d /home/elasticsearch6.5.4/ElasticStack/elasticsearch-6.5.4/plugins/ik/ /home/elasticsearch6.5.4/plugins/elasticsearch-analysis-ik-6.5.4.zip
```

![img](file:///D:/Documents/My Knowledge/temp/f166f7be-1579-464a-9d6d-519c6fe778b0/128/index_files/bbf16be2-0fdd-46db-803a-891b34a04428.png)

### （3）验证安装

重启es,然后在elasticsearch-head中执行如下图的验证：

```shell
/etc/init.d/elasticsearch stop
/etc/init.d/elasticsearch start

_analyze
{
  "analyzer": "ik_max_word",
  "text": "我不会自动同意你的意见的"
}
```

![img](file:///D:/Documents/My Knowledge/temp/f166f7be-1579-464a-9d6d-519c6fe778b0/128/index_files/3057af1f-2d8f-4ad1-b92d-14f65cd38ddc.png)

报错的话，是因为目录下有空格造成的，修改Elastic Stack目录：

```
[root@localhost elasticsearch6.5.4]# mv Elastic\ Stack/ ElasticStack

#修改/etc/init.d/elasticsearch脚本中elasticsearch的安装目录
# 然后重启
/etc/init.d/elasticsearch stop
/etc/init.d/elasticsearch start
```

![img](file:///D:/Documents/My Knowledge/temp/f166f7be-1579-464a-9d6d-519c6fe778b0/128/index_files/e8a529cf-973e-47fc-8121-02892e0f36d9.png)

## 4、elasticsearch-sql数据库插件安装

elasticsearch-SQL可以用sql查询Elasticsearch。

elasticsearch-sql实现的功能：

> - 1)插件式的安装；
> - 2)SQL查询；
> - 3)超越SQL之外的查询；
> - 4)对JDBC方式的支持；

### （1）下载

官网下载：https://github.com/NLPchina/elasticsearch-sql/releases （找到与es对应版本）

百度云下载：

​    链接：https://pan.baidu.com/s/1OjOqaJiP63xV3BM8_jH15Q 

​    提取码：cwa9

### （2）解压zip文件到es安装目录下的plugins下

```shell
cd /home/elasticsearch6.5.4/ElasticStack/elasticsearch-6.5.4/plugins/
mkdir sql
unzip -d /home/elasticsearch6.5.4/ElasticStack/elasticsearch-6.5.4/plugins/sql/ /home/elasticsearch6.5.4/plugins/elasticsearch-sql-6.5.4.0.zip
```

### （3）验证安装

![img](file:///D:/Documents/My Knowledge/temp/f166f7be-1579-464a-9d6d-519c6fe778b0/128/index_files/a348e1c9-5da7-4abe-b3ae-e6800b27d5d4.png)

重启es,然后在elasticsearch-head中执行如下图的验证：

```shell
/etc/init.d/elasticsearch stop
/etc/init.d/elasticsearch start

#新建索引people后
#再在kibana的开发者工具中执行如下：
PUT /people/man/1
{
  "name":"张三",
  "age":"24"
}

# sql查询，如下图
_sql?sql=SELECT * FROM people
```

![img](file:///D:/Documents/My Knowledge/temp/f166f7be-1579-464a-9d6d-519c6fe778b0/128/index_files/31482cdd-0948-4bac-9fec-044ecc2f5ea7.png)

### （4）可视化前端界面es-sql-site-standalone

> 百度云下载：

> > > 链接：https://pan.baidu.com/s/1XiDMpMz8AYt-KZNiEc-iuA 
> >
> > > 提取码：7uyo

> 下载压缩包上传后，解压：

```shell
[root@localhost elasticsearch6.5.4]# mkdir es-sql-site-standalone
[root@localhost elasticsearch6.5.4]# unzip -d es-sql-site-standalone es-sql-site-standalone.zip
```

![img](file:///D:/Documents/My Knowledge/temp/f166f7be-1579-464a-9d6d-519c6fe778b0/128/index_files/5fbaec7f-6c50-4c9a-aa97-3d77bf077984.png)

 

```shell
cd site-server
npm install express --save

#修改端口为8888
vim site_configuration.json

# 启动运行
node node-server.js &
```

```shell
# centos6.8设置防火墙
vim /etc/sysconfig/iptables
# 加入如下代码，比着两葫芦画瓢 :)
-A INPUT -m state --state NEW -m tcp -p tcp --dport 8888 -j ACCEPT
# 保存退出后重启防火墙
service iptables restart

# centos7.6设置防火墙
# 设置开放的端口号
firewall-cmd --zone=public --add-port=8888/tcp --permanent 
# 开启或关闭端口需要重启，重启后配置立即生效
firewall-cmd --reload 
```



> ![img](file:///D:/Documents/My Knowledge/temp/f166f7be-1579-464a-9d6d-519c6fe778b0/128/index_files/d06c4f89-20d4-4784-944e-2f2209a51688.png)

## 5、bigdesk插件安装

bigdesk是elasticsearch的一个集群监控工具，可以通过它来查看es集群的各种状态，如：cpu、内存使用情况，索引数据、搜索情况，http连接数等。

### （1）下载

官网下载：https://github.com/hlstudio/bigdesk

百度云下载：

> > 链接：https://pan.baidu.com/s/1PvGVM_aZnNDYx6RSBrP4bQ 
>
> > 提取码：zqtt 

### （2）解压安装

```shell
#解压
unzip bigdesk-master.zip

#进入到sit目录
cd /bigdesk-master/_site

#启动web服务器
#监听端口号是 8000
python -m SimpleHTTPServer &
```

```shell
# centos6.8设置防火墙
vim /etc/sysconfig/iptables
# 加入如下代码，比着两葫芦画瓢 :)
-A INPUT -m state --state NEW -m tcp -p tcp --dport 8000 -j ACCEPT
# 保存退出后重启防火墙
service iptables restart

# centos7.6设置防火墙
# 设置开放的端口号
firewall-cmd --zone=public --add-port=8000/tcp --permanent 
# 开启或关闭端口需要重启，重启后配置立即生效
firewall-cmd --reload 
```

### （3）配置elasticsearch

```shell
设定远程可以http访问elastic
vim config/elasticsearch.yml

#添加下面配置
#network.host 绑定ip
#http.cors.enabled 允许http
#http.cors.allow-origin 允许访问的ip * 表示任何ip都可以访问
network.host: 192.168.1.218
http.cors.enabled: true
http.cors.allow-origin: "*"
```

![img](file:///D:/Documents/My Knowledge/temp/f166f7be-1579-464a-9d6d-519c6fe778b0/128/index_files/26827e7d-3ae8-418e-9027-7e21834fc16c.png)

### （4）验证

![img](file:///D:/Documents/My Knowledge/temp/f166f7be-1579-464a-9d6d-519c6fe778b0/128/index_files/e6678271-3b20-441f-964a-26f4be8b2062.png)

# 五、elasticsearch 搭建集群及优化

## 1、elasticsearch节点角色

在生产环境下，如果不修改elasticsearch节点的角色信息，在高数据量，高并发的场景下集群容易出现脑裂等问题。 

默认情况下，elasticsearch集群中每个节点都有成为主节点的资格，也都存储数据，即双重角色。

由两个属性控制：node.master和node.data，默认情况下这两个属性的值都是true： 

\* **node.master**：表示节点是否具有成为主节点的资格，值为true并不意味着这个节点就是主节点，真正的主节点是由多个具有主节点资格的节点进行选举产生的。 

\* **node.data**：表示节点是否存储数据。

这两个属性可以有四种组合： 

### **（1）双重节点**

这种组合表示这个节点即有成为主节点的资格，又存储数据， 这个时候如果某个节点被选举成为了真正的主节点，那么他还要存储数据，这样对于这个节点的压力就比较大了。elasticsearch默认每个节点都是这样的配置，在测试环境下这样做没问题。实际工作中建议不要这样设置，这样相当于主节点和数据节点的角色混合到一块了。 

```shell
node.master: true 
node.data: true 
```

### **（2）数据节点**

这种组合表示这个节点没有成为主节点的资格，也就不参与选举，只会存储数据。这个节点我们称为data(数据)节点。在集群中需要单独设置几个这样的节点负责存储数据。后期提供存储和查询服务。 

```shell
node.master: false 
node.data: true
```

### **（3）主节点**

这种组合表示这个节点不会存储数据，有成为主节点的资格，可以参与选举，有可能成为真正的主节点。这个节点我们称为master节点 

```shell
node.master: true 
node.data: false
```

### **（4）客户端节点**

这种组合表示这个节点即不会成为主节点，也不会存储数据， 这个节点的意义是作为一个client(客户端)节点，主要是针对海量请求的时候可以进行负载均衡。 

```shell
node.master: false 
node.data: false 
```

默认情况下，每个节点都有成为主节点的资格，也会存储数据，还会处理客户端的请求。 在一个生产集群中我们可以对这些节点的职责进行划分。

建议集群中设置3台以上的节点作为master节点，这些节点只负责成为主节点，维护整个集群的状态。

再根据数据量设置一批data节点，这些节点只负责存储数据，后期提供建立索引和查询索引的服务，这样的话如果用户请求比较频繁，这些节点的压力也会比较大。

所以在集群中建议再设置一批client节点 ，这些节点只负责处理用户请求，实现请求转发，负载均衡等功能。 

```shell
master节点：普通服务器即可(CPU 内存 消耗一般) 
data节点：主要消耗磁盘，内存 
client节点：普通服务器即可(如果要进行分组聚合操作的话，建议这个节点内存也分配多一点)
```



## 2、轻量级集群设置节点建议

![img](file:///D:/Documents/My Knowledge/temp/f166f7be-1579-464a-9d6d-519c6fe778b0/128/index_files/d6c289ac-15fc-410c-bc77-2050c8235a8a.png)

## 3、3节点集群搭建

按照上述步骤在l另外两台虚拟机安装好elasticsearch及其插件。

```shell
主节点所在机器：192.168.1.128
主从节点所在机器：192.168.1.129
从节点所在机器：192.168.1.130

# 其实如果节点很少，这里最好设置为主从节点，让三节点都存储数据，并且有两个主节点
# 即两个主从节点，一个从节点

# 下面是一个主节点，一个主从节点，一个从节点的安装配置
```

分别修改elasticsearch/config下的elasticsearch.yml文件内容。

### （1）主节点：192.168.1.128

```shell
#集群名称，所有机器上保持一致
cluster.name: ahhs6.5.4

#节点名称
node.name: node-master1

#设置成主节点
node.master: true
node.data: false

#主机ip，虚拟机设置的ip地址
network.host: 192.168.1.128

#http端口，默认9200
http.port: 9200

# 是否支持跨域访问资源
http.cors.enabled: true
# 允许访问资源的类型
http.cors.allow-origin: "*"
 
#集群节点ip或者主机
# 配置单播Ping的IP地址
discovery.zen.ping.unicast.hosts: ["192.168.1.128","192.168.1.129","192.168.1.130"]

# 为了防止脑裂，配置最小主节点个数（超过有效节点总数一半   total number of nodes / 2 + 1)
discovery.zen.minimum_master_nodes: 2
```



### （2）主从节点：192.168.1.129

```shell
#集群名称，所有机器上保持一致
cluster.name: ahhs6.5.4

#节点名称
node.name: node-master-slave1

#设置成主从节点
node.master: true
node.data: true

#主机ip，虚拟机设置的ip地址
network.host: 192.168.1.129

#http端口，默认9200
http.port: 9200

# 是否支持跨域访问资源
http.cors.enabled: true
# 允许访问资源的类型
http.cors.allow-origin: "*"
 
#集群节点ip或者主机
# 配置单播Ping的IP地址
discovery.zen.ping.unicast.hosts: ["192.168.1.128","192.168.1.129","192.168.1.130"]

# 为了防止脑裂，配置最小主节点个数（超过有效节点总数一半   total number of nodes / 2 + 1)
discovery.zen.minimum_master_nodes: 2
```

### （3）从节点：192.168.1.130

```shell
#集群名称，所有机器上保持一致
cluster.name: ahhs6.5.4

#节点名称
node.name: node-slave1

#设置成从节点
node.master: false
node.data: true

#主机ip，虚拟机设置的ip地址
network.host: 192.168.1.130

#http端口，默认9200
http.port: 9200

# 是否支持跨域访问资源
http.cors.enabled: true
# 允许访问资源的类型
http.cors.allow-origin: "*"
 
#集群节点ip或者主机
# 配置单播Ping的IP地址
discovery.zen.ping.unicast.hosts: ["192.168.1.128","192.168.1.129","192.168.1.130"]

# 为了防止脑裂，配置最小主节点个数（超过有效节点总数一半   total number of nodes / 2 + 1)
discovery.zen.minimum_master_nodes: 2
```

删除data和log文件夹后（防止报错），分别启动192.168.1.128和192.168.1.129和192.168.1.130机器下的elasticsearch。

注意：**先启动主节点的elasticsearch，再启动从节点的elasticsearch**。

![img](file:///D:/Documents/My Knowledge/temp/f166f7be-1579-464a-9d6d-519c6fe778b0/128/index_files/88e6f789-f0b5-461e-8bab-c8e90b4d1da3.png)

相关配置说明：

链接：https://pan.baidu.com/s/10bzfYXrEHDPLZ5sS7V5fmw 

提取码：tmz7

[![img](file:///D:/Documents/My Knowledge/temp/f166f7be-1579-464a-9d6d-519c6fe778b0/128/index_files/1742021468.png)](wiz://open_attachment?guid=ba2fce23-f3f7-43ea-b304-cfd41bbbe305)

[![img](file:///D:/Documents/My Knowledge/temp/f166f7be-1579-464a-9d6d-519c6fe778b0/128/index_files/1742021515.png)](wiz://open_attachment?guid=7fdd27f3-cef4-4a6c-907f-4217a3838239)

## 4、**Elasticsearch性能优化建议**

### （1）集群规划优化实践

#### ① 基于目标数据量规划集群

在业务初期，经常被问到的问题，要几个节点的集群，内存、CPU要多大，要不要SSD？

最主要的考虑点是：你的目标存储数据量是多大？可以针对目标数据量反推节点多少。

#### ② 要留出容量Buffer

注意：Elasticsearch有三个警戒水位线，磁盘使用率达到85%、90%、95%。

不同警戒水位线会有不同的应急处理策略。

这点，磁盘容量选型中要规划在内。控制在85%之下是合理的。

当然，也可以通过配置做调整。

#### ③ ES集群各节点尽量不要和其他业务功能复用一台机器

除非内存非常大。

举例：普通服务器，安装了ES+Mysql+redis，业务数据量大了之后，势必会出现内存不足等问题。

#### ④ 磁盘尽量选择SSD

Elasticsearch官方文档肯定推荐SSD，考虑到成本的原因。需要结合业务场景，如果业务对写入、检索速率有较高的速率要求，建议使用SSD磁盘。

阿里的业务场景，SSD磁盘比机械硬盘的速率提升了5倍。但要因业务场景而异。

#### ⑤ 内存配置要合理

官方建议：堆内存的大小是官方建议是：Min（32GB，机器内存大小/2）。

Medcl和wood大叔都有明确说过，不必要设置32/31GB那么大，建议：热数据设置：26GB，冷数据：31GB。

总体内存大小没有具体要求，但肯定是内容越大，检索性能越好。

经验值供参考：每天200GB+增量数据的业务场景，服务器至少要64GB内存。除了JVM之外的预留内存要充足，否则也会经常OOM。

#### ⑥ CPU核数不要太小

CPU核数是和ESThread pool关联的。和写入、检索性能都有关联。

建议：16核+。

#### ⑦ 超大量级的业务场景，可以考虑跨集群检索

除非业务量级非常大，例如：滴滴、携程的PB+的业务场景，否则基本不太需要跨集群检索。

#### ⑧ 集群节点个数无需奇数

ES内部维护集群通信，不是基于zookeeper的分发部署机制，所以，无需奇数。

但是discovery.zen.minimum_master_nodes的值要设置为：候选主节点的个数/2+1，才能有效避免脑裂。

#### ⑨ 节点类型优化分配

集群节点数：<=3，建议：所有节点的master：true， data：true。既是主节点也是路由节点。 

集群节点数：>3, 根据业务场景需要，建议：逐步独立出Master节点和协调/路由节点。

#### ⑩ 建议冷热数据分离

热数据存储SSD和普通历史数据存储机械磁盘，物理上提高检索效率。

### （2）索引优化实践

Mysql等关系型数据库要分库、分表。Elasticserach的话也要做好充分的考虑。

#### ① 设置多少个索引

建议根据业务场景进行存储。

不同通道类型的数据要分索引存储。举例：知乎采集信息存储到知乎索引；APP采集信息存储到APP索引。

#### ② 设置多少分片

建议根据数据量衡量。

经验值：建议每个分片大小不要超过30GB。

#### ③ 分片数设置

建议根据集群节点的个数规模，分片个数建议>=集群节点的个数。

5节点的集群，5个分片就比较合理。

注意：除非reindex操作，分片数是不可以修改的。

#### ④ 副本数设置

除非你对系统的健壮性有异常高的要求，比如：银行系统。可以考虑2个副本以上。否则，1个副本足够。

注意：副本数是可以通过配置随时修改的。

#### ⑤ 不要再在一个索引下创建多个type

即便你是5.X版本，考虑到未来版本升级等后续的可扩展性。

建议：一个索引对应一个type。6.x默认对应_doc，5.x你就直接对应type统一为doc。

#### ⑥ 按照日期规划索引

随着业务量的增加，单一索引和数据量激增给的矛盾凸显。按照日期规划索引是必然选择。

好处1：可以实现历史数据秒删。很对历史索引delete即可。注意：一个索引的话需要借助delete_by_query+force_merge操作，慢且删除不彻底。

好处2：便于冷热数据分开管理，检索最近几天的数据，直接物理上指定对应日期的索引，速度快的一逼！

操作参考：模板使用+rollover API使用。

#### ⑤ 务必使用别名

ES不像mysql方面的更改索引名称。使用别名就是一个相对灵活的选择。

### （3）数据模型优化实践

#### ① 不要使用默认的Mapping

默认Mapping的字段类型是系统自动识别的。其中：string类型默认分成：text和keyword两种类型。如果你的业务中不需要分词、检索，仅需要精确匹配，仅设置为keyword即可。

根据业务需要选择合适的类型，有利于节省空间和提升精度，如：浮点型的选择。

#### ② Mapping各字段的选型流程

![img](file:///D:/Documents/My Knowledge/temp/f166f7be-1579-464a-9d6d-519c6fe778b0/128/index_files/f754451b-b60a-4c4e-a1c5-a4c4aa1c96dc.png)

#### ③ 选择合理的分词器

常见的开源中文分词器包括：ik分词器、ansj分词器、hanlp分词器、结巴分词器、海量分词器、“ElasticSearch最全分词器比较及使用方法” 搜索可查看对比效果。

如果选择ik，建议使用ik_max_word。因为：粗粒度的分词结果基本包含细粒度ik_smart的结果。

#### ④ date、long、还是keyword

根据业务需要，如果需要基于时间轴做分析，必须date类型；如果仅需要秒级返回，建议使用keyword。

### （4）数据写入优化实践

#### ① 要不要秒级响应

Elasticsearch近实时的本质是：最快1s写入的数据可以被查询到。

如果refresh_interval设置为1s，势必会产生大量的segment，检索性能会受到影响。

所以，非实时的场景可以调大，设置为30s，甚至-1。

#### ② 减少副本，提升写入性能

写入前，副本数设置为0，写入后，副本数设置为原来值。

#### ③ 能批量就不单条写入

批量接口为bulk，批量的大小要结合队列的大小，而队列大小和线程池大小、机器的cpu核数。

#### ④ 禁用swap

在Linux系统上，通过运行以下命令临时禁用交换：

```shell
sudo swapoff -a
```



### （5）检索聚合优化实战

#### ① 禁用 wildcard模糊匹配

数据量级达到TB+甚至更高之后，wildcard在多字段组合的情况下很容易出现卡死，甚至导致集群节点崩溃宕机的情况。

后果不堪设想。

替代方案：

方案一：针对精确度要求高的方案:两套分词器结合，standard和ik结合，使用match_phrase检索。

方案二：针对精确度要求不高的替代方案：建议ik分词，通过match_phrase和slop结合查询。

#### ② 极小的概率使用match匹配

中文match匹配显然结果是不准确的。很大的业务场景会使用短语匹配“match_phrase”。

match_phrase结合合理的分词词典、词库，会使得搜索结果精确度更高，避免噪音数据。

#### ③ 结合业务场景，大量使用filter过滤器

对于不需要使用计算相关度评分的场景，无疑filter缓存机制会使得检索更快。

举例：过滤某邮编号码。

#### ④ 控制返回字段和结果

和mysql查询一样，业务开发中，select * 操作几乎是不必须的。

同理，ES中，_source 返回全部字段也是非必须的。

要通过_source 控制字段的返回，只返回业务相关的字段。

网页正文content，网页快照html_content类似字段的批量返回，可能就是业务上的设计缺陷。

显然，摘要字段应该提前写入，而不是查询content后再截取处理。

#### ⑤ 分页深度查询和遍历

分页查询使用：from+size;

遍历使用：scroll；

并行遍历使用：scroll+slice。

斟酌集合业务选型使用。

#### ⑥ 聚合Size的合理设置

聚合结果是不精确的。除非你设置size为2的32次幂-1，否则聚合的结果是取每个分片的Top size元素后综合排序后的值。

实际业务场景要求精确反馈结果的要注意。

尽量不要获取全量聚合结果——从业务层面取TopN聚合结果值是非常合理的。因为的确排序靠后的结果值意义不大。

#### ⑦ 聚合分页合理实现

聚合结果展示的时，势必面临聚合后分页的问题，而ES官方基于性能原因不支持聚合后分页。

如果需要聚合后分页，需要自开发实现。包含但不限于：

方案一：每次取聚合结果，拿到内存中分页返回。

方案二：scroll结合scroll after集合redis实现。

### （6）业务优化

让Elasticsearch做它擅长的事情，很显然，它更擅长基于倒排索引进行搜索。

业务层面，用户想最快速度看到自己想要的结果，中间的“字段处理、格式化、标准化”等一堆操作，用户是不关注的。

为了让Elasticsearch更高效的检索，建议：

- 要做足“前戏”：字段抽取、倾向性分析、分类/聚类、相关性判定放在写入ES之前的ETL阶段;

- “睡服”产品经理：产品经理基于各种奇葩业务场景可能会提各种无理需求。

作为技术人员，要“通知以情晓之以理”，给产品经理讲解明白搜索引擎的原理、Elasticsearch的原理，哪些能做，哪些真的“臣妾做不到”。

### （7）小结

实际业务开发中，公司一般要求又想马儿不吃草，又想马儿飞快跑。

对于Elasticsearch开发也是，硬件资源不足（cpu、内存、磁盘都爆满）几乎没有办法提升性能的。

除了检索聚合，让Elasticsearch做N多相关、不相干的工作，然后得出结论“Elastic也就那样慢，没有想像的快”。

你脑海中是否也有类似的场景浮现呢？

提供相对NB的硬件资源、做好前期的各种准备工作、让Elasticsearch轻装上阵，相信你的Elasticsearch也会飞起来！

# 六、相关问题及解决

## **1、**ElasticSearch查询超过10000条（1000页）时出现Result window is too large的问题

```shell
PUT 索引名称/_settings 
{
  "index":{
    "max_result_window":100000000
  }
}
```

在config/elasticsearch.yml文件中的最后加上index.max_result_window: 100000000，但是这种方法要注意在最前面加上空格

## **2、Caused by: org.elasticsearch.common.io.stream.NotSerializableExceptionWrapper: too_many_clauses: maxClauseCount is set to 1024**

用了es的in查询，in中id大于1024个，导致es报错，es默认支持元素数量为1024个。

解决办法：

编辑elasticsearch.yml，添加如下配置：

```shell
index.query.bool.max_clause_count: 10240
```

新版本报错已经修改配置项名称，需添加如下字段：

```shell
indices.query.bool.max_clause_count: 300000
```

