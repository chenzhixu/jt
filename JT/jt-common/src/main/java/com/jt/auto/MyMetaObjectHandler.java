package com.jt.auto;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * 此类的作用是完成自动填充功能
 */

@Component  //将该对象交给spring容器管理
public class MyMetaObjectHandler  implements MetaObjectHandler {
    /**
     * 在POJO中添加了 新增/更新的注解,但是必须在数据库的字段中完成赋值的操作.
     * 所以.必须明确,新增/更新时操作的是哪个字段,及值是多少
     * * * @param metaObject
     */
    @Override
    public void insertFill(MetaObject metaObject) {

        this.setInsertFieldValByName("created", new Date(), metaObject);
        this.setInsertFieldValByName("updated", new Date(), metaObject);
    }

    @Override
    public void updateFill(MetaObject metaObject) {

        this.setUpdateFieldValByName("updated", new Date(), metaObject);
    }
}
