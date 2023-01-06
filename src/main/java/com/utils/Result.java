package com.utils;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;
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
@Accessors(chain = true)  //链式编程注解
public class Result {
    @ApiModelProperty(value = "是否成功") //swagger注解
    private Boolean success;

    @ApiModelProperty(value = "返回状态码")
    private Integer code;

    @ApiModelProperty(value = "返回消息")
    private String message;

    @ApiModelProperty(value = "返回数据")
    private Map<String,Object> data = new HashMap<>();

    //构造方法私有化,使其他类不能new 只能使用类中固定的方法
    private Result(){}

    //成功静态方法
//    @NotNull
    public static Result succeed(){
        Result resultReturn = new Result();
        resultReturn
                .setSuccess(true)
                .setCode(ResultCode.SUCCESS) //直接引用状态码接口
                .setMessage("执行成功");
        return resultReturn;
    }

    //失败静态方法
//    @NotNull
    public static Result error(){
        Result resultReturn = new Result();
        resultReturn.setSuccess(false);
        resultReturn.setCode(ResultCode.ERROR); //直接引用状态码接口
        resultReturn.setMessage("执行失败");
        return resultReturn;
    }

    public Result success(Boolean success){
        this.setSuccess(success);
        return this;
    }
    public Result message(String message){
        this.setMessage(message);
        return this;
    }
    public Result code(Integer code){
        this.setCode(code);
        return this;
    }

    public Result data(String key, Object value){
        this.data.put(key,value);
        return this;
    }

    public Result data(Map<String,Object> map){
        this.setData(map);
        return this;
    }
}
