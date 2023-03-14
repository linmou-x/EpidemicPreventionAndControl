package com.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.entity.Good;
import com.entity.Service;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @Author：Charles
 * @Package：com.mapper
 * @Project：EpidemicPreventionAndControl
 * @name：GoodsMapper
 * @Date：2023/3/8 14:48
 * @Filename：GoodsMapper
 */
@Mapper
public interface GoodMapper extends BaseMapper<Good> {
    @Select("SELECT  * FROM good ")
    List<Good> getGoodList();

}
