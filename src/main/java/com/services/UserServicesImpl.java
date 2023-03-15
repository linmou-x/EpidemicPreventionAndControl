package com.services;

import com.alibaba.fastjson2.JSONObject;
import com.auth0.jwt.JWT;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.entity.User;
import com.mapper.UserMapper;
import com.services.Impl.UserService;
import com.utils.Result;
import com.utils.ResultEnum;
import com.utils.Token;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;

/**
 * @Author：Charles
 * @Package：com.services
 * @Project：EpidemicPreventionAndControl
 * @name：UserServices
 * @Date：2023/1/2 20:48
 * @Filename：UserServices
 */

@Service
public class UserServicesImpl implements UserService {

    @Resource
    public UserMapper userMapper;

    @Resource
    Token JWTtoken;
    @Override
    public User getUserByPhone(String phone) {
        return userMapper.getUserByPhone(phone);
    }

    @Override
    public String getPasswordByPhone(String phone) {
        return userMapper.getPasswordByPhone(phone);
    }

    @Override
    public Result login(String phone, String password) {
        User user=userMapper.getUserByPhone(phone);
        if(user==null){
            return new Result(ResultEnum.FAIL,"登录失败,用户不存在");
        }else {
            if (!user.getPassword().equals(password)){
                return new Result(ResultEnum.FAIL,"登录失败,密码错误");
            }else {
                String token = JWTtoken.getToken(user.getPhone(), user.getPassword(), user.getId());
                return new Result(ResultEnum.SUCCESS, token);
            }
        }
    }

    @Override
    public Result batchImport(List<User> userList) {
        userList.forEach(user -> userMapper.insert(user));
        return new Result(ResultEnum.SUCCESS,"批量导入成功");
    }

    @Override
    public Result batchDelete(List<User> userList) {
        userList.forEach(user -> {
            /**
             * updawrapper 在循环外创建时语句为UPDATE user SET del_flag=？, del_flag=？WHERE (id =?AND id=?)
             * 只能更新最开始的一条
             */
            UpdateWrapper<User> updateWrapper=new UpdateWrapper<>();
            updateWrapper.set("del_flag",1);
            updateWrapper.eq("id",user.getId());
            userMapper.update(null,updateWrapper);
        });
        return new Result(ResultEnum.SUCCESS,"批量删除成功");
    }

    @Override
    public Result batchModify(List<User> userList) {
        userList.forEach(user -> {
            userMapper.updateById(user);
        });
        return new Result(ResultEnum.SUCCESS,"更新成功");
    }

}
