# 一、字符串相关的类

## 1、String 的特性

![img](file:///D:/Documents/My Knowledge/temp/63c63919-a4f2-4d0f-8b62-0640b693b965/128/index_files/111e64c8-5ae6-405b-9a80-abd61a231d3e.png)

```java
String:字符串，使用一对""引起来表示。
1.String声明为final的，不可被继承
2.String 实现了Serializable接口：表示字符串是支持序列化的。
        实现了Comparable接口：表示String可以比较大小
3.String内部定义了final char[] value用于存储字符串数据
4.String:代表不可变的字符序列。简称：不可变性。
    体现：1.当对字符串重新赋值时，需要重写指定内存区域赋值，不能使用原有的value进行赋值。
         2. 当对现有的字符串进行连接操作时，也需要重新指定内存区域赋值，不能使用原有的value进行赋值。
         3. 当调用String的replace()方法修改指定字符或字符串时，也需要重新指定内存区域赋值，不能使用原有的value进行赋值。
5.通过字面量的方式（区别于new）给一个字符串赋值，此时的字符串值声明在字符串常量池中。
6.字符串常量池中是不会存储相同内容的字符串的。
```

![img](file:///D:/Documents/My Knowledge/temp/63c63919-a4f2-4d0f-8b62-0640b693b965/128/index_files/e9e3d325-3f4a-49f1-86b4-4e0b3a88d6a2.png)

## 2、String不同实例化方式的对比

String的实例化方式：

- -  方式一：通过字面量定义的方式
  -  方式二：通过new + 构造器的方式

```java
//通过字面量定义的方式：此时的s1和s2的数据javaEE声明在方法区中的字符串常量池中。
String s1 = "javaEE";
String s2 = "javaEE";
//通过new + 构造器的方式:此时的s3和s4保存的地址值，是数据在堆空间中开辟空间以后对应的地址值。
String s3 = new String("javaEE");
String s4 = new String("javaEE");
```

![img](file:///D:/Documents/My Knowledge/temp/63c63919-a4f2-4d0f-8b62-0640b693b965/128/index_files/cbbc575c-0d40-44ba-89cd-91c2e5d0b65b.png)

![img](file:///D:/Documents/My Knowledge/temp/63c63919-a4f2-4d0f-8b62-0640b693b965/128/index_files/1e88b63c-de0c-4e7d-9dd5-afdea3366635.png)

![img](file:///D:/Documents/My Knowledge/temp/63c63919-a4f2-4d0f-8b62-0640b693b965/128/index_files/d7319a3e-88d4-4d5c-9a84-94c7bd208343.png)

![img](file:///D:/Documents/My Knowledge/temp/63c63919-a4f2-4d0f-8b62-0640b693b965/128/index_files/2c7a260d-c21e-4d96-84cb-034a2894e6dc.png)

![img](file:///D:/Documents/My Knowledge/temp/63c63919-a4f2-4d0f-8b62-0640b693b965/128/index_files/8cae2079-a49f-4515-87f9-457163baa639.png)

![img](file:///D:/Documents/My Knowledge/temp/63c63919-a4f2-4d0f-8b62-0640b693b965/128/index_files/b8ead519-6944-4cfd-8044-53bcd2f9bacd.png)

## 3、String不同拼接操作的对比

![img](file:///D:/Documents/My Knowledge/temp/63c63919-a4f2-4d0f-8b62-0640b693b965/128/index_files/98bd0cf1-11d6-4cab-a91c-75b39c648498.png)

![img](file:///D:/Documents/My Knowledge/temp/63c63919-a4f2-4d0f-8b62-0640b693b965/128/index_files/cefa79fc-b3e8-4327-af2d-f0734b651c6b.png)

```java
    @Test
    public void test4(){
        String s1 = "javaEEhadoop";
        String s2 = "javaEE";
        String s3 = s2 + "hadoop";
        System.out.println(s1 == s3);//false

        final String s4 = "javaEE";//s4:常量,加了final后变为常量，常量与常量的拼接结果在常量池。且常量池中不会存在相同内容的常量。
        String s5 = s4 + "hadoop";
        System.out.println(s1 == s5);//true

    }
```



## 4、String 常用方法

![img](file:///D:/Documents/My Knowledge/temp/63c63919-a4f2-4d0f-8b62-0640b693b965/128/index_files/93358d23-c5e3-4d3a-aa56-7628c366f1f0.png)

![img](file:///D:/Documents/My Knowledge/temp/63c63919-a4f2-4d0f-8b62-0640b693b965/128/index_files/8bad9c5d-1e61-4cbb-b7b8-e31338819744.png)

![img](file:///D:/Documents/My Knowledge/temp/63c63919-a4f2-4d0f-8b62-0640b693b965/128/index_files/ef0addc7-696c-4fbb-839e-ae03df996883.png)

![img](file:///D:/Documents/My Knowledge/temp/63c63919-a4f2-4d0f-8b62-0640b693b965/128/index_files/39436bf8-cb73-430d-9d3e-72f940b9ea93.png)

![img](file:///D:/Documents/My Knowledge/temp/63c63919-a4f2-4d0f-8b62-0640b693b965/128/index_files/6685c950-4e12-46eb-bd63-ac44a275f9ee.png)

## 5、String与基本数据类型转换

![img](file:///D:/Documents/My Knowledge/temp/63c63919-a4f2-4d0f-8b62-0640b693b965/128/index_files/3ae67f68-9a03-413d-b7b8-5f9026bad4f2.png)

```java
/*
    复习：
    String 与基本数据类型、包装类之间的转换。

    String --> 基本数据类型、包装类：调用包装类的静态方法：parseXxx(str)
    基本数据类型、包装类 --> String:调用String重载的valueOf(xxx)

     */
    @Test
    public void test1(){
        String str1 = "123";
//        int num = (int)str1;//错误的
        int num = Integer.parseInt(str1);

        String str2 = String.valueOf(num);//"123"
        String str3 = num + "";

        System.out.println(str1 == str3); // false
    }
```



## 6、String 与字符数组转换

![img](file:///D:/Documents/My Knowledge/temp/63c63919-a4f2-4d0f-8b62-0640b693b965/128/index_files/4ddb80b5-69e2-46e5-9254-f75711bb8d74.png)

```java
    /*
    String 与 char[]之间的转换

    String --> char[]:调用String的toCharArray()
    char[] --> String:调用String的构造器
     */
    @Test
    public void test2(){
        String str1 = "abc123";  //题目： a21cb3

        char[] charArray = str1.toCharArray();
        for (int i = 0; i < charArray.length; i++) {
            System.out.println(charArray[i]);
            // a
            // b
            // c
            // 1
            // 2
            // 3
        }

        char[] arr = new char[]{'h','e','l','l','o'};
        String str2 = new String(arr);
        System.out.println(str2); // hello
    }
```



## 7、String 与字节数组转换

![img](file:///D:/Documents/My Knowledge/temp/63c63919-a4f2-4d0f-8b62-0640b693b965/128/index_files/b641993b-5462-4f57-b18d-6c10345632ec.png)

```java
    /*
    String 与 byte[]之间的转换
    编码：String --> byte[]:调用String的getBytes()
    解码：byte[] --> String:调用String的构造器

    编码：字符串 -->字节  (看得懂 --->看不懂的二进制数据)
    解码：编码的逆过程，字节 --> 字符串 （看不懂的二进制数据 ---> 看得懂）

    说明：解码时，要求解码使用的字符集必须与编码时使用的字符集一致，否则会出现乱码。
     */
    @Test
    public void test3() throws UnsupportedEncodingException {
        String str1 = "abc123中国";
        byte[] bytes = str1.getBytes();//使用默认的字符集，进行编码。
        System.out.println(Arrays.toString(bytes)); // [97, 98, 99, 49, 50, 51, -28, -72, -83, -27, -101, -67]

        byte[] gbks = str1.getBytes("gbk");//使用gbk字符集进行编码。
        System.out.println(Arrays.toString(gbks)); // [97, 98, 99, 49, 50, 51, -42, -48, -71, -6]

        System.out.println("******************");

        String str2 = new String(bytes);//使用默认的字符集，进行解码。
        System.out.println(str2); // abc123中国

        String str3 = new String(gbks);
        System.out.println(str3);//出现乱码。原因：编码集和解码集不一致！ abc123�й�


        String str4 = new String(gbks, "gbk");
        System.out.println(str4);//没有出现乱码。原因：编码集和解码集一致！ abc123中国
    }
```



## 8、StringBuffer类

![img](file:///D:/Documents/My Knowledge/temp/63c63919-a4f2-4d0f-8b62-0640b693b965/128/index_files/012f5171-4f7f-462e-9c04-76b004b4be45.jpg)

![img](file:///D:/Documents/My Knowledge/temp/63c63919-a4f2-4d0f-8b62-0640b693b965/128/index_files/d70a97ee-8716-409b-9bcf-845a7a37bb63.png)

![img](file:///D:/Documents/My Knowledge/temp/63c63919-a4f2-4d0f-8b62-0640b693b965/128/index_files/e12b80ac-431d-49ab-a4f8-d60bc57049e8.png)

![img](file:///D:/Documents/My Knowledge/temp/63c63919-a4f2-4d0f-8b62-0640b693b965/128/index_files/a95373ae-44cc-423f-9948-8e739187e6e4.png)

## 9、StringBuilder类

![img](file:///D:/Documents/My Knowledge/temp/63c63919-a4f2-4d0f-8b62-0640b693b965/128/index_files/3e53cbc7-9900-45b9-ac3b-77e605e79ec9.png)

# 二、JDK 8之前的日期时间API

![img](file:///D:/Documents/My Knowledge/temp/63c63919-a4f2-4d0f-8b62-0640b693b965/128/index_files/372692d4-ca79-4824-9981-2c7a1f8c167d.jpg)

## 1、java.lang.System类

![img](file:///D:/Documents/My Knowledge/temp/63c63919-a4f2-4d0f-8b62-0640b693b965/128/index_files/54dc077f-5d7d-4041-9977-d32d6878a803.png)

```java
    @Test
    public void test1(){
        long time = System.currentTimeMillis();
        //返回当前时间与1970年1月1日0时0分0秒之间以毫秒为单位的时间差。
        //称为时间戳
        System.out.println(time); // 1595381587581
    }
```



## 2、java.util.Date类

![img](file:///D:/Documents/My Knowledge/temp/63c63919-a4f2-4d0f-8b62-0640b693b965/128/index_files/332bad3f-0a16-433a-8c7d-e643878a392e.png)

```java
   @Test
    public void test2(){
        //构造器一：Date()：创建一个对应当前时间的Date对象
        Date date1 = new Date();
        System.out.println(date1.toString());//Wed Jul 22 09:34:42 CST 2020

        System.out.println(date1.getTime());//1595381682661

        //构造器二：创建指定毫秒数的Date对象
        Date date2 = new Date(155030620410L);
        System.out.println(date2.toString());//Sat Nov 30 16:03:40 CST 1974

        //创建java.sql.Date对象
        java.sql.Date date3 = new java.sql.Date(35235325345L);
        System.out.println(date3);//1971-02-13

        //如何将java.util.Date对象转换为java.sql.Date对象
        //情况一：
//        Date date4 = new java.sql.Date(2343243242323L);
//        java.sql.Date date5 = (java.sql.Date) date4;
        //情况二：
        Date date6 = new Date();
        java.sql.Date date7 = new java.sql.Date(date6.getTime());
        System.out.println(date7.toString()); // 2020-07-22

    }
```



## 3、 java.text.SimpleDateFormat类

![img](file:///D:/Documents/My Knowledge/temp/63c63919-a4f2-4d0f-8b62-0640b693b965/128/index_files/338e486a-0707-4ddf-980a-7d18886864a8.png)

![img](file:///D:/Documents/My Knowledge/temp/63c63919-a4f2-4d0f-8b62-0640b693b965/128/index_files/43af1f98-0bfe-494e-824f-971cffcbf351.png)

```java
    @Test
    public void testSimpleDateFormat() throws ParseException {
        //实例化SimpleDateFormat:使用默认的构造器
        SimpleDateFormat sdf = new SimpleDateFormat();

        //格式化：日期 --->字符串
        Date date = new Date();
        System.out.println(date); // Wed Jul 22 09:36:17 CST 2020

        String format = sdf.format(date);
        System.out.println(format); // 20-7-22 上午9:3

        //解析：格式化的逆过程，字符串 ---> 日期
        String str = "19-12-18 上午11:43";
        Date date1 = sdf.parse(str);
        System.out.println(date1);// Wed Dec 18 11:43:00 CST 201

        //*************按照指定的方式格式化和解析：调用带参的构造器*****************
//        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyyy.MMMMM.dd GGG hh:mm aaa");
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        //格式化
        String format1 = sdf1.format(date);
        System.out.println(format1);//2020-07-22 09:36:17
        //解析:要求字符串必须是符合SimpleDateFormat识别的格式(通过构造器参数体现),
        //否则，抛异常
        Date date2 = sdf1.parse("2020-02-18 11:48:27");
        System.out.println(date2); // Tue Feb 18 11:48:27 CST 2020
    }
```



## 4、java.util.Calendar( 日历)类

![img](file:///D:/Documents/My Knowledge/temp/63c63919-a4f2-4d0f-8b62-0640b693b965/128/index_files/babf7359-105c-411a-83f0-c2cb20843b96.png)

```java
    @Test
    public void testCalendar(){
        //1.实例化
        //方式一：创建其子类（GregorianCalendar）的对象
        //方式二：调用其静态方法getInstance()
        Calendar calendar = Calendar.getInstance();
//        System.out.println(calendar.getClass());

        //2.常用方法
        //get()
        int days = calendar.get(Calendar.DAY_OF_MONTH);
        System.out.println(days); // 22
        System.out.println(calendar.get(Calendar.DAY_OF_YEAR));//204

        //set()
        //calendar可变性
        calendar.set(Calendar.DAY_OF_MONTH,22);
        days = calendar.get(Calendar.DAY_OF_MONTH);
        System.out.println(days);//22

        //add()
        calendar.add(Calendar.DAY_OF_MONTH,-3);
        days = calendar.get(Calendar.DAY_OF_MONTH);
        System.out.println(days);//19

        //getTime():日历类---> Date
        Date date = calendar.getTime();
        System.out.println(date);//Sun Jul 19 09:38:10 CST 2020

        //setTime():Date ---> 日历类
        Date date1 = new Date();
        calendar.setTime(date1);
        days = calendar.get(Calendar.DAY_OF_MONTH);
        System.out.println(days);//22

    }
```



# 三、JDK 8中新日期时间API

## 1、新日期时间API出现的背景

![img](file:///D:/Documents/My Knowledge/temp/63c63919-a4f2-4d0f-8b62-0640b693b965/128/index_files/4498b627-0180-4aac-81e8-570a48407615.png)

![img](file:///D:/Documents/My Knowledge/temp/63c63919-a4f2-4d0f-8b62-0640b693b965/128/index_files/88ebfe7a-ea6e-4481-b24c-2da4bf1a1bd9.png)

![img](file:///D:/Documents/My Knowledge/temp/63c63919-a4f2-4d0f-8b62-0640b693b965/128/index_files/e5e13072-9ae3-472c-bb02-b44de08e699f.png)

## 2、LocalDate 、LocalTime 、LocalDateTime的使用

![img](file:///D:/Documents/My Knowledge/temp/63c63919-a4f2-4d0f-8b62-0640b693b965/128/index_files/fbf466ae-b7d8-4c63-8aaa-23d67b5753d1.png)

![img](file:///D:/Documents/My Knowledge/temp/63c63919-a4f2-4d0f-8b62-0640b693b965/128/index_files/07aa210a-c3a4-4a61-a9eb-9bff2a8fb94a.png)

```java
    /*
    LocalDate、LocalTime、LocalDateTime 的使用
    说明：
        1.LocalDateTime相较于LocalDate、LocalTime，使用频率要高
        2.类似于Calendar
     */
    @Test
    public void test1(){
        //now():获取当前的日期、时间、日期+时间
        LocalDate localDate = LocalDate.now();
        LocalTime localTime = LocalTime.now();
        LocalDateTime localDateTime = LocalDateTime.now();

        System.out.println(localDate); // 2020-07-22
        System.out.println(localTime); // 10:53:17.898 
        System.out.println(localDateTime); // 2020-07-22T10:53:17.898

        //of():设置指定的年、月、日、时、分、秒。没有偏移量
        LocalDateTime localDateTime1 = LocalDateTime.of(2020, 10, 6, 13, 23, 43);
        System.out.println(localDateTime1); // 2020-10-06T13:23:43


        //getXxx()：获取相关的属性
        System.out.println(localDateTime.getDayOfMonth()); // 22
        System.out.println(localDateTime.getDayOfWeek()); // WEDNESDAY
        System.out.println(localDateTime.getMonth()); // JULY
        System.out.println(localDateTime.getMonthValue()); // 7
        System.out.println(localDateTime.getMinute()); // 53

        //体现不可变性
        //withXxx():设置相关的属性
        LocalDate localDate1 = localDate.withDayOfMonth(22);
        System.out.println(localDate); // 2020-07-22
        System.out.println(localDate1); // 2020-07-22


        LocalDateTime localDateTime2 = localDateTime.withHour(4);
        System.out.println(localDateTime); // 2020-07-22T10:53:17.898
        System.out.println(localDateTime2); // 2020-07-22T04:53:17.898

        //不可变性
        LocalDateTime localDateTime3 = localDateTime.plusMonths(3);
        System.out.println(localDateTime); // 2020-07-22T10:53:17.898
        System.out.println(localDateTime3); // 2020-10-22T10:53:17.898

        LocalDateTime localDateTime4 = localDateTime.minusDays(6);
        System.out.println(localDateTime); // 2020-07-22T10:53:17.898
        System.out.println(localDateTime4); // 2020-07-16T10:53:17.898
    }
```



## 3、Instant类的使用

![img](file:///D:/Documents/My Knowledge/temp/63c63919-a4f2-4d0f-8b62-0640b693b965/128/index_files/7cf87439-e380-4f4e-8fa7-eb1f401d6021.png)

![img](file:///D:/Documents/My Knowledge/temp/63c63919-a4f2-4d0f-8b62-0640b693b965/128/index_files/17b737ad-ea9d-4743-a694-a0f84cd84047.png)

![img](file:///D:/Documents/My Knowledge/temp/63c63919-a4f2-4d0f-8b62-0640b693b965/128/index_files/544b27ae-92df-479c-a034-b1451c56f33d.jpg)

```java
    /*
    Instant的使用
    类似于 java.util.Date类

     */
    @Test
    public void test2(){
        //now():获取本初子午线对应的标准时间
        Instant instant = Instant.now();
        System.out.println(instant);//2020-07-22T03:13:55.925Z 当前11点 差8小时

        //添加时间的偏移量
        OffsetDateTime offsetDateTime = instant.atOffset(ZoneOffset.ofHours(8));
        System.out.println(offsetDateTime);//2020-07-22T11:13:55.925+08:00

        //toEpochMilli():获取自1970年1月1日0时0分0秒（UTC）开始的毫秒数  ---> Date类的getTime()
        long milli = instant.toEpochMilli();
        System.out.println(milli);// 1595387635925

        //ofEpochMilli():通过给定的毫秒数，获取Instant实例  -->Date(long millis)
        Instant instant1 = Instant.ofEpochMilli(1550475314878L);
        System.out.println(instant1); // 2019-02-18T07:35:14.878Z
    }
```



## 4、格式化与解析日期或时间

![img](file:///D:/Documents/My Knowledge/temp/63c63919-a4f2-4d0f-8b62-0640b693b965/128/index_files/5cca1827-66d0-471a-8183-446fccbaee0f.png)

```java
    /*
    DateTimeFormatter:格式化或解析日期、时间
    类似于SimpleDateFormat

     */

    @Test
    public void test3(){
//        方式一：预定义的标准格式。如：ISO_LOCAL_DATE_TIME;ISO_LOCAL_DATE;ISO_LOCAL_TIME
        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
        //格式化:日期-->字符串
        LocalDateTime localDateTime = LocalDateTime.now();
        String str1 = formatter.format(localDateTime);
        System.out.println(localDateTime); // 2020-07-22T11:15:05.982
        System.out.println(str1);//2020-07-22T11:15:05.982

        //解析：字符串 -->日期
        TemporalAccessor parse = formatter.parse("2019-02-18T15:42:18.797");
        System.out.println(parse);// {},ISO resolved to 2019-02-18T15:42:18.797

//        方式二：
//        本地化相关的格式。如：ofLocalizedDateTime()
//        FormatStyle.LONG / FormatStyle.MEDIUM / FormatStyle.SHORT :适用于LocalDateTime
        DateTimeFormatter formatter1 = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.LONG);
        //格式化
        String str2 = formatter1.format(localDateTime);
        System.out.println(str2);//2020年7月22日 上午11时15分05秒


//      本地化相关的格式。如：ofLocalizedDate()
//      FormatStyle.FULL / FormatStyle.LONG / FormatStyle.MEDIUM / FormatStyle.SHORT : 适用于LocalDate
        DateTimeFormatter formatter2 = DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM);
        //格式化
        String str3 = formatter2.format(LocalDate.now());
        System.out.println(str3);//2020-7-22


//       重点： 方式三：自定义的格式。如：ofPattern(“yyyy-MM-dd hh:mm:ss”)
        DateTimeFormatter formatter3 = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss");
        //格式化
        String str4 = formatter3.format(LocalDateTime.now());
        System.out.println(str4);//2020-07-22 11:15:05

        //解析
        TemporalAccessor accessor = formatter3.parse("2019-02-18 03:52:09");
        System.out.println(accessor); // {MinuteOfHour=52, MilliOfSecond=0, NanoOfSecond=0, HourOfAmPm=3, SecondOfMinute=9, MicroOfSecond=0},ISO resolved to 2019-02-18

    }
```



## 5、其它API

![img](file:///D:/Documents/My Knowledge/temp/63c63919-a4f2-4d0f-8b62-0640b693b965/128/index_files/ef06fef2-fd16-4c73-ae87-0556b354a0d3.png)

![img](file:///D:/Documents/My Knowledge/temp/63c63919-a4f2-4d0f-8b62-0640b693b965/128/index_files/c1ab812b-233d-4f2b-bd3f-5177bbf231df.png)

![img](file:///D:/Documents/My Knowledge/temp/63c63919-a4f2-4d0f-8b62-0640b693b965/128/index_files/d436e483-1e14-4322-aa87-99713ee7c108.png)

![img](file:///D:/Documents/My Knowledge/temp/63c63919-a4f2-4d0f-8b62-0640b693b965/128/index_files/804355a1-2977-425b-a839-cdada552417c.png)

![img](file:///D:/Documents/My Knowledge/temp/63c63919-a4f2-4d0f-8b62-0640b693b965/128/index_files/be83116d-acca-4b6b-a3fe-a76f2749405f.png)

![img](file:///D:/Documents/My Knowledge/temp/63c63919-a4f2-4d0f-8b62-0640b693b965/128/index_files/1108dd6d-bd99-48d4-818d-9a4b3d69aa33.png)

# 四、Java比较器

![img](file:///D:/Documents/My Knowledge/temp/63c63919-a4f2-4d0f-8b62-0640b693b965/128/index_files/0822a0b7-b6ac-48a2-a242-effbc5f81ec7.png)

## 1、方式一： 自然排序：java.lang.Comparable

![img](file:///D:/Documents/My Knowledge/temp/63c63919-a4f2-4d0f-8b62-0640b693b965/128/index_files/e6b75092-9ad0-41bb-a63d-36a57d2e666d.png)

![img](file:///D:/Documents/My Knowledge/temp/63c63919-a4f2-4d0f-8b62-0640b693b965/128/index_files/b960842b-8ca5-46b4-b535-93d45a6fe459.png)

```java
package com.atguigu.java;

/**
 * 商品类
 * @author shkstart
 * @create 2019 下午 4:52
 */
public class Goods implements  Comparable{

    private String name;
    private double price;

    public Goods() {
    }

    public Goods(String name, double price) {
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Goods{" +
                "name='" + name + '\'' +
                ", price=" + price +
                '}';
    }

    //指明商品比较大小的方式:按照价格从低到高排序,再按照产品名称从高到低排序
    @Override
    public int compareTo(Object o) {
//        System.out.println("**************");
        if(o instanceof Goods){
            Goods goods = (Goods)o;
            //方式一：
            if(this.price > goods.price){
                return 1;
            }else if(this.price < goods.price){
                return -1;
            }else{
//                return 0;
               return -this.name.compareTo(goods.name);
            }
            //方式二：按照价格从低到高排
//           return Double.compare(this.price,goods.price);
        }
//        return 0;
        throw new RuntimeException("传入的数据类型不一致！");
    }
}

```

```java
    /*
    Comparable接口的使用举例：  自然排序
    1.像String、包装类等实现了Comparable接口，重写了compareTo(obj)方法，给出了比较两个对象大小的方式。
    2.像String、包装类重写compareTo()方法以后，进行了从小到大的排列
    3. 重写compareTo(obj)的规则：
        如果当前对象this大于形参对象obj，则返回正整数，
        如果当前对象this小于形参对象obj，则返回负整数，
        如果当前对象this等于形参对象obj，则返回零。
    4. 对于自定义类来说，如果需要排序，我们可以让自定义类实现Comparable接口，重写compareTo(obj)方法。
       在compareTo(obj)方法中指明如何排序
     */
    @Test
    public void test1(){
        String[] arr = new String[]{"AA","CC","KK","MM","GG","JJ","DD"};
        //
        Arrays.sort(arr);

        System.out.println(Arrays.toString(arr)); // [AA, CC, DD, GG, JJ, KK, MM]

    }

    @Test
    public void test2(){
        Goods[] arr = new Goods[5];
        arr[0] = new Goods("lenovoMouse",34);
        arr[1] = new Goods("dellMouse",43);
        arr[2] = new Goods("xiaomiMouse",12);
        arr[3] = new Goods("huaweiMouse",65);
        arr[4] = new Goods("microsoftMouse",43);

        Arrays.sort(arr);

        System.out.println(Arrays.toString(arr));
    }
```





## 2、方式二： 定制排序：java.util.Comparator

![img](file:///D:/Documents/My Knowledge/temp/63c63919-a4f2-4d0f-8b62-0640b693b965/128/index_files/68d6ffa7-705c-44a2-94ce-34fb03f36f85.png)

```java
    /*
    Comparator接口的使用：定制排序
    1.背景：
    当元素的类型没有实现java.lang.Comparable接口而又不方便修改代码，
    或者实现了java.lang.Comparable接口的排序规则不适合当前的操作，
    那么可以考虑使用 Comparator 的对象来排序
    2.重写compare(Object o1,Object o2)方法，比较o1和o2的大小：
    如果方法返回正整数，则表示o1大于o2；
    如果返回0，表示相等；
    返回负整数，表示o1小于o2。

     */
    @Test
    public void test3() {
        String[] arr = new String[]{"AA", "CC", "KK", "MM", "GG", "JJ", "DD"};
        Arrays.sort(arr, new Comparator() {

            //按照字符串从大到小的顺序排列
            @Override
            public int compare(Object o1, Object o2) {
                if (o1 instanceof String && o2 instanceof String) {
                    String s1 = (String) o1;
                    String s2 = (String) o2;
                    return -s1.compareTo(s2);
                }
//                return 0;
                throw new RuntimeException("输入的数据类型不一致");
            }
        });
        System.out.println(Arrays.toString(arr));
    }

    @Test
    public void test4() {
        Goods[] arr = new Goods[6];
        arr[0] = new Goods("lenovoMouse", 34);
        arr[1] = new Goods("dellMouse", 43);
        arr[2] = new Goods("xiaomiMouse", 12);
        arr[3] = new Goods("huaweiMouse", 65);
        arr[4] = new Goods("huaweiMouse", 224);
        arr[5] = new Goods("microsoftMouse", 43);

        Arrays.sort(arr, new Comparator() {
            //指明商品比较大小的方式:按照产品名称从低到高排序,再按照价格从高到低排序
            @Override
            public int compare(Object o1, Object o2) {
                if (o1 instanceof Goods && o2 instanceof Goods) {
                    Goods g1 = (Goods) o1;
                    Goods g2 = (Goods) o2;
                    if (g1.getName().equals(g2.getName())) {
                        return -Double.compare(g1.getPrice(), g2.getPrice());
                    } else {
                        return g1.getName().compareTo(g2.getName());
                    }
                }
                throw new RuntimeException("输入的数据类型不一致");
            }
        });

        System.out.println(Arrays.toString(arr));
    }
```





# 五、System类

![img](file:///D:/Documents/My Knowledge/temp/63c63919-a4f2-4d0f-8b62-0640b693b965/128/index_files/d5c74f1d-4547-4c0a-b79a-643b78d19712.png)

![img](file:///D:/Documents/My Knowledge/temp/63c63919-a4f2-4d0f-8b62-0640b693b965/128/index_files/7e38c187-df0d-4651-8104-4003a0c52251.png)

```java
    @Test
    public void test1() {
        String javaVersion = System.getProperty("java.version");
        System.out.println("java的version:" + javaVersion);

        String javaHome = System.getProperty("java.home");
        System.out.println("java的home:" + javaHome);

        String osName = System.getProperty("os.name");
        System.out.println("os的name:" + osName);

        String osVersion = System.getProperty("os.version");
        System.out.println("os的version:" + osVersion);

        String userName = System.getProperty("user.name");
        System.out.println("user的name:" + userName);

        String userHome = System.getProperty("user.home");
        System.out.println("user的home:" + userHome);

        String userDir = System.getProperty("user.dir");
        System.out.println("user的dir:" + userDir);

    }
//    java的version:1.8.0_92
//    java的home:C:\Program Files\Java\jdk1.8.0_92\jre
//    os的name:Windows 10
//    os的version:10.0
//    user的name:wangl
//    user的home:C:\Users\wangl
//    user的dir:E:\学习资料\尚硅谷全套\尚硅谷Java学科全套教程\1.尚硅谷全套JAVA教程--基础阶段\尚硅谷Java核心基础_2019年版---------重点\课件笔记源码资料\5_代码\第2部分：Java高级编程\JavaSenior\day04
```





# 六、Math类

![img](file:///D:/Documents/My Knowledge/temp/63c63919-a4f2-4d0f-8b62-0640b693b965/128/index_files/6a410f82-bf5d-40da-b985-98f4efcfff5e.png)

# 七、BigInteger与BigDecimal

## 1、BigInteger类

![img](file:///D:/Documents/My Knowledge/temp/63c63919-a4f2-4d0f-8b62-0640b693b965/128/index_files/960ea0ee-e0f0-4c29-9df4-9e809be5f28b.png)

![img](file:///D:/Documents/My Knowledge/temp/63c63919-a4f2-4d0f-8b62-0640b693b965/128/index_files/90e8d962-44cd-47e5-9a39-a17d8c0ca40a.png)

## 2、BigDecimal类

![img](file:///D:/Documents/My Knowledge/temp/63c63919-a4f2-4d0f-8b62-0640b693b965/128/index_files/c50f0072-d397-463c-b699-d58c9d7259ef.png)

```java
    @Test
    public void test2() {
        BigInteger bi = new BigInteger("1243324112234324324325235245346567657653");
        BigDecimal bd = new BigDecimal("12435.351");
        BigDecimal bd2 = new BigDecimal("11");
        System.out.println(bi);
//         System.out.println(bd.divide(bd2)); // 除不尽报错
        System.out.println(bd.divide(bd2, BigDecimal.ROUND_HALF_UP));
        System.out.println(bd.divide(bd2, 25, BigDecimal.ROUND_HALF_UP));

    }
//    1243324112234324324325235245346567657653
//    1130.486
//    1130.4864545454545454545454545
```





# 八、每日练习

## 1、String s = new String("abc");方式创建对象，在内存中创建了几个对象？

两个:一个是堆空间中new结构，另一个是char[]对应的常量池中的数据："abc"

## 2、下面程序的输出结果？

```java
public class StringTest {

    String str = new String("good");
    char[] ch = { 't', 'e', 's', 't' };

    public void change(String str, char ch[]) {
        str = "test ok";
        ch[0] = 'b';
    }
    public static void main(String[] args) {
        StringTest ex = new StringTest();
        ex.change(ex.str, ex.ch);
        System.out.println(ex.str);//good
        System.out.println(ex.ch);//best
    }
```



## 3、对比String 、StringBuffer 、StringBuilder三者的异同？

![img](file:///D:/Documents/My Knowledge/temp/63c63919-a4f2-4d0f-8b62-0640b693b965/128/index_files/c28be680-523b-4ae7-85e3-77005fb5071a.png)

```java
    /*
    String、StringBuffer、StringBuilder三者的异同？
    String:不可变的字符序列；底层使用char[]存储
    StringBuffer:可变的字符序列；线程安全的，效率低；底层使用char[]存储
    StringBuilder:可变的字符序列；jdk5.0新增的，线程不安全的，效率高；底层使用char[]存储

    源码分析：
    String str = new String();//char[] value = new char[0];
    String str1 = new String("abc");//char[] value = new char[]{'a','b','c'};

    StringBuffer sb1 = new StringBuffer();//char[] value = new char[16];底层创建了一个长度是16的数组。
    System.out.println(sb1.length());// 0
    sb1.append('a');//value[0] = 'a';
    sb1.append('b');//value[1] = 'b';

    StringBuffer sb2 = new StringBuffer("abc");//char[] value = new char["abc".length() + 16];

    //问题1. System.out.println(sb2.length());//3
    //问题2. 扩容问题:如果要添加的数据底层数组盛不下了，那就需要扩容底层的数组。
             默认情况下，扩容为原来容量的2倍 + 2，同时将原有数组中的元素复制到新的数组中。

            指导意义：开发中建议大家使用：StringBuffer(int capacity) 或 StringBuilder(int capacity)
     */
```



## 4、如何理解String类的不可变性？

```java
String:代表不可变的字符序列。简称：不可变性。
    体现：1.当对字符串重新赋值时，需要重写指定内存区域赋值，不能使用原有的value进行赋值。
         2. 当对现有的字符串进行连接操作时，也需要重新指定内存区域赋值，不能使用原有的value进行赋值。
         3. 当调用String的replace()方法修改指定字符或字符串时，也需要重新指定内存区域赋值，不能使用原有的value进行赋值。
```



## 5、将一个字符串进行反转。将字符串中指定部分进行反转。比如“abcdefg”反转为”abfedcg”？

```java
package com.atguigu.exer;

import org.junit.Test;

/**
 * @author shkstart
 * @create 2019 上午 10:07
 */
public class StringDemo {

    /*
    将一个字符串进行反转。将字符串中指定部分进行反转。比如“abcdefg”反转为”abfedcg”

    方式一：转换为char[]
     */
    public String reverse(String str, int startIndex, int endIndex) {

        if (str != null) {
            char[] arr = str.toCharArray();
            for (int x = startIndex, y = endIndex; x < y; x++, y--) {
                char temp = arr[x];
                arr[x] = arr[y];
                arr[y] = temp;
            }

            return new String(arr);
        }
        return null;
    }

    //方式二：使用String的拼接
    public String reverse1(String str, int startIndex, int endIndex) {
        if (str != null) {
            //第1部分
            String reverseStr = str.substring(0, startIndex);
            //第2部分
            for (int i = endIndex; i >= startIndex; i--) {
                reverseStr += str.charAt(i);
            }
            //第3部分
            reverseStr += str.substring(endIndex + 1);

            return reverseStr;

        }
        return null;
    }

    //方式三：使用StringBuffer/StringBuilder替换String
    public String reverse2(String str, int startIndex, int endIndex) {
        if (str != null) {
            StringBuilder builder = new StringBuilder(str.length());

            //第1部分
            builder.append(str.substring(0, startIndex));
            //第2部分
            for (int i = endIndex; i >= startIndex; i--) {

                builder.append(str.charAt(i));
            }
            //第3部分
            builder.append(str.substring(endIndex + 1));

            return builder.toString();
        }
        return null;

    }

    @Test
    public void testReverse() {
        String str = "abcdefg";
        String reverse = reverse2(str, 2, 5);
        System.out.println(reverse);
    }

}

```



## 6、获取一个字符串在另一个字符串中出现的次数。比如：获取“ab”在 “abkkcadkabkebfkaabkskab” 中出现的次数？

```java
package com.atguigu.exer;

import org.junit.Test;

/**
 * @author shkstart
 * @create 2019 上午 10:26
 */
public class StringDemo1 {
    /*
    获取一个字符串在另一个字符串中出现的次数。
      比如：获取“ab”在 “abkkcadkabkebfkaabkskab” 中出现的次数

     */

    /**
     * 获取subStr在mainStr中出现的次数
     *
     * @param mainStr
     * @param subStr
     * @return
     */
    public int getCount(String mainStr, String subStr) {
        int mainLength = mainStr.length();
        int subLength = subStr.length();
        int count = 0;
        int index = 0;
        if (mainLength >= subLength) {
            //方式一：
//            while((index = mainStr.indexOf(subStr)) != -1){
//                count++;
//                mainStr = mainStr.substring(index + subStr.length());
//            }
            //方式二：对方式一的改进
            //  int indexOf(String str, int fromIndex)：返回指定子字符串在此字符串中第一次出现处的索引，从指定的索引开始
            while ((index = mainStr.indexOf(subStr, index)) != -1) {
                count++;
                index += subLength;
            }

            return count;
        } else {
            return 0;
        }
    }

    @Test
    public void testGetCount() {
        String mainStr = "abkkcadkabkebfkaabkskab";
        String subStr = "ab";
        int count = getCount(mainStr, subStr);
        System.out.println(count);
    }
}

```



## 7、获取两个字符串中最大相同子串。比如：str1 = "abcwerthelloyuiodefabcdef";str2 = "cvhellobnm"提示：将短的那个串进行长度依次递减的子串与较长的串比较？

```java
package com.atguigu.exer;

import org.junit.Test;

import java.util.Arrays;

/**
 * @author shkstart
 * @create 2019 上午 10:42
 */
public class StringDemo2 {
    /*
    获取两个字符串中最大相同子串。比如：
   str1 = "abcwerthelloyuiodefabcdef";str2 = "cvhellobnm"
   提示：将短的那个串进行长度依次递减的子串与较长的串比较。

     */
    //前提：两个字符串中只有一个最大相同子串
    public String getMaxSameString(String str1, String str2) {
        if (str1 != null && str2 != null) {
            String maxStr = (str1.length() >= str2.length()) ? str1 : str2;
            String minStr = (str1.length() < str2.length()) ? str1 : str2;
            int length = minStr.length();

            for (int i = 0; i < length; i++) {
                for (int x = 0, y = length - i; y <= length; x++, y++) {
                    String subStr = minStr.substring(x, y);
                    if (maxStr.contains(subStr)) {
                        return subStr;
                    }

                }
            }

        }
        return null;
    }

    // 如果存在多个长度相同的最大相同子串
    // 此时先返回String[]，后面可以用集合中的ArrayList替换，较方便
    public String[] getMaxSameString1(String str1, String str2) {
        if (str1 != null && str2 != null) {
            StringBuffer sBuffer = new StringBuffer();
            String maxString = (str1.length() > str2.length()) ? str1 : str2;
            String minString = (str1.length() > str2.length()) ? str2 : str1;

            int len = minString.length();
            for (int i = 0; i < len; i++) {
                for (int x = 0, y = len - i; y <= len; x++, y++) {
                    String subString = minString.substring(x, y);
                    if (maxString.contains(subString)) {
                        sBuffer.append(subString + ",");
                    }
                }
//                System.out.println(sBuffer);
                if (sBuffer.length() != 0) {
                    break;
                }
            }
            String[] split = sBuffer.toString().replaceAll(",$", "").split("\\,");
            return split;
        }

        return null;
    }

    @Test
    public void testGetMaxSameString() {
        String str1 = "abcwerthello1yuiodefabcdef";
        String str2 = "cvhello1bnmabcdef";
        String[] maxSameStrings = getMaxSameString1(str1, str2);
        System.out.println(Arrays.toString(maxSameStrings));

    }

}

```



## 8、解释何为编码？解码？ 何为日期时间的格式化？解析？

编码：字符串 -> 字节

解码：字节 -> 字符串

格式化：日期 -> 字符串

解析：字符串 -> 日期

## 9、Math.round(11.5)等于多少? Math.round(-11.5)等于多少？

答: Math.round(11.5)==12;Math.round(-11.5)==-11;round方法返回与参数最接近的长整数，参数加1/2后求其floor

## 10、是否可以继承String类？

答：String类是final类故不可以继承