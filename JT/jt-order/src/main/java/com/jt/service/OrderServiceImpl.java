package com.jt.service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.jt.mapper.OrderItemMapper;
import com.jt.mapper.OrderMapper;
import com.jt.mapper.OrderShippingMapper;
import com.jt.pojo.Order;
import com.jt.pojo.OrderItem;
import com.jt.pojo.OrderShipping;

@Service
public class OrderServiceImpl implements DubboOrderService {

	@Autowired
	private OrderMapper orderMapper;
	@Autowired
	private OrderShippingMapper orderShippingMapper;
	@Autowired
	private OrderItemMapper orderItemMapper;


	@Override
	@Transactional //事务控制
	public String saveOrder(Order order) {
		//1.准备订单Id号   大量的字符串拼接
		String orderId = ""+order.getUserId() + System.currentTimeMillis();//注意书写字符串的前后顺序。
		//完成订单信息的入库操作
		order.setOrderId(orderId).setStatus(1); //1.未付款状态
		//调用OrderMapper的方法完成订单的入库操作
		orderMapper.insert(order);

		//完成订单物流信息的入库操作
		OrderShipping orderShipping=order.getOrderShipping();
		orderShipping.setOrderId(orderId);
		orderShippingMapper.insert(orderShipping);

		//完成订单商品信息的入库操作
		List<OrderItem>  orderItemList=order.getOrderItems();
		for (OrderItem orderItem:orderItemList) {
			orderItem.setOrderId(orderId);
			orderItemMapper.insert(orderItem);
		}
		System.out.println("完成订单入库操作");
		return orderId;
	}

	/**通过订单id查询订单*/
	@Override
	public Order findOrderById(String id) {
		//调用orderMapper的selectById方法根据订单id查询订单
		Order order=orderMapper.selectById(id);
		//调用orderShippingMapper的orderShippingMapper方法根据订单物流id查询订单物流信息
		OrderShipping orderShipping=orderShippingMapper.selectById(id);
		QueryWrapper<OrderItem> queryWrapper=new QueryWrapper<>();
		queryWrapper.eq("order_id",id);
		//调用orderItemMapper的orderItemMapper方法查询订单商品信息
		List<OrderItem> orderItemList=orderItemMapper.selectList(queryWrapper);
		order.setOrderShipping(orderShipping).setOrderItems(orderItemList);
		return  order;
	}
}
