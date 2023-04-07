package com.services.Impl;

import com.entity.GoodType;
import com.utils.Result;

public interface GoodTypeService {
    Result getGoodTypeList();

    Result insertGoodType(GoodType goodType);

    Result updateGoodType(GoodType goodType);

    Result deleteGoodType(GoodType goodType);
}
