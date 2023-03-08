package com.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.entity.User;
import org.springframework.stereotype.Repository;

import javax.management.Query;

/**
 * @Author：Charles
 * @Package：com.mapper
 * @Project：EpidemicPreventionAndControl
 * @name：UserMapper
 * @Date：2023/1/6 15:27
 * @Filename：UserMapper
 */

@Repository
public interface UserMapper extends BaseMapper<User> {
}
