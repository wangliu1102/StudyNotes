# 一、ElasticSearch概述

## 1.1 ElasticSearch简介

官网：https://www.elastic.co/cn/elasticsearch

![img](file:///D:/Documents/My Knowledge/temp/3923c1bb-9a06-4f25-9526-8916afc0803d/128/index_files/153cf641-6aa2-4761-a43d-e78c28293cfc.png)

## 1.2 ElasticSearch的基本概念

### 1.2.1 Index/Type/Document/Mapping/indexed/DSL基本概念

![img](file:///D:/Documents/My Knowledge/temp/3923c1bb-9a06-4f25-9526-8916afc0803d/128/index_files/1cad744c-a1ff-48bd-8c12-42432f939394.jpg)

### 1.2.2 elasticsearch 5.x 6.x 7.x的主要区别

```shell
# ElasticSearch5.X 一个索引index可以有多个类型type
# ElasticSearch6.X 一个索引index只有一个类型type
# ElasticSearch7.X 没有了类型type的概念，去除了类型
```



## 1.3 RESTfull API

![img](file:///D:/Documents/My Knowledge/temp/3923c1bb-9a06-4f25-9526-8916afc0803d/128/index_files/eb9b2754-a99c-4fd6-b8bc-d7e09fff047c.jpg)

## 1.4 CRUL命令

![img](file:///D:/Documents/My Knowledge/temp/3923c1bb-9a06-4f25-9526-8916afc0803d/128/index_files/d0d9d11f-dab6-4a92-af57-292958961c92.jpg)

## 1.5 ElasticSearch安装及插件配置

ElasticSearch、插件（ lasticSearch-head、Kibana、IK分词器等等）：

​    为知笔记地址：[CentOS安装配置ElasticSearch6.5.4](wiz://open_document?guid=f166f7be-1579-464a-9d6d-519c6fe778b0&kbguid=&private_kbguid=9e15e816-792d-4528-9d21-a849cc4117d5)

​    GitHub地址：[https://github.com/wangliu1102/StudyNotes/tree/master/%E5%B0%9A%E7%A1%85%E8%B0%B7Java/%E5%85%AD%E3%80%81Java%E8%BF%9B%E9%98%B6/Elasticsearch/%E5%AE%89%E8%A3%85](https://github.com/wangliu1102/StudyNotes/tree/master/尚硅谷Java/六、Java进阶/Elasticsearch/安装)

​        

# 二、ElasticSearch基本操作

## 2.1 倒排索引

### 2.1.1 倒排索引介绍

![img](file:///D:/Documents/My Knowledge/temp/3923c1bb-9a06-4f25-9526-8916afc0803d/128/index_files/232da9f9-338f-4b0b-be06-98e343629866.jpg)

![img](file:///D:/Documents/My Knowledge/temp/3923c1bb-9a06-4f25-9526-8916afc0803d/128/index_files/d690f741-1688-4059-96f7-291cb153c3c9.jpg)

![img](file:///D:/Documents/My Knowledge/temp/3923c1bb-9a06-4f25-9526-8916afc0803d/128/index_files/6714a1d6-b23a-4cc5-aa8c-3fbcbf9bd3b6.jpg) 

### 2.1.2 倒排索引举例

![img](file:///D:/Documents/My Knowledge/temp/3923c1bb-9a06-4f25-9526-8916afc0803d/128/index_files/0ceeffbe-6ba4-4e4b-9ebd-cf08c0c85886.jpg)

![img](file:///D:/Documents/My Knowledge/temp/3923c1bb-9a06-4f25-9526-8916afc0803d/128/index_files/0dffdc00-210b-4e04-a164-038cdee4e698.jpg)          

## 2.2 使用Kibana实现基本的增删改查操作

### 2.2.1 添加索引

```shell
# 添加索引,3个分片1个副本
PUT /lib
{
  "settings": {
    "number_of_shards": 3, 
    "number_of_replicas": 1 
  }
}

# 添加索引,不设置分片和副本的话，默认5分片1副
PUT /lib2
```

### 2.2.2 查看索引信息

```shell
# 查看指定索引的信息
GET /lib/_settings
GET /lib2/_settings

# 查看所有索引信息
GET _all/_settings
```

### 2.2.3 添加文档

```shell
# 添加文档，PUT指定id
PUT /lib/user/1
{
  "first_name": "Jane",
  "last_name": "Smith",
  "age": 32,
  "about": "I like to collect rock albums",
  "interests": ["music"]
}

# 添加文档，POST自动生成id
POST /lib/user/
{
  "first_name": "Dauglas",
  "last_name": "Fir",
  "age": 23,
  "about": "I like to build cabinets",
  "interests": ["forestry"]
}

```



### 2.2.4 查看文档

```shell
# 查看文档，获取lib索引下id为1的user
GET /lib/user/1

# 查看文档，获取lib索引下id为wfWeLHIBsF5g6MW4H5jM的user
GET /lib/user/wfWeLHIBsF5g6MW4H5jM

# 查看文档，获取lib索引下id为1的user，指定查询age和interests字段
GET /lib/user/1?_source=age,interests

# 判断文档是否存在，当然，这只表示你在查询的那一刻文档不存在，但并不表示几毫秒后依旧不存在。另一个进程在这期间可能创建新文档。
HEAD /lib5/user/1
```

#### 1、文档解析

```shell
# 查询lib索引下user的所有文档
GET /lib/user/_search
```

![img](file:///D:/Documents/My Knowledge/temp/3923c1bb-9a06-4f25-9526-8916afc0803d/128/index_files/88adebc4-8bb5-4367-b65b-771256faec3c.png)

##### （1）时间

```shell
"took" : 4,
"timed_out" : false,

# took字段告诉你ElasticSearch花了多久处理请求，时间单位是毫秒

# time_out字段表示搜索请求是否超时。默认情况下，搜索永远不会超时，但是可以通过timeout参数来设定限制，例如:
# GET /lib/user/_search?timeout=3s
# 如果搜索超时了，time_out值为true,而且只能获得超时前所收集的结果
```

##### （2）分片

```shell
  "_shards" : {
    "total" : 3,
    "successful" : 3,
    "skipped" : 0,
    "failed" : 0
  },
  
# 搜索相关的分片信息
# 上述表示在3个分片的索引中搜索。成功的值是3，失败的值是0，说明所有的分片都有返回
```

##### （3）命中统计数据

```shell
    "total" : 2,
    "max_score" : 1.0,

# total ： 符合检索条件的文档总数
# max_score：这些匹配文档的最高得分
```

##### （4）结果文档

```shell
"hits" : [
      {
        "_index" : "lib",
        "_type" : "user",
        "_id" : "4",
        "_score" : 1.0,
        "_source" : {
          "first_name" : "Jane",
          "last_name" : "Lucy",
          "age" : 13,
          "about" : "I like to collect rock albums",
          "interests" : [
            "music"
          ]
        }
      },
      {
        "_index" : "lib",
        "_type" : "user",
        "_id" : "3",
        "_score" : 1.0,
        "_source" : {
          "first_name" : "Jane",
          "last_name" : "Lucy",
          "age" : 20,
          "about" : "I like to collect rock albums",
          "interests" : [
            "music"
          ]
        }
      }
    ]
    
# hits：实际的检索结果数组
# 数组中的每个元素就是搜索匹配的文档，每个文档都展示了其所属的索引(_index)\类型(_type)、它的ID(_id)、它的得分(_score)以及它再搜索查询中所知道返回的字段的数值(_source)
```

### 2.2.5 更新文档 

```shell
# 更新文档，覆盖方式：修改lib索引下id为1的user的age为36
PUT /lib/user/1
{
  "first_name": "Jane",
  "last_name": "Smith",
  "age": 36,
  "about": "I like to collect rock albums",
  "interests": ["music"]
}

# 更新文档，修改方式：修改lib索引下id为1的user的age为30
# 在内部，依然会查询到这个文档数据，然后进行覆盖操作，步骤如下：
#   1. 从旧文档中检索JSON
#   2. 修改它
#   3. 删除旧文档
#   4. 索引新文档
# 3.10 改变文档内容原理解析 有介绍其原理
POST /lib/user/1/_update
{
  "doc": {
    "age": 30
  }
}
```

### 2.2.6 删除一个文档或索引

```shell
# 删除文档，删除lib索引下的id为1的user
# result标记为deleted，version也增加了。如果删除一条不存在的数据，会响应404
# 删除一个文档也不会立即从磁盘上移除，它只是被标记成已删除。Elasticsearch将会在你之后添加更多索引的时候才会在后台进行删除内容的清理。
DELETE /lib/user/1

#删除索引，删除lib索引
DELETE /lib
```

### 2.2.7 关闭/打开索引

```shell
# 关闭索引，关闭后无法通过ElasticSearch来读取和写入数据，直到再次打开它
POST /lib/_close

# 打开索引
POST /lib/_close
```



## 2.3 使用MultiGet实现批量获取文档

数据准备：

```shell
# 添加索引,3个分片1个副本
PUT /lib
{
  "settings": {
    "number_of_shards": 3,
    "number_of_replicas": 1 
  }
}

# 添加文档，PUT指定id为1
PUT /lib/user/1
{
  "first_name": "Jane",
  "last_name": "Smith",
  "age": 32,
  "about": "I like to collect rock albums",
  "interests": ["music"]
}

# 添加文档，PUT指定id为2
PUT /lib/user/2
{
  "first_name": "Jane",
  "last_name": "Tom",
  "age": 23,
  "about": "I like to collect rock albums",
  "interests": ["music"]
}

# 添加文档，PUT指定id为3
PUT /lib/user/3
{
  "first_name": "Jane",
  "last_name": "Lucy",
  "age": 20,
  "about": "I like to collect rock albums",
  "interests": ["music"]
}
```

### 2.3.1 使用curl命令

![img](file:///D:/Documents/My Knowledge/temp/3923c1bb-9a06-4f25-9526-8916afc0803d/128/index_files/db5f654d-b7b4-4e7f-a4f4-60fb24ae52c8.jpg)

```shell
curl -H "Content-Type: application/json" 'http://192.168.1.128:9200/_mget' -d '{
    "docs": [
        {
            "_index": "lib",
            "_type": "user",
            "_id": 1
        },
        {
            "_index": "lib",
            "_type": "user",
            "_id": 2
        }
    ]
}'
```

### 2.3.2 在客户端工具中(Kibana)

```shell
# _megt批量获取文档
GET /_mget
{
  "docs": [
    {
      "_index": "lib",
      "_type": "user",
      "_id": 1
    },
    {
      "_index": "lib",
      "_type": "user",
      "_id": 2
    },
    {
      "_index": "lib",
      "_type": "user",
      "_id": 3
    }
  ]
}

# _megt批量获取文档,并指定字段
GET /_mget
{
  "docs": [
    {
      "_index": "lib",
      "_type": "user",
      "_id": 1,
      "_source": "interests"
    },
    {
      "_index": "lib",
      "_type": "user",
      "_id": 2,
      "_source": ["age","interests"]
    }
  ]
}

# _mget批量获取同索引同类型下的不同文档 
GET /lib/user/_mget
{
  "docs": [
    {
      "_id": 1
    },
    {
      "_type": "user",
      "_id": 2
    }
  ]
}

# _mget批量获取同索引同类型下的不同文档,另外一种写法（根据id）
GET /lib/user/_mget
{
  "ids": ["1","2"]
}
```



## 2.4 使用Bulk API实现批量操作

### 2.4.1 bulk格式

![img](file:///D:/Documents/My Knowledge/temp/3923c1bb-9a06-4f25-9526-8916afc0803d/128/index_files/df223103-fd1b-4f9b-b68d-1297a31bbda8.jpg)

### 2.4.2 示例：批量删除

```shell
# 使用bulk批量删除文档
POST /lib/user/_bulk
{"delete":{"_index":"lib","_type":"user","_id":1}}
{"delete":{"_index":"lib","_type":"user","_id":2}}
```

### 2.4.3 示例：批量添加

```shell
# 使用bulk批量添加文档
POST /lib2/books/_bulk
{"index":{"_id":1}}
{"title":"Java","price":55}
{"index":{"_id":2}}
{"title":"Htlm5","price":45}
{"index":{"_id":3}}
{"title":"Php","price":35}
{"index":{"_id":4}}
{"title":"Python","price":50}
```

### 2.4.4 示例：批量获取 

```shell
# _mget根据id批量获取文档
GET /lib2/books/_mget
{
  "ids": ["1","2","3","4"]
}
```

### 2.4.5 示例：批量增删改

```shell
# 使用bulk批量增删改
POST /lib2/books/_bulk
{"delete":{"_index":"lib2","_type":"books","_id":4}}
{"create":{"_index":"tt","_type":"ttt","_id":"100"}}
{"name":"lisi"}
{"index":{"_index":"tt","_type":"ttt"}}
{"name":"zhaosi"}
{"update":{"_index":"lib2","_type":"books","_id":"3"}}
{"doc":{"price":58}}


GET /lib2/books/_mget
{
  "ids": ["1","2","3","4"]
}

GET /tt/ttt/_mget
{
  "ids": ["100"]
}
```



### 2.4.6 bulk最大处理数据量

![img](file:///D:/Documents/My Knowledge/temp/3923c1bb-9a06-4f25-9526-8916afc0803d/128/index_files/6b31c658-f36a-44ef-8fa2-cc0d3a7a11d5.jpg)

elasticsearch.yml来修改这个值【不建议修改，太大的话bulk也会慢】：

```shell
# 设置内容的最大容量，默认100mb
http.max_content_length: 100mb
```



## 2.5 版本控制

![img](file:///D:/Documents/My Knowledge/temp/3923c1bb-9a06-4f25-9526-8916afc0803d/128/index_files/b9381448-1144-41de-acbd-b3edff6f91eb.jpg)

### 2.5.1 内部版本控制 

```shell
# 内部版本控制
# 添加文档，PUT指定id为4，version为1
PUT /lib/user/4
{
  "first_name": "Jane",
  "last_name": "Lucy",
  "age": 20,
  "about": "I like to collect rock albums",
  "interests": ["music"]
}

# 修改文档，修改age为23，version为2
PUT /lib/user/4
{
  "first_name": "Jane",
  "last_name": "Lucy",
  "age": 23,
  "about": "I like to collect rock albums",
  "interests": ["music"]
}

# 修改文档，修改age为27，当前版本是2，这里指定版本为3,修改失败
PUT /lib/user/4?version=3
{
  "first_name": "Jane",
  "last_name": "Lucy",
  "age": 27,
  "about": "I like to collect rock albums",
  "interests": ["music"]
}

# 修改文档，修改age为27，当前版本是2，这里指定版本为2,修改成功，版本变为3
PUT /lib/user/4?version=2
{
  "first_name": "Jane",
  "last_name": "Lucy",
  "age": 27,
  "about": "I like to collect rock albums",
  "interests": ["music"]
}

GET /lib/user/4
```

![img](file:///D:/Documents/My Knowledge/temp/3923c1bb-9a06-4f25-9526-8916afc0803d/128/index_files/6f7a72a0-1c4d-416e-bb7a-31b2bf160217.png)

![img](file:///D:/Documents/My Knowledge/temp/3923c1bb-9a06-4f25-9526-8916afc0803d/128/index_files/c7a3cca4-8d0b-40d2-a561-1b98eb0d360b.png)

![img](file:///D:/Documents/My Knowledge/temp/3923c1bb-9a06-4f25-9526-8916afc0803d/128/index_files/0018683e-1de2-4886-83e9-6d4a1e052e78.png)

![img](file:///D:/Documents/My Knowledge/temp/3923c1bb-9a06-4f25-9526-8916afc0803d/128/index_files/615ba6a4-c4ce-487f-9297-3d2d3dc495d8.png)

### 2.5.2 外部版本控制

```shell
# 外部版本控制
# 修改文档，修改age为25，当前版本是3，这里指定版本为3,修改失败
PUT /lib/user/4?version=3&version_type=external
{
  "first_name": "Jane",
  "last_name": "Lucy",
  "age": 25,
  "about": "I like to collect rock albums",
  "interests": ["music"]
}

# 修改文档，修改age为25，当前版本是3，这里指定版本为5,修改成功
PUT /lib/user/4?version=5&version_type=external
{
  "first_name": "Jane",
  "last_name": "Lucy",
  "age": 25,
  "about": "I like to collect rock albums",
  "interests": ["music"]
}
```

![img](file:///D:/Documents/My Knowledge/temp/3923c1bb-9a06-4f25-9526-8916afc0803d/128/index_files/d26c22bf-4d5e-4f5b-bf8b-14716fe6d9a8.png)

![img](file:///D:/Documents/My Knowledge/temp/3923c1bb-9a06-4f25-9526-8916afc0803d/128/index_files/f4394062-bae4-4017-ba71-cb26a2ee535d.png)

## 2.6 什么是Mapping

### 2.6.1 Mapping映射简介

#### 1、 Mapping简介

映射(Mapping)相当于数据表的表结构。ElasticSearch中的映射（Mapping）用来定义一个文档，可以定义所包含的字段以及字段的类型、分词器及属性等等。

映射可以分为动态映射和静态映射：

- 动态映射 （dynamic mapping）：在关系数据库中，需要事先创建数据库，然后在该数据库实例下创建数据表，然后才能在该数据表中插入数据。而ElasticSearch中不需要事先定义映射（Mapping），文档写入ElasticSearch时，会根据文档字段自动识别类型，这种机制称之为动态映射。
- 静态映射 ：在ElasticSearch中也可以事先定义好映射，包含文档的各个字段及其类型等，这种方式称之为静态映射。 

```shell
# 静态映射：若映射（mapping）已经创建，写入数据时，elasticsearch会根据映射和写入数据的key进行对比，然后写入相应的映射中；
# 动态映射：如果mapping没有创建，elasticsearch将会根据写入的数据的key自行创建相应的mapping，并写入数据。

# 这里没有事先创建索引myindex和article的映射，将会动态映射
PUT /myindex/article/1
{
  "post_date": "2018-05-10",
  "title": "Java",
  "content": "java is the best language",
  "author_id": 119
}

# 可以使用_mapping查询索引的mapping
GET /myindex/article/_mapping
# 返回结果
{
  "myindex" : {
    "mappings" : {
      "article" : {
        "properties" : {
          "author_id" : {
            "type" : "long"
          },
          "content" : {
            "type" : "text",
            "fields" : {
              "keyword" : {
                "type" : "keyword",
                "ignore_above" : 256
              }
            }
          },
          "post_date" : {
            "type" : "date"
          },
          "title" : {
            "type" : "text",
            "fields" : {
              "keyword" : {
                "type" : "keyword",
                "ignore_above" : 256
              }
            }
          }
        }
      }
    }
  }
}
```

#### 2、示例：手动创建mapping

```shell
# 手动创建mapping
PUT /lib6
{
  "settings": {
    "number_of_shards": 3,
    "number_of_replicas": 1
  },
  "mappings": {
    "books": {
      "properties": {
        "title": {
          "type": "text"
        },
        "name": {
          "type": "text",
          "analyzer": "standard"
        },
        "publish_date": {
          "type": "date",
          "index": false
        },
        "price": {
          "type": "double"
        },
        "number": {
          "type": "integer"
        }
      }
    }
  }
}
```



#### 3、ElasticSearch 7.x 默认不再支持指定索引类型

在elasticsearch7.x上执行：

```shell
PUT es_test
{
  "settings": {
    "number_of_shards": 3,
    "number_of_replicas": 0
  },
  "mappings": {
    "books": {
      "properties": {
        "title": {
          "type": "text"
        },
        "name": {
          "type": "text",
          "index": false
        },
        "publish_date": {
          "type": "date",
          "index": false
        },
        "price": {
          "type": "double"
        },
        "number": {
          "type": "object",
          "dynamic": true
        }
      }
    }
  }
}
```

执行结果则会出错：**Root mapping definition has unsupported parameters**

```shell
{
  "error": {
    "root_cause": [
      {
        "type": "mapper_parsing_exception",
        "reason": "Root mapping definition has unsupported parameters:  [books : {properties={number={dynamic=true, type=object}, price={type=double}, name={index=false, type=text}, title={type=text}, publish_date={index=false, type=date}}}]"
      }
    ],
    "type": "mapper_parsing_exception",
    "reason": "Failed to parse mapping [_doc]: Root mapping definition has unsupported parameters:  [books : {properties={number={dynamic=true, type=object}, price={type=double}, name={index=false, type=text}, title={type=text}, publish_date={index=false, type=date}}}]",
    "caused_by": {
      "type": "mapper_parsing_exception",
      "reason": "Root mapping definition has unsupported parameters:  [books : {properties={number={dynamic=true, type=object}, price={type=double}, name={index=false, type=text}, title={type=text}, publish_date={index=false, type=date}}}]"
    }
  },
  "status": 400
}
```

如果在6.x上执行，则会正常执行：

```shell
{
  "acknowledged" : true
}
```

出现这个的原因是，elasticsearch7默认不在支持指定索引类型，默认索引类型是_doc，如果想改变，则配置include_type_name: true 即可(这个没有测试，官方文档说的，无论是否可行，建议不要这么做，因为elasticsearch8后就不在提供该字段)。官方文档：https://www.elastic.co/guide/en/elasticsearch/reference/current/removal-of-types.html

所以在Elasticsearch7中应该这么创建索引：

```shell
PUT /test
{
  "settings": {
    "number_of_shards": 3,
    "number_of_replicas": 2
  },
  "mappings": {
    "properties": {
      "id": {
        "type": "long"
      },
      "name": {
        "type": "text",
        "analyzer": "ik_smart"
      },
      "text": {
        "type": "text",
        "analyzer": "ik_max_word"
      }
    }
  }
}
```



### 2.6.2 支持的数据类型

#### 1、核心数据类型（Core datatype）

**字符串**：string，string类型包含 text 和 keyword。

- text：该类型被用来索引长文本，在创建索引前会将这些文本进行分词，转化为词的组合，建立索引；允许es来检索这些词，text类型不能用来排序和聚合。
- keyword：该类型不需要进行分词，可以被用来检索过滤、排序和聚合，keyword类型只能用本身来进行检索（不可用text分词后的模糊检索）。

**数指型**：long、integer、short、byte、double、float

**日期型**：date

**布尔型**：boolean

**二进制型**：binary

```shell
# 除字符串类型以外，其他类型必须要进行精确查询，因为除字符串外其他类型不会进行分词。
#新增数据
PUT /myindex/article/2
{
  "post_date": "2018-05-12",
  "title": "html",
  "content": "I like html",
  "author_id": 120
}

PUT /myindex/article/3
{
  "post_date": "2018-05-16",
  "title": "es",
  "content": "Es is distributed document store",
  "author_id": 110
}

# 查不出数据
GET /myindex/article/_search?q=post_date:2018

# 查出数据
GET /myindex/article/_search?q=post_date:2018-05-10

# 查出数据
GET /myindex/article/_search?q=content:html
```



#### 2、复杂数据类型（Complex datatypes）

**数组类型**（Array datatype），数组类型不需要专门指定数组元素的type，例如：

-   字符型数组：[“one”，“two”]
-   整型数组：[1, 2]
-   数组型数组：[1, [2, 3]] 等价于 [1, 2, 3]
-   对象数组：[{“name”: “Mary”, “age”: 12}, {“name”: “John”, “age”: 10}]

**对象类型**（Object datatype）：object 用于单个Json对象。

**嵌套类型**（Nested datatype）：nested 用于Json数组。

```shell
# object对象类型
PUT /lib3/person/1
{
  "name": "Tom",
  "age": 25,
  "birthday": "1985-12-12",
  "address": {
    "country": "china",
    "province": "hubei",
    "city": "wuhan"
  }
}
# 底层存储格式——符合面向对象思想
{
  "name": ["Tom"],
  "age": [25],
  "birthday": ["1985-12-12"],
  "address.country": ["china"],
  "address.province": ["hubei"],
  "address.city": ["wuhan"]
}

# 嵌套类型
PUT /lib4/person/1
{
  "persons": [
    {
      "name": "lisi",
      "age": 27
    },
    {
      "name": "wangwu",
      "age": 26
    },
    {
      "name": "zhangsan",
      "age": 23
    }
  ]
}
# 底层存储
{
  "persons": ["lisi", "wangwu", "zhangsan"],
  "persons": [27, 26, 23]
}
```



#### 3、地理位置类型（Geo datatypes）

**地理坐标类型**（Geo-point datatype）：geo_point 用于经纬度坐标。

**地理形状类型**（Geo-Shape datatype）：geo_shape 用于类似于多边形的复杂形状。

#### 4、特定类型（Specialised datatypes）

**IPv4 类型**（IPv4 datatype）：ip 用于IPv4 地址。

**Completion 类型**（Completion datatype）：completion 提供自动补全建议。

**Token count 类型**（Token count datatype）：token_count 用于统计做子标记的字段的index数目，该值会一直增加，不会因为过滤条件而减少。

**mapper-murmur3 类型**：通过插件，可以通过_murmur3_来计算index的哈希值。

**附加类型**（Attachment datatype）：采用mapper-attachments插件，可支持_attachments_索引，例如 Microsoft office 格式，Open Documnet 格式， ePub，HTML等。

### 2.6.3 支持的属性

- **enabled**：仅存储、不做搜索和聚合分析

```shell
"enabled":true （缺省）| false
```

- **index**：是否构建倒排索引（即是否分词，设置false，字段将不会被索引）

```shell
"index": true（缺省）| false
```

- **index_option**：存储倒排索引的哪些信息

4个可选参数：

​    docs：索引文档号；

​    freqs：文档号+词频；

​    positions：文档号+词频+位置，通常用来距离查询；

​     offsets：文档号+词频+位置+偏移量，通常被使用在高亮字段；

分词字段默认时positions，其他默认时docs。

```shell
"index_options": "docs"
```

- **norms**：是否归一化相关参数、如果字段仅用于过滤和聚合分析，可关闭

分词字段默认配置，不分词字段：默认{“enable”: false}，存储长度因子和索引时boost，建议对需要参加评分字段使用，会额外增加内存消耗。

```shell
"norms": {"enable": true, "loading": "lazy"}
```

- **doc_value**：是否开启doc_value，用户聚合和排序分析

对not_analyzed字段，默认都是开启，分词字段不能使用，对排序和聚合能提升较大性能，节约内存

```shell
"doc_value": true（缺省）| false
```

- **fielddata**：是否为text类型启动fielddata，实现排序和聚合分析

针对分词字段，参与排序或聚合时能提高性能，不分词字段统一建议使用doc_value。

```shell
"fielddata": {"format": "disabled"}
```

- **store**：是否单独设置此字段的是否存储而从_source字段中分离，只能搜索，不能获取值

```shell
"store": false（默认）| true
```

- **coerce**：是否开启自动数据类型转换功能，比如：字符串转数字，浮点转整型

```shell
"coerce: true（缺省）| false"
```

- **multifields**：灵活使用多字段解决多样的业务需求

- **dynamic**：控制mapping的自动更新

```shell
"dynamic": true（缺省）| false | strict

# true：遇到陌生字段就 dynamic mapping
# false：遇到陌生字段就忽略
# strict：遇到陌生字段就报错

# 设置为报错
PUT /lib8
{
    "settings":{
        "number_of_shards": 3,
        "number_of_replicas": 0
    },
    "mappings":{
        "user":{
            "dynamic": "strict",
            "properties": {
                "name": {"type": "text"},
                "address": {"type": "object", "dynamic": true}
            }
        }
    }
}

# 插入以下文档，将会报错
# user此层设置dynamic是strict，在user层内设置age将报错
# 在address层设置dynamic是ture，将动态映射生成字段
PUT /lib8/user/1
{
  "name": "lisi",
  "age": "20",
  "address": {
    "province": "beijing",
    "city": "beijing"
  }
}
```

- **data_detection**：是否自动识别日期类型

```shell
data_detection：true（缺省）| false

# 默认值是true，会按照一定格式识别date，比如yyyy-MM-dd
# 可以手动关闭某个type的date_detection
PUT /lib9
{
  "settings": {
    "number_of_shards": 3,
    "number_of_replicas": 0
  },
  "mappings": {
    "user": {
      "date_detection": false
    }
  }
}

# 定制 dynamic mapping template（type）
# match: *_en 表示是字段名是以_en结尾
# match_mapping_type: string 表示是添加的所有string类型的字段都用这个模板
# mapping.type: text 表示是字段的值是text类型
# mapping.analyzer: english 表示是分词是english分词器
PUT /my_index
{
  "mappings": {
    "my_type": {
      "dynamic_templates": [
        {
          "en": {
            "match": "*_en",
            "match_mapping_type": "string",
            "mapping": {
              "type": "text",
              "analyzer": "english"
            }
          }
        }
      ]
    }
  }
}

# 使用模板
PUT /my_index/my_type/3
{
    "title_en": "this is my dog"
}
# 未使用模板，使用standard分词器
PUT /my_index/my_type/5
{
    "title": "this is my cat"
}

# 查询title_en，将无法查出数据（english分词）
GET /my_index/my_type/_search
{
    "query": {
        "match": {
            "title_en": "is"
        }
    }
}
# 查询title，将查出数据（standard分词）
GET /my_index/my_type/_search
{
    "query": {
        "match": {
            "title": "is"
        }
    }
}
```

- **analyzer**：指定分词器，默认分词器为standard analyzer

```shell
"analyzer": "ik"
```

- **boost**：字段级别的分数加权，默认值是1.0

```shell
"boost": 1.23
```

- **fields**：可以对一个字段提供多种索引模式，同一个字段的值，一个分词，一个不分词

```shell
"fields": {"raw": {"type": "string", "index": "not_analyzed"}}
```

- **ignore_above**：超过100个字符的文本，将会被忽略，不被索引

```shell
"ignore_above": 100
```

- **include_in_all**：设置是否此字段包含在_all字段中，默认时true，除非index设置成no

```shell
"include_in_all": true
```

- **null_value**：设置一些缺失字段的初始化，只有string可以使用，分词字段的null值也会被分词

```shell
"null_value": "NULL"
```

- **position_increament_gap**：影响距离查询或近似查询，可以设置在多值字段的数据上或分词字段上，查询时可以指定slop间隔，默认值时100

```shell
"position_increament_gap": 0
```

- **search_analyzer**：设置搜索时的分词器，默认跟analyzer是一致的，比如index时用standard+ngram，搜索时用standard用来完成自动提示功能

```shell
"search_analyzer": "ik"
```

- **similarity**：默认时TF/IDF算法，指定一个字段评分策略，仅仅对字符串型和分词类型有效

```shell
"similarity": "BM25"
```

- **trem_vector**：默认不存储向量信息，支持参数yes（term存储），with_positions（term+位置），with_offsets（term+偏移量），with_positions_offsets（term+位置+偏移量）对快速高亮fast vector highlighter能提升性能，但开启又会加大索引体积，不适合大数据量用

```shell
"trem_vector": "no"
```

### 2.6.4 Mapping 字段设置流程

![img](file:///D:/Documents/My Knowledge/temp/3923c1bb-9a06-4f25-9526-8916afc0803d/128/index_files/f9da77be-3776-4010-bdee-bcfe7dfaf4a8.png)

## **2.7 基本查询（英文 Query查询）**

### 2.7.1 数据准备

```shell
############数据准备####################
PUT /lib5
{
  "settings": {
    "number_of_shards": 3,
    "number_of_replicas": 0
  },
  "mappings": {
    "user": {
      "properties": {
        "name": {
          "type": "text"
        },
        "addres": {
          "type": "text"
        },
        "age": {
          "type": "integer"
        },
        "interests": {
          "type": "text"
        },
        "birthday": {
          "type": "date"
        }
      }
    }
  }
}

PUT /lib5/user/1
{
  "name": "zhaoliu",
  "address": "hei long jiang sheng tie ling shi",
  "age": 50,
  "birthday": "1970-12-12",
  "interests": "xi huan hejiu,duanlian,lvyou"
}

PUT /lib5/user/2
{
  "name": "zhaoming",
  "address": "bei jing hai dian qu qing he zhen",
  "age": 20,
  "birthday": "1998-10-12",
  "interests": "xi huan hejiu,duanlian,changge"
}


PUT /lib5/user/3
{
  "name": "lisi",
  "address": "bei jing hai dian qu qing he zhen",
  "age": 23,
  "birthday": "1998-10-12",
  "interests": "xi huan hejiu,duanlian,changge"
}


PUT /lib5/user/4
{
  "name": "wangwu",
  "address": "bei jing hai dian qu qing he zhen",
  "age": 26,
  "birthday": "1995-10-12",
  "interests": "xi huan biancheng,tingyinyue,lvyou"
}


PUT /lib5/user/5
{
  "name": "zhangsan",
  "address": "bei jing chao yang qu",
  "age": 29,
  "birthday": "1988-10-12",
  "interests": "xi huan tingyinyue,changge,tiaowu"
}

# 查询name为lisi的user
GET /lib5/user/_search?q=name:lisi

# 查询interests包含changge的user，根据age降序
GET /lib5/user/_search?q=interests:changge&sort=age:desc
```

### 2.7.2 term查询和terms查询(精确匹配)

![img](file:///D:/Documents/My Knowledge/temp/3923c1bb-9a06-4f25-9526-8916afc0803d/128/index_files/e1c3ff6b-0cbd-4a17-94ff-08fc35326f3a.jpg)

```shell
# term:查询某个字段里含有某个关键词的文档
GET /lib5/user/_search/
{
  "query": {
    "term": {
      "interests": {
        "value": "changge"
      }
    }
  }
}

# terms:查询某个字段里含有多个关键词的文档,interests含有heijiu或changge的都被查出来
GET /lib5/user/_search/
{
  "query": {
    "terms": {
      "interests": [
        "hejiu",
        "changge"
      ]
    }
  }
}
```

### 2.7.3 from和size控制查询返回的数量(分页)

```shell
# form:从哪一个文档开始
# size:需要的个数
GET /lib5/user/_search
{
  "from": 0,
  "size": 2,
  "query": {
    "terms": {
      "interests": [
        "hejiu",
        "changge"
      ]
    }
  }
}
```

### 2.7.4 version返回版本号

```shell
# 返回版本号
GET /lib5/user/_search
{
 "version": true, 
  "query": {
    "terms": {
      "interests": [
        "hejiu",
        "changge"
      ]
    }
  }
}
```



### 2.7.5 match查询

![img](file:///D:/Documents/My Knowledge/temp/3923c1bb-9a06-4f25-9526-8916afc0803d/128/index_files/232f3566-1171-48d6-a248-a3a107d02306.jpg)

match 查询是一个标准查询，不管你需要全文本查询还是精确查询基本上都要用到它。

如果你使用 match 查询一个全文本字段，它会在真正查询之前用分析器先分析 match 一下查询字符。

如果用 match 下指定了一个确切值，在遇到数字，日期，布尔值或者 not_analyzed 的字符串时，它将为你搜索你给定的值。

 

```shell
# match查询，查询name含有zhaoliu zhaoming的文档,用到了分词，结果查出了zhaoliu和zhaoming两个文档
# 默认使用OR操作
GET /lib5/user/_search
{
  "query": {
    "match": {
      "name": "zhaoliu zhaoming"
    }
  }
}

# 修改使用and操作，必须interests必须同时包含duanlian和changge
GET /lib7/user/_search
{
  "query": {
    "match": {
      "interests": {
        "query": "duanlian changge",
        "operator": "and"
      }
    }
  }
}

# match查询age为20的
GET /lib5/user/_search
{
  "query": {
    "match": {
      "age": 20
    }
  }
}
```

![img](file:///D:/Documents/My Knowledge/temp/3923c1bb-9a06-4f25-9526-8916afc0803d/128/index_files/e773adc8-c0f4-48ce-8d1f-a19ca0426987.jpg)

```shell
# match_all查询所有文档
GET /lib5/user/_search
{
  "query": {
    "match_all": {}
  }
}
```

![img](file:///D:/Documents/My Knowledge/temp/3923c1bb-9a06-4f25-9526-8916afc0803d/128/index_files/f904158b-a929-4781-a201-47aadf4f40ed.jpg)

```shell
# multi_match 指定多个字段查询,查询interests和name字段中含有changge的文档
GET /lib5/user/_search
{
  "query": {
    "multi_match": {
      "query": "changge",
      "fields": ["interests","name"]
    }
  }
}
```

![img](file:///D:/Documents/My Knowledge/temp/3923c1bb-9a06-4f25-9526-8916afc0803d/128/index_files/f7fba2e2-b1a4-423d-bf52-c2a4f57b70ca.jpg)

```shell
# match_phrase短语匹配查询，查询interests必须包含duanlian,changge短语的文档，精确匹配
GET /lib5/user/_search
{
  "query": {
    "match_phrase": {
      "interests": "duanlian,changge"
    }
  }
}
```

### 2.7.6 _source指定返回的字段

```shell
# _source指定返回的字段
GET /lib5/user/_search
{
  "_source": ["address","name"], 
  "query": {
    "match": {
      "interests": "changge"
    }
  }
}
```

### 2.7.7 includes和excludes控制加载的字段

```shell
# includes指定包含哪些字段
# excludes指定排除哪些字段
GET /lib5/user/_search
{
  "query": {
    "match_all": {}
  },
  "_source": {
    "includes": ["name","address"],
    "excludes": ["age","birthday"]
  }
}

# includes和excludes使用通配符
GET /lib5/user/_search
{
  "query": {
    "match_all": {}
  },
  "_source": {
    "includes": "addr*",
    "excludes": ["age","bir*"]
  }
}
```

### 2.7.8 sort排序

```shell
# sort排序，desc降序，asc升序
GET /lib5/user/_search
{
  "query": {
    "match_all": {}
  },
  "sort": [
    {
      "age": {
        "order": "desc"
      }
    }
  ]
}
```

### 2.7.9 match_phrase_prefix前缀匹配查询

```shell
# match_phrase_prefix前缀匹配查询，查询name字段以zhao开头的文档
GET /lib5/user/_search
{
  "query": {
    "match_phrase_prefix": {
      "name": "zhao"
    }
  }
}
```

### 2.7.10 range范围查询

![img](file:///D:/Documents/My Knowledge/temp/3923c1bb-9a06-4f25-9526-8916afc0803d/128/index_files/1e075ad3-a497-491f-828b-8cf8bd5fe567.jpg)

```shell
# range范围查询
# from大于，to小于
# 下面等同于gt和lt
GET /lib5/user/_search
{
  "query": {
    "range": {
      "birthday": {
        "from": "1990-10-10",
        "to": "2018-05-01"
      }
    }
  }
}

# include_lower，是否包含左边界
# include_upper，是否包含右边界
# 下面等同于gte和lt
GET /lib5/user/_search
{
  "query": {
    "range": {
      "age": {
        "from": 20,
        "to": 26,
        "include_lower": true,
        "include_upper": false
      }
    }
  }
}

# gt大于，lt小于
GET /lib5/user/_search
{
  "query": {
    "range": {
      "birthday": {
        "gt": "1988-10-12",
        "lt": "2018-05-01"
      }
    }
  }
}

# gte大于等于，lte小于等于
GET /lib5/user/_search
{
  "query": {
    "range": {
      "birthday": {
        "gte": "1988-10-12",
        "lte": "2018-05-01"
      }
    }
  }
}
```

### 2.7.11 wildcard查询

![img](file:///D:/Documents/My Knowledge/temp/3923c1bb-9a06-4f25-9526-8916afc0803d/128/index_files/8a5f703e-9d4b-4c9a-80e3-76dffd25a708.jpg)

```shell
# wildcard查询name以zhao开头的文档，查出了zhaoming和zhaoliu
GET /lib5/user/_search
{
  "query": {
    "wildcard": {
      "name": {
        "value": "zhao*"
      }
    }
  }
}

# wildcard查询name第三个字符任意的文档，查出了lisi
GET /lib5/user/_search
{
  "query": {
    "wildcard": {
      "name": {
        "value": "li?i"
      }
    }
  }
}
```

### 2.7.12 fuzzy实现模糊查询

![img](file:///D:/Documents/My Knowledge/temp/3923c1bb-9a06-4f25-9526-8916afc0803d/128/index_files/dd06daeb-18b9-47fe-9258-d42b541bc7a3.jpg)

```shell
# fuzzy模糊查询
# 查询name字段含有zholiu的文档，查出了zhouliu
GET /lib5/user/_search
{
  "query": {
    "fuzzy": {
      "name": {
        "value": "zholiu"
      }
    }
  }
}

# fuzzy模糊查询
# 查询interests字段含有chagge的文档
GET /lib5/user/_search
{
  "query": {
    "fuzzy": {
      "interests": {
        "value": "chagge"
      }
    }
  }
}
```

### 2.7.13 highlight高亮搜索结果

```shell
# highlight高亮搜索结果
# 查询出的所有interests含changge的文档中，interests字段中的changge都被高亮，默认被<em>和</em> 标记
GET /lib5/user/_search
{
  "query": {
    "match": {
      "interests": "changge"
    }
  },
  "highlight": {
    "fields": {
      "interests": {}
    }
  }
}

# pre_tags和post_tags自定义高亮标签
GET /lib5/user/_search
{
  "query": {
    "match": {
      "interests": "changge"
    }
  },
  "highlight": {
    "pre_tags": [
      "<tag1>",
      "<tag2>"
    ],
    "post_tags": [
      "</tag1>",
      "</tag2>"
    ],
    "fields": {
      "interests": {}
    }
  }
}
```

### 2.7.14 boost权重

有些时候，我们可能需要对某些词增加权重来影响该条数据的得分。

```shell
# boost权重
GET /lib5/user/_search
{
  "query": {
    "match": {
      "interests": {
        "query": "changge",
        "boost": 10
      }
    }
  }
}
```

![img](file:///D:/Documents/My Knowledge/temp/3923c1bb-9a06-4f25-9526-8916afc0803d/128/index_files/7cbca27a-dc6f-4186-8912-904fcdde4084.png)

如果不设置权重查询结果如下：

![img](file:///D:/Documents/My Knowledge/temp/3923c1bb-9a06-4f25-9526-8916afc0803d/128/index_files/9be51d13-9936-4eac-93ba-f0df28ecee2a.png)

## **2.7-2 基本查询（中文），使用中文分词器IK**

### 2.7.1 数据准备

![img](file:///D:/Documents/My Knowledge/temp/3923c1bb-9a06-4f25-9526-8916afc0803d/128/index_files/e0eb37b8-a07a-40a9-957a-928ed4ec2437.jpg)

```shell
###########数据准备，中文query##############
PUT /lib7
{
  "settings": {
    "number_of_shards": 3,
    "number_of_replicas": 0
  },
  "mappings": {
    "user": {
      "properties": {
        "name": {
          "type": "text",
          "analyzer": "ik_max_word"
        },
        "address": {
          "type": "text",
          "analyzer": "ik_max_word"
        },
        "age": {
          "type": "integer"
        },
        "interests": {
          "type": "text",
          "analyzer": "ik_max_word"
        },
        "birthday": {
          "type": "date"
        }
      }
    }
  }
}

PUT /lib7/user/1
{
  "name": "赵六",
  "address": "黑龙江省铁岭",
  "age": 50,
  "birthday": "1970-12-12",
  "interests": "喜欢喝酒，锻炼，说相声"
}

PUT /lib7/user/2
{
  "name": "赵明",
  "address": "北京海淀区清河",
  "age": 20,
  "birthday": "1998-10-12",
  "interests": "喜欢喝酒，锻炼，唱歌"
}


PUT /lib7/user/3
{
  "name": "lisi",
  "address": "北京海淀区清河",
  "age": 23,
  "birthday": "1998-10-12",
  "interests": "喜欢喝酒，锻炼，唱歌"
}


PUT /lib7/user/4
{
  "name": "王五",
  "address": "北京海淀区清河",
  "age": 26,
  "birthday": "1995-10-12",
  "interests": "喜欢编程，听音乐，旅游"
}


PUT /lib7/user/5
{
  "name": "张三",
  "address": "北京海淀区清河",
  "age": 29,
  "birthday": "1988-10-12",
  "interests": "喜欢摄影，听音乐，跳舞"
}

# 查询name为lisi的user
GET /lib7/user/_search?q=name:lisi


###########数据准备，中文query##############
```

### 2.7.2 term查询和terms查询(精确匹配)

![img](file:///D:/Documents/My Knowledge/temp/3923c1bb-9a06-4f25-9526-8916afc0803d/128/index_files/e1c3ff6b-0cbd-4a17-94ff-08fc35326f3a.jpg)

```shell
# term:查询某个字段里含有某个关键词的文档
GET /lib7/user/_search/
{
  "query": {
    "term": {
      "interests": {
        "value": "唱歌"
      }
    }
  }
}

# terms:查询某个字段里含有多个关键词的文档,interests含有喝酒或唱歌的都被查出来
GET /lib7/user/_search/
{
  "query": {
    "terms": {
      "interests": [
        "喝酒",
        "唱歌"
      ]
    }
  }
}
```

### 2.7.3 from和size控制查询返回的数量(分页)

```shell
# form:从哪一个文档开始
# size:需要的个数
GET /lib7/user/_search
{
  "from": 0,
  "size": 2,
  "query": {
    "terms": {
      "interests": [
        "喝酒",
        "唱歌"
      ]
    }
  }
}
```

### 2.7.4 version返回版本号

```shell
# 返回版本号
GET /lib7/user/_search
{
 "version": true, 
  "query": {
    "terms": {
      "interests": [
        "喝酒",
        "唱歌"
      ]
    }
  }
}
```

### 2.7.5 match查询

![img](file:///D:/Documents/My Knowledge/temp/3923c1bb-9a06-4f25-9526-8916afc0803d/128/index_files/232f3566-1171-48d6-a248-a3a107d02306.jpg)

match 查询是一个标准查询，不管你需要全文本查询还是精确查询基本上都要用到它。

如果你使用 match 查询一个全文本字段，它会在真正查询之前用分析器先分析 match 一下查询字符。

如果用 match 下指定了一个确切值，在遇到数字，日期，布尔值或者 not_analyzed 的字符串时，它将为你搜索你给定的值。

 

```shell
# match查询，查询name含有赵六或赵明的文档,用到了分词，结果查出了赵六和赵明两个文档
# 默认使用OR操作
GET /lib7/user/_search
{
  "query": {
    "match": {
      "name": "赵六 赵明"
    }
  }
}

# 修改使用and操作，必须interests必须同时包含锻炼和唱歌
GET /lib7/user/_search
{
  "query": {
    "match": {
      "interests": {
        "query": "锻炼 唱歌",
        "operator": "and"
      }
    }
  }
}

# match查询age为20的
GET /lib7/user/_search
{
  "query": {
    "match": 
      "age": 20
    }
  }
}
```

![img](file:///D:/Documents/My Knowledge/temp/3923c1bb-9a06-4f25-9526-8916afc0803d/128/index_files/e773adc8-c0f4-48ce-8d1f-a19ca0426987.jpg)

```shell
# match_all查询所有文档
GET /lib7/user/_search
{
  "query": {
    "match_all": {}
  }
}
```

![img](file:///D:/Documents/My Knowledge/temp/3923c1bb-9a06-4f25-9526-8916afc0803d/128/index_files/f904158b-a929-4781-a201-47aadf4f40ed.jpg)

```shell
# multi_match 指定多个字段查询,查询interests和name字段中含有唱歌的文档
GET /lib7/user/_search
{
  "query": {
    "multi_match": {
      "query": "唱歌",
      "fields": ["interests","name"]
    }
  }
}
```

![img](file:///D:/Documents/My Knowledge/temp/3923c1bb-9a06-4f25-9526-8916afc0803d/128/index_files/f7fba2e2-b1a4-423d-bf52-c2a4f57b70ca.jpg)

```shell
# match_phrase短语匹配查询，查询interests必须包含 锻炼，唱歌 短语的文档，精确匹配
GET /lib7/user/_search
{
  "query": {
    "match_phrase": {
      "interests": "锻炼，唱歌"
    }
  }
}
```

### 2.7.6 _source指定返回的字段

```shell
# _source指定返回的字段
GET /lib7/user/_search
{
  "_source": ["address","name"], 
  "query": {
    "match": {
      "interests": "唱歌"
    }
  }
}
```

### 2.7.7 includes和excludes控制加载的字段

```shell
# includes指定包含哪些字段
# excludes指定排除哪些字段
GET /lib7/user/_search
{
  "query": {
    "match_all": {}
  },
  "_source": {
    "includes": ["name","address"],
    "excludes": ["age","birthday"]
  }
}

# includes和excludes使用通配符
GET /lib7/user/_search
{
  "query": {
    "match_all": {}
  },
  "_source": {
    "includes": "addr*",
    "excludes": ["age","bir*"]
  }
}
```

### 2.7.8 sort排序

```shell
# sort排序，desc降序，asc升序
GET /lib7/user/_search
{
  "query": {
    "match_all": {}
  },
  "sort": [
    {
      "age": {
        "order": "desc"
      }
    }
  ]
}
```

### 2.7.9 match_phrase_prefix前缀匹配查询

```shell
# match_phrase_prefix前缀匹配查询，查询name字段以赵开头的文档
GET /lib7/user/_search
{
  "query": {
    "match_phrase_prefix": {
      "name": "赵"
    }
  }
}
```

### 2.7.10 range范围查询

![img](file:///D:/Documents/My Knowledge/temp/3923c1bb-9a06-4f25-9526-8916afc0803d/128/index_files/1e075ad3-a497-491f-828b-8cf8bd5fe567.jpg)

```shell
# range范围查询
# from大于，to小于
# 下面等同于gt和lt
GET /lib7/user/_search
{
  "query": {
    "range": {
      "birthday": {
        "from": "1990-10-10",
        "to": "2018-05-01"
      }
    }
  }
}

# include_lower，是否包含左边界
# include_upper，是否包含右边界
# 下面等同于gte和lt
GET /lib7/user/_search
{
  "query": {
    "range": {
      "age": {
        "from": 20,
        "to": 26,
        "include_lower": true,
        "include_upper": false
      }
    }
  }
}

# gt大于，lt小于
GET /lib7/user/_search
{
  "query": {
    "range": {
      "birthday": {
        "gt": "1988-10-12",
        "lt": "2018-05-01"
      }
    }
  }
}

# gte大于等于，lte小于等于
GET /lib7/user/_search
{
  "query": {
    "range": {
      "birthday": {
        "gte": "1988-10-12",
        "lte": "2018-05-01"
      }
    }
  }
}
```

### 2.7.11 wildcard查询

![img](file:///D:/Documents/My Knowledge/temp/3923c1bb-9a06-4f25-9526-8916afc0803d/128/index_files/8a5f703e-9d4b-4c9a-80e3-76dffd25a708.jpg)

```shell
# wildcard查询name以赵开头的文档，查出了赵六和赵明
GET /lib7/user/_search
{
  "query": {
    "wildcard": {
      "name": {
        "value": "赵*"
      }
    }
  }
}

# wildcard查询name第三个字符任意的文档，查出了lisi
GET /lib7/user/_search
{
  "query": {
    "wildcard": {
      "name": {
        "value": "li?i"
      }
    }
  }
}
```

### 2.7.12 fuzzy实现模糊查询

![img](file:///D:/Documents/My Knowledge/temp/3923c1bb-9a06-4f25-9526-8916afc0803d/128/index_files/dd06daeb-18b9-47fe-9258-d42b541bc7a3.jpg)

```shell
# fuzzy模糊查询
# 查询name字段含有六的文档，查出了赵六
GET /lib7/user/_search
{
  "query": {
    "fuzzy": {
      "name": {
        "value": "六"
      }
    }
  }
}

# fuzzy模糊查询
# 查询interests字段含有唱歌的文档
GET /lib7/user/_search
{
  "query": {
    "fuzzy": {
      "interests": {
        "value": "唱歌"
      }
    }
  }
}
```

### 2.7.13 highlight高亮搜索结果

```shell
# highlight高亮搜索结果
# 查询出的所有interests含唱歌的文档中，interests字段中的唱歌都被高亮，默认被<em>和</em> 标记
GET /lib7/user/_search
{
  "query": {
    "match": {
      "interests": "唱歌"
    }
  },
  "highlight": {
    "fields": {
      "interests": {}
    }
  }
}

# pre_tags和post_tags自定义高亮标签
GET /lib7/user/_search
{
  "query": {
    "match": {
      "interests": "唱歌"
    }
  },
  "highlight": {
    "pre_tags": [
      "<tag1>",
      "<tag2>"
    ],
    "post_tags": [
      "</tag1>",
      "</tag2>"
    ],
    "fields": {
      "interests": {}
    }
  }
}

```

### 2.7.14 boost权重

有些时候，我们可能需要对某些词增加权重来影响该条数据的得分。

```shell
# boost权重
GET /lib7/user/_search
{
  "query": {
    "match": {
      "interests": {
        "query": "唱歌",
        "boost": 10
      }
    }
  }
}
```

![img](file:///D:/Documents/My Knowledge/temp/3923c1bb-9a06-4f25-9526-8916afc0803d/128/index_files/12e812de-dc01-4657-9161-9273f4276051.png)

如果不设置权重查询结果如下：

![img](file:///D:/Documents/My Knowledge/temp/3923c1bb-9a06-4f25-9526-8916afc0803d/128/index_files/109f6cf8-c3bc-4af1-9beb-0161b045f341.png)

## 2.8 Filter查询

### 2.8.1 数据准备

![img](file:///D:/Documents/My Knowledge/temp/3923c1bb-9a06-4f25-9526-8916afc0803d/128/index_files/624b2ff4-fb6b-4603-b8b6-9c7f839bce51.jpg)

```shell
#########Filter查询，数据准备##########
# 使用bulk批量添加文档
POST /lib10/items/_bulk
{"index":{"_id":1}}
{"itemID":"ID100123","price":40}
{"index":{"_id":2}}
{"itemID":"ID100124","price":50}
{"index":{"_id":3}}
{"itemID":"ID100125","price":25}
{"index":{"_id":4}}
{"itemID":"ID100126","price":30}
{"index":{"_id":5}}
{"itemID":"ID100127","price":null}

#########Filter查询，数据准备##########
```

### **2.8.2 简单的过滤查询(bool filter)**

```shell
# 简单过滤查询
# term指定特定字段
GET /lib10/items/_search
{
  "query": {
    "bool": {
      "filter": {
        "term": {
          "price": 40
        }
      }
    }
  }
}
# terms指定多个字段
GET /lib10/items/_search
{
  "query": {
    "bool": {
      "filter": {
        "terms": {
          "price": [25,40]
        }
      }
    }
  }
}

# 查不出来文档，因为itemID默认分词后，倒排索引中ID为小写id，将ID100123改为id100123就能查出来
GET /lib10/items/_search
{
  "query": {
    "bool": {
      "filter": {
        "term": {
          "itemID": "ID100123"
        }
      }
    }
  }
}

# 查看lib10映射
GET /lib10/_mapping

# 若不希望商品id字段被分词，则重新创建映射
DELETE lib10

# index：是否构建倒排索引（即是否分词，设置false，字段将不会被索引）,这样就不能通过itemID查询了
PUT /lib10
{
  "mappings": {
    "items": {
      "properties": {
        "itemID": {
          "type": "text",
          "index": false
        }
      }
    }
  }
}
# 重新数据准备
POST /lib10/items/_bulk
{"index":{"_id":1}}
{"itemID":"ID100123","price":40}
{"index":{"_id":2}}
{"itemID":"ID100124","price":50}
{"index":{"_id":3}}
{"itemID":"ID100125","price":25}
{"index":{"_id":4}}
{"itemID":"ID100126","price":30}
{"index":{"_id":5}}
{"itemID":"ID100127","price":null}

# 再次查询，查询itemID为ID100123的文档时报错，因为itemID没有被索引
GET /lib10/items/_search
{
  "query": {
    "bool": {
      "filter": {
        "term": {
          "itemID": "ID100123"
        }
      }
    }
  }
}
```

### 2.8.3 bool组合过滤查询

![img](file:///D:/Documents/My Knowledge/temp/3923c1bb-9a06-4f25-9526-8916afc0803d/128/index_files/7d29a392-19c8-4efc-93e7-63ec81b78acd.jpg)

```shell
# bool组合过滤查询
# 查询出price为25或itemID为ID100123，且price不能为30的文档
GET /lib10/items/_search
{
  "query": {
    "bool": {
      "should": [
        {
          "term": {
            "price": {
              "value": "25"
            }
          }
        },
        {
          "term": {
            "itemID": {
              "value": "id100123"
            }
          }
        }
      ],
      "must_not": [
        {
          "term": {
            "price": {
              "value": "30"
            }
          }
        }
      ]
    }
  }
}
```



```shell
# 嵌套使用bool
# 查询itemID为ID100123或者（itemID为id100124且price为50）的文档
GET /lib10/items/_search
{
  "query": {
    "bool": {
      "should": [
        {
          "term": {
            "itemID": {
              "value": "id100123"
            }
          }
        },
        {
          "bool": {
            "must": [
              {
                "term": {
                  "itemID": {
                    "value": "id100124"
                  }
                }
              },
              {
                "term": {
                  "price": {
                    "value": "50"
                  }
                }
              }
            ]
          }
        }
      ]
    }
  }
}
```

### 2.8.4 范围过滤

![img](file:///D:/Documents/My Knowledge/temp/3923c1bb-9a06-4f25-9526-8916afc0803d/128/index_files/0697b062-d3f8-4978-98d7-bcc30932dadc.jpg)

```shell
# 范围过滤
GET /lib10/items/_search
{
  "query": {
    "bool": {
      "filter": {
        "range": {
          "price": {
            "gt": 25,
            "lt": 50
          }
        }
      }
    }
  }
}

```

### 2.8.5 过滤非空 

```shell
# 过滤非空
# 查询出price不为空的文档
GET /lib10/items/_search
{
  "query": {
    "bool": {
      "filter": {
        "exists": {
          "field": "price"
        }
      }
    }
  }
}

# 过滤空
# 查询出price为空的文档
GET /lib10/items/_search
{
  "query": {
    "bool": {
      "must_not": [
        {
          "exists": {
            "field": "price"
          }
        }
      ]
    }
  }
}

```

### 2.8.6 过滤器缓存

![img](file:///D:/Documents/My Knowledge/temp/3923c1bb-9a06-4f25-9526-8916afc0803d/128/index_files/88077f81-2c61-4be3-ab0a-e296ad7323c5.jpg)

```shell
numeric_range
script
geo_bbox
geo_distance
geo_distance_range
geo_polygon
geo_shape
and
or
not
```



## 2.9 聚合查询

### 2.9.1 sum求总和

```shell
# sum求总和,price_of_sum自己起的名字，查询会返回
GET /lib10/items/_search
{
  "aggs": {
    "price_of_sum": {
      "sum": {
        "field": "price"
      }
    }
  }
}
```

![img](file:///D:/Documents/My Knowledge/temp/3923c1bb-9a06-4f25-9526-8916afc0803d/128/index_files/0c54e4c6-7a91-442a-9b68-8d6465a92206.png)

### 2.9.2 min求最小值

```shell
# min求最小值
GET /lib10/items/_search
{
  "aggs": {
    "price_of_min": {
      "min": {
        "field": "price"
      }
    }
  }
}
```

### 2.9.3 max求最大值

```shell
# max求最大值
GET /lib10/items/_search
{
  "aggs": {
    "price_of_max": {
      "max": {
        "field": "price"
      }
    }
  }
}
```

### 2.9.4 avg求平均数

```shell
# avg求平均数
GET /lib10/items/_search
{
  "aggs": {
    "price_of_avg": {
      "avg": {
        "field": "price"
      }
    }
  }
}
```

### 2.9.5 cardinality求基数

```shell
# cardinality求基数，互不相同的个数
GET /lib10/items/_search
{
  "aggs": {
    "price_of_cardinality": {
      "cardinality": {
        "field": "price"
      }
    }
  }
}
```

![img](file:///D:/Documents/My Knowledge/temp/3923c1bb-9a06-4f25-9526-8916afc0803d/128/index_files/08df36c8-dc30-4e57-9a06-4c15f8ab22b1.png)

### 2.9.6 terms分组

```shell
# terms分组，互不相同的有四个，分为四组
GET /lib10/items/_search
{
  "aggs": {
    "price_of_group": {
      "terms": {
        "field": "price"
      }
    }
  }
}
```

![img](file:///D:/Documents/My Knowledge/temp/3923c1bb-9a06-4f25-9526-8916afc0803d/128/index_files/44af9b60-a1ba-4654-b106-e39b49adce14.png)

### 2.9.7 练习

```shell
# 对那些有唱歌changge兴趣的用户按年龄age分组
# 并对每个分组中age求平均值，同时按平均值降序排序
GET /lib5/user/_search
{
  "query": {
    "match": {
      "interests": "changge"
    }
  },
  "aggs": {
    "age_of_group": {
      "terms": {
        "field": "age",
        "order": {
          "age_of_avg": "desc"
        }
      },
      "aggs": {
        "age_of_avg": {
          "avg": {
            "field": "age"
          }
        }
      }
    }
  }
}
```

![img](file:///D:/Documents/My Knowledge/temp/3923c1bb-9a06-4f25-9526-8916afc0803d/128/index_files/4b8f6966-806c-4417-9043-66943c620369.png)

## 2.10 复合查询

![img](file:///D:/Documents/My Knowledge/temp/3923c1bb-9a06-4f25-9526-8916afc0803d/128/index_files/e266f365-a117-4a74-b0a7-8d13bde98128.jpg)

### 2.10.1 使用bool查询 

![img](file:///D:/Documents/My Knowledge/temp/3923c1bb-9a06-4f25-9526-8916afc0803d/128/index_files/341d53fa-b327-42bb-bee9-698e7f3bb909.jpg)

```shell
# 查询interests一定包含changge的，并且一定不包含lvyou的，同时可以地址包含bei jing或者 birthday大于等于1996-01-01的文档
GET /lib5/user/_search
{
  "query": {
    "bool": {
      "must": [
        {
          "match": {
            "interests": "changge"
          }
        }
      ],
      "must_not": [
        {
          "match": {
            "interests": "lvyou"
          }
        }
      ],
      "should": [
        {
          "match": {
            "address": "bei jing"
          }
        },
        {
          "range": {
            "birthday": {
              "gte": "1996-01-01"
            }
          }
        }
      ]
    }
  }
}
```

![img](file:///D:/Documents/My Knowledge/temp/3923c1bb-9a06-4f25-9526-8916afc0803d/128/index_files/f9df4f78-9842-4ebb-9037-2bc3ed9f207c.jpg)

```shell
# 查询interests一定包含changge的，并且一定不包含lvyou的，同时可以地址包含bei jing,并且 birthday一定要大于等于1996-01-01的文档
GET /lib5/user/_search
{
  "query": {
    "bool": {
      "must": [
        {
          "match": {
            "interests": "changge"
          }
        }
      ],
      "must_not": [
        {
          "match": {
            "interests": "lvyou"
          }
        }
      ],
      "should": [
        {
          "match": {
            "address": "bei jing"
          }
        }
      ],
      "filter": {
        "range": {
          "birthday": {
              "gte": "1996-01-01"
            }
        }
      }
    }
  }
}
```

![img](file:///D:/Documents/My Knowledge/temp/3923c1bb-9a06-4f25-9526-8916afc0803d/128/index_files/f2b95cbb-ad32-42c9-ae7b-e100beb153e4.jpg)

```shell
# 查询interests一定包含changge的，并且一定不包含lvyou的，同时可以地址包含bei jing,并且 birthday一定要大于等于1996-01-01且age小于等于30且age一定等于20的文档
GET /lib5/user/_search
{
  "query": {
    "bool": {
      "must": [
        {
          "match": {
            "interests": "changge"
          }
        }
      ],
      "must_not": [
        {
          "match": {
            "interests": "lvyou"
          }
        }
      ],
      "should": [
        {
          "match": {
            "address": "bei jing"
          }
        }
      ],
      "filter": {
        "bool": {
          "must": [
            {
              "range": {
                "birthday": {
                  "gte": "1996-01-01"
                }
              }
            },
            {
              "range": {
                "age": {
                  "lte": "30"
                }
              }
            }
          ],
          "must_not": [
            {
              "term": {
                "age": "20"
              }
            }
          ]
        }
      }
    }
  }
}
```

### 2.10.2 constant_score查询

![img](file:///D:/Documents/My Knowledge/temp/3923c1bb-9a06-4f25-9526-8916afc0803d/128/index_files/002f99a6-b932-43b9-ab4d-a1d83f5b2a79.jpg)

```shell
# constant_score不计算相关性得分，"_score" 都为1
# 查询interests包含changge的文档
GET /lib5/user/_search
{
  "query": {
    "constant_score": {
      "filter": {
        "term": {
          "interests": "changge"
        }
      }
    }
  }
}

# 上面的可以取代bool中只包含filter的过滤查询
# 这里过滤查询，不会计算相关性得分，"_score" 都为0.0
GET /lib5/user/_search
{
  "query": {
    "bool": {
      "filter": {
        "term": {
          "interests": "changge"
        }
      }
    }
  }
}
```

![img](file:///D:/Documents/My Knowledge/temp/3923c1bb-9a06-4f25-9526-8916afc0803d/128/index_files/457c09fc-bbe9-4bca-8c91-e5a3ab903e93.png)

## 2.11 分词器

### 2.11.1 分词器介绍及内置分词器

![img](file:///D:/Documents/My Knowledge/temp/3923c1bb-9a06-4f25-9526-8916afc0803d/128/index_files/95802874-7938-48c8-904e-1c2855c8e43f.jpg)

### 2.11.2 中文分词器IK

分词就是指将一个文本转化成一系列单词的过程，也叫文本分析，在Elasticsearch中称之为Analysis。

举例：我是中国人 --> 我/是/中国人。



推荐使用**中文分词器IK**。具体安装配置参考下面地址中的插件安装：

​    为知笔记地址：[CentOS安装配置ElasticSearch6.5.4](wiz://open_document?guid=f166f7be-1579-464a-9d6d-519c6fe778b0&kbguid=&private_kbguid=9e15e816-792d-4528-9d21-a849cc4117d5)

​    GitHub地址：[https://github.com/wangliu1102/StudyNotes/tree/master/%E5%B0%9A%E7%A1%85%E8%B0%B7Java/%E5%85%AD%E3%80%81Java%E8%BF%9B%E9%98%B6/Elasticsearch/%E5%AE%89%E8%A3%85](https://github.com/wangliu1102/StudyNotes/tree/master/尚硅谷Java/六、Java进阶/Elasticsearch/安装)

中文分词的难点在于，在汉语中没有明显的词汇分界点，如在英语中，空格可以作为分隔符，如果分隔不正确就会造成歧义。

如：我/爱/炒肉丝   我/爱/炒/肉丝

常用中文分词器，IK、jieba、THULAC等，推荐使用**IK分词器。https://github.com/medcl/elasticsearch-analysis-ik**。

> > IK Analyzer是一个开源的，基于java语言开发的轻量级的中文分词工具包。从2006年12月推出1.0版开始，IKAnalyzer已经推出了3个大版本。最初，它是以开源项目Luence为应用主体的，结合词典分词和文法分析算法的中文分词组件。新版本的IK Analyzer 3.0则发展为面向Java的公用分词组件，独立于Lucene项目，同时提供了对Lucene的默认优化实现。
>
> > 采用了特有的“正向迭代最细粒度切分算法“，具有80万字/秒的高速处理能力 采用了多子处理器分析模式，支持：英文字母（IP地址、Email、URL）、数字（日期，常用中文数量词，罗马数字，科学计数法），中文词汇（姓名、地名处理）等分词处理。 优化的词典存储，更小的内存占用。

 

```shell
# ik分词器
# 新增索引，给content字段添加ik分词器
# ik_max_word: 会将文本做最细粒度的拆分，比如会将“中华人民共和国国歌”拆分为“中华人民共和国,中华人民,中华,华人,人民共和国,人民,人,民,共和国,共和,和,国国,国歌”，会穷尽各种可能的组合，适合 Term Query；
# ik_smart: 会做最粗粒度的拆分，比如会将“中华人民共和国国歌”拆分为“中华人民共和国,国歌”，适合 Phrase 查询
DELETE /index
PUT /index
{
  "mappings": {
    "test": {
      "properties": {
        "content": {
          "type": "text",
          "analyzer": "ik_max_word",
          "search_analyzer": "ik_smart"
        }
      }
    }
  }
}

# 新增数据
PUT /index/test/1
{
  "content":"美国留给伊拉克的是个烂摊子吗"
}

PUT /index/test/2
{
  "content":"公安部：各地校车将享最高路权"
}

PUT /index/test/3
{
  "content":"中韩渔警冲突调查：韩警平均每天扣1艘中国渔船"
}

PUT /index/test/4
{
  "content":"中国驻洛杉矶领事馆遭亚裔男子枪击 嫌犯已自首"
}

# 获取分词效果
GET /index/_analyze
{
  "analyzer": "ik_smart",
  "text": "中韩渔警冲突调查：韩警平均每天扣1艘中国渔船"
}

# 查询高亮
GET /index/test/_search
{
  "query": {
    "match": {
      "content": "中国"
    }
  },
  "highlight": {
    "pre_tags": [
      "<tag1>",
      "<tag2>"
    ],
    "post_tags": [
      "</tag1>",
      "</tag2>"
    ],
    "fields": {
      "content": {}
    }
  }
}
```

![img](file:///D:/Documents/My Knowledge/temp/3923c1bb-9a06-4f25-9526-8916afc0803d/128/index_files/17f3dfe0-740b-464b-b20f-5e544251e450.png)

### 2.11.3 ik的高级配置

#### 1、ik的扩展配置

如果我们仔细查看插件的目录，就可以看到有很多的预先设定的配置，比如停止词等等。

![img](file:///D:/Documents/My Knowledge/temp/3923c1bb-9a06-4f25-9526-8916afc0803d/128/index_files/824597e3-be40-4c9e-8cc5-4b559bf681c0.png)

我们看一下IKAnalyzer.cfg.xml这个文件：

```xml
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE properties SYSTEM "http://java.sun.com/dtd/properties.dtd">
<properties>
    <comment>IK Analyzer 扩展配置</comment>
    <!--用户可以在这里配置自己的扩展字典 -->
    <entry key="ext_dict"></entry>
     <!--用户可以在这里配置自己的扩展停止词字典-->
    <entry key="ext_stopwords"></entry>
    <!--用户可以在这里配置远程扩展字典 -->
    <!-- <entry key="remote_ext_dict">words_location</entry> -->
    <!--用户可以在这里配置远程扩展停止词字典-->
    <!-- <entry key="remote_ext_stopwords">words_location</entry> -->
</properties>

```

扩展词理所当然是我们自己常用的，但是又不被广泛认可的词，比如我们的姓名等，下面是停止词的一些理解：

![img](file:///D:/Documents/My Knowledge/temp/3923c1bb-9a06-4f25-9526-8916afc0803d/128/index_files/0.14931246869764944.png)

可以看到我们可以增加一些配置在我们的文件之中，比如我们新建一个文件，这个文件之中加入我们的分词，然后重新启动es，再次查询这个词，就能发现系统不会将这些词分隔开了。

#### 2、ik的扩展测试

下面我们重新建立一个索引，走一下这个过程，整个过程如下：

```shell
#创建索引
PUT /zyr_lsx_index

#创建映射
POST /zyr_lsx_index/zyr_lsx_fulltext/_mapping
{
   "properties": {
       "detail_test": {
           "type": "text",
           "analyzer": "ik_max_word",
           "search_analyzer": "ik_max_word"
        }
   }
}

#插入数据
PUT /zyr_lsx_index/zyr_lsx_fulltext/1?pretty
{
   "detail_test":"这是一个测试文档"
}

PUT /zyr_lsx_index/zyr_lsx_fulltext/2?pretty
{
   "detail_test":"可以了解一些测试方面的东西"
}

PUT /zyr_lsx_index/zyr_lsx_fulltext/3?pretty
{
   "detail_test":"关于分词方面的测试"
}
PUT /zyr_lsx_index/zyr_lsx_fulltext/4?pretty
{
   "detail_test":"如果你想了解更多的内容"
}
PUT /zyr_lsx_index/zyr_lsx_fulltext/5?pretty
{
   "detail_test":"可以查看我的博客"
}
PUT /zyr_lsx_index/zyr_lsx_fulltext/6?pretty
{
   "detail_test":"我是朱彦荣"
}


#搜索测试
POST /zyr_lsx_index/zyr_lsx_fulltext/_search
{
    "query" : {
      "match" : { "detail_test" : "朱彦荣" }
    },
    "highlight" : {
        "pre_tags" : ["<tag1>", "<tag2>"],
        "post_tags" : ["</tag1>", "</tag2>"],
        "fields" : {
            "detail_test" : {}
        }
    }
}
```

![img](file:///D:/Documents/My Knowledge/temp/3923c1bb-9a06-4f25-9526-8916afc0803d/128/index_files/779d34ea-9e21-4e01-9fdd-753142d840ef.png)

 同时我们对ik的配置文件进行修改：

这里我们需要注意，系统会默认将文件前面的目录补全，我们如果是在config目录下面新建的文件词典，那么直接在配置之中写入文件名即可。

![img](file:///D:/Documents/My Knowledge/temp/3923c1bb-9a06-4f25-9526-8916afc0803d/128/index_files/a8ff4070-3c2c-4e71-9f4b-5664d4362b7b.png)

IKAnalyzer.cfg.xml：

![img](file:///D:/Documents/My Knowledge/temp/3923c1bb-9a06-4f25-9526-8916afc0803d/128/index_files/6a4007c5-a9a5-4a92-8c70-cdbc5c685c0a.png)

test.dic：

![img](file:///D:/Documents/My Knowledge/temp/3923c1bb-9a06-4f25-9526-8916afc0803d/128/index_files/e174607c-0f66-4bc9-be46-b99737b1ab84.png)

重启es，将上面的代码执行一遍，然后就会发现，我们自己定义的扩展词已经生效了，不会再被分割成一个个的字了，至此，我们对ik有了更深的理解，其次，我们还可以通过远程的方式来更新我们的词库，这样，我们就能理解搜狗输入法的一些记忆功能了。

最终的结果：

![img](file:///D:/Documents/My Knowledge/temp/3923c1bb-9a06-4f25-9526-8916afc0803d/128/index_files/83262099-6992-4a15-a132-49526556bdfd.jpg)

#### 3、热更新 IK 分词使用方法

目前该插件支持热更新 IK 分词，通过上文在 IK 配置文件中提到的如下配置：

```xml
     <!--用户可以在这里配置远程扩展字典 -->
    <entry key="remote_ext_dict">location</entry>
     <!--用户可以在这里配置远程扩展停止词字典-->
    <entry key="remote_ext_stopwords">location</entry>
```

其中 location 是指一个 url，比如 http://yoursite.com/getCustomDict，该请求只需满足以下两点即可完成分词热更新。

- 该 http 请求需要返回两个头部(header)，一个是 Last-Modified，一个是 ETag，这两者都是字符串类型，只要有一个发生变化，该插件就会去抓取新的分词进而更新词库。
- 该 http 请求返回的内容格式是一行一个分词，换行符用 \n 即可。

满足上面两点要求就可以实现热更新分词了，不需要重启 ES 实例。

可以将需自动更新的热词放在一个 UTF-8 编码的 .txt 文件里，放在 nginx 或其他简易 http server 下，当 .txt 文件修改时，http server 会在客户端请求该文件时自动返回相应的 Last-Modified 和 ETag。可以另外做一个工具来从业务系统提取相关词汇，并更新这个 .txt 文件。

#### 4、常见问题

##### （1）自定义词典为什么没有生效？

请确保你的扩展词典的文本格式为 UTF8 编码。

##### （2）ik_max_word 和 ik_smart 什么区别?

##### ik_max_word: 会将文本做最细粒度的拆分，比如会将“中华人民共和国国歌”拆分为“中华人民共和国,中华人民,中华,华人,人民共和国,人民,人,民,共和国,共和,和,国国,国歌”，会穷尽各种可能的组合，适合 Term Query；ik_smart: 会做最粗粒度的拆分，比如会将“中华人民共和国国歌”拆分为“中华人民共和国,国歌”，适合 Phrase 查询。

##### （3）Changes

自 v5.0.0 起

- 移除名为 ik 的analyzer和tokenizer,请分别使用 ik_smart 和 ik_max_word。

# 三、ElasticSearch原理

## 3.1 解析es的分布式架构

### 3.1.1 分布式架构的透明隐藏特性

![img](file:///D:/Documents/My Knowledge/temp/3923c1bb-9a06-4f25-9526-8916afc0803d/128/index_files/6405b7d3-11d2-4ba7-ba38-7e1e60ece65d.jpg)

### 3.1.2 扩容机制

![img](file:///D:/Documents/My Knowledge/temp/3923c1bb-9a06-4f25-9526-8916afc0803d/128/index_files/348dc3f1-472d-4eef-a8cc-79b2c975588a.jpg)

![img](file:///D:/Documents/My Knowledge/temp/3923c1bb-9a06-4f25-9526-8916afc0803d/128/index_files/a0f63ff2-fae6-4ed5-ad93-bd589d0941af.jpg)

### 3.1.3 rebalance

![img](file:///D:/Documents/My Knowledge/temp/3923c1bb-9a06-4f25-9526-8916afc0803d/128/index_files/f1239b70-d70b-4072-9d95-80c2ba6ecdd9.jpg) 

### 3.1.4 master节点

![img](file:///D:/Documents/My Knowledge/temp/3923c1bb-9a06-4f25-9526-8916afc0803d/128/index_files/e4e173ec-1bee-4e45-a556-767e1de744b9.jpg)

### 3.1.5 节点对等

![img](file:///D:/Documents/My Knowledge/temp/3923c1bb-9a06-4f25-9526-8916afc0803d/128/index_files/acb0b4a3-f0d0-4a52-81c1-60ad50439931.jpg)

## 3.2 分片和副本机制

![img](file:///D:/Documents/My Knowledge/temp/3923c1bb-9a06-4f25-9526-8916afc0803d/128/index_files/716d8e33-2af2-4700-a8d0-f49bc477d734.jpg)

```shell
# 查看集群健康状况
GET _cat/health

# 修改副本数量
PUT /lib9/_settings
{
    "number_of_replicas": 1
}
```



## 3.3 单节点环境下创建索引分析

![img](file:///D:/Documents/My Knowledge/temp/3923c1bb-9a06-4f25-9526-8916afc0803d/128/index_files/eb748915-e384-40c4-a3c9-5ec21e5bffa9.jpg)

## 3.4 两个节点环境下创建索引分析

![img](file:///D:/Documents/My Knowledge/temp/3923c1bb-9a06-4f25-9526-8916afc0803d/128/index_files/f36214ab-713b-46e3-b83d-0c43bd213368.jpg)

## 3.5 水平扩容的过程

![img](file:///D:/Documents/My Knowledge/temp/3923c1bb-9a06-4f25-9526-8916afc0803d/128/index_files/c93c8573-96a5-4804-9084-0149d4c81b34.jpg)

![img](file:///D:/Documents/My Knowledge/temp/3923c1bb-9a06-4f25-9526-8916afc0803d/128/index_files/78a28fef-4373-437a-a482-bb30de9e04ff.png)

## 3.6 ElasticSearch的容错

![img](file:///D:/Documents/My Knowledge/temp/3923c1bb-9a06-4f25-9526-8916afc0803d/128/index_files/42dbf9ae-4735-4a6c-824c-63fc21c58176.jpg)

## 3.7 文档的核心元数据

![img](file:///D:/Documents/My Knowledge/temp/3923c1bb-9a06-4f25-9526-8916afc0803d/128/index_files/0c5e7501-c3af-40fa-b31a-2f968707e979.jpg)

## 3.8 文档id生成方式

![img](file:///D:/Documents/My Knowledge/temp/3923c1bb-9a06-4f25-9526-8916afc0803d/128/index_files/f5f6a5a6-b03d-479e-9b2d-eb1009b7d3db.jpg)

## 3.9 _source元数据分析

![img](file:///D:/Documents/My Knowledge/temp/3923c1bb-9a06-4f25-9526-8916afc0803d/128/index_files/34ea4a7e-f853-4f74-b119-1eab7327e48f.jpg)

## 3.10 改变文档内容原理解析

![img](file:///D:/Documents/My Knowledge/temp/3923c1bb-9a06-4f25-9526-8916afc0803d/128/index_files/b249b142-7eac-4fc6-b15a-b26f920023e1.jpg)

![img](file:///D:/Documents/My Knowledge/temp/3923c1bb-9a06-4f25-9526-8916afc0803d/128/index_files/8a3ca787-44c6-40c7-8a10-7e3f41cbca13.jpg)

## 3.11 基于groovy脚本执行partial update

 

```shell
# 基于groovy脚本执行partial update
# es有内置的脚本支持，可以基于groovy脚本实现复杂的操作

# 查询lib索引下id为4的user
GET /lib/user/4

# 修改年龄
POST /lib/user/4/_update
{
  "script": "ctx._source.age+=1"
}

# 修改名字
POST /lib/user/4/_update
{
  "script": "ctx._source.last_name+='hehe'"
}

# 添加爱好
POST /lib/user/4/_update
{
  "script": {
    "source": "ctx._source.interests.add(params.tag)",
    "params": {
      "tag": "picture"
    }
  }
}

# 删除爱好
POST /lib/user/4/_update
{
  "script": {
    "source": "ctx._source.interests.remove(ctx._source.interests.indexOf(params.tag))",
    "params": {
      "tag": "picture"
    }
  }
}

# 删除文档,满足年龄age等于27删除，否则不操作
POST /lib/user/4/_update
{
  "script": {
    "source": "ctx.op=ctx._source.age==params.count?'delete':'none'",
    "params": {
      "count": 27
    }
  }
}

# upsert操作：如果该文档不存在会进行初始化，如果存在执行"script": "ctx._source.age+=1"
GET /lib/user/4/_update
{
  "script": "ctx._source.age+=1",
  "upsert": {
    "first_name": "Jane",
    "last_name": "Lucy",
    "age": 27,
    "about": "I like to collect rock albums",
    "interests": ["music"]
  }
}

# 查询lib索引下id为4的user
GET /lib/user/4

# 更新文档对并发问题的处理
# retry_on_conflict:重新获取文档数据和版本信息进行更新，不断的操作，最多操作的次数就是retry_on_conflict的值
POST /lib/user/4/_update?retry_on_conflict=3
{
  "script": "ctx._source.age+=1"
}
```

## 3.13 文档数据路由原理解析

![img](file:///D:/Documents/My Knowledge/temp/3923c1bb-9a06-4f25-9526-8916afc0803d/128/index_files/1dc9c0b3-34a0-456d-9258-3ffc87849105.jpg)

## 3.14 文档增删改内部原理

![img](file:///D:/Documents/My Knowledge/temp/3923c1bb-9a06-4f25-9526-8916afc0803d/128/index_files/b3aeab80-ea0f-4f0f-a86a-093bbea992c9.jpg)

![img](file:///D:/Documents/My Knowledge/temp/3923c1bb-9a06-4f25-9526-8916afc0803d/128/index_files/b321336d-4f71-4065-a16e-beeb19ed37dd.jpg)

## 3.15 写一致性原理和quorum机制

![img](file:///D:/Documents/My Knowledge/temp/3923c1bb-9a06-4f25-9526-8916afc0803d/128/index_files/6ac58eeb-d6b8-4a46-b16b-a99a3431bd6d.jpg)

**elasticsearch 5.0以上不支持consistency 和 quorum**。

consistency检查是在Put之前做的。然而，虽然检查的时候，shard满足quorum，但是真正从primary shard写到replica之前，仍会出现shard挂掉，但Update Api会返回succeed。因此，这个检查并不能保证replica成功写入，甚至这个primary shard是否能成功写入也未必能保证。。

因此，修改了语法，用了下面的 wait_for_active_shards，因为这个更能清楚表述，而没有歧义。

```shell
# elasticsearch 5.0以上不支持consistency 和 quorum
# wait_for_active_shards
PUT /myindex/article/5?wait_for_active_shards=1
{
  "post_date": "2018-05-16",
  "title": "es",
  "content": "Es is distributed document store",
  "author_id": 110
}
```



## 3.16 文档查询内部原理

![img](file:///D:/Documents/My Knowledge/temp/3923c1bb-9a06-4f25-9526-8916afc0803d/128/index_files/a40fb384-2ea4-48a8-ac64-b0e2238cac73.jpg)

## 3.17 bulk批量操作的json格式解析

![img](file:///D:/Documents/My Knowledge/temp/3923c1bb-9a06-4f25-9526-8916afc0803d/128/index_files/9fc1eed6-b38b-4bfe-8704-7b8f5613ade9.jpg)

## 3.18 查询结果分析

![img](file:///D:/Documents/My Knowledge/temp/3923c1bb-9a06-4f25-9526-8916afc0803d/128/index_files/59834e1a-1b10-4e46-8895-5474291f224b.jpg)

## 3.19 多index，多type查询模式

![img](file:///D:/Documents/My Knowledge/temp/3923c1bb-9a06-4f25-9526-8916afc0803d/128/index_files/40d1236f-5f6d-4e06-938b-136a45bddef6.jpg)

```shell
# 多index，多type查询模式
GET /lib,lib2/user,books/_search
```

## 3.20 分页查询中的deep paging问题

![img](file:///D:/Documents/My Knowledge/temp/3923c1bb-9a06-4f25-9526-8916afc0803d/128/index_files/6c6a7488-9b1c-4cef-b45c-02f444cf6fad.jpg)

![img](file:///D:/Documents/My Knowledge/temp/3923c1bb-9a06-4f25-9526-8916afc0803d/128/index_files/40ccfe37-53e7-445a-8555-90e8979c20d1.jpg)

## 3.21 query string查询及copy_to解析

query string查询：

```shell
GET /myindex/article/_search

# query string查询
# 查询post_date字段值为2018-05-10的文档
GET /myindex/article/_search?q=post_date:2018-05-10
# 查询content字段值含有html的文档
GET /myindex/article/_search?q=content:html
# 未指定字段时，会查询所有字段，这里查询所有字段值包含html或es的文档
GET /myindex/article/_search?q=html,es
```

![img](file:///D:/Documents/My Knowledge/temp/3923c1bb-9a06-4f25-9526-8916afc0803d/128/index_files/cecb849a-8915-4d1b-8ccd-bbd6e256e41a.jpg)

```shell
# copy_to解析
# 手动创建mapping
PUT /myindex2
{
  "settings": {
    "number_of_shards": 3,
    "number_of_replicas": 1
  },
  "mappings": {
    "article": {
      "properties": {
        "post_date": {
          "type": "date"
        },
        "title": {
          "type": "text",
          "copy_to": "fullcontents"
        },
        "content": {
          "type": "text",
          "copy_to": "fullcontents"
        },
        "author_id": {
          "type": "integer"
        }
      }
    }
  }
}

#新增数据
PUT /myindex2/article/1
{
  "post_date": "2018-05-10",
  "title": "Java",
  "content": "java is the best language",
  "author_id": 119
}

PUT /myindex2/article/2
{
  "post_date": "2018-05-12",
  "title": "html",
  "content": "I like html",
  "author_id": 120
}

PUT /myindex2/article/3
{
  "post_date": "2018-05-16",
  "title": "es",
  "content": "Es is distributed document store",
  "author_id": 110
}

GET /myindex2/article/_search
# 查询post_date字段值为2018-05-10的文档
GET /myindex2/article/_search?q=post_date:2018-05-10
# 查询content字段值含有html的文档
GET /myindex2/article/_search?q=content:html
# 当没有指定字段时，就会从copy_to字段中查询，这里查询含有html或java的文档，这里copy_to字段为fullcontents（title + content）,
GET /myindex/article/_search?q=html,java
```



## 3.22 字符串排序问题

![img](file:///D:/Documents/My Knowledge/temp/3923c1bb-9a06-4f25-9526-8916afc0803d/128/index_files/0c0792f7-79c4-474e-b04b-2300d0646785.jpg)

```shell
# 字符串排序问题
GET /lib5/_search
GET /lib5/_mapping

# 对字符串interests排序，会报错
GET /lib5/_search
{
  "query": {
    "match_all": {}
  },
  "sort": [
    {
      "interests": {
        "order": "desc"
      }
    }
  ]
}
```

对字符串interests排序，会报错的问题的解决办法：对字段索引两次，一次索引分词（用于搜索），一次索引不分词（用于排序）

```shell
# 添加映射时，对interests字段添加如下属性
        "fields": {
            "raw": {
              "type": "keyword"
            }
          },
          "fielddata": true
```



```shell
# 先删除lib5 索引，再添加索引和映射
DELETE /lib5
PUT /lib5
{
  "settings": {
    "number_of_shards": 3,
    "number_of_replicas": 0
  },
  "mappings": {
    "user": {
      "properties": {
        "name": {
          "type": "text"
        },
        "address": {
          "type": "text"
        },
        "age": {
          "type": "integer"
        },
        "interests": {
          "type": "text",
          "fields": {
            "raw": {
              "type": "keyword"
            }
          },
          "fielddata": true
        },
        "birthday": {
          "type": "date"
        }
      }
    }
  }
}
# 新增数据
PUT /lib5/user/1
{
  "name": "zhaoliu",
  "address": "hei long jiang sheng tie ling shi",
  "age": 50,
  "birthday": "1970-12-12",
  "interests": "xi huan hejiu,duanlian,lvyou"
}

PUT /lib5/user/2
{
  "name": "zhaoming",
  "address": "bei jing hai dian qu qing he zhen",
  "age": 20,
  "birthday": "1998-10-12",
  "interests": "xi huan hejiu,duanlian,changge"
}


PUT /lib5/user/3
{
  "name": "lisi",
  "address": "bei jing hai dian qu qing he zhen",
  "age": 23,
  "birthday": "1998-10-12",
  "interests": "xi huan hejiu,duanlian,changge"
}


PUT /lib5/user/4
{
  "name": "wangwu",
  "address": "bei jing hai dian qu qing he zhen",
  "age": 26,
  "birthday": "1995-10-12",
  "interests": "xi huan biancheng,tingyinyue,lvyou"
}


PUT /lib5/user/5
{
  "name": "zhangsan",
  "address": "bei jing chao yang qu",
  "age": 29,
  "birthday": "1988-10-12",
  "interests": "xi huan tingyinyue,changge,tiaowu"
}

# 再次对字符串interests排序，不会报错，但是这里还是先对interests进行了分词，然后根据分词后的第一个单词xi进行排序
GET /lib5/_search
{
  "query": {
    "match_all": {}
  },
  "sort": [
    {
      "interests": {
        "order": "desc"
      }
    }
  ]
}

# 给interests添加raw，再次对字符串interests排序，就不会以分词后的单词进行排序了，而是根据整个interests进行排序
GET /lib5/_search
{
  "query": {
    "match_all": {}
  },
  "sort": [
    {
      "interests.raw": {
        "order": "desc"
      }
    }
  ]
}
```



## 3.23 如何计算相关度分数

![img](file:///D:/Documents/My Knowledge/temp/3923c1bb-9a06-4f25-9526-8916afc0803d/128/index_files/dbb50f23-ba87-45eb-b44c-ee6573b55825.jpg)

 

```shell
# 计算相关度分数
# 查看分数是如何计算的
GET /lib5/user/_search?explain=true
{
  "query": {
    "match": {
      "interests": "duanlian,changge"
    }
  }
}

# 查看一个文档能否匹配上某个查询
GET /lib5/user/2/_explain
{
  "query": {
    "match": {
      "interests": "duanlian,changge"
    }
  }
}
```



## 3.24 Doc Values解析

![img](file:///D:/Documents/My Knowledge/temp/3923c1bb-9a06-4f25-9526-8916afc0803d/128/index_files/850384f0-7600-4d05-af39-827afa0b5071.jpg)

 

```shell
# 通过属性"doc_values": false 来关闭正排索引，关闭后，就不支持排序
# 如上的建立lib3索引，设置了age字段关闭正排索引，再次查询使用age来排序就会报错
```



## 3.25 基于scroll技术滚动搜索大量数据

![img](file:///D:/Documents/My Knowledge/temp/3923c1bb-9a06-4f25-9526-8916afc0803d/128/index_files/bbcc13b4-21d4-4795-8f8b-add5b3191d60.jpg)

```shell
# 基于scroll技术滚动搜索大量数据
GET /lib5/user/_search

# scroll=1m:在一分钟之内完成查询，"size": 2每次查询出2个文档
# 第一次查询会建立一个快照,返回一个scroll_id
GET /lib5/user/_search?scroll=1m
{
  "query": {
    "match_all": {}
  },
  "sort": ["_doc"],
  "size": 2
}

# 后面通过快照来进行下一次查询
GET /_search/scroll
{
  "scroll": "1m",
  "scroll_id": "DnF1ZXJ5VGhlbkZldGNoAwAAAAAAAO-KFmxzc1o2U0JsUllhdFRnbDVVak5yRncAAAAAAADwchZnOEZKV2JaYlFhbU1wS0I3bFBvR3lnAAAAAAAA74kWbHNzWjZTQmxSWWF0VGdsNVVqTnJGdw=="
}
```



## 3.26 dynamic mapping策略

- **dynamic**：控制mapping的自动更新

```shell
"dynamic": true（缺省）| false | strict

# true：遇到陌生字段就 dynamic mapping
# false：遇到陌生字段就忽略
# strict：遇到陌生字段就报错

# 设置为报错
PUT /lib8
{
    "settings":{
        "number_of_shards": 3,
        "number_of_replicas": 0
    },
    "mappings":{
        "user":{
            "dynamic": "strict",
            "properties": {
                "name": {"type": "text"},
                "address": {"type": "object", "dynamic": true}
            }
        }
    }
}

# 插入以下文档，将会报错
# user此层设置dynamic是strict，在user层内设置age将报错
# 在address层设置dynamic是ture，将动态映射生成字段
PUT /lib8/user/1
{
  "name": "lisi",
  "age": "20",
  "address": {
    "province": "beijing",
    "city": "beijing"
  }
}
```

- **data_detection**：是否自动识别日期类型

```shell
data_detection：true（缺省）| false

# 默认值是true，会按照一定格式识别date，比如yyyy-MM-dd
# 可以手动关闭某个type的date_detection
PUT /lib9
{
  "settings": {
    "number_of_shards": 3,
    "number_of_replicas": 0
  },
  "mappings": {
    "user": {
      "date_detection": false
    }
  }
}

# 定制 dynamic mapping template（type）
# match: *_en 表示是字段名是以_en结尾
# match_mapping_type: string 表示是添加的所有string类型的字段都用这个模板
# mapping.type: text 表示是字段的值是text类型
# mapping.analyzer: english 表示是分词是english分词器
PUT /my_index
{
  "mappings": {
    "my_type": {
      "dynamic_templates": [
        {
          "en": {
            "match": "*_en",
            "match_mapping_type": "string",
            "mapping": {
              "type": "text",
              "analyzer": "english"
            }
          }
        }
      ]
    }
  }
}

# 使用模板
PUT /my_index/my_type/3
{
    "title_en": "this is my dog"
}
# 未使用模板，使用standard分词器
PUT /my_index/my_type/5
{
    "title": "this is my cat"
}

# 查询title_en，将无法查出数据（english分词）
GET /my_index/my_type/_search
{
    "query": {
        "match": {
            "title_en": "is"
        }
    }
}
# 查询title，将查出数据（standard分词）
GET /my_index/my_type/_search
{
    "query": {
        "match": {
            "title": "is"
        }
    }
}
```



## 3.27 重建索引

![img](file:///D:/Documents/My Knowledge/temp/3923c1bb-9a06-4f25-9526-8916afc0803d/128/index_files/8d91f330-afd8-42a1-bab5-5b2684fa3d05.jpg)

```shell
# 重建索引

# 新建索引，并插入数据，这里会自动把content映射为date类型
PUT /index1/type1/4
{
  "content": "1990-12-12"
}

GET /index1/type1/_search
GET /index1/_mapping

# content为date类型，插入字符串报错
PUT /index1/type1/4
{
  "content": "I like JAVA"
}
# 要想实现添加字符串类型，就必须把content的类型改为string
# 修改content类型为string类型时报错，不允许修改
PUT /index1/_mapping/type1
{
  "properties": {
    "content": {
      "type": "text"
    }
  }
}

# 字段的类型一旦确定就不能被修改，要想修改字段的类型，只能是新建一个新的索引，新的索引的字段类型是string类型，把旧的索引支行的数据再导入到新的索引中
# 但是，如果新建一个索引，那么在应用程序中使用的是原有的索引，那么就会导致需要重新启动应用程序,为了不用重启应，我们使用别名的方式
PUT /index1/_alias/index2

# 创建新的索引
PUT /newindex
{
  "mappings": {
    "type1": {
      "properties": {
        "content": {
          "type": "text"
        }
      }
    }
  }
}
# 把旧的索引支行的数据再导入到新的索引中,有可能旧的索引中的数据量非常大
# 使用scroll方式批量查询数据，然后使用bulk再批量添加到新的索引中
GET /index1/type1/_search?scroll=1m
{
  "query": {
    "match_all": {}
  },
  "sort": ["_doc"],
  "size": 2
}

# 这里举例，随便手动插入一条
# 采用bulk api将scoll查出来的一批数据，批量写入新索引
# 反复循环scroll查询和bulk批量添加，查询一批又一批的数据出来，采取bulk api将每一批数据批量写入新索引
POST /_bulk
{"index":{"_index":"newindex","_type":"type1","_id":1}}
{"content":"1988-08-08"}

# 把新的所有和别名进行关联
POST /_aliases
{
  "actions": [
    {
      "remove": {
        "index": "index1",
        "alias": "index2"
      }
    },
    {
      "add": {
        "index": "newindex",
        "alias": "index2"
      }
    }
  ]
}

# 通过别名查询索引下的数据
GET /index2/type1/_search
```





## 3.28 索引不可变的原因

![img](file:///D:/Documents/My Knowledge/temp/3923c1bb-9a06-4f25-9526-8916afc0803d/128/index_files/c1cc8dbe-10d3-4c64-b875-ac3ed33b6a4d.jpg)

