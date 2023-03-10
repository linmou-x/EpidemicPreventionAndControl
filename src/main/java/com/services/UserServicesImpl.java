package com.services;

import ch.qos.logback.classic.Logger;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.entity.User;
import com.mapper.UserMapper;
import com.services.Impl.UserService;
import com.utils.Result;
import com.utils.ResultEnum;
import org.slf4j.LoggerFactory;
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
    @Override
    public Result login(String name, String password) {
        return new Result(ResultEnum.SUCCESS,userMapper.selectById(1));
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

}
