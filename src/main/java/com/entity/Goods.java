package com.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

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
@TableName(value = "goods")
public class Goods implements Serializable {
    private static final long serialVersionUID = -40356785423868312L;
    /**
     * 唯一id
     */
    private Integer id;
}