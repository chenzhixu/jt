package com.jt.controller;

import com.jt.mapper.UserMapper;
import com.jt.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller  //执行视图解析器代理.
//@RestController //一般适用于ajax 不走视图解析器. 并且返回JSON数据.
public class UserController {
    @Autowired
    private UserMapper userMapper;

    /**
     * 需求1: 请求路径localhost:8090/findAll   跳转到userList.jsp页面中
     * 页面取值方式: ${userList}
     */
    @RequestMapping("/findAll")
    public String findAll(Model model){ //model是SpringMVC中提供操作request对象的API

        //1.从数据库中获取的数据
        List<User> userList = userMapper.selectList(null);
        //2.将userList集合保存到域中,之后页面取值
        model.addAttribute("userList",userList);
        //model调用的是request对象
        //返回页面逻辑名称
        return "userList";
    }


    //跳转ajax页面
    @RequestMapping("/ajax")
    public String toAjax(){
        return "userAjax";
    }

    //实现ajax业务调用,返回页面列表数据.
    @RequestMapping("/findAjax")
    @ResponseBody
    public List<User> findAjax(){
        return userMapper.selectList(null);
    }

    //http://localhost:8090/findAjax2?id=1&name=tomcat
    @RequestMapping("/findAjax2")
    @ResponseBody //直接返回原字符串, ajax请求的结束.
    public String findAjax2(Integer id,String name){
        System.out.println("用户新增成功!!!"+id+" "+name);
        return "业务调用成功!!!!";
    }

    @RequestMapping("/doAjaxPost")
    @ResponseBody //直接返回原字符串, ajax请求的结束.
    public String doAjaxPost(Integer id,String name){
        System.out.println("用户新增成功!!!"+id+" "+name);
        return "业务调用成功";


    }
}
