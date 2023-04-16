package com.controller;

import ch.qos.logback.classic.Logger;
import com.alibaba.fastjson.JSON;
import com.entity.GoodType;
import com.services.Impl.GoodTypeService;
import com.utils.Result;
import com.utils.ResultEnum;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/type")
@CrossOrigin(origins = "*")
public class GoodTypeController {

    Logger logger = (Logger) LoggerFactory.getLogger(Logger.class);
    @Resource
    GoodTypeService goodTypeService;
    @GetMapping("/getList")
    public Result getList(Integer status,HttpServletRequest httpServletRequest){
        return goodTypeService.getGoodTypeList(status,httpServletRequest);
    }

    @GetMapping("/delete")
    public Result delete(String jsonObject,HttpServletRequest httpServletRequest){
        if (!jsonObject.isEmpty()){
            GoodType goodType= JSON.parseObject(jsonObject,GoodType.class);
            return goodTypeService.deleteGoodType(goodType,httpServletRequest);
        }else {
            return  new Result(ResultEnum.FAIL,"JSON is empty");
        }
    }

    @GetMapping("/insert")
    public Result insert(String jsonObject,HttpServletRequest httpServletRequest){
        if (!jsonObject.isEmpty()){
            logger.debug(jsonObject);
            GoodType goodType= JSON.parseObject(jsonObject,GoodType.class);
            logger.debug(goodType.toString()+"AAA");
            return goodTypeService.insertGoodType(goodType,httpServletRequest);
        }else {
            return  new Result(ResultEnum.FAIL,"JSON is empty");
        }
    }

    @GetMapping("/update")
    public Result update(String jsonObject,HttpServletRequest httpServletRequest){
        if (!jsonObject.isEmpty()){
            GoodType goodType= JSON.parseObject(jsonObject, GoodType.class);
            logger.debug(goodType.toString()+"AAA");
            return goodTypeService.updateGoodType(goodType,httpServletRequest);
        }else {
            return  new Result(ResultEnum.FAIL,"JSON is empty");
        }
    }
}
