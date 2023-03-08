package com.services;

import com.mapper.GoodsMapper;
import com.services.Impl.GoodsServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @Author：Charles
 * @Package：com.services
 * @Project：EpidemicPreventionAndControl
 * @name：GoodsServiceImpl
 * @Date：2023/3/8 14:51
 * @Filename：GoodsServiceImpl
 */
@Service
public class GoodsServiceImpl implements GoodsService {
    @Resource
    private GoodsMapper goodsMapper;
}
