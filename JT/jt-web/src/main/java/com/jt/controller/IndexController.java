package com.jt.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {

    /**
     * SpringMVC的规则:
     *  默认的条件下mvc只能拦截前缀 像 /index   /addUser等
     *  但是如果遇到请求  /index.html则表示查找具体的某个静态页面,则不会经过MVC
     *  扩展:为了简化前台程序的配置,需要拦截.html结尾的请求.
     *  例如:https://item.jd.com/24284446355.html
     *  *
     * @return
     */
    @RequestMapping("/index")
    public String index(){
        return "index";   //走视图解析器拼接前缀/后缀
    }
}
