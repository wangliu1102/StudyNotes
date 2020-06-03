# 一、安装nginx依赖的软件

nginx是C写的，需要用GCC编译；nginx中的rewrite module需要PCRE；nginx中的gzip module需要zlib；nginx中的HTTP SSL module需要OpenSSL。

## 1.1 gcc安装

因为后面安装需要编译，所以事先得先安装gcc编译。阿里云主机已经默认安装了 gcc，如果是自己安装的虚拟机，那么需要先安装一下 gcc：

### **1.1.1 yum安装**

```shell
yum -y install gcc gcc-c++ autoconf automake make

# 升级gcc为9.3
# 查看gcc版本是否在5.3以上，centos7.6默认安装4.8.5
gcc -v
# 升级gcc到5.3及以上,如下：
升级到gcc 9.3：
yum -y install centos-release-scl
yum -y install devtoolset-9-gcc devtoolset-9-gcc-c++ devtoolset-9-binutils
scl enable devtoolset-9 bash

#需要注意的是scl命令启用只是临时的，退出shell或重启就会恢复原系统gcc版本。
#如果要长期使用gcc 9.3的话：
echo "source /opt/rh/devtoolset-9/enable" >>/etc/profile
#这样退出shell重新打开就是新版的gcc了

```



### 1.1.2 rpm安装

gcc是linux下的一个编译程序，是C程序的编译工具。

GCC(GNU Compiler Collection) 是 GNU(GNU's Not Unix) 计划提供的编译器家族，它能够支持 C, C++, Objective-C, Fortran, Java 和 Ada 等等程序设计语言前端，同时能够运行在 x86, x86-64, IA-64, PowerPC, SPARC 和 Alpha 等等几乎目前所有的硬件平台上。鉴于这些特征，以及 GCC 编译代码的高效性，使得 GCC 成为绝大多数自由软件开发编译的首选工具。虽然对于程序员们来说，编译器只是一个工具，除了开发和维护人员，很少有人关注编译器的发展，但是 GCC 的影响力是如此之大，它的性能提升甚至有望改善所有的自由软件的运行效率，同时它的内部结构的变化也体现出现代编译器发展的新特征。

下载依赖包：

官网：https://pkgs.org/

百度云下载（已下载好所有依赖包）：

> >   链接：https://pan.baidu.com/s/1B_4et9GsH51LKWKTD5bitQ 
>
> >   提取码：82b4

使用WinSCP上传到服务器后，运行如下命令：

```shell
rpm -Uvh *.rpm --nodeps --force
```

安装好，查询下：

```shell
gcc -v
```

![img](file:///D:/Documents/My Knowledge/temp/2dcab4c5-f249-4355-bb6d-4291b6e43dd7/128/index_files/8361ce73-f253-4609-bbb1-6816ca8f24d2.png)

离线升级gcc为9.3，编译安装章节中有在线升级。下载gcc升级包：

![img](file:///D:/Documents/My Knowledge/temp/2dcab4c5-f249-4355-bb6d-4291b6e43dd7/128/index_files/a6a66fbd-a390-4172-96dc-b0d77e0bfa5c.png)

解压gcc-9.3.0.tar.gz：

```shell
tar -zxvf gcc-9.3.0.tar.gz
```

将另外四个上传到解压目录gcc-9.3.0下。

进入gcc-9.3.0目录下：

```shell
cd gcc-9.3.0/
./contrib/download_prerequisites
```

若出现找不到wget或tar: bzip2：无法 exec: 没有那个文件或目录错误，需要安装wget和bzip2：

```shell
yum install -y wget
yum install -y bzip2

# 离线安装可自行百度

# 安装完成后再次命令：
./contrib/download_prerequisites
```



```shell
#创建预编译目录
mkdir  build  &&  cd build

#设置编译选项并编译
../configure --prefix=/usr/local/gcc-9.3.0 --enable-bootstrap --enable-checking=release --enable-languages=c,c++ --disable-multilib

#安装
#编译生成makefile文件
make
#安装GCC
make install

#安装后的设置
#设置环境变量
touch /etc/profile.d/gcc.sh
sudo chmod 777 /etc/profile.d/gcc.sh 
sudo echo -e '\nexport PATH=/usr/local/gcc-9.3.0/bin:$PATH\n' >> /etc/profile.d/gcc.sh && source /etc/profile.d/gcc.sh

#设置头文件
sudo ln -sv /usr/local/gcc/include/ /usr/include/gcc

#设置库文件
touch /etc/ld.so.conf.d/gcc.conf
sudo chmod 777 /etc/ld.so.conf.d/gcc.conf 
sudo echo -e "/usr/local/gcc/lib64" >> /etc/ld.so.conf.d/gcc.conf

#加载动态连接库
sudo ldconfig -v
ldconfig -p |grep gcc

#测试版本号
gcc -v
```



## 1.2 zlib安装

### 1.2.1 yum安装

```shell
yum -y install zlib zlib-devel
```

### 1.2.2 源码安装 

#### 1、下载

zlib官网：http://www.zlib.net/

下载zlib-1.2.11： http://prdownloads.sourceforge.net/libpng/zlib-1.2.11.tar.gz

百度云：

> > 链接：https://pan.baidu.com/s/1t_nqoz4UnsCKNzfM8T2sWA 
>
> > 提取码：mb1y

#### **2、解压并进入zlib代码根目录**

通过WinSCP将下载的压缩包上传到服务器。

```shell
tar -zxvf zlib-1.2.11.tar.gz

cd zlib-1.2.11
```

#### 3、配置、编译、安装

```shell
# 配置
./configure
# 编译
make
# 安装
make install

# 查看
whereis zlib
```

## 1.3 PCRE安装

### 1.3.1 yum安装 

```shell
yum -y install pcre-devel
```

### 1.3.2 源码安装

#### 1、下载

PCRE官网：http://www.pcre.org/

下载pcre-8.41：http://downloads.sourceforge.net/project/pcre/pcre/8.41/pcre-8.41.tar.gz

百度云：

> > 链接：https://pan.baidu.com/s/1t_nqoz4UnsCKNzfM8T2sWA 
>
> > 提取码：mb1y

#### 2、解压并进入PCRE代码根目录

通过WinSCP将下载的压缩包上传到服务器。

```shell
tar -zxvf pcre-8.41.tar.gz

cd pcre-8.41
```

#### 3、配置、编译、安装

```shell
# 配置
./configure
# 编译
make
# 安装
make install

# 查看
pcre-config --version
```

## 1.4 OpenSSL安装

### 1.4.1 yum安装

```shell
yum -y install openssl openssl-devel
```



### 1.4.2 源码安装

#### 1、下载

OpenSSL官网：https://www.openssl.org/

下载pcre-8.41：https://www.openssl.org/source/openssl-1.0.2n.tar.gz百度云：链接：https://pan.baidu.com/s/1t_nqoz4UnsCKNzfM8T2sWA 提取码：mb1y

#### 2、解压并进入openssl代码根目录

通过WinSCP将下载的压缩包上传到服务器。

```shell
tar -zxvf openssl-1.0.2n.tar.gz

cd openssl-1.0.2n
```

#### 3、配置、编译、安装

```shell
# 配置
./config
# 编译
make
# 安装
make install

# 查看版本
openssl version

whereis openssl
```

# **二、安装nginx**

## 2.1 下载

官网下载nginx-1.14.2：http://nginx.org/download/



百度云：

> > 链接：https://pan.baidu.com/s/1t_nqoz4UnsCKNzfM8T2sWA 
>
> > 提取码：mb1y

## **2.2 解压并进入nginx代码根目录**

通过WinSCP将下载的压缩包上传到服务器。

```shell
tar zxvf nginx-1.14.2.tar.gz

cd nginx-1.14.2
```



## 2.3 配置

 

```shell
# --with-http_stub_status_module 开启状态查询
# --with-http_ssl_module 开启ssl加密
# --with-pcre=../pcre-8.41 --with-zlib=../zlib-1.2.11 --with-openssl=../openssl-1.0.2n 指定pcre和zlib和openssl版本
./configure --with-http_stub_status_module --with-http_ssl_module --with-pcre=../pcre-8.41 --with-zlib=../zlib-1.2.11 --with-openssl=../openssl-1.0.2n
```



## 2.4 编译安装

```shell
make

make install
```



## 2.5 检查nginx.conf配置正确性

```shell
/usr/local/nginx/sbin/nginx -t
```

![img](file:///D:/Documents/My Knowledge/temp/2dcab4c5-f249-4355-bb6d-4291b6e43dd7/128/index_files/86cf86d1-8f47-440e-9555-dfc233d10fbc.png)

## 2.6 启动nginx

```shell
/usr/local/nginx/sbin/nginx
```

## ![img](file:///D:/Documents/My Knowledge/temp/2dcab4c5-f249-4355-bb6d-4291b6e43dd7/128/index_files/f47ef762-019e-42d7-bbe6-1925056de353.png)

## 2.7 nginx 常用命令

```shell
cd /usr/local/nginx/sbin
```

1、启动nginx，直接运行nginx的可执行文件

```shell
./nginx
```

2、停止nginx

```shell
./nginx -s stop
```

```shell
# 优雅关闭
./nginx -s quit 
```

3、重新加载配置文件：nginx -s reload 或者 kill -HUP 主进程号

```shell
./nginx -s reload
```

4、测试配置文件，检查配置文件语法是否正确，然后试图打开文件涉及的配置：nginx -t 配置文件

```shell
./nginx -t
```

5、查看nginx版本信息：

```shell
./nginx -v
```

6、查看nginx版本信息，编译版本，和配置参数

```shell
./nginx -V
```

7、重启日志文件，备份日志文件时常用：./nginx -s reopen 或者 kill -USR1 主进程号

```shell
./nginx -s reopen
```

## **2.8 nginx加入到开机自动启动**

1、开机自启

先编辑系统启动脚本vim /etc/rc.local 这个文件是系统启动后会自动执行的,我们就将启动命令加入到这个文件中

```shell
vim /etc/rc.local

/usr/local/nginx/sbin/nginx -c /usr/local/nginx/conf/nginx.conf & >/tmp/nginx.log 2>&1

chmod +x /etc/rc.d/rc.local
```

或者：

```shell
cd /lib/systemd/system/

vim nginx.service
[Unit]
Description=nginx service
After=network.target 
   
[Service] 
Type=forking 
ExecStart=/usr/local/nginx/sbin/nginx
ExecReload=/usr/local/nginx/sbin/nginx -s reload
ExecStop=/usr/local/nginx/sbin/nginx -s quit
PrivateTmp=true 
   
[Install] 
WantedBy=multi-user.target

# 保存退出
# 加载生效
systemctl daemon-reload

# 重启后生效，发现nginx自启，并且可以使用下面的命令了
```

[Unit]: 服务的说明

Description:描述服务
After:描述服务类别
[Service]服务运行参数的设置
Type=forking是后台运行的形式
ExecStart为服务的具体运行命令
ExecReload为重启命令
ExecStop为停止命令
PrivateTmp=True表示给服务分配独立的临时空间
注意：[Service]的启动、重启、停止命令全部要求使用绝对路径
[Install]运行级别下服务安装的相关设置，可设置为多用户，即系统运行级别为3

 

```shell
# systemctl start nginx         启动nginx服务

# systemctl stop nginx　          停止服务

# systemctl restart nginx　       重新启动服务

# systemctl list-units --type=service     查看所有已启动的服务

# systemctl status nginx          查看服务当前状态

# systemctl enable nginx         设置开机自启动

# systemctl disable nginx         停止开机自启动
```





2、修改全局的环境变量，使得nginx命令在任意目录下都能执行,例如：停止nginx -s stop ,重新加载：nginx -s reload ,启动：nginx

```shell
vim /etc/profile
export PATH=$PATH:/usr/local/nginx/sbin

# 使之生效
source /etc/profile

# 停止
nginx -s stop
# 重新加载
nginx -s reload 
# 启动
nginx
```





































 

```
#Nginx的默认安装位置
/usr/local/nginx

# nginx的配置文件
/usr/local/nginx/conf/nginx.conf 

# 默认的网页文件
/usr/local/nginx/html 
```