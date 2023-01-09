package com.services;

import com.entity.User;
import com.mapper.UserMapper;
import com.services.Impl.UserService;
import com.utils.Result;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author：Charles
 * @Package：com.services
 * @Project：EpidemicPreventionAndControl
 * @name：UserServices
 * @Date：2023/1/2 20:48
 * @Filename：UserServices
 */
public class UserServicesImpl implements UserService {

    private UserMapper userMapper;
    @Override
    public Result login(String username, String password) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("username",username);
        map.put("password",password);
        return Result.succeed().message(String.valueOf(userMapper.selectByMap(map)));
    }

    @Override
    public Result batchImport(List<User> userList) {
        return null;
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
}
