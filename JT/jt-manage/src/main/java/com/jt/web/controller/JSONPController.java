package com.jt.web.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.JSONPObject;
import com.jt.pojo.ItemDesc;
import com.jt.util.ObjectMapperUtil;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 此类用于测试跨域访问
 */


@RestController
public class JSONPController {


    /** 测试跨域访问是否成功
     * Request URL: http://manage.jt.com/web/testJSONP?callback=jQuery111106573617408148174_1600140269904&_=1600140269905
     * 返回值的应该是经过特殊格式封装的数据.  callback(JSON数据)
     */


    @RequestMapping("/web/testJSONP")
    public JSONPObject jsonp(String callback){
        ItemDesc itemDesc=new ItemDesc();
        itemDesc.setItemId(100L).setItemDesc("测试JSONP API!!");
        //将对象转换为json
       JSONPObject  jsonpObject=new JSONPObject(callback,itemDesc);
       return  jsonpObject;

    }





/*    @RequestMapping("/web/testJSONP")
    public String testJSONP(String callback){
        ItemDesc itemDesc=new ItemDesc();
        itemDesc.setItemId(100L).setItemDesc("jsonp跨域测试成功！！");
        //将对象转换为json
       String json=ObjectMapperUtil.toJSON(itemDesc);
        return callback+"("+json+")";

    }*/



}
