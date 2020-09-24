package com.jt.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jt.mapper.UserMapper;
import com.jt.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {

   private static Map<Integer,String> paramMap=new HashMap<>();   //采用工具API形式动态获取


    static { //类加载时就要执行  只执行一次
        paramMap.put(1,"username");
        paramMap.put(2,"phone");
        paramMap.put(3,"email");

    }


    @Autowired
    private UserMapper userMapper;




    @Override
    public List<User> findAll() {
        return userMapper.selectList(null);
    }


    /**校验数据是否存在即可，查询数据库中的总记录数即可
     * @param param  需要校验的参数(数据)
     * @param type   校验参数的类型  1：username 2：phone 3：email 6789910
     * @return   true 表示数据已存在   false 表示数据可以使用
     */
    @Override
    public Boolean checkUser(String param, Integer type) {
        //参数校验
        if(param==null || param=="")throw new IllegalArgumentException("参数不合法");
        if(type==0 ||type==null)throw new IllegalArgumentException("参数不合法");
       String column= paramMap.get(type);
        QueryWrapper<User> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq(column,param);
        int record=userMapper.selectCount(queryWrapper);
        boolean flag=record>0?true:false;
       // boolean flag=record==0?false:true;   //效果与上面的等价
        // boolean flag=record>0   //效果与上面的等价
        return flag;
    }


    @Override
    public User findUserById(Long userId) {
        User user=userMapper.selectById(userId);
        return user;
    }
}
