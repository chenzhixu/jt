package com.jt.controller;

import com.jt.vo.SysResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PortController {
    /**
     * 通过spring容器动态获取YML配置文件中的端口号即可
     */
    @Value("${server.port}") //${}表达式用于从yml配置文件中取值  #{}表达式用于从yml文件为属性赋值
    private int port;



    @RequestMapping("/getPort")
    public String getPost(){
        return "当前访问的端口号为:"+port;
    }
}
