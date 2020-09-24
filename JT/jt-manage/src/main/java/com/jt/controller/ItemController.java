package com.jt.controller;

import com.jt.pojo.Item;
import com.jt.pojo.ItemDesc;
import com.jt.service.ItemService;
import com.jt.vo.EasyUITable;
import com.jt.vo.SysResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

//@Controller
@RestController    //此注解等效于@Controller+@ResponseBody   @RestControlle返回值都是JSON数据
@RequestMapping("/item")
public class ItemController {

	@Autowired
//	@Qualifier("aaa") //表示将bean的名字为aaa的对象注入给描述的属性 //@Qualifier注解的作用是用于将哪一个bean的名字对应的对象注入给描述的属性，注意：@Qualifier注解必须配置@Autowired注解使用
	private ItemService itemService;


	/**
	 * 业务需求:商品信息的分页查询.
	 * url地址: http://localhost:8091/item/query?page=1&rows=50
	 * 请求参数: page 页数 , rows 行数
	 * 返回值结果: EasyUITable
	 * 开发顺序: mapper~~service~~~controller~~页面  自下而上的开发
	 * 京淘开发顺序: 分析页面需求~~~~Controller~~~~Service~~~Mapper  自上而下的开发
	 *
	 * */
	@RequestMapping("query")
	public EasyUITable findItemByPage(Integer page,Integer rows){
		EasyUITable easyUITable=itemService.findItemByPage(page,rows);
		return easyUITable;

	}


	/**
	 * 业务需求：完成商品信息和商品详情信息的同时的入库操作，返回系统vo对象
	 * 请求url：	 http://localhost:8091/item/save
	 *  参数类型: 整个form表单
	 * 	返回值类型: SysResult对象
	 *
	 *
	 * 复习: 页面中的参数是如何通过SpringMVC为属性赋值???
	 * 分析: 页面参数提交 一般方式3种   1.form表单提交     2.ajax页面提交  3.a标签 参数提交
	 * 		 页面参数提交一般都会遵守协议规范 key=value
	 * 分析2: SpringMVC的底层实现servlet. 包含了2大请求对象 request对象/response对象
	 * 		  servlet如何获取数据?????
	 * 规则:  参数提交的名称与mvc中接受参数的名称必须一致!!!!
	 */
	@RequestMapping("save")
	public SysResult saveItem(Item item,ItemDesc itemDesc){
		//商品数据和商品描述数据一起完成入库操作.
		itemService.saveItem(item,itemDesc);
		return SysResult.success();
		//全局异常的处理机制!!!!  因为定义了全局的异常处理类了，不在这里处理异常了。
	/*	try{
			itemService.saveItem(entity);
			return SysResult.success();
		}catch (Exception e){
			e.printStackTrace();
			return SysResult.fail();

		}*/

	}


	/**业务需求说明：完成商品和商品详情的修改操作
	 * 请求 URL: http://localhost:8091/item/update
	 *  参数类型: 整个form表单
	 * 	返回值类型: SysResult对象
	 * @param entity
	 * @return
	 */
	@RequestMapping("update")
	public SysResult updateItem(Item entity,ItemDesc itemDesc){
		itemService.updateItem(entity,itemDesc);
		return SysResult.success();

	}


	/**
	 * 业务需求说明：完成商品和商品详情的删除操作
	 * 	请求URL:http://localhost:8091/item/delete
	 * 	 参数类型: {"ids":"100"}
	 * 	返回值类型: SysResult对象
	 *
	 * 注意事项：取值必须与赋值的key保持一致
	 * @param ids
	 * @return
	 */
	@RequestMapping("delete")
	public SysResult deleteItems(Long...ids){  //这里的参数也可以的是 可变参数Long...ids，也可以是Long[] ids
		itemService.deleteItem(ids);
		return SysResult.success();

	}


	/**
	 * 业务需求说明：利用一个方法完成商品的上架/下架操作    restFul风格实现
	 * 下架 Request URL: http://localhost:8091/item/updateStatus/2 status=2
	 * 上架 Request URL: http://localhost:8091/item/updateStatus/1 status=1
	 */

	//restful 的url编码风格
	@RequestMapping("updateStatus/{status}")
	public SysResult updateStatus(@PathVariable Integer status,Long... ids){
		itemService.updateStatus(status,ids);
		return SysResult.success();
	}


	/**	业务需求说明： 根据商品id(ItemId)查询ItemDesc对象
	 * Request URL: http://localhost:8091/item/query/item/desc/1474391963
	 * 参数: restFul形式
	 * 	返回值: SysResult对象
	 */
	@RequestMapping("query/item/desc/{itemId}")
	public SysResult findItemDescById(@PathVariable  Long itemId){
		ItemDesc itemDesc=itemService.findItemDescById(itemId);
		return  SysResult.success(itemDesc); //200 返回业务数据
	}





}
