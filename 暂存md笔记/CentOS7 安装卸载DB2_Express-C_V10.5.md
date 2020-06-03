# 一、安装VNC

1.导入rpm包

百度云下载：

  链接：https://pan.baidu.com/s/1v2tyjCvL_UxmyV68bIouzw 

  提取码：lfq8

```shell
rpm -ivh --nodeps tigervnc-1.8.0-13.el7.x86_64.rpm tigervnc-server-1.8.0-13.el7.x86_64.rpm tigervnc-server-module-1.8.0-17.el7.x86_64.rpm
```

或者

```shell
yum -y install tigervnc tigervnc-server tigervnc-server-module
```

2、复制配置文件

```shell
cp /lib/systemd/system/vncserver@.service /lib/systemd/system/vncserver@:1.service
```

3、修改配置文件

```shell
vim /lib/systemd/system/vncserver@:1.service
```

```shell
[Unit]
Description=Remote desktop service (VNC)
After=syslog.target network.target
[Service]
Type=forking
# Clean any existing files in /tmp/.X11-unix environment
ExecStartPre=/bin/sh -c '/usr/bin/vncserver -kill %i > /dev/null 2>&1 || :'
ExecStart=/usr/sbin/runuser -l root -c "/usr/bin/vncserver %i -geometry 1920x1080"
PIDFile=/root/.vnc/%H%i.pid
ExecStop=/bin/sh -c '/usr/bin/vncserver -kill %i > /dev/null 2>&1 || :'
[Install]
WantedBy=multi-user.target
```

4.设置vncserver的密码；

```shell
vncpasswd root
```

按提示输入密码以及确认密码
5.更新systemctl以使其生效；

```shell
systemctl daemon-reload
```

6.启动该服务用来启用vnc的1号窗口；

```shell
systemctl start vncserver@:1.service 
```

关闭1号窗口：

```shell
systemctl stop vncserver@:1.service 
```

查看状态：

```shell
systemctl status vncserver@:1.service -l
```

7.设置为开机自动启动

```shell
systemctl enable vncserver@:1.service
```

8.修改防火墙

```shell
systemctl start firewalld      #启动firewalld服务
systemctl status firewalld     #查看运行状态
systemctl enable firewalld     #设置开机启动
systemctl stop firewalld       #关闭firewalld服务
```

首先判断firewalld是否启动，输入以下命令判断

```shell
sudo firewall-cmd --state
```

如果启动应该输出

```shell
running
```

如果是not running，执行下面命令

```shell
sudo systemctl start firewalld
```

添加端口号5901-5905

```shell
sudo firewall-cmd --permanent --zone=public --add-port=5901-5905/tcp
```

重新加载防火墙

```shell
sudo firewall-cmd --reload
```

可以使用下面命令查看端口号是否被加入

```shell
firewall-cmd --list-all-zones
```

9.访问contos7上的vnc

Centos7的ip地址:1 即可访问

![img](file:///D:/Documents/My Knowledge/temp/ff3233d1-7dbc-458c-9260-508f3d05fd7f/128/index_files/aa4dc5a7-5505-4723-a8ee-dbc9d5702160.png)



10.蓝屏可能是gnome没安造成的,安装GNOME后重启linux

```shell
yum groupinstall "GNOME Desktop" "Graphical Administration Tools"
reboot
```

11.启动vnc报错 ：Job for vncserver@:1.service failed because a configured resource limit was exceeded. 

执行# systemctl status vncserver@:1.service

发现一条信息：

localhost.localdomain systemd[1]: PID file /root/.vnc/localhost.localdomain:1.pid not readable (yet?) after start.

检查 /root/.vnc/，发现没有localhost.localdomain:1.pid



解决方法：原来目录/tmp/下有一个/.X11-unix目录会占用这个pid序号资源。把这个目录改名/.X11-unix.bak后，再次执行

```shell
 mv /tmp/.X11-unix /tmp/.X11-unix.bak
 systemctl start vncserver@:1.service
```





# 二、安装DB2

## 1、下载DB2

百度云下载：

  链接：https://pan.baidu.com/s/1l5HEAj4soRJgnV1_-KtO0A 

  提取码：2614

这里我们下载v10.5的版本。

![img](file:///D:/Documents/My%20Knowledge/temp/ff3233d1-7dbc-458c-9260-508f3d05fd7f/128/index_files/eaa9cd9f-a64d-4b19-9031-1144265276db.png)

## 2、上传到/home/DB2目录下并解压

```shell
tar -zxvf v10.5_linuxx64_expc.tar.gz
```

解压后出现如下目录：

![img](file:///D:/Documents/My%20Knowledge/temp/ff3233d1-7dbc-458c-9260-508f3d05fd7f/128/index_files/98b94403-182c-4ecd-a096-f2b15fffafd1.png)

## 3、使用VNC连接服务器，进入可视化界面

```shell
cd /home/DB2/expc
```

![img](file:///D:/Documents/My%20Knowledge/temp/ff3233d1-7dbc-458c-9260-508f3d05fd7f/128/index_files/5a844c44-8741-4078-9279-e70e60718569.jpg)

## 4、执行./db2setup,图形化界面安装

![img](file:///D:/Documents/My%20Knowledge/temp/ff3233d1-7dbc-458c-9260-508f3d05fd7f/128/index_files/c553bc00-720e-4134-b9c0-6a6c28aefa1c.jpg)

## 5、按照如下的过程进行安装DB2 v10.5数据库

![img](file:///D:/Documents/My%20Knowledge/temp/ff3233d1-7dbc-458c-9260-508f3d05fd7f/128/index_files/32b776fc-7694-4192-90cb-35ef1399700e.jpg)

![img](file:///D:/Documents/My%20Knowledge/temp/ff3233d1-7dbc-458c-9260-508f3d05fd7f/128/index_files/71a9641d-9363-4155-bcec-de80d7165bb9.jpg)

![img](file:///D:/Documents/My%20Knowledge/temp/ff3233d1-7dbc-458c-9260-508f3d05fd7f/128/index_files/0f8770a8-b2cd-436f-acf6-2e3e95ffc1cc.png)

![img](file:///D:/Documents/My%20Knowledge/temp/ff3233d1-7dbc-458c-9260-508f3d05fd7f/128/index_files/8033de59-009b-47a7-b070-e3dcf0d41b2d.jpg)

![img](file:///D:/Documents/My%20Knowledge/temp/ff3233d1-7dbc-458c-9260-508f3d05fd7f/128/index_files/9f7481cb-d232-4519-b58f-88e860e4b26f.jpg)

![img](file:///D:/Documents/My%20Knowledge/temp/ff3233d1-7dbc-458c-9260-508f3d05fd7f/128/index_files/fb5573c3-1932-49bd-9da2-4b3d1ee0911a.jpg)

![img](file:///D:/Documents/My%20Knowledge/temp/ff3233d1-7dbc-458c-9260-508f3d05fd7f/128/index_files/1cd0cf63-17ec-4ce1-8440-2e8329620628.png)

注：选择安装完DB2 再进行创建实例

![img](file:///D:/Documents/My%20Knowledge/temp/ff3233d1-7dbc-458c-9260-508f3d05fd7f/128/index_files/3ac1d0c2-26a6-4545-a145-621211113fd5.jpg)

![img](file:///D:/Documents/My%20Knowledge/temp/ff3233d1-7dbc-458c-9260-508f3d05fd7f/128/index_files/906e7c10-12bc-4ef9-9a83-c72b1e8946c4.jpg)

![img](file:///D:/Documents/My%20Knowledge/temp/ff3233d1-7dbc-458c-9260-508f3d05fd7f/128/index_files/3d77eea0-39e3-4e2f-8c9c-a982da7a55ba.png)

![img](file:///D:/Documents/My%20Knowledge/temp/ff3233d1-7dbc-458c-9260-508f3d05fd7f/128/index_files/387dcf91-1c5f-4e61-bd52-993f8bd7ba45.png)

## 6、在数据库服务器上创建用户组

创建组db2iadm1（实例管理组）,db2fgrp1（DB2 fencing管理组）和dasadm1（数据库管理员用户组），其中dasadm1组在安装数据库时已经创建，我们只需要创建db2iadm1和db2fgrp组

```shell
groupadd -g 206 db2iadm1
groupadd -g 203 db2fgrp1
```

## 7、在数据库服务器上创建用户

创建用户db2inst1(实例管理用户)、db2fenc1(DB2 fencing 管理用户)和dasusr1(数据库管理员用户)，其中dasusr1用户在安装数据库时已经创建，我们只需要创建db2inst1和db2fenc用户

```shell
useradd -g db2iadm1 -u 209 -d /home/DB2/db2inst1 db2inst1
useradd -g db2fgrp1 -u 210 -d /home/DB2/db2fenc1 db2fenc1
```

![img](file:///D:/Documents/My%20Knowledge/temp/ff3233d1-7dbc-458c-9260-508f3d05fd7f/128/index_files/ecbb2110-71de-40e5-b5e7-d7b56fe97ab0.png)

![img](file:///D:/Documents/My%20Knowledge/temp/ff3233d1-7dbc-458c-9260-508f3d05fd7f/128/index_files/de0d1a94-150c-4701-9f40-5ccc0807bdfe.png)

## 8、添加用户的密码

添加用户db2inst1(实例管理用户)、db2fenc1(DB2 fencing 管理用户)和dasusr1(数据库管理员用户)的密码，其中dasusr1用户的密码在安装数据库时已经添加，我们只需要添加db2inst1和db2fenc用户的密码。

```shell
# 添加实例的用户密码
passwd db2inst1
# 123456

# 添加受防护的用户密码
passwd db2fenc1
# 123456
```

![img](file:///D:/Documents/My%20Knowledge/temp/ff3233d1-7dbc-458c-9260-508f3d05fd7f/128/index_files/b9de64fc-b8a0-40be-be6c-804c107ac84a.png)

## 9、检查用户组和用户是否创建成功

```shell
# 用户组: 
more /etc/group | grep db2
# 用户: 
more /etc/passwd | grep db2
```

![img](file:///D:/Documents/My%20Knowledge/temp/ff3233d1-7dbc-458c-9260-508f3d05fd7f/128/index_files/48caa59e-e1b4-4f26-bb46-a2473dac0f35.png)

## 10、创建实例

db2icrt创建的是实例，这里db2fenc1指定db2inst1为它的防护用户，而db2inst1为一实例用户。

```shell
cd /opt/ibm/db2/V10.5/instance/

# 创建DB2实例
./db2icrt -u db2fenc1 db2inst1
```

![img](file:///D:/Documents/My%20Knowledge/temp/ff3233d1-7dbc-458c-9260-508f3d05fd7f/128/index_files/eb925e2d-f618-4c3b-b3cb-096b1f6fca0b.png)

## 11、配置DB2实例

```shell
cd /opt/ibm/db2/V10.5/instance/

# 设置DB2自启动(注:依然在root用户下)，设置对db2inst1在Linux启动时自动启动。
./db2iauto -on db2inst1

# 修改网络服务端口(注:在db2inst1用户下)
# 修改DB2的服务端口为50000,默认情况下端口也是50000
 su - db2inst1
 db2 update dbm cfg using SVCENAME 50000
 # 修改DB2连接方式为TCP/IP
 # 修改DB2连接方式为TCPIP,然后可通过JDBC、ODBC等访问本DB2服务器上的数据库,安装了DB2客户端的其它机器也可访问数据库
 db2set DB2COMM=TCPIP
```

![img](file:///D:/Documents/My%20Knowledge/temp/ff3233d1-7dbc-458c-9260-508f3d05fd7f/128/index_files/4d3b84c5-0195-404e-908e-7bd5850a6095.png)

## 12、查看DB2

```shell
 su - db2inst1
# 查看DB2许可证情况
db2licm -l
# 查看DB2版本
db2level
# 检查相关参数
 db2set -all
```

![img](file:///D:/Documents/My%20Knowledge/temp/ff3233d1-7dbc-458c-9260-508f3d05fd7f/128/index_files/fef60c7c-5509-4273-a3b8-fe2d2e1149c0.png)

## 13、启动/停止实例

```shell
su - db2inst1
# 启动
db2start

# 停止
db2stop 
# 强制停止
db2stop force

# 停止所有数据库应用程序
db2 force application all
```



## **14、在数据库服务器上创建数据库 test**

```shell
db2 create database test
```



![img](file:///D:/Documents/My%20Knowledge/temp/ff3233d1-7dbc-458c-9260-508f3d05fd7f/128/index_files/5d48d592-e503-47d8-8f70-befaaa459f2d.png)

## 15、连接数据库test

```shell
# 指定用户和密码
db2 connect to test user db2inst1 using 123456
```

![img](file:///D:/Documents/My%20Knowledge/temp/ff3233d1-7dbc-458c-9260-508f3d05fd7f/128/index_files/2fafe5bd-635a-4512-b351-54ebfa38b4a6.png)

## 16、使用DBeaver连接DB2

首先防火墙开启端口50000（上面db2inst1实例指定的端口）

```shell
# 开放50000端口
firewall-cmd --zone=public --add-port=50000/tcp --permanent

# 开启或关闭端口需要重启，重启后配置立即生效
firewall-cmd --reload 

#查看所有开放的端口
firewall-cmd --list-ports 

#  查看防火墙状态
systemctl status firewalld
# 停止防火墙
systemctl stop firewalld
# 开启防火墙
systemctl start firewalld
```

使用DBeaver工具，如下：

![img](file:///D:/Documents/My%20Knowledge/temp/ff3233d1-7dbc-458c-9260-508f3d05fd7f/128/index_files/42eb21a3-4634-4ea6-96a6-fa5678f3166b.png)

![img](file:///D:/Documents/My%20Knowledge/temp/ff3233d1-7dbc-458c-9260-508f3d05fd7f/128/index_files/1a30a064-4b2e-41c9-a1cd-5bfb7f7ff713.png)

**注意：需要下载驱动并添加（百度云下载后添加，或者让DBeaver自己下载）**

百度云下载：

> > 链接：https://pan.baidu.com/s/1cM8wiLIDxexMOvujk7f4KQ 
>
> > 提取码：z162

![img](file:///D:/Documents/My%20Knowledge/temp/ff3233d1-7dbc-458c-9260-508f3d05fd7f/128/index_files/d442f4ac-5237-4471-a6b6-3b846ad5af73.png)

# 三、卸载DB2

## 1、删除db2数据库

```shell
# Remove DB[首先删除数据库]
su - db2inst1
db2start
db2 list db directory
db2 drop db <db name>
```

![img](file:///D:/Documents/My Knowledge/temp/ff3233d1-7dbc-458c-9260-508f3d05fd7f/128/index_files/4650bbfb-5b47-4b34-a1fb-bc14b665bb2f.png)

## 2、停止db2数据库并删除db2进程

```shell
# 查看db2进程
ps -ef|grep db2
```

![img](file:///D:/Documents/My Knowledge/temp/ff3233d1-7dbc-458c-9260-508f3d05fd7f/128/index_files/cbbab8ab-c0f2-4771-8a7c-23ab35b1483c.png)

 

```shell
# 切换到db2inst1用户
su - db2inst1
# 停止数据库
db2stop force

# 切换到dasusr1管理用户
su - dasusr1
# 停止DB2管理服务器
db2admin stop
```

![img](file:///D:/Documents/My Knowledge/temp/ff3233d1-7dbc-458c-9260-508f3d05fd7f/128/index_files/86437219-e665-4ecc-bf5a-703b52ca7781.png)

 

```shell
# 删除db2进程
su root
kill -9 `ps aux|grep db2 | grep -v 'grep db2' | awk '{print $2}'`
# 查看
ps -ef|grep db2
```

![img](file:///D:/Documents/My Knowledge/temp/ff3233d1-7dbc-458c-9260-508f3d05fd7f/128/index_files/a2018c79-1d25-432a-bf40-4cc880ee5acc.png)

![img](file:///D:/Documents/My Knowledge/temp/ff3233d1-7dbc-458c-9260-508f3d05fd7f/128/index_files/983380b3-2726-4752-b36a-2670eb4729eb.png)

## 3、删除实例、das、卸载

```shell
# Remove Instance【删除实例】
su - root
cd <db2 dir>/instance # 默认/opt/ibm/db2/V10.5/instance/
./db2ilist
./db2idrop <instance name>

# Remove das【删除das】
(1)su - root
(2)cd <db2 dir>/instance # 默认/opt/ibm/db2/V10.5/instance/
(3)./daslist
(4)./dasdrop <das user>

# Uninstall【卸载】
(1)su - root
(2)cd <db2 dir>/install # 默认/opt/ibm/db2/V10.5/install/
(3)./db2_deinstall -a
```

![img](file:///D:/Documents/My Knowledge/temp/ff3233d1-7dbc-458c-9260-508f3d05fd7f/128/index_files/bca6142c-a906-41dd-a7bd-23919f047acb.png)

![img](file:///D:/Documents/My Knowledge/temp/ff3233d1-7dbc-458c-9260-508f3d05fd7f/128/index_files/01d1ffb9-37ec-4682-93bd-1244342f830a.png)

![img](file:///D:/Documents/My Knowledge/temp/ff3233d1-7dbc-458c-9260-508f3d05fd7f/128/index_files/25e101b5-7249-4ee6-a856-fd5f8b411d1d.png)

## 4、删除db2数据库相关文件

```shell
# 删除db2用户，删除db2相关数据
vim /etc/passwd
# 进入文件后，输入/db2,按enter键查找db2相关配置，按n跳转到下一个
# 删除下图中的三行关于db2的配置
# 选中到某一行，输入dd，即可删除当前行
# 删除成功后，按i键进入输入模式
# 按Esc键后，输入:wq!即可保存退出
```

![img](file:///D:/Documents/My Knowledge/temp/ff3233d1-7dbc-458c-9260-508f3d05fd7f/128/index_files/ea2e7737-f0c3-49ce-a83e-bf6aa9b9d5af.png)

```shell
# 删除db2组
vim /etc/group
# 操作同上，删除下图中的三行关于db2的配置
```

![img](file:///D:/Documents/My Knowledge/temp/ff3233d1-7dbc-458c-9260-508f3d05fd7f/128/index_files/193a9435-6ce5-460f-8477-a0ad693d64c9.png)

```shell
# 删除db2端口
vim /etc/services
# 操作同上,未找到相关db2配置不做操作
# 按i键进入输入模式，按Esc键后，输入:q!即可不保存退出
```

```
# 删除db2相关用户实例
vim /etc/shadow
# 操作同上，删除下图中的三行关于db2的配置
```

![img](file:///D:/Documents/My Knowledge/temp/ff3233d1-7dbc-458c-9260-508f3d05fd7f/128/index_files/997e3023-3f00-4607-acdb-8d4d4b84799a.png)

```
# 删除db2相关目录
cd /var
ll
rm -rf db2*
```

![img](file:///D:/Documents/My Knowledge/temp/ff3233d1-7dbc-458c-9260-508f3d05fd7f/128/index_files/12014901-c0ea-4a6e-90f1-ae3657dfdcbf.png)

 

```shell
# 删除db2相关文件
cd /tmp
ll
rm -rf db2*
rm -rf dascrt*
```

```shell
# 删除dasusr1，db2inst1,db2fenc1文件夹
cd /home/DB2
rm -rf dasusr1
rm -rf db2inst1
rm -rf db2fenc1
```

![img](file:///D:/Documents/My Knowledge/temp/ff3233d1-7dbc-458c-9260-508f3d05fd7f/128/index_files/0e6c455b-db39-4192-89ea-f321fa335470.png)

 

```shell
# 删除db2安装目录和rsp文件
cd /opt
rm -rf ibm/

cd /root
rm -rf db2expc.rsp
```

![img](file:///D:/Documents/My Knowledge/temp/ff3233d1-7dbc-458c-9260-508f3d05fd7f/128/index_files/473a356d-987b-44d8-94bb-5e805dafd30b.png)

到这里就卸载成功了，可以重新安装了。