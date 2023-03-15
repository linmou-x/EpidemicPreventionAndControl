package com.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.entity.Service;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import java.util.List;

/**
 * @Author：Charles
 * @Package：com.mapper
 * @Project：EpidemicPreventionAndControl
 * @name：ServiceMapper
 * @Date：3/13/2023 6:04 PM
 * @Filename：ServiceMapper
 */
@Mapper
public interface ServiceMapper extends BaseMapper<Service> {

    @Select("SELECT  * FROM service ")
    List<Service> getServiceList();
}
