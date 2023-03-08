package com.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.Entity;
import java.io.Serializable;
import java.util.Collection;
import java.util.Date;

/**
 * 用户类
 * @author Charles
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "user")
public class User implements Serializable, UserDetails{
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
    @TableField("house_holder")
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
    @TableField("create_time")
    Date createTime;
    /**
     * 更新人
     */
    @TableField("update_by")
    Long updateBy;
    /**
     * 更新时间
     */
    @TableField("update_time")
    Date updateTime;
    /**
     * 删除标志（0代表未删除，1代表已删除）
     */
    @TableField("del_flag")
    Integer delFlag;
    /**
     * 用户类型（管理员，普通住户，租户）
     */
    @TableField("user_type")
    String userType;
    /**
     * 头像
     */
    String avatar;
    /**
     * 上一次登陆
     */
    @TableField("last_login")
    Date lastLogin;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getUsername() {
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
