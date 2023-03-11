package com.config;

import cn.hutool.core.bean.BeanUtil;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.entity.ExcelUserDTO;
import com.entity.User;
import com.mapper.ExcelUserDTOMapper;
import com.mapper.UserMapper;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author：Charles
 * @Package：com.config
 * @Project：EpidemicPreventionAndControl
 * @name：UserListener
 * @Date：3/11/2023 7:16 PM
 * @Filename：UserListener
 */
public class UserListener extends AnalysisEventListener<ExcelUserDTO> {
    private UserMapper userMapper;
    private List<ExcelUserDTO> list=new ArrayList<>();
    public UserListener(UserMapper mapper){
        this.userMapper=mapper;
    }
    @Override
    public void invoke(ExcelUserDTO excelUserDTO, AnalysisContext analysisContext) {
        list.add(excelUserDTO);
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        System.out.println(list.toString());
        list.forEach(excelUserDTO -> {
            User user= BeanUtil.copyProperties(excelUserDTO, User.class);
            userMapper.insert(user);
            System.out.println(user.toString());
        });
    }
}