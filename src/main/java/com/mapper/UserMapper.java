package com.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import javax.management.Query;
import java.util.Collection;
import java.util.List;

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
    @Select("select password from  user where phone = ?")
    String getPasswordByPhone(String phone);
}
