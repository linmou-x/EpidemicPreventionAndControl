package com.entity;

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
public class GoodVO implements Serializable {
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
     * status
     */
    @TableField(value = "status")
    Integer status;
}