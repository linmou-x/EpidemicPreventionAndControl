package com.services;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.entity.GoodType;
import com.mapper.GoodTypeMapper;
import com.services.Impl.GoodTypeService;
import com.utils.Result;
import com.utils.ResultEnum;
import com.utils.Token;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@Service
public class GoodTypeServiceImpl implements GoodTypeService {

    @Resource
    GoodTypeMapper goodTypeMapper;

    @Resource
    Token token;

    @Override
    public Result getGoodTypeList(HttpServletRequest httpServletRequest) {
        QueryWrapper<GoodType> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("status",1);
        if (!"user".equals(token.getRole(httpServletRequest.getHeader("X-Token")))){
            queryWrapper.or().eq("status",0);
        };
        return new Result(ResultEnum.SUCCESS,goodTypeMapper.selectList(queryWrapper));
    }

    @Override
    public Result insertGoodType(GoodType goodType, HttpServletRequest httpServletRequest) {
        goodType.setUpdateBy(token.getId(httpServletRequest.getHeader("X-Token")));
        goodType.setUpdateName(token.getName(httpServletRequest.getHeader("X-Token")));
        return new Result(ResultEnum.SUCCESS,goodTypeMapper.insert(goodType));
    }

    @Override
    public Result updateGoodType(GoodType goodType,HttpServletRequest httpServletRequest) {
        goodType.setUpdateBy(token.getId(httpServletRequest.getHeader("X-Token")));
        goodType.setUpdateName(token.getName(httpServletRequest.getHeader("X-Token")));
        return new Result(ResultEnum.SUCCESS,goodTypeMapper.updateById(goodType));
    }

    @Override
    public Result deleteGoodType(GoodType goodType,HttpServletRequest httpServletRequest) {
        goodType.setUpdateBy(token.getId(httpServletRequest.getHeader("X-Token")));
        goodType.setUpdateName(token.getName(httpServletRequest.getHeader("X-Token")));
        goodType.setStatus(-1);
        return new Result(ResultEnum.SUCCESS,goodTypeMapper.updateById(goodType));
    }

}
