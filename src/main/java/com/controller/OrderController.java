package com.controller;

import com.entity.Order;
import com.entity.OrderDTO;
import com.mapper.OrderMapper;
import com.services.Impl.OrderService;
import com.utils.Result;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * @author charles
 */

@RestController
@ResponseBody
@Slf4j
@Tag(name = "OrderController",description = "服务管理")
@RequestMapping("/order")
public class OrderController {

    @Resource
    OrderService orderService;
    @Resource
    OrderMapper orderMapper;

    @GetMapping("/getOrderList")
    public Result getOrderList(HttpServletRequest httpServletRequest){
        return orderService.getOrderList(httpServletRequest);
    }

    @GetMapping("/batchInsert")
    public Result batchInsert(HttpServletRequest httpServletRequest, @RequestBody OrderDTO orderDTO){
        return orderService.batchInsert(orderDTO, httpServletRequest);
    }

    @GetMapping("/batchUpdate")
    public Result batchUpdate(HttpServletRequest httpServletRequest,@RequestBody List<OrderDTO> orderDTOList){
        return orderService.batchUpdate(orderDTOList, httpServletRequest);
    }
    @GetMapping("/batchDelete")
    public Result batchDelete(HttpServletRequest httpServletRequest,@RequestBody List<OrderDTO> orderDTOList){
        return orderService.batchDelete(orderDTOList, httpServletRequest);
    }


    @GetMapping("/insert")
    public void orderInsert(@RequestBody Order order){
        orderMapper.insert(order);
    }
}
