package com.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Value;
import org.springframework.format.annotation.DateTimeFormat;
import java.io.Serializable;
import java.util.Date;

/**
 * @Author：Charles
 * @Package：com.entity
 * @Project：EpidemicPreventionAndControl
 * @name：Service
 * @Date：2023/3/10 17:00
 * @Filename：Service
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "service")
public class Service implements Serializable {
    private static final long serialVersionUID = -40356785423868312L;
    /**
     * 唯一id
     */
    @TableId(value = "id",type = IdType.ASSIGN_ID)
    Long id;

    /**
     * 名称
     */
    String name;
    /**
     * 描述
     */
    String description;
    /**
     * 商品价格
     */
    Integer price;
    /**
     * create time
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField(value = "create_time",fill = FieldFill.INSERT)
    Data createTime;
    /**
     * update time
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField(value = "update_time",fill = FieldFill.INSERT_UPDATE)
    Date updateTime;
    /**
     * 更新人
     */
    @TableField(value = "update_by")
    Long updateBy;

    @TableField(value = "status",fill = FieldFill.INSERT)
    Integer status;
}
