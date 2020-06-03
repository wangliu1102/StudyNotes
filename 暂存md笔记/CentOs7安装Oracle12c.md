# 

# 一、Oracle安装准备

## 

1.建立文件保存位置

```shell
mkdir /home/oracle12c
```

2.将需要的文件下载到该目录下
使用winScp软件上传文件（文件包括oracle12c离线rpm包；oracle12c安装压缩包）

百度云下载（Oracle企业版的zip压缩包）：

  链接：https://pan.baidu.com/s/1PB5k5FJdrMX7jb41wm9y5A 

  提取码：4g4l

rpm下载官网：https://pkgs.org/

![img](file:///D:/Documents/My%20Knowledge/temp/4fbef4c4-8a81-4c4c-9a5d-a2d0810b8f29/128/index_files/5818f034-32a5-4994-81c9-f5432d0cbe47.png)

3.并将压缩包解压

```shell
unzip **.zip -d /指定路径
```

4.导入oracle12c的rpm配置包（查看yum安装的配置，百度云中找不到就去rpm官网找）

```shell
# 需要输入很多个rpm包名
rpm -ivh --nodeps rpm包1 rpm包2 ...

或

# 运行当前目录下的所有rpm
rpm -Uvh *.rpm --nodeps --force
```

或者

```shell
yum -y install binutils compat-libcapl compat-libstdc++-33 gcc gcc-c++ glibc glibc-devel ksh libaio libaio-devel libgcc libstdc++ libstdc++-devel libXi libXtst make sysstat unixODBC unixODBC-devel
```



# 二、安装jdk

为知笔记地址：[Linux 下安装JDK1.8/切换使用两个版本的JDK](wiz://open_document?guid=b3ac50bc-be46-4613-a029-9f29468c303d&kbguid=&private_kbguid=9e15e816-792d-4528-9d21-a849cc4117d5)

GitHub地址：[https://github.com/wangliu1102/StudyNotes/tree/master/Linux/%E5%AE%89%E8%A3%85](https://github.com/wangliu1102/StudyNotes/tree/master/Linux/安装)

# 三、安装vnc

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
        systemctl start firewalld         启动firewalld服务
        systemctl status firewalld     查看运行状态
        systemctl enable firewalld     设置开机启动
        systemctl stop firewalld         关闭firewalld服务
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

![img](file:///D:/Documents/My%20Knowledge/temp/4fbef4c4-8a81-4c4c-9a5d-a2d0810b8f29/128/index_files/aa4dc5a7-5505-4723-a8ee-dbc9d5702160.png)



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



# 四、安装Oracle12c

1.创建运行Oracle时所需的用户和组：

```shell
groupadd dba                   #dba组中的成员用于管理、操作数据库
groupadd oinstall             #oinstall组中的成员用于管理Oracle数据库软件，即各种Oracle物理文件
useradd -m -g oinstall -G dba oracle
echo "123456" | passwd --stdin oracle
id oracle
```

2、创建安装Oracle时所需的目录，并设置权限：

 

```shell
 mkdir -pv /home/oracle /home/oraInventory
 chown -R oracle.oinstall /home/oracle
 chmod -R 755 /home/oracle
 chown -R oracle.oinstall /home/oraInventory
 chmod -R 755 /home/oraInventory
```

3、设定oracle用户的环境变量：（可以将注释删除） 

```shell
su - oracle
vim .bash_profile
```

末尾追加如下内容：

```shell
export ORACLE_BASE=/home/oracle #这个是自己建的安装路径 设置好
export ORACLE_HOME=$ORACLE_BASE/product/12.2.0/dbhome_1
export ORACLE_SID=orcl #此名称要与之后新建数据库时输入的SID名称一致
export PATH=$ORACLE_HOME/bin:$PATH
```

```shell
. .bash_profile
```

4、调整系统及内核参数（root用户操作）：
（1）# vim /etc/security/limits.conf，末尾追加如下内容：

```shell
oracle soft nproc 2047
oracle hard nproc 16384
oracle soft nofile 1024
oracle hard nofile 65536
oracle soft stack 10240
```

（2） # vim /etc/pam.d/login，末尾追加内容：

```shell
session required pam_limits.so
```

（3） # vim /etc/profile，末尾追加如下内容：

```shell
if [ "$USER" == "oracle" ];then
    if [ "$SHELL" == "/bin/ksh" ];then
        ulimit -p 16384
        ulimit -n 65536
    else
        ulimit -u 16384 -n 65536
    fi
fi
```

```shell
. /etc/profile
```

（4）# vim /etc/sysctl.conf，末尾追加如下内容（等号左右两边都有空格）：

```shell
fs.file-max = 6815744
fs.aio-max-nr = 1048576
kernel.sem = 250 32000 100 128
kernel.shmall = 2097152
kernel.shmmax = 8413413376
kernel.shmmni = 4096
net.ipv4.ip_local_port_range = 9000 65500
net.core.rmem_default = 262144
net.core.rmem_max = 4194304
net.core.wmem_default = 262144
net.core.wmem_max = 1048576
```

```shell
 sysctl -p
```

# 五、在vnc上安装oracle

在VNC远程终端中执行如下操作:

```shell
export DISPLAY=localhost:1
xdpyinfo | grep "name of display"  -->  name of display: localhost:1
xhost +  -->  access control disabled, clients can connect from any host
su - oracle
export DISPLAY=localhost:1
xdpyinfo | grep "name of display"  -->  name of display: localhost:1
//进入你自己解压的oracle路径
cd /home/oracle12c/database
LANG=en_US ./runInstaller
```

![img](file:///D:/Documents/My Knowledge/temp/4fbef4c4-8a81-4c4c-9a5d-a2d0810b8f29/128/index_files/0.1916923779671252.png)


1.Oracle的安装

![img](file:///D:/Documents/My Knowledge/temp/4fbef4c4-8a81-4c4c-9a5d-a2d0810b8f29/128/index_files/0.414606361669144.png)



**点击next**后，出现提示框选择yes

![img](file:///D:/Documents/My Knowledge/temp/4fbef4c4-8a81-4c4c-9a5d-a2d0810b8f29/128/index_files/0.7541732497262102.png)

![img](file:///D:/Documents/My Knowledge/temp/4fbef4c4-8a81-4c4c-9a5d-a2d0810b8f29/128/index_files/0.35145710839352073.png)

![img](file:///D:/Documents/My Knowledge/temp/4fbef4c4-8a81-4c4c-9a5d-a2d0810b8f29/128/index_files/0.17298664923716492.png)

![img](file:///D:/Documents/My Knowledge/temp/4fbef4c4-8a81-4c4c-9a5d-a2d0810b8f29/128/index_files/0.8941443295469844.png)

![img](file:///D:/Documents/My Knowledge/temp/4fbef4c4-8a81-4c4c-9a5d-a2d0810b8f29/128/index_files/0.5326831767457922.png)

![img](file:///D:/Documents/My Knowledge/temp/4fbef4c4-8a81-4c4c-9a5d-a2d0810b8f29/128/index_files/0.781033275616001.png)

**点击next**后弹出提示框 **选择yes**

![img](file:///D:/Documents/My Knowledge/temp/4fbef4c4-8a81-4c4c-9a5d-a2d0810b8f29/128/index_files/0.32616713202254916.png)

![img](file:///D:/Documents/My Knowledge/temp/4fbef4c4-8a81-4c4c-9a5d-a2d0810b8f29/128/index_files/0.8358661442241654.png)

**//**在这里都是rpm包导入失败的 再导入一次就行了

**//**导入完成后点击 **check again** **按钮**

**//**一直重复到没有问题

![img](file:///D:/Documents/My Knowledge/temp/4fbef4c4-8a81-4c4c-9a5d-a2d0810b8f29/128/index_files/0.2191393285657232.png)

如果出现上图问题这需要修改swap分区大小没有则跳过

修改Swap分区大小：

1、   创建swapfile：# dd if=/dev/zero of=swapfile bs=1024 count=16432448    //参照Expected Value的值

2、   将swapfile设置为swap空间：# mkswap swapfile

3、   启用swap空间：# swapon swapfile

4、   修改swapfile权限：# chmod 0600 swapfile

点击“Check Again”，重新检查：

 

备注：有时提示的错误信息，在Fixable列中为“Yes”，选中Fixable列中为“Yes”的行，点击“Fix & Check Again”，会弹出修改方法，根据修改方法，以root用户在终端中执行对应的脚本即可修复。

![img](file:///D:/Documents/My Knowledge/temp/4fbef4c4-8a81-4c4c-9a5d-a2d0810b8f29/128/index_files/0.6170448693544125.png)

![img](file:///D:/Documents/My Knowledge/temp/4fbef4c4-8a81-4c4c-9a5d-a2d0810b8f29/128/index_files/0.40723270328357924.png)

![img](file:///D:/Documents/My Knowledge/temp/4fbef4c4-8a81-4c4c-9a5d-a2d0810b8f29/128/index_files/0.5854402725798333.png)

**按照提示，以****root****用户身份执行上述脚本：**

```shell
 /home/oraInventory/orainstRoot.sh
```

**![img](file:///D:/Documents/My Knowledge/temp/4fbef4c4-8a81-4c4c-9a5d-a2d0810b8f29/128/index_files/0.06681764231533065.png)
**

```shell
 /home/oracle/product/12.2.0/dbhome_1/root.sh
```

 ![img](file:///D:/Documents/My Knowledge/temp/4fbef4c4-8a81-4c4c-9a5d-a2d0810b8f29/128/index_files/0.3607286112692246.png)

如果中间遇到不动时 可以按一下回车

 点击“OK”：

![img](file:///D:/Documents/My Knowledge/temp/4fbef4c4-8a81-4c4c-9a5d-a2d0810b8f29/128/index_files/0.10905230583710958.png)





2.配置监听：

//如果netca命令没找到  就有改一下 .bash_profile的oracle_home的路径 改为自己的路径

```shell
vim .bash_profile

. .bash_profile
```

```shell
LANG=en_US netca
```

![img](file:///D:/Documents/My Knowledge/temp/4fbef4c4-8a81-4c4c-9a5d-a2d0810b8f29/128/index_files/0.5662732753532699.png)

![img](file:///D:/Documents/My Knowledge/temp/4fbef4c4-8a81-4c4c-9a5d-a2d0810b8f29/128/index_files/0.6405168614377349.png)

![img](file:///D:/Documents/My Knowledge/temp/4fbef4c4-8a81-4c4c-9a5d-a2d0810b8f29/128/index_files/0.49699161303081296.png)

![img](file:///D:/Documents/My Knowledge/temp/4fbef4c4-8a81-4c4c-9a5d-a2d0810b8f29/128/index_files/0.22530952431998644.png)

![img](file:///D:/Documents/My Knowledge/temp/4fbef4c4-8a81-4c4c-9a5d-a2d0810b8f29/128/index_files/0.975479589623712.png)

![img](file:///D:/Documents/My Knowledge/temp/4fbef4c4-8a81-4c4c-9a5d-a2d0810b8f29/128/index_files/0.4837769346135079.png)

![img](file:///D:/Documents/My Knowledge/temp/4fbef4c4-8a81-4c4c-9a5d-a2d0810b8f29/128/index_files/0.7944728819920152.png)

![img](file:///D:/Documents/My Knowledge/temp/4fbef4c4-8a81-4c4c-9a5d-a2d0810b8f29/128/index_files/0.5806344817804724.png)

 



3.新建数据库实例AHHS：

```shell
LANG=en_US dbca
```

![img](file:///D:/Documents/My Knowledge/temp/4fbef4c4-8a81-4c4c-9a5d-a2d0810b8f29/128/index_files/0.24791320835005498.png)

![img](file:///D:/Documents/My Knowledge/temp/4fbef4c4-8a81-4c4c-9a5d-a2d0810b8f29/128/index_files/0.42733073224072615.png)

![img](file:///D:/Documents/My Knowledge/temp/4fbef4c4-8a81-4c4c-9a5d-a2d0810b8f29/128/index_files/0.6132761402504338.png)

![img](file:///D:/Documents/My Knowledge/temp/4fbef4c4-8a81-4c4c-9a5d-a2d0810b8f29/128/index_files/0.2860448511456913.png)

![img](file:///D:/Documents/My Knowledge/temp/4fbef4c4-8a81-4c4c-9a5d-a2d0810b8f29/128/index_files/0.9374651863724589.png)

![img](file:///D:/Documents/My Knowledge/temp/4fbef4c4-8a81-4c4c-9a5d-a2d0810b8f29/128/index_files/0.6551156273003536.png)

**密码为 AHHS2019s** 

![img](file:///D:/Documents/My Knowledge/temp/4fbef4c4-8a81-4c4c-9a5d-a2d0810b8f29/128/index_files/0.0410958447571564.png)

![img](file:///D:/Documents/My Knowledge/temp/4fbef4c4-8a81-4c4c-9a5d-a2d0810b8f29/128/index_files/0.0003960237871353681.png)

 

![img](file:///D:/Documents/My Knowledge/temp/4fbef4c4-8a81-4c4c-9a5d-a2d0810b8f29/128/index_files/0.3254706784904366.png)

![img](file:///D:/Documents/My Knowledge/temp/4fbef4c4-8a81-4c4c-9a5d-a2d0810b8f29/128/index_files/0.9504488814047313.png)

//看实际需求给予大小

![img](file:///D:/Documents/My Knowledge/temp/4fbef4c4-8a81-4c4c-9a5d-a2d0810b8f29/128/index_files/0.4535343960331422.png)

![img](file:///D:/Documents/My Knowledge/temp/4fbef4c4-8a81-4c4c-9a5d-a2d0810b8f29/128/index_files/0.44286509142939545.png)

![img](file:///D:/Documents/My Knowledge/temp/4fbef4c4-8a81-4c4c-9a5d-a2d0810b8f29/128/index_files/0.027613629739511536.png)

![img](file:///D:/Documents/My Knowledge/temp/4fbef4c4-8a81-4c4c-9a5d-a2d0810b8f29/128/index_files/0.7808325550113288.png)

 

![img](file:///D:/Documents/My Knowledge/temp/4fbef4c4-8a81-4c4c-9a5d-a2d0810b8f29/128/index_files/0.3164023238246428.png)



**备注：监听和数据库实例默认都是启动的**





# 六、配置数据库连接

 

```shell
sqlplus / as sysdba
```



![img](file:///D:/Documents/My Knowledge/temp/4fbef4c4-8a81-4c4c-9a5d-a2d0810b8f29/128/index_files/ccbaf987-9cee-4d6d-ae2d-de98d1ded3a1.jpg)

1、 查看数据库版本、字符集：

```shell
SQL> select * from v$version;
SQL> select * from nls_database_parameters where parameter='NLS_CHARACTERSET';
SQL> select userenv('language') from dual;
```

![img](file:///D:/Documents/My Knowledge/temp/4fbef4c4-8a81-4c4c-9a5d-a2d0810b8f29/128/index_files/320653b6-5327-463e-9548-2780f4982995.jpg)

![img](file:///D:/Documents/My Knowledge/temp/4fbef4c4-8a81-4c4c-9a5d-a2d0810b8f29/128/index_files/faeeedad-1b76-4e63-8147-4ea7a9f24cc0.jpg)

![img](file:///D:/Documents/My Knowledge/temp/4fbef4c4-8a81-4c4c-9a5d-a2d0810b8f29/128/index_files/bda4d24e-00fd-4a08-8f54-3d93696f337f.jpg)

2、 创建数据库连接

用户AHHS，密码为AHHS2019，权限为dba：

```shell
SQL> create user creditloan identified by 123456;
SQL> grant dba to creditloan;
SQL> commit;
SQL> quit;
```

3、 使用AHHS用户创建表和测试数据：

```shell
$ sqlplus creditloan/123456@orcl              //格式：$ sqlplus 用户名/用户名密码@数据库IP:port/SID
SQL> create table testtb(name varchar2(20),age number(3));
SQL> insert into testtb values('Keyso',35);
SQL> select * from testtb;
SQL> desc testtb;
SQL> commit;
```

![img](file:///D:/Documents/My Knowledge/temp/4fbef4c4-8a81-4c4c-9a5d-a2d0810b8f29/128/index_files/4249a776-6520-49d6-935c-92c6f961ef42.jpg)

4、 使用Navicat Premium连接数据库

防火墙开启1521端口：

```shell
sudo firewall-cmd --permanent --zone=public --add-port=1521/tcp
```

重新加载防火墙

```shell
sudo firewall-cmd --reload
```

可以使用下面命令查看端口号是否被加入

```shell
firewall-cmd --list-all-zones
```

用户名/密码：SYSTEM/AHHS2019s  或者 ahhs/ahhs2019

![img](file:///D:/Documents/My Knowledge/temp/4fbef4c4-8a81-4c4c-9a5d-a2d0810b8f29/128/index_files/38da1043-2294-4809-b6ae-b54be9926980.png)

若连接不是，看是不是环境问题，需要配置Navicat环境。

百度云下载：

> > 链接：https://pan.baidu.com/s/13e0X5HisTYpYb5h5-Z_jbA 
>
> > 提取码：slpu

在工具-》选项-》环境中，选择自己的oci.dll，确认后重启Navicat，如下图：

![img](file:///D:/Documents/My%20Knowledge/temp/4fbef4c4-8a81-4c4c-9a5d-a2d0810b8f29/128/index_files/1df0beb9-2d33-4cdf-97a2-1f46c80824ac.png)

# 七、配置oracle开机自启

1.user root 下面修改：

```shell
[root@data55 ~]# vi /etc/oratab  
hpxtdb:/opt/oracle/product/12.1.0.2:Y
```

将N该为Y

2.user oracle 下面修改：

```shell
[root@data55 ~]# su - oracle
[oracle@data55 ~]$cd $ORACLE_HOME/bin
[oracle@data55 bin]$ vim dbstart
```

找到 ORACLE_HOME_LISTNER=$1  这行， 修改成:

ORACLE_HOME_LISTNER=/opt/oracle/product/12.1.0.2/      //建议这一条

或者直接修改成：

ORACLE_HOME_LISTNER=$ORACLE_HOME

 同样道理修改 dbshut

```shell
[oracle@data55 bin]$ vim dbshut
```

测试运行 dbshut, dbstart 看能否启动oracle 服务及listener服务

```shell
[oracle@data55 bin]$ dbstart
[oracle@data55 bin]$ ps -efw | grep ora_
[oracle@data55 bin]$ lsnrctl status
[oracle@data55 bin]$ ps -efw | grep LISTEN | grep -v grep
```

3.系统启动项

```shell
[root@data55 ~]# chmod +x /etc/rc.d/rc.local
[root@data55 ~]# vi /etc/rc.d/rc.local
```

\## 末尾添加

```shell
su - oracle -lc dbstart
```

```shell
[root@data55 ~]# cat /etc/rc.d/rc.local //查看配置
```





//参考文档https://blog.51cto.com/qiuyue/2156367