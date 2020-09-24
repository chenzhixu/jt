package com.jt.annotation;

import org.aspectj.lang.annotation.Around;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)  //@Retention注解用于定义注解何时生效
@Target({ElementType.METHOD}) //只对方法有效  @Target注解定义注解可以描述的对象
@Documented
public @interface CacheFind {


    String key();  //此属性为必须添加的 ，因为key应该标识业务属性
    int seconds() default  0; //设定超时时间，默认为不超时
}
