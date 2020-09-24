package com.jt.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

@TableName("tb_item_cat")
@Data
@Accessors(chain = true)
public class ItemCat extends BasePojo{
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long parentId;  // 父级ID
    private String name;    //分类名称
    private Integer status; //状态信息
    private Integer sortOrder;  //排序号
    private Boolean isParent;   //是否为父级.
}
