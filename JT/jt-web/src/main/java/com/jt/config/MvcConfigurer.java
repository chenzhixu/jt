package com.jt.config;

import com.jt.interceptor.UserInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration						  //配置web.xml配置文件
public class MvcConfigurer implements WebMvcConfigurer{
	
	//开启匹配后缀型配置,为了将来程序实现静态页面的跳转而准备的
	@Override
	public void configurePathMatch(PathMatchConfigurer configurer) {
		
		configurer.setUseSuffixPatternMatch(true);
	}

	//添加拦截器配置
	@Autowired
	private UserInterceptor userInterceptor;

	@Override
	public void addInterceptors(InterceptorRegistry registry) {

		registry.addInterceptor(userInterceptor).addPathPatterns("/cart/**","/order/**");
	}
}
