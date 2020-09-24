package com.jt.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.jt.pojo.Cart;
import com.jt.pojo.User;
import com.jt.service.DubboCartService;
import com.jt.thread.UserThreadLocal;
import com.jt.vo.SysResult;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("/cart")
public class CartController {

    @Reference(check = false,timeout = 3000)
    private DubboCartService cartService;

    /**
     * 1.购物车列表数据展现
     * url地址: http://www.jt.com/cart/show.html
     * 参数:    暂时没有
     * 返回值:  页面逻辑名称  cart.jsp
     * 页面取值: ${cartList}
     * 应该将数据添加到域对象中 Request域  model工具API操作request对象
     *
     */
    @RequestMapping("/show")
    public String show(Model model, HttpServletRequest request){
      /*  User user = (User) request.getAttribute("JT_USER");
        Long userId = user.getId();*/
        Long userId = UserThreadLocal.get().getId();
        //jt-web服务器访问jt-cart的服务器 RPC通讯  全新的线程
        List<Cart> cartList = cartService.findCartListByUserId(userId);
        model.addAttribute("cartList",cartList);
        return "cart";
    }

    /**
     * 业务说明: 完成购物车数量的更新操作
     * url地址: http://www.jt.com/cart/update/num/562379/9
     * 参数:    暂时没有
     * 返回值:  void
     */
    @RequestMapping("/update/num/{itemId}/{num}")
    @ResponseBody  //将对象转化为json 满足前端调用的需求 ajax调用
    public void updateCartNum(Cart cart,HttpServletRequest request){   //如果{name} 与属性的名称一致,则可以自动的赋值.

     /*   User user = (User) request.getAttribute("JT_USER");
        Long userId = user.getId();*/
        Long userId = UserThreadLocal.get().getId();
        cart.setUserId(userId);
        cartService.updateCartNum(cart);
    }

    /**
     * 删除购物车数据
     * url地址:http://www.jt.com/cart/delete/562379.html
     * 参数:   562379
     * 返回值: 购物车列表页面
     */
    @RequestMapping("/delete/{itemId}")
    public String deleteCart(Cart cart){
        Long userId = UserThreadLocal.get().getId();
        cart.setUserId(userId);
        cartService.deleteCart(cart);
        return "redirect:/cart/show.html";
    }


    /**
     * 业务需求: 完成购物车入库操作
     * url地址: http://www.jt.com/cart/add/562379.html
     * 参数:    form表单提交的数据/itemId
     * 返回值:  重定向到购物车列表页面
     * 如果用户重复提交数据.则应该修改商品的数量即可!!!  5分钟练习
     */
    @RequestMapping("/add/{itemId}")
    public String addCart(Cart cart){
        Long userId = UserThreadLocal.get().getId();
        cart.setUserId(userId);
        cartService.addCart(cart);
        return "redirect:/cart/show.html";
    }













}
