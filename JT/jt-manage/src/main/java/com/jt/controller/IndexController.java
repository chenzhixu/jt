package com.jt.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {


	/**
	 * 关于通用页面跳转的说明
	 * url地址:  /page/item-add
	 * url地址:  /page/item-list
	 * url地址:  /page/item-param-list
	 * 按照常规: 1个请求对应的1个controller方法
	 * 需求: 能否利用一个方法实行页面的通用的跳转.
	 * 想法: 能否动态的接收url中的参数呢??
	 *
	 * restFul风格实现1:
	 * 	1.参数与参数之间使用/分隔
	 * 	2.参数使用{}形式包裹
	 * 	3.@PathVariable 实现数据的转化.
	 *
	 * restFul风格实现2:
	 * 	可以利用请求的类型,指定业务功能.
	 *	TYPE="GET"   查询业务
	 * 	TYPE="POST"  新增业务
	 * 	TYPE="PUT"   更新业务
	 * 	TYPE="DELETE" 删除业务
	 *
	 * 	总结1: 如果需要获取url地址中的参数时,则可以使用RestFul风格实现.
	 * 	总结2: 可以按照类型执行特定的功能.
	 */

//@RequestMapping(value = "/page/{moduleName}",method = RequestMethod.GET)
	@GetMapping("/page/{moduleName}")
	public String itemAdd(@PathVariable String moduleName){
		System.out.println("moduleName="+moduleName);
		//目的:跳转页面 item-add
		return moduleName;
	}
}

