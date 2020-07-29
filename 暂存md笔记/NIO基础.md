# 一、Java NIO 简介 

![img](file:///D:/Documents/My%20Knowledge/temp/f8a75a7d-eb75-473d-9eb0-70b015ebb564/128/index_files/0c6e61fb-bece-49c7-be87-74ccfb884585.png)

# 二、Java NIO 与 IO 的主要区别

![img](file:///D:/Documents/My%20Knowledge/temp/f8a75a7d-eb75-473d-9eb0-70b015ebb564/128/index_files/2f0adcc7-e297-4c0e-bc7b-06669b058f9c.png)

# 三、缓冲区(Buffer)和通道(Channel)

![img](file:///D:/Documents/My%20Knowledge/temp/f8a75a7d-eb75-473d-9eb0-70b015ebb564/128/index_files/83d0111e-76e1-4a0d-ba63-d625975ba5f4.png)

## 1、缓冲区（Buffer）

### （1）缓冲区概述及常用Buffer

![img](file:///D:/Documents/My%20Knowledge/temp/f8a75a7d-eb75-473d-9eb0-70b015ebb564/128/index_files/880fa5e1-cd9c-4b8c-aadc-f422c1975dff.png)

![img](file:///D:/Documents/My%20Knowledge/temp/f8a75a7d-eb75-473d-9eb0-70b015ebb564/128/index_files/1e87ba01-f547-4353-abb1-0e6ebb796114.png)

### （2）缓冲区的基本属性

![img](file:///D:/Documents/My%20Knowledge/temp/f8a75a7d-eb75-473d-9eb0-70b015ebb564/128/index_files/7940b5f1-475f-4239-ac01-2e7eab9e65e1.png)

![img](file:///D:/Documents/My%20Knowledge/temp/f8a75a7d-eb75-473d-9eb0-70b015ebb564/128/index_files/6ed3d26c-c6f9-4d34-9e40-68e5b2287267.png)

### （3）Buffer 的常用方法

![img](file:///D:/Documents/My%20Knowledge/temp/f8a75a7d-eb75-473d-9eb0-70b015ebb564/128/index_files/5be843e1-c51e-4441-b69b-dcad02ae3ab4.png)

### （4）缓冲区的数据操作

![img](file:///D:/Documents/My%20Knowledge/temp/f8a75a7d-eb75-473d-9eb0-70b015ebb564/128/index_files/ee912c01-b160-4dd0-b508-75f96b476a66.png)

```java
    @Test
    public void test1(){
        String str = "abcde";

        //1. 分配一个指定大小的缓冲区
        ByteBuffer buf = ByteBuffer.allocate(1024);

        System.out.println("-----------------allocate()----------------");
        System.out.println(buf.position()); // 0
        System.out.println(buf.limit());  // 1024
        System.out.println(buf.capacity());  // 1024

        //2. 利用 put() 存入数据到缓冲区中
        buf.put(str.getBytes());

        System.out.println("-----------------put()----------------");
        System.out.println(buf.position());  // 5
        System.out.println(buf.limit());  // 1024
        System.out.println(buf.capacity());  // 1024

        //3. 切换读取数据模式
        buf.flip();

        System.out.println("-----------------flip()----------------");
        System.out.println(buf.position());  // 0
        System.out.println(buf.limit());  // 5
        System.out.println(buf.capacity());  // 1024

        //4. 利用 get() 读取缓冲区中的数据
        byte[] dst = new byte[buf.limit()];
        buf.get(dst);
        System.out.println(new String(dst, 0, dst.length));  // abcde

        System.out.println("-----------------get()----------------");
        System.out.println(buf.position());  // 5
        System.out.println(buf.limit());  // 5
        System.out.println(buf.capacity());  // 1024

        //5. rewind() : 可重复读
        buf.rewind();

        System.out.println("-----------------rewind()----------------");
        System.out.println(buf.position());  // 0
        System.out.println(buf.limit());  // 5
        System.out.println(buf.capacity());  // 1024

        //6. clear() : 清空缓冲区. 但是缓冲区中的数据依然存在，但是处于“被遗忘”状态
        buf.clear();

        System.out.println("-----------------clear()----------------");
        System.out.println(buf.position());  // 0
        System.out.println(buf.limit());  // 1024
        System.out.println(buf.capacity());  // 1024

        System.out.println((char)buf.get());  // a

    }
```

```java
    @Test
    public void test2(){
        String str = "abcde";

        ByteBuffer buf = ByteBuffer.allocate(1024);

        buf.put(str.getBytes());

        buf.flip();

        byte[] dst = new byte[buf.limit()];
        buf.get(dst, 0, 2);
        System.out.println(new String(dst, 0, 2)); // ab
        System.out.println(buf.position()); // 2

        //mark() : 标记
        buf.mark();

        buf.get(dst, 2, 2);
        System.out.println(new String(dst, 2, 2));// cd
        System.out.println(buf.position()); // 4

        //reset() : 恢复到 mark 的位置
        buf.reset();
        System.out.println(buf.position()); // 2

        //判断缓冲区中是否还有剩余数据
        if(buf.hasRemaining()){

            //获取缓冲区中可以操作的数量
            System.out.println(buf.remaining());// 3
        }
    }
```



### （5）直接与非直接缓冲区

非直接缓冲区：通过 allocate() 方法分配缓冲区，将缓冲区建立在 JVM 的内存中

直接缓冲区：通过 allocateDirect() 方法分配直接缓冲区，将缓冲区建立在物理内存中。可以提高效率

![img](file:///D:/Documents/My%20Knowledge/temp/f8a75a7d-eb75-473d-9eb0-70b015ebb564/128/index_files/68dfe943-0840-4efb-9569-7082a2cbf772.png)

![img](file:///D:/Documents/My%20Knowledge/temp/f8a75a7d-eb75-473d-9eb0-70b015ebb564/128/index_files/a467c5be-c7aa-4b85-a146-03e20191d5bf.jpg)

![img](file:///D:/Documents/My%20Knowledge/temp/f8a75a7d-eb75-473d-9eb0-70b015ebb564/128/index_files/4d675852-4105-4bad-8672-296b582a8b4e.jpg)

 

```java
    @Test
    public void test3(){
        //分配直接缓冲区
        ByteBuffer buf = ByteBuffer.allocateDirect(1024);

        System.out.println(buf.isDirect());// true
    }
```



## 2、通道(Channel)

### （1）通道概述

![img](file:///D:/Documents/My%20Knowledge/temp/f8a75a7d-eb75-473d-9eb0-70b015ebb564/128/index_files/b6e6ac4f-33fa-4c78-916b-1cd6a7548540.png)

![img](file:///D:/Documents/My%20Knowledge/temp/f8a75a7d-eb75-473d-9eb0-70b015ebb564/128/index_files/1e7d1034-b323-4004-a068-3aa5a7d6f3cb.jpg)

![img](file:///D:/Documents/My%20Knowledge/temp/f8a75a7d-eb75-473d-9eb0-70b015ebb564/128/index_files/2066070e-1a6f-439b-a65b-a9041fc50461.jpg)

![img](file:///D:/Documents/My%20Knowledge/temp/f8a75a7d-eb75-473d-9eb0-70b015ebb564/128/index_files/42d7dff1-ad81-42a5-af75-c682d56c492f.jpg)

### （2）通道的主要实现

![img](file:///D:/Documents/My%20Knowledge/temp/f8a75a7d-eb75-473d-9eb0-70b015ebb564/128/index_files/8b9c972b-90ba-4a76-a80c-cec6d0f65cab.png)

### （3）获取通道

![img](file:///D:/Documents/My%20Knowledge/temp/f8a75a7d-eb75-473d-9eb0-70b015ebb564/128/index_files/487357d3-ba13-4c3b-81a0-6cc1a3a4f60d.png)

### （4）通道的数据传输

![img](file:///D:/Documents/My%20Knowledge/temp/f8a75a7d-eb75-473d-9eb0-70b015ebb564/128/index_files/b437d832-9149-4e6d-b0d1-5de52b6358ed.png)

### （5）分散 (Scatter) 和聚集 (Gather)

![img](file:///D:/Documents/My%20Knowledge/temp/f8a75a7d-eb75-473d-9eb0-70b015ebb564/128/index_files/edeeab99-66ab-4e51-a510-0bb142deb623.jpg)

![img](file:///D:/Documents/My%20Knowledge/temp/f8a75a7d-eb75-473d-9eb0-70b015ebb564/128/index_files/ca888681-66a3-41f3-a1cb-26e9b9d5388e.jpg)

 

```java
    //分散和聚集
    @Test
    public void test4() throws IOException{
        RandomAccessFile raf1 = new RandomAccessFile("1.txt", "rw");

        //1. 获取通道
        FileChannel channel1 = raf1.getChannel();

        //2. 分配指定大小的缓冲区
        ByteBuffer buf1 = ByteBuffer.allocate(100);
        ByteBuffer buf2 = ByteBuffer.allocate(1024);

        //3. 分散读取
        ByteBuffer[] bufs = {buf1, buf2};
        channel1.read(bufs);

        for (ByteBuffer byteBuffer : bufs) {
            byteBuffer.flip();
        }

        System.out.println(new String(bufs[0].array(), 0, bufs[0].limit()));
        System.out.println("-----------------");
        System.out.println(new String(bufs[1].array(), 0, bufs[1].limit()));

        //4. 聚集写入
        RandomAccessFile raf2 = new RandomAccessFile("2.txt", "rw");
        FileChannel channel2 = raf2.getChannel();

        channel2.write(bufs);
    }
```



### （6）transferFrom()

![img](file:///D:/Documents/My%20Knowledge/temp/f8a75a7d-eb75-473d-9eb0-70b015ebb564/128/index_files/5d9183fa-7360-4a78-8d3b-344153283a4a.jpg)

 

```java
    //通道之间的数据传输(直接缓冲区)
    @Test
    public void test3() throws IOException{
        FileChannel inChannel = FileChannel.open(Paths.get("d:/1.mkv"), StandardOpenOption.READ);
        FileChannel outChannel = FileChannel.open(Paths.get("d:/2.mkv"), StandardOpenOption.WRITE, StandardOpenOption.READ, StandardOpenOption.CREATE);

//      inChannel.transferTo(0, inChannel.size(), outChannel);
        outChannel.transferFrom(inChannel, 0, inChannel.size());

        inChannel.close();
        outChannel.close();
    }
```



### （7）transferTo()

![img](file:///D:/Documents/My%20Knowledge/temp/f8a75a7d-eb75-473d-9eb0-70b015ebb564/128/index_files/607b65aa-0dd7-4371-9d46-678f2c57003f.jpg)

### （8）字符集Charset

编码：字符串 -> 字节数组

解码：字节数组 -> 字符串

 

```java
    @Test
    public void test5(){
        Map<String, Charset> map = Charset.availableCharsets();

        Set<Entry<String, Charset>> set = map.entrySet();

        for (Entry<String, Charset> entry : set) {
            System.out.println(entry.getKey() + "=" + entry.getValue());
        }
    }
```

![img](file:///D:/Documents/My%20Knowledge/temp/f8a75a7d-eb75-473d-9eb0-70b015ebb564/128/index_files/90fd8b1c-f8ca-44e4-b152-e9a475e372c6.png)

 

```java
    //字符集
    @Test
    public void test6() throws IOException{
        Charset cs1 = Charset.forName("GBK");

        //获取编码器
        CharsetEncoder ce = cs1.newEncoder();

        //获取解码器
        CharsetDecoder cd = cs1.newDecoder();

        CharBuffer cBuf = CharBuffer.allocate(1024);
        cBuf.put("尚硅谷威武！");
        cBuf.flip();

        //编码
        ByteBuffer bBuf = ce.encode(cBuf);

        for (int i = 0; i < 12; i++) {
            System.out.println(bBuf.get());
        }

        //解码
        bBuf.flip();
        CharBuffer cBuf2 = cd.decode(bBuf);
        System.out.println(cBuf2.toString());

        System.out.println("------------------------------------------------------");

        Charset cs2 = Charset.forName("GBK");
        bBuf.flip();
        CharBuffer cBuf3 = cs2.decode(bBuf);
        System.out.println(cBuf3.toString());
    }
```

![img](file:///D:/Documents/My%20Knowledge/temp/f8a75a7d-eb75-473d-9eb0-70b015ebb564/128/index_files/6ef83eda-308c-4779-99f5-21c8a68e8365.png)

# 四、文件通道(FileChannel)

![img](file:///D:/Documents/My%20Knowledge/temp/f8a75a7d-eb75-473d-9eb0-70b015ebb564/128/index_files/aca9d2f3-6ecc-4a03-a568-4aec208bdb21.png)

 

```java
    //利用通道完成文件的复制（非直接缓冲区）
    @Test
    public void test1(){//10874-10953
        long start = System.currentTimeMillis();

        FileInputStream fis = null;
        FileOutputStream fos = null;
        //①获取通道
        FileChannel inChannel = null;
        FileChannel outChannel = null;
        try {
            fis = new FileInputStream("d:/1.mkv");
            fos = new FileOutputStream("d:/2.mkv");

            inChannel = fis.getChannel();
            outChannel = fos.getChannel();

            //②分配指定大小的缓冲区
            ByteBuffer buf = ByteBuffer.allocate(1024);

            //③将通道中的数据存入缓冲区中
            while(inChannel.read(buf) != -1){
                buf.flip(); //切换读取数据的模式
                //④将缓冲区中的数据写入通道中
                outChannel.write(buf);
                buf.clear(); //清空缓冲区
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(outChannel != null){
                try {
                    outChannel.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if(inChannel != null){
                try {
                    inChannel.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if(fos != null){
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if(fis != null){
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        long end = System.currentTimeMillis();
        System.out.println("耗费时间为：" + (end - start));

    }
```



 

```java
    //使用直接缓冲区完成文件的复制(内存映射文件)
    @Test
    public void test2() throws IOException{//2127-1902-1777
        long start = System.currentTimeMillis();

        FileChannel inChannel = FileChannel.open(Paths.get("d:/1.mkv"), StandardOpenOption.READ);
        FileChannel outChannel = FileChannel.open(Paths.get("d:/2.mkv"), StandardOpenOption.WRITE, StandardOpenOption.READ, StandardOpenOption.CREATE);

        //内存映射文件
        MappedByteBuffer inMappedBuf = inChannel.map(MapMode.READ_ONLY, 0, inChannel.size());
        MappedByteBuffer outMappedBuf = outChannel.map(MapMode.READ_WRITE, 0, inChannel.size());

        //直接对缓冲区进行数据的读写操作
        byte[] dst = new byte[inMappedBuf.limit()];
        inMappedBuf.get(dst);
        outMappedBuf.put(dst);

        inChannel.close();
        outChannel.close();

        long end = System.currentTimeMillis();
        System.out.println("耗费时间为：" + (end - start));
    }
```



# 五、NIO 的非阻塞式网络通信

## 1、阻塞与非阻塞

![img](file:///D:/Documents/My%20Knowledge/temp/f8a75a7d-eb75-473d-9eb0-70b015ebb564/128/index_files/e1acde45-7421-41f0-ba4e-0dcb862ed5c5.png)

## 2、选择器（Selector）

![img](file:///D:/Documents/My%20Knowledge/temp/f8a75a7d-eb75-473d-9eb0-70b015ebb564/128/index_files/66127ad9-d352-401e-8128-fb3b288205b5.jpg)

## 3、选择器（Selector ）的应用

![img](file:///D:/Documents/My%20Knowledge/temp/f8a75a7d-eb75-473d-9eb0-70b015ebb564/128/index_files/5a853912-d266-40f2-acef-f1f65ae4bf51.png)

## 4、SelectionKey

![img](file:///D:/Documents/My%20Knowledge/temp/f8a75a7d-eb75-473d-9eb0-70b015ebb564/128/index_files/b44089a8-0fb7-4179-ad86-a0927c35fced.png)

![img](file:///D:/Documents/My%20Knowledge/temp/f8a75a7d-eb75-473d-9eb0-70b015ebb564/128/index_files/7e1a4098-bf4a-45aa-ab5b-83268799220a.png)

## 5、Selector的常用方法

![img](file:///D:/Documents/My%20Knowledge/temp/f8a75a7d-eb75-473d-9eb0-70b015ebb564/128/index_files/80f54b70-d5c7-407e-94a5-b8298efce0bb.png)

## 6、SocketChannel

![img](file:///D:/Documents/My%20Knowledge/temp/f8a75a7d-eb75-473d-9eb0-70b015ebb564/128/index_files/3ca34756-5190-4900-8e47-bf56e380afce.png)

## 7、ServerSocketChannel 

![img](file:///D:/Documents/My%20Knowledge/temp/f8a75a7d-eb75-473d-9eb0-70b015ebb564/128/index_files/8803b33e-a144-46d1-addb-93ecd261ebae.png)

#### 阻塞式：

 

```java
package com.wl.java.nio2;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

import org.junit.Test;

/*
 * 一、使用 NIO 完成网络通信的三个核心：
 *
 * 1. 通道（Channel）：负责连接
 *
 *     java.nio.channels.Channel 接口：
 *          |--SelectableChannel
 *              |--SocketChannel
 *              |--ServerSocketChannel
 *              |--DatagramChannel
 *
 *              |--Pipe.SinkChannel
 *              |--Pipe.SourceChannel
 *
 * 2. 缓冲区（Buffer）：负责数据的存取
 *
 * 3. 选择器（Selector）：是 SelectableChannel 的多路复用器。用于监控 SelectableChannel 的 IO 状况
 *
 */
public class TestBlockingNIO {

    //客户端
    @Test
    public void client() throws IOException{
        //1. 获取通道
        SocketChannel sChannel = SocketChannel.open(new InetSocketAddress("127.0.0.1", 9898));

        FileChannel inChannel = FileChannel.open(Paths.get("1.jpg"), StandardOpenOption.READ);

        //2. 分配指定大小的缓冲区
        ByteBuffer buf = ByteBuffer.allocate(1024);

        //3. 读取本地文件，并发送到服务端
        while(inChannel.read(buf) != -1){
            buf.flip();
            sChannel.write(buf);
            buf.clear();
        }

        //4. 关闭通道
        inChannel.close();
        sChannel.close();
    }

    //服务端
    @Test
    public void server() throws IOException{
        //1. 获取通道
        ServerSocketChannel ssChannel = ServerSocketChannel.open();

        FileChannel outChannel = FileChannel.open(Paths.get("2.jpg"), StandardOpenOption.WRITE, StandardOpenOption.CREATE);

        //2. 绑定连接
        ssChannel.bind(new InetSocketAddress(9898));

        //3. 获取客户端连接的通道
        SocketChannel sChannel = ssChannel.accept();

        //4. 分配指定大小的缓冲区
        ByteBuffer buf = ByteBuffer.allocate(1024);

        //5. 接收客户端的数据，并保存到本地
        while(sChannel.read(buf) != -1){
            buf.flip();
            outChannel.write(buf);
            buf.clear();
        }

        //6. 关闭通道
        sChannel.close();
        outChannel.close();
        ssChannel.close();

    }

}

```

 

```java
package com.wl.java.nio2;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

import org.junit.Test;

public class TestBlockingNIO2 {

    //客户端
    @Test
    public void client() throws IOException{
        SocketChannel sChannel = SocketChannel.open(new InetSocketAddress("127.0.0.1", 9898));

        FileChannel inChannel = FileChannel.open(Paths.get("1.jpg"), StandardOpenOption.READ);

        ByteBuffer buf = ByteBuffer.allocate(1024);

        while(inChannel.read(buf) != -1){
            buf.flip();
            sChannel.write(buf);
            buf.clear();
        }

        sChannel.shutdownOutput();

        //接收服务端的反馈
        int len = 0;
        while((len = sChannel.read(buf)) != -1){
            buf.flip();
            System.out.println(new String(buf.array(), 0, len));
            buf.clear();
        }

        inChannel.close();
        sChannel.close();
    }

    //服务端
    @Test
    public void server() throws IOException{
        ServerSocketChannel ssChannel = ServerSocketChannel.open();

        FileChannel outChannel = FileChannel.open(Paths.get("2.jpg"), StandardOpenOption.WRITE, StandardOpenOption.CREATE);

        ssChannel.bind(new InetSocketAddress(9898));

        SocketChannel sChannel = ssChannel.accept();

        ByteBuffer buf = ByteBuffer.allocate(1024);

        while(sChannel.read(buf) != -1){
            buf.flip();
            outChannel.write(buf);
            buf.clear();
        }

        //发送反馈给客户端
        buf.put("服务端接收数据成功".getBytes());
        buf.flip();
        sChannel.write(buf);

        sChannel.close();
        outChannel.close();
        ssChannel.close();
    }

}

```

#### 非阻塞式：

 

```java
package com.wl.java.nio2;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Date;
import java.util.Iterator;
import java.util.Scanner;

import org.junit.Test;

/*
 * 一、使用 NIO 完成网络通信的三个核心：
 *
 * 1. 通道（Channel）：负责连接
 *
 *     java.nio.channels.Channel 接口：
 *          |--SelectableChannel
 *              |--SocketChannel
 *              |--ServerSocketChannel
 *              |--DatagramChannel
 *
 *              |--Pipe.SinkChannel
 *              |--Pipe.SourceChannel
 *
 * 2. 缓冲区（Buffer）：负责数据的存取
 *
 * 3. 选择器（Selector）：是 SelectableChannel 的多路复用器。用于监控 SelectableChannel 的 IO 状况
 *
 */
public class TestNonBlockingNIO {

    //客户端
    @Test
    public void client() throws IOException{
        //1. 获取通道
        SocketChannel sChannel = SocketChannel.open(new InetSocketAddress("127.0.0.1", 9898));

        //2. 切换非阻塞模式
        sChannel.configureBlocking(false);

        //3. 分配指定大小的缓冲区
        ByteBuffer buf = ByteBuffer.allocate(1024);

        //4. 发送数据给服务端
        Scanner scan = new Scanner(System.in);

        while(scan.hasNext()){
            String str = scan.next();
            buf.put((new Date().toString() + "\n" + str).getBytes());
            buf.flip();
            sChannel.write(buf);
            buf.clear();
        }

        //5. 关闭通道
        sChannel.close();
    }

    //服务端
    @Test
    public void server() throws IOException{
        //1. 获取通道
        ServerSocketChannel ssChannel = ServerSocketChannel.open();

        //2. 切换非阻塞模式
        ssChannel.configureBlocking(false);

        //3. 绑定连接
        ssChannel.bind(new InetSocketAddress(9898));

        //4. 获取选择器
        Selector selector = Selector.open();

        //5. 将通道注册到选择器上, 并且指定“监听接收事件”
        ssChannel.register(selector, SelectionKey.OP_ACCEPT);

        //6. 轮询式的获取选择器上已经“准备就绪”的事件
        while(selector.select() > 0){

            //7. 获取当前选择器中所有注册的“选择键(已就绪的监听事件)”
            Iterator<SelectionKey> it = selector.selectedKeys().iterator();

            while(it.hasNext()){
                //8. 获取准备“就绪”的是事件
                SelectionKey sk = it.next();

                //9. 判断具体是什么事件准备就绪
                if(sk.isAcceptable()){
                    //10. 若“接收就绪”，获取客户端连接
                    SocketChannel sChannel = ssChannel.accept();

                    //11. 切换非阻塞模式
                    sChannel.configureBlocking(false);

                    //12. 将该通道注册到选择器上
                    sChannel.register(selector, SelectionKey.OP_READ);
                }else if(sk.isReadable()){
                    //13. 获取当前选择器上“读就绪”状态的通道
                    SocketChannel sChannel = (SocketChannel) sk.channel();

                    //14. 读取数据
                    ByteBuffer buf = ByteBuffer.allocate(1024);

                    int len = 0;
                    while((len = sChannel.read(buf)) > 0 ){
                        buf.flip();
                        System.out.println(new String(buf.array(), 0, len));
                        buf.clear();
                    }
                }

                //15. 取消选择键 SelectionKey
                it.remove();
            }
        }
    }
}

```

## 8、DatagramChannel

![img](file:///D:/Documents/My%20Knowledge/temp/f8a75a7d-eb75-473d-9eb0-70b015ebb564/128/index_files/46975cff-78f7-4cf9-b449-016beed60aa6.png)

#### 非阻塞式：

 

```java
package com.wl.java.nio2;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.util.Date;
import java.util.Iterator;
import java.util.Scanner;

import org.junit.Test;

public class TestNonBlockingNIO2 {

    @Test
    public void send() throws IOException{
        DatagramChannel dc = DatagramChannel.open();

        dc.configureBlocking(false);

        ByteBuffer buf = ByteBuffer.allocate(1024);

        Scanner scan = new Scanner(System.in);

        while(scan.hasNext()){
            String str = scan.next();
            buf.put((new Date().toString() + ":\n" + str).getBytes());
            buf.flip();
            dc.send(buf, new InetSocketAddress("127.0.0.1", 9898));
            buf.clear();
        }

        dc.close();
    }

    @Test
    public void receive() throws IOException{
        DatagramChannel dc = DatagramChannel.open();

        dc.configureBlocking(false);

        dc.bind(new InetSocketAddress(9898));

        Selector selector = Selector.open();

        dc.register(selector, SelectionKey.OP_READ);

        while(selector.select() > 0){
            Iterator<SelectionKey> it = selector.selectedKeys().iterator();

            while(it.hasNext()){
                SelectionKey sk = it.next();

                if(sk.isReadable()){
                    ByteBuffer buf = ByteBuffer.allocate(1024);

                    dc.receive(buf);
                    buf.flip();
                    System.out.println(new String(buf.array(), 0, buf.limit()));
                    buf.clear();
                }
            }

            it.remove();
        }
    }

}

```

# 六、管道(Pipe)

## 1、管道概述

![img](file:///D:/Documents/My%20Knowledge/temp/f8a75a7d-eb75-473d-9eb0-70b015ebb564/128/index_files/7cab95ff-f1c0-4c59-95d7-2c210ec73676.jpg)

## 2、向管道写数据

![img](file:///D:/Documents/My%20Knowledge/temp/f8a75a7d-eb75-473d-9eb0-70b015ebb564/128/index_files/3d6456ad-294e-4dc4-a65f-c90b57cd5a0d.jpg)

## 3、从管道读取数据

![img](file:///D:/Documents/My%20Knowledge/temp/f8a75a7d-eb75-473d-9eb0-70b015ebb564/128/index_files/ed32258c-b8ef-4f0b-b323-4b8490087068.png)

 

```java
    @Test
    public void test1() throws IOException{
        //1. 获取管道
        Pipe pipe = Pipe.open();

        //2. 将缓冲区中的数据写入管道
        ByteBuffer buf = ByteBuffer.allocate(1024);

        Pipe.SinkChannel sinkChannel = pipe.sink();
        buf.put("通过单向管道发送数据".getBytes());
        buf.flip();
        sinkChannel.write(buf);

        //3. 读取缓冲区中的数据
        Pipe.SourceChannel sourceChannel = pipe.source();
        buf.flip();
        int len = sourceChannel.read(buf);
        System.out.println(new String(buf.array(), 0, len)); // 通过单向管道发送数据

        sourceChannel.close();
        sinkChannel.close();
    }
```

# 七、Java NIO2 (Path、Paths 与 Files )

## 

## 1、Java NIO概述

![img](file:///D:/Documents/My Knowledge/temp/f470ae37-1053-44e9-a266-28be83e95c23/128/index_files/b5cb147f-39ce-416e-bb1e-c191522320c8.png)

## 2、NIO. 2

![img](file:///D:/Documents/My Knowledge/temp/f470ae37-1053-44e9-a266-28be83e95c23/128/index_files/2209d5b3-3680-4ded-a40e-3d1978de7d10.png)

## 3、Path 、Paths 和Files 核心API

![img](file:///D:/Documents/My Knowledge/temp/f470ae37-1053-44e9-a266-28be83e95c23/128/index_files/e023735f-b0a0-4691-8369-635f34f31337.png)

![img](file:///D:/Documents/My Knowledge/temp/f470ae37-1053-44e9-a266-28be83e95c23/128/index_files/553d5cc3-02a9-425b-8589-d0644fd3c1b1.png)

## 4、Path接口

![img](file:///D:/Documents/My Knowledge/temp/f470ae37-1053-44e9-a266-28be83e95c23/128/index_files/5ec92d8a-e521-475d-aa6d-8eec627b7f08.png)

 

```java
    //如何使用Paths实例化Path
    @Test
    public void test1() {
        Path path1 = Paths.get("d:\\nio\\hello.txt");//new File(String filepath)

        Path path2 = Paths.get("d:\\", "nio\\hello.txt");//new File(String parent,String filename);

        System.out.println(path1);// d:\nio\hello.txt
        System.out.println(path2);// d:\nio\hello.txt

        Path path3 = Paths.get("d:\\", "nio");
        System.out.println(path3);// d:\nio
    }

    //Path中的常用方法
    @Test
    public void test2() {
        Path path1 = Paths.get("d:\\", "nio\\nio1\\nio2\\hello.txt");
        Path path2 = Paths.get("hello.txt");

//      String toString() ： 返回调用 Path 对象的字符串表示形式
        System.out.println(path1); // d:\nio\nio1\nio2\hello.txt

//      boolean startsWith(String path) : 判断是否以 path 路径开始
        System.out.println(path1.startsWith("d:\\nio"));// true
//      boolean endsWith(String path) : 判断是否以 path 路径结束
        System.out.println(path1.endsWith("hello.txt"));// true
//      boolean isAbsolute() : 判断是否是绝对路径
        System.out.println(path1.isAbsolute() + "~");// true~
        System.out.println(path2.isAbsolute() + "~");// false~
//      Path getParent() ：返回Path对象包含整个路径，不包含 Path 对象指定的文件路径
        System.out.println(path1.getParent());// d:\nio\nio1\nio2
        System.out.println(path2.getParent()); // null
//      Path getRoot() ：返回调用 Path 对象的根路径
        System.out.println(path1.getRoot());// d:\
        System.out.println(path2.getRoot());// null
//      Path getFileName() : 返回与调用 Path 对象关联的文件名
        System.out.println(path1.getFileName() + "~");// hello.txt~
        System.out.println(path2.getFileName() + "~");// hello.txt~
//      int getNameCount() : 返回Path 根目录后面元素的数量
//      Path getName(int idx) : 返回指定索引位置 idx 的路径名称
        for (int i = 0; i < path1.getNameCount(); i++) {
            System.out.println(path1.getName(i) + "*****");
            // nio*****
            // nio1*****
            // nio2*****
            // hello.txt*****
        }

//      Path toAbsolutePath() : 作为绝对路径返回调用 Path 对象
        System.out.println(path1.toAbsolutePath());// d:\nio\nio1\nio2\hello.txt
        System.out.println(path2.toAbsolutePath());// E:\学习资料\尚硅谷全套\尚硅谷Java学科全套教程\1.尚硅谷全套JAVA教程--基础阶段\尚硅谷Java核心基础_2019年版---------重点\课件笔记源码资料\5_代码\第2部分：Java高级编程\JavaSenior\day10\hello.txt
//      Path resolve(Path p) :合并两个路径，返回合并后的路径对应的Path对象
        Path path3 = Paths.get("d:\\", "nio");
        Path path4 = Paths.get("nioo\\hi.txt");
        path3 = path3.resolve(path4);
        System.out.println(path3); // d:\nio\nioo\hi.txt

//      File toFile(): 将Path转化为File类的对象
        File file = path1.toFile();//Path--->File的转换

        Path newPath = file.toPath();//File--->Path的转换

    }
```

## 5、Files 类

![img](file:///D:/Documents/My Knowledge/temp/f470ae37-1053-44e9-a266-28be83e95c23/128/index_files/daa2a0c8-868a-4b19-8540-e29d1062a170.png)

![img](file:///D:/Documents/My Knowledge/temp/f470ae37-1053-44e9-a266-28be83e95c23/128/index_files/9e58c0be-5dbf-4cde-b4f8-b98b37911c52.png)

 

```java
/**
 * Files工具类的使用：操作文件或目录的工具类
 * @author shkstart
 * @create 2019 下午 2:44
 */
public class FilesTest {

    @Test
    public void test1() throws IOException{
        Path path1 = Paths.get("d:\\", "hello.txt");
        Path path2 = Paths.get("atguigu.txt");
        Path path22 = Paths.get("d:\\home","atguigu.txt");

//      Path copy(Path src, Path dest, CopyOption … how) : 文件的复制
        //要想复制成功，要求path1对应的物理上的文件存在。path1对应的文件没有要求。
        Files.copy(path1, path2, StandardCopyOption.REPLACE_EXISTING);

//      Path createDirectory(Path path, FileAttribute<?> … attr) : 创建一个目录
        //要想执行成功，要求path对应的物理上的文件目录不存在。一旦存在，抛出异常。
        Path path3 = Paths.get("d:\\nio\\nio1");
//      Files.createDirectory(path3);// 只能一级一级创建，d:\nio必须存在且nio1不存在才不会抛异常

//      Path createFile(Path path, FileAttribute<?> … arr) : 创建一个文件
        //要想执行成功，要求path对应的物理上的文件不存在。一旦存在，抛出异常。
        Path path4 = Paths.get("d:\\nio\\hi.txt");
//      Files.createFile(path4);

//      void delete(Path path) : 删除一个文件/目录，如果不存在，执行报错
//      Files.delete(path4);

//      void deleteIfExists(Path path) : Path对应的文件/目录如果存在，执行删除.如果不存在，正常执行结束
        Files.deleteIfExists(path3);

//      Path move(Path src, Path dest, CopyOption…how) : 将 src 移动到 dest 位置
        //要想执行成功，src对应的物理上的文件需要存在，dest对应的文件没有要求。
        Files.move(path1, path22, StandardCopyOption.ATOMIC_MOVE);

//      long size(Path path) : 返回 path 指定文件的大小
        long size = Files.size(path2);
        System.out.println(size); // 53

    }

    @Test
    public void test2() throws IOException{
        Path path1 = Paths.get("d:\\", "hello.txt");
        Path path2 = Paths.get("atguigu.txt");
//      boolean exists(Path path, LinkOption … opts) : 判断文件是否存在
        System.out.println(Files.exists(path2, LinkOption.NOFOLLOW_LINKS)); // true

//      boolean isDirectory(Path path, LinkOption … opts) : 判断是否是目录
        //不要求此path对应的物理文件存在。
        System.out.println(Files.isDirectory(path1, LinkOption.NOFOLLOW_LINKS)); // false

//      boolean isRegularFile(Path path, LinkOption … opts) : 判断是否是文件

//      boolean isHidden(Path path) : 判断是否是隐藏文件
        //要求此path对应的物理上的文件需要存在。才可判断是否隐藏。否则，抛异常。
        System.out.println(Files.isHidden(path1)); // false

//      boolean isReadable(Path path) : 判断文件是否可读
        System.out.println(Files.isReadable(path1)); // true
//      boolean isWritable(Path path) : 判断文件是否可写
        System.out.println(Files.isWritable(path1)); // true
//      boolean notExists(Path path, LinkOption … opts) : 判断文件是否不存在
        System.out.println(Files.notExists(path1, LinkOption.NOFOLLOW_LINKS));// false
    }

    /**
     * StandardOpenOption.READ:表示对应的Channel是可读的。
     * StandardOpenOption.WRITE：表示对应的Channel是可写的。
     * StandardOpenOption.CREATE：如果要写出的文件不存在，则创建。如果存在，忽略
     * StandardOpenOption.CREATE_NEW：如果要写出的文件不存在，则创建。如果存在，抛异常
     *
     * @author shkstart 邮箱：shkstart@126.com
     * @throws IOException
     */
    @Test
    public void test3() throws IOException{
        Path path1 = Paths.get("d:\\", "hello.txt");

//      InputStream newInputStream(Path path, OpenOption…how):获取 InputStream 对象
        InputStream inputStream = Files.newInputStream(path1, StandardOpenOption.READ);

//      OutputStream newOutputStream(Path path, OpenOption…how) : 获取 OutputStream 对象
        OutputStream outputStream = Files.newOutputStream(path1, StandardOpenOption.WRITE,StandardOpenOption.CREATE);


//      SeekableByteChannel newByteChannel(Path path, OpenOption…how) : 获取与指定文件的连接，how 指定打开方式。
        SeekableByteChannel channel = Files.newByteChannel(path1, StandardOpenOption.READ,StandardOpenOption.WRITE,StandardOpenOption.CREATE);

//      DirectoryStream<Path>  newDirectoryStream(Path path) : 打开 path 指定的目录
        Path path2 = Paths.get("d:\\home\\upload");
        DirectoryStream<Path> directoryStream = Files.newDirectoryStream(path2);
        Iterator<Path> iterator = directoryStream.iterator();
        while(iterator.hasNext()){
            System.out.println(iterator.next());
        }


    }
}
```

## 6、自动资源管理

![img](file:///D:/Documents/My%20Knowledge/temp/f8a75a7d-eb75-473d-9eb0-70b015ebb564/128/index_files/6070f0bf-1d40-4c44-91ed-9e91190ff850.png)

![img](file:///D:/Documents/My%20Knowledge/temp/f8a75a7d-eb75-473d-9eb0-70b015ebb564/128/index_files/a76bc0a7-0a53-44d1-964f-635d4946a815.png)

 

```java
    //自动资源管理：自动关闭实现 AutoCloseable 接口的资源
    @Test
    public void test8(){
        try(FileChannel inChannel = FileChannel.open(Paths.get("1.jpg"), StandardOpenOption.READ);
                FileChannel outChannel = FileChannel.open(Paths.get("2.jpg"), StandardOpenOption.WRITE, StandardOpenOption.CREATE)){

            ByteBuffer buf = ByteBuffer.allocate(1024);
            inChannel.read(buf);

        }catch(IOException e){

        }
    }
```