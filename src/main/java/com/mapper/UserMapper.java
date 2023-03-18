package com.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * @Author：Charles
 * @Package：com.mapper
 * @Project：EpidemicPreventionAndControl
 * @name：UserMapper
 * @Date：2023/1/6 15:27
 * @Filename：UserMapper
 */

@Mapper
public interface UserMapper extends BaseMapper<User> {
    @Select("select password from  user where phone = ${phone}")
    String getPasswordByPhone(String phone);

    @Select("select  * from  user where phone = ${phone}")
    User getUserByPhone(String phone);
}
