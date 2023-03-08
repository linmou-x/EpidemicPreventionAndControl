package com.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.entity.User;
import com.services.UserServicesImpl;
import com.utils.Result;
import com.utils.ResultEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

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

    @Autowired
    public UserServicesImpl userServices;


    @RequestMapping("/userLogin")
    public Result userLogin(String phone, String password){
        return userServices.login(phone,password);
    }
    /**
     * test controller
     * @return
     */
    @RequestMapping("/hello")
    public Result HelloContoller(){
        return new Result(ResultEnum.SUCCESS,"Hello Springboot");
    }
}
