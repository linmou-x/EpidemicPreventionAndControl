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
    //ExcelProperty为excel文件内的列名信息
    @ExcelProperty(value = "姓名")
    String name;
    @ExcelProperty(value = "年龄")
    Integer age;
    @ExcelProperty(value = "性别")
    String gender;
    @ExcelProperty(value = "住址")
    String address;
    @ExcelProperty(value = "手机号")
    String phone;
}

