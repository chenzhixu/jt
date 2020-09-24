package com.jt.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController //@Controller注解+@ResponseBody注解
//导入配置文件,之后由Spring容器扫描
@PropertySource(value = "classpath:/properties/image.properties",encoding = "UTF-8")
public class FileController {
    //问题：如何将配置文件信息动态进行获取？
    //private String localDir="D:/JT-SOFT/images":  //如果放在这里则代码的耦合性提高，不利于后期的维护和扩展

    @Value("${image.localDir}") //spel表达式由spring框架所提供的
    private String loaclDir;   //="D://JT-SOFT//images"; //如果将上传文件的目录信息直接写死到代码中,这样的代码耦合性高.不便于扩展.
    @RequestMapping("/getPath")
    public String getPath(){
        System.out.println("指定图片地址："+loaclDir);
        return "图片地址为："+loaclDir;
    }

}
