package com.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.entity.User;
import com.services.UserServicesImpl;
import com.utils.Result;
import com.utils.ResultEnum;
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
public class UserController {

    @Autowired
    public UserServicesImpl userServices;


    @RequestMapping("/getuser")
    public Result userLogin(String name, String password){


        return userServices.login(name,password);
    }


    @RequestMapping("/hello")
    public Result HelloContoller(){
        return new Result(ResultEnum.SUCCESS,"Hello Springboot");
    }
}
