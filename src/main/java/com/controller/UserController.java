package com.controller;

import ch.qos.logback.classic.Logger;
import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.entity.User;
import com.mapper.UserMapper;
import com.services.Impl.UserService;
import com.utils.Result;
import com.utils.ResultEnum;
import com.utils.UserLoginToken;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpRequest;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * @author Charles
 * @author Charles
 */
@RestController
@ResponseBody
/**
 日志注解
 */
@Slf4j
@Tag(name = "UserController",description = "用户管理")
@RequestMapping("/user")
public class UserController {
    Logger logger = (Logger) LoggerFactory.getLogger(Logger.class);
    @Resource
    public UserService userService;
    @Resource
    public UserMapper userMapper;

    @RequestMapping("/userLogin")
    @Operation(summary = "用户登录",description = "用户需要先行登录")
    public Result userLogin(String phone, String password){
        return userService.login(phone,password);
    }

    @GetMapping(value = "/page")
    @Operation(summary = "用户按需查询分页",description = "分页查询")
    public Result selectByPage(@RequestBody Map<String,String> map){
        Integer currentPage=Integer.valueOf(map.get("currentPage"));
        Integer pageSize=Integer.valueOf(map.get("pageSize"));
        User user= JSON.parseObject(map.get("user"),User.class);
        QueryWrapper<User> queryWrapper=new QueryWrapper<>();
        /**
         * del_flag 为禁用标识 1为可用，0为禁用
         * 用户类型为管理员实可以查询全部账户
         * 否则只可以查询当前可用账户
         */
        if ("admin".equals(user.getUserType())){
            queryWrapper.eq("del_flag",1)
                    .eq("del_flag",0);
        }else if ("user".equals(user.getUserType())){
            queryWrapper.eq("del_flag",1);
        }
        logger.debug(user.toString());
        /**
         * 用户姓名非空时拼接条件到SQL语句，
         */
        queryWrapper.like(!StringUtils.isNotBlank(user.getName()), "name", user.getName());
        /**
         * 条件判定非空时添加年龄查询条件
         */
        queryWrapper.eq(!StringUtils.isEmpty(String.valueOf(user.getAge())), "age", user.getAge());
        /**
         * 条件判定非空时添加性别查询条件
         */
        queryWrapper.eq(!StringUtils.isEmpty(String.valueOf(user.getGender())), "gender", user.getGender());
        /**
         * 条件判定非空时添加地址查询条件
         */
        queryWrapper.eq(!StringUtils.isEmpty(String.valueOf(user.getAddress())), "address", user.getAddress());
        /**
         * 条件判定非空时添加户主查询条件
         */
        queryWrapper.eq(!StringUtils.isEmpty(String.valueOf(user.getHouseHolder())), "house_holder", user.getHouseHolder());
        /**
         * 条件判定非空时添加手机查询条件
         */
        queryWrapper.eq(!StringUtils.isEmpty(String.valueOf(user.getPhone())), "phone", user.getPhone());
        Page<User> page=new Page<>(currentPage,pageSize);
        Page<User> userPage=userMapper.selectPage(page,queryWrapper);
        return new Result(ResultEnum.SUCCESS,"this is Paging query result",userPage);
    }

    @GetMapping("/batchInsert")
    @Operation(summary = "批量插入",description = "批量插入")
    public Result batchInsert(@RequestBody List<User> userList){
        userService.batchImport(userList);
        return new Result(ResultEnum.SUCCESS);
    }

    @GetMapping("/batchDelete")
    @Operation(summary = "批量删除",description = "批量删除")
    public Result batchDelete(@RequestBody List<User> userList){
        return userService.batchDelete(userList);
    }

    @GetMapping("/batchModify")
    @Operation(summary = "批量修改",description = "批量修改")
    public Result batchModify(@RequestBody List<User> userList){
        return userService.batchModify(userList);
    }

    @GetMapping("/message")
    @UserLoginToken
    @Operation(summary = "测试,从HttpServletRequest中获取token，使用token工具解析到User信息",description = "测试")
    public Result message(HttpServletRequest httpServletRequest){
        String token = httpServletRequest.getHeader("token");
        return new Result(ResultEnum.SUCCESS,token);
    }
}
