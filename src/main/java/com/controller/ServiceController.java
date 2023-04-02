package com.controller;

import ch.qos.logback.classic.Logger;
import com.alibaba.fastjson.JSON;
import com.entity.OrderDTO;
import com.entity.PageServiceDTO;
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
import java.util.ArrayList;
import java.util.List;

@RestController
@ResponseBody
@Slf4j
@CrossOrigin(origins = "*")
@Tag(name = "ServiceController",description = "服务管理")
@RequestMapping("/service")
public class ServiceController {
    Logger logger = (Logger) LoggerFactory.getLogger(Logger.class);
    @Resource
    public ServiceService serviceService;

    @Resource
    public ServiceMapper serviceMapper;

    @GetMapping(value = "/servicePage")
    @Operation(summary = "服务查询",description = "服务查询")
    public Result servicePage(String jsonObject,HttpServletRequest httpServletRequest){
        return new Result(ResultEnum.SUCCESS,"this is Paging query result",serviceService);
    }

    @GetMapping("/batchInsert")
    @Operation(summary = "批量插入",description = "批量插入")
    public Result batchInsert(String jsonObject, HttpServletRequest httpServletRequest){
        if (!jsonObject.isEmpty())
        {
            ServiceDTO serviceDTO= JSON.parseObject(jsonObject, ServiceDTO.class);
            List<ServiceDTO> list =new ArrayList<>();
            list.add(serviceDTO);
            return serviceService.batchImport(list,httpServletRequest);
        }else {
            return new Result(ResultEnum.FAIL,"Str为空");
        }
    }

    @GetMapping("/batchDelete")
    @Operation(summary = "批量删除",description = "批量删除")
    public Result batchDelete(String jsonObject, HttpServletRequest httpServletRequest){
        if (!jsonObject.isEmpty())
        {
            ServiceDTO serviceDTO= JSON.parseObject(jsonObject, ServiceDTO.class);
            List<ServiceDTO> list =new ArrayList<>();
            list.add(serviceDTO);
            return serviceService.batchDelete(list,httpServletRequest);
        }else {
            return new Result(ResultEnum.FAIL,"Str为空");
        }
    }

    @GetMapping("/batchModify")
    @Operation(summary = "批量修改",description = "批量修改")
    public Result batchModify(String jsonObject, HttpServletRequest httpServletRequest){
        if (!jsonObject.isEmpty())
        {
            ServiceDTO serviceDTO= JSON.parseObject(jsonObject, ServiceDTO.class);
            List<ServiceDTO> list =new ArrayList<>();
            list.add(serviceDTO);
            return serviceService.batchUpdate(list,httpServletRequest);
        }else {
            return new Result(ResultEnum.FAIL,"Str为空");
        }
    }
}
