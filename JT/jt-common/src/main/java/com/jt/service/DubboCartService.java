package com.jt.service;

import com.jt.pojo.Cart;

import java.util.List;

public interface DubboCartService {
    /**根据用户id查询用户的购物车*/
    List<Cart> findCartListByUserId(Long userId);

    void updateCartNum(Cart cart);

    void addCart(Cart cart);

    void deleteCart(Cart cart);
}
