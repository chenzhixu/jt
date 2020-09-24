package com.jt.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.jt.pojo.Item;
import com.jt.pojo.ItemDesc;
import com.jt.service.DubboItemService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ItemController {

    @Reference(check = false,timeout = 3000)
    private DubboItemService itemService;

    /**
     * 业务: 根据itemId查询商品信息(item/itemDesc)
     * url地址: http://www.jt.com/items/562379.html
     * 参数:    注意restFul风格即可
     * 返回值:  页面逻辑名称   item.jsp
     * 页面取值: ${item.title }/${itemDesc.itemDesc }
     *  5分钟完成.
     * */
     @RequestMapping("/items/{itemId}")
     public String findItemById(@PathVariable Long itemId, Model model){
         Item item = itemService.findItemById(itemId);
         ItemDesc itemDesc = itemService.findItemDescById(itemId);
         model.addAttribute("item", item);
         model.addAttribute("itemDesc", itemDesc);
         return "item";
     }
}
