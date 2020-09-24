package com.jt.service;


import com.jt.pojo.User;

import java.util.List;

public interface UserService {
    List<User> findAll();

    Boolean checkUser(String param, Integer type);
    /**根据用户id查询用户信息*/
    User findUserById(Long userId);

}
