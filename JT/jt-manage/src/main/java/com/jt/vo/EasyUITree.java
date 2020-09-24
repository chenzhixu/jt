package com.jt.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

//该对象的主要的目的是为了展现树形结构的数据.
@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class EasyUITree implements Serializable {
    //"id":"3","text":"吃鸡游戏","state":"open/closed" 数据来源 数据表
    private Long id;       //商品分类的Id信息
    private String text;   //商品分类name属性
    private String state;  //由是否为父级决定 如果是父级则关闭closed 否则为子级open
}
