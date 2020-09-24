package com.jt.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jt.mapper.UserMapper;
import com.jt.pojo.User;
import com.jt.util.ObjectMapperUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;
import redis.clients.jedis.JedisCluster;

import java.util.UUID;


@Service  //注意导入dubbo包下的@Server注解
public class DubboUserServiceImpl implements  DubboUserService {

    @Autowired
    private UserMapper userMapper;


    @Autowired
    private JedisCluster jedisCluster;


    /**
     * 1.邮箱暂时使用电话号码代替
     * 2.需要将密码进行加密处理    md5/md5-hash
     * @param user
     * @return
     */
    @Override
    @Transactional //事务控制
    public void saveUser(User user) {
        //1.获取明文
        String password = user.getPassword();
        //2.利用Spring的工具API进行加密操作
        password = DigestUtils.md5DigestAsHex(password.getBytes());
        user.setPassword(password).setEmail(user.getPhone());
        userMapper.insert(user);


    }


    /**
     * 1.根据用户名和密码查询数据库
     * 2.校验用户数据的有效性.
     * 3.如果用户的数据是正确的 则开始进行单点登录操作.
     * 4.如果用户数据不正确 则ticket数据为null即可.
     * @param user
     * @return
     */
    @Override
    public String doLogin(User user) {
        //1.将密码进行加密处理
        String password = DigestUtils.md5DigestAsHex(user.getPassword().getBytes());
        user.setPassword(password);
        //如果xx条件构造器传递的是对象,则根据对象中不为null的属性充当where条件
        QueryWrapper<User> queryWrapper = new QueryWrapper<>(user);
        User userDB = userMapper.selectOne(queryWrapper);
        //2.校验数据是否有效
        if(userDB == null){
            return null;
        }
        //userDB数据不为null,用户的输入信息正确.开启单点登录操作.
        //3.1动态生成uuid
        String ticket = UUID.randomUUID().toString().replace("-", "");
        //3.2脱敏处理
        userDB.setPassword("123456你信不??");
        String userJSON = ObjectMapperUtil.toJSON(userDB);
        //3.3 将数据保存到redis中
        jedisCluster.setex(ticket, 7*24*60*60, userJSON);
        return ticket;
    }
}
