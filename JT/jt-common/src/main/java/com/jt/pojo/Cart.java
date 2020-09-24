package com.jt.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

@TableName("tb_cart")
@Accessors(chain = true)
@Data
public class Cart  extends  BasePojo{

    @TableId(type = IdType.AUTO)
    private Long id;            //主键自增
    private Long userId;        //用户id
    private Long itemId;        //商品id
    private String itemTitle;
    private String itemImage;
    private Long itemPrice;
    private Integer num;



}
