package com.jt.service;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.jt.mapper.ItemDescMapper;
import com.jt.pojo.Item;
import com.jt.pojo.ItemDesc;
import com.jt.vo.EasyUITable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jt.mapper.ItemMapper;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

@Service
public class ItemServiceImpl implements  ItemService {
	
	@Autowired
	private ItemMapper itemMapper;

	@Autowired
	private ItemDescMapper itemDescMapper;



	@Override
	public ItemDesc findItemDescById(Long itemId) {
		ItemDesc itemDesc=itemDescMapper.selectById(itemId);
		return itemDesc;
	}
	@Override
	public void findItemByPage(Item item) {
		//更新时需要修改更新时间!!!
//		item.setUpdated(new Date());
		itemMapper.updateById(item);

	}


	@Override
	public EasyUITable findItemByPage(Integer page, Integer rows) {
		//1.计算起始位置
		int startIndex = (page - 1) * rows;
		List<Item> itemList = itemMapper.findItemByPage(startIndex, rows);
		//2.获取总记录数
		int count = itemMapper.selectCount(null);
		return new EasyUITable(count, itemList);
	}


	@Transactional //控制事务  注意事务控制   spring一般只能控制运行时异常,检查异常需要手动封装.
	@Override
	public void saveItem(Item item, ItemDesc itemDesc) {
		//1.默认商品为上架状态   1表示商品上架状态
//		item.setStatus(1).setCreated(new Date()).setUpdated(new Date());
		item.setStatus(1);
		itemMapper.insert(item);//先入库之后才有主键,将主键动态的返回.
		//MP支持,用户的操作可以实现自动的主键回显功能.
		//<!--<insert id="" keyProperty="id" keyColumn="id" useGeneratedKeys="true"></insert>-->
		//2.完成商品详情入库操作  要求 item的ID的应该与itemDesc的Id值一致的!!!!
		//知识点: id应该如何获取?  itemDesc.setItemId(item.getId());
		itemDesc.setItemId(item.getId());
		itemDescMapper.insert(itemDesc);
	}




	@Override
	public void updateItem(Item item, ItemDesc itemDesc) {
		//item.setUpdated(new Date());
		itemMapper.updateById(item);
		//.完成商品详情修改操作  要求 item的ID的应该与itemDesc的Id值一致的!!!!
		//知识点: id应该如何获取?  itemDesc.setItemId(item.getId());
		itemDesc.setItemId(item.getId());
		itemDescMapper.updateById(itemDesc);
	}


	@Override
	public void deleteItem(Long[] ids) {
		//方式一：使用MP API完成批量删除商品信息操作
//		List<Long>  idList=Arrays.asList(ids);
//		itemMapper.deleteBatchIds(idList);
		//方式2:手动编写方法实现批量删除商品信息的操作
		itemMapper.deleteItemsById(ids);
		//完成详情表的批量删除操作
		List<Long> idList=Arrays.asList(ids);
		itemDescMapper.deleteBatchIds(idList);

	}


	/**
	 * update(arg1,arg2)
	 * arg1: 需要修改的数据
	 * arg2: 修改的条件构造器
	 * @param status
	 * @param ids
	 */
	@Override
	public void updateStatus(Integer status, Long[] ids) {
		Item item=new Item();
		item.setStatus(status);
		List<Long> idList=Arrays.asList(ids);
		UpdateWrapper<Item> updateWrapper=new UpdateWrapper();
		updateWrapper.in("id",idList);
		itemMapper.update(item,updateWrapper);
	}
}
