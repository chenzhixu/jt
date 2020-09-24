package com.jt.util;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 该类为一个Cookie的工具API，主要任务如下：
 *   1.根据cookie的名称 返回cookie对象
 *   2.根据cookie的名称 返回valve的值
 *   3.新增cookie方法
 *    4.删除cookie方法
 */



public class CookieUtil {
    public static Cookie getCookie(String cookieName, HttpServletRequest request){
        Cookie[] cookies = request.getCookies();
        if(cookies !=null && cookies.length >0) {
            for (Cookie cookie : cookies) {
                if (cookieName.equals(cookie.getName())) {
                    return cookie;
                }
            }
        }
        return null ;
    }


    public static String getCookieValue(String cookieName,HttpServletRequest request){
        Cookie cookie = getCookie(cookieName, request);
        //String cookieValue=cookie.getValue();
        //return cookie ==null?null:cookieValue;
      return cookie ==null?null:cookie.getValue();
    }


    public static void addCookie(String cookieName, String cookieValue, String path, String domain, int maxAge, HttpServletResponse response){
        Cookie cookie = new Cookie(cookieName,cookieValue);
        cookie.setPath(path);
        cookie.setDomain(domain);
        cookie.setMaxAge(maxAge);
        response.addCookie(cookie);
    }




    public static void deleteCookie(String cookieName,String path,
                                    String domain,HttpServletResponse response){
        addCookie(cookieName,"",path, domain, 0, response);
    }
}
