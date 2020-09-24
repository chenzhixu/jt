package com.jt.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.jt.pojo.User;
import com.jt.service.DubboUserService;
import com.jt.service.UserService;
import com.jt.util.CookieUtil;
import com.jt.vo.SysResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import redis.clients.jedis.JedisCluster;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.ref.ReferenceQueue;

@Controller //由于设计到页面跳转功能.
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;
    @Reference(check = false,timeout = 3000)
    private DubboUserService dubboUserService;
    @Autowired
    private JedisCluster jedisCluster;

    /**
     * 通用页面跳转实现
     * 1.http://www.jt.com/user/login.html       login.jsp页面
     * 2.http://www.jt.com/user/register.html    register.jsp页面
     */
    @RequestMapping("/{moduleName}")
    public String module(@PathVariable String moduleName){

        return moduleName;
    }

    /**
     * 为了测试httpClient 实现业务功能查询用户信息
     * url地址:http://www.jt.com/user/findUserById/7
     * 参数:  7用户ID
     * 返回值: user对象
     */
    @RequestMapping("/findUserById/{userId}")
    @ResponseBody
    public User findUserById(@PathVariable Long userId){

        return userService.findUserById(userId);
    }

    /**
     * 完成用户注册操作.
     * url地址: http://www.jt.com/user/doRegister
     * 参数:    {password:_password,username:_username,phone:_phone}
     * 返回值:  SysResult对象   返回的是JSON串
     * 业务说明:通过dubbo框架将user信息RPC传入jt-sso实现数据的入库操作.
     * */
    @RequestMapping("/doRegister")
    @ResponseBody
    public SysResult saveUser(User user){

        dubboUserService.saveUser(user);
        return SysResult.success();
    }

    /**
     * 完成用户的登录操作
     * url地址:http://www.jt.com/user/doLogin?r=0.8989367429030823
     * 参数:   username/password
     * 返回值: SysResult对象  的JSON的数据.
     *
     *  cookie.setMaxAge(-1);  关闭浏览器会话时删除
     *  cookie.setMaxAge(0);   立即删除cookie
     *  cookie.setMaxAge(100); cookie可以存储的时间单位是秒
     *
     *  http://www.jt.com/saveUser/xxx
     *  cookie.setPath("/");
     *  cookie.setPath("/add");
     */
     @RequestMapping("/doLogin")
     @ResponseBody
     public SysResult doLogin(User user, HttpServletResponse response){

         //1.实现用户的登录操作!!!
         String ticket = dubboUserService.doLogin(user);
         //2.校验ticket是否有值.
         if(StringUtils.isEmpty(ticket)){
             //用户名或者密码错误
             return SysResult.fail();
         }
         //3.如果用户的ticket不为null,则表示登录正确,需要将数据保存到cookie中
         //Cookie要求   1.7天有效  2.要求cookie可以在jt.com的域名中共享  3.cookie权限 /
         Cookie cookie = new Cookie("JT_TICKET",ticket);
         cookie.setMaxAge(7*24*3600);
         cookie.setDomain("jt.com"); //在jt.com中实现页面共享.
         cookie.setPath("/");        //定于cookie的权限根目录有效
         response.addCookie(cookie); //利用response将cookie保存到客户端中.
         return SysResult.success();
     }


    /**
     * 实现用户的登出操作  要求删除cookie 和redis中的数据(key)
     * 步骤: 通过cookie获取ticket信息.
     * url: http://www.jt.com/user/logout.html
     * 参数: 暂时没有
     * 返回值: 重定向到系统首页
     */
    @RequestMapping("/logout")
    public String logout(HttpServletRequest request,HttpServletResponse response){
        String ticket = CookieUtil.getCookieValue("JT_TICKET", request);
        if(!StringUtils.isEmpty(ticket)){
            //1.删除redis
            jedisCluster.del(ticket);
            //2.删除cookie
            CookieUtil.deleteCookie("JT_TICKET", "/", "jt.com", response);
        }
        return "redirect:/";

       /* Cookie[] cookies = request.getCookies();
        if(cookies !=null && cookies.length >0){
            for(Cookie cookie : cookies){
                if("JT_TICKET".equals(cookie.getName())){
                    //获取value之后删除cookie
                    String ticket = cookie.getValue();
                    jedisCluster.del(ticket);   //删除redis中的数据
                    //删除cookie时 必须与原来的cookie数据保持一致
                    cookie.setDomain("jt.com");
                    cookie.setPath("/");
                    cookie.setMaxAge(0);
                    response.addCookie(cookie);
                    break;
                }
            }
        }*/
    }







}
