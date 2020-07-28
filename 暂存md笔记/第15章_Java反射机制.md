# 一、Java反射机制概述

## 1、java反射机制

![img](file:///D:/Documents/My Knowledge/temp/642a5af3-7b5f-409b-ac8c-bb4b727f7c7f/128/index_files/dfdb964e-c9f3-4003-9d6a-825b414e63b5.png)

## 2、动态语言 vs 静态语言

![img](file:///D:/Documents/My Knowledge/temp/642a5af3-7b5f-409b-ac8c-bb4b727f7c7f/128/index_files/2a202440-3b44-4b28-b749-c81a25be9524.png)

## 3、Java反射机制研究及应用

![img](file:///D:/Documents/My Knowledge/temp/642a5af3-7b5f-409b-ac8c-bb4b727f7c7f/128/index_files/70d86204-5e50-4dce-814c-f72ac213b3fd.png)

## 4、反射相关的主要API

![img](file:///D:/Documents/My Knowledge/temp/642a5af3-7b5f-409b-ac8c-bb4b727f7c7f/128/index_files/1d977c2b-beaf-4d29-94b8-661c2b204938.png) 

```java
public class Person {

    private String name;
    public int age;

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Person(String name, int age) {

        this.name = name;
        this.age = age;
    }

    private Person(String name) {
        this.name = name;
    }

    public Person() {
        System.out.println("Person()");
    }

    public void show(){
        System.out.println("你好，我是一个人");
    }

    private String showNation(String nation){
        System.out.println("我的国籍是：" + nation);
        return nation;
    }
}
```

```java
    //反射之前，对于Person的操作
    @Test
    public void test1() {

        //1.创建Person类的对象
        Person p1 = new Person("Tom", 12);

        //2.通过对象，调用其内部的属性、方法
        p1.age = 10;
        System.out.println(p1.toString()); // Person{name='Tom', age=10}

        p1.show(); // 你好，我是一个人

        //在Person类外部，不可以通过Person类的对象调用其内部私有结构。
        //比如：name、showNation()以及私有的构造器
    }

    //反射之后，对于Person的操作
    @Test
    public void test2() throws Exception{
        Class clazz = Person.class;
        //1.通过反射，创建Person类的对象
        Constructor cons = clazz.getConstructor(String.class,int.class);
        Object obj = cons.newInstance("Tom", 12);
        Person p = (Person) obj;
        System.out.println(p.toString());// Person{name='Tom', age=12}
        //2.通过反射，调用对象指定的属性、方法
        //调用属性
        Field age = clazz.getDeclaredField("age");
        age.set(p,10);
        System.out.println(p.toString()); // Person{name='Tom', age=10}

        //调用方法
        Method show = clazz.getDeclaredMethod("show");
        show.invoke(p); // 你好，我是一个人

        System.out.println("*******************************");

        //通过反射，可以调用Person类的私有结构的。比如：私有的构造器、方法、属性
        //调用私有的构造器
        Constructor cons1 = clazz.getDeclaredConstructor(String.class);
        cons1.setAccessible(true);
        Person p1 = (Person) cons1.newInstance("Jerry");
        System.out.println(p1);// Person{name='Jerry', age=0}

        //调用私有的属性
        Field name = clazz.getDeclaredField("name");
        name.setAccessible(true);
        name.set(p1,"HanMeimei");
        System.out.println(p1);// Person{name='HanMeimei', age=0}

        //调用私有的方法
        Method showNation = clazz.getDeclaredMethod("showNation", String.class);
        showNation.setAccessible(true);
        String nation = (String) showNation.invoke(p1,"中国");//相当于String nation = p1.showNation("中国") // 我的国籍是：中国
        System.out.println(nation);// 中国


    }
    //疑问1：通过直接new的方式或反射的方式都可以调用公共的结构，开发中到底用那个？
    //建议：直接new的方式。
    //什么时候会使用：反射的方式。 反射的特征：动态性
    //疑问2：反射机制与面向对象中的封装性是不是矛盾的？如何看待两个技术？
    //不矛盾。面向对象中的封装性是建议调什么，反射机制是能不能调，不矛盾。
```



# 二、理解Class类并获取Class实例

## 1、Class 类

![img](file:///D:/Documents/My Knowledge/temp/642a5af3-7b5f-409b-ac8c-bb4b727f7c7f/128/index_files/b56e30c9-e05b-4b91-a85a-33cd838f442d.jpg)

![img](file:///D:/Documents/My Knowledge/temp/642a5af3-7b5f-409b-ac8c-bb4b727f7c7f/128/index_files/c0d3c361-fc25-40df-b084-251457fe881c.png)

## 2、Class类的常用方法

![img](file:///D:/Documents/My Knowledge/temp/642a5af3-7b5f-409b-ac8c-bb4b727f7c7f/128/index_files/d7540f6e-cd1b-47a8-a20e-d085547962ae.png)

## 3、获取Class 类的实例(四种方法)

![img](file:///D:/Documents/My Knowledge/temp/642a5af3-7b5f-409b-ac8c-bb4b727f7c7f/128/index_files/1551f1ac-4590-4771-b76f-79513932a3ed.png) 

```java
    /*
    关于java.lang.Class类的理解
    1.类的加载过程：
    程序经过javac.exe命令以后，会生成一个或多个字节码文件(.class结尾)。
    接着我们使用java.exe命令对某个字节码文件进行解释运行。相当于将某个字节码文件
    加载到内存中。此过程就称为类的加载。加载到内存中的类，我们就称为运行时类，此
    运行时类，就作为Class的一个实例。

    2.换句话说，Class的实例就对应着一个运行时类。
    3.加载到内存中的运行时类，会缓存一定的时间。在此时间之内，我们可以通过不同的方式
    来获取此运行时类。
     */
    //获取Class的实例的方式（前三种方式需要掌握）
    @Test
    public void test3() throws ClassNotFoundException {
        //方式一：调用运行时类的属性：.class
        Class clazz1 = Person.class;
        System.out.println(clazz1); // class com.atguigu.java.Person
        //方式二：通过运行时类的对象,调用getClass()
        Person p1 = new Person(); // Person()
        Class clazz2 = p1.getClass();
        System.out.println(clazz2);// class com.atguigu.java.Person

        //方式三：调用Class的静态方法：forName(String classPath)
        Class clazz3 = Class.forName("com.atguigu.java.Person");
//        clazz3 = Class.forName("java.lang.String");
        System.out.println(clazz3); // class com.atguigu.java.Person

        System.out.println(clazz1 == clazz2);// true
        System.out.println(clazz1 == clazz3);// true

        //方式四：使用类的加载器：ClassLoader  (了解)
        ClassLoader classLoader = ReflectionTest.class.getClassLoader();
        Class clazz4 = classLoader.loadClass("com.atguigu.java.Person");
        System.out.println(clazz4);// class com.atguigu.java.Person

        System.out.println(clazz1 == clazz4);// true

    }
```



## 4、哪些类型可以有Class 对象

![img](file:///D:/Documents/My Knowledge/temp/642a5af3-7b5f-409b-ac8c-bb4b727f7c7f/128/index_files/53a945d0-6490-41ce-9256-51311ebbe5de.png)

```java
    //Class实例可以是哪些结构的说明：
    @Test
    public void test4(){
        Class c1 = Object.class;
        Class c2 = Comparable.class;
        Class c3 = String[].class;
        Class c4 = int[][].class;
        Class c5 = ElementType.class;
        Class c6 = Override.class;
        Class c7 = int.class;
        Class c8 = void.class;
        Class c9 = Class.class;

        int[] a = new int[10];
        int[] b = new int[100];
        Class c10 = a.getClass();
        Class c11 = b.getClass();
        // 只要数组的元素类型与维度一样，就是同一个Class
        System.out.println(c10 == c11); // true

    }
```





# 三、类的加载与ClassLoader的理解

## 1、了解：类的加载过程

![img](file:///D:/Documents/My Knowledge/temp/642a5af3-7b5f-409b-ac8c-bb4b727f7c7f/128/index_files/e9af8857-b326-4623-9b97-e7511d89d677.png)

![img](file:///D:/Documents/My Knowledge/temp/642a5af3-7b5f-409b-ac8c-bb4b727f7c7f/128/index_files/35602ff2-a91a-44dc-bb01-e3756f0116cd.png)

![img](file:///D:/Documents/My Knowledge/temp/642a5af3-7b5f-409b-ac8c-bb4b727f7c7f/128/index_files/3fe840cb-84f6-4e6b-99e5-e144d4934f29.png)

## 2、了解： 什么时候会发生类初始化

![img](file:///D:/Documents/My Knowledge/temp/642a5af3-7b5f-409b-ac8c-bb4b727f7c7f/128/index_files/f456fc89-7615-48be-a7e1-8e244e2ebb41.png)

## 3、类加载器的作用

![img](file:///D:/Documents/My Knowledge/temp/642a5af3-7b5f-409b-ac8c-bb4b727f7c7f/128/index_files/460cb339-1405-4306-8416-7e2c9141c506.jpg)

## 4、了解：ClassLoader

![img](file:///D:/Documents/My Knowledge/temp/642a5af3-7b5f-409b-ac8c-bb4b727f7c7f/128/index_files/b84a4665-d5cd-419b-83ef-6e2853be4597.jpg)

![img](file:///D:/Documents/My Knowledge/temp/642a5af3-7b5f-409b-ac8c-bb4b727f7c7f/128/index_files/cf62541f-b47c-4e5d-808b-580c50808257.png)

![img](file:///D:/Documents/My Knowledge/temp/642a5af3-7b5f-409b-ac8c-bb4b727f7c7f/128/index_files/6ca77cd8-d622-48d7-980b-931a548ab600.png)

```java
    @Test
    public void test1(){
        //对于自定义类，使用系统类加载器进行加载
        ClassLoader classLoader = ClassLoaderTest.class.getClassLoader();
        System.out.println(classLoader);// sun.misc.Launcher$AppClassLoader@18b4aac2
        //调用系统类加载器的getParent()：获取扩展类加载器
        ClassLoader classLoader1 = classLoader.getParent();
        System.out.println(classLoader1);// sun.misc.Launcher$ExtClassLoader@a09ee92
        //调用扩展类加载器的getParent()：无法获取引导类加载器
        //引导类加载器主要负责加载java的核心类库，无法加载自定义类的。
        ClassLoader classLoader2 = classLoader1.getParent();
        System.out.println(classLoader2);// null

        ClassLoader classLoader3 = String.class.getClassLoader();
        System.out.println(classLoader3);// null
    }
```

```java
    @Test
    public void test2() throws Exception {

        Properties pros =  new Properties();
        //此时的文件默认在当前的module下。
        //读取配置文件的方式一：
//        FileInputStream fis = new FileInputStream("jdbc.properties");
//        FileInputStream fis = new FileInputStream("src\\jdbc1.properties");
//        pros.load(fis);

        //读取配置文件的方式二：使用ClassLoader
        //配置文件默认识别为：当前module的src下
        ClassLoader classLoader = ClassLoaderTest.class.getClassLoader();
        // 关于类加载器的一个主要方法：getResourceAsStream(String str):获取类路径下的指定文件的输入流
        InputStream is = classLoader.getResourceAsStream("jdbc1.properties");
        pros.load(is);


        String user = pros.getProperty("user");
        String password = pros.getProperty("password");
        System.out.println("user = " + user + ",password = " + password); // user = 黄峰,password = abc123
    }
```







# 四、创建运行时类的对象

![img](file:///D:/Documents/My Knowledge/temp/642a5af3-7b5f-409b-ac8c-bb4b727f7c7f/128/index_files/f5aeccfb-6c99-444c-960f-428670abcfa3.png)

```java
    @Test
    public void test1() throws IllegalAccessException, InstantiationException {

        Class<Person> clazz = Person.class;
        /*
        newInstance():调用此方法，创建对应的运行时类的对象。内部调用了运行时类的空参的构造器。

        要想此方法正常的创建运行时类的对象，要求：
        1.运行时类必须提供空参的构造器
        2.空参的构造器的访问权限得够。通常，设置为public。


        在javabean中要求提供一个public的空参构造器。原因：
        1.便于通过反射，创建运行时类的对象
        2.便于子类继承此运行时类时，默认调用super()时，保证父类有此构造器

         */
        Person obj = clazz.newInstance();
        System.out.println(obj);
        // Person()
        // Person{name='null', age=0}
    }
```

```java
    //体会反射的动态性
    @Test
    public void test2(){

        for(int i = 0;i < 100;i++){
            int num = new Random().nextInt(3);//0,1,2
            String classPath = "";
            switch(num){
                case 0:
                    classPath = "java.util.Date";
                    break;
                case 1:
                    classPath = "java.lang.Object";
                    break;
                case 2:
                    classPath = "com.atguigu.java.Person";
                    break;
            }
            try {
                Object obj = getInstance(classPath);
                System.out.println(obj);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /*
     *  创建一个指定类的对象。classPath:指定类的全类名
     */
    public Object getInstance(String classPath) throws Exception {
       Class clazz =  Class.forName(classPath);
       return clazz.newInstance();
    }
```

![img](file:///D:/Documents/My Knowledge/temp/642a5af3-7b5f-409b-ac8c-bb4b727f7c7f/128/index_files/be473523-223e-47be-8005-47c1a728b06e.png)

# 五、获取运行时类的完整结构

![img](file:///D:/Documents/My Knowledge/temp/642a5af3-7b5f-409b-ac8c-bb4b727f7c7f/128/index_files/7b0b05fa-1b29-4796-ac87-04c5b2c26f6e.png)

使用反射可以取得：

```java
package com.atguigu.java1;

import java.io.Serializable;

/**
 * @author shkstart
 * @create 2019 下午 3:12
 */
public class Creature<T> implements Serializable {
    private char gender;
    public double weight;

    private void breath(){
        System.out.println("生物呼吸");
    }

    public void eat(){
        System.out.println("生物吃东西");
    }

}
```

```java
package com.atguigu.java1;

/**
 * @author shkstart
 * @create 2019 下午 3:15
 */

public interface MyInterface {
    void info();
}
```

```java
package com.atguigu.java1;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;

/**
 * @author shkstart
 * @create 2019 下午 3:19
 */
@Target({TYPE, FIELD, METHOD, PARAMETER, CONSTRUCTOR, LOCAL_VARIABLE})
@Retention(RetentionPolicy.RUNTIME)
public @interface MyAnnotation {
    String value() default "hello";

}
```

```java
package com.atguigu.java1;

/**
 * @author shkstart
 * @create 2019 下午 3:12
 */
@MyAnnotation(value="hi")
public class Person extends Creature<String> implements Comparable<String>,MyInterface{

    private String name;
    int age;
    public int id;

    public Person(){}

    @MyAnnotation(value="abc")
    private Person(String name){
        this.name = name;
    }

     Person(String name,int age){
        this.name = name;
        this.age = age;
    }
    @MyAnnotation
    private String show(String nation){
        System.out.println("我的国籍是：" + nation);
        return nation;
    }

    public String display(String interests,int age) throws NullPointerException,ClassCastException{
        return interests + age;
    }


    @Override
    public void info() {
        System.out.println("我是一个人");
    }

    @Override
    public int compareTo(String o) {
        return 0;
    }

    private static void showDesc(){
        System.out.println("我是一个可爱的人");
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", id=" + id +
                '}';
    }
}
```



## 1、实现的全部接口

![img](file:///D:/Documents/My Knowledge/temp/642a5af3-7b5f-409b-ac8c-bb4b727f7c7f/128/index_files/e733fb09-4aa2-4543-9c96-d69d0c0a28cd.png)

```java
   /*
    获取运行时类实现的接口
     */
    @Test
    public void test5(){
        Class clazz = Person.class;

        Class[] interfaces = clazz.getInterfaces();
        for(Class c : interfaces){
            System.out.println(c);
        }

        System.out.println();
        //获取运行时类的父类实现的接口
        Class[] interfaces1 = clazz.getSuperclass().getInterfaces();
        for(Class c : interfaces1){
            System.out.println(c);
        }

    }
```

![img](file:///D:/Documents/My Knowledge/temp/642a5af3-7b5f-409b-ac8c-bb4b727f7c7f/128/index_files/bf856a0f-29e4-490e-a662-7aa0c8b88f13.png)

## 2、所继承的父类

![img](file:///D:/Documents/My Knowledge/temp/642a5af3-7b5f-409b-ac8c-bb4b727f7c7f/128/index_files/83af33c9-0982-45d1-8f0f-effcb3b2d346.png)

```java
    /*
    获取运行时类的父类

     */
    @Test
    public void test2(){
        Class clazz = Person.class;

        Class superclass = clazz.getSuperclass();
        System.out.println(superclass);
    }
```

![img](file:///D:/Documents/My Knowledge/temp/642a5af3-7b5f-409b-ac8c-bb4b727f7c7f/128/index_files/18e1c904-e84a-4ecc-ba4b-bce49a50af6e.png)

## 3、全部的构造器

![img](file:///D:/Documents/My Knowledge/temp/642a5af3-7b5f-409b-ac8c-bb4b727f7c7f/128/index_files/25125b8d-5301-49f4-a1da-1406c354ad38.png)

```java
    /*
    获取构造器结构

     */
    @Test
    public void test1(){

        Class clazz = Person.class;
        //getConstructors():获取当前运行时类中声明为public的构造器
        Constructor[] constructors = clazz.getConstructors();
        for(Constructor c : constructors){
            System.out.println(c);
        }

        System.out.println();
        //getDeclaredConstructors():获取当前运行时类中声明的所有的构造器
        Constructor[] declaredConstructors = clazz.getDeclaredConstructors();
        for(Constructor c : declaredConstructors){
            System.out.println(c);
        }

    }
```

![img](file:///D:/Documents/My Knowledge/temp/642a5af3-7b5f-409b-ac8c-bb4b727f7c7f/128/index_files/93de85e9-daf3-4339-b5cf-2ea9b49ee2b5.png)

## 4、全部的方法

![img](file:///D:/Documents/My Knowledge/temp/642a5af3-7b5f-409b-ac8c-bb4b727f7c7f/128/index_files/530dfc6a-dfe8-411d-b4e0-303735fac7dd.png)

```java
    @Test
    public void test1(){

        Class clazz = Person.class;

        //getMethods():获取当前运行时类及其所有父类中声明为public权限的方法
        Method[] methods = clazz.getMethods();
        for(Method m : methods){
            System.out.println(m);
        }
        System.out.println();
        //getDeclaredMethods():获取当前运行时类中声明的所有方法。（不包含父类中声明的方法）
        Method[] declaredMethods = clazz.getDeclaredMethods();
        for(Method m : declaredMethods){
            System.out.println(m);
        }
    }
```

![img](file:///D:/Documents/My Knowledge/temp/642a5af3-7b5f-409b-ac8c-bb4b727f7c7f/128/index_files/e286d4bd-29c6-4833-9a02-48099907d886.png)

```java
    /*
    @Xxxx
    权限修饰符  返回值类型  方法名(参数类型1 形参名1,...) throws XxxException{}
     */
    @Test
    public void test2(){
        Class clazz = Person.class;
        Method[] declaredMethods = clazz.getDeclaredMethods();
        for(Method m : declaredMethods){
            //1.获取方法声明的注解
            Annotation[] annos = m.getAnnotations();
            for(Annotation a : annos){
                System.out.println(a);
            }

            //2.权限修饰符
            System.out.print(Modifier.toString(m.getModifiers()) + "\t");

            //3.返回值类型
            System.out.print(m.getReturnType().getName() + "\t");

            //4.方法名
            System.out.print(m.getName());
            System.out.print("(");
            //5.形参列表
            Class[] parameterTypes = m.getParameterTypes();
            if(!(parameterTypes == null && parameterTypes.length == 0)){
                for(int i = 0;i < parameterTypes.length;i++){

                    if(i == parameterTypes.length - 1){
                        System.out.print(parameterTypes[i].getName() + " args_" + i);
                        break;
                    }

                    System.out.print(parameterTypes[i].getName() + " args_" + i + ",");
                }
            }

            System.out.print(")");

            //6.抛出的异常
            Class[] exceptionTypes = m.getExceptionTypes();
            if(exceptionTypes.length > 0){
                System.out.print("throws ");
                for(int i = 0;i < exceptionTypes.length;i++){
                    if(i == exceptionTypes.length - 1){
                        System.out.print(exceptionTypes[i].getName());
                        break;
                    }

                    System.out.print(exceptionTypes[i].getName() + ",");
                }
            }
            System.out.println();
        }
    }
```

![img](file:///D:/Documents/My Knowledge/temp/642a5af3-7b5f-409b-ac8c-bb4b727f7c7f/128/index_files/325e3bcf-fdef-4a53-bd58-a0a55c5b8c84.png)

## 5、全部的Field

![img](file:///D:/Documents/My Knowledge/temp/642a5af3-7b5f-409b-ac8c-bb4b727f7c7f/128/index_files/b774bf5b-b395-4c14-b973-4e1b0b41d2ca.png)

```
    @Test
    public void test1(){

        Class clazz = Person.class;

        //获取属性结构
        //getFields():获取当前运行时类及其父类中声明为public访问权限的属性
        Field[] fields = clazz.getFields();
        for(Field f : fields){
            System.out.println(f);
        }
        System.out.println();

        //getDeclaredFields():获取当前运行时类中声明的所有属性。（不包含父类中声明的属性）
        Field[] declaredFields = clazz.getDeclaredFields();
        for(Field f : declaredFields){
            System.out.println(f);
        }
    }
```

![img](file:///D:/Documents/My Knowledge/temp/642a5af3-7b5f-409b-ac8c-bb4b727f7c7f/128/index_files/fe518fff-28e7-4379-9fa3-5afcb7c06276.png)

```java
    //权限修饰符  数据类型 变量名
    @Test
    public void test2(){
        Class clazz = Person.class;
        Field[] declaredFields = clazz.getDeclaredFields();
        for(Field f : declaredFields){
            //1.权限修饰符
            int modifier = f.getModifiers();
            System.out.print(Modifier.toString(modifier) + "\t");

            //2.数据类型
            Class type = f.getType();
            System.out.print(type.getName() + "\t");

            //3.变量名
            String fName = f.getName();
            System.out.print(fName);

            System.out.println();
        }

    }
```

![img](file:///D:/Documents/My Knowledge/temp/642a5af3-7b5f-409b-ac8c-bb4b727f7c7f/128/index_files/e4efecae-cf65-4d62-92d2-16a6ec362ff0.png)

## 6、Annotation 相关

![img](file:///D:/Documents/My Knowledge/temp/642a5af3-7b5f-409b-ac8c-bb4b727f7c7f/128/index_files/ba5f4cb3-c45c-437e-8a10-b9ced1d69612.png)

```java
    /*
        获取运行时类声明的注解
     */
    @Test
    public void test7(){
        Class clazz = Person.class;

        Annotation[] annotations = clazz.getAnnotations();
        for(Annotation annos : annotations){
            System.out.println(annos);
        }
    }
```

![img](file:///D:/Documents/My Knowledge/temp/642a5af3-7b5f-409b-ac8c-bb4b727f7c7f/128/index_files/b5d6dc58-c6e8-447c-850c-85e5ebaa76ae.png)

## 7、泛型相关

![img](file:///D:/Documents/My Knowledge/temp/642a5af3-7b5f-409b-ac8c-bb4b727f7c7f/128/index_files/880619fd-5ea4-4da2-a597-6ff52675ff00.png)

```java
    /*
    获取运行时类的带泛型的父类

     */
    @Test
    public void test3(){
        Class clazz = Person.class;

        Type genericSuperclass = clazz.getGenericSuperclass();
        System.out.println(genericSuperclass);
    }
```

![img](file:///D:/Documents/My Knowledge/temp/642a5af3-7b5f-409b-ac8c-bb4b727f7c7f/128/index_files/71e60157-4df0-4b52-aaaa-45b433f09d68.png)

```java
    /*
    获取运行时类的带泛型的父类的泛型


    代码：逻辑性代码  vs 功能性代码
     */
    @Test
    public void test4(){
        Class clazz = Person.class;

        Type genericSuperclass = clazz.getGenericSuperclass();
        ParameterizedType paramType = (ParameterizedType) genericSuperclass;
        //获取泛型类型
        Type[] actualTypeArguments = paramType.getActualTypeArguments();
//        System.out.println(actualTypeArguments[0].getTypeName());
        System.out.println(((Class)actualTypeArguments[0]).getName());
    }
```

![img](file:///D:/Documents/My Knowledge/temp/642a5af3-7b5f-409b-ac8c-bb4b727f7c7f/128/index_files/4b46ec05-4bcb-4082-80c9-7a9ab8fd31f2.png)

## 8、类所在的包

![img](file:///D:/Documents/My Knowledge/temp/642a5af3-7b5f-409b-ac8c-bb4b727f7c7f/128/index_files/2dbeaef0-ff66-4052-9d34-99f3bdc98e7e.png)

```java
    /*
        获取运行时类所在的包
     */
    @Test
    public void test6(){
        Class clazz = Person.class;

        Package pack = clazz.getPackage();
        System.out.println(pack);
    }
```

![img](file:///D:/Documents/My Knowledge/temp/642a5af3-7b5f-409b-ac8c-bb4b727f7c7f/128/index_files/2cf2e10b-deda-4375-985d-6f9aa7cf5913.png)

![img](file:///D:/Documents/My Knowledge/temp/642a5af3-7b5f-409b-ac8c-bb4b727f7c7f/128/index_files/c612c8d0-3418-4531-9ac1-fe7b9b924309.png)

# 六、调用运行时类的指定结构

## 1、调用指定方法

![img](file:///D:/Documents/My Knowledge/temp/642a5af3-7b5f-409b-ac8c-bb4b727f7c7f/128/index_files/fcf6425b-3434-49c2-b739-f16edd907476.png)

![img](file:///D:/Documents/My Knowledge/temp/642a5af3-7b5f-409b-ac8c-bb4b727f7c7f/128/index_files/8b2df1c9-6a14-4278-8491-66ba7f8e2803.png)

```java
    /*
    如何操作运行时类中的指定的方法 -- 需要掌握
     */
    @Test
    public void testMethod() throws Exception {

        Class clazz = Person.class;

        //创建运行时类的对象
        Person p = (Person) clazz.newInstance();

        /*
        1.获取指定的某个方法
        getDeclaredMethod():参数1 ：指明获取的方法的名称  参数2：指明获取的方法的形参列表
         */
        Method show = clazz.getDeclaredMethod("show", String.class);
        //2.保证当前方法是可访问的
        show.setAccessible(true);

        /*
        3. 调用方法的invoke():参数1：方法的调用者  参数2：给方法形参赋值的实参
        invoke()的返回值即为对应类中调用的方法的返回值。
         */
        Object returnValue = show.invoke(p,"CHN"); //String nation = p.show("CHN");
        System.out.println(returnValue);// 我的国籍是：CHN

        System.out.println("*************如何调用静态方法*****************");

        // private static void showDesc()

        Method showDesc = clazz.getDeclaredMethod("showDesc");
        showDesc.setAccessible(true);
        //如果调用的运行时类中的方法没有返回值，则此invoke()返回null
//        Object returnVal = showDesc.invoke(null);
        Object returnVal = showDesc.invoke(Person.class);
        System.out.println(returnVal);//null

    }
```

![img](file:///D:/Documents/My Knowledge/temp/642a5af3-7b5f-409b-ac8c-bb4b727f7c7f/128/index_files/95b9de19-0047-4d73-b574-715732c55d90.png)

## 2、调用指定属性

![img](file:///D:/Documents/My Knowledge/temp/642a5af3-7b5f-409b-ac8c-bb4b727f7c7f/128/index_files/23051740-f541-41cc-9388-1b459ce0454d.png)

```java
    }
    /*
    如何操作运行时类中的指定的属性 -- 需要掌握
     */
    @Test
    public void testField1() throws Exception {
        Class clazz = Person.class;

        //创建运行时类的对象
        Person p = (Person) clazz.newInstance();

        //1. getDeclaredField(String fieldName):获取运行时类中指定变量名的属性
        Field name = clazz.getDeclaredField("name");

        //2.保证当前属性是可访问的
        name.setAccessible(true);
        //3.获取、设置指定对象的此属性值
        name.set(p,"Tom");

        System.out.println(name.get(p)); // Tom
    }
```



## 3、关于setAccessible方法的使用

![img](file:///D:/Documents/My Knowledge/temp/642a5af3-7b5f-409b-ac8c-bb4b727f7c7f/128/index_files/a7830fa1-d69f-4ff2-9693-adb7eff3c99e.png)

```java
   /*
    如何调用运行时类中的指定的构造器
     */
    @Test
    public void testConstructor() throws Exception {
        Class clazz = Person.class;

        //private Person(String name)
        /*
        1.获取指定的构造器
        getDeclaredConstructor():参数：指明构造器的参数列表
         */

        Constructor constructor = clazz.getDeclaredConstructor(String.class);

        //2.保证此构造器是可访问的
        constructor.setAccessible(true);

        //3.调用此构造器创建运行时类的对象
        Person per = (Person) constructor.newInstance("Tom");
        System.out.println(per);// Person{name='Tom', age=0, id=0}
    }
```



# 七、反射的应用：动态代理

## 1、代理设计模式的原理

![img](file:///D:/Documents/My Knowledge/temp/642a5af3-7b5f-409b-ac8c-bb4b727f7c7f/128/index_files/4154ec24-e3e9-4caa-96ae-d86b9901698c.png)

## 2、动态代理

![img](file:///D:/Documents/My Knowledge/temp/642a5af3-7b5f-409b-ac8c-bb4b727f7c7f/128/index_files/6fe7856e-8242-4552-8cba-141655de637e.png)

## 3、Java 动态代理相关API

![img](file:///D:/Documents/My Knowledge/temp/642a5af3-7b5f-409b-ac8c-bb4b727f7c7f/128/index_files/1d32e1a0-dd40-45ef-b8c2-ede8cdf106c9.png)

## 4、动态代理步骤

### （1）动态代理创建使用步骤

![img](file:///D:/Documents/My Knowledge/temp/642a5af3-7b5f-409b-ac8c-bb4b727f7c7f/128/index_files/2d8b307f-a54a-4611-a71e-e04def37cb9d.png)

![img](file:///D:/Documents/My Knowledge/temp/642a5af3-7b5f-409b-ac8c-bb4b727f7c7f/128/index_files/2b5f4283-7f88-4730-b781-d13dfd32c499.png)

![img](file:///D:/Documents/My Knowledge/temp/642a5af3-7b5f-409b-ac8c-bb4b727f7c7f/128/index_files/70f10eed-e061-4bd3-9a7d-7eacab8ff872.png)

![img](file:///D:/Documents/My Knowledge/temp/642a5af3-7b5f-409b-ac8c-bb4b727f7c7f/128/index_files/69f3eccc-fbf9-4068-8af4-6eca85fcf222.png)

### （2）静态代理举例

```java
package com.atguigu.java;

/**
 * 静态代理举例
 *
 * 特点：代理类和被代理类在编译期间，就确定下来了。
 *
 * @author shkstart
 * @create 2019 上午 10:11
 */
interface ClothFactory{

    void produceCloth();

}

//代理类
class ProxyClothFactory implements ClothFactory{

    private ClothFactory factory;//用被代理类对象进行实例化

    public ProxyClothFactory(ClothFactory factory){
        this.factory = factory;
    }

    @Override
    public void produceCloth() {
        System.out.println("代理工厂做一些准备工作");

        factory.produceCloth();

        System.out.println("代理工厂做一些后续的收尾工作");

    }
}

//被代理类
class NikeClothFactory implements ClothFactory{

    @Override
    public void produceCloth() {
        System.out.println("Nike工厂生产一批运动服");
    }
}

public class StaticProxyTest {
    public static void main(String[] args) {
        //创建被代理类的对象
        ClothFactory nike = new NikeClothFactory();
        //创建代理类的对象
        ClothFactory proxyClothFactory = new ProxyClothFactory(nike);

        proxyClothFactory.produceCloth();

    }
}

```

![img](file:///D:/Documents/My Knowledge/temp/642a5af3-7b5f-409b-ac8c-bb4b727f7c7f/128/index_files/bdb793e4-0110-4e20-94c5-6d673f4c9581.png)



### （3）动态代理举例

```java
package com.atguigu.java;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 *
 * 动态代理的举例
 *
 * @author shkstart
 * @create 2019 上午 10:18
 */

interface Human{

    String getBelief();

    void eat(String food);

}
//被代理类
class SuperMan implements Human{


    @Override
    public String getBelief() {
        return "I believe I can fly!";
    }

    @Override
    public void eat(String food) {
        System.out.println("我喜欢吃" + food);
    }
}

class HumanUtil{

    public void method1(){
        System.out.println("====================通用方法一====================");

    }

    public void method2(){
        System.out.println("====================通用方法二====================");
    }

}

/*
要想实现动态代理，需要解决的问题？
问题一：如何根据加载到内存中的被代理类，动态的创建一个代理类及其对象。
问题二：当通过代理类的对象调用方法a时，如何动态的去调用被代理类中的同名方法a。
 */

class ProxyFactory{
    //调用此方法，返回一个代理类的对象。解决问题一
    public static Object getProxyInstance(Object obj){//obj:被代理类的对象
        MyInvocationHandler handler = new MyInvocationHandler();

        handler.bind(obj);

        return Proxy.newProxyInstance(obj.getClass().getClassLoader(),obj.getClass().getInterfaces(),handler);
    }

}

class MyInvocationHandler implements InvocationHandler{

    private Object obj;//需要使用被代理类的对象进行赋值

    public void bind(Object obj){
        this.obj = obj;
    }

    //当我们通过代理类的对象，调用方法a时，就会自动的调用如下的方法：invoke()
    //将被代理类要执行的方法a的功能就声明在invoke()中
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        HumanUtil util = new HumanUtil();
        util.method1();

        //method:即为代理类对象调用的方法，此方法也就作为了被代理类对象要调用的方法
        //obj:被代理类的对象
        Object returnValue = method.invoke(obj,args);

        util.method2();

        //上述方法的返回值就作为当前类中的invoke()的返回值。
        return returnValue;

    }
}

public class ProxyTest {

    public static void main(String[] args) {
        SuperMan superMan = new SuperMan();
        //proxyInstance:代理类的对象
        Human proxyInstance = (Human) ProxyFactory.getProxyInstance(superMan);
        //当通过代理类对象调用方法时，会自动的调用被代理类中同名的方法
        String belief = proxyInstance.getBelief();
        System.out.println(belief);
        proxyInstance.eat("四川麻辣烫");

        System.out.println("*****************************");

        NikeClothFactory nikeClothFactory = new NikeClothFactory();

        ClothFactory proxyClothFactory = (ClothFactory) ProxyFactory.getProxyInstance(nikeClothFactory);

        proxyClothFactory.produceCloth();

    }
}

```

![img](file:///D:/Documents/My Knowledge/temp/642a5af3-7b5f-409b-ac8c-bb4b727f7c7f/128/index_files/044ff48f-a55c-4aee-a88c-3906bccf6cdc.png)

## 5、动态代理与AOP （Aspect Orient Programming)

![img](file:///D:/Documents/My Knowledge/temp/642a5af3-7b5f-409b-ac8c-bb4b727f7c7f/128/index_files/6f2de735-a320-4a41-b8d5-b24cb3224c13.png)

![img](file:///D:/Documents/My Knowledge/temp/642a5af3-7b5f-409b-ac8c-bb4b727f7c7f/128/index_files/cf5f2fd2-c2dd-44d3-91eb-6c7923a86d34.png)

![img](file:///D:/Documents/My Knowledge/temp/642a5af3-7b5f-409b-ac8c-bb4b727f7c7f/128/index_files/a8b8d489-f060-489b-b482-eab7eb8ecb2e.png)

![img](file:///D:/Documents/My Knowledge/temp/642a5af3-7b5f-409b-ac8c-bb4b727f7c7f/128/index_files/296b2527-e56c-4a2b-82de-db19b153e8a6.png)

# 八、每日练习

## 1、描述一下JVM加载class文件的原理机制?

答：JVM中类的装载是由ClassLoader和它的子类来实现的，Java ClassLoader 是一个重要的Java运行时系统组件。它负责在运行时查找和装入类文件的类。

## 2、写出获取Class实例的三种常见方式？

Class clazz1 = String.class;

Class clazz2 = person.getClass(); //sout(person); //xxx.yyy.zzz.Person@...

Class clazz3 = Class.forName(String classPath);//体现反射的动态性

## 3、谈谈你对Class类的理解？

Class实例对应着加载到内存中的一个运行时类。

## 4、创建Class对应运行时类的对象的通用方法，代码实现。以及这样操作，需要对应的运行时类构造器方面满足的要求？

Object obj = clazz.newInstance();//创建了对应的运行时类的对象

1.必须有空参的构造器

2.权限修饰符的权限要够。通常设置为public

## 5、如何调用方法show() ？

类声明如下：

package com.atguigu.java;

class User{

  public void show(){

​    System.out.println(“我是一个中国人！”); 

} }

User user = (User)clazz.newInstance();

Method show = clazz.getDeclaredMethod("show");

show.setAccessiable(true);

show.invoke(user);