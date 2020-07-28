# 一、Java集合框架概述 

![img](file:///D:/Documents/My Knowledge/temp/02b956c4-c6a2-44de-a64e-597bdbadcf6f/128/index_files/60ddd4b8-6770-4e6f-8a4f-fb929cac4e0c.png)

![img](file:///D:/Documents/My Knowledge/temp/02b956c4-c6a2-44de-a64e-597bdbadcf6f/128/index_files/513acbfb-51a6-44ea-a560-9aa0d1e84f6e.png)

![img](file:///D:/Documents/My Knowledge/temp/02b956c4-c6a2-44de-a64e-597bdbadcf6f/128/index_files/9b533761-ee4e-4807-92a8-63216e30db3f.png)

![img](file:///D:/Documents/My Knowledge/temp/02b956c4-c6a2-44de-a64e-597bdbadcf6f/128/index_files/113d3e29-570d-4fea-adc3-1dd44a9fc0c9.png)

# 二、Collection接口方法

![img](file:///D:/Documents/My Knowledge/temp/02b956c4-c6a2-44de-a64e-597bdbadcf6f/128/index_files/1581d47d-7efc-46ff-b777-92cf18fe0516.png)

![img](file:///D:/Documents/My Knowledge/temp/02b956c4-c6a2-44de-a64e-597bdbadcf6f/128/index_files/f39fa050-ad62-4495-8392-0763635f6c8f.png)

![img](file:///D:/Documents/My Knowledge/temp/02b956c4-c6a2-44de-a64e-597bdbadcf6f/128/index_files/fdf52c06-feb0-47a2-8182-ad7ee9bc1084.png)

```java
    @Test
    public void test1(){
        Collection coll = new ArrayList();

        //add(Object e):将元素e添加到集合coll中
        coll.add("AA");
        coll.add("BB");
        coll.add(123);//自动装箱
        coll.add(new Date());

        //size():获取添加的元素的个数
        System.out.println(coll.size());//4

        //addAll(Collection coll1):将coll1集合中的元素添加到当前的集合中
        Collection coll1 = new ArrayList();
        coll1.add(456);
        coll1.add("CC");
        coll.addAll(coll1);

        System.out.println(coll.size());//6
        System.out.println(coll);// [AA, BB, 123, Wed Jul 22 17:02:51 CST 2020, 456, CC]

        //clear():清空集合元素
        coll.clear();

        //isEmpty():判断当前集合是否为空
        System.out.println(coll.isEmpty()); // true

    }
```

```java
// 向Collection接口的实现类的对象中添加数据obj时，要求obj所在类要重写equals()
// 平时项目中使用lombok下的@Data注解，不需要手动重写，它的作用：
    // 所有属性的get和set方法
    // toString 方法
    // hashCode方法
    // equals方法
public class Person {

    private String name;
    private int age;

    public Person() {
    }

    public Person(String name, int age) {
        this.name = name;
        this.age = age;
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

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        System.out.println("Person equals()....");
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return age == person.age &&
                Objects.equals(name, person.name);
    }

    @Override
    public int hashCode() {

        return Objects.hash(name, age);
    }
}
```

```java
    @Test
    public void test1(){
        Collection coll = new ArrayList();
        coll.add(123);
        coll.add(456);

        coll.add(new Person("Jerry",20));
        coll.add(new String("Tom"));
        coll.add(false);
        //1.contains(Object obj):判断当前集合中是否包含obj
        //我们在判断时会调用obj对象所在类的equals()。
        boolean contains = coll.contains(123);
        System.out.println(contains); // true
        System.out.println(coll.contains(new String("Tom"))); // true String重写了equals方法，比较的是值是否相等
        System.out.println(coll.contains(new Person("Jerry",20)));
        // Person equals().... 第一次比较123
        // Person equals().... 第二次比较456
        // Person equals().... 第二次比较 Person,Person重写了equals方法，比较相同
        // true

        //2.containsAll(Collection coll1):判断形参coll1中的所有元素是否都存在于当前集合中。
        Collection coll1 = Arrays.asList(123,4567);
        System.out.println(coll.containsAll(coll1)); // false
    }
```



# 三、Iterator迭代器接口

## 1、使用 Iterator 接口遍历集合元素

![img](file:///D:/Documents/My Knowledge/temp/02b956c4-c6a2-44de-a64e-597bdbadcf6f/128/index_files/f467446d-4553-4ee5-a2af-6aa1aa6f8cd5.png) 

```java
        Collection coll = new ArrayList();
        coll.add(123);
        coll.add(456);
        coll.add(new Person("Jerry", 20));
        coll.add(new String("Tom"));
        coll.add(false);

        Iterator iterator = coll.iterator();
        ////hasNext():判断是否还有下一个元素
        while (iterator.hasNext()) {
            //next():①指针下移 ②将下移以后集合位置上的元素返回
            System.out.println(iterator.next());
        }
```



## 2、Iterator 接口的方法

![img](file:///D:/Documents/My Knowledge/temp/02b956c4-c6a2-44de-a64e-597bdbadcf6f/128/index_files/8478b088-4904-4feb-a880-0038466614df.png)

![img](file:///D:/Documents/My Knowledge/temp/02b956c4-c6a2-44de-a64e-597bdbadcf6f/128/index_files/4c8ae04a-710c-4be9-a4b5-601089c877f4.png) 

```java
    /**
     * Iterator遍历集合的两种错误写法
     */
    @Test
    public void test2() {

        Collection coll = new ArrayList();
        coll.add(123);
        coll.add(456);
        coll.add(new Person("Jerry", 20));
        coll.add(new String("Tom"));
        coll.add(false);

        //错误方式一：
//        Iterator iterator = coll.iterator();
//        while((iterator.next()) != null){
//            System.out.println(iterator.next());
//        }

        //错误方式二：
        //集合对象每次调用iterator()方法都得到一个全新的迭代器对象，默认游标都在集合的第一个元素之前。
        while (coll.iterator().hasNext()) {
            System.out.println(coll.iterator().next());
        }
    }
```



## 3、Iterator接口remove()方法

![img](file:///D:/Documents/My Knowledge/temp/02b956c4-c6a2-44de-a64e-597bdbadcf6f/128/index_files/59d389a9-4aab-4c5f-bdbd-fef438078766.png)

## 4、使用foreach循环遍历集合元素

![img](file:///D:/Documents/My Knowledge/temp/02b956c4-c6a2-44de-a64e-597bdbadcf6f/128/index_files/d8f281e2-a0d0-46b7-bd3d-4e70dbadf6f0.png)

# 四、Collection子接口一：List

## 1、List 接口概述

![img](file:///D:/Documents/My Knowledge/temp/02b956c4-c6a2-44de-a64e-597bdbadcf6f/128/index_files/17e86185-b21c-42a4-9691-e82a012eeea3.png)

## 2、List 接口方法

![img](file:///D:/Documents/My Knowledge/temp/02b956c4-c6a2-44de-a64e-597bdbadcf6f/128/index_files/4aaf0f54-04ba-42ef-ba0b-30adc8ceefe1.png)

## 3、List 实现类之一：ArrayList

![img](file:///D:/Documents/My Knowledge/temp/02b956c4-c6a2-44de-a64e-597bdbadcf6f/128/index_files/a725e997-fd12-4188-a80e-0e128c8d4cd7.png) 

```java
    // 区分List中remove(int index)和remove(Object obj)
    @Test
    public void testListRemove() {
        List list = new ArrayList();
        list.add(1);
        list.add(2);
        list.add(3);
        updateList(list);
        System.out.println(list);
    }
    private static void updateList(List list) {
        list.remove(2); //  [1, 2]
        list.remove(new Integer(2)); //  [1, 3]
    }
```



## 4、List 实现类之二：LinkedList

![img](file:///D:/Documents/My Knowledge/temp/02b956c4-c6a2-44de-a64e-597bdbadcf6f/128/index_files/ca83f3b8-0a61-44f7-ab25-85a988fb48fc.jpg)

![img](file:///D:/Documents/My Knowledge/temp/02b956c4-c6a2-44de-a64e-597bdbadcf6f/128/index_files/c3c72fa2-9231-4e3b-94eb-1c221235132a.png)

## 5、List实现类之三：Vector

![img](file:///D:/Documents/My Knowledge/temp/02b956c4-c6a2-44de-a64e-597bdbadcf6f/128/index_files/925e9c76-7191-495d-ba88-f618799585b9.png)

```java
jdk7和jdk8中通过Vector()构造器创建对象时，底层都创建了长度为10的数组。在扩容方面，默认扩容为原来的数组长度的2倍。
```

# 五、Collection子接口二：Set

## 1、Set接口概述

![img](file:///D:/Documents/My Knowledge/temp/02b956c4-c6a2-44de-a64e-597bdbadcf6f/128/index_files/5aceed93-79f3-475c-a303-e0d23b9894c1.png)

```java
1. Set接口中没有额外定义新的方法，使用的都是Collection中声明过的方法。

2. 要求：向Set(主要指：HashSet、LinkedHashSet)中添加的数据，其所在的类一定要重写hashCode()和equals()
   要求：重写的hashCode()和equals()尽可能保持一致性：相等的对象必须具有相等的散列码
   重写两个方法的小技巧：对象中用作 equals() 方法比较的 Field，都应该用来计算 hashCode 值。
```

1. 无序性：不等于随机性。存储的数据在底层数组中并非按照数组索引的顺序添加，而是根据数据的哈希值决定的。

2. 不可重复性：保证添加的元素按照equals()判断时，不能返回true.即：相同的元素只能添加一个。



## 2、Set 实现类之一：HashSet

![img](file:///D:/Documents/My Knowledge/temp/02b956c4-c6a2-44de-a64e-597bdbadcf6f/128/index_files/d21da5db-b22b-445b-bc3d-152260b60534.png)

![img](file:///D:/Documents/My Knowledge/temp/02b956c4-c6a2-44de-a64e-597bdbadcf6f/128/index_files/5e552b28-6a32-4f24-8fe8-8e69814ce4e4.png)

![img](file:///D:/Documents/My Knowledge/temp/02b956c4-c6a2-44de-a64e-597bdbadcf6f/128/index_files/25d10b65-4887-4bc6-aaee-b4da5249943a.jpg)

### （1）重写 hashCode()方法的基本原则

![img](file:///D:/Documents/My Knowledge/temp/02b956c4-c6a2-44de-a64e-597bdbadcf6f/128/index_files/673ea291-e4b7-4f65-8a67-8b13c2b623e1.png)

### （2）重写 equals() 方法的基本原则

![img](file:///D:/Documents/My Knowledge/temp/02b956c4-c6a2-44de-a64e-597bdbadcf6f/128/index_files/dca613f9-fa30-4f81-baa7-e3c99410abbc.png)

### （3）Eclipse/IDEA 工具里hashCode()的重写

![img](file:///D:/Documents/My Knowledge/temp/02b956c4-c6a2-44de-a64e-597bdbadcf6f/128/index_files/7c189bef-8e1d-4d13-8114-a7025d12248f.png) 

```java
package com.atguigu.java1;

/**
 * @author shkstart
 * @create 2019 下午 3:56
 */
public class User implements Comparable {
    private String name;
    private int age;

    public User() {
    }

    public User(String name, int age) {
        this.name = name;
        this.age = age;
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

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        System.out.println("User equals()....");
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (age != user.age) return false;
        return name != null ? name.equals(user.name) : user.name == null;
    }

    @Override
    public int hashCode() { //return name.hashCode() + age;
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + age;
        return result;
    }

    //按照姓名从大到小排列,年龄从小到大排列
    @Override
    public int compareTo(Object o) {
        if (o instanceof User) {
            User user = (User) o;
//            return -this.name.compareTo(user.name);
            int compare = -this.name.compareTo(user.name);
            if (compare != 0) {
                return compare;
            } else {
                return Integer.compare(this.age, user.age);
            }
        } else {
            throw new RuntimeException("输入的类型不匹配");
        }

    }
}

```

```java
    /*
    一、Set：存储无序的、不可重复的数据
    以HashSet为例说明：
    1. 无序性：不等于随机性。存储的数据在底层数组中并非按照数组索引的顺序添加，而是根据数据的哈希值决定的。

    2. 不可重复性：保证添加的元素按照equals()判断时，不能返回true.即：相同的元素只能添加一个。

    二、添加元素的过程：以HashSet为例：
        我们向HashSet中添加元素a,首先调用元素a所在类的hashCode()方法，计算元素a的哈希值，
        此哈希值接着通过某种算法计算出在HashSet底层数组中的存放位置（即为：索引位置），判断
        数组此位置上是否已经有元素：
            如果此位置上没有其他元素，则元素a添加成功。 --->情况1
            如果此位置上有其他元素b(或以链表形式存在的多个元素），则比较元素a与元素b的hash值：
                如果hash值不相同，则元素a添加成功。--->情况2
                如果hash值相同，进而需要调用元素a所在类的equals()方法：
                       equals()返回true,元素a添加失败
                       equals()返回false,则元素a添加成功。--->情况3

        对于添加成功的情况2和情况3而言：元素a 与已经存在指定索引位置上数据以链表的方式存储。
        jdk 7 :元素a放到数组中，指向原来的元素。
        jdk 8 :原来的元素在数组中，指向元素a
        总结：七上八下

        HashSet底层：数组+链表的结构。
     */
    @Test
    public void test1(){
        Set set = new HashSet();
        set.add(456);
        set.add(123);
        set.add(123);
        set.add("AA");
        set.add("CC");
        set.add(new User("Tom",12));
        set.add(new User("Tom",12));
        set.add(129);

        Iterator iterator = set.iterator();
        while(iterator.hasNext()){
            System.out.println(iterator.next());
        }
    }
```

![img](file:///D:/Documents/My Knowledge/temp/02b956c4-c6a2-44de-a64e-597bdbadcf6f/128/index_files/ddeb3c2f-39f1-4dc6-b027-8436563b4dae.png)

## 3、Set 实现类之二：LinkedHashSet

![img](file:///D:/Documents/My Knowledge/temp/02b956c4-c6a2-44de-a64e-597bdbadcf6f/128/index_files/48d17336-7f21-4cb0-a7ba-6a1777148c27.png)

![img](file:///D:/Documents/My Knowledge/temp/02b956c4-c6a2-44de-a64e-597bdbadcf6f/128/index_files/0500d1e0-cbd0-46f3-b561-a916114fe98a.png) 

```java
    //LinkedHashSet作为HashSet的子类，在添加数据的同时，每个数据还维护了两个引用，记录此数据前一个数据和后一个数据。
    //优点：对于频繁的遍历操作，LinkedHashSet效率高于HashSet
    @Test
    public void test2(){
        Set set = new LinkedHashSet();
        set.add(456);
        set.add(123);
        set.add(123);
        set.add("AA");
        set.add("CC");
        set.add(new User("Tom",12));
        set.add(new User("Tom",12));
        set.add(129);

        Iterator iterator = set.iterator();
        while(iterator.hasNext()){
            System.out.println(iterator.next());
        }
    }
```

![img](file:///D:/Documents/My Knowledge/temp/02b956c4-c6a2-44de-a64e-597bdbadcf6f/128/index_files/6bbb6d73-8852-461e-9f0d-cad492f8c247.png)

## 4、Set 实现类之三：TreeSet

![img](file:///D:/Documents/My Knowledge/temp/02b956c4-c6a2-44de-a64e-597bdbadcf6f/128/index_files/c5b5ebc3-2390-4084-b2dd-a3a5993f9826.png)

![img](file:///D:/Documents/My Knowledge/temp/02b956c4-c6a2-44de-a64e-597bdbadcf6f/128/index_files/c8cef562-2628-46cd-9695-98830dec5d44.jpg)

### （1）排序：自然排序

![img](file:///D:/Documents/My Knowledge/temp/02b956c4-c6a2-44de-a64e-597bdbadcf6f/128/index_files/51fe742e-09dd-4f00-a134-ff720309c6e1.png)

![img](file:///D:/Documents/My Knowledge/temp/02b956c4-c6a2-44de-a64e-597bdbadcf6f/128/index_files/1c72a662-3ef1-4936-87f9-1e3443b44500.png) 

```java
    /*
    1.向TreeSet中添加的数据，要求是相同类的对象。
    2.两种排序方式：自然排序（实现Comparable接口） 和 定制排序（Comparator）


    3.自然排序中，比较两个对象是否相同的标准为：compareTo()返回0.不再是equals().
    4.定制排序中，比较两个对象是否相同的标准为：compare()返回0.不再是equals().
     */
    @Test
    public void test1() {
        TreeSet set = new TreeSet();

        //失败：不能添加不同类的对象
//        set.add(123);
//        set.add(456);
//        set.add("AA");
//        set.add(new User("Tom",12));

        //举例一：
//        set.add(34);
//        set.add(-34);
//        set.add(43);
//        set.add(11);
//        set.add(8);

        //举例二：
        set.add(new User("Tom", 12));
        set.add(new User("Jerry", 32));
        set.add(new User("Jim", 2));
        set.add(new User("Mike", 65));
        set.add(new User("Jack", 33));
        set.add(new User("Jack", 56));


        Iterator iterator = set.iterator();
        while (iterator.hasNext()) {
            System.out.println(iterator.next());
            // 自然排序，User实现了Comparable接口，按照姓名从大到小排列,年龄从小到大排列
        }

    }
```

![img](file:///D:/Documents/My Knowledge/temp/02b956c4-c6a2-44de-a64e-597bdbadcf6f/128/index_files/a979302d-fba9-4a5c-91c0-7a8f15ad671a.png)

### （2）排序：定制排序

![img](file:///D:/Documents/My Knowledge/temp/02b956c4-c6a2-44de-a64e-597bdbadcf6f/128/index_files/76b64525-cefe-4bda-9c3d-f41e3eb4b523.png) 

```java
    @Test
    public void test2() {
        Comparator com = new Comparator() {
            //按照年龄从小到大排列
            @Override
            public int compare(Object o1, Object o2) {
                if (o1 instanceof User && o2 instanceof User) {
                    User u1 = (User) o1;
                    User u2 = (User) o2;
                    return Integer.compare(u1.getAge(), u2.getAge());
                } else {
                    throw new RuntimeException("输入的数据类型不匹配");
                }
            }
        };

        TreeSet set = new TreeSet(com);
        set.add(new User("Tom", 12));
        set.add(new User("Jerry", 32));
        set.add(new User("Jim", 2));
        set.add(new User("Mike", 65));
        set.add(new User("Mary", 33));
        set.add(new User("Jack", 33));
        set.add(new User("Jack", 56));


        Iterator iterator = set.iterator();
        while (iterator.hasNext()) {
            System.out.println(iterator.next());
            // 定制排序
        }
    }

```

![img](file:///D:/Documents/My Knowledge/temp/02b956c4-c6a2-44de-a64e-597bdbadcf6f/128/index_files/272ecd7d-c4fd-4c87-885b-b49d882ca390.png)

# 六、Map接口

![img](file:///D:/Documents/My Knowledge/temp/02b956c4-c6a2-44de-a64e-597bdbadcf6f/128/index_files/d3036f4c-f8de-40ba-ae52-f0f94870ea8e.png)

## 1、Map 接口概述

![img](file:///D:/Documents/My Knowledge/temp/02b956c4-c6a2-44de-a64e-597bdbadcf6f/128/index_files/99309e1d-e5e9-48f6-b573-122dd436c673.png)

![img](file:///D:/Documents/My Knowledge/temp/02b956c4-c6a2-44de-a64e-597bdbadcf6f/128/index_files/c09657c0-9c3e-4718-bbab-272b4cdcba87.png)

![img](file:///D:/Documents/My Knowledge/temp/02b956c4-c6a2-44de-a64e-597bdbadcf6f/128/index_files/76d5f382-547f-4ec4-8f6b-77f4f70abdae.jpg)

## 2、Map 接口常用方法

![img](file:///D:/Documents/My Knowledge/temp/02b956c4-c6a2-44de-a64e-597bdbadcf6f/128/index_files/5df6efda-17e5-48a2-a999-38ff2d32cb62.png)

![img](file:///D:/Documents/My Knowledge/temp/02b956c4-c6a2-44de-a64e-597bdbadcf6f/128/index_files/0a8a20f5-2f36-43ef-acff-cb80ff7c334d.png)

```java
    /*
     添加、删除、修改操作：
    Object put(Object key,Object value)：将指定key-value添加到(或修改)当前map对象中
    void putAll(Map m):将m中的所有key-value对存放到当前map中
    Object remove(Object key)：移除指定key的key-value对，并返回value
    void clear()：清空当前map中的所有数据
     */
    @Test
    public void test3() {
        Map map = new HashMap();
        //添加
        map.put("AA", 123);
        map.put(45, 123);
        map.put("BB", 56);
        //修改
        map.put("AA", 87);

        System.out.println(map); // {AA=87, BB=56, 45=123}

        Map map1 = new HashMap();
        map1.put("CC", 123);
        map1.put("DD", 123);

        map.putAll(map1);

        System.out.println(map); // {AA=87, BB=56, CC=123, DD=123, 45=123}

        //remove(Object key)
        Object value = map.remove("CC");
        System.out.println(value); // 123
        System.out.println(map); // {AA=87, BB=56, DD=123, 45=123}

        //clear()
        map.clear();//与map = null操作不同
        System.out.println(map.size()); // 0
        System.out.println(map);// {}
    } 
```



## 3、Map实现类之一：HashMap

![img](file:///D:/Documents/My Knowledge/temp/02b956c4-c6a2-44de-a64e-597bdbadcf6f/128/index_files/21de5614-2bd6-4f4e-a5b7-8f07788a3ae4.png)

### （1）HashMap 源码中的重要常量

![img](file:///D:/Documents/My Knowledge/temp/02b956c4-c6a2-44de-a64e-597bdbadcf6f/128/index_files/3910632e-e0ab-4682-bf89-a8e0ab712985.png) 

```java
DEFAULT_INITIAL_CAPACITY : HashMap的默认容量，16
DEFAULT_LOAD_FACTOR：HashMap的默认加载因子：0.75
threshold：扩容的临界值，=容量*填充因子：16 * 0.75 => 12
TREEIFY_THRESHOLD：Bucket中链表长度大于该默认值，转化为红黑树:8
MIN_TREEIFY_CAPACITY：桶中的Node被树化时最小的hash表容量:64
```



### （2）HashMap的存储结构

![img](file:///D:/Documents/My Knowledge/temp/02b956c4-c6a2-44de-a64e-597bdbadcf6f/128/index_files/175ba242-1c0b-4ad0-aaea-e89bbfcc6d5c.png)

#### ① JDK 1.8 之前

![img](file:///D:/Documents/My Knowledge/temp/02b956c4-c6a2-44de-a64e-597bdbadcf6f/128/index_files/f34af88a-78ac-4855-a015-9cfddd3fb792.jpg)

![img](file:///D:/Documents/My Knowledge/temp/02b956c4-c6a2-44de-a64e-597bdbadcf6f/128/index_files/3285764546.png)

![img](file:///D:/Documents/My Knowledge/temp/02b956c4-c6a2-44de-a64e-597bdbadcf6f/128/index_files/d9381e39-5238-4819-8497-a6e274c070e7.png)

#### ② JDK 1.8

![img](file:///D:/Documents/My Knowledge/temp/02b956c4-c6a2-44de-a64e-597bdbadcf6f/128/index_files/e9706a0e-05ba-4d68-bc7d-e4b84cb7cdfb.jpg)

![img](file:///D:/Documents/My Knowledge/temp/02b956c4-c6a2-44de-a64e-597bdbadcf6f/128/index_files/092cf8ab-9f5b-4c9b-a69b-78a024488725.png)

![img](file:///D:/Documents/My Knowledge/temp/02b956c4-c6a2-44de-a64e-597bdbadcf6f/128/index_files/7bc07de3-c1e3-4a8c-aae3-26ab8ea14a98.png)

![img](file:///D:/Documents/My Knowledge/temp/02b956c4-c6a2-44de-a64e-597bdbadcf6f/128/index_files/5ac14d28-f567-4d4c-af43-c8c86510a73b.png)

## 4、Map实现类之二：LinkedHashMap

## ![img](file:///D:/Documents/My Knowledge/temp/02b956c4-c6a2-44de-a64e-597bdbadcf6f/128/index_files/eb000855-495f-466d-8984-86b88eedb402.png)![img](file:///D:/Documents/My Knowledge/temp/02b956c4-c6a2-44de-a64e-597bdbadcf6f/128/index_files/a89e9d80-7933-4dfc-8750-764eafe0bf31.png) 

## 5、Map实现类之三：TreeMap

![img](file:///D:/Documents/My Knowledge/temp/02b956c4-c6a2-44de-a64e-597bdbadcf6f/128/index_files/3228128109.png) 

```java
package com.atguigu.java;

/**
 * @author shkstart
 * @create 2019 下午 3:56
 */
public class User implements Comparable {
    private String name;
    private int age;

    public User() {
    }

    public User(String name, int age) {
        this.name = name;
        this.age = age;
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

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        System.out.println("User equals()....");
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (age != user.age) return false;
        return name != null ? name.equals(user.name) : user.name == null;
    }

    @Override
    public int hashCode() { //return name.hashCode() + age;
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + age;
        return result;
    }

    //按照姓名从大到小排列,年龄从小到大排列
    @Override
    public int compareTo(Object o) {
        if (o instanceof User) {
            User user = (User) o;
//            return -this.name.compareTo(user.name);
            int compare = -this.name.compareTo(user.name);
            if (compare != 0) {
                return compare;
            } else {
                return Integer.compare(this.age, user.age);
            }
        } else {
            throw new RuntimeException("输入的类型不匹配");
        }

    }
}
 
```

```java
    //向TreeMap中添加key-value，要求key必须是由同一个类创建的对象
    //因为要按照key进行排序：自然排序 、定制排序
    //自然排序
    @Test
    public void test1() {
        TreeMap map = new TreeMap();
        User u1 = new User("Tom", 23);
        User u2 = new User("Jerry", 32);
        User u3 = new User("Jack", 20);
        User u4 = new User("Rose", 18);

        map.put(u1, 98);
        map.put(u2, 89);
        map.put(u3, 76);
        map.put(u4, 100);

        Set entrySet = map.entrySet();
        Iterator iterator1 = entrySet.iterator();
        while (iterator1.hasNext()) {
            Object obj = iterator1.next();
            Map.Entry entry = (Map.Entry) obj;
            System.out.println(entry.getKey() + "---->" + entry.getValue());
        }
        // User{name='Tom', age=23}---->98
        // User{name='Rose', age=18}---->100
        // User{name='Jerry', age=32}---->89
        // User{name='Jack', age=20}---->76
    }

    //定制排序
    @Test
    public void test2() {
        TreeMap map = new TreeMap(new Comparator() {
            @Override
            public int compare(Object o1, Object o2) {
                if (o1 instanceof User && o2 instanceof User) {
                    User u1 = (User) o1;
                    User u2 = (User) o2;
                    return Integer.compare(u1.getAge(), u2.getAge());
                }
                throw new RuntimeException("输入的类型不匹配！");
            }
        });
        User u1 = new User("Tom", 23);
        User u2 = new User("Jerry", 32);
        User u3 = new User("Jack", 20);
        User u4 = new User("Rose", 18);

        map.put(u1, 98);
        map.put(u2, 89);
        map.put(u3, 76);
        map.put(u4, 100);

        Set entrySet = map.entrySet();
        Iterator iterator1 = entrySet.iterator();
        while (iterator1.hasNext()) {
            Object obj = iterator1.next();
            Map.Entry entry = (Map.Entry) obj;
            System.out.println(entry.getKey() + "---->" + entry.getValue());
        }
        //User{name='Rose', age=18}---->100
        //User{name='Jack', age=20}---->76
        //User{name='Tom', age=23}---->98
        //User{name='Jerry', age=32}---->89
    }
```



## 6、Map实现类之四：Hashtable

![img](file:///D:/Documents/My Knowledge/temp/02b956c4-c6a2-44de-a64e-597bdbadcf6f/128/index_files/2615203f-8ad5-4bf6-906e-dd3de4b725e2.png)

## 7、Map实现类之五：Properties

![img](file:///D:/Documents/My Knowledge/temp/02b956c4-c6a2-44de-a64e-597bdbadcf6f/128/index_files/f6eada7b-bdfa-4569-b2ca-784a52d95fb0.png)

# 七、Collections工具类

## 1、Collections工具类概述

![img](file:///D:/Documents/My Knowledge/temp/02b956c4-c6a2-44de-a64e-597bdbadcf6f/128/index_files/2cdf8254-c6a4-430c-984e-8efe050b7ac9.png)

## 2、Collections 常用方法

![img](file:///D:/Documents/My Knowledge/temp/02b956c4-c6a2-44de-a64e-597bdbadcf6f/128/index_files/444722d3-c983-4c95-9840-f96b2b27a32d.png)

## 3、Collections常用方法：同步控制

![img](file:///D:/Documents/My Knowledge/temp/02b956c4-c6a2-44de-a64e-597bdbadcf6f/128/index_files/3398ba0c-c4c3-4cb0-b841-480300b0d8d6.png)

## 4、补充：Enumeration

![img](file:///D:/Documents/My Knowledge/temp/02b956c4-c6a2-44de-a64e-597bdbadcf6f/128/index_files/bab91f91-fa43-45b1-9118-e0b06af1bd58.png) 

```java
/*
reverse(List)：反转 List 中元素的顺序
shuffle(List)：对 List 集合元素进行随机排序
sort(List)：根据元素的自然顺序对指定 List 集合元素按升序排序
sort(List，Comparator)：根据指定的 Comparator 产生的顺序对 List 集合元素进行排序
swap(List，int， int)：将指定 list 集合中的 i 处元素和 j 处元素进行交换

Object max(Collection)：根据元素的自然顺序，返回给定集合中的最大元素
Object max(Collection，Comparator)：根据 Comparator 指定的顺序，返回给定集合中的最大元素
Object min(Collection)
Object min(Collection，Comparator)
int frequency(Collection，Object)：返回指定集合中指定元素的出现次数
void copy(List dest,List src)：将src中的内容复制到dest中
boolean replaceAll(List list，Object oldVal，Object newVal)：使用新值替换 List 对象的所有旧值
 */
    @Test
    public void test2(){
        List list = new ArrayList();
        list.add(123);
        list.add(43);
        list.add(765);
        list.add(-97);
        list.add(0);

        //报异常：IndexOutOfBoundsException("Source does not fit in dest")
//        List dest = new ArrayList(); // size=0
//        Collections.copy(dest,list);
        //正确的：
        List dest = Arrays.asList(new Object[list.size()]); // size=5
        System.out.println(dest.size());//list.size();
        Collections.copy(dest,list);

        System.out.println(dest);


        /*
        Collections 类中提供了多个 synchronizedXxx() 方法，
        该方法可使将指定集合包装成线程同步的集合，从而可以解决
        多线程并发访问集合时的线程安全问题
         */
        //返回的list1即为线程安全的List
        List list1 = Collections.synchronizedList(list);


    }

    @Test
    public void test1(){
        List list = new ArrayList();
        list.add(123);
        list.add(43);
        list.add(765);
        list.add(765);
        list.add(765);
        list.add(-97);
        list.add(0);

        System.out.println(list);

//        Collections.reverse(list);
//        Collections.shuffle(list);
//        Collections.sort(list);
//        Collections.swap(list,1,2);
        int frequency = Collections.frequency(list, 123);

        System.out.println(list);
        System.out.println(frequency); // 1

    }

```



# 八、每日练习

## 1、说说你所理解的集合框架都有哪些接口，存储数据的特点是什么？ 

```java
/**
 *      |----Collection接口：单列集合，用来存储一个一个的对象
 *          |----List接口：存储有序的、可重复的数据。  -->“动态”数组
 *              |----ArrayList、LinkedList、Vector
 *
 *          |----Set接口：存储无序的、不可重复的数据   -->高中讲的“集合”
 *              |----HashSet、LinkedHashSet、TreeSet
 *
 *      |----Map接口：双列集合，用来存储一对(key - value)一对的数据   -->高中函数：y = f(x)
 *              |----HashMap、LinkedHashMap、TreeMap、Hashtable、Properties
 */
```



## 2、请问ArrayList/LinkedList/Vector的异同？谈谈你的理解？ArrayList底层是什么？扩容机制？Vector和ArrayList的最大区别?

同：三个类都是实现了List接口，存储数据的特点相同：存储有序的、可重复的数据

![img](file:///D:/Documents/My Knowledge/temp/02b956c4-c6a2-44de-a64e-597bdbadcf6f/128/index_files/c1034db4-a900-4d52-923d-3219e1dd5f5b.png)

## 3、集合Collection中存储的如果是自定义类的对象，需要自定义类重写哪个方法？为什么？

equals()方法。 contains(Object obj) /remove(Object obj)等方法，我们在判断时会调用obj对象所在类的equals()。

List：equals()方法

Set：(HashSet、LinkedHashSet为例)：equals()、hashCode()

​     (TreeSet为例)：Comparable：compareTo(Object obj)自然排序

​                  Comparator：compare(Object o1,Object o2)定制排序

## 4、Set存储数据的特点是什么？常见的实现类有什么？说明一下彼此的特点？

无序且不重复。

- 无序性：不等于随机性。存储的数据在底层数组中并非按照数组索引的顺序添加，而是根据数据的哈希值决定的。
- 不可重复性：保证添加的元素按照equals()判断时，不能返回true.即：相同的元素只能添加一个。

HashSet：底层HashMap  

LinkedHashSet：底层LinkedHashMap  

TreeSet：底层TreeMap

## 5、Set经典练习？ 

```java
    @Test
    public void test3(){
        HashSet set = new HashSet();
        Person p1 = new Person(1001,"AA");
        Person p2 = new Person(1002,"BB");

        set.add(p1);
        set.add(p2);
        System.out.println(set);// [Person{id=1002, name='BB'}, Person{id=1001, name='AA'}]

        p1.name = "CC";
        set.remove(p1); // 找1001 CC的hash值，但是p1上的hash值还是AA的时候的值，找不到1001 CC对应的hash值，删除不了数据
        System.out.println(set);// [Person{id=1002, name='BB'}, Person{id=1001, name='CC'}]
        set.add(new Person(1001,"CC")); // p1上的hash值还是AA的时候的值,添加的时候，以1001 CC对应的hash值为索引存储，所以添加成功
        System.out.println(set);// [Person{id=1002, name='BB'}, Person{id=1001, name='CC'}, Person{id=1001, name='CC'}]
        set.add(new Person(1001,"AA"));// p1上的hash值还是AA的时候的值,添加的时候,判断出和当前p1的hash值相等，然后比较equals值，现在p1的值为CC和AA不相等，所以添加成功
        System.out.println(set);// [Person{id=1002, name='BB'}, Person{id=1001, name='CC'}, Person{id=1001, name='CC'}, Person{id=1001, name='AA'}]

    }
```



## 6、谈谈你对HashMap中put/get方法的认识？如果了解再谈谈HashMap的扩容机制？默认大小是多少？什么是负载因子(或填充比)？什么是吞吐临界值(或阈值、threshold)？

jdk7的put方法：

![img](file:///D:/Documents/My Knowledge/temp/02b956c4-c6a2-44de-a64e-597bdbadcf6f/128/index_files/e02d711a-64e4-4468-9de0-0303fe13d956.jpg)

![img](file:///D:/Documents/My Knowledge/temp/02b956c4-c6a2-44de-a64e-597bdbadcf6f/128/index_files/fdcc1c78-260e-47f3-84b1-db8a6d7d68de.png)

jdk8的put方法：

![img](file:///D:/Documents/My Knowledge/temp/02b956c4-c6a2-44de-a64e-597bdbadcf6f/128/index_files/e077397c-ff5a-4bb6-a1f3-0064274a9ac2.png)

## 7、负载因子值的大小，对HashMap 有什么影响？

![img](file:///D:/Documents/My Knowledge/temp/02b956c4-c6a2-44de-a64e-597bdbadcf6f/128/index_files/17362001-d3d7-45f3-bcb8-46b3bb7970a0.png)

## 8、Map存储数据的特点是什么？并指明key，value，entry存储数据的特点？

双列数据，存储key-value键值对数据。

key：无序的、不可重复的–>Set存储

value:无序的、可重复的 –>Collection存储

key-value:无序的、不可重复–>Set存储

## 9、描述HashMap的底层实现原理(jdk 8版)？

![img](file:///D:/Documents/My Knowledge/temp/02b956c4-c6a2-44de-a64e-597bdbadcf6f/128/index_files/be679288-7b8a-470d-a4dc-8d3e1985b655.jpg)

## 10、Collection 和 Collections的区别？

Collection是集合类的上级接口，继承于他的接口主要有Set 和List。

Collections是针对集合类的一个帮助类，他提供一系列静态方法实现对各种集合的搜索、排序、线程安全化等操作。

## 11、Set里的元素是不能重复的，那么用什么方法来区分重复与否呢? 是用==还是equals()? 它们有何区别？

Set里的元素是不能重复的，用equals()方法判读两个Set是否相等。

equals()和==方法决定引用值是否指向同一对象equals()在类中被覆盖，为的是当两个分离的对象的内容和类型相配的话，返回真值，

## 12、List, Set, Map是否继承自Collection接口？

 List，Set是，Map不是

## 13、两个对象值相同(x.equals(y) == true)，但却可有不同的hashCode，这句话对不对？

不对，有相同的hashCode

## 14、HashMap和Hashtable的区别？

- HashMap与Hashtable都实现了Map接口。由于HashMap的非线程安全性，效率上可能高于Hashtable。Hashtable的方法是Synchronize的，而HashMap不是，在多个线程访问Hashtable时，不需要自己为它的方法实现同步，而HashMap 就必须为之提供外同步。
- HashMap允许将null作为一个entry的key或者value，而Hashtable不允许。
- HashMap把Hashtable的contains方法去掉了，改成containsvalue和containsKey。因为contains方法容易让人引起误解。 
- Hashtable继承自Dictionary类，而HashMap是Java1.2引进的Map interface的一个实现。
- Hashtable和HashMap采用的hash/rehash算法都大概一样，所以性能不会有很大的差异。