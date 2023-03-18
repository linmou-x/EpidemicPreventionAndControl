package com.controller;

import ch.qos.logback.classic.Logger;
import com.entity.Good;
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
@Tag(name = "GoodController",description = "商品管理")
@RequestMapping("/good")
public class GoodController {
    Logger logger = (Logger) LoggerFactory.getLogger(Logger.class);
    @Resource
    public GoodService goodService;

    @Resource
    public GoodMapper goodMapper;
    @GetMapping(value = "/getGoodList")
    @Operation(summary = "商品查询",description = "商品查询")
    public Result selectByPage(){
        return new Result(ResultEnum.SUCCESS,"this is Paging query result",goodService.getGoodList());
    }

    @GetMapping("/batchInsert")
    @Operation(summary = "批量插入",description = "批量插入")
    public Result batchInsert(@RequestBody List<Good> goodList){
        return goodService.batchImport(goodList);
    }

    @GetMapping("/batchDelete")
    @Operation(summary = "批量删除",description = "批量删除")
    public Result batchDelete(@RequestBody List<Good> goodList){
        return goodService.batchDelete(goodList);
    }

    @GetMapping("/batchModify")
    @Operation(summary = "批量修改",description = "批量修改")
    public Result batchModify(@RequestBody List<Good> goodList){
        return goodService.batchModify(goodList);
    }
}
