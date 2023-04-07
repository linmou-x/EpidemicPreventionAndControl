package com.controller;

import ch.qos.logback.classic.Logger;
import com.alibaba.fastjson.JSON;
import com.entity.GoodDTO;
import com.entity.PageGoodDTO;
import com.entity.UserDTO;
import com.mapper.GoodMapper;
import com.services.Impl.GoodService;
import com.utils.Result;
import com.utils.ResultEnum;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author：Charles
 * @Package：com.controller
 * @Project：EpidemicPreventionAndControl
 * @name：GoodsController
 * @Date：3/11/2023 3:58 PM
 * @Filename：GoodsController
 */
@RestController
@ResponseBody
@Slf4j
@CrossOrigin(origins = "*")
@Tag(name = "GoodController",description = "商品管理")
@RequestMapping("/good")
public class GoodController {
    Logger logger = (Logger) LoggerFactory.getLogger(Logger.class);
    @Resource
    public GoodService goodService;

    @Resource
    public GoodMapper goodMapper;
    @GetMapping(value = "/goodPage")
    @Operation(summary = "商品查询",description = "商品查询")
    public Result goodPage(String jsonObject,HttpServletRequest httpServletRequest){
        if (!jsonObject.isEmpty())
        {
            PageGoodDTO pageGoodDTO= JSON.parseObject(jsonObject, PageGoodDTO.class);
            return goodService.getGoodList(pageGoodDTO, httpServletRequest);
        }else {
            return new Result(ResultEnum.FAIL,"Str为空");
        }
    }

    @GetMapping("/batchInsert")
    @Operation(summary = "批量插入",description = "批量插入")
    public Result batchInsert(String  jsonObject,HttpServletRequest httpServletRequest){
        if (!jsonObject.isEmpty())
        {
            GoodDTO goodDTO= JSON.parseObject(jsonObject, GoodDTO.class);
            List<GoodDTO> list =new ArrayList<>();
            list.add(goodDTO);
            return goodService.batchImport(list, httpServletRequest);
        }else {
            return new Result(ResultEnum.FAIL,"Str为空");
        }
    }

    @GetMapping("/batchDelete")
    @Operation(summary = "批量删除",description = "批量删除")
    public Result batchDelete(String  jsonObject,HttpServletRequest httpServletRequest){
        if (!jsonObject.isEmpty())
        {
            GoodDTO goodDTO= JSON.parseObject(jsonObject, GoodDTO.class);
            List<GoodDTO> list =new ArrayList<>();
            list.add(goodDTO);
            return goodService.batchDelete(list, httpServletRequest);
        }else {
            return new Result(ResultEnum.FAIL,"Str为空");
        }
    }

    @GetMapping("/batchUpdate")
    @Operation(summary = "批量修改",description = "批量修改")
    public Result batchUpdate(String jsonObject,HttpServletRequest httpServletRequest){
        if (!jsonObject.isEmpty())
        {
            GoodDTO goodDTO= JSON.parseObject(jsonObject, GoodDTO.class);
            List<GoodDTO> list =new ArrayList<>();
            list.add(goodDTO);
            return goodService.batchUpdate(list, httpServletRequest);
        }else {
            return new Result(ResultEnum.FAIL,"Str为空");
        }
    }
}