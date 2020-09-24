package com.jt.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.jt.pojo.Cart;
import com.jt.pojo.Order;
import com.jt.service.DubboCartService;
import com.jt.service.DubboOrderService;
import com.jt.thread.UserThreadLocal;
import com.jt.vo.SysResult;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/order")
public class OrderController {

    @Reference(check = false,timeout = 3000)
    private DubboCartService cartService;
    @Reference(check = false,timeout = 3000)
    private DubboOrderService orderService;

    /**
     * 订单确认页面跳转
     * 1.url地址:http://www.jt.com/order/create.html
     * 2.参数说明:  暂时没有
     * 3.返回值:  order-cart.jsp页面
     * 4.页面的取值:  ${carts}
     */
    @RequestMapping("/create")
    public String findCartByUserId(Model model){ //request域

        Long userId = UserThreadLocal.get().getId();
        List<Cart> cartList = cartService.findCartListByUserId(userId);
        model.addAttribute("carts",cartList);
        return "order-cart";
    }

    /**
     * 完成订单业务提交
     * 1.url地址: /order/submit
     * 2.页面参数: 整个form表单
     * 3.返回值结果: SysResult对象(orderId). json数据
     */
    @RequestMapping("/submit")
    @ResponseBody
    public SysResult submit(Order order){
        Long userId = UserThreadLocal.get().getId();
        order.setUserId(userId);
        String orderId = orderService.saveOrder(order);
        if(StringUtils.isEmpty(orderId)){
            return SysResult.fail();
        }
        return SysResult.success(orderId);
    }


    /**
     * 实现订单的查询,返回order对象
     * url: http://www.jt.com/order/success.html?id=191600674802663
     * 参数: id 订单的编号
     * 返回值: success
     * 页面取值: ${order.orderId}
     */
    @RequestMapping("/success")
    public String findOrderById(String id,Model model){

        Order order = orderService.findOrderById(id);
        model.addAttribute("order",order);
        return "success";
    }







}
