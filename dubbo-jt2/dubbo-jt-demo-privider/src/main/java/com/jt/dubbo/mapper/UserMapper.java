package com.jt.dubbo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jt.pojo.User;
import org.apache.ibatis.annotations.Update;

public interface UserMapper extends BaseMapper<User> {

//    @Update("update user set name=#{name},age=#{age},sex=#{sex} where id=#{userId}")
//    void updateById(Integer userId);



}
