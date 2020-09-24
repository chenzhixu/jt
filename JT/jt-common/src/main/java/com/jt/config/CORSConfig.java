package com.jt.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration //标识我是一个配置类
public class CORSConfig implements WebMvcConfigurer {

    /**
     * 实现跨域的方式
     * 需要配置服务端程序
     * 方法说明:
     *      1.addMapping(/**) 允许什么样的请求可以跨域  所有的请求      /* 表示只允许一级目录请求    /** 表示允许多级目录请求.
     *      2.allowedOrigins("*")可以允许任意的域名
     *      3.allowCredentials(true) 跨域时是否允许携带cookie等参数
     *      4.  maxAge(1800) 定义探针检测时间 在规定的时间内不再询问是否允许跨域 ,默认值也是1800秒
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
     CorsRegistration corsRegistration= registry.addMapping("/**");
     corsRegistration=corsRegistration.allowedOrigins("*");
     corsRegistration=corsRegistration.allowCredentials(true);
     corsRegistration=corsRegistration.maxAge(1800);



    }
}
