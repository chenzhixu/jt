package com.jt.aop;

import com.fasterxml.jackson.databind.util.JSONPObject;
import com.jt.vo.SysResult;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

@RestControllerAdvice   //作用: 标识我是一个通知方法,并且只拦截Controll层的异常.并且返回JSON串.
public class SysResultExceptionAOP {



    /**
     * 如果跨域访问发生了异常 ,则返回值结果必须经过特殊的格式封装才行.
     * 如果是跨域访问形式,全局异常处理 可以正确的返回结果.
     * 思路: 1.判断用户提交参数中是否有callback参数
     * callback(json)
     * * @param e
     * @return
     */
    //需要定义一个全局的方法 返回指定的报错信息.
    //ExceptionHandler 配置异常的类型,当遇到了某种异常时再执行该方法.
    //JSONP的异常处理应该是 callback({status:201,msg:"xx",data:"xxx"})
    //利用Request对象动态获取callback参数.之后动态封装返回值
    @ExceptionHandler({RuntimeException.class})
    public Object Exception(Exception e, HttpServletRequest request){
        //1.获取用户的请求参数
        String callback = request.getParameter("callback");
        //参数校验
        if(StringUtils.isEmpty(callback)){ //用户请求不是jsonp跨域访问形式
            SysResult sysResult= SysResult.fail();
            e.printStackTrace();    //打印异常信息  可以为日志记录/控制台输出. 让程序员知道哪里报错!!!
            return  sysResult;
        }else{//用户请求是jsonp跨域访问形式
            //jsonp的报错信息.
            e.printStackTrace();  //打印异常信息
            SysResult sysResult= SysResult.fail();
            return new JSONPObject(callback, sysResult);
        }

//        if(!StringUtils.isEmpty(callback)){ //用户请求是jsonp跨域访问形式
//           return new JSONPObject(callback,  SysResult.fail());
//        }
//        e.printStackTrace(); // 日志记录/控制台输出. 打印异常信息 让程序员知道哪里报错!!!
//        return SysResult.fail();

    }
}
