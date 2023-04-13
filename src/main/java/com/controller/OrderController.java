package com.controller;

import ch.qos.logback.classic.Logger;
import cn.hutool.core.bean.BeanUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.entity.*;
import com.mapper.OrderMapper;
import com.mapper.UserMapper;
import com.services.Impl.OrderService;
import com.utils.Result;
import com.utils.ResultEnum;
import com.utils.Token;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.LoggerFactory;
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
    Logger logger = (Logger) LoggerFactory.getLogger(Logger.class);

    @Resource
    OrderService orderService;

    @Resource
    UserMapper userMapper;
    @Resource
    OrderMapper orderMapper;
    @Resource
    Token token;


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


    @GetMapping("/familyOrder")
    public Result familyOrder(String jsonObject,HttpServletRequest httpServletRequest) {
        PageOrderDTO pageOrderDTO;
        if (!jsonObject.isEmpty()) {
            pageOrderDTO = JSON.parseObject(jsonObject, PageOrderDTO.class);
        } else {
            return new Result(ResultEnum.FAIL);
        }
        /**
         * 获取家庭商品订单
         */
        List<Long> familyList = new ArrayList<>();
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        /**
         * 获取户主ID
         */
        User user = userMapper.selectById(token.getId(httpServletRequest.getHeader("X-Token")));
        if (user.getHouseHolder() == 0) {
            queryWrapper.eq("house_holder", user.getId());

        } else {
            queryWrapper.eq("house_holder", user.getHouseHolder());
        }
        List<User> list = userMapper.selectList(queryWrapper);
        list.forEach(user1 -> {
            familyList.add(user1.getId());
        });
        familyList.add(user.getId());
        return orderService.getFamilyOrder(familyList, pageOrderDTO);
    }

    @GetMapping("/orderList")
    public Result orderList(String jsonObject,HttpServletRequest httpServletRequest) {
        PageOrderDTO pageOrderDTO;
        if (!jsonObject.isEmpty()) {
            pageOrderDTO = JSON.parseObject(jsonObject, PageOrderDTO.class);
        } else {
            return new Result(ResultEnum.FAIL);
        }
        return orderService.getOrderList(pageOrderDTO);
    }
}
