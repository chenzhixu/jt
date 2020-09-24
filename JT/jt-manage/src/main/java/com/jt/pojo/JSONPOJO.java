package com.jt.pojo;

import lombok.Data;

@Data
public class JSONPOJO {

    private Integer id;
    private String name;


    public String getAge(){
        String string="凭空捏造一个年龄";
        return string;
    }




}
