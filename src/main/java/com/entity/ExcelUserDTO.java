package com.entity;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author：Charles
 * @Package：com.entity
 * @Project：EpidemicPreventionAndControl
 * @name：ExcelUserDTO
 * @Date：3/11/2023 6:48 PM
 * @Filename：ExcelUserDTO
 */

/**
 * 用户类
 * @author Charles
 */
@Data
public class ExcelUserDTO implements Serializable {
    private static final long serialVersionUID = -40356785423868312L;

    @ExcelProperty(value = "姓名")
    String name;
    /**
     * 年龄
     */
    @ExcelProperty(value = "年龄")
    Integer age;
    /**
     * 性别
     */
    @ExcelProperty(value = "性别")
    String gender;
    /**
     * 家庭住址
     */
    @ExcelProperty(value = "住址")
    String address;

    /**
     * 电话
     */
    @ExcelProperty(value = "手机号")
    String phone;
}

