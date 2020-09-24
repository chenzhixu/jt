package com.jt.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data //重写set/get/toStrint..etc方法,只会重写自己的属性，不会添加父级的属性
@Accessors(chain = true)//链式加载规则
@NoArgsConstructor//提供无参构造方法
@AllArgsConstructor//提供全参构造方法
public class User {

    private Integer id;
    private String name;
    private Integer age;
    private String sex;

    //@Accessors(chain = true)等效于 accessors重写了set方法
 /*   public User setId(Integer id){
        this.id=id;
        return this;
    }*/
}
