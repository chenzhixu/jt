package com.jt.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.ser.Serializers;
import lombok.Data;
import lombok.experimental.Accessors;

@TableName("tb_user")
@Accessors(chain = true)
@Data
public class User  extends BasePojo {

    @TableId(type = IdType.AUTO)  //主键自增
    private Long id;
    private String username;
    private String password;
    private String phone;
    private String email;  //由于邮箱暂时没有，先使用电话号码代替
}
