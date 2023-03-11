package com.entity;

import com.alibaba.excel.annotation.ExcelProperty;
import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
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
    Long id;
    /**
     * 姓名，暂时用作账户名
     */
    String name;
    /**
     * 年龄
     */
    Integer age;
    /**
     * 性别
     */

    String gender;
    /**
     * 家庭住址
     */

    String address;
    /**
     * 户主
     */
    @TableField(value = "house_holder",fill = FieldFill.INSERT)
    Long houseHolder;
    /**
     * 电话
     */
    String phone;
    /**
     * 密码
     */

    String password;
    /**
     * 创建时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    Date createTime;
    /**
     * 更新人
     */
    @TableField(value = "update_by",fill = FieldFill.INSERT)
    Long updateBy;
    /**
     * 更新时间
     */
    @TableField(value = "update_time",fill = FieldFill.INSERT_UPDATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    Date updateTime;
    /**
     * 删除标志（0代表未删除，1代表已删除）
     */
    @TableField("del_flag")
    Integer delFlag;
    /**
     * 用户类型（管理员，普通住户，租户）
     */
    @TableField(value = "user_type",fill = FieldFill.INSERT)
    String userType;
    /**
     * 头像
     */
    String avatar;
    /**
     * 上一次登陆
     */
    @TableField(value = "last_login",fill = FieldFill.DEFAULT)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    Date lastLogin;
}
