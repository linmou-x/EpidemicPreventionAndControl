package com.controller;

import cn.hutool.core.bean.BeanUtil;
import com.entity.GoodType;
import com.services.Impl.GoodTypeService;
import com.utils.Result;
import com.utils.ResultEnum;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/type")
public class GoodTypeController {

    @Resource
    GoodTypeService goodTypeService;
    @GetMapping("/getList")
    public Result getList(HttpServletRequest httpServletRequest){
        return goodTypeService.getGoodTypeList();
    }

    @GetMapping("/delete")
    public Result delete(String jsonObject,HttpServletRequest httpServletRequest){
        if (!jsonObject.isEmpty()){
            GoodType goodType= BeanUtil.copyProperties(jsonObject,GoodType.class);
            return goodTypeService.deleteGoodType(goodType,httpServletRequest);
        }else {
            return  new Result(ResultEnum.FAIL,"JSON is empty");
        }
    }

    @GetMapping("/insert")
    public Result insert(String jsonObject,HttpServletRequest httpServletRequest){
        if (!jsonObject.isEmpty()){
            GoodType goodType= BeanUtil.copyProperties(jsonObject,GoodType.class);
            return goodTypeService.insertGoodType(goodType,httpServletRequest);
        }else {
            return  new Result(ResultEnum.FAIL,"JSON is empty");
        }
    }

    @GetMapping("/update")
    public Result update(String jsonObject,HttpServletRequest httpServletRequest){
        if (!jsonObject.isEmpty()){
            GoodType goodType= BeanUtil.copyProperties(jsonObject,GoodType.class);
            return goodTypeService.updateGoodType(goodType,httpServletRequest);
        }else {
            return  new Result(ResultEnum.FAIL,"JSON is empty");
        }
    }
}
