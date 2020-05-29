package com.wl.java8.test;

import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.ElementType.LOCAL_VARIABLE;

/**
 * @author 王柳
 * @date 2020/5/29 11:51
 */
@Repeatable(MyAnnotations.class) // 可重复注解的关键
@Target({TYPE, FIELD, METHOD, PARAMETER, CONSTRUCTOR, LOCAL_VARIABLE,TYPE_PARAMETER}) //TYPE_PARAMETER可在类型参数上加注解
@Retention(RetentionPolicy.RUNTIME)
public @interface MyAnnotation {
    String value() default "atguigu";
}
