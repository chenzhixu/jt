package com.jt.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jt.annotation.CacheFind;
import com.jt.mapper.ItemCatMapper;
import com.jt.pojo.ItemCat;
import com.jt.util.ObjectMapperUtil;
import com.jt.vo.EasyUITree;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import redis.clients.jedis.Jedis;

import java.util.ArrayList;
import java.util.List;

@Service
public class ItemCatServiceImpl implements  ItemCatService {


   @Autowired(required = false) //jedis对象程序启动时第一时间注入不是必须的
    private  Jedis jedis;

    @Autowired
    private ItemCatMapper itemCatMapper;




    @Override
    public String findItemCatNameById(Long itemCatId) {
        return itemCatMapper.selectById(itemCatId).getName();
    }


    /**
     * 思路:
     *  1.通过parentId查询数据库信息,返回值结果List<ItemCat>
     *  2.将ItemCat信息转化为EasyUITree对象
     *  3.返回的是List<EasyUITree>
     * @param parentId
     * @return
     */
    @Override
    @CacheFind(key="ITEM_CAT_PARENTID" ,seconds = 100)  //K-V结构的数据
    public List<EasyUITree> findItemCatListByParentId(Long parentId) {
        //1.根据父级分类Id查询数据
        QueryWrapper<ItemCat> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("parent_id", parentId);
        List<ItemCat> catList = itemCatMapper.selectList(queryWrapper);

        //2.需要将数据进行转化. cartList遍历 封装EasyUITree 添加到集合中即可
        List<EasyUITree> treeList = new ArrayList<>();
        for (ItemCat itemCat: catList){
            Long id = itemCat.getId();
            String text = itemCat.getName();
            //是父级就打开  否则关闭
            String state = itemCat.getIsParent()?"closed":"open";
            //利用构造方法 为VO对象赋值  至此已经实现了数据的转化
            EasyUITree easyUITree = new EasyUITree(id, text, state);
            //3.封装返回值数据
            treeList.add(easyUITree);
        }
        //用户需要返回List<EasyUITree>
        return treeList;
    }


    /**
     * 步骤:
     *  先查询Redis缓存  K:V
     *      true   直接返回数据
     *      false  查询数据库
     *
     *  KEY有什么特点: 1.key应该动态变化   2.key应该标识业务属性
     *      key=ITEM_CAT_PARENTID::parentId
     * @param parentId
     * @return
     */
    @Override
    public List<EasyUITree> findItemCache(Long parentId) {
        Long startTime=System.currentTimeMillis();//记录开始执行的时间
        //0.定义一个空集合
        List<EasyUITree> treeList=new ArrayList<>();
        String key= "ITEM_CAT_PARENTID::"+parentId;
        //1.从缓存中查询数据
        String json=jedis.get(key);
        //2.校验json串中是否有值
        if(StringUtils.isEmpty(json)){
            //3.如果缓存中没有数据，则查询数据库
          treeList= findItemCatListByParentId(parentId);
          Long endTime=System.currentTimeMillis();//记录结束执行的时间
          json=ObjectMapperUtil.toJSON(treeList);
          jedis.set(key,json);
            System.out.println("第一次查询数据库 耗时："+(endTime-startTime)+" ml");
        }else {//表示key不为空,执行数据库查询
            //表示程序json串不为空，程序有值，将json数据转化为对象即可
           treeList= ObjectMapperUtil.toObject(json,treeList.getClass());
            Long endTime = System.currentTimeMillis();
            System.out.println("查询redis缓存服务器成功 耗时："+(endTime-startTime)+" ml");
        }
        return treeList;
    }
}
