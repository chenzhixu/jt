package com.jt.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@Accessors(chain = true)//链式加载规则
@TableName("user")  //实现表与对象的关联 如果表名与对象名称一致(忽略大小写)，可以省略表名
public class User  implements Serializable {
    //但凡是定义pojo实体对象中的属性类型必须使用包装类型
    @TableId(type = IdType.AUTO)
    private Integer id; //主键，并且主键自增
    @TableField("name") //当对象中属性名与表中字段名相同时，可省略@TableField注解括号中的列名
    private String name;
    @TableField("age")
    private  Integer age;
    private String sex;

    //动态生成get和set方法以及构造方法 快捷键： alt+insert


}

