# 一、Docker简介

## 1、是什么

### （1）问题：为什么会有docker出现

![img](file:///D:/Documents/My Knowledge/temp/1216a45d-441f-466c-be67-ab70776f9e7b/128/index_files/770ae85d-4f49-4dae-ac54-117993f0580f.jpg)

![img](file:///D:/Documents/My Knowledge/temp/1216a45d-441f-466c-be67-ab70776f9e7b/128/index_files/5b307c99-5f64-449f-b112-2ba6a4f9f00b.jpg)

### （2）docker理念

![img](file:///D:/Documents/My Knowledge/temp/1216a45d-441f-466c-be67-ab70776f9e7b/128/index_files/22caa6dd-7a4a-48bf-814c-9b5215c31ddb.jpg)

### （3）一句话

解决了运行环境和配置问题的软件容器，方便做持续集成并有助于整体发布的容器虚拟化技术。

## 2、能干嘛

### （1）之前的虚拟机技术

![img](file:///D:/Documents/My Knowledge/temp/1216a45d-441f-466c-be67-ab70776f9e7b/128/index_files/67caab26-fe86-4e3d-968b-a9d3da6b956f.jpg)

![img](file:///D:/Documents/My Knowledge/temp/1216a45d-441f-466c-be67-ab70776f9e7b/128/index_files/359df12c-437e-4de3-a1f1-d769a530aa3a.jpg)

### （2）容器虚拟化技术

![img](file:///D:/Documents/My Knowledge/temp/1216a45d-441f-466c-be67-ab70776f9e7b/128/index_files/cea2fe55-bc2f-43f0-bc61-76fe03a5ba4c.jpg)

![img](file:///D:/Documents/My Knowledge/temp/1216a45d-441f-466c-be67-ab70776f9e7b/128/index_files/20b73584-c2ab-418c-9f61-bc84ca4c3067.jpg)

### （3）开发/运维（DevOps）一次构建、随处运行

#### ① 更快速的应用交付和部署

传统的应用开发完成后，需要提供一堆安装程序和配置说明文档，安装部署后需根据配置文档进行繁杂的配置才能正常运行。Docker化之后只需要交付少量容器镜像文件，在正式生产环境加载镜像并运行即可，应用安装配置在镜像里已经内置好，大大节省部署配置和测试验证时间。

#### ② 更便捷的升级和扩缩容

随着微服务架构和Docker的发展，大量的应用会通过微服务方式架构，应用的开发构建将变成搭乐高积木一样，每个Docker容器将变成一块“积木”，应用的升级将变得非常容易。当现有的容器不足以支撑业务处理时，可通过镜像运行新的容器进行快速扩容，使应用系统的扩容从原先的天级变成分钟级甚至秒级。

#### ③ 更简单的系统运维

应用容器化运行后，生产环境运行的应用可与开发、测试环境的应用高度一致，容器会将应用程序相关的环境和状态完全封装起来，不会因为底层基础架构和操作系统的不一致性给应用带来影响，产生新的BUG。当出现程序异常时，也可以通过测试环境的相同容器进行快速定位和修复。

#### ④ 更高效的计算资源利用

Docker是内核级虚拟化，其不像传统的虚拟化技术一样需要额外的Hypervisor支持，所以在一台物理机上可以运行很多个容器实例，可大大提升物理服务器的CPU和内存的利用率。

### （4）企业级案例

#### ① 新浪

![img](file:///D:/Documents/My Knowledge/temp/1216a45d-441f-466c-be67-ab70776f9e7b/128/index_files/b4f0c242-6ea2-4748-a633-fbb629a07643.jpg)

![img](file:///D:/Documents/My Knowledge/temp/1216a45d-441f-466c-be67-ab70776f9e7b/128/index_files/10e78d47-81aa-46ac-a63c-9baf892901fc.jpg)

#### ② 美团

![img](file:///D:/Documents/My Knowledge/temp/1216a45d-441f-466c-be67-ab70776f9e7b/128/index_files/e85126f8-c37f-4865-9c66-0c4d04600300.jpg)

#### ③ 蘑菇街

![img](file:///D:/Documents/My Knowledge/temp/1216a45d-441f-466c-be67-ab70776f9e7b/128/index_files/d5c2f000-df5a-4c1f-9f8e-3cdfd8dba9a8.jpg)

## 3、去哪下

### （1）官网

docker官网：https://www.docker.com/

docker中文网站：https://www.docker-cn.com/

​               http://www.docker.org.cn/index.html

### （2）仓库

https://hub.docker.com/

# 二、Docker安装

## 1、前提说明

![img](file:///D:/Documents/My Knowledge/temp/1216a45d-441f-466c-be67-ab70776f9e7b/128/index_files/15079b59-00cd-48bb-b29b-5b9d105695b7.jpg)

![img](file:///D:/Documents/My Knowledge/temp/1216a45d-441f-466c-be67-ab70776f9e7b/128/index_files/b169d0dd-31b6-4e15-95eb-3af88a2dcdee.png)

## 2、Docker的基本组成(三要素)

Docker的架构图：

![img](file:///D:/Documents/My Knowledge/temp/1216a45d-441f-466c-be67-ab70776f9e7b/128/index_files/384169c2-4610-4613-afae-30c6cd6dd2c9.jpg)

### （1）镜像（image）

![img](file:///D:/Documents/My Knowledge/temp/1216a45d-441f-466c-be67-ab70776f9e7b/128/index_files/38120cf9-7d44-49ee-a9c1-e7548beb6587.jpg)

### （2）容器（container）

![img](file:///D:/Documents/My Knowledge/temp/1216a45d-441f-466c-be67-ab70776f9e7b/128/index_files/84b4a2e0-bd69-46f8-80b0-e9e488c531dc.jpg)

### （3）仓库（repository）

![img](file:///D:/Documents/My Knowledge/temp/1216a45d-441f-466c-be67-ab70776f9e7b/128/index_files/151bbc0d-5e12-4229-a432-a8dcbae533a2.jpg)

### （4）小总结

![img](file:///D:/Documents/My Knowledge/temp/1216a45d-441f-466c-be67-ab70776f9e7b/128/index_files/2626e8c0-6b8f-446b-93a0-f929c5e1f78f.jpg)

## 3、安装步骤

### （1）CentOS6.8安装Docker

#### ① yum install -y epel-release

![img](file:///D:/Documents/My Knowledge/temp/1216a45d-441f-466c-be67-ab70776f9e7b/128/index_files/67505e1a-e019-4b3a-8c80-ca766a4b9adb.jpg)

#### ② yum install -y docker-io

![img](file:///D:/Documents/My Knowledge/temp/1216a45d-441f-466c-be67-ab70776f9e7b/128/index_files/b92967a6-f5f7-4055-b7fb-d4e411da19d0.jpg)

若报错No package docker available

我们只需要运行下面命令：

```shell
yum install https://get.docker.com/rpm/1.7.1/centos-6/RPMS/x86_64/docker-engine-1.7.1-1.el6.x86_64.rpm
```



#### **③ 安装后的配置文件：/etc/sysconfig/docker**

![img](file:///D:/Documents/My Knowledge/temp/1216a45d-441f-466c-be67-ab70776f9e7b/128/index_files/7556c412-d46d-401c-9121-d39352cb7d15.jpg)

#### ④ 启动Docker后台服务：service docker start

#### ⑤ docker version验证

![img](file:///D:/Documents/My Knowledge/temp/1216a45d-441f-466c-be67-ab70776f9e7b/128/index_files/400b7716-8db0-4dcb-bf39-8a4595029fc1.png)

#### ⑥ 卸载docker

```shell
列出所有docker安装包：yum list installed | grep docker

删除安装包: yum -y remove docker-io.x86_64

删除所有镜像容器等: rm -rf /var/lib/docker
```



![img](file:///D:/Documents/My Knowledge/temp/1216a45d-441f-466c-be67-ab70776f9e7b/128/index_files/12c0a5c4-1fa7-4a03-9254-9375385c27a4.png)

### （2）CentOS7安装Docker

#### ① 官网安装手册

https://docs.docker.com/engine/install/centos/

#### ② 安装步骤

##### 1、确定你是CentOS7及以上版本

```shell
cat /etc/redhat-release
```



##### 2、yum安装gcc相关

CentOS7能上外网：

![img](file:///D:/Documents/My Knowledge/temp/1216a45d-441f-466c-be67-ab70776f9e7b/128/index_files/5984afd8-d1ab-4e7e-b8d1-3c92bc7299c2.png)

```shell
yum -y install gcc
yum -y install gcc-c++
gcc -v
```



##### 3、卸载旧版本

```shell
 sudo yum remove docker \
                  docker-client \
                  docker-client-latest \
                  docker-common \
                  docker-latest \
                  docker-latest-logrotate \
                  docker-logrotate \
                  docker-engine
```



##### 4、安装需要的软件包

```shell
sudo yum install -y yum-utils
```



##### 5、设置stable镜像仓库

大坑：

```shell
yum-config-manager --add-repo https://download.docker.com/linux/centos/docker-ce.repo
```

推荐：

```shell
yum-config-manager --add-repo http://mirrors.aliyun.com/docker-ce/linux/centos/docker-ce.repo
```



##### 6、更新yum软件包索引

```shell
yum makecache fast
```



##### 7、安装DOCKER CE

查看docker版本，一般使用稳定版：

```shell
yum list docker-ce --showduplicates | sort -r
```



![img](file:///D:/Documents/My Knowledge/temp/1216a45d-441f-466c-be67-ab70776f9e7b/128/index_files/279a504e-0b70-43d2-a43a-0bc8164b9b39.png)

安装docker、默认安装最新版本：

```shell
yum install docker-ce docker-ce-cli containerd.io
```



安装某特定版本需增加版本号（如18.06.3.ce-3.el7）：

```shell
yum install docker-ce-18.06.3.ce docker-ce-cli-18.06.3.ce containerd.io
```

开机启动：

```shell
systemctl enable docker
```



##### 8、启动docker

```shell
systemctl start docker
```

##### 9、测试

```shell
docker version
docker run hello-world
```



##### 10、配置镜像加速

```shell
mkdir -p /etc/docker
```

```shell
vim  /etc/docker/daemon.json

配置镜像加速，参考下一章节：永远的HelloWorld
 
 #网易云
{"registry-mirrors": ["http://hub-mirror.c.163.com"] }

或

 #阿里云
{
  "registry-mirrors": ["https://｛自已的编码｝.mirror.aliyuncs.com"]
}
```



```shell
systemctl daemon-reload
systemctl restart docker
ps -ef|grep docker |grep -v grep
```

![img](file:///D:/Documents/My Knowledge/temp/1216a45d-441f-466c-be67-ab70776f9e7b/128/index_files/b86dddf8-40f7-473a-aacb-02e8819e62bd.png)

##### 11、卸载

```shell
systemctl stop docker 
yum remove docker-ce docker-ce-cli containerd.io
rm -rf /var/lib/docker
```

必须手动删除所有已编辑的配置文件。

## **4、永远的HelloWorld**

### （1）阿里云镜像加速

#### ① 是什么

https://dev.aliyun.com/search.html

#### ② 注册一个属于自己的阿里云账户(可复用淘宝账号)

#### ③ 获得加速器地址连接

登陆阿里云开发者平台；

获取加速器地址：

![img](file:///D:/Documents/My Knowledge/temp/1216a45d-441f-466c-be67-ab70776f9e7b/128/index_files/08e59114-56ab-405f-ad94-38be29f2407c.png)

#### ④ 配置本机Docker运行镜像加速器

![img](file:///D:/Documents/My Knowledge/temp/1216a45d-441f-466c-be67-ab70776f9e7b/128/index_files/0af39cde-971e-4767-92f3-4424fb93609f.jpg)

 

```shell
--registry-mirror=https://xxx.mirror.aliyuncs.com
```

#### ⑤ 重新启动Docker后台服务

```shell
service docker restart
```

#### ⑥ Linux 系统下配置完加速器需要检查是否生效

```shell
ps -ef|grep docker
```

![img](file:///D:/Documents/My Knowledge/temp/1216a45d-441f-466c-be67-ab70776f9e7b/128/index_files/4aef69e8-812f-49f8-a927-f640294fc862.png)

### （2）网易云加速

![img](file:///D:/Documents/My Knowledge/temp/1216a45d-441f-466c-be67-ab70776f9e7b/128/index_files/876a012d-5758-4bca-bf4e-8e2fbf911126.png)

### （3）启动Docker后台容器(测试运行 hello-world)

```shell
docker run hello-world
```

![img](file:///D:/Documents/My Knowledge/temp/1216a45d-441f-466c-be67-ab70776f9e7b/128/index_files/a2dcccf0-6500-4d67-ac16-ed0f64574402.png)

![img](file:///D:/Documents/My Knowledge/temp/1216a45d-441f-466c-be67-ab70776f9e7b/128/index_files/d24193f3-3307-4f9b-862b-1901fda27a97.png)

## 5、底层原理

### （1）Docker是怎么工作的

![img](file:///D:/Documents/My Knowledge/temp/1216a45d-441f-466c-be67-ab70776f9e7b/128/index_files/298064df-3bd1-4950-b5c9-8682865d9d55.jpg)

![img](file:///D:/Documents/My Knowledge/temp/1216a45d-441f-466c-be67-ab70776f9e7b/128/index_files/32cd7a47-ae06-445d-a2eb-8ec29607a7fd.jpg)

### （2）为什么Docker比较比VM快

![img](file:///D:/Documents/My Knowledge/temp/1216a45d-441f-466c-be67-ab70776f9e7b/128/index_files/4f6eff06-cd9c-46f5-a28e-b6f31754a7e0.jpg)

![img](file:///D:/Documents/My Knowledge/temp/1216a45d-441f-466c-be67-ab70776f9e7b/128/index_files/0bbf496d-54ff-44e2-b968-663fced53e26.jpg)

# 三、Docker常用命令

## 1、帮助命令

```shell
docker version
docker info
docker --help
```

## 2、镜像命令

### （1）docker images

#### ① 意思：列出本地主机上的镜像

![img](file:///D:/Documents/My Knowledge/temp/1216a45d-441f-466c-be67-ab70776f9e7b/128/index_files/f90179ac-33c1-4cbf-93fc-0e0bb6273078.jpg)

#### ② OPTIONS说明：

```shell
-a :列出本地所有的镜像（含中间映像层）
-q :只显示镜像ID。
--digests :显示镜像的摘要信息
--no-trunc :显示完整的镜像信息
```

![img](file:///D:/Documents/My Knowledge/temp/1216a45d-441f-466c-be67-ab70776f9e7b/128/index_files/41fcfa1e-81fb-4790-a616-2b62f989be20.png)

### （2）docker search 某个XXX镜像名字

#### ① 网站

从https://hub.docker.com搜索镜像

#### ② 命令

```shell
docker search [OPTIONS] 镜像名字

OPTIONS说明：
    --no-trunc : 显示完整的镜像描述
    -s : 列出收藏数不小于指定值的镜像。
    --automated : 只列出 automated build类型的镜像；
```

![img](file:///D:/Documents/My Knowledge/temp/1216a45d-441f-466c-be67-ab70776f9e7b/128/index_files/93cb4e84-920b-455e-b79f-9ed4ffb22813.png)

### （3）docker pull 某个XXX镜像名字

下载镜像

```shell
docker pull 镜像名字[:TAG]
```

### （4）docker rmi 某个XXX镜像名字ID

删除镜像

```shell
删除单个: docker rmi  -f 镜像ID
删除多个: docker rmi -f 镜像名1:TAG 镜像名2:TAG 
删除全部: docker rmi -f $(docker images -qa)
```

![img](file:///D:/Documents/My Knowledge/temp/1216a45d-441f-466c-be67-ab70776f9e7b/128/index_files/8e992feb-b6aa-4e76-bb30-778505cfc9fe.png)

![img](file:///D:/Documents/My Knowledge/temp/1216a45d-441f-466c-be67-ab70776f9e7b/128/index_files/2c3b2fcd-10a5-438b-b7f4-2035c96303a1.png)

## 3、容器命令

### （1）有镜像才能创建容器，这是根本前提(下载一个CentOS镜像演示)

```shell
docker pull centos
```

### （2）新建并启动容器

```shell
docker run [OPTIONS] IMAGE [COMMAND] [ARG...]
```

####  OPTIONS说明：

```shell
 OPTIONS说明（常用）：有些是一个减号，有些是两个减号
 
--name="容器新名字": 为容器指定一个名称；
-d: 后台运行容器，并返回容器ID，也即启动守护式容器；
-i：以交互模式运行容器，通常与 -t 同时使用；
-t：为容器重新分配一个伪输入终端，通常与 -i 同时使用；
-P: 随机端口映射；
-p: 指定端口映射，有以下四种格式
      ip:hostPort:containerPort
      ip::containerPort
      hostPort:containerPort
      containerPort
```



docker run -it centos提示FATAL: kernel too old。docker认为你的内核版本不适配，需要升级。

```shell
#centos6.8环境下
# 更新nss
yum update nss
# 安装elrepo的yum源
rpm --import https://www.elrepo.org/RPM-GPG-KEY-elrepo.org
rpm -Uvh http://www.elrepo.org/elrepo-release-6-8.el6.elrepo.noarch.rpm
# 升级内核
安装ml内核使用如下命令：
    yum --enablerepo=elrepo-kernel -y install kernel-ml
安装lt内核使用如下命令：
    yum --enablerepo=elrepo-kernel -y install kernel-lt
# 此处选择lt内核
# 修改grub.conf文件
vim /etc/grub.conf
default=0
# 然后重启系统
reboot
# 再次查看内核详细信息
uname -a
# 再次启动容器
docker run -it centos
```



![img](file:///D:/Documents/My Knowledge/temp/1216a45d-441f-466c-be67-ab70776f9e7b/128/index_files/cefa74f1-ca67-4cd5-aa4e-82e58e19e300.png)

#### **启动交互式容器：**

```shell
#使用镜像centos:latest以交互模式启动一个容器,在容器内执行/bin/bash命令。
docker run -it centos /bin/bash 
```

![img](file:///D:/Documents/My Knowledge/temp/1216a45d-441f-466c-be67-ab70776f9e7b/128/index_files/b2aea837-8661-4c66-99ab-d7db8fa4bea4.png)

### （3）列出当前所有正在运行的容器

```shell
docker ps [OPTIONS]
```

####  OPTIONS说明：

```shell
OPTIONS说明（常用）：
 
-a :列出当前所有正在运行的容器+历史上运行过的
-l :显示最近创建的容器。
-n：显示最近n个创建的容器。
-q :静默模式，只显示容器编号。
--no-trunc :不截断输出。
```

### （4）退出容器

两种退出方式：

```shell
exit      #容器停止退出
ctrl+P+Q  #容器不停止退出
```

### （5）启动容器

```shell
docker start 容器ID或者容器名
```

### （6）重启容器

```shell
docker restart 容器ID或者容器名
```

### （7）停止容器

```shell
docker stop 容器ID或者容器名
```

### （8）强制停止容器

```shell
docker kill 容器ID或者容器名
```

### （9）删除已停止的容器

```shell
docker rm 容器ID

一次性删除多个容器：
    docker rm -f $(docker ps -a -q)
    docker ps -a -q | xargs docker rm
```

### （10）重要

#### ① 启动守护式容器

```shell
docker run -d 容器名
```

![img](file:///D:/Documents/My Knowledge/temp/1216a45d-441f-466c-be67-ab70776f9e7b/128/index_files/ff6b5b6d-a9b8-4a3b-afbe-2d67c8d569d9.jpg)

#### ② 查看容器日志

```shell
docker logs -f -t --tail 容器ID

*   -t 是加入时间戳
*   -f 跟随最新的日志打印
*   --tail 数字 显示最后多少条
```



```shell
 
 docker run -d centos /bin/sh -c "while true;do echo hello zzyy;sleep 2;done"
```



#### ③ 查看容器内运行的进程

```shell
docker top 容器ID
```

#### ④ 查看容器内部细节

```shell
docker inspect 容器ID
```

#### ⑤ 进入正在运行的容器并以命令行交互

```shell
docker exec -it 容器ID bashShell

重新进入：docker attach 容器ID
```

![img](file:///D:/Documents/My Knowledge/temp/1216a45d-441f-466c-be67-ab70776f9e7b/128/index_files/c0424ae9-c95c-45af-8ea9-bcd9cdaba88c.png)

上述两个区别：

```shell
attach 直接进入容器启动命令的终端，不会启动新的进程
exec 是在容器中打开新的终端，并且可以启动新的进程
```

#### ⑥ 从容器内拷贝文件到主机上

```shell
docker cp  容器ID:容器内路径 目的主机路径
```

![img](file:///D:/Documents/My Knowledge/temp/1216a45d-441f-466c-be67-ab70776f9e7b/128/index_files/20abd026-cb77-4715-a764-aedb46e5b99e.jpg)

## 4、小总结

### （1）常用命令

```shell
attach    Attach to a running container                 # 当前 shell 下 attach 连接指定运行镜像
build     Build an image from a Dockerfile              # 通过 Dockerfile 定制镜像
commit    Create a new image from a container changes   # 提交当前容器为新的镜像
cp        Copy files/folders from the containers filesystem to the host path   #从容器中拷贝指定文件或者目录到宿主机中
create    Create a new container                        # 创建一个新的容器，同 run，但不启动容器
diff      Inspect changes on a container's filesystem   # 查看 docker 容器变化
events    Get real time events from the server          # 从 docker 服务获取容器实时事件
exec      Run a command in an existing container        # 在已存在的容器上运行命令
export    Stream the contents of a container as a tar archive   # 导出容器的内容流作为一个 tar 归档文件[对应 import ]
history   Show the history of an image                  # 展示一个镜像形成历史
images    List images                                   # 列出系统当前镜像
import    Create a new filesystem image from the contents of a tarball # 从tar包中的内容创建一个新的文件系统映像[对应export]
info      Display system-wide information               # 显示系统相关信息
inspect   Return low-level information on a container   # 查看容器详细信息
kill      Kill a running container                      # kill 指定 docker 容器
load      Load an image from a tar archive              # 从一个 tar 包中加载一个镜像[对应 save]
login     Register or Login to the docker registry server    # 注册或者登陆一个 docker 源服务器
logout    Log out from a Docker registry server          # 从当前 Docker registry 退出
logs      Fetch the logs of a container                 # 输出当前容器日志信息
port      Lookup the public-facing port which is NAT-ed to PRIVATE_PORT    # 查看映射端口对应的容器内部源端口
pause     Pause all processes within a container        # 暂停容器
ps        List containers                               # 列出容器列表
pull      Pull an image or a repository from the docker registry server   # 从docker镜像源服务器拉取指定镜像或者库镜像
push      Push an image or a repository to the docker registry server    # 推送指定镜像或者库镜像至docker源服务器
restart   Restart a running container                   # 重启运行的容器
rm        Remove one or more containers                 # 移除一个或者多个容器
rmi       Remove one or more images             # 移除一个或多个镜像[无容器使用该镜像才可删除，否则需删除相关容器才可继续或 -f 强制删除]
run       Run a command in a new container              # 创建一个新的容器并运行一个命令
save      Save an image to a tar archive                # 保存一个镜像为一个 tar 包[对应 load]
search    Search for an image on the Docker Hub         # 在 docker hub 中搜索镜像
start     Start a stopped containers                    # 启动容器
stop      Stop a running containers                     # 停止容器
tag       Tag an image into a repository                # 给源中镜像打标签
top       Lookup the running processes of a container   # 查看容器中运行的进程信息
unpause   Unpause a paused container                    # 取消暂停容器
version   Show the docker version information           # 查看 docker 版本号
wait      Block until a container stops, then print its exit code   # 截取容器停止时的退出状态值
```



# 四、Docker 镜像

## 1、是什么

![img](file:///D:/Documents/My Knowledge/temp/1216a45d-441f-466c-be67-ab70776f9e7b/128/index_files/b41a591d-148f-4200-9de3-354d19810643.jpg)

### （1）UnionFS（联合文件系统）

![img](file:///D:/Documents/My Knowledge/temp/1216a45d-441f-466c-be67-ab70776f9e7b/128/index_files/2a836cba-68aa-4a18-96cc-2e80818207ed.jpg)

### （2） Docker镜像加载原理

![img](file:///D:/Documents/My Knowledge/temp/1216a45d-441f-466c-be67-ab70776f9e7b/128/index_files/48c42714-56af-4a11-b62d-8a496fdc6fc0.jpg)

![img](file:///D:/Documents/My Knowledge/temp/1216a45d-441f-466c-be67-ab70776f9e7b/128/index_files/3c888431-d543-41d6-aee4-b3adba065e63.jpg)

### （3）分层的镜像

![img](file:///D:/Documents/My Knowledge/temp/1216a45d-441f-466c-be67-ab70776f9e7b/128/index_files/441593aa-3067-4ea2-8698-45794446dd69.png)

### （4）为什么 Docker 镜像要采用这种分层结构呢

![img](file:///D:/Documents/My Knowledge/temp/1216a45d-441f-466c-be67-ab70776f9e7b/128/index_files/5b1b70ff-052c-4202-927c-e18c63187725.jpg)

## 2、特点

![img](file:///D:/Documents/My Knowledge/temp/1216a45d-441f-466c-be67-ab70776f9e7b/128/index_files/28f0e4a9-0ec7-4b2d-87b9-6375ad53c014.png)

## 3、Docker镜像commit操作补充

### （1）docker commit提交容器副本使之成为一个新的镜像

### （2）docker commit -m=“提交的描述信息” -a=“作者” 容器ID 要创建的目标镜像名:[标签名]

### （3）案例演示

#### ① 从Hub上下载tomcat镜像到本地并成功运行

```shell
docker run -it -p 8888:8080 tomcat

-p 主机端口:docker容器端口
-P 随机分配端口
i:交互
t:终端
```

![img](file:///D:/Documents/My Knowledge/temp/1216a45d-441f-466c-be67-ab70776f9e7b/128/index_files/69386840-ad20-462a-bec4-35fe87728d27.png)

#### ② 故意删除上一步镜像生产tomcat容器的文档

![img](file:///D:/Documents/My Knowledge/temp/1216a45d-441f-466c-be67-ab70776f9e7b/128/index_files/288c8020-1a88-4602-b468-c29e6d373ef9.png)

#### ③ 也即当前的tomcat运行实例是一个没有文档内容的容器，以它为模板commit一个没有doc的tomcat新镜像atguigu/tomcat02

![img](file:///D:/Documents/My Knowledge/temp/1216a45d-441f-466c-be67-ab70776f9e7b/128/index_files/f6e55024-17c6-4113-a3cc-6e91dd85f8c9.png)

#### ④ 启动我们的新镜像并和原来的对比

启动atguigu/tomcat02，它没有docs：

![img](file:///D:/Documents/My Knowledge/temp/1216a45d-441f-466c-be67-ab70776f9e7b/128/index_files/e7af1e80-daf3-4fe1-9933-49d7c738a1ee.png)

新启动原来的tomcat，它有docs：

![img](file:///D:/Documents/My Knowledge/temp/1216a45d-441f-466c-be67-ab70776f9e7b/128/index_files/5303c16d-b693-4ca6-820f-10b6daf1645f.png)

# 五、Docker容器数据卷

## 1、是什么

一句话：有点类似我们Redis里面的rdb和aof文件

![img](file:///D:/Documents/My Knowledge/temp/1216a45d-441f-466c-be67-ab70776f9e7b/128/index_files/9030cbb2-9d2b-4aca-8309-24b6f2b5bde7.jpg)

## 2、能干嘛

```shell
容器的持久化；
容器间继承+共享数据；
```

![img](file:///D:/Documents/My Knowledge/temp/1216a45d-441f-466c-be67-ab70776f9e7b/128/index_files/58d001f3-0dbf-4ad3-b729-df7496fcbeae.jpg)

## 3、数据卷

### （1）直接命令添加

#### ① 命令

```shell
 docker run -it -v /宿主机绝对路径目录:/容器内目录      镜像名
 
 docker run -it -v /myDataVolume:/dataVolumeContainer centos
```

#### ② 查看数据卷是否挂载成功

```shell
docker inspect 容器ID
```

![img](file:///D:/Documents/My Knowledge/temp/1216a45d-441f-466c-be67-ab70776f9e7b/128/index_files/d4140633-1f87-42dd-8bb2-7dbe9575d65f.png)

#### ③ 容器和宿主机之间数据共享

![img](file:///D:/Documents/My Knowledge/temp/1216a45d-441f-466c-be67-ab70776f9e7b/128/index_files/f1e3ba3b-6b85-491a-8f49-c8979462d3e4.png)

#### ④ 容器停止退出后，主机修改后数据是否同步

![img](file:///D:/Documents/My Knowledge/temp/1216a45d-441f-466c-be67-ab70776f9e7b/128/index_files/0805d32c-18ad-453b-9cd8-a76128c96064.png)

#### ⑤ 命令(带权限)

```shell
 # ro只读
 docker run -it -v /宿主机绝对路径目录:/容器内目录:ro 镜像名
 
 docker run -it -v /myDataVolume:/dataVolumeContainer:ro centos    #主机可以新建同步到容器，但是容器里面是只读的，不能新建和修改。
```

### （2）DockerFile添加

#### ① 根目录下新建mydocker文件夹并进入

#### ② 可在Dockerfile中使用VOLUME指令来给镜像添加一个或多个数据卷

![img](file:///D:/Documents/My Knowledge/temp/1216a45d-441f-466c-be67-ab70776f9e7b/128/index_files/668503b7-b63c-47bd-931e-372b3ec4d44f.png)

```shell
VOLUME["/dataVolumeContainer","/dataVolumeContainer2","/dataVolumeContainer3"]
```

#### ③ DockerFile构建

 

```shell
vim DockerFile

# volume test
FROM centos
VOLUME ["/dataVolumeContainer1","/dataVolumeContainer2"]
CMD echo "finished,--------success1"
CMD /bin/bash
```

```shell
等同于之前使用命令添加数据卷：
docker run -it -v /host1:/dataVolumeContainer1 -v /host2:/dataVolumeContainer2 centos /bin/bash
```

#### ④ build后生成镜像

获得一个新镜像zzyy/centos

![img](file:///D:/Documents/My Knowledge/temp/1216a45d-441f-466c-be67-ab70776f9e7b/128/index_files/5599cb5a-49a1-4def-b6e4-69cac350eb31.png)

#### ⑤ run容器

![img](file:///D:/Documents/My Knowledge/temp/1216a45d-441f-466c-be67-ab70776f9e7b/128/index_files/e2712f36-6f31-48f7-9361-1f0c66f66990.png)

#### ⑥ 通过上述步骤，容器内的卷目录地址已经知道对应的主机目录地址哪？？

```shell
docker inspect 容器id
```

![img](file:///D:/Documents/My Knowledge/temp/1216a45d-441f-466c-be67-ab70776f9e7b/128/index_files/ff267a99-f1e2-4a7a-bdff-87fc415048f7.png)

#### ⑦ 主机对应默认地址

![img](file:///D:/Documents/My Knowledge/temp/1216a45d-441f-466c-be67-ab70776f9e7b/128/index_files/1ec29702-f27e-4cba-9574-5e7bbf572e7d.png)

### （3）备注

![img](file:///D:/Documents/My Knowledge/temp/1216a45d-441f-466c-be67-ab70776f9e7b/128/index_files/b9ec0ab3-2928-4cba-85d2-754589675493.jpg)

![img](file:///D:/Documents/My Knowledge/temp/1216a45d-441f-466c-be67-ab70776f9e7b/128/index_files/967f7924-0460-4882-8995-f8912f89b5a8.png)

## 4、数据卷容器

### （1）是什么

![img](file:///D:/Documents/My Knowledge/temp/1216a45d-441f-466c-be67-ab70776f9e7b/128/index_files/c1e7c9c2-1435-413f-9a6f-36974a09fd29.png)

### （2）总体介绍

① 以上一步新建的镜像zzyy/centos为模板并运行容器dc01/dc02/dc03；

②它们已经具有容器卷：

- /dataVolumeContainer1
- /dataVolumeContainer2

### （3）容器间传递共享(--volumes-from)

#### ① 先启动一个父容器dc01

在dataVolumeContainer2新增内容

```shell
docker run -it --name dc01 zzyy/centos
cd dataVolumeContainer2
touch dc01_add.txt
ls -l
```

![img](file:///D:/Documents/My Knowledge/temp/1216a45d-441f-466c-be67-ab70776f9e7b/128/index_files/b9d88729-1fa4-47cd-bcf7-1c78b2fc1615.png)

#### ② dc02/dc03继承自dc01

```shell
--volumes-from

#命令
docker run -it --name dc02 --volumes-from dc01 zzyy/centos
```

dc02/dc03分别在dataVolumeContainer2各自新增内容：

```shell
cd dataVolumeContainer2
touch dc02_add.txt
```

```shell
cd dataVolumeContainer2
touch dc03_add.txt
```

![img](file:///D:/Documents/My Knowledge/temp/1216a45d-441f-466c-be67-ab70776f9e7b/128/index_files/c4d6fa55-6d9a-4e64-a0ed-6838d2771aaf.png)

#### ③ 回到dc01可以看到02/03各自添加的都能共享了

![img](file:///D:/Documents/My Knowledge/temp/1216a45d-441f-466c-be67-ab70776f9e7b/128/index_files/441ea4d3-9aa4-4bd8-b552-b8e4ab1ab35a.png)

#### ④ 删除dc01，dc02修改后dc03可否访问

![img](file:///D:/Documents/My Knowledge/temp/1216a45d-441f-466c-be67-ab70776f9e7b/128/index_files/b287a19d-9580-41f1-bb35-cfaa039d04b7.png)

#### ⑤ 删除dc02后dc03可访问

#### ⑥ 新建dc04继承dc03后再删除dc03

![img](file:///D:/Documents/My Knowledge/temp/1216a45d-441f-466c-be67-ab70776f9e7b/128/index_files/13a176c4-f2cb-47b2-a6ac-80d32a8d83af.png)

#### ⑦ 结论：容器之间配置信息的传递，数据卷的生命周期一直持续到没有容器使用它为止

# 六、DockerFile解析

## 1、是什么

### （1）定义

Dockerfile是用来构建Docker镜像的构建文件，是由一系列命令和参数构成的脚本。

### （2）构建三步骤

编写Dockerfile文件 -》docker build -》docker run

### （3）文件什么样

以我们熟悉的CentOS为例 ：https://hub.docker.com/_/centos/ 

![img](file:///D:/Documents/My Knowledge/temp/1216a45d-441f-466c-be67-ab70776f9e7b/128/index_files/c6a13df2-ba87-4c6d-82c4-d094477e62f1.png)

## 2、DockerFile构建过程解析

### （1）Dockerfile内容基础知识

![img](file:///D:/Documents/My Knowledge/temp/1216a45d-441f-466c-be67-ab70776f9e7b/128/index_files/c59e7db9-c50d-42c3-be46-bccdc3f5bec6.png)

### （2）Docker执行Dockerfile的大致流程

![img](file:///D:/Documents/My Knowledge/temp/1216a45d-441f-466c-be67-ab70776f9e7b/128/index_files/7c55ac83-8b45-42c1-b5fd-b781c5b68134.png)

### （3）小总结

![img](file:///D:/Documents/My Knowledge/temp/1216a45d-441f-466c-be67-ab70776f9e7b/128/index_files/b952456e-0afc-43c6-94cf-b7584cae27d5.jpg)

![img](file:///D:/Documents/My Knowledge/temp/1216a45d-441f-466c-be67-ab70776f9e7b/128/index_files/01222fab-7b21-4e13-b668-e85e748aba57.jpg)

## 3、DockerFile体系结构(保留字指令)

### （1）FROM

基础镜像，当前新镜像是基于哪个镜像的。

### （2）MAINTAINER

镜像维护者的姓名和邮箱地址。

### （3）RUN

容器构建时需要运行的命令。

### （4）EXPOSE

当前容器对外暴露出的端口。

### （5）WORKDIR

指定在创建容器后，终端默认登陆的进来工作目录，一个落脚点。

### （6）ENV

用来在构建镜像过程中设置环境变量

![img](file:///D:/Documents/My Knowledge/temp/1216a45d-441f-466c-be67-ab70776f9e7b/128/index_files/c5488c08-2a66-40fb-b047-3e25c27b4332.jpg)

### （7）ADD

将宿主机目录下的文件拷贝进镜像且ADD命令会自动处理URL和解压tar压缩包。

### （8）COPY

类似ADD，拷贝文件和目录到镜像中。

将从构建上下文目录中 <源路径> 的文件/目录复制到新的一层的镜像内的 <目标路径> 位置。

```shell
COPY src dest
COPY ["src", "dest"]
```

### （9）VOLUME

容器数据卷，用于数据保存和持久化工作。

### （10）CMD

指定一个容器启动时要运行的命令：

![img](file:///D:/Documents/My Knowledge/temp/1216a45d-441f-466c-be67-ab70776f9e7b/128/index_files/40c57cd3-2508-475b-b324-d4b1ce46d12f.png)

Dockerfile 中可以有多个 CMD 指令，但只有最后一个生效，CMD 会被 docker run 之后的参数替换。

### （11）ENTRYPOINT 

指定一个容器启动时要运行的命令；

ENTRYPOINT 的目的和 CMD 一样，都是在指定容器启动程序及参数。

### （12）ONBUILD

当构建一个被继承的Dockerfile时运行命令，父镜像在被子继承后父镜像的onbuild被触发。

![img](file:///D:/Documents/My Knowledge/temp/1216a45d-441f-466c-be67-ab70776f9e7b/128/index_files/f5dd8a03-c1ec-4b02-b2d8-926ce2e6ad80.png)

![img](file:///D:/Documents/My Knowledge/temp/1216a45d-441f-466c-be67-ab70776f9e7b/128/index_files/f77d17e1-6365-43df-88dc-a2d896c23570.png)

### （13）小总结

![img](file:///D:/Documents/My Knowledge/temp/1216a45d-441f-466c-be67-ab70776f9e7b/128/index_files/a5c33be5-ce87-4e39-8b6f-6956bb7798ee.png)

## 4、案例

### （1）Base镜像(scratch)

Docker Hub 中 99% 的镜像都是通过在 base 镜像中安装和配置需要的软件构建出来的。

![img](file:///D:/Documents/My Knowledge/temp/1216a45d-441f-466c-be67-ab70776f9e7b/128/index_files/c727cfca-e066-4630-9785-33960b835b7a.jpg)

### （2）自定义镜像mycentos

#### ① 编写

##### Hub默认CentOS镜像什么情况

![img](file:///D:/Documents/My Knowledge/temp/1216a45d-441f-466c-be67-ab70776f9e7b/128/index_files/0548291f-bf35-4fa4-9eac-7ca8a2f929cf.png)

##### 准备编写DockerFile文件

![img](file:///D:/Documents/My Knowledge/temp/1216a45d-441f-466c-be67-ab70776f9e7b/128/index_files/2e44e440-73d4-44d8-9ecd-0df1cd22839e.png)

##### myCentOS内容DockerFile

```shell
FROM centos
MAINTAINER zzyy<zzyy167@126.com>
ENV MYPATH /usr/local
WORKDIR $MYPATH
RUN yum -y install vim
RUN yum -y install net-tools
EXPOSE 80
CMD echo $MYPATH
CMD echo "success--------------ok"
CMD /bin/bash 
```

#### **② 构建**

```shell
docker build -t 新镜像名字:TAG .
```

![img](file:///D:/Documents/My Knowledge/temp/1216a45d-441f-466c-be67-ab70776f9e7b/128/index_files/dbba3374-dedb-405a-8228-4f4410f3ca09.png)

#### ③ 运行

```shell
docker run -it 新镜像名字:TAG 
```

可以看到，我们自己的新镜像已经支持vim/ifconfig命令，扩展成功了。

![img](file:///D:/Documents/My Knowledge/temp/1216a45d-441f-466c-be67-ab70776f9e7b/128/index_files/19f37b54-e4a6-4410-add4-81e310e7e023.png)

#### ④ 列出镜像的变更历史

```shell
docker history 镜像名
```

### （3）CMD/ENTRYPOINT 镜像案例

#### ① 都是指定一个容器启动时要运行的命令

#### ② CMD

Dockerfile 中可以有多个 CMD 指令，但只有最后一个生效，CMD 会被 docker run 之后的参数替换。

```shell
#tomcat的讲解演示：
docker run -it -p 8888:8080 tomcat ls -l
```

![img](file:///D:/Documents/My Knowledge/temp/1216a45d-441f-466c-be67-ab70776f9e7b/128/index_files/46dce8f7-7418-4f34-a3f5-d703ee42d39d.png)

#### ③ ENTRYPOINT 

docker run 之后的参数会被当做参数传递给 ENTRYPOINT，之后形成新的命令组合。

##### CASE：

###### 制作CMD版可以查询IP信息的容器：

```shell
 FROM centos
 RUN yum install -y curl
 CMD [ "curl", "-s", "http://ip.cn" ]
```

![img](file:///D:/Documents/My Knowledge/temp/1216a45d-441f-466c-be67-ab70776f9e7b/128/index_files/850841a4-f174-4641-956d-4d6329391e1b.png)

crul命令解释：

![img](file:///D:/Documents/My Knowledge/temp/1216a45d-441f-466c-be67-ab70776f9e7b/128/index_files/c92a6259-647f-4ab4-a77a-a32fbfe80a5e.jpg)

###### 问题：

如果我们希望显示 HTTP 头信息，就需要加上 -i 参数。

![img](file:///D:/Documents/My Knowledge/temp/1216a45d-441f-466c-be67-ab70776f9e7b/128/index_files/52d53aa9-8a4e-4f47-a095-e933294a23f4.png)

###### WHY：

![img](file:///D:/Documents/My Knowledge/temp/1216a45d-441f-466c-be67-ab70776f9e7b/128/index_files/63a0eba4-8a69-46db-af8e-7f0bb6b8c9a0.jpg)

###### 制作ENTROYPOINT版查询IP信息的容器：

```shell
FROM centos
RUN yum install -y curl
ENTRYPOINT [ "curl", "-s", "http://ip.cn" ]
```

![img](file:///D:/Documents/My Knowledge/temp/1216a45d-441f-466c-be67-ab70776f9e7b/128/index_files/5e281881-1a7b-4a92-87a3-3f831f8146f7.png)

### （4）自定义镜像Tomcat9

#### ① mkdir -p /zzyyuse/mydockerfile/tomcat9

#### ② 在上述目录下touch c.txt

#### ③ 将jdk和tomcat安装的压缩包拷贝进上一步目录

#### ④ 在/zzyyuse/mydockerfile/tomcat9目录下新建Dockerfile文件

![img](file:///D:/Documents/My Knowledge/temp/1216a45d-441f-466c-be67-ab70776f9e7b/128/index_files/5b5fb1a8-71e1-4342-8ded-7b899270192c.jpg)

```shell
 FROM centos
 MAINTAINER    zzyy<zzyybs@126.com>
 #把宿主机当前上下文的c.txt拷贝到容器/usr/local/路径下
 COPY c.txt /usr/local/cincontainer.txt
 #把java与tomcat添加到容器中
 ADD jdk-8u171-linux-x64.tar.gz /usr/local/
 ADD apache-tomcat-9.0.8.tar.gz /usr/local/
 #安装vim编辑器
 RUN yum -y install vim
 #设置工作访问时候的WORKDIR路径，登录落脚点
 ENV MYPATH /usr/local
 WORKDIR $MYPATH
 #配置java与tomcat环境变量
 ENV JAVA_HOME /usr/local/jdk1.8.0_171
 ENV CLASSPATH $JAVA_HOME/lib/dt.jar:$JAVA_HOME/lib/tools.jar
 ENV CATALINA_HOME /usr/local/apache-tomcat-9.0.8
 ENV CATALINA_BASE /usr/local/apache-tomcat-9.0.8
 ENV PATH $PATH:$JAVA_HOME/bin:$CATALINA_HOME/lib:$CATALINA_HOME/bin
 #容器运行时监听的端口
 EXPOSE  8080
 #启动时运行tomcat
 # ENTRYPOINT ["/usr/local/apache-tomcat-9.0.8/bin/startup.sh" ]
 # CMD ["/usr/local/apache-tomcat-9.0.8/bin/catalina.sh","run"]
 CMD /usr/local/apache-tomcat-9.0.8/bin/startup.sh && tail -F /usr/local/apache-tomcat-9.0.8/bin/logs/catalina.out
 
```

#### ⑤ 构建

![img](file:///D:/Documents/My Knowledge/temp/1216a45d-441f-466c-be67-ab70776f9e7b/128/index_files/d5a5bec7-47c4-42aa-bd1b-ace3c73ac8bc.png)

#### ⑥ run运行

```shell
 docker run -d -p 9080:8080 --name myt9 -v /zzyyuse/mydockerfile/tomcat9/test:/usr/local/apache-tomcat-9.0.8/webapps/test -v /zzyyuse/mydockerfile/tomcat9/tomcat9logs/:/usr/local/apache-tomcat-9.0.8/logs --privileged=true zzyytomcat9
```



![img](file:///D:/Documents/My Knowledge/temp/1216a45d-441f-466c-be67-ab70776f9e7b/128/index_files/4d77e4ea-a8c6-4771-b89f-b80cd3318a6c.png)

 

```shell
Docker挂载主机目录Docker访问出现cannot open directory .: Permission denied
解决办法：在挂载目录后多加一个--privileged=true参数即可
```

#### ⑦ 验证

http://localhost:9080 

![img](file:///D:/Documents/My Knowledge/temp/1216a45d-441f-466c-be67-ab70776f9e7b/128/index_files/ba118847-c0b5-4af1-8bdb-869d71480391.jpg)

#### ⑧ 结合前述的容器卷将测试的web服务test发布

![img](file:///D:/Documents/My Knowledge/temp/1216a45d-441f-466c-be67-ab70776f9e7b/128/index_files/a765bb37-fb59-415e-9560-1a07afdb11bc.png)

web.xml：

```xml
<?xml version="1.0" encoding="UTF-8"?>
<web-app xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
  xmlns="http://java.sun.com/xml/ns/javaee"
  xsi:schemaLocation="http://java.sun.com/xml/ns/javaee http://java.sun.com/xml/ns/javaee/web-app_2_5.xsd"
  id="WebApp_ID" version="2.5">
  
  <display-name>test</display-name>
 
</web-app>
```



a.jsp：

```html
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Insert title here</title>
  </head>
  <body>
    -----------welcome------------
    <%="i am in docker tomcat self "%>
    <br>
    <br>
    <% System.out.println("=============docker tomcat self");%>
  </body>
</html>
```



测试：

重启当前的tomcat。

```shell
docker restart 容器id
```



![img](file:///D:/Documents/My Knowledge/temp/1216a45d-441f-466c-be67-ab70776f9e7b/128/index_files/c0cb2a10-4c55-4c7b-8070-25a332825767.png)

## 5、小总结

![img](file:///D:/Documents/My Knowledge/temp/1216a45d-441f-466c-be67-ab70776f9e7b/128/index_files/f2ae9fb9-6bdd-4959-b53a-f856f537a834.jpg)

# 七、Docker常用安装

## 1、总体步骤

![img](file:///D:/Documents/My Knowledge/temp/1216a45d-441f-466c-be67-ab70776f9e7b/128/index_files/7b4475b6-56eb-41ee-8737-4f21a0baeb7f.png)

## 2、安装tomcat

（1）docker hub上面查找tomcat镜像

```shell
docker search tomcat
```



（2）从docker hub上拉取tomcat镜像到本地

```shell
docker pull tomcat
```



（3）docker images查看是否有拉取到的tomcat

（4）使用tomcat镜像创建容器(也叫运行镜像)

```shell
docker run -it -p 8080:8080 tomcat

-p 主机端口:docker容器端口
-P 随机分配端口
i:交互
t:终端
```



## 3、安装mysql

（1）docker hub上面查找mysql镜像

![img](file:///D:/Documents/My Knowledge/temp/1216a45d-441f-466c-be67-ab70776f9e7b/128/index_files/8cea4786-9329-4f79-abb3-08b98e6caf53.png)

（2）从docker hub上(阿里云加速器)拉取mysql镜像到本地标签为5.6

![img](file:///D:/Documents/My Knowledge/temp/1216a45d-441f-466c-be67-ab70776f9e7b/128/index_files/dbdc814d-c0ab-4827-8924-17ed9f29beef.png)

（3）使用mysql5.6镜像创建容器(也叫运行镜像)

① 使用mysql镜像

```shell
 docker run -p 12345:3306 --name mysql -v /zzyyuse/mysql/conf:/etc/mysql/conf.d -v /zzyyuse/mysql/logs:/logs -v /zzyyuse/mysql/data:/var/lib/mysql -e MYSQL_ROOT_PASSWORD=123456 -d mysql:5.6 
 #命令说明：
 -p 12345:3306：将主机的12345端口映射到docker容器的3306端口。
 --name mysql：运行服务名字
 -v /zzyyuse/mysql/conf:/etc/mysql/conf.d ：将主机/zzyyuse/mysql录下的conf/my.cnf 挂载到容器的 /etc/mysql/conf.d
 -v /zzyyuse/mysql/logs:/logs：将主机/zzyyuse/mysql目录下的 logs 目录挂载到容器的 /logs。
 -v /zzyyuse/mysql/data:/var/lib/mysql ：将主机/zzyyuse/mysql目录下的data目录挂载到容器的 /var/lib/mysql 
 -e MYSQL_ROOT_PASSWORD=123456：初始化 root 用户的密码。
 -d mysql:5.6 : 后台程序运行mysql5.6
 
 docker exec -it MySQL运行成功后的容器ID     /bin/bash
```



② 外部Win10也来连接运行在dokcer上的mysql服务

![img](file:///D:/Documents/My Knowledge/temp/1216a45d-441f-466c-be67-ab70776f9e7b/128/index_files/b2a1b1a5-5ad8-4058-92cd-bcf9ae5d5aff.jpg)

③ 数据备份小测试(可以不做)

```shell
 docker exec myql服务容器ID sh -c ' exec mysqldump --all-databases -uroot -p"123456" ' > /zzyyuse/all-databases.sql
```



## **4、安装redis**

（1）从docker hub上(阿里云加速器)拉取redis镜像到本地标签为3.2

![img](file:///D:/Documents/My Knowledge/temp/1216a45d-441f-466c-be67-ab70776f9e7b/128/index_files/63cf3f7d-7454-4d15-8506-ae61f0796cb7.png)

（2）使用redis3.2镜像创建容器(也叫运行镜像)

① 使用镜像

```shell
docker run -p 6379:6379 -v /zzyyuse/myredis/data:/data -v /zzyyuse/myredis/conf/redis.conf:/usr/local/etc/redis/redis.conf  -d redis:3.2 redis-server /usr/local/etc/redis/redis.conf --appendonly yes
```



② 在主机/zzyyuse/myredis/conf/redis.conf目录下新建redis.conf文件

```shell
vim /zzyyuse/myredis/conf/redis.conf/redis.conf
```

```shell
# Redis configuration file example.
#
# Note that in order to read the configuration file, Redis must be
# started with the file path as first argument:
#
# ./redis-server /path/to/redis.conf
 
# Note on units: when memory size is needed, it is possible to specify
# it in the usual form of 1k 5GB 4M and so forth:
#
# 1k => 1000 bytes
# 1kb => 1024 bytes
# 1m => 1000000 bytes
# 1mb => 1024*1024 bytes
# 1g => 1000000000 bytes
# 1gb => 1024*1024*1024 bytes
#
# units are case insensitive so 1GB 1Gb 1gB are all the same.
################################## INCLUDES ###################################
 
# Include one or more other config files here.  This is useful if you
# have a standard template that goes to all Redis servers but also need
# to customize a few per-server settings.  Include files can include
# other files, so use this wisely.
#
# Notice option "include" won't be rewritten by command "CONFIG REWRITE"
# from admin or Redis Sentinel. Since Redis always uses the last processed
# line as value of a configuration directive, you'd better put includes
# at the beginning of this file to avoid overwriting config change at runtime.
#
# If instead you are interested in using includes to override configuration
# options, it is better to use include as the last line.
#
# include /path/to/local.conf
# include /path/to/other.conf
 
################################## NETWORK #####################################
 
# By default, if no "bind" configuration directive is specified, Redis listens
# for connections from all the network interfaces available on the server.
# It is possible to listen to just one or multiple selected interfaces using
# the "bind" configuration directive, followed by one or more IP addresses.
#
# Examples:
#
# bind 192.168.1.100 10.0.0.1
# bind 127.0.0.1 ::1
#
# ~~~ WARNING ~~~ If the computer running Redis is directly exposed to the
# internet, binding to all the interfaces is dangerous and will expose the
# instance to everybody on the internet. So by default we uncomment the
# following bind directive, that will force Redis to listen only into
# the IPv4 lookback interface address (this means Redis will be able to
# accept connections only from clients running into the same computer it
# is running).
#
# IF YOU ARE SURE YOU WANT YOUR INSTANCE TO LISTEN TO ALL THE INTERFACES
# JUST COMMENT THE FOLLOWING LINE.
# ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
#bind 127.0.0.1
 
# Protected mode is a layer of security protection, in order to avoid that
# Redis instances left open on the internet are accessed and exploited.
#
# When protected mode is on and if:
#
# 1) The server is not binding explicitly to a set of addresses using the
#    "bind" directive.
# 2) No password is configured.
#
# The server only accepts connections from clients connecting from the
# IPv4 and IPv6 loopback addresses 127.0.0.1 and ::1, and from Unix domain
# sockets.
#
# By default protected mode is enabled. You should disable it only if
# you are sure you want clients from other hosts to connect to Redis
# even if no authentication is configured, nor a specific set of interfaces
# are explicitly listed using the "bind" directive.
protected-mode yes
 
# Accept connections on the specified port, default is 6379 (IANA #815344).
# If port 0 is specified Redis will not listen on a TCP socket.
port 6379
 
# TCP listen() backlog.
#
# In high requests-per-second environments you need an high backlog in order
# to avoid slow clients connections issues. Note that the Linux kernel
# will silently truncate it to the value of /proc/sys/net/core/somaxconn so
# make sure to raise both the value of somaxconn and tcp_max_syn_backlog
# in order to get the desired effect.
tcp-backlog 511
 
# Unix socket.
#
# Specify the path for the Unix socket that will be used to listen for
# incoming connections. There is no default, so Redis will not listen
# on a unix socket when not specified.
#
# unixsocket /tmp/redis.sock
# unixsocketperm 700
 
# Close the connection after a client is idle for N seconds (0 to disable)
timeout 0
 
# TCP keepalive.
#
# If non-zero, use SO_KEEPALIVE to send TCP ACKs to clients in absence
# of communication. This is useful for two reasons:
#
# 1) Detect dead peers.
# 2) Take the connection alive from the point of view of network
#    equipment in the middle.
#
# On Linux, the specified value (in seconds) is the period used to send ACKs.
# Note that to close the connection the double of the time is needed.
# On other kernels the period depends on the kernel configuration.
#
# A reasonable value for this option is 300 seconds, which is the new
# Redis default starting with Redis 3.2.1.
tcp-keepalive 300
 
################################# GENERAL #####################################
 
# By default Redis does not run as a daemon. Use 'yes' if you need it.
# Note that Redis will write a pid file in /var/run/redis.pid when daemonized.
#daemonize no
 
# If you run Redis from upstart or systemd, Redis can interact with your
# supervision tree. Options:
#   supervised no      - no supervision interaction
#   supervised upstart - signal upstart by putting Redis into SIGSTOP mode
#   supervised systemd - signal systemd by writing READY=1 to $NOTIFY_SOCKET
#   supervised auto    - detect upstart or systemd method based on
#                        UPSTART_JOB or NOTIFY_SOCKET environment variables
# Note: these supervision methods only signal "process is ready."
#       They do not enable continuous liveness pings back to your supervisor.
supervised no
 
# If a pid file is specified, Redis writes it where specified at startup
# and removes it at exit.
#
# When the server runs non daemonized, no pid file is created if none is
# specified in the configuration. When the server is daemonized, the pid file
# is used even if not specified, defaulting to "/var/run/redis.pid".
#
# Creating a pid file is best effort: if Redis is not able to create it
# nothing bad happens, the server will start and run normally.
pidfile /var/run/redis_6379.pid
 
# Specify the server verbosity level.
# This can be one of:
# debug (a lot of information, useful for development/testing)
# verbose (many rarely useful info, but not a mess like the debug level)
# notice (moderately verbose, what you want in production probably)
# warning (only very important / critical messages are logged)
loglevel notice
 
# Specify the log file name. Also the empty string can be used to force
# Redis to log on the standard output. Note that if you use standard
# output for logging but daemonize, logs will be sent to /dev/null
logfile ""
 
# To enable logging to the system logger, just set 'syslog-enabled' to yes,
# and optionally update the other syslog parameters to suit your needs.
# syslog-enabled no
 
# Specify the syslog identity.
# syslog-ident redis
 
# Specify the syslog facility. Must be USER or between LOCAL0-LOCAL7.
# syslog-facility local0
 
# Set the number of databases. The default database is DB 0, you can select
# a different one on a per-connection basis using SELECT <dbid> where
# dbid is a number between 0 and 'databases'-1
databases 16
 
################################ SNAPSHOTTING  ################################
#
# Save the DB on disk:
#
#   save <seconds> <changes>
#
#   Will save the DB if both the given number of seconds and the given
#   number of write operations against the DB occurred.
#
#   In the example below the behaviour will be to save:
#   after 900 sec (15 min) if at least 1 key changed
#   after 300 sec (5 min) if at least 10 keys changed
#   after 60 sec if at least 10000 keys changed
#
#   Note: you can disable saving completely by commenting out all "save" lines.
#
#   It is also possible to remove all the previously configured save
#   points by adding a save directive with a single empty string argument
#   like in the following example:
#
#   save ""
 
save 120 1
save 300 10
save 60 10000
 
# By default Redis will stop accepting writes if RDB snapshots are enabled
# (at least one save point) and the latest background save failed.
# This will make the user aware (in a hard way) that data is not persisting
# on disk properly, otherwise chances are that no one will notice and some
# disaster will happen.
#
# If the background saving process will start working again Redis will
# automatically allow writes again.
#
# However if you have setup your proper monitoring of the Redis server
# and persistence, you may want to disable this feature so that Redis will
# continue to work as usual even if there are problems with disk,
# permissions, and so forth.
stop-writes-on-bgsave-error yes
 
# Compress string objects using LZF when dump .rdb databases?
# For default that's set to 'yes' as it's almost always a win.
# If you want to save some CPU in the saving child set it to 'no' but
# the dataset will likely be bigger if you have compressible values or keys.
rdbcompression yes
 
# Since version 5 of RDB a CRC64 checksum is placed at the end of the file.
# This makes the format more resistant to corruption but there is a performance
# hit to pay (around 10%) when saving and loading RDB files, so you can disable it
# for maximum performances.
#
# RDB files created with checksum disabled have a checksum of zero that will
# tell the loading code to skip the check.
rdbchecksum yes
 
# The filename where to dump the DB
dbfilename dump.rdb
 
# The working directory.
#
# The DB will be written inside this directory, with the filename specified
# above using the 'dbfilename' configuration directive.
#
# The Append Only File will also be created inside this directory.
#
# Note that you must specify a directory here, not a file name.
dir ./
 
################################# REPLICATION #################################
 
# Master-Slave replication. Use slaveof to make a Redis instance a copy of
# another Redis server. A few things to understand ASAP about Redis replication.
#
# 1) Redis replication is asynchronous, but you can configure a master to
#    stop accepting writes if it appears to be not connected with at least
#    a given number of slaves.
# 2) Redis slaves are able to perform a partial resynchronization with the
#    master if the replication link is lost for a relatively small amount of
#    time. You may want to configure the replication backlog size (see the next
#    sections of this file) with a sensible value depending on your needs.
# 3) Replication is automatic and does not need user intervention. After a
#    network partition slaves automatically try to reconnect to masters
#    and resynchronize with them.
#
# slaveof <masterip> <masterport>
 
# If the master is password protected (using the "requirepass" configuration
# directive below) it is possible to tell the slave to authenticate before
# starting the replication synchronization process, otherwise the master will
# refuse the slave request.
#
# masterauth <master-password>
 
# When a slave loses its connection with the master, or when the replication
# is still in progress, the slave can act in two different ways:
#
# 1) if slave-serve-stale-data is set to 'yes' (the default) the slave will
#    still reply to client requests, possibly with out of date data, or the
#    data set may just be empty if this is the first synchronization.
#
# 2) if slave-serve-stale-data is set to 'no' the slave will reply with
#    an error "SYNC with master in progress" to all the kind of commands
#    but to INFO and SLAVEOF.
#
slave-serve-stale-data yes
 
# You can configure a slave instance to accept writes or not. Writing against
# a slave instance may be useful to store some ephemeral data (because data
# written on a slave will be easily deleted after resync with the master) but
# may also cause problems if clients are writing to it because of a
# misconfiguration.
#
# Since Redis 2.6 by default slaves are read-only.
#
# Note: read only slaves are not designed to be exposed to untrusted clients
# on the internet. It's just a protection layer against misuse of the instance.
# Still a read only slave exports by default all the administrative commands
# such as CONFIG, DEBUG, and so forth. To a limited extent you can improve
# security of read only slaves using 'rename-command' to shadow all the
# administrative / dangerous commands.
slave-read-only yes
 
# Replication SYNC strategy: disk or socket.
#
# -------------------------------------------------------
# WARNING: DISKLESS REPLICATION IS EXPERIMENTAL CURRENTLY
# -------------------------------------------------------
#
# New slaves and reconnecting slaves that are not able to continue the replication
# process just receiving differences, need to do what is called a "full
# synchronization". An RDB file is transmitted from the master to the slaves.
# The transmission can happen in two different ways:
#
# 1) Disk-backed: The Redis master creates a new process that writes the RDB
#                 file on disk. Later the file is transferred by the parent
#                 process to the slaves incrementally.
# 2) Diskless: The Redis master creates a new process that directly writes the
#              RDB file to slave sockets, without touching the disk at all.
#
# With disk-backed replication, while the RDB file is generated, more slaves
# can be queued and served with the RDB file as soon as the current child producing
# the RDB file finishes its work. With diskless replication instead once
# the transfer starts, new slaves arriving will be queued and a new transfer
# will start when the current one terminates.
#
# When diskless replication is used, the master waits a configurable amount of
# time (in seconds) before starting the transfer in the hope that multiple slaves
# will arrive and the transfer can be parallelized.
#
# With slow disks and fast (large bandwidth) networks, diskless replication
# works better.
repl-diskless-sync no
 
# When diskless replication is enabled, it is possible to configure the delay
# the server waits in order to spawn the child that transfers the RDB via socket
# to the slaves.
#
# This is important since once the transfer starts, it is not possible to serve
# new slaves arriving, that will be queued for the next RDB transfer, so the server
# waits a delay in order to let more slaves arrive.
#
# The delay is specified in seconds, and by default is 5 seconds. To disable
# it entirely just set it to 0 seconds and the transfer will start ASAP.
repl-diskless-sync-delay 5
 
# Slaves send PINGs to server in a predefined interval. It's possible to change
# this interval with the repl_ping_slave_period option. The default value is 10
# seconds.
#
# repl-ping-slave-period 10
 
# The following option sets the replication timeout for:
#
# 1) Bulk transfer I/O during SYNC, from the point of view of slave.
# 2) Master timeout from the point of view of slaves (data, pings).
# 3) Slave timeout from the point of view of masters (REPLCONF ACK pings).
#
# It is important to make sure that this value is greater than the value
# specified for repl-ping-slave-period otherwise a timeout will be detected
# every time there is low traffic between the master and the slave.
#
# repl-timeout 60
 
# Disable TCP_NODELAY on the slave socket after SYNC?
#
# If you select "yes" Redis will use a smaller number of TCP packets and
# less bandwidth to send data to slaves. But this can add a delay for
# the data to appear on the slave side, up to 40 milliseconds with
# Linux kernels using a default configuration.
#
# If you select "no" the delay for data to appear on the slave side will
# be reduced but more bandwidth will be used for replication.
#
# By default we optimize for low latency, but in very high traffic conditions
# or when the master and slaves are many hops away, turning this to "yes" may
# be a good idea.
repl-disable-tcp-nodelay no
 
# Set the replication backlog size. The backlog is a buffer that accumulates
# slave data when slaves are disconnected for some time, so that when a slave
# wants to reconnect again, often a full resync is not needed, but a partial
# resync is enough, just passing the portion of data the slave missed while
# disconnected.
#
# The bigger the replication backlog, the longer the time the slave can be
# disconnected and later be able to perform a partial resynchronization.
#
# The backlog is only allocated once there is at least a slave connected.
#
# repl-backlog-size 1mb
 
# After a master has no longer connected slaves for some time, the backlog
# will be freed. The following option configures the amount of seconds that
# need to elapse, starting from the time the last slave disconnected, for
# the backlog buffer to be freed.
#
# A value of 0 means to never release the backlog.
#
# repl-backlog-ttl 3600
 
# The slave priority is an integer number published by Redis in the INFO output.
# It is used by Redis Sentinel in order to select a slave to promote into a
# master if the master is no longer working correctly.
#
# A slave with a low priority number is considered better for promotion, so
# for instance if there are three slaves with priority 10, 100, 25 Sentinel will
# pick the one with priority 10, that is the lowest.
#
# However a special priority of 0 marks the slave as not able to perform the
# role of master, so a slave with priority of 0 will never be selected by
# Redis Sentinel for promotion.
#
# By default the priority is 100.
slave-priority 100
 
# It is possible for a master to stop accepting writes if there are less than
# N slaves connected, having a lag less or equal than M seconds.
#
# The N slaves need to be in "online" state.
#
# The lag in seconds, that must be <= the specified value, is calculated from
# the last ping received from the slave, that is usually sent every second.
#
# This option does not GUARANTEE that N replicas will accept the write, but
# will limit the window of exposure for lost writes in case not enough slaves
# are available, to the specified number of seconds.
#
# For example to require at least 3 slaves with a lag <= 10 seconds use:
#
# min-slaves-to-write 3
# min-slaves-max-lag 10
#
# Setting one or the other to 0 disables the feature.
#
# By default min-slaves-to-write is set to 0 (feature disabled) and
# min-slaves-max-lag is set to 10.
 
# A Redis master is able to list the address and port of the attached
# slaves in different ways. For example the "INFO replication" section
# offers this information, which is used, among other tools, by
# Redis Sentinel in order to discover slave instances.
# Another place where this info is available is in the output of the
# "ROLE" command of a masteer.
#
# The listed IP and address normally reported by a slave is obtained
# in the following way:
#
#   IP: The address is auto detected by checking the peer address
#   of the socket used by the slave to connect with the master.
#
#   Port: The port is communicated by the slave during the replication
#   handshake, and is normally the port that the slave is using to
#   list for connections.
#
# However when port forwarding or Network Address Translation (NAT) is
# used, the slave may be actually reachable via different IP and port
# pairs. The following two options can be used by a slave in order to
# report to its master a specific set of IP and port, so that both INFO
# and ROLE will report those values.
#
# There is no need to use both the options if you need to override just
# the port or the IP address.
#
# slave-announce-ip 5.5.5.5
# slave-announce-port 1234
 
################################## SECURITY ###################################
 
# Require clients to issue AUTH <PASSWORD> before processing any other
# commands.  This might be useful in environments in which you do not trust
# others with access to the host running redis-server.
#
# This should stay commented out for backward compatibility and because most
# people do not need auth (e.g. they run their own servers).
#
# Warning: since Redis is pretty fast an outside user can try up to
# 150k passwords per second against a good box. This means that you should
# use a very strong password otherwise it will be very easy to break.
#
# requirepass foobared
 
# Command renaming.
#
# It is possible to change the name of dangerous commands in a shared
# environment. For instance the CONFIG command may be renamed into something
# hard to guess so that it will still be available for internal-use tools
# but not available for general clients.
#
# Example:
#
# rename-command CONFIG b840fc02d524045429941cc15f59e41cb7be6c52
#
# It is also possible to completely kill a command by renaming it into
# an empty string:
#
# rename-command CONFIG ""
#
# Please note that changing the name of commands that are logged into the
# AOF file or transmitted to slaves may cause problems.
 
################################### LIMITS ####################################
 
# Set the max number of connected clients at the same time. By default
# this limit is set to 10000 clients, however if the Redis server is not
# able to configure the process file limit to allow for the specified limit
# the max number of allowed clients is set to the current file limit
# minus 32 (as Redis reserves a few file descriptors for internal uses).
#
# Once the limit is reached Redis will close all the new connections sending
# an error 'max number of clients reached'.
#
# maxclients 10000
 
# Don't use more memory than the specified amount of bytes.
# When the memory limit is reached Redis will try to remove keys
# according to the eviction policy selected (see maxmemory-policy).
#
# If Redis can't remove keys according to the policy, or if the policy is
# set to 'noeviction', Redis will start to reply with errors to commands
# that would use more memory, like SET, LPUSH, and so on, and will continue
# to reply to read-only commands like GET.
#
# This option is usually useful when using Redis as an LRU cache, or to set
# a hard memory limit for an instance (using the 'noeviction' policy).
#
# WARNING: If you have slaves attached to an instance with maxmemory on,
# the size of the output buffers needed to feed the slaves are subtracted
# from the used memory count, so that network problems / resyncs will
# not trigger a loop where keys are evicted, and in turn the output
# buffer of slaves is full with DELs of keys evicted triggering the deletion
# of more keys, and so forth until the database is completely emptied.
#
# In short... if you have slaves attached it is suggested that you set a lower
# limit for maxmemory so that there is some free RAM on the system for slave
# output buffers (but this is not needed if the policy is 'noeviction').
#
# maxmemory <bytes>
 
# MAXMEMORY POLICY: how Redis will select what to remove when maxmemory
# is reached. You can select among five behaviors:
#
# volatile-lru -> remove the key with an expire set using an LRU algorithm
# allkeys-lru -> remove any key according to the LRU algorithm
# volatile-random -> remove a random key with an expire set
# allkeys-random -> remove a random key, any key
# volatile-ttl -> remove the key with the nearest expire time (minor TTL)
# noeviction -> don't expire at all, just return an error on write operations
#
# Note: with any of the above policies, Redis will return an error on write
#       operations, when there are no suitable keys for eviction.
#
#       At the date of writing these commands are: set setnx setex append
#       incr decr rpush lpush rpushx lpushx linsert lset rpoplpush sadd
#       sinter sinterstore sunion sunionstore sdiff sdiffstore zadd zincrby
#       zunionstore zinterstore hset hsetnx hmset hincrby incrby decrby
#       getset mset msetnx exec sort
#
# The default is:
#
# maxmemory-policy noeviction
 
# LRU and minimal TTL algorithms are not precise algorithms but approximated
# algorithms (in order to save memory), so you can tune it for speed or
# accuracy. For default Redis will check five keys and pick the one that was
# used less recently, you can change the sample size using the following
# configuration directive.
#
# The default of 5 produces good enough results. 10 Approximates very closely
# true LRU but costs a bit more CPU. 3 is very fast but not very accurate.
#
# maxmemory-samples 5
 
############################## APPEND ONLY MODE ###############################
 
# By default Redis asynchronously dumps the dataset on disk. This mode is
# good enough in many applications, but an issue with the Redis process or
# a power outage may result into a few minutes of writes lost (depending on
# the configured save points).
#
# The Append Only File is an alternative persistence mode that provides
# much better durability. For instance using the default data fsync policy
# (see later in the config file) Redis can lose just one second of writes in a
# dramatic event like a server power outage, or a single write if something
# wrong with the Redis process itself happens, but the operating system is
# still running correctly.
#
# AOF and RDB persistence can be enabled at the same time without problems.
# If the AOF is enabled on startup Redis will load the AOF, that is the file
# with the better durability guarantees.
#
# Please check http://redis.io/topics/persistence for more information.
 
appendonly no
 
# The name of the append only file (default: "appendonly.aof")
 
appendfilename "appendonly.aof"
 
# The fsync() call tells the Operating System to actually write data on disk
# instead of waiting for more data in the output buffer. Some OS will really flush
# data on disk, some other OS will just try to do it ASAP.
#
# Redis supports three different modes:
#
# no: don't fsync, just let the OS flush the data when it wants. Faster.
# always: fsync after every write to the append only log. Slow, Safest.
# everysec: fsync only one time every second. Compromise.
#
# The default is "everysec", as that's usually the right compromise between
# speed and data safety. It's up to you to understand if you can relax this to
# "no" that will let the operating system flush the output buffer when
# it wants, for better performances (but if you can live with the idea of
# some data loss consider the default persistence mode that's snapshotting),
# or on the contrary, use "always" that's very slow but a bit safer than
# everysec.
#
# More details please check the following article:
# http://antirez.com/post/redis-persistence-demystified.html
#
# If unsure, use "everysec".
 
# appendfsync always
appendfsync everysec
# appendfsync no
 
# When the AOF fsync policy is set to always or everysec, and a background
# saving process (a background save or AOF log background rewriting) is
# performing a lot of I/O against the disk, in some Linux configurations
# Redis may block too long on the fsync() call. Note that there is no fix for
# this currently, as even performing fsync in a different thread will block
# our synchronous write(2) call.
#
# In order to mitigate this problem it's possible to use the following option
# that will prevent fsync() from being called in the main process while a
# BGSAVE or BGREWRITEAOF is in progress.
#
# This means that while another child is saving, the durability of Redis is
# the same as "appendfsync none". In practical terms, this means that it is
# possible to lose up to 30 seconds of log in the worst scenario (with the
# default Linux settings).
#
# If you have latency problems turn this to "yes". Otherwise leave it as
# "no" that is the safest pick from the point of view of durability.
 
no-appendfsync-on-rewrite no
 
# Automatic rewrite of the append only file.
# Redis is able to automatically rewrite the log file implicitly calling
# BGREWRITEAOF when the AOF log size grows by the specified percentage.
#
# This is how it works: Redis remembers the size of the AOF file after the
# latest rewrite (if no rewrite has happened since the restart, the size of
# the AOF at startup is used).
#
# This base size is compared to the current size. If the current size is
# bigger than the specified percentage, the rewrite is triggered. Also
# you need to specify a minimal size for the AOF file to be rewritten, this
# is useful to avoid rewriting the AOF file even if the percentage increase
# is reached but it is still pretty small.
#
# Specify a percentage of zero in order to disable the automatic AOF
# rewrite feature.
 
auto-aof-rewrite-percentage 100
auto-aof-rewrite-min-size 64mb
 
# An AOF file may be found to be truncated at the end during the Redis
# startup process, when the AOF data gets loaded back into memory.
# This may happen when the system where Redis is running
# crashes, especially when an ext4 filesystem is mounted without the
# data=ordered option (however this can't happen when Redis itself
# crashes or aborts but the operating system still works correctly).
#
# Redis can either exit with an error when this happens, or load as much
# data as possible (the default now) and start if the AOF file is found
# to be truncated at the end. The following option controls this behavior.
#
# If aof-load-truncated is set to yes, a truncated AOF file is loaded and
# the Redis server starts emitting a log to inform the user of the event.
# Otherwise if the option is set to no, the server aborts with an error
# and refuses to start. When the option is set to no, the user requires
# to fix the AOF file using the "redis-check-aof" utility before to restart
# the server.
#
# Note that if the AOF file will be found to be corrupted in the middle
# the server will still exit with an error. This option only applies when
# Redis will try to read more data from the AOF file but not enough bytes
# will be found.
aof-load-truncated yes
 
################################ LUA SCRIPTING  ###############################
 
# Max execution time of a Lua script in milliseconds.
#
# If the maximum execution time is reached Redis will log that a script is
# still in execution after the maximum allowed time and will start to
# reply to queries with an error.
#
# When a long running script exceeds the maximum execution time only the
# SCRIPT KILL and SHUTDOWN NOSAVE commands are available. The first can be
# used to stop a script that did not yet called write commands. The second
# is the only way to shut down the server in the case a write command was
# already issued by the script but the user doesn't want to wait for the natural
# termination of the script.
#
# Set it to 0 or a negative value for unlimited execution without warnings.
lua-time-limit 5000
 
################################ REDIS CLUSTER  ###############################
#
# ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
# WARNING EXPERIMENTAL: Redis Cluster is considered to be stable code, however
# in order to mark it as "mature" we need to wait for a non trivial percentage
# of users to deploy it in production.
# ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
#
# Normal Redis instances can't be part of a Redis Cluster; only nodes that are
# started as cluster nodes can. In order to start a Redis instance as a
# cluster node enable the cluster support uncommenting the following:
#
# cluster-enabled yes
 
# Every cluster node has a cluster configuration file. This file is not
# intended to be edited by hand. It is created and updated by Redis nodes.
# Every Redis Cluster node requires a different cluster configuration file.
# Make sure that instances running in the same system do not have
# overlapping cluster configuration file names.
#
# cluster-config-file nodes-6379.conf
 
# Cluster node timeout is the amount of milliseconds a node must be unreachable
# for it to be considered in failure state.
# Most other internal time limits are multiple of the node timeout.
#
# cluster-node-timeout 15000
 
# A slave of a failing master will avoid to start a failover if its data
# looks too old.
#
# There is no simple way for a slave to actually have a exact measure of
# its "data age", so the following two checks are performed:
#
# 1) If there are multiple slaves able to failover, they exchange messages
#    in order to try to give an advantage to the slave with the best
#    replication offset (more data from the master processed).
#    Slaves will try to get their rank by offset, and apply to the start
#    of the failover a delay proportional to their rank.
#
# 2) Every single slave computes the time of the last interaction with
#    its master. This can be the last ping or command received (if the master
#    is still in the "connected" state), or the time that elapsed since the
#    disconnection with the master (if the replication link is currently down).
#    If the last interaction is too old, the slave will not try to failover
#    at all.
#
# The point "2" can be tuned by user. Specifically a slave will not perform
# the failover if, since the last interaction with the master, the time
# elapsed is greater than:
#
#   (node-timeout * slave-validity-factor) + repl-ping-slave-period
#
# So for example if node-timeout is 30 seconds, and the slave-validity-factor
# is 10, and assuming a default repl-ping-slave-period of 10 seconds, the
# slave will not try to failover if it was not able to talk with the master
# for longer than 310 seconds.
#
# A large slave-validity-factor may allow slaves with too old data to failover
# a master, while a too small value may prevent the cluster from being able to
# elect a slave at all.
#
# For maximum availability, it is possible to set the slave-validity-factor
# to a value of 0, which means, that slaves will always try to failover the
# master regardless of the last time they interacted with the master.
# (However they'll always try to apply a delay proportional to their
# offset rank).
#
# Zero is the only value able to guarantee that when all the partitions heal
# the cluster will always be able to continue.
#
# cluster-slave-validity-factor 10
 
# Cluster slaves are able to migrate to orphaned masters, that are masters
# that are left without working slaves. This improves the cluster ability
# to resist to failures as otherwise an orphaned master can't be failed over
# in case of failure if it has no working slaves.
#
# Slaves migrate to orphaned masters only if there are still at least a
# given number of other working slaves for their old master. This number
# is the "migration barrier". A migration barrier of 1 means that a slave
# will migrate only if there is at least 1 other working slave for its master
# and so forth. It usually reflects the number of slaves you want for every
# master in your cluster.
#
# Default is 1 (slaves migrate only if their masters remain with at least
# one slave). To disable migration just set it to a very large value.
# A value of 0 can be set but is useful only for debugging and dangerous
# in production.
#
# cluster-migration-barrier 1
 
# By default Redis Cluster nodes stop accepting queries if they detect there
# is at least an hash slot uncovered (no available node is serving it).
# This way if the cluster is partially down (for example a range of hash slots
# are no longer covered) all the cluster becomes, eventually, unavailable.
# It automatically returns available as soon as all the slots are covered again.
#
# However sometimes you want the subset of the cluster which is working,
# to continue to accept queries for the part of the key space that is still
# covered. In order to do so, just set the cluster-require-full-coverage
# option to no.
#
# cluster-require-full-coverage yes
 
# In order to setup your cluster make sure to read the documentation
# available at http://redis.io web site.
 
################################## SLOW LOG ###################################
 
# The Redis Slow Log is a system to log queries that exceeded a specified
# execution time. The execution time does not include the I/O operations
# like talking with the client, sending the reply and so forth,
# but just the time needed to actually execute the command (this is the only
# stage of command execution where the thread is blocked and can not serve
# other requests in the meantime).
#
# You can configure the slow log with two parameters: one tells Redis
# what is the execution time, in microseconds, to exceed in order for the
# command to get logged, and the other parameter is the length of the
# slow log. When a new command is logged the oldest one is removed from the
# queue of logged commands.
 
# The following time is expressed in microseconds, so 1000000 is equivalent
# to one second. Note that a negative number disables the slow log, while
# a value of zero forces the logging of every command.
slowlog-log-slower-than 10000
 
# There is no limit to this length. Just be aware that it will consume memory.
# You can reclaim memory used by the slow log with SLOWLOG RESET.
slowlog-max-len 128
 
################################ LATENCY MONITOR ##############################
 
# The Redis latency monitoring subsystem samples different operations
# at runtime in order to collect data related to possible sources of
# latency of a Redis instance.
#
# Via the LATENCY command this information is available to the user that can
# print graphs and obtain reports.
#
# The system only logs operations that were performed in a time equal or
# greater than the amount of milliseconds specified via the
# latency-monitor-threshold configuration directive. When its value is set
# to zero, the latency monitor is turned off.
#
# By default latency monitoring is disabled since it is mostly not needed
# if you don't have latency issues, and collecting data has a performance
# impact, that while very small, can be measured under big load. Latency
# monitoring can easily be enabled at runtime using the command
# "CONFIG SET latency-monitor-threshold <milliseconds>" if needed.
latency-monitor-threshold 0
 
############################# EVENT NOTIFICATION ##############################
 
# Redis can notify Pub/Sub clients about events happening in the key space.
# This feature is documented at http://redis.io/topics/notifications
#
# For instance if keyspace events notification is enabled, and a client
# performs a DEL operation on key "foo" stored in the Database 0, two
# messages will be published via Pub/Sub:
#
# PUBLISH __keyspace@0__:foo del
# PUBLISH __keyevent@0__:del foo
#
# It is possible to select the events that Redis will notify among a set
# of classes. Every class is identified by a single character:
#
#  K     Keyspace events, published with __keyspace@<db>__ prefix.
#  E     Keyevent events, published with __keyevent@<db>__ prefix.
#  g     Generic commands (non-type specific) like DEL, EXPIRE, RENAME, ...
#  $     String commands
#  l     List commands
#  s     Set commands
#  h     Hash commands
#  z     Sorted set commands
#  x     Expired events (events generated every time a key expires)
#  e     Evicted events (events generated when a key is evicted for maxmemory)
#  A     Alias for g$lshzxe, so that the "AKE" string means all the events.
#
#  The "notify-keyspace-events" takes as argument a string that is composed
#  of zero or multiple characters. The empty string means that notifications
#  are disabled.
#
#  Example: to enable list and generic events, from the point of view of the
#           event name, use:
#
#  notify-keyspace-events Elg
#
#  Example 2: to get the stream of the expired keys subscribing to channel
#             name __keyevent@0__:expired use:
#
#  notify-keyspace-events Ex
#
#  By default all notifications are disabled because most users don't need
#  this feature and the feature has some overhead. Note that if you don't
#  specify at least one of K or E, no events will be delivered.
notify-keyspace-events ""
 
############################### ADVANCED CONFIG ###############################
 
# Hashes are encoded using a memory efficient data structure when they have a
# small number of entries, and the biggest entry does not exceed a given
# threshold. These thresholds can be configured using the following directives.
hash-max-ziplist-entries 512
hash-max-ziplist-value 64
 
# Lists are also encoded in a special way to save a lot of space.
# The number of entries allowed per internal list node can be specified
# as a fixed maximum size or a maximum number of elements.
# For a fixed maximum size, use -5 through -1, meaning:
# -5: max size: 64 Kb  <-- not recommended for normal workloads
# -4: max size: 32 Kb  <-- not recommended
# -3: max size: 16 Kb  <-- probably not recommended
# -2: max size: 8 Kb   <-- good
# -1: max size: 4 Kb   <-- good
# Positive numbers mean store up to _exactly_ that number of elements
# per list node.
# The highest performing option is usually -2 (8 Kb size) or -1 (4 Kb size),
# but if your use case is unique, adjust the settings as necessary.
list-max-ziplist-size -2
 
# Lists may also be compressed.
# Compress depth is the number of quicklist ziplist nodes from *each* side of
# the list to *exclude* from compression.  The head and tail of the list
# are always uncompressed for fast push/pop operations.  Settings are:
# 0: disable all list compression
# 1: depth 1 means "don't start compressing until after 1 node into the list,
#    going from either the head or tail"
#    So: [head]->node->node->...->node->[tail]
#    [head], [tail] will always be uncompressed; inner nodes will compress.
# 2: [head]->[next]->node->node->...->node->[prev]->[tail]
#    2 here means: don't compress head or head->next or tail->prev or tail,
#    but compress all nodes between them.
# 3: [head]->[next]->[next]->node->node->...->node->[prev]->[prev]->[tail]
# etc.
list-compress-depth 0
 
# Sets have a special encoding in just one case: when a set is composed
# of just strings that happen to be integers in radix 10 in the range
# of 64 bit signed integers.
# The following configuration setting sets the limit in the size of the
# set in order to use this special memory saving encoding.
set-max-intset-entries 512
 
# Similarly to hashes and lists, sorted sets are also specially encoded in
# order to save a lot of space. This encoding is only used when the length and
# elements of a sorted set are below the following limits:
zset-max-ziplist-entries 128
zset-max-ziplist-value 64
 
# HyperLogLog sparse representation bytes limit. The limit includes the
# 16 bytes header. When an HyperLogLog using the sparse representation crosses
# this limit, it is converted into the dense representation.
#
# A value greater than 16000 is totally useless, since at that point the
# dense representation is more memory efficient.
#
# The suggested value is ~ 3000 in order to have the benefits of
# the space efficient encoding without slowing down too much PFADD,
# which is O(N) with the sparse encoding. The value can be raised to
# ~ 10000 when CPU is not a concern, but space is, and the data set is
# composed of many HyperLogLogs with cardinality in the 0 - 15000 range.
hll-sparse-max-bytes 3000
 
# Active rehashing uses 1 millisecond every 100 milliseconds of CPU time in
# order to help rehashing the main Redis hash table (the one mapping top-level
# keys to values). The hash table implementation Redis uses (see dict.c)
# performs a lazy rehashing: the more operation you run into a hash table
# that is rehashing, the more rehashing "steps" are performed, so if the
# server is idle the rehashing is never complete and some more memory is used
# by the hash table.
#
# The default is to use this millisecond 10 times every second in order to
# actively rehash the main dictionaries, freeing memory when possible.
#
# If unsure:
# use "activerehashing no" if you have hard latency requirements and it is
# not a good thing in your environment that Redis can reply from time to time
# to queries with 2 milliseconds delay.
#
# use "activerehashing yes" if you don't have such hard requirements but
# want to free memory asap when possible.
activerehashing yes
 
# The client output buffer limits can be used to force disconnection of clients
# that are not reading data from the server fast enough for some reason (a
# common reason is that a Pub/Sub client can't consume messages as fast as the
# publisher can produce them).
#
# The limit can be set differently for the three different classes of clients:
#
# normal -> normal clients including MONITOR clients
# slave  -> slave clients
# pubsub -> clients subscribed to at least one pubsub channel or pattern
#
# The syntax of every client-output-buffer-limit directive is the following:
#
# client-output-buffer-limit <class> <hard limit> <soft limit> <soft seconds>
#
# A client is immediately disconnected once the hard limit is reached, or if
# the soft limit is reached and remains reached for the specified number of
# seconds (continuously).
# So for instance if the hard limit is 32 megabytes and the soft limit is
# 16 megabytes / 10 seconds, the client will get disconnected immediately
# if the size of the output buffers reach 32 megabytes, but will also get
# disconnected if the client reaches 16 megabytes and continuously overcomes
# the limit for 10 seconds.
#
# By default normal clients are not limited because they don't receive data
# without asking (in a push way), but just after a request, so only
# asynchronous clients may create a scenario where data is requested faster
# than it can read.
#
# Instead there is a default limit for pubsub and slave clients, since
# subscribers and slaves receive data in a push fashion.
#
# Both the hard or the soft limit can be disabled by setting them to zero.
client-output-buffer-limit normal 0 0 0
client-output-buffer-limit slave 256mb 64mb 60
client-output-buffer-limit pubsub 32mb 8mb 60
 
# Redis calls an internal function to perform many background tasks, like
# closing connections of clients in timeout, purging expired keys that are
# never requested, and so forth.
#
# Not all tasks are performed with the same frequency, but Redis checks for
# tasks to perform according to the specified "hz" value.
#
# By default "hz" is set to 10. Raising the value will use more CPU when
# Redis is idle, but at the same time will make Redis more responsive when
# there are many keys expiring at the same time, and timeouts may be
# handled with more precision.
#
# The range is between 1 and 500, however a value over 100 is usually not
# a good idea. Most users should use the default of 10 and raise this up to
# 100 only in environments where very low latency is required.
hz 10
 
# When a child rewrites the AOF file, if the following option is enabled
# the file will be fsync-ed every 32 MB of data generated. This is useful
# in order to commit the file to the disk more incrementally and avoid
# big latency spikes.
aof-rewrite-incremental-fsync yes
```



③ 测试redis-cli连接上来

```shell
 docker exec -it 运行着Rediis服务的容器ID redis-cli
```



![img](file:///D:/Documents/My Knowledge/temp/1216a45d-441f-466c-be67-ab70776f9e7b/128/index_files/cfef107d-8370-4b80-a67f-358bae039820.png)

④ 测试持久化文件生成

![img](file:///D:/Documents/My Knowledge/temp/1216a45d-441f-466c-be67-ab70776f9e7b/128/index_files/e01cd346-6c96-46d6-9be2-222284b0fdce.png)

# 八、本地镜像发布到阿里云

## 1、本地镜像发布到阿里云流程

![img](file:///D:/Documents/My Knowledge/temp/1216a45d-441f-466c-be67-ab70776f9e7b/128/index_files/4f405bf1-b089-47fa-bfbf-78dbde838ed9.jpg)

## 2、镜像的生成方法

### （1）前面的DockerFile

### （2）从容器创建一个新的镜像

```shell
docker commit [OPTIONS] 容器ID [REPOSITORY[:TAG]]

OPTIONS说明：
-a :提交的镜像作者；
-m :提交时的说明文字；
```



![img](file:///D:/Documents/My Knowledge/temp/1216a45d-441f-466c-be67-ab70776f9e7b/128/index_files/851aac10-0680-4332-b052-cc347302a6f2.png)

## 3、将本地镜像推送到阿里云

### （1）本地镜像素材原型

![img](file:///D:/Documents/My Knowledge/temp/1216a45d-441f-466c-be67-ab70776f9e7b/128/index_files/aec485c3-df6b-440a-b80b-afd40e3a1e75.png)

### （2）阿里云开发者平台

https://dev.aliyun.com/search.html 

![img](file:///D:/Documents/My Knowledge/temp/1216a45d-441f-466c-be67-ab70776f9e7b/128/index_files/7fae071e-d649-4565-82b2-ce5bee50e5a3.png)

### （3）创建仓库镜像

```shell
命名空间
仓库名称
```

![img](file:///D:/Documents/My Knowledge/temp/1216a45d-441f-466c-be67-ab70776f9e7b/128/index_files/6e24696d-3d07-424d-b958-7f6086e2b748.png)

![img](file:///D:/Documents/My Knowledge/temp/1216a45d-441f-466c-be67-ab70776f9e7b/128/index_files/0596ec1b-2ede-4b67-8183-4bb3e5f5309b.png)

### （4）将镜像推送到registry

https://cr.console.aliyun.com/repository/cn-hangzhou/wlcentos/mycentos/details

```shell
sudo docker login --username= registry.cn-hangzhou.aliyuncs.com
sudo docker tag [ImagesId] registry.cn-hangzhou.aliyuncs.com/wlcentos/mycentos:[镜像版本号]
sudo docker push registry.cn-hangzhou.aliyuncs.com/wlcentos/mycentos:[镜像版本号]
```

![img](file:///D:/Documents/My Knowledge/temp/1216a45d-441f-466c-be67-ab70776f9e7b/128/index_files/a4d27195-5935-45e3-9e6e-8f5ee6fd5802.png)

### （5）公有云可以查询到

![img](file:///D:/Documents/My Knowledge/temp/1216a45d-441f-466c-be67-ab70776f9e7b/128/index_files/1275e7ad-e5ee-44e6-a1e8-0dd394d47cc9.png)

### （6）查看详情

![img](file:///D:/Documents/My Knowledge/temp/1216a45d-441f-466c-be67-ab70776f9e7b/128/index_files/b01bc622-dfdf-4a71-8376-0c719957b989.png)

## 4、将阿里云上的镜像下载到本地

![img](file:///D:/Documents/My Knowledge/temp/1216a45d-441f-466c-be67-ab70776f9e7b/128/index_files/430c1f1d-4ded-415c-b81a-21a6b2b0a94e.png)

 

```
docker pull registry.cn-hangzhou.aliyuncs.com/wlcentos/mycentos:1.4.1
```



# 九、Centos7暴露的docker服务端口号 2375

1、修改宿主机的docker配置，让其docker服务可以远程访问, 暴露的docker服务端口号 2375

```shell
vim /lib/systemd/system/docker.service
```

在 ExecStart= 后添加加配置

```shell
-H tcp://0.0.0.0:2375 -H unix:///var/run/docker.sock
```

0.0.0.0 代表所有 ip ，也可指定 ip。修改后如下：

![img](file:///D:/Documents/My%20Knowledge/temp/1216a45d-441f-466c-be67-ab70776f9e7b/128/index_files/5b14feaa-3786-4c0e-ac0f-0fee1f9680d7.png)

2、刷新配置，重启服务

```shell
systemctl daemon-reload
systemctl restart docker
```

3、验证是否生效，访问：http://IP:2375/version , 响应如下内容则成功：

![img](file:///D:/Documents/My%20Knowledge/temp/1216a45d-441f-466c-be67-ab70776f9e7b/128/index_files/22fb6e76-6c56-4cec-9ff7-11290f087a56.png)

如果访问不到，则查看防火墙是否关闭

```shell
查看状态： systemctl status firewalld
关闭：  systemctl stop firewalld
开机禁用： systemctl disable firewalld
```