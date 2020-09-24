package com.jt.controller;


import com.fasterxml.jackson.databind.util.JSONPObject;
import com.jt.pojo.User;
import com.jt.service.UserService;
import com.jt.vo.SysResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.JedisCluster;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
//服务器端程序要求返回的都是JSON数据 所以使用@RestController
@RestController //等价于@Controller注解+@ResponseBody注解      在类上加了此注解，等效于在所有方法加了@ResponseBody注解 表示将方法返回值都转为json数据并输出
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;


    @Autowired
    private JedisCluster jedisCluster;




    /**
     * 根据ticket信息查询用户的json信息 jsonp请求 返回值使用特定的对象封装.
     * url地址:http://sso.jt.com/user/query/ca620491866a42e596b29ee52fc27aff?callback=jsonp1600333068471&_=1600333068521
     */
    @RequestMapping("/query/{ticket}")
    public JSONPObject findUserByTicket(@PathVariable String ticket, HttpServletResponse response, String callback){
        if(jedisCluster.exists(ticket)){
            //可以正确返回
            String userJSON = jedisCluster.get(ticket);
            return new JSONPObject(callback, SysResult.success(userJSON));
        }else{
            //如果根据ticket查询有误,则应该删除Cookie信息.  删除cookie信息要跟原有cookie保存一致
            Cookie cookie = new Cookie("JT_TICKET","");
            cookie.setDomain("jt.com");
            cookie.setPath("/");
            cookie.setMaxAge(0);
            response.addCookie(cookie);  //将cooikie信息添加到响应对象中，带回浏览器端进行博爱村
            return new JSONPObject(callback, SysResult.fail());
        }

    }




    /**
     * 测试httpClient调用方式
     *  请求url地址: http://sso.jt.com/user/findUserById/"+userId
     *  返回:  user对象的JSON数据
     * @param userId
     * @return
     */
    @RequestMapping("/findUserById/{userId}")
    public User findUserById(@PathVariable  Long userId){
        return  userService.findUserById(userId);

    }


    /**
     * 需求分析(业务说明)：校验用户的数据是否可用
     * 请求URL地址	http://sso.jt.com/user/check/{param}/{type}
     * 请求参数： {需要校验的数据(param)}/{校验的类型是谁(type)}/
     * 返回值结果类型： SysResult对象
     * JSONP的跨域请求  返回值必须经过特殊的格式封装 callback(json)
     * @param param
     * @param type
     * @return
     */
    @RequestMapping("/check/{param}/{type}")
    public JSONPObject checkUser(@PathVariable String param, @PathVariable Integer type,String callback){
        //根据用户信息查询数据库获取响应记录，从而校验数据库中是否存在该数据
        Boolean flag=userService.checkUser(param,type);
        SysResult sysResult=SysResult.success(flag); //存在则为true  不存在则为false
        JSONPObject jsonpObject=new JSONPObject(callback,sysResult);
        return  jsonpObject;

    }










    @RequestMapping("/findAll")
    public List<User> findAll(){
        List<User> userList=userService.findAll();
        return  userList;
    }



    /*demo测试*/
    @RequestMapping("/getMsg")
    public String  getMsg(){
        return "sso单点登录系统正常";
    }

}
