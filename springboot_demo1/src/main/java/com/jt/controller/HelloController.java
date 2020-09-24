package com.jt.controller;

import com.jt.pojo.User;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @RequestMapping("/getMsg")
    public String getMsg(){
        User user=new User();
        user.setId(1).setAge(2).setName("chenwen").setSex("男");
       
        return "恭喜您学会了项目发布，哈哈哈！！！";
    }

}
