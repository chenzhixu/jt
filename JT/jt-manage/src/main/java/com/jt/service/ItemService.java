package com.jt.service;

import com.jt.pojo.Item;
import com.jt.pojo.ItemDesc;
import com.jt.vo.EasyUITable;

public interface ItemService {

    void findItemByPage(Item item);
    EasyUITable findItemByPage(Integer page, Integer rows);
    void saveItem(Item item, ItemDesc itemDesc);
    void updateItem(Item item, ItemDesc itemDesc);
    /**通过商品id查询商品描述信息*/
    ItemDesc findItemDescById(Long itemId);
    void deleteItem(Long[] ids);

    void updateStatus(Integer status, Long[] ids);
}
