package com.jt;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(value = "com.jt.mapper")//利用包扫描的形式为接口创建代理对象
public class SpringbootJspApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootJspApplication.class, args);
	}

}