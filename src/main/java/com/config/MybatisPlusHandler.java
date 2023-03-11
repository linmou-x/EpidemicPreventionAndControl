package com.config;

import cn.hutool.core.date.LocalDateTimeUtil;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @Author：Charles
 * @Package：com.config
 * @Project：EpidemicPreventionAndControl
 * @name：MybatisPlusHandler
 * @Date：3/11/2023 7:42 PM
 * @Filename：MybatisPlusHandler
 */
@Component
public class MybatisPlusHandler  implements MetaObjectHandler {
    @Override
    public void insertFill(MetaObject metaObject) {
        this.setFieldValByName("password","12345678",metaObject);
        this.setFieldValByName("updateTime", new Date(), metaObject);
        this.setFieldValByName("houseHolder", 1L, metaObject);
        this.setFieldValByName("updateBy",1L,metaObject);
        this.setFieldValByName("userType","user",metaObject);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        this.setFieldValByName("lastLogin", LocalDateTimeUtil.now(), metaObject);
        this.setFieldValByName("updateTime", LocalDateTimeUtil.now(), metaObject);
    }
}