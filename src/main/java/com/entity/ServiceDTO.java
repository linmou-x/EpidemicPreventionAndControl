package com.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;

/**
 * @author charles
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ServiceDTO implements Serializable {
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
    String image;
    /**
     * 描述
     */
    String description;
    /**
     * 商品价格
     */
    Integer price;

    Integer status;
}