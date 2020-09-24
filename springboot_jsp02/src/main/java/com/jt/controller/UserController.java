package com.jt.controller;

import com.jt.mapper.UserMapper;
import com.jt.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
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
    //public String findAll(HttpServletRequest reuqest) { //复习： model是SpringMVC中提供操作request对象的API  重点注意：model不是域对象
    // java web中一共有的四个域对象 pageContext<request（常用）< session（常用）< application
    public String findAll(Model model) {
        //1.从数据库中获取的数据
        List<User> userList = userMapper.selectList(null);
        //2.将userList集合保存到域中,之后页面取值
        //方式一  使用request域对象，将数据存储到域中，然后再进行取值。
        //reuqest.setAttribute("userList",userList);
        //方式二：使用model将数据存储到request域中
        model.addAttribute("userList", userList);
        //model调用的是request对象

        //返回页面逻辑名称
        return "userList";
    }

    //跳转userAjax页面
    @RequestMapping("/ajax")
    public String ajax(){
        return "userAjax";

    }


    //实现ajax业务调用。返回页面列表数据
    @RequestMapping("/findAjax")
    @ResponseBody
    public List<User> findAjax(){
        List<User> userList=userMapper.selectList(null);
        return userList;

    }


    //http://localhost:8090/findAjax2?id=10&name=tomcat
    @RequestMapping("/findAjax2")
    @ResponseBody
    public String findAjax2(Integer id,String name){
        System.out.println("用户新增成功"+id+" "+name);
        return "业务调用成功";
    }


    @RequestMapping("/findAjax3")
    @ResponseBody
    public String findAjax3(Integer id,String name){
        System.out.println("用户新增成功!!!!"+id+" "+name);
        return "业务调用成功";
    }


    @RequestMapping("/findAjax4")
    @ResponseBody
    public String findAjax4(Integer id,String name){
        System.out.println("用户新增成功!!!!"+id+" "+name);
        return "业务调用成功";
    }




}
