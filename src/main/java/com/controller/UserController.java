package com.controller;


import ch.qos.logback.classic.Logger;
import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.entity.User;
import com.mapper.UserMapper;
import com.services.UserServicesImpl;
import com.utils.Result;
import com.utils.ResultEnum;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;


/**
 * @Author：Charles
 * @Package：com.controller
 * @Project：EpidemicPreventionAndControl
 * @name：UserController
 * @Date：2023/1/2 20:47
 * @Filename：UserController
 */
@RestController
@ResponseBody
/**
 日志注解
 */
@Slf4j
@RequestMapping("/user")
public class UserController {
    Logger logger = (Logger) LoggerFactory.getLogger(Logger.class);
    @Resource
    public UserServicesImpl userServicesImpl;

    @Resource
    public UserMapper userMapper;

    @RequestMapping("/userLogin")
    public Result userLogin(String phone, String password){
        return userServicesImpl.login(phone,password);
    }
    /**
     * test controller
     * @return
     */

    @RequestMapping("/page")
    public Result selectByPage(@RequestParam(defaultValue = "1",required = false) Integer currentPage,
                               @RequestParam(defaultValue = "10",required = false) Integer pageSize,
                               @RequestParam(defaultValue = "charles") String name){
        QueryWrapper<User> queryWrapper=new QueryWrapper<>();
//        queryWrapper.eq("id",null);
        /**
         * del_flag 为禁用标识 1为可用，0为禁用
         */
        queryWrapper.eq("del_flag",1);
        Page<User> page=new Page<>(currentPage,pageSize);
        Page<User> userPage=userMapper.selectPage(page,queryWrapper);
        logger.debug(userMapper.selectById(1L).toString());
        return new Result(ResultEnum.SUCCESS,"this is Paging query result",userPage);
    }

    @GetMapping("/batchInsert")
    public Result batchInsert(@RequestBody List<User> userList){
        userServicesImpl.batchImport(userList);
        return new Result(ResultEnum.SUCCESS);
    }

    @GetMapping("/batchDelete")
    public Result batchDelete(@RequestBody List<User> userList){
        return userServicesImpl.batchDelete(userList);
    }

    @GetMapping("/test")
    public Result test(){
        return new Result(ResultEnum.SUCCESS,"this is test result",userMapper.selectById(1L));
    }
}
