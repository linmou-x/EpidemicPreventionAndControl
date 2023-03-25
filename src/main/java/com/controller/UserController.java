package com.controller;

import ch.qos.logback.classic.Logger;
import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.entity.PageUserDTO;
import com.entity.User;
import com.entity.UserDTO;
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
@CrossOrigin(origins = "*")
@RequestMapping("/user")
public class UserController {
    Logger logger = (Logger) LoggerFactory.getLogger(Logger.class);
    @Resource
    public UserService userService;
    @Resource
    public UserMapper userMapper;


    @PostMapping("/userLogin")
    @Operation(summary = "用户登录",description = "用户需要先行登录")
    public Result userLogin(String phone, String password){
        return userService.login(phone,password);
    }

    @GetMapping(value = "/page")
    @Operation(summary = "用户按需查询分页",description = "分页查询")
    public Result selectByPage(@RequestBody PageUserDTO pageUserDTO,HttpServletRequest httpServletRequest){
        return userService.selectByPage(pageUserDTO, httpServletRequest);
    }

    @GetMapping("/batchInsert")
    @Operation(summary = "批量插入",description = "批量插入")
    public Result batchInsert(@RequestBody List<UserDTO> userList,HttpServletRequest httpServletRequest){
        return userService.batchImport(userList, httpServletRequest);
    }

    @GetMapping("/batchDelete")
    @Operation(summary = "批量删除",description = "批量删除")
    public Result batchDelete(@RequestBody List<UserDTO> userList,HttpServletRequest httpServletRequest){
        return userService.batchDelete(userList, httpServletRequest);
    }

    @GetMapping("/batchUpdate")
    @Operation(summary = "批量修改",description = "批量修改")
    public Result batchUpdate(@RequestBody List<UserDTO> userList,HttpServletRequest httpServletRequest){
        return userService.batchUpdate(userList, httpServletRequest);
    }

    @GetMapping("/message")
    @UserLoginToken
    @Operation(summary = "测试,从HttpServletRequest中获取token，使用token工具解析到User信息",description = "测试")
    public Result message(HttpServletRequest httpServletRequest){
        String token = httpServletRequest.getHeader("token");
        return new Result(ResultEnum.SUCCESS,token);
    }
}
