package com.jt.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jt.pojo.Item;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface ItemMapper extends BaseMapper<Item>{

    @Select("select * from tb_item order by updated desc limit #{startIndex},#{rows}")
    List<Item> findItemByPage(int startIndex, Integer rows);

    /**
     * 由于数组取值需要进行循环遍历 所以需要通过遍历标签实现
     *   Mybatis中规范,默认条件下可以进行单值传递 后端用任意的参数接收都可以.
     *   有时可能进行多值传递.会将多值封装为Map集合进行参数的传递
     *   旧版本时如果需要封装为单值,则必须添加Param
     *   新版本时可以自动的添加@Param,前提条件是多值传递.
     * @param ids
     */
    void deleteItemsById(Long[] ids);
}
