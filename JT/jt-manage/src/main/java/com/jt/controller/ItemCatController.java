package com.jt.controller;


import com.jt.annotation.CacheFind;
import com.jt.pojo.ItemCat;
import com.jt.service.ItemCatService;
import com.jt.vo.EasyUITree;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/item/cat") //请求业务名称
public class ItemCatController {

    @Autowired
    private ItemCatService itemCatService;


    /**分析业务: 通过itemCatId获取商品分类的名称
     * 1.url地址:http://localhost:8091/item/cat/queryItemName
     * 2.参数: {itemCatId:val},
     * 3.返回值: 商品分类名称  String
     */
    @CacheFind(key = "ITEM_CAT_NAME")
    @RequestMapping("/queryItemName")
    public String findItemCatName(Long itemCatId){
        return itemCatService.findItemCatNameById(itemCatId);
    }


    /**
     *
     * 关于缓存实现业务说明
     *  1.应该查询缓存
     *  2.判断缓存中是否有数据
     *   3.如果没有数据,则查询数据库
     *  4.如果有数据,则直接返回数据.
     *   思考: redis中主要操作的类型是String类型,业务数据如何于String进行交互!!!!!
     *    实现思路:  业务数据   ~~~    JSON    ~~~    String
     *-----------------------------------
     *
     * 业务需求: 实现商品分类的展现
     *      url地址: http://localhost:8091/item/cat/list
     *      参数:    parentId = 0  查询一级商品分类菜单.
     *      返回值结果: List<EasyUITree>
     * @return
     */
    @RequestMapping("/list")
    public List<EasyUITree> findItemCatList(Long id){
        //当初始时树形结构没有加载则不会传递id，所以id为null，只需要传递0
        Long parentId = (id==null)?0:id; //根据parentId=0 查询一级商品分类信息
        return itemCatService.findItemCatListByParentId(parentId);
//        return itemCatService.findItemCache(parentId);
    }






}
