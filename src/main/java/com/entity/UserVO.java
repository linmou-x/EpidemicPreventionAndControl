package com.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserVO implements Serializable {
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
    @TableField(value = "house_holder",fill = FieldFill.INSERT)
    @JsonProperty(value = "houseHolder")
    Long houseHolder;
    /**
     * 电话
     */
    @JsonProperty(value = "phone")
    String phone;
    /**
     * 密码
     */
    @JsonProperty(value = "password")
    String password;

    /**
     * 用户类型（管理员，普通住户，租户）
     */
    @JsonProperty(value = "userType")
    String userType;
}

