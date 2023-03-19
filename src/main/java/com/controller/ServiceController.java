package com.controller;

import ch.qos.logback.classic.Logger;
import com.entity.Service;
import com.entity.ServiceDTO;
import com.mapper.ServiceMapper;
import com.services.Impl.ServiceService;
import com.utils.Result;
import com.utils.ResultEnum;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@ResponseBody
@Slf4j
@Tag(name = "ServiceController",description = "服务管理")
@RequestMapping("/service")
public class ServiceController {
    Logger logger = (Logger) LoggerFactory.getLogger(Logger.class);
    @Resource
    public ServiceService serviceService;

    @Resource
    public ServiceMapper serviceMapper;

    @GetMapping(value = "/getServiceList")
    @Operation(summary = "服务查询",description = "服务查询")
    public Result selectByPage(){
        return new Result(ResultEnum.SUCCESS,"this is Paging query result",serviceMapper.getServiceList());
    }

    @GetMapping("/batchInsert")
    @Operation(summary = "批量插入",description = "批量插入")
    public Result batchInsert(@RequestBody List<ServiceDTO> serviceDTOList, HttpServletRequest httpServletRequest){
        return serviceService.batchImport(serviceDTOList,httpServletRequest);
    }

    @GetMapping("/batchDelete")
    @Operation(summary = "批量删除",description = "批量删除")
    public Result batchDelete(@RequestBody List<ServiceDTO> serviceDTOList, HttpServletRequest httpServletRequest){
        return serviceService.batchDelete(serviceDTOList,httpServletRequest);
    }

    @GetMapping("/batchModify")
    @Operation(summary = "批量修改",description = "批量修改")
    public Result batchModify(@RequestBody List<ServiceDTO> serviceDTOList, HttpServletRequest httpServletRequest){
        return serviceService.batchUpdate(serviceDTOList,httpServletRequest);
    }
}
