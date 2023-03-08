package com.services;

import com.entity.User;
import com.mapper.UserMapper;
import com.services.Impl.UserService;
import com.utils.Result;
import com.utils.ResultEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
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

@Service
public class UserServicesImpl implements UserService {

    @Resource
    public UserMapper userMapper;

    @Override
    public Result login(String name, String password) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("name",name);
        map.put("password",password);
//        return new Result(ResultEnum.SUCCESS,userMapper.selectByMap(map));
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
}
