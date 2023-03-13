package com.entity;

import com.alibaba.excel.annotation.ExcelProperty;
import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ser.std.StdKeySerializers;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户类
 * @author Charles
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "user")
public class User implements Serializable{
    private static final long serialVersionUID = -40356785423868312L;
    /**
     * 唯一ID，自动生成唯一ID
     */
    @TableId(value ="id",type = IdType.ASSIGN_ID )
    @JsonProperty(value = "id")
    Long id;
    /**
     * 姓名，暂时用作账户名
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
     * 创建时间
     */
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    @JsonProperty(value = "createTime")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    Date createTime;
    /**
     * 更新人
     */
    @TableField(value = "update_by",fill = FieldFill.INSERT)
    @JsonProperty(value = "updateBy")
    Long updateBy;
    /**
     * 更新时间
     */
    @TableField(value = "update_time",fill = FieldFill.INSERT_UPDATE)
    @JsonProperty(value = "updateTime")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    Date updateTime;
    /**
     * 删除标志（0代表未删除，1代表已删除）
     */
    @TableField("del_flag")
    @JsonProperty(value = "delFlag")
    Integer delFlag;
    /**
     * 用户类型（管理员，普通住户，租户）
     */
    @JsonProperty(value = "userType")
    String userType;
    /**
     * 头像
     */
    @JsonProperty(value = "avatar")
    String avatar;
    /**
     * 上一次登陆
     */
    @TableField(value = "last_login",fill = FieldFill.DEFAULT)
    @JsonProperty(value = "lastLogin")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    Date lastLogin;
}
