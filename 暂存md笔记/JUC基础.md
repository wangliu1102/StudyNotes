# 一、Java JUC 简介 

![img](file:///D:/Documents/My%20Knowledge/temp/41183223-c770-47d8-8c4d-3ff3a161df63/128/index_files/fff660e2-defc-4720-a19d-5e300f790ef7.png)

# 二、volatile 关键字-内存可见性

## 1、内存可见性

![img](file:///D:/Documents/My%20Knowledge/temp/41183223-c770-47d8-8c4d-3ff3a161df63/128/index_files/b576997d-86e9-4176-887b-7125b9131687.png)

## 2、volatile 关键字

![img](file:///D:/Documents/My%20Knowledge/temp/41183223-c770-47d8-8c4d-3ff3a161df63/128/index_files/7e15b3ea-696e-46bc-b234-46b333fe20ad.png)

```java
package com.wl.java;

/*
 * 一、volatile 关键字：当多个线程进行操作共享数据时，可以保证内存中的数据可见。
 *                    相较于 synchronized 是一种较为轻量级的同步策略。
 *
 * 注意：
 * 1. volatile 不具备“互斥性”
 * 2. volatile 不能保证变量的“原子性”
 */
public class TestVolatile {

    public static void main(String[] args) {
        ThreadDemo td = new ThreadDemo();
        new Thread(td).start();

        while(true){
            if(td.isFlag()){
                System.out.println("------------------");
                break;
            }
        }

    }

}

class ThreadDemo implements Runnable {

    private volatile boolean flag = false;

    @Override
    public void run() {

        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
        }

        flag = true;

        System.out.println("flag=" + isFlag());

    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

}

```

![img](file:///D:/Documents/My%20Knowledge/temp/41183223-c770-47d8-8c4d-3ff3a161df63/128/index_files/527ba849-e058-43ad-ae89-9667fb9d680e.png)

# 三、原子变量-CAS算法

## 1、CAS 算法

![img](file:///D:/Documents/My%20Knowledge/temp/41183223-c770-47d8-8c4d-3ff3a161df63/128/index_files/9b3c5bc2-18fa-47d1-b29b-0d0eeb8a07c6.png)

## 2、原子变量

![img](file:///D:/Documents/My%20Knowledge/temp/41183223-c770-47d8-8c4d-3ff3a161df63/128/index_files/063bb429-f032-402d-bd05-e1f391d1e36f.png)

```java
package com.wl.java.juc;

import java.util.concurrent.atomic.AtomicInteger;

/*
 * 一、i++ 的原子性问题：i++ 的操作实际上分为三个步骤“读-改-写”
 *        int i = 10;
 *        i = i++; //10
 *
 *        int temp = i;
 *        i = i + 1;
 *        i = temp;
 *
 * 二、原子变量：在 java.util.concurrent.atomic 包下提供了一些原子变量。
 *      1. volatile 保证内存可见性
 *      2. CAS（Compare-And-Swap） 算法保证数据变量的原子性
 *          CAS 算法是硬件对于并发操作的支持
 *          CAS 包含了三个操作数：
 *          ①内存值  V
 *          ②预估值  A
 *          ③更新值  B
 *          当且仅当 V == A 时， V = B; 否则，不会执行任何操作。
 */
public class TestAtomicDemo {

    public static void main(String[] args) {
        AtomicDemo ad = new AtomicDemo();

        for (int i = 0; i < 10; i++) {
            new Thread(ad).start();
        }
    }

}

class AtomicDemo implements Runnable{

//  private volatile int serialNumber = 0;

    private AtomicInteger serialNumber = new AtomicInteger(0);

    @Override
    public void run() {

        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
        }

        System.out.println(getSerialNumber());
    }

    public int getSerialNumber(){
        return serialNumber.getAndIncrement();
    }
}
```

![img](file:///D:/Documents/My%20Knowledge/temp/41183223-c770-47d8-8c4d-3ff3a161df63/128/index_files/24990db6-07b5-44f6-98e7-1c201c107483.png)

## 3、模拟CAS算法

```java
package com.wl.java.juc;

/*
 * 模拟 CAS 算法
 */
public class TestCompareAndSwap {

    public static void main(String[] args) {
        final CompareAndSwap cas = new CompareAndSwap();

        for (int i = 0; i < 10; i++) {
            new Thread(new Runnable() {

                @Override
                public void run() {
                    int expectedValue = cas.get();
                    boolean b = cas.compareAndSet(expectedValue, (int)(Math.random() * 101));
                    System.out.println(b);
                }
            }).start();
        }

    }

}

class CompareAndSwap{
    private int value;

    //获取内存值
    public synchronized int get(){
        return value;
    }

    //比较
    public synchronized int compareAndSwap(int expectedValue, int newValue){
        int oldValue = value;

        if(oldValue == expectedValue){
            this.value = newValue;
        }

        return oldValue;
    }

    //设置
    public synchronized boolean compareAndSet(int expectedValue, int newValue){
        return expectedValue == compareAndSwap(expectedValue, newValue);
    }
}

```





# 四、ConcurrentHashMap 锁分段机制

![img](file:///D:/Documents/My%20Knowledge/temp/41183223-c770-47d8-8c4d-3ff3a161df63/128/index_files/e5c31422-6ea2-4bfd-b7cc-a3eb95b85a5a.png)

```java
package com.wl.java.juc;

import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;

/*
 * CopyOnWriteArrayList/CopyOnWriteArraySet : “写入并复制”
 * 注意：添加操作多时，效率低，因为每次添加时都会进行复制，开销非常的大。并发迭代操作多时可以选择。
 */
public class TestCopyOnWriteArrayList {

    public static void main(String[] args) {
        HelloThread ht = new HelloThread();

        for (int i = 0; i < 10; i++) {
            new Thread(ht).start();
        }
    }

}

class HelloThread implements Runnable{

//  private static List<String> list = Collections.synchronizedList(new ArrayList<String>()); // 报并发修改异常

    private static CopyOnWriteArrayList<String> list = new CopyOnWriteArrayList<>();

    static{
        list.add("AA");
        list.add("BB");
        list.add("CC");
    }

    @Override
    public void run() {

        Iterator<String> it = list.iterator();

        while(it.hasNext()){
            System.out.println(it.next());

            list.add("AA");
        }

    }
}
```





# 五、CountDownLatch 闭锁

![img](file:///D:/Documents/My%20Knowledge/temp/41183223-c770-47d8-8c4d-3ff3a161df63/128/index_files/6c1b9e9e-69d8-458a-a7c4-3ab21a5fbf9e.png)

```java
package com.wl.java.juc;

import java.util.concurrent.CountDownLatch;

/*
 * CountDownLatch ：闭锁，在完成某些运算时，只有其他所有线程的运算全部完成，当前运算才继续执行
 */
public class TestCountDownLatch {

    public static void main(String[] args) {
        final CountDownLatch latch = new CountDownLatch(50);// latch参数设置和线程数相同
        LatchDemo ld = new LatchDemo(latch);

        long start = System.currentTimeMillis();

        for (int i = 0; i < 50; i++) {
            new Thread(ld).start();
        }

        try {
            latch.await(); // 主线程等待
        } catch (InterruptedException e) {
        }

        long end = System.currentTimeMillis();

        System.out.println("耗费时间为：" + (end - start));
    }

}

class LatchDemo implements Runnable {

    private CountDownLatch latch;

    public LatchDemo(CountDownLatch latch) {
        this.latch = latch;
    }

    @Override
    public void run() {

        try {
            for (int i = 0; i < 50000; i++) {
                if (i % 2 == 0) {
                    System.out.println(i);
                }
            }
        } finally {
            latch.countDown(); // latch的参数递减1
        }
    }
}
```





# 六、实现 Callable 接口

![img](file:///D:/Documents/My%20Knowledge/temp/41183223-c770-47d8-8c4d-3ff3a161df63/128/index_files/0917293d-4f86-4b7a-bd1d-0f7395191771.png)

```java
package com.wl.java.juc2;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/*
 * 一、创建执行线程的方式三：实现 Callable 接口。 相较于实现 Runnable 接口的方式，方法可以有返回值，并且可以抛出异常。
 *
 * 二、执行 Callable 方式，需要 FutureTask 实现类的支持，用于接收运算结果。  FutureTask 是  Future 接口的实现类
 */
public class TestCallable {

    public static void main(String[] args) {
        ThreadDemo td = new ThreadDemo();

        //1.执行 Callable 方式，需要 FutureTask 实现类的支持，用于接收运算结果。
        FutureTask<Integer> result = new FutureTask<>(td);

        new Thread(result).start();

        //2.接收线程运算后的结果
        try {
            Integer sum = result.get();  //FutureTask 可用于闭锁，线程执行完才能接收线程运算后的结果
            System.out.println(sum); // 705082704
            System.out.println("------------------------------------");
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }

}

class ThreadDemo implements Callable<Integer>{

    @Override
    public Integer call() throws Exception {
        int sum = 0;

        for (int i = 0; i <= 100000; i++) {
            sum += i;
        }

        return sum;
    }

}

/*class ThreadDemo implements Runnable{

    @Override
    public void run() {
    }

}*/
```



# 七、Lock 同步锁

## 1、显示锁Lock

![img](file:///D:/Documents/My%20Knowledge/temp/41183223-c770-47d8-8c4d-3ff3a161df63/128/index_files/88c6e1a4-c063-4bef-864f-41f32470c90d.png)

```java
package com.wl.java.juc2;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/*
 * 一、用于解决多线程安全问题的方式：
 *
 * synchronized:隐式锁
 * 1. 同步代码块
 *
 * 2. 同步方法
 *
 * jdk 1.5 后：
 * 3. 同步锁 Lock
 * 注意：是一个显示锁，需要通过 lock() 方法上锁，必须通过 unlock() 方法进行释放锁
 */
public class TestLock {

    public static void main(String[] args) {
        Ticket ticket = new Ticket();

        new Thread(ticket, "1号窗口").start();
        new Thread(ticket, "2号窗口").start();
        new Thread(ticket, "3号窗口").start();
    }

}

class Ticket implements Runnable{

    private int tick = 100;

    private Lock lock = new ReentrantLock();

    @Override
    public void run() {
        while(true){

            lock.lock(); //上锁

            try{
                if(tick > 0){
                    try {
                        Thread.sleep(200);
                    } catch (InterruptedException e) {
                    }

                    System.out.println(Thread.currentThread().getName() + " 完成售票，余票为：" + --tick);
                }
            }finally{
                lock.unlock(); //释放锁
            }
        }
    }
}
```





##  

## 2、生产者消费者案例-虚假唤醒(synchronized)

```java
package com.wl.java.juc2;

/*
 * 生产者和消费者案例
 */
public class TestProductorAndConsumer {

    public static void main(String[] args) {
        Clerk clerk = new Clerk();

        Productor pro = new Productor(clerk);
        Consumer cus = new Consumer(clerk);

        new Thread(pro, "生产者 A").start();
        new Thread(cus, "消费者 B").start();

        new Thread(pro, "生产者 C").start();
        new Thread(cus, "消费者 D").start();
    }

}

//店员
class Clerk{
    private int product = 0;

    //进货
    public synchronized void get(){//循环次数：0
        while(product >= 1){//为了避免虚假唤醒问题，应该总是使用在循环中
            System.out.println("产品已满！");

            try {
                this.wait();
            } catch (InterruptedException e) {
            }

        }

        System.out.println(Thread.currentThread().getName() + " : " + ++product);
        this.notifyAll();
    }

    //卖货
    public synchronized void sale(){//product = 0; 循环次数：0
        while(product <= 0){
            System.out.println("缺货！");

            try {
                this.wait();
            } catch (InterruptedException e) {
            }
        }

        System.out.println(Thread.currentThread().getName() + " : " + --product);
        this.notifyAll();
    }
}

//生产者
class Productor implements Runnable{
    private Clerk clerk;

    public Productor(Clerk clerk) {
        this.clerk = clerk;
    }

    @Override
    public void run() {
        for (int i = 0; i < 20; i++) {
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
            }

            clerk.get();
        }
    }
}

//消费者
class Consumer implements Runnable{
    private Clerk clerk;

    public Consumer(Clerk clerk) {
        this.clerk = clerk;
    }

    @Override
    public void run() {
        for (int i = 0; i < 20; i++) {
            clerk.sale();
        }
    }
}

```

![img](file:///D:/Documents/My%20Knowledge/temp/41183223-c770-47d8-8c4d-3ff3a161df63/128/index_files/be41747a-15d0-4d9d-b477-974c6921ce43.png)

## 3、生产者消费者案例-虚假唤醒(lock)

```java
package com.wl.java.juc2;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/*
 * 生产者消费者案例：
 */
public class TestProductorAndConsumerForLock {

    public static void main(String[] args) {
        Clerk clerk = new Clerk();

        Productor pro = new Productor(clerk);
        Consumer con = new Consumer(clerk);

        new Thread(pro, "生产者 A").start();
        new Thread(con, "消费者 B").start();

         new Thread(pro, "生产者 C").start();
         new Thread(con, "消费者 D").start();
    }

}

class Clerk {
    private int product = 0;

    private Lock lock = new ReentrantLock();
    private Condition condition = lock.newCondition();

    // 进货
    public void get() {
        lock.lock();

        try {
            while (product >= 1) { // 为了避免虚假唤醒，应该总是使用在循环中。
                System.out.println("产品已满！");

                try {
                    condition.await();
                } catch (InterruptedException e) {
                }

            }
            System.out.println(Thread.currentThread().getName() + " : "
                    + ++product);

            condition.signalAll();
        } finally {
            lock.unlock();
        }

    }

    // 卖货
    public void sale() {
        lock.lock();

        try {
            while (product <= 0) {
                System.out.println("缺货！");

                try {
                    condition.await();
                } catch (InterruptedException e) {
                }
            }

            System.out.println(Thread.currentThread().getName() + " : "
                    + --product);

            condition.signalAll();

        } finally {
            lock.unlock();
        }
    }
}

// 生产者
class Productor implements Runnable {

    private Clerk clerk;

    public Productor(Clerk clerk) {
        this.clerk = clerk;
    }

    @Override
    public void run() {
        for (int i = 0; i < 20; i++) {
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            clerk.get();
        }
    }
}

// 消费者
class Consumer implements Runnable {

    private Clerk clerk;

    public Consumer(Clerk clerk) {
        this.clerk = clerk;
    }

    @Override
    public void run() {
        for (int i = 0; i < 20; i++) {
            clerk.sale();
        }
    }

}

```

![img](file:///D:/Documents/My%20Knowledge/temp/41183223-c770-47d8-8c4d-3ff3a161df63/128/index_files/02d2c822-e640-47ba-b10a-0c8e854ebb68.png)

# 八、Condition 控制线程通信

![img](file:///D:/Documents/My%20Knowledge/temp/41183223-c770-47d8-8c4d-3ff3a161df63/128/index_files/9740ad42-c2cf-4700-921b-a790362a878d.png)

# 九、线程按序交替

![img](file:///D:/Documents/My%20Knowledge/temp/41183223-c770-47d8-8c4d-3ff3a161df63/128/index_files/76d6da0b-15d5-4ec1-ab00-d9cf85cd38b0.png)

```java
package com.wl.java.juc2;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/*
 * 编写一个程序，开启 3 个线程，这三个线程的 ID 分别为 A、B、C，每个线程将自己的 ID 在屏幕上打印 10 遍，要求输出的结果必须按顺序显示。
 *  如：ABCABCABC…… 依次递归
 */
public class TestABCAlternate {

    public static void main(String[] args) {
        AlternateDemo ad = new AlternateDemo();

        new Thread(new Runnable() {
            @Override
            public void run() {

                for (int i = 1; i <= 20; i++) {
                    ad.loopA(i);
                }

            }
        }, "A").start();

        new Thread(new Runnable() {
            @Override
            public void run() {

                for (int i = 1; i <= 20; i++) {
                    ad.loopB(i);
                }

            }
        }, "B").start();

        new Thread(new Runnable() {
            @Override
            public void run() {

                for (int i = 1; i <= 20; i++) {
                    ad.loopC(i);

                    System.out.println("-----------------------------------");
                }

            }
        }, "C").start();
    }

}

class AlternateDemo{

    private int number = 1; //当前正在执行线程的标记

    private Lock lock = new ReentrantLock();
    private Condition condition1 = lock.newCondition();
    private Condition condition2 = lock.newCondition();
    private Condition condition3 = lock.newCondition();

    /**
     * @param totalLoop : 循环第几轮
     */
    public void loopA(int totalLoop){
        lock.lock();

        try {
            //1. 判断
            if(number != 1){
                condition1.await();
            }

            //2. 打印
            for (int i = 1; i <= 1; i++) {
                System.out.println(Thread.currentThread().getName() + "\t" + i + "\t" + totalLoop);
            }

            //3. 唤醒
            number = 2;
            condition2.signal();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public void loopB(int totalLoop){
        lock.lock();

        try {
            //1. 判断
            if(number != 2){
                condition2.await();
            }

            //2. 打印
            for (int i = 1; i <= 1; i++) {
                System.out.println(Thread.currentThread().getName() + "\t" + i + "\t" + totalLoop);
            }

            //3. 唤醒
            number = 3;
            condition3.signal();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public void loopC(int totalLoop){
        lock.lock();

        try {
            //1. 判断
            if(number != 3){
                condition3.await();
            }

            //2. 打印
            for (int i = 1; i <= 1; i++) {
                System.out.println(Thread.currentThread().getName() + "\t" + i + "\t" + totalLoop);
            }

            //3. 唤醒
            number = 1;
            condition1.signal();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

}

```

![img](file:///D:/Documents/My%20Knowledge/temp/41183223-c770-47d8-8c4d-3ff3a161df63/128/index_files/d50be2b7-0854-43f6-83fd-64bb93f4020d.png)

# 十、ReadWriteLock 读写锁

![img](file:///D:/Documents/My%20Knowledge/temp/41183223-c770-47d8-8c4d-3ff3a161df63/128/index_files/d215ef8d-c525-4946-b4e9-fc4a650e8fae.png)

```java
package com.wl.java.juc2;

import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/*
 * 1. ReadWriteLock : 读写锁
 *
 * 写写/读写 需要“互斥”
 * 读读 不需要互斥
 *
 */
public class TestReadWriteLock {

    public static void main(String[] args) {
        ReadWriteLockDemo rw = new ReadWriteLockDemo();

        new Thread(new Runnable() {

            @Override
            public void run() {
                rw.set((int)(Math.random() * 101));
            }
        }, "Write:").start();


        for (int i = 0; i < 100; i++) {
            new Thread(new Runnable() {

                @Override
                public void run() {
                    rw.get();
                }
            }).start();
        }
    }

}

class ReadWriteLockDemo{

    private int number = 0;

    private ReadWriteLock lock = new ReentrantReadWriteLock();

    //读
    public void get(){
        lock.readLock().lock(); //上锁

        try{
            System.out.println(Thread.currentThread().getName() + " : " + number);
        }finally{
            lock.readLock().unlock(); //释放锁
        }
    }

    //写
    public void set(int number){
        lock.writeLock().lock();

        try{
            System.out.println(Thread.currentThread().getName());
            this.number = number;
        }finally{
            lock.writeLock().unlock();
        }
    }
}

```

![img](file:///D:/Documents/My%20Knowledge/temp/41183223-c770-47d8-8c4d-3ff3a161df63/128/index_files/37029f21-9b75-4055-b5ee-72b951191067.png)

# 十一、线程八锁

![img](file:///D:/Documents/My%20Knowledge/temp/41183223-c770-47d8-8c4d-3ff3a161df63/128/index_files/14e58652-008e-455e-9326-32b5ca52e980.png)

```java
package com.wl.java.juc2;

/*
 * 题目：判断打印的 "one" or "two" ？
 *
 * 1. 两个普通同步方法，两个线程，标准打印， 打印? //one  two
 * 2. 新增 Thread.sleep() 给 getOne() ,打印? //one  two
 * 3. 新增普通方法 getThree() , 打印? //three  one   two
 * 4. 两个普通同步方法，两个 Number 对象，打印?  //two  one
 * 5. 修改 getOne() 为静态同步方法，打印?  //two   one
 * 6. 修改两个方法均为静态同步方法，一个 Number 对象?  //one   two
 * 7. 一个静态同步方法，一个非静态同步方法，两个 Number 对象?  //two  one
 * 8. 两个静态同步方法，两个 Number 对象?   //one  two
 *
 * 线程八锁的关键：
 * ① 非静态方法的锁默认为  this,  静态方法的锁为 对应的 Class 实例
 * ② 某一个时刻内，只能有一个线程持有锁，无论几个方法。
 */
public class TestThread8Monitor {

    public static void main(String[] args) {
        Number number = new Number();
        Number number2 = new Number();

        new Thread(new Runnable() {
            @Override
            public void run() {
                number.getOne();
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
//              number.getTwo();
                number2.getTwo();
            }
        }).start();

        /*new Thread(new Runnable() {
            @Override
            public void run() {
                number.getThree();
            }
        }).start();*/

    }

}

class Number{

    public static synchronized void getOne(){//Number.class
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
        }

        System.out.println("one");
    }

    public synchronized void getTwo(){//this
        System.out.println("two");
    }

    public void getThree(){
        System.out.println("three");
    }

}

```



# 十二、线程池

![img](file:///D:/Documents/My%20Knowledge/temp/41183223-c770-47d8-8c4d-3ff3a161df63/128/index_files/5281e1dc-ce00-4e8d-bcaa-45eae93e5dfc.png)

```java
package com.wl.java.juc2;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/*
 * 一、线程池：提供了一个线程队列，队列中保存着所有等待状态的线程。避免了创建与销毁额外开销，提高了响应的速度。
 *
 * 二、线程池的体系结构：
 *  java.util.concurrent.Executor : 负责线程的使用与调度的根接口
 *      |--**ExecutorService 子接口: 线程池的主要接口
 *          |--ThreadPoolExecutor 线程池的实现类
 *          |--ScheduledExecutorService 子接口：负责线程的调度
 *              |--ScheduledThreadPoolExecutor ：继承 ThreadPoolExecutor， 实现 ScheduledExecutorService
 *
 * 三、工具类 : Executors
 * ExecutorService newFixedThreadPool() : 创建固定大小的线程池
 * ExecutorService newCachedThreadPool() : 缓存线程池，线程池的数量不固定，可以根据需求自动的更改数量。
 * ExecutorService newSingleThreadExecutor() : 创建单个线程池。线程池中只有一个线程
 *
 * ScheduledExecutorService newScheduledThreadPool() : 创建固定大小的线程，可以延迟或定时的执行任务。
 */
public class TestThreadPool {

    public static void main(String[] args) throws Exception {
        //1. 创建线程池
        ExecutorService pool = Executors.newFixedThreadPool(5);

        List<Future<Integer>> list = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            Future<Integer> future = pool.submit(new Callable<Integer>(){

                @Override
                public Integer call() throws Exception {
                    int sum = 0;

                    for (int i = 0; i <= 100; i++) {
                        sum += i;
                    }

                    return sum;
                }

            });

            list.add(future);
        }

        pool.shutdown();

        for (Future<Integer> future : list) {
            System.out.println(future.get());
        }



        /*ThreadPoolDemo tpd = new ThreadPoolDemo();

        //2. 为线程池中的线程分配任务
        for (int i = 0; i < 10; i++) {
            pool.submit(tpd);
        }

        //3. 关闭线程池
        pool.shutdown();*/
    }

//  new Thread(tpd).start();
//  new Thread(tpd).start();

}

class ThreadPoolDemo implements Runnable{

    private int i = 0;

    @Override
    public void run() {
        while(i <= 100){
            System.out.println(Thread.currentThread().getName() + " : " + i++);
        }
    }

}

```

![img](file:///D:/Documents/My%20Knowledge/temp/41183223-c770-47d8-8c4d-3ff3a161df63/128/index_files/91061dd4-c701-45d8-97d9-4a79b5ba2f1e.png)

# 十三、线程调度

![img](file:///D:/Documents/My%20Knowledge/temp/41183223-c770-47d8-8c4d-3ff3a161df63/128/index_files/30655e9f-8d5d-4ae3-8d8f-0d98a28a877b.png)

```java
package com.wl.java.juc2;

import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/*
 * 一、线程池：提供了一个线程队列，队列中保存着所有等待状态的线程。避免了创建与销毁额外开销，提高了响应的速度。
 *
 * 二、线程池的体系结构：
 *  java.util.concurrent.Executor : 负责线程的使用与调度的根接口
 *      |--**ExecutorService 子接口: 线程池的主要接口
 *          |--ThreadPoolExecutor 线程池的实现类
 *          |--ScheduledExecutorService 子接口：负责线程的调度
 *              |--ScheduledThreadPoolExecutor ：继承 ThreadPoolExecutor， 实现 ScheduledExecutorService
 *
 * 三、工具类 : Executors
 * ExecutorService newFixedThreadPool() : 创建固定大小的线程池
 * ExecutorService newCachedThreadPool() : 缓存线程池，线程池的数量不固定，可以根据需求自动的更改数量。
 * ExecutorService newSingleThreadExecutor() : 创建单个线程池。线程池中只有一个线程
 *
 * ScheduledExecutorService newScheduledThreadPool() : 创建固定大小的线程，可以延迟或定时的执行任务。
 */
public class TestScheduledThreadPool {

    public static void main(String[] args) throws Exception {
        ScheduledExecutorService pool = Executors.newScheduledThreadPool(5);

        for (int i = 0; i < 5; i++) {
            Future<Integer> result = pool.schedule(new Callable<Integer>(){

                @Override
                public Integer call() throws Exception {
                    int num = new Random().nextInt(100);//生成随机数
                    System.out.println(Thread.currentThread().getName() + " : " + num);
                    return num;
                }

            }, 1, TimeUnit.SECONDS);

            System.out.println(result.get());
        }

        pool.shutdown();
    }
}
```

![img](file:///D:/Documents/My%20Knowledge/temp/41183223-c770-47d8-8c4d-3ff3a161df63/128/index_files/b68fac8b-1139-4096-9841-77f5ae519717.png)

# 十四、ForkJoinPool 分支/合并框架 工作窃取

## 1、Fork/Join框架

![img](file:///D:/Documents/My%20Knowledge/temp/41183223-c770-47d8-8c4d-3ff3a161df63/128/index_files/edd8a8c2-04ed-466e-a361-4931a87fe3a1.png)

## 2、Fork/Join框架与线程池的区别

![img](file:///D:/Documents/My%20Knowledge/temp/41183223-c770-47d8-8c4d-3ff3a161df63/128/index_files/60e3a588-956e-488f-9b79-ff5498566f23.png)

 

```java
package com.wl.java8.test;
import java.util.concurrent.RecursiveTask;
public class ForkJoinCalculate extends RecursiveTask<Long>{
   /**
     *
     */
    private static final long serialVersionUID = 13475679780L;
    private long start;
    private long end;
    private static final long THRESHOLD = 10000L; //临界值

    public ForkJoinCalculate(long start, long end) {
        this.start = start;
        this.end = end;
    }

    @Override
    protected Long compute() {
        long length = end - start;
        if (length <= THRESHOLD) {
            long sum = 0;
            for (long i = start; i <= end; i++) {
                sum += i;
            }
            return sum;
        } else {
            long middle = (start + end) / 2;
            ForkJoinCalculate left = new ForkJoinCalculate(start, middle);
            left.fork(); //拆分，并将该子任务压入线程队列
            ForkJoinCalculate right = new ForkJoinCalculate(middle + 1, end);
            right.fork();
            return left.join() + right.join();
        }
    }
}
  /**
  * TestForkJoin 计算0-10000000000L的累加
  */
  // java 7 ForkJoin框架
  @Test
  public void test131(){
    long start = System.currentTimeMillis();
    ForkJoinPool pool = new ForkJoinPool();
    ForkJoinTask<Long> task = new ForkJoinCalculate(0L, 10000000000L);
    long sum = pool.invoke(task);
    System.out.println(sum); // -5340232216128654848
    long end = System.currentTimeMillis();
    System.out.println("耗费的时间为: " + (end - start)); //3153-1923-2397
 }
  // 普通 for
  @Test
  public void test132(){
    long start = System.currentTimeMillis();
    long sum = 0L;
    for (long i = 0L; i <= 10000000000L; i++) {
      sum += i;
   }
    System.out.println(sum); // -5340232216128654848
    long end = System.currentTimeMillis();
    System.out.println("耗费的时间为: " + (end - start)); //3023-3086-4719
 }
  // java8 提供并行流,效率更大
  @Test
  public void test133(){
    long start = System.currentTimeMillis();
    Long sum = LongStream.rangeClosed(0L, 10000000000L)
       .parallel()
       .sum();
    System.out.println(sum); // -5340232216128654848
    long end = System.currentTimeMillis();
    System.out.println("耗费的时间为: " + (end - start)); //1294-2188-1883
 }
```