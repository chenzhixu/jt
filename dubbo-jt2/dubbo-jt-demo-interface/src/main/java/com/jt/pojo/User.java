package com.jt.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@Accessors(chain=true)
@TableName
public class User  implements Serializable {

    //dubbo协议中传输的对象必须序列化
    private static final long serialVersionUID = 1L;
    @TableId(type= IdType.AUTO)
    private Integer id;
    private String name;
    private Integer age;
    private String sex;
}
