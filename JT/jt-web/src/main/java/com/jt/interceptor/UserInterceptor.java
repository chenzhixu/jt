package com.jt.interceptor;

import com.jt.pojo.User;
import com.jt.thread.UserThreadLocal;
import com.jt.util.CookieUtil;
import com.jt.util.ObjectMapperUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import redis.clients.jedis.JedisCluster;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//处理器拦截器
//一般在接口中定义default可以使得实现类不必重写所有的接口的方法.
@Component  //将拦截器交给Spring容器管理
public class UserInterceptor implements HandlerInterceptor {

    @Autowired
    private JedisCluster jedisCluster;

    /**
     * 返回值说明:
     *      boolean:  true   放行
     *             :  false  拦截  一般配合重定向的方式使用
     *
     * 业务说明:
     *      如果用户已经登录则程序放行.否则拦截
     * 判断依据:  1.是否有cookie 2.redis中是否有记录.
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        //1.动态获取JT_TICKET数据
        String ticket = CookieUtil.getCookieValue("JT_TICKET",request);
        if(!StringUtils.isEmpty(ticket)){
            //2.判断redis中是否有数据
            if(jedisCluster.exists(ticket)){
                //4.动态获取user信息.将用户信息交给request对象传递给下一级
                String userJSON = jedisCluster.get(ticket);
                User user = ObjectMapperUtil.toObject(userJSON,User.class);
                //当前的对象会携带当前的用户信息!!!!
                request.setAttribute("JT_USER",user);

                //2.利用ThreadLocal的方式动态传参
                UserThreadLocal.set(user);
                return true;    //表示放行
            }
            //3.将无效的cookie删除.
            CookieUtil.deleteCookie("JT_TICKET","/","jt.com",response);
        }

        //重定向到用户的登录页面.
        response.sendRedirect("/user/login.html");
        return false;
    }

    /**
     * 说明:利用拦截器方法清空数据
     * @param request
     * @param response
     * @param handler
     * @param ex
     * @throws Exception
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

        //1.删除Request对象的数据
        request.removeAttribute("JT_USER");

        //2.删除ThreadLocal中的数据
        UserThreadLocal.remove();
    }




}


