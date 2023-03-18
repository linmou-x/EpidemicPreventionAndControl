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
@TableName(value = "order")
public class Order implements Serializable {
    private static final long serialVersionUID = -40356785423868312L;
    /**
     * 唯一id
     */
    @TableId(value ="id",type = IdType.ASSIGN_ID )
    Long id;
    /**
     * 物品名称
     */
    Long  goodId;
    /**
     * 数量
     */
    Integer amount;
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
    Long serviceId;
    /**
     * 下单人
     */
    Long orderBy;
    /**
     * create time
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField(value = "create_time",fill = FieldFill.INSERT)
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    Date createTime;
    /**
     * update time
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField(value = "update_time",fill = FieldFill.INSERT_UPDATE)
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    Date updateTime;
    /**
     * update by
     */
    @TableField(value = "update_by")
    Long updateBy;
    /**
     * status
     */
    @TableField(value = "status",fill = FieldFill.INSERT)
    Integer status;
}
