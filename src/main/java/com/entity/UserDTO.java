package com.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonProperty;
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
public class UserDTO implements Serializable {
    private static final long serialVersionUID = -40356785423868312L;
    /**
     * 唯一ID，自动生成唯一ID
     */
    @TableId(value ="id",type = IdType.ASSIGN_ID )
    @JsonProperty(value = "id")
    Long id;
    /**
     * 姓名
     */
    @JsonProperty(value = "name")
    String name;
    /**
     * 年龄
     */
    @JsonProperty(value = "age")
    Integer age;
    /**
     * 性别
     */
    @JsonProperty(value = "gender")
    String gender;
    /**
     * 家庭住址
     */
    @JsonProperty(value = "address")
    String address;
    /**
     * 户主
     */
    @TableField(value = "house_holder")
    @JsonProperty(value = "houseHolder")
    Long houseHolder;
    /**
     * 电话
     */
    @JsonProperty(value = "phone")
    String phone;
}
