package com.controller;

import ch.qos.logback.classic.Logger;
import com.entity.GoodDTO;
import com.entity.PageGoodDTO;
import com.mapper.GoodMapper;
import com.services.Impl.GoodService;
import com.utils.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
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
    @GetMapping(value = "/goodPage")
    @Operation(summary = "商品查询",description = "商品查询")
    public Result goodPage(PageGoodDTO pageGoodDTO,HttpServletRequest httpServletRequest){
        return goodService.getGoodList(pageGoodDTO,httpServletRequest);
    }

    @GetMapping("/batchInsert")
    @Operation(summary = "批量插入",description = "批量插入")
    public Result batchInsert(@RequestBody List<GoodDTO> goodDTOList,HttpServletRequest httpServletRequest){
        return goodService.batchImport(goodDTOList, httpServletRequest);
    }

    @GetMapping("/batchDelete")
    @Operation(summary = "批量删除",description = "批量删除")
    public Result batchDelete(@RequestBody List<GoodDTO> goodDTOList,HttpServletRequest httpServletRequest){
        return goodService.batchDelete(goodDTOList,  httpServletRequest);
    }

    @GetMapping("/batchUpdate")
    @Operation(summary = "批量修改",description = "批量修改")
    public Result batchUpdate(@RequestBody List<GoodDTO> goodDTOList,HttpServletRequest httpServletRequest){
        return goodService.batchUpdate(goodDTOList, httpServletRequest);
    }

}