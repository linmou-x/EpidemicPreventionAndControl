package com.services;

import com.entity.GoodType;
import com.mapper.GoodTypeMapper;
import com.services.Impl.GoodTypeService;
import com.utils.Result;
import com.utils.ResultEnum;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@Service
public class GoodTypeServiceImpl implements GoodTypeService {

    @Resource
    GoodTypeMapper goodTypeMapper;

    @Override
    public Result getGoodTypeList() {
        return new Result(ResultEnum.SUCCESS,goodTypeMapper.selectList(null));
    }

    @Override
    public Result insertGoodType(GoodType goodType, HttpServletRequest httpServletRequest) {
        return new Result(ResultEnum.SUCCESS,goodTypeMapper.insert(goodType));
    }

    @Override
    public Result updateGoodType(GoodType goodType,HttpServletRequest httpServletRequest) {
        return new Result(ResultEnum.SUCCESS,goodTypeMapper.updateById(goodType));
    }

    @Override
    public Result deleteGoodType(GoodType goodType,HttpServletRequest httpServletRequest) {
        goodType.setStatus(0);
        return new Result(ResultEnum.SUCCESS,goodTypeMapper.updateById(goodType));
    }

}
