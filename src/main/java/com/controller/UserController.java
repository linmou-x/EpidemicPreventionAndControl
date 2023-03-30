package com.controller;

import ch.qos.logback.classic.Logger;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.plugins.pagination.PageDTO;
import com.entity.PageUserDTO;
import com.entity.UserDTO;
import com.mapper.UserMapper;
import com.services.Impl.UserService;
import com.utils.Result;
import com.utils.ResultEnum;
import com.utils.UserLoginToken;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

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
    @Operation(summary = "userLogin",description = "用户登录",
                parameters = {@Parameter(name = "phone",description = "用户手机号"),
                                @Parameter(name = "password",description = "用户密码")})
    public Result userLogin(String phone,String password){
        logger.debug("login");
        return userService.login(phone,password);
    }


    @GetMapping("/userinfo")
    @Operation(summary = "userinfo",description = "获取用户信息")
    public Result userInfo(String token){
        return userService.userinfo(token);
    }

    @GetMapping(value = "/userPage")
    @Operation(summary = "userPage",description = "分页查询")
    public Result selectByPage(String  jsonObject,HttpServletRequest httpServletRequest){
        if (!jsonObject.isEmpty())
        {
            PageUserDTO pageUserDTO=JSON.parseObject(jsonObject, PageUserDTO.class);
            return userService.selectByPage(pageUserDTO, httpServletRequest);
        }else {
            return new Result(ResultEnum.FAIL,"Str为空");
        }
    }

    @GetMapping("/batchInsert")
    @Operation(summary = "batchInsert",description = "批量插入")
    public Result batchInsert(@RequestBody List<UserDTO> userList,HttpServletRequest httpServletRequest){
        return userService.batchImport(userList, httpServletRequest);
    }


    @GetMapping("/batchDelete")
    @Operation(summary="batchDelete",description = "用户删除")
    public Result batchDelete(@RequestBody List<UserDTO> userList,HttpServletRequest httpServletRequest){
        return userService.batchDelete(userList, httpServletRequest);
    }

    @GetMapping("/batchUpdate")
    @Operation(summary = "batchUpdate",description = "批量修改")
    public Result batchUpdate(@RequestBody List<UserDTO> userList,HttpServletRequest httpServletRequest){
        return userService.batchUpdate(userList, httpServletRequest);
    }

    @GetMapping("/message")
//    @UserLoginToken
    @Operation(summary = "测试,从HttpServletRequest中获取token，使用token工具解析到User信息",description = "测试")
    public Result message(HttpServletRequest httpServletRequest,String str){
        String token = httpServletRequest.getHeader("X-Token");
        logger.debug("AA"+String.valueOf(str));
        logger.debug(JSON.parseObject(str, PageUserDTO.class).toString());
        logger.debug(JSON.parseObject("{\"id\":\"\",\"name\":\"\",\"age\":\"\",\"gender\":\"\",\"address\":\"\",\"houseHolder\":\"\",\"phone\":\"\"}",UserDTO.class).toString());
        return new Result(ResultEnum.SUCCESS,token);
    }

    @Operation(summary = "setAdmin",description = "设置用户权限为管理员")
    @GetMapping("/setAdmin")
    public Result setRole(Long id){
        return userService.setAdmin(id);
    }

    @Operation(summary = "setVolunteer",description = "设置用户权限为志愿者/工作者")
    @GetMapping("/setVolunteer")
    public Result setVolunteer(Long id){
        return userService.setVolunteer(id);
    }

    @Operation(summary = "setUser",description = "设置用户权限为用户")
    @GetMapping("/setUser")
    public Result setUser(Long id){
        return userService.setUser(id);
    }
}
