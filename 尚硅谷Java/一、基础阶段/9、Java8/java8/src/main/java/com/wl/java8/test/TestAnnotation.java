package com.wl.java8.test;

/**
 * @author 王柳
 * @date 2020/5/29 12:03
 */
public class TestAnnotation {
    @MyAnnotation("Hello")
    @MyAnnotation("World")
    public void show(@MyAnnotation("abc") String a) {
    }
}
