package com.controller;

import com.alibaba.fastjson.JSON;
import com.entity.GoodDTO;
import com.entity.Order;
import com.entity.OrderDTO;
import com.entity.PageOrderDTO;
import com.mapper.OrderMapper;
import com.services.Impl.OrderService;
import com.utils.Result;
import com.utils.ResultEnum;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author charles
 */

@RestController
@ResponseBody
@Slf4j
@CrossOrigin(origins = "*")
@Tag(name = "OrderController",description = "服务管理")
@RequestMapping("/order")
public class OrderController {

    @Resource
    OrderService orderService;
    @Resource
    OrderMapper orderMapper;

    @GetMapping("/getGoodOrderList")
    public Result getGoodOrderList(PageOrderDTO pageOrderDTO,HttpServletRequest httpServletRequest){
        return orderService.orderPageWitchGood(pageOrderDTO,httpServletRequest);
    }

    @GetMapping("/getServiceOrderList")
    public Result getServiceOrderList(PageOrderDTO pageOrderDTO, HttpServletRequest httpServletRequest){
        return orderService.orderPageWithService(pageOrderDTO,httpServletRequest);
    }

    /**
     * 商品或服务订购操作
     * @param httpServletRequest
     * @param jsonObject
     * @return
     */
    @GetMapping("/batchInsert")

    public Result batchInsert(String jsonObject,HttpServletRequest httpServletRequest){
        if (!jsonObject.isEmpty())
        {
            OrderDTO orderDTO= JSON.parseObject(jsonObject, OrderDTO.class);
            return orderService.batchInsert(orderDTO,httpServletRequest);
        }else {
            return new Result(ResultEnum.FAIL,"Str为空");
        }
    }

    @GetMapping("/batchUpdate")
    public Result batchUpdate(HttpServletRequest httpServletRequest,String jsonObject){
        if (!jsonObject.isEmpty())
        {
            OrderDTO orderDTO= JSON.parseObject(jsonObject, OrderDTO.class);
            List<OrderDTO> list =new ArrayList<>();
            list.add(orderDTO);
            return orderService.batchUpdate(list,httpServletRequest);
        }else {
            return new Result(ResultEnum.FAIL,"Str为空");
        }
    }
    @GetMapping("/batchDelete")
    public Result batchDelete(HttpServletRequest httpServletRequest,String jsonObject){
        if (!jsonObject.isEmpty())
        {
            OrderDTO orderDTO= JSON.parseObject(jsonObject, OrderDTO.class);
            List<OrderDTO> list =new ArrayList<>();
            list.add(orderDTO);
            return orderService.batchDelete(list,httpServletRequest);
        }else {
            return new Result(ResultEnum.FAIL,"Str为空");
        }
    }


    @GetMapping("/insert")
    public void orderInsert(@RequestBody Order order){
        orderMapper.insert(order);
    }
}
