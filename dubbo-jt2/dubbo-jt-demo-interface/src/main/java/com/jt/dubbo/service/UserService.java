package com.jt.dubbo.service;

import com.jt.pojo.User;

import java.util.List;

public interface UserService {
    /**查询用户所有信息
     * select * from user
     * */
    List<User> findAll();
    /**新增一名用户，完成入库操作
     *
     * */
    void addUser(User user);
    /**修改用户信息*/
    void updateUser(User user,Integer userId);

    /**删除用户所有信息*/
    void deleteUser(Integer userId);


    /**通过id查询用户*/
    User findUserById(Integer userId);
}
