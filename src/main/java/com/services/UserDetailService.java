package com.services;

import com.mapper.UserMapper;
import com.services.Impl.UserDetailServiceImpl;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import javax.annotation.Resource;

/**
 * @Author：Charles
 * @Package：com.services
 * @Project：EpidemicPreventionAndControl
 * @name：UserDetailServiceImpl
 * @Date：2023/3/8 18:40
 * @Filename：UserDetailServiceImpl
 */
@Resource
public class UserDetailService implements UserDetailServiceImpl {
    @Resource
    private UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String phone) throws UsernameNotFoundException {
        return null;
    }
//    @Override
//    public UserDetails loadUserByUsername(String phone) throws UsernameNotFoundException {
//        QueryWrapper<User> queryWrapper=new QueryWrapper<>();
//        queryWrapper.eq("Phone",phone);
//        User user=userMapper.selectOne(queryWrapper);
//        return
//    }
}
