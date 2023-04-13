package com.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * @author charles
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDTO implements Serializable {
    private static final long serialVersionUID = -40356785423868312L;
    /**
     * 唯一id
     */
    @TableId(value ="id",type = IdType.ASSIGN_ID )
    Long id;
    String name;
    /**
     * 物品
     */
    @TableField("good")
    Long  good;
    /**
     * 服务
     */
    @TableField("service")
    Long service;
    /**
     * 数量
     */
    Long amount;
    /**
     * 单位
     */
    String units;
    /**
     * 价格
     */
    Integer price;
    /**
     * 服务ID
     */
    Integer status;
}

