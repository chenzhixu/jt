package com.jt.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.core.conditions.query.Query;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.jt.mapper.CartMapper;
import com.jt.pojo.Cart;
import org.springframework.beans.factory.annotation.Autowired;
import sun.security.util.Cache;

import java.util.Date;
import java.util.List;

@Service
public class DubboCartServiceImpl implements DubboCartService{

    @Autowired
    private CartMapper cartMapper;


    @Override
    public List<Cart> findCartListByUserId(Long userId) {
        QueryWrapper<Cart> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userId);
        return cartMapper.selectList(queryWrapper);
    }

    /**
     * 更新购物车的数量 num
     * @param cart
     * Sql: update tb_cart set num = #{num},updated = now()
     *               where user_id = #{user} and item_id = #{itemId}
     */
    @Override
    public void updateCartNum(Cart cart) { //itemId,userId
        //创建了一个新的对象 根据对象中不为null的属性充当set条件
        Cart cartTemp = new Cart();
        cartTemp.setNum(cart.getNum());
        UpdateWrapper<Cart> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("user_id", cart.getUserId())
                     .eq("item_id", cart.getItemId());
        cartMapper.update(cart,updateWrapper);
    }

    @Override
    public void deleteCart(Cart cart) {  //itemId/userId
        //根据对象中不为null的元素充当where条件
        cartMapper.delete(new QueryWrapper<>(cart));
    }

    /**
     * 思路:
     *     1.先查询数据库 user_id/item_id
     *     2.判断返回值是否有结果
     *          true:    只做数量的更新
     *          false:   直接入库即可
     */
    @Override
    public void addCart(Cart cart) {
        QueryWrapper<Cart> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", cart.getUserId());
        queryWrapper.eq("item_id", cart.getItemId());
        Cart cartDB = cartMapper.selectOne(queryWrapper);
        if(cartDB == null){
            //新增入库
            cartMapper.insert(cart);
        }else{
            //更新数量  MP方式
            int num = cart.getNum() + cartDB.getNum(); //数量求和
            cartDB.setNum(num).setUpdated(new Date());
            cartMapper.updateCartNum(cartDB);
        }
    }
}
