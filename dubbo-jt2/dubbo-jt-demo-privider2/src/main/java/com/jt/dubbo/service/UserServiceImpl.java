package com.jt.dubbo.service;


import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.jt.dubbo.mapper.UserMapper;
import com.jt.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Service(timeout = 3000)//3秒超时 内部实现了rpc
public class UserServiceImpl  implements  UserService{


    @Autowired
    private UserMapper userMapper;

    @Override
    public List<User> findAll() {
        List<User> userList=userMapper.selectList(null);
        System.out.println("我是第2个服务的提供者......");
        return userList;
    }

    @Override
    public void addUser(User user) {
        System.out.println("我是第2个服务的提供者......");
        userMapper.insert(user);

    }

    @Override
    public void updateUser(User user, Integer userId) {
        System.out.println("我是第2个服务的提供者......");
        user.setName(user.getName()).setSex(user.getSex()).setAge(user.getAge());
        UpdateWrapper<User> updateWrapper=new UpdateWrapper<>();
        updateWrapper.eq("id",userId);
        userMapper.update(user,updateWrapper);

    }


    @Override
    public void deleteUser(Integer userId) {
        System.out.println("我是第2个服务的提供者......");
        userMapper.deleteById(userId);
    }


    @Override
    public User findUserById(Integer userId) {
        System.out.println("我是第2个服务的提供者......");
        User user=userMapper.selectById(userId);
        return  user;
    }
}

