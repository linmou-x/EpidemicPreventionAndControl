package com.services;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.entity.User;
import com.mapper.UserMapper;
import com.services.Impl.UserServiceImpl;
import com.utils.Result;
import com.utils.ResultEnum;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * @Author：Charles
 * @Package：com.services
 * @Project：EpidemicPreventionAndControl
 * @name：UserServices
 * @Date：2023/1/2 20:48
 * @Filename：UserServices
 */

@Service
public class UserServices implements UserServiceImpl {

    @Resource
    public UserMapper userMapper;
    @Override
    public Result login(String name, String password) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("name",name);
        map.put("password",password);
        return new Result(ResultEnum.SUCCESS,userMapper.selectById(1));
    }

    @Override
    public Result batchImport(List<User> userList) {
        return new Result(ResultEnum.SUCCESS,"批量导入结果");
    }

    @Override
    public Result batchDelete(List<User> userList) {
        return null;
    }

    @Override
    public Result batchModify(List<User> userList) {
        return null;
    }

    @Override
    public Result batchSearch(List<User> userList) {
        return null;
    }

    @Override
    public Result getUserByPhone(String phone) {
        QueryWrapper<User> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("Phone",phone);
        return new Result(ResultEnum.SUCCESS,userMapper.selectOne(queryWrapper));
    }

    @Override
    public void login(User user) {

    }

//    @Override
//    public void login(User user) {
//        UsernamePasswordAuthenticationToken token=new UsernamePasswordAuthenticationToken(user.getPhone(),user.getPassword());
//        authenticationManager.authenticate(token);
//    }

}
