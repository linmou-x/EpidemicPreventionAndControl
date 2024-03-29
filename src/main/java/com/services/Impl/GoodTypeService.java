package com.services.Impl;

import com.entity.GoodType;
import com.utils.Result;

import javax.servlet.http.HttpServletRequest;

public interface GoodTypeService {
    Result getGoodTypeList(Integer status,HttpServletRequest httpServletRequest);

    Result insertGoodType(GoodType goodType,HttpServletRequest httpServletRequest);

    Result updateGoodType(GoodType goodType,HttpServletRequest httpServletRequest);

    Result deleteGoodType(GoodType goodType, HttpServletRequest httpServletRequest);
}
