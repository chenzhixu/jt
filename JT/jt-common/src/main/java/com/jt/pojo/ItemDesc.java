package com.jt.pojo;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 此类为一个商品描述表的实体类
 */
@TableName("tb_item_desc")
@Data
@Accessors(chain = true)
public class ItemDesc extends  BasePojo {

    @TableId
    private Long itemId; //这里不能设置为主键自增，原因是因为商品描述表要与商品表ID一致，即一个商品一个商品描述，关系为one2one
    private String itemDesc;


}
