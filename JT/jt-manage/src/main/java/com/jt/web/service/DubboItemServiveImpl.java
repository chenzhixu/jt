package com.jt.web.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.jt.mapper.ItemDescMapper;
import com.jt.mapper.ItemMapper;
import com.jt.pojo.Item;
import com.jt.pojo.ItemDesc;
import com.jt.service.DubboItemService;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class DubboItemServiveImpl implements DubboItemService {


    @Autowired
    private ItemMapper  itemMapper;  //商品信息


    @Autowired
    private ItemDescMapper itemDescMapper;  //商品详情信息


    @Override
    public Item findItemById(Long itemId) {
        return itemMapper.selectById(itemId);

    }

    @Override
    public ItemDesc findItemDescById(Long itemId) {
        return itemDescMapper.selectById(itemId);
    }
}
