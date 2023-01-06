package com.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author：Charles
 * @Package：com.controller
 * @Project：EpidemicPreventionAndControl
 * @name：UserController
 * @Date：2023/1/2 20:47
 * @Filename：UserController
 */
@RestController
public class UserController {
    public String userLogin(String username,String password){
        return "aa";
    }


    @RequestMapping("/hello")
    public String HelloContoller(){
        return "Hello Springboot";
    }
}
