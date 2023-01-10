package com.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户类
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "user")
public class User implements Serializable {
    private static final long serialVersionUID = -40356785423868312L;
    /**
     * 唯一ID，自动生成唯一ID
     */
    @TableId
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
    @TableField("householder")
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
    @TableField("createTime")
    Date createTime;
    /**
     * 更新人
     */
    @TableField("updateby")
    Long updateBy;
    /**
     * 更新时间
     */
    @TableField("updatetime")
    Date updateTime;
    /**
     * 删除标志（0代表未删除，1代表已删除）
     */
    @TableField("delflag")
    Integer delFlag;
    /**
     * 用户类型（0管理员，1普通用户）
     */
    @TableField("usertype")
    String userType;
    /**
     * 头像
     */
    String avatar;

    /**
     * 上一次登陆
     */
    @TableField("lastlogin")
    Date lastLogin;
}
