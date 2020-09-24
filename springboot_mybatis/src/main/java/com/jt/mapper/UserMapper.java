package com.jt.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jt.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
//@Mapper  //将UserMapper接口交给spring容器管理
//注意泛型的引入，如果不添加数据库操作没办法完成
public interface UserMapper  extends BaseMapper<User> {
    //查询user表的全部数据库记录
    @Select("select * from user")
    List<User> findAll();
}
