package com.jt.test;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.jt.mapper.UserMapper;
import com.jt.pojo.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SpringBootTest
public class TestMybatis {

    @Autowired
    private UserMapper userMapper;

    @Test
    public void testSelect01(){
        List<User> userList=userMapper.findAll();
        System.out.println(userList);

    }
    //测试Mybatis plus
    @Test
    public void test02(){
       List<User> userList=userMapper.selectList(null);//查询user表中所有记录
        System.out.println(userList);
    }

    /**
     * 入库操作
     * 将用户信息  王大锤  20  男 入库
     */
    @Test
    public void testInsert(){
        User user=new User();
        user.setName("王大锤").setAge(20).setSex("男");//链式加载规则(既可以连这点)
        userMapper.insert(user);//使用MP，基本告别单表sql
    }

    /**
     * 查询练习 查询ID为21的用户
     */
    @Test
    public void testSelect02(){
        User  user=userMapper.selectById(21);
        System.out.println(user);
        //查询总记录数
        int count=userMapper.selectCount(null);
        System.out.println(count);

    }


    /**
     * 需求: 查询性别为(=)女 and 年龄大于100岁
     *      * 条件构造器: 动态拼接where条件的. 多条件中默认的链接符and
     *      * 常见逻辑运算符如下所示
     *      *  1.eq =   2.gt(gather than) >   3.lt(less than)  <
     *      *  >= , <=
     */
    @Test
    public void testSelect03(){
        QueryWrapper<User> queryWrapper=new QueryWrapper();
        queryWrapper.eq("sex","女").gt("age",100);
       List<User> userList=userMapper.selectList(queryWrapper);
        System.out.println(userList);


    }


    /**
     * 需求: 1.查询名称中包含'精'字的男性  "%精%"
     *       2.查询以精结尾的              %精
     *       3.查询以精开头                精%
    */
    @Test
    public void testSelect04(){
        QueryWrapper<User> queryWrapper=new QueryWrapper();
        //1.
//        queryWrapper.like("name","精").eq("sex","男");
        //2.
       queryWrapper.likeLeft("name","精");
       //3.
//        queryWrapper.likeRight("name","精");

        List<User> userList=userMapper.selectList(queryWrapper);
        System.out.println(userList);


    }

    /**
     * 需求: 查询sex=女,之后按照age 倒序排列.如果年龄相同按照id降序排列.
     * */
    @Test
    public void testSelect05(){
        QueryWrapper<User> queryWrapper=new QueryWrapper();
        queryWrapper.eq("sex","女").orderByDesc("age","id");
        List<User> userList=userMapper.selectList(queryWrapper);
        System.out.println(userList);

    }


    /**
     * 需求: 查询id为 1,3,5,7,8,9的数据???
     * 关键字  in  or
     * 在关联查询中慎用
     * */
    @Test
    public void testSelect06(){
        Integer[] ids={1,3,5,7,8,9};
 /*       List<Integer> idList=new ArrayList<>();
        idList.add(1);
        idList.add(3);
        idList.add(5);
        idList.add(7);
        idList.add(8);
        idList.add(9);*/
        /**数组转为List集合，不能转换为set集合，原因在于set集合不能存放重复元素，通过Arrays转换的集合是不能添加新元素的
         */
        // 将数组转为List集合
        List<Integer> idList=Arrays.asList(ids);
        List<User> userList=userMapper.selectBatchIds(idList);
        System.out.println(userList);

    }




    @Test
    public void testDelete1(){
        int rows=userMapper.deleteById(53);
        System.out.println(rows);

    }


    /**
     * 分析查询测试案例
     *   直接将对象传入到条件构造器中，得根据对象中不为null的属性当做操作的要素!!!!
     * 注意事项: POJO 类型必须是包装类型,不能是基本类型.因为基本类型有默认值,影响代码     */
    @Test
    public void testSelect(){
        User user = new User();
        user.setName("王昭君").setAge(19);
        QueryWrapper<User> queryWrapper = new QueryWrapper(user);
        List<User> userList = userMapper.selectList(queryWrapper);
        System.out.println(userList);
    }

    /**
     * 删除name为null的用户信息
     * sql怎么写 条件构造器怎么加
     * */
    @Test
    public void testDelete(){
        //定义条件构造器,作用是为了动态拼接where条件。 多条件中默认的链接符and
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.isNull("name");
        userMapper.delete(queryWrapper);
    }

    /**
     * 更新操作:
     *  1.将name为null的数据 修改为 name=安琪拉  sex=女   年龄8
     *  参数说明:
     *      1.实体对象   更新的数据内容.
     *      2.条件构造器  动态拼接where条件.
     * */
    @Test
    public void testUpdate(){
        User user = new User();
        user.setName("安琪拉").setSex("女").setAge(8);
        UpdateWrapper<User> updateWrapper = new UpdateWrapper<>();
        updateWrapper.isNull("name");
        userMapper.update(user,updateWrapper);
    }



}

