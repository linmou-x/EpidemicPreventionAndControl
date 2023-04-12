package com.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
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
@TableName(value = "order_record")
public class Order implements Serializable {
    private static final long serialVersionUID = -40356785423868312L;
    /**
     * 唯一id
     */
    @TableId(value ="id",type = IdType.ASSIGN_ID )
    @JsonFormat(shape =JsonFormat.Shape.STRING )
    Long id;
    /**
     * 物品名称
     */
    @TableField("good")
    Long  good;
    /**
     * 服务ID
     */
    @TableField("service")
    Long service;
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
     * 下单人
     */
    @TableField("record_by")
    Long recordBy;
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

    @TableField(value = "update_name")
    String updateName;
    /**
     * status
     */
    @TableField(value = "status",fill = FieldFill.INSERT)
    Integer status;
}
