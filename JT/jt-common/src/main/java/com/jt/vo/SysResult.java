package com.jt.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class SysResult  implements Serializable {

    private Integer status;         //定义状态信息 200业务处理成功, 201业务处理失败.
    private String  msg;            //服务器返回的提示信息.
    private Object  data;           //服务器返回业务数据.

    //封装一些静态API 简化用户调用过程.
    public static SysResult fail(){
        SysResult sysResult=new SysResult(201,"服务器调用失败",null);
        return  sysResult;
    }

    public static SysResult success(){
        SysResult sysResult=new SysResult(200, "业务执行成功!!!", null);
        return sysResult;
    }

    public static SysResult success(Object data){
        SysResult sysResult=new SysResult(200, "业务执行成功!!!", data);
        return sysResult;
    }



}

