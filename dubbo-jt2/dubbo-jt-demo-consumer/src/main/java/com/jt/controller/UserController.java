package com.jt.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.jt.pojo.User;
import com.jt.dubbo.service.UserService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserController {

    //利用dubbo的方式为接口创建代理对象 利用rpc调用
    //check=false 表示先启动提供者/消费者都没有关系
    @Reference(check = false,timeout = 3000,loadbalance = "leastactive")	 //loadbalance="leastactive"  表示挑选负载压力小的服务器进行访问
    private UserService userService;

    /**
     * Dubbo框架调用特点:远程RPC调用就像调用自己本地服务一样简单
     * @return
     */
@RequestMapping("/findAll")
    public List<User> findAll(){
    return  userService.findAll();//远程调用时传递的对象数据必须序列化.
}



    @RequestMapping("/addUser/{name}/{sex}/{age}")
    public String saveUser(User user){ //如果{}变量名 与属性的名称一致,则可以自动的赋值.
        System.out.println(user);
       userService.addUser(user);
       return  "用户入库成功！！";


}
    @RequestMapping("/deleteUser/userId/{userId}")
    public String deleteUser(@PathVariable  Integer userId){
        System.out.println(userId);
        userService.deleteUser(userId);
       return  "删除用户成功";

}



    @RequestMapping("/updateUser/{name}/{age}/{sex}/{userId}")
    public String deleteUser(User user,@PathVariable  Integer userId){//如果{}变量名 与属性的名称一致,则可以自动的赋值.
    userService.updateUser(user,userId);
        System.out.println(userId);
        System.out.println(user);
        return  "修改用户成功";

}


@RequestMapping("/findUserById/{userId}")
    public User finduserById(@PathVariable  Integer userId){
     return  userService.findUserById(userId);
}


}
