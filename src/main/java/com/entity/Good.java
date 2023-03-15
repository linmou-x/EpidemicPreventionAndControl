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
 * @Author：Charles
 * @Package：com.entity
 * @Project：EpidemicPreventionAndControl
 * @name：Goods
 * @Date：2023/3/8 14:47
 * @Filename：Goods
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "good")
public class Good implements Serializable {
    private static final long serialVersionUID = -40356785423868312L;
    /**
     * 唯一id
     */
    @TableId(value ="id",type = IdType.ASSIGN_ID )
    Long id;

    /**
     * 物品名称
     */
    String name;
    /**
     *  类别
     */
    String type;
    /**
     * 描述
     */
    String description;
    /**
     * 商品数量
     */
    Integer amount;
    /**
     * 物品单位
     */
    String units;
    /**
     * 商品价格
     */
    Integer price;
    /**
     * create time
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField(value = "create_time",fill = FieldFill.INSERT)
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    Date createTime;
    /**
     * create time
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField(value = "update_time",fill = FieldFill.INSERT)
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    Date updateTime;
    /**
     * update time
     */
    @TableField(value = "update_person")
    Long updatePerson;
}
