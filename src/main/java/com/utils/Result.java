package com.utils;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author：Charles
 * @Package：com.utils
 * @Project：EpidemicPreventionAndControl
 * @name：Result
 * @Date：2023/1/6 15:42
 * @Filename：Result
 */
@Data
public class Result<Object> implements Serializable {

    private ResultEnum code;

    private String message;

    private Object data;

    public Result(ResultEnum code,Object data) {
        this.code=code;
        this.data=data;
    }
    public Result(ResultEnum code,String message,Object data) {
        this.code=code;
        this.message=message;
        this.data=data;
    }
}
