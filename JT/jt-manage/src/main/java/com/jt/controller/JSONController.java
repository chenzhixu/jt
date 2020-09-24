package com.jt.controller;

import com.jt.pojo.JSONPOJO;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class JSONController {

    /**
     * 获取json数据
     * 原理说明: 对象转化为json串时调用的是对象的getXxx()方法.
     * 调用步骤: 1.获取所有的getId()
     *          2.去掉开头的get之后(Id)将剩余的字母首字母小写(id),当做key
     *          3.调用getId()方法,获取属性的值,之后形成数据的拼接.
     *          {"id":"1","name":"tomcat"}

     * json转化为对象:
     *          json串:{"id":"1","name":"tomcat"}
     *          1.传递xxx.class的类型 可以转化为空对象.
     *          2.从json串中获取第一个key=id
     *          3.之后为id拼接字符串形成set方法setId().
     *          4.调用对象身上的set方法完成赋值操作.
     */

    @RequestMapping("/getJSON")
    public JSONPOJO getJSON(){
        JSONPOJO jsonpojo=new JSONPOJO();
        return jsonpojo;
    }
}
