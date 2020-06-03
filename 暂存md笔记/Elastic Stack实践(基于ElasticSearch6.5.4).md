学习目标：

> > > \- 理解Nginx日志分析系统的业务流程
>
> > > \- 掌握nginx的部署安装
>
> > > \- 理解Filebeat的架构
>
> > > \- 掌握Filebeat的部署安装
>
> > > \- 掌握Filebeat的基本使用
>
> > > \- 理解Filebeat的工作原理
>
> > > \- 掌握Filebeat的NginxModule的使用
>
> > > \- 掌握Metricbeat的部署安装
>
> > > \- 掌握Metricbeat的NginxModule的使用
>
> > > \- 掌握Kibana的部署安装
>
> > > \- 掌握Kibana的数据探索功能
>
> > > \- 掌握Metricbeat仪表盘安装到Kibana
>
> > > \- 掌握Filebeat仪表盘安装到Kibana
>
> > > \- 掌握Kibana的自定义图表制作
>
> > > \- 掌握Logstash的部署安装
>
> > > \- 掌握Logstash的基本使用
>
> > > \- 掌握综合练习中的自定义图表和仪表盘的制作

# 一、Elastic Stack简介

如果你没有听说过Elastic Stack，那你一定听说过ELK，实际上ELK是三款软件的简称，分别是Elasticsearch、Logstash、Kibana组成，在发展的过程中，又有新成员Beats的加入，所以就形成了Elastic Stack。所以说，ELK是旧的称呼，Elastic Stack是新的名字。

# 

![img](file:///D:/Documents/My Knowledge/temp/85e15363-a771-4e2a-9651-f3e9a40b2229/128/index_files/e27d4fcd-712c-4570-a50f-e835cd4b0232.jpg)

全系的Elastic Stack技术栈包括：

![img](file:///D:/Documents/My Knowledge/temp/85e15363-a771-4e2a-9651-f3e9a40b2229/128/index_files/26749f12-7ee4-4624-baf7-672850c18bb1.png)

**Elasticsearch**：Elasticsearch 基于java，是个开源分布式搜索引擎，它的特点有：分布式，零配置，自动发现，索引自动分片，索引副本机制，restful风格接口，多数据源，自动搜索负载等。

![img](file:///D:/Documents/My Knowledge/temp/85e15363-a771-4e2a-9651-f3e9a40b2229/128/index_files/001ce108-2852-4f6d-a6f7-6699a0158308.png)

**Logstash**：Logstash 基于java，是一个开源的用于收集,分析和存储日志的工具。

**Kibana**：Kibana 基于nodejs，也是一个开源和免费的工具，Kibana可以为 Logstash 和 ElasticSearch 提供的日志分析友好的Web 界面，可以汇总、分析和搜索重要数据日志。

**Beats**：Beats是elastic公司开源的一款采集系统监控数据的代理agent，是在被监控服务器上以客户端形式运行的数据收集器的统称，可以直接把数据发送给Elasticsearch或者通过Logstash发送给Elasticsearch，然后进行后续的数据分析活动。

Beats由如下组成：

-   **Packetbeat**：是一个网络数据包分析器，用于监控、收集网络流量信息，Packetbeat嗅探服务器之间的流量，解析应用层协议，并关联到消息的处理，其支 持ICMP (v4 and v6)、DNS、HTTP、Mysql、PostgreSQL、Redis、MongoDB、Memcache等协议；
-   **Filebeat**：用于监控、收集服务器日志文件，其已取代 logstash forwarder；
-   **Metricbeat**：可定期获取外部系统的监控指标信息，其可以监控、收集 Apache、HAProxy、MongoDBMySQL、Nginx、PostgreSQL、Redis、System、Zookeeper等服务；
-   **Winlogbeat**：用于监控、收集Windows系统的日志信息；
- ......

# 二、Nginx日志分析系统

## 2.1 项目需求

Nginx是一款非常优秀的web服务器，往往nginx服务会作为项目的访问入口，那么，nginx的性能保障就变得非常重要了，如果nginx的运行出现了问题就会对项目有较大的影响，所以，我们需要对nginx的运行有监控措施，实时掌握nginx的运行情况，那就需要收集nginx的运行指标和分析nginx的运行日志了。

## 2.2 业务流程

![img](file:///D:/Documents/My Knowledge/temp/85e15363-a771-4e2a-9651-f3e9a40b2229/128/index_files/430db5e6-948b-4e8b-a2b8-1768b658ac3e.png)

说明：

- 通过Beats采集Nginx的指标数据和日志数据
- Beats采集到数据后发送到Elasticsearch中
- Kibana读取数据进行分析
- 用户通过Kibana进行查看分析报表

## 2.3 部署安装Nginx

 

```shell
# 上传解压压缩包
tar -zxvf nginx-1.14.2.tar.gz
# 安装 pcre、zlib、openssl等Nginx需要的编译环境
yum -y install pcre-devel zlib-devel openssl openssl-devel
# 安装gcc
yum -y install gcc gcc-c++ autoconf automake make

# 编译安装
cd nginx-1.14.2
./configure
make install

#启动
cd /usr/local/nginx/sbin/
./nginx

#通过浏览器访问页面并且查看日志
#访问地址：http://192.168.1.130/
tail -f /usr/local/nginx/logs/access.log
```



![img](file:///D:/Documents/My Knowledge/temp/85e15363-a771-4e2a-9651-f3e9a40b2229/128/index_files/a0183bf7-1eba-447d-96f6-d4ee34979e59.png)

# 三、Beats 简介

![img](file:///D:/Documents/My Knowledge/temp/85e15363-a771-4e2a-9651-f3e9a40b2229/128/index_files/2a783cd7-a403-43df-a68c-69a6dc32efd5.jpg)

官网：https://www.elastic.co/cn/beats

![img](file:///D:/Documents/My Knowledge/temp/85e15363-a771-4e2a-9651-f3e9a40b2229/128/index_files/85f62892-c502-4ffa-86d8-90b2dce87b73.png)

Beats系列产品：

![img](file:///D:/Documents/My Knowledge/temp/85e15363-a771-4e2a-9651-f3e9a40b2229/128/index_files/d10e40ef-7530-4771-9e9e-166a6d2cf26d.png)

# 四、Filebeat

![img](file:///D:/Documents/My Knowledge/temp/85e15363-a771-4e2a-9651-f3e9a40b2229/128/index_files/d9eca3d7-c045-4ef7-b250-cd32bfd61e78.png)

## 4.1 架构

用于监控、收集服务器日志文件：

![img](file:///D:/Documents/My Knowledge/temp/85e15363-a771-4e2a-9651-f3e9a40b2229/128/index_files/39f1ecce-1159-4807-b048-4918c24cf502.png)

## 4.2 部署与运行

下载（或使用资料中提供的安装包，版本为：filebeat-6.5.4）：https://www.elastic.co/cn/downloads/beats

  链接：https://pan.baidu.com/s/1vuz7t-tU4q7zkfO6Pa5XLw 

  提取码：47hj 

 

```shell
mkdir /home/beats
tar -zxvf filebeat-6.5.4-linux-x86_64.tar.gz
cd filebeat-6.5.4-linux-x86_64

#创建如下配置文件 vim itcast.yml
filebeat.inputs:
- type: stdin
  enabled: true
setup.template.settings:
  index.number_of_shards: 3
output.console:
  pretty: true
  enable: true

#启动filebeat
./filebeat -e -c itcast.yml
# 后台运行，exit;退出控制台，通kibana类似
filebeat -e -c itcast.yml &

#输入hello运行结果如下：
hello
```



![img](file:///D:/Documents/My Knowledge/temp/85e15363-a771-4e2a-9651-f3e9a40b2229/128/index_files/62398abb-2a22-409c-864c-1f55d6584ebc.png)

 

```shell
{
  "@timestamp": "2020-05-25T06:02:01.396Z",
  "@metadata": { #元数据信息
    "beat": "filebeat",
    "type": "doc",
    "version": "6.5.4"
  },
  "source": "",
  "offset": 0,
  "message": "hello", #输入的内容
  "prospector": { #标准输入勘探器
    "type": "stdin"
  },
  "input": { #控制台标准输入
    "type": "stdin"
  },
  "beat": { #beat版本以及主机信息
    "name": "localhost.localdomain",
    "hostname": "localhost.localdomain",
    "version": "6.5.4"
  },
  "host": {
    "name": "localhost.localdomain"
  }
}

```



## 4.3 读取文件

 

```shell
#配置读取文件项 itcast-log.yml
filebeat.inputs:
- type: log
  enabled: true
  paths:
    - /home/beats/logs/*.log
setup.template.settings:
  index.number_of_shards: 3
output.console:
  pretty: true
  enable: true

#启动filebeat
./filebeat -e -c itcast-log.yml

#/home/beats/logs下创建a.log文件，并输入如下内容:
hello
world

mkdir /home/beats/logs
touch /home/beats/logs/a.log
echo "hello" >> /home/beats/logs/a.log
echo "world" >> /home/beats/logs/a.log

#观察filebeat输出
# 可以看出，已经检测到日志文件有更新，立刻就会读取到更新的内容，并且输出到控制台。
```

![img](file:///D:/Documents/My Knowledge/temp/85e15363-a771-4e2a-9651-f3e9a40b2229/128/index_files/3bfe0faa-0ff0-4174-9c17-9778a68f428e.png)

## 4.4 自定义字段

 

```shell
#配置读取文件项 itcast-log.yml
filebeat.inputs:
- type: log
  enabled: true
  paths:
    - /home/beats/logs/*.log
  tags: ["web"] # 添加自定义tag，便于后续的处理
  fields: # 添加自定义字段
    from: "itcast-im"
  fields_under_root: false # true为添加到根节点，false为添加到子节点中
setup.template.settings:
  index.number_of_shards: 3 # 指定的索引分区数
output.console:
  pretty: true
  enable: true


#启动filebeat
./filebeat -e -c itcast-log.yml

#/home/beats/logs下创建b.log文件，并输入如下内容
123

touch /home/beats/logs/b.log
echo "123" >> /home/beats/logs/b.log
```

![img](file:///D:/Documents/My Knowledge/temp/85e15363-a771-4e2a-9651-f3e9a40b2229/128/index_files/b2ab7753-d704-4e00-bcea-bfdaef4b20c9.png)

## 4.5 输出到Elasticsearch

 

```shell
#配置读取文件项 vim itcast-elasticsearch.yml
filebeat.inputs:
- type: log
  enabled: true
  paths:
    - /home/beats/logs/*.log
  tags: ["web"] # 添加自定义tag，便于后续的处理
  fields: # 添加自定义字段
    from: "itcast-im"
  fields_under_root: false # true为添加到根节点，false为添加到子节点中
setup.template.settings:
  index.number_of_shards: 3 # 指定的索引分区数
#output.console:
#  pretty: true
#  enable: true
output.elasticsearch: #指定ES的配置
  hosts: ["192.168.1.128:9200","192.168.1.129:9200","192.168.1.130:9200"]

# 启动filebeat
./filebeat -e -c itcast-elasticsearch.yml
```

在日志文件中输入新的内容进行测试：

```shell
echo "456" >> /home/beats/logs/b.log
```

![img](file:///D:/Documents/My Knowledge/temp/85e15363-a771-4e2a-9651-f3e9a40b2229/128/index_files/655bff6a-65af-4a99-ad04-376c89e9df05.png)

查看数据：

![img](file:///D:/Documents/My Knowledge/temp/85e15363-a771-4e2a-9651-f3e9a40b2229/128/index_files/aa6b6ae6-aa8a-4883-bdd8-a003dc4f1272.png)

## 4.6 Filebeat工作原理

Filebeat由两个主要组件组成：prospector 和 harvester。

- harvester：

- - 负责读取单个文件的内容。
  - 如果文件在读取时被删除或重命名，Filebeat将继续读取文件。

- prospector

- - prospector 负责管理harvester并找到所有要读取的文件来源。
  - 如果输入类型为日志，则查找器将查找路径匹配的所有文件，并为每个文件启动一个harvester。
  - Filebeat目前支持两种prospector类型：log和stdin。

- Filebeat如何保持文件的状态

- - Filebeat 保存每个文件的状态并经常将状态刷新到磁盘上的注册文件中。
  - 该状态用于记住harvester正在读取的最后偏移量，并确保发送所有日志行。
  - 如果输出（例如Elasticsearch或Logstash）无法访问，Filebeat会跟踪最后发送的行，并在输出再次可用时继续读取文件。
  - 在Filebeat运行时，每个prospector内存中也会保存的文件状态信息，当重新启动Filebeat时，将使用注册文件的数据来重建文件状态，Filebeat将每个harvester在从保存的最后偏移量继续读取。
  - 文件状态记录在data/registry文件中。

启动命令：

 

```shell
./filebeat -e -c itcast.yml
./filebeat -e -c itcast.yml -d "publish"

#参数说明
-e: 输出到标准输出，默认输出到syslog和logs下
-c: 指定配置文件
-d: 输出debug信息

#测试： 
./filebeat -e -c itcast-log.yml -d "publish"

echo "456" >> /home/beats/logs/a.log
```

![img](file:///D:/Documents/My Knowledge/temp/85e15363-a771-4e2a-9651-f3e9a40b2229/128/index_files/38553a6b-1cf4-4484-a089-82c35b5ec9ae.png)

## 4.7 读取Nginx日志文件

 

```shell
# vim itcast-nginx.yml
filebeat.inputs:
- type: log
  enabled: true
  paths:
    - /usr/local/nginx/logs/*.log
  tags: ["nginx"] # 添加自定义tag，便于后续的处理
setup.template.settings:
  index.number_of_shards: 3 # 指定的索引分区数
output.elasticsearch: #指定ES的配置
  hosts: ["192.168.1.128:9200","192.168.1.129:9200","192.168.1.130:9200"]
  
# 删除之前的索引

#启动
./filebeat -e -c itcast-nginx.yml
```

![img](file:///D:/Documents/My Knowledge/temp/85e15363-a771-4e2a-9651-f3e9a40b2229/128/index_files/112528bc-bd40-43d2-bcaa-d1768196daae.png)

启动后，可以在Elasticsearch中看到索引以及查看数据：

![img](file:///D:/Documents/My Knowledge/temp/85e15363-a771-4e2a-9651-f3e9a40b2229/128/index_files/d4e7d534-cb34-4464-96b2-e761b53436d9.png)

可以看到，在message中已经获取到了nginx的日志，但是，内容并没有经过处理，只是读取到原数据，那么对于我们后期的操作是不利的，有办法解决吗？

## 4.7 Module

前面要想实现日志数据的读取以及处理都是自己手动配置的，其实，在Filebeat中，有大量的Module，可以简化我们的配置，直接就可以使用，如下：

```shell
./filebeat modules list
```

![img](file:///D:/Documents/My Knowledge/temp/85e15363-a771-4e2a-9651-f3e9a40b2229/128/index_files/53b5bdd7-835e-4fab-ac3b-f087c81ed0f5.png)

可以看到，内置了很多的module，但是都没有启用，如果需要启用需要进行enable操作：

```shell
./filebeat modules enable nginx #启用
./filebeat modules disable nginx #禁用
```

```shell
./filebeat modules list
# 可以发现，nginx的module已经被启用。
```

![img](file:///D:/Documents/My Knowledge/temp/85e15363-a771-4e2a-9651-f3e9a40b2229/128/index_files/98bd4c16-f07e-4209-b3fc-a3257631824f.png)

### 4.7.1 nginx module 配置

```shell
cd modules.d/
vim nginx.yml
```

![img](file:///D:/Documents/My Knowledge/temp/85e15363-a771-4e2a-9651-f3e9a40b2229/128/index_files/a936b7f3-7cc1-4119-adf5-ca0f64dca5e6.png)

 

```shell
- module: nginx
  # Access logs
  access:
    enabled: true
    var.paths: ["/usr/local/nginx/logs/access.log*"]
    # Set custom paths for the log files. If left empty,
    # Filebeat will choose the paths depending on your OS.
    #var.paths:

  # Error logs
  error:
    enabled: true
    var.paths: ["/usr/local/nginx/logs/error.log*"]
    # Set custom paths for the log files. If left empty,
    # Filebeat will choose the paths depending on your OS.
    #var.paths:

```



### 4.7.2 配置filebeat

```shell
filebeat.inputs:
#- type: log
#  enabled: true
#  paths:
#    - /usr/local/nginx/logs/*.log
#  tags: ["nginx"] # 添加自定义tag，便于后续的处理
setup.template.settings:
  index.number_of_shards: 3 # 指定的索引分区数
output.elasticsearch: #指定ES的配置
  hosts: ["192.168.1.128:9200","192.168.1.129:9200","192.168.1.130:9200"]
filebeat.config.modules:
  path: ${path.config}/modules.d/*.yml
  reload.enabled: false
```

### 4.7.3 测试

```shell
# 删除之前的索引

# 启动
./filebeat -e -c itcast-nginx.yml

#启动会出错，如下
ERROR  fileset/factory.go:142 Error loading pipeline: Error loading pipeline for fileset nginx/access: This module requires the following Elasticsearch plugins:
ingest-user-agent, ingest-geoip. You can install them by running the following
commands on all the Elasticsearch nodes:
  sudo bin/elasticsearch-plugin install ingest-user-agent
  sudo bin/elasticsearch-plugin install ingest-geoip
 
#解决：需要在Elasticsearch中安装ingest-user-agent、ingest-geoip插件
#在资料中可以找到，ingest-user-agent.tar、ingest-geoip.tar、ingest-geoip-conf.tar 3个文件
#其中，ingest-user-agent.tar、ingest-geoip.tar解压到plugins下
#ingest-geoip-conf.tar解压到config下
# 重启es问题解决。这里将解压后的文件远程复制到其他es节点，保证es所有节点都包含。

tar -xvf ingest-user-agent.tar -C /home/elasticsearch6.5.4/ElasticStack/elasticsearch-6.5.4/plugins/
tar -xvf ingest-geoip.tar -C /home/elasticsearch6.5.4/ElasticStack/elasticsearch-6.5.4/plugins/
tar -xvf ingest-geoip-conf.tar -C /home/elasticsearch6.5.4/ElasticStack/elasticsearch-6.5.4/config/
chown -R es:eszu /home/elasticsearch6.5.4/ElasticStack/elasticsearch-6.5.4/config/

scp -r /home/elasticsearch6.5.4/ElasticStack/elasticsearch-6.5.4/plugins/ingest-geoip/ root@192.168.1.129:/home/elasticsearch6.5.4/ElasticStack/elasticsearch-6.5.4/plugins/
scp -r /home/elasticsearch6.5.4/ElasticStack/elasticsearch-6.5.4/plugins/ingest-user-agent/ root@192.168.1.129:/home/elasticsearch6.5.4/ElasticStack/elasticsearch-6.5.4/plugins/
scp -r /home/elasticsearch6.5.4/ElasticStack/elasticsearch-6.5.4/config/ingest-geoip/ root@192.168.1.129:/home/elasticsearch6.5.4/ElasticStack/elasticsearch-6.5.4/config/
scp -r /home/elasticsearch6.5.4/ElasticStack/elasticsearch-6.5.4/plugins/ingest-geoip/ root@192.168.1.130/home/elasticsearch6.5.4/ElasticStack/elasticsearch-6.5.4/plugins/
scp -r /home/elasticsearch6.5.4/ElasticStack/elasticsearch-6.5.4/plugins/ingest-user-agent/ root@192.168.1.130:/home/elasticsearch6.5.4/ElasticStack/elasticsearch-6.5.4/plugins/
scp -r /home/elasticsearch6.5.4/ElasticStack/elasticsearch-6.5.4/config/ingest-geoip/ root@192.168.1.130:/home/elasticsearch6.5.4/ElasticStack/elasticsearch-6.5.4/config/

service elasticsearch restart

# 再启动
./filebeat -e -c itcast-nginx.yml
```



测试发现，数据已经写入到了Elasticsearch中，并且拿到的数据更加明确了：

![img](file:///D:/Documents/My Knowledge/temp/85e15363-a771-4e2a-9651-f3e9a40b2229/128/index_files/4035c304-3383-46aa-95db-851f9d73bf35.png)

当然了，其他的Module的用法参加官方文档：https://www.elastic.co/guide/en/beats/filebeat/current/filebeat-modules.html

# 五、Metricbeat

![img](file:///D:/Documents/My Knowledge/temp/85e15363-a771-4e2a-9651-f3e9a40b2229/128/index_files/02f3a96f-6487-41f0-904a-02c5b2054041.png)

- 定期收集操作系统或应用服务的指标数据。
- 存储到Elasticsearch中，进行实时分析。

## 5.1 Metricbeat组成

Metricbeat有2部分组成，一部分是Module，另一部分为Metricset。

- Module

- - 收集的对象，如：mysql、redis、nginx、操作系统等；

- Metricset

- - 收集指标的集合，如：cpu、memory、network等；

以Redis Module为例：

![img](file:///D:/Documents/My Knowledge/temp/85e15363-a771-4e2a-9651-f3e9a40b2229/128/index_files/1809a406-1c1e-4ca8-80d3-c166955ee6d4.png)

## 5.2 部署与收集系统指标

 

```shell
tar -zxvf metricbeat-6.5.4-linux-x86_64.tar.gz
cd metricbeat-6.5.4-linux-x86_64
vim metricbeat.yml

metricbeat.config.modules:
    path: ${path.config}/modules.d/*.yml
    reload.enabled: false
setup.template.settings:
    index.number_of_shards: 1
    index.codec: best_compression
setup.kibana:
output.elasticsearch:
    hosts: ["192.168.1.128:9200","192.168.1.129:9200","192.168.1.130:9200"]
processors:
    - add_host_metadata: ~
    - add_cloud_metadata: ~
    
#启动
./metricbeat -e
# 后台运行，exit;退出控制台，通kibana类似
./metricbeat -e &
```

在ELasticsearch中可以看到，系统的一些指标数据已经写入进去了：

![img](file:///D:/Documents/My Knowledge/temp/85e15363-a771-4e2a-9651-f3e9a40b2229/128/index_files/4947e9b0-dcb3-496c-941d-c2c9fe401b49.png)

system module配置：

```shell
cd  modules.d/
cat system.yml

# Module: system
# Docs: https://www.elastic.co/guide/en/beats/metricbeat/6.5/metricbeat-module-system.html

- module: system
  period: 10s
  metricsets:
    - cpu
    - load
    - memory
    - network
    - process
    - process_summary
    #- core
    #- diskio
    #- socket
  process.include_top_n:
    by_cpu: 5      # include top 5 processes by CPU
    by_memory: 5   # include top 5 processes by memory

- module: system
  period: 1m
  metricsets:
    - filesystem
    - fsstat
  processors:
  - drop_event.when.regexp:
      system.filesystem.mount_point: '^/(sys|cgroup|proc|dev|etc|host|lib)($|/)'

- module: system
  period: 15m
  metricsets:
    - uptime

#- module: system
#  period: 5m
#  metricsets:
#    - raid
#  raid.mount_point: '/'
```



## 5.3 Module

```shell
# 查看列表
./metricbeat modules list
```

![img](file:///D:/Documents/My Knowledge/temp/85e15363-a771-4e2a-9651-f3e9a40b2229/128/index_files/bbdfc9e3-472a-4155-b386-4aa9c9687e21.png)

## 5.4 Nginx Module

### 5.4.1 开启nginx的状态查询

在nginx中，需要开启状态查询，才能查询到指标数据。

 

```shell
#重新编译nginx
cd /home/nginx/nginx-1.14.2
./configure --prefix=/usr/local/nginx --with-http_stub_status_module
make
make install

cd /usr/local/nginx/sbin/
./nginx -V  #查询版本信息
nginx version: nginx/1.11.6
built by gcc 4.4.7 20120313 (Red Hat 4.4.7-23) (GCC)
configure arguments: --prefix=/usr/local/nginx --with-http_stub_status_module

#配置nginx
cd /usr/local/nginx/conf
vim nginx.conf
location /nginx-status {
    stub_status on;
    access_log off;
}
```



![img](file:///D:/Documents/My Knowledge/temp/85e15363-a771-4e2a-9651-f3e9a40b2229/128/index_files/844e93b6-8061-4431-b280-5ba2227cbd0c.png)

测试：

```shell
# 重启nginx
cd /usr/local/nginx/sbin/
./nginx -s stop
./nginx
```

![img](file:///D:/Documents/My Knowledge/temp/85e15363-a771-4e2a-9651-f3e9a40b2229/128/index_files/2e17de64-4a5d-46fc-9d82-9c5c45cb92ce.png)

**结果说明**：

- Active connections：正在处理的活动连接数

- server accepts handled requests

- - 第一个 server 表示Nginx启动到现在共处理了9个连接
  - 第二个 accepts 表示Nginx启动到现在共成功创建 9 次握手
  - 第三个 handled requests 表示总共处理了 21 次请求
  - 请求丢失数 = 握手数 - 连接数 ，可以看出目前为止没有丢失请求

- Reading: 0 Writing: 1 Waiting: 1

- - Reading：Nginx 读取到客户端的 Header 信息数
  - Writing：Nginx 返回给客户端 Header 信息数
  - Waiting：Nginx 已经处理完正在等候下一次请求指令的驻留链接（开启keep-alive的情况下，这个值等于Active - (Reading+Writing)）

### 5.4.2 配置Nginx Module

 

```shell
#启用nginx module
cd /home/beats/metricbeat-6.5.4-linux-x86_64
./metricbeat modules enable nginx

#修改nginx module配置
vim modules.d/nginx.yml
# Module: nginx
# Docs: https://www.elastic.co/guide/en/beats/metricbeat/6.5/metricbeat-module-nginx.html

- module: nginx
  #metricsets:
  #  - stubstatus
  period: 10s

  # Nginx hosts
  hosts: ["http://192.168.1.128"]

  # Path to server status. Default server-status
  server_status_path: "nginx-status"

  #username: "user"
  #password: "secret"

#启动
./metricbeat -e
```

测试：

![img](file:///D:/Documents/My Knowledge/temp/85e15363-a771-4e2a-9651-f3e9a40b2229/128/index_files/c167f3cf-0824-4d11-906e-b57a863ef3c1.png)

可以看到，nginx的指标数据已经写入到了Elasticsearch。

更多的Module使用参见官方文档：https://www.elastic.co/guide/en/beats/metricbeat/current/metricbeat-modules.html

# 六、Kibana

![img](file:///D:/Documents/My Knowledge/temp/85e15363-a771-4e2a-9651-f3e9a40b2229/128/index_files/f9e7bbb5-2d21-466c-8bda-2b682af2a551.png)

Kibana 是一款开源的数据分析和可视化平台，它是 Elastic Stack 成员之一，设计用于和 Elasticsearch 协作。您可以使用 Kibana 对 Elasticsearch 索引中的数据进行搜索、查看、交互操作。您可以很方便的利用图表、表格及地图对数据进行多元化的分析和呈现。

官网：https://www.elastic.co/cn/kibana

## 6.1 配置安装

参考：

​     为知笔记地址：[CentOS安装配置ElasticSearch6.5.4及其插件](wiz://open_document?guid=f166f7be-1579-464a-9d6d-519c6fe778b0&kbguid=&private_kbguid=9e15e816-792d-4528-9d21-a849cc4117d5)

​    GitHub地址：[https://github.com/wangliu1102/StudyNotes/tree/master/%E5%B0%9A%E7%A1%85%E8%B0%B7Java/%E5%85%AD%E3%80%81Java%E8%BF%9B%E9%98%B6/Elasticsearch/%E5%AE%89%E8%A3%85](https://github.com/wangliu1102/StudyNotes/tree/master/尚硅谷Java/六、Java进阶/Elasticsearch/安装)

 

```shell
#解压安装包
tar -zxvf kibana-6.5.4-linux-x86_64.tar.gz

#修改配置文件
vim config/kibana.yml
server.host: "192.168.40.133"  #对外暴露服务的地址
elasticsearch.url: "http://192.168.1.128:9200"  #配置Elasticsearch

#启动
./bin/kibana

#通过浏览器进行访问
http://192.168.40.133:5601/app/kibana
```

![img](file:///D:/Documents/My Knowledge/temp/85e15363-a771-4e2a-9651-f3e9a40b2229/128/index_files/13d0164f-150e-41a0-b2f5-41ee23b9bbe1.png)

可以看到kibana页面，并且可以看到提示，导入数据到Kibana。

## 6.2 功能说明

![img](file:///D:/Documents/My Knowledge/temp/85e15363-a771-4e2a-9651-f3e9a40b2229/128/index_files/19816fa8-c67b-4f42-8434-49b8443db2b0.png)

## 6.3 数据探索

首先先添加索引信息：

![img](file:///D:/Documents/My Knowledge/temp/85e15363-a771-4e2a-9651-f3e9a40b2229/128/index_files/43660e43-c51d-496e-a6f8-c493308976a9.png)

![img](file:///D:/Documents/My Knowledge/temp/85e15363-a771-4e2a-9651-f3e9a40b2229/128/index_files/0a68205e-c909-4e1a-a058-3e57a6ccbe0d.png)

即可查看索引数据：

![img](file:///D:/Documents/My Knowledge/temp/85e15363-a771-4e2a-9651-f3e9a40b2229/128/index_files/54328a6c-35d3-4924-a7b7-403950b76b86.png)

## 6.4 Metricbeat 仪表盘

可以将Metricbeat的数据在Kibana中展示。

```shell
#修改metricbeat.yml配置
cd /home/beats/metricbeat-6.5.4-linux-x86_64
vim metricbeat.yml
setup.kibana:
  host: "192.168.1.128:5601"

#安装仪表盘到Kibana
./metricbeat setup --dashboards
```

即可在Kibana中看到仪表盘数据：

![img](file:///D:/Documents/My Knowledge/temp/85e15363-a771-4e2a-9651-f3e9a40b2229/128/index_files/fe523d6c-98f7-4797-9f50-3b28bede5475.png)

查看系统信息：

![img](file:///D:/Documents/My Knowledge/temp/85e15363-a771-4e2a-9651-f3e9a40b2229/128/index_files/dce995df-0fa7-4c59-ae6a-df3b31e57853.png)

## 6.5 Nginx 指标仪表盘

![img](file:///D:/Documents/My Knowledge/temp/85e15363-a771-4e2a-9651-f3e9a40b2229/128/index_files/efbb9a79-b517-4788-8269-e15e72b7b793.png)

![img](file:///D:/Documents/My Knowledge/temp/85e15363-a771-4e2a-9651-f3e9a40b2229/128/index_files/7066bf7e-283f-4e83-a139-bc2740e593f0.png)

## 6.6 Nginx 日志仪表盘

 

```shell
#修改配置文件 vim itcast-nginx.yml
filebeat.inputs:
#- type: log
#  enabled: true
#  paths:
#    - /usr/local/nginx/logs/*.log
#  tags: ["nginx"] # 添加自定义tag，便于后续的处理
setup.template.settings:
  index.number_of_shards: 3 # 指定的索引分区数
output.elasticsearch: #指定ES的配置
  hosts: ["192.168.1.128:9200","192.168.1.129:9200","192.168.1.130:9200"]
filebeat.config.modules:
  path: ${path.config}/modules.d/*.yml
  reload.enabled: false
setup.kibana:
  host: "192.168.1.128:5601"
  

#安装仪表盘到kibana
./filebeat -c itcast-nginx.yml setup 

# 启动，让他实时产生日志
./filebeat -e -c itcast-nginx.yml
```

可以看到nginx的Filebeat的仪表盘了：

![img](file:///D:/Documents/My Knowledge/temp/85e15363-a771-4e2a-9651-f3e9a40b2229/128/index_files/fa64e8cd-2812-4fb7-b5d1-06e6341d9775.png)

![img](file:///D:/Documents/My Knowledge/temp/85e15363-a771-4e2a-9651-f3e9a40b2229/128/index_files/a55008ea-3ba0-45cf-b696-f8d4365f2c74.png)

![img](file:///D:/Documents/My Knowledge/temp/85e15363-a771-4e2a-9651-f3e9a40b2229/128/index_files/913f3a08-39c3-4b01-91e4-82e862f9e9b4.png)

## 6.7 自定义图表

在Kibana中，也可以进行自定义图表，如制作柱形图：

![img](file:///D:/Documents/My Knowledge/temp/85e15363-a771-4e2a-9651-f3e9a40b2229/128/index_files/458c25dd-6b14-4e37-947d-d8979df1b752.png)

![img](file:///D:/Documents/My Knowledge/temp/85e15363-a771-4e2a-9651-f3e9a40b2229/128/index_files/4f872cb9-f771-41a2-ba15-47f6774c6a93.png)

![img](file:///D:/Documents/My Knowledge/temp/85e15363-a771-4e2a-9651-f3e9a40b2229/128/index_files/66613577-1774-484f-98a0-0c22d1609f66.png)

![img](file:///D:/Documents/My Knowledge/temp/85e15363-a771-4e2a-9651-f3e9a40b2229/128/index_files/b9a35bdb-1b5c-4969-b33f-05c7eda7e93f.png)

![img](file:///D:/Documents/My Knowledge/temp/85e15363-a771-4e2a-9651-f3e9a40b2229/128/index_files/e1dbd60d-b782-4c6b-bea8-68bf0d58b6da.png)

将图表添加到自定义Dashboard中，并且可以save保存为自定义的仪表盘：

![img](file:///D:/Documents/My Knowledge/temp/85e15363-a771-4e2a-9651-f3e9a40b2229/128/index_files/0449573d-c95e-4983-9cc2-a285af4e8253.png)

## 6.8 开发者工具

在Kibana中，为开发者的测试提供了便捷的工具使用，如下：

![img](file:///D:/Documents/My Knowledge/temp/85e15363-a771-4e2a-9651-f3e9a40b2229/128/index_files/ace27af9-5e84-4a16-9dcc-01c295eb2ba5.png)

# 七、Logstash

## 7.1 简介

![img](file:///D:/Documents/My Knowledge/temp/85e15363-a771-4e2a-9651-f3e9a40b2229/128/index_files/ec30a6cb-b053-49b8-bbea-416b09b74922.png)

用途：

![img](file:///D:/Documents/My Knowledge/temp/85e15363-a771-4e2a-9651-f3e9a40b2229/128/index_files/0f891044-383e-4bb0-bcb8-713fd9c617cc.jpg)

## 7.2 部署安装

![img](file:///D:/Documents/My Knowledge/temp/85e15363-a771-4e2a-9651-f3e9a40b2229/128/index_files/f29bf097-5ac6-424c-9a5e-40688b6aeff1.png)

 

```shell
#检查jdk环境，要求jdk1.8+
java -version

#解压安装包
tar -zxvf logstash-6.5.4.tar.gz

#第一个logstash示例
cd logstash-6.5.4
bin/logstash -e 'input { stdin { } } output { stdout {} }'
```



![img](file:///D:/Documents/My Knowledge/temp/85e15363-a771-4e2a-9651-f3e9a40b2229/128/index_files/0462ea3a-b3d0-498e-bdc6-6bd6642dcd8c.png)

## 7.3 配置详解

Logstash的配置有三部分，如下：

 

```shell
input { #输入
    stdin { ... } #标准输入
}

filter { #过滤，对数据进行分割、截取等处理
    ...
}

output { #输出
    stdout { ... } #标准输出
}
```

### 7.3.1 输入

- 采集各种样式、大小和来源的数据，数据往往以各种各样的形式，或分散或集中地存在于很多系统中。
- Logstash 支持各种输入选择 ，可以在同一时间从众多常用来源捕捉事件。能够以连续的流式传输方式，轻松地从您的日志、指标、Web 应用、数据存储以及各种 AWS 服务采集数据。

![img](file:///D:/Documents/My%20Knowledge/temp/85e15363-a771-4e2a-9651-f3e9a40b2229/128/index_files/04946fbf-626c-470f-bc1c-37ec56f05bd2.png)

### 7.3.2 过滤

- 实时解析和转换数据。
- 数据从源传输到存储库的过程中，Logstash 过滤器能够解析各个事件，识别已命名的字段以构建结构，并将它们转换成通用格式，以便更轻松、更快速地分析和实现商业价值。

![img](file:///D:/Documents/My%20Knowledge/temp/85e15363-a771-4e2a-9651-f3e9a40b2229/128/index_files/d35fcbf1-2cae-4d22-b1cb-8ee36e1c9931.png)

### 7.3.3 输出

Logstash 提供众多输出选择，您可以将数据发送到您要指定的地方，并且能够灵活地解锁众多下游用例。

![img](file:///D:/Documents/My%20Knowledge/temp/85e15363-a771-4e2a-9651-f3e9a40b2229/128/index_files/5ae3dbd6-6ce0-4e16-ba2e-8adfdfb9d2ca.png)

## 7.4 读取自定义日志

前面我们通过Filebeat读取了nginx的日志，如果是自定义结构的日志，就需要读取处理后才能使用，所以，这个时候就需要使用Logstash了，因为Logstash有着强大的处理能力，可以应对各种各样的场景。

### 7.4.1 日志结构

 

```
2019-03-15 21:21:21|ERROR|读取数据出错|参数：id=1002 
```

可以看到，日志中的内容是使用“|”进行分割的，使用，我们在处理的时候，也需要对数据做分割处理。

### 7.4.2 编写配置文件

 

```shell
cd /home/logstash/logstash-6.5.4
#vim itcast-pipeline.conf
input {
  file {        
    path => "/home/logstash/logs/app.log"
    start_position => "beginning"
  }
}
filter {
  mutate {
    split => {"message"=>"|"}
  }
}
output {
  stdout { codec => rubydebug }
}

```

### 7.4.3 启动测试

 

```shell
#启动
./bin/logstash -f itcast-pipeline.conf

# 后台运行，exit;退出控制台，通kibana类似
./bin/logstash -f itcast-pipeline.conf &

#写日志到文件
mkdir /home/logstash/logs
echo "2019-03-15 21:21:21|ERROR|读取数据出错|参数：id=1002" >> /home/logstash/logs/app.log

#输出的结果,可以看到，数据已经被分割了。
```

![img](file:///D:/Documents/My%20Knowledge/temp/85e15363-a771-4e2a-9651-f3e9a40b2229/128/index_files/4937ca2c-bbe4-4941-a909-fcb57ae8c20a.png)

### 7.4.5 输出到Elasticsearch

 

```shell
cd /home/logstash/logstash-6.5.4
#vim itcast-pipeline.conf
input {
  file {        
    path => "/home/logstash/logs/app.log"
    start_position => "beginning"
  }
}
filter {
  mutate {
    split => {"message"=>"|"}
  }
}
output {
  elasticsearch {
    hosts => [ "192.168.1.128:9200","192.168.1.129:9200","192.168.1.130:9200"]
  }
}

#启动
./bin/logstash -f itcast-pipeline.conf

#写入数据
echo "2019-03-16 21:21:21|ERROR|读取数据出错|参数：id=1003" >> /home/logstash/logs/app.log
```

测试：

![img](file:///D:/Documents/My%20Knowledge/temp/85e15363-a771-4e2a-9651-f3e9a40b2229/128/index_files/31b6b0b8-7fd8-435d-9441-548e2647eb7e.png)

![img](file:///D:/Documents/My%20Knowledge/temp/85e15363-a771-4e2a-9651-f3e9a40b2229/128/index_files/02324daa-6975-461c-9865-4b96d49fd881.png)

# 八、综合练习

下面我们将前面所学习到的Elasticsearch + Logstash + Beats + Kibana整合起来做一个综合性的练习，目的就是让我们能够更加深刻的理解Elastic Stack的使用。

## 8.1 流程说明

![img](file:///D:/Documents/My%20Knowledge/temp/85e15363-a771-4e2a-9651-f3e9a40b2229/128/index_files/05f7b347-4a10-4d95-9ee9-bf618cfb29b7.png)

- 应用APP生产日志，用来记录用户的操作

- - [INFO] 2019-03-15 22:55:20 [cn.itcast.dashboard.Main] - DAU|5206|使用优惠券|2019-03-1503:37:20
  - [INFO] 2019-03-15 22:55:21 [cn.itcast.dashboard.Main] - DAU|3880|浏览页面|2019-03-15 07:25:09

- 通过Filebeat读取日志文件中的内容，并且将内容发送给Logstash，原因是需要对内容做处理

- Logstash接收到内容后，进行处理，如分割操作，然后将内容发送到Elasticsearch中

- Kibana会读取Elasticsearch中的数据，并且在Kibana中进行设计Dashboard，最后进行展示

说明：日志格式、图表、Dashboard都是自定义的。

## 8.2 APP介绍

APP在生产环境应该是真实的系统，然而，我们现在仅仅的学习，为了简化操作，所以就做数据的模拟生成即可。

业务代码如下：

 

```java
package cn.itcast.dashboard;

import org.apache.commons.lang3.RandomUtils;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Main {
  private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);
    
  public static final String[] VISIT = new String[]{"浏览页面", "评论商品", "加入收藏","加入购物车", "提交订单", "使用优惠券", "领取优惠券", "搜索", "查看订单"};
  public static void main(String[] args) throws Exception {
    while(true){
      Long sleep = RandomUtils.nextLong(200, 1000 * 5);
      Thread.sleep(sleep);
      Long maxUserId = 9999L;
      Long userId = RandomUtils.nextLong(1, maxUserId);
      String visit = VISIT[RandomUtils.nextInt(0, VISIT.length)];
      DateTime now = new DateTime();
      int maxHour = now.getHourOfDay();
      int maxMillis = now.getMinuteOfHour();
      int maxSeconds = now.getSecondOfMinute();
      String date = now.plusHours(-(RandomUtils.nextInt(0, maxHour)))
         .plusMinutes(-(RandomUtils.nextInt(0, maxMillis)))
         .plusSeconds(-(RandomUtils.nextInt(0, maxSeconds)))
         .toString("yyyy-MM-dd HH:mm:ss");
      String result = "DAU|" + userId + "|" + visit + "|" + date;
      LOGGER.info(result);
   }
 }
}
```

运行结果：

![img](file:///D:/Documents/My%20Knowledge/temp/85e15363-a771-4e2a-9651-f3e9a40b2229/128/index_files/8c8c7443-cfca-4c03-b330-e9afccb9d11e.png)

代码在资料中可以找到，itcast-dashboard-generate.zip。

​    链接：https://pan.baidu.com/s/1iwOcN_gvaZdqmVYncnTfBw 

​    提取码：pqjv

部署：

```shell
#打包成jar包，在linux上运行
java -jar itcast-dashboard-generate-1.0-SNAPSHOT.jar &

#运行之后，就可以将日志写入到/itcast/logs/app.log文件中，文件路径配置在resource/log4j.properties中
```

## 8.3 Filebeat

 

```shell
cd /home/beats/filebeat-6.5.4-linux-x86_64
#vim itcast-dashboard.yml
filebeat.inputs:
- type: log
  enabled: true
  paths:
    - /itcast/logs/*.log
setup.template.settings:
  index.number_of_shards: 3 # 指定的索引分区数
output.logstash: #指定logstash的配置
  hosts: ["192.168.1.128:5044"]
  
#启动
./filebeat -e -c itcast-dashboard.yml
```

## 8.4 Logstash

 

```shell
cd /home/logstash/logstash-6.5.4
#vim itcast-dashboard.conf
input {
  beats {
    port => "5044"
  }
}
filter {
  mutate { 
    split => {"message"=>"|"}
  }
  mutate {
    add_field => {
      "userId" => "%{message[1]}"
      "visit" => "%{message[2]}"
      "date" => "%{message[3]}"
    }
  }
  mutate {
    convert => {
      "userId" => "integer"
      "visit" => "string"
      "date" => "string"
    }
  }
}
#output {
#  stdout { codec => rubydebug }
#}
output {
  elasticsearch {
    hosts => [ "192.168.1.128:9200","192.168.1.129:9200","192.168.1.130:9200"]
  }
}

# 先删除logstash索引
#启动
./bin/logstash -f itcast-dashboard.conf
```

## 8.5 Kibana

启动Kibana：

 

```shell
#启动
./bin/kibana
#通过浏览器进行访问
http://192.168.1.128:5601/app/kibana
```

添加Logstash索引到Kibana中：

![img](file:///D:/Documents/My%20Knowledge/temp/85e15363-a771-4e2a-9651-f3e9a40b2229/128/index_files/2f668c12-186b-4606-a7a0-a465cb31f8e4.png)

### 8.5.1 时间间隔的柱形图

说明：x轴是时间，以天为单位，y轴是count数

保存：（my-dashboard-时间间隔的柱形图）

![img](file:///D:/Documents/My%20Knowledge/temp/85e15363-a771-4e2a-9651-f3e9a40b2229/128/index_files/0897a4a0-3737-41d2-b2b0-911787d14a67.png)

### 8.5.2 各个操作的饼图分布

统计各个操作的数量，形成饼图。

保存：（my-dashboard-各个操作的饼图）

![img](file:///D:/Documents/My%20Knowledge/temp/85e15363-a771-4e2a-9651-f3e9a40b2229/128/index_files/670ec976-8564-47b1-8c56-c06e590c85cb.png)

![img](file:///D:/Documents/My%20Knowledge/temp/85e15363-a771-4e2a-9651-f3e9a40b2229/128/index_files/2fea9190-246e-4fdf-b07f-ba78b33cd69a.png)

### 8.5.3 数据表格

在数据探索中进行保存，并且保存，将各个操作的数据以表格的形式展现出来。

保存：（my-dashboard-表格）

![img](file:///D:/Documents/My%20Knowledge/temp/85e15363-a771-4e2a-9651-f3e9a40b2229/128/index_files/2a8a7b1e-dbe9-4335-8542-807b73c688f8.png)

### 8.5.4 制作Dashboard

![img](file:///D:/Documents/My%20Knowledge/temp/85e15363-a771-4e2a-9651-f3e9a40b2229/128/index_files/9c5fffe8-4210-4d2a-8d51-319e26b2a798.png)