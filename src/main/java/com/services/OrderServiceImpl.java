package com.services;

import ch.qos.logback.classic.Logger;
import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.entity.*;
import com.mapper.OrderMapper;
import com.mapper.UserMapper;
import com.services.Impl.GoodService;
import com.services.Impl.OrderService;
import com.services.Impl.UserService;
import com.utils.Result;
import com.utils.ResultEnum;
import com.utils.Token;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Objects;

/**
 * @author charles
 */
@Service
public class OrderServiceImpl implements OrderService {

    @Resource
    OrderMapper orderMapper;

    @Resource
    GoodService goodService;

    @Resource
    UserService userService;

    @Resource
    UserMapper userMapper;


    @Resource
    Token token;

    Logger logger = (Logger) LoggerFactory.getLogger(Logger.class);
    /**
     * 获取订单列表
     * @param httpServletRequest
     */
    @Override
    public Result orderPageWithService( HttpServletRequest httpServletRequest) {
        Long id = token.getId(httpServletRequest.getHeader("X-Token"));
        User user=userMapper.selectById(id);
        QueryWrapper<User> userQueryWrapper=new QueryWrapper<>();
        QueryWrapper<Order> orderQueryWrapper=new QueryWrapper<>();
        if (user.getHouseHolder()==0){
            userQueryWrapper.eq("house_holder",user.getId());
            userQueryWrapper.or().eq("id",user.getId());
        }else {
            userQueryWrapper.eq("house_holder",user.getHouseHolder());
            userQueryWrapper.or().eq("id",user.getHouseHolder());
        }
        List<User> userList=userMapper.selectList(userQueryWrapper);
        orderQueryWrapper.eq("status",1);
        orderQueryWrapper.eq("good",0);
        orderQueryWrapper.and(orderQueryWrapper1 -> {
            userList.forEach(user1 -> {
                orderQueryWrapper1.or().eq("record_by",user1.getId());
            });
        });
        List<Order> orderList=orderMapper.selectList(orderQueryWrapper);
        return new Result(ResultEnum.SUCCESS,orderList);
    }

    @Override
    public Result orderPageWitchGood(PageOrderDTO pageOrderDTO, HttpServletRequest httpServletRequest) {
        Long id=token.getId(httpServletRequest.getHeader("X-Token"));
        QueryWrapper<Order> queryWrapper=new QueryWrapper<>();
        queryWrapper.isNotNull("good");
        List<String> list=userService.getList(id);
        list.forEach(userId->{
            queryWrapper.or().eq("record_by",id);
        });
        return new Result(ResultEnum.SUCCESS,orderMapper.selectList(queryWrapper));
    }

    /**
     * 订单增加
     * @param
     * @param httpServletRequest
     */
    @Override
    public Result batchInsert(OrderDTO orderDTO, HttpServletRequest httpServletRequest) {
        if(orderDTO.getGood()==null&&orderDTO.getService()==null){
            return new Result(ResultEnum.FAIL,"禁止空数组");
        }
        logger.debug(orderDTO.toString());
        Order order=BeanUtil.copyProperties(orderDTO,Order.class);
        if (orderDTO.getService()==0) {
            if (goodService.updateGoodAmount(order.getGood(), order.getAmount(), true)){
                order.setUpdateBy(token.getId(httpServletRequest.getHeader("X-Token")));
                order.setUpdateName(token.getName(httpServletRequest.getHeader("X-Token")));
                order.setRecordBy(token.getId(httpServletRequest.getHeader("X-Token")));
                orderMapper.insert(order);
                return new Result(ResultEnum.SUCCESS,"商品订购成功");
            }else{
                return new Result(ResultEnum.FAIL,"商品订购失败");
            }
        }
        if (orderDTO.getGood()==0){
            logger.debug("服务订购");
            if(serviceBuy(order.getService(), httpServletRequest)){
                return new Result(ResultEnum.FAIL,"家庭已订购该服务");
            }
        }
        order.setUpdateBy(token.getId(httpServletRequest.getHeader("X-Token")));
        order.setUpdateName(token.getName(httpServletRequest.getHeader("X-Token")));
        order.setRecordBy(token.getId(httpServletRequest.getHeader("X-Token")));
        orderMapper.insert(order);
        return new Result(ResultEnum.SUCCESS,"服务订购成功");
    }

    /**
     * 订单更新
     * @param orderDTOList
     * @param httpServletRequest
     */
    @Override
    public Result batchUpdate(List<OrderDTO> orderDTOList, HttpServletRequest httpServletRequest) {
        if(orderDTOList.isEmpty()){
            return new Result(ResultEnum.FAIL,"禁止空数组");
        }
        Long id=token.getId(httpServletRequest.getHeader("X-Token"));
        final Order[] order={null};
        orderDTOList.forEach(orderDTO -> {
            order[0]= BeanUtil.copyProperties(orderDTO,Order.class);
            order[0].setUpdateBy(id);
            order[0].setUpdateName(token.getName(httpServletRequest.getHeader("X-Token")));
            orderMapper.updateById(order[0]);
        });
        return new Result(ResultEnum.SUCCESS,"更新成功");
    }

    /**
     * 订单取消
     * @param orderDTOList
     * @param httpServletRequest
     */
    @Override
    public Result batchDelete(List<OrderDTO> orderDTOList, HttpServletRequest httpServletRequest) {
        if(orderDTOList.isEmpty()){
            return new Result(ResultEnum.FAIL,"禁止空数组");
        }
        Long id=token.getId(httpServletRequest.getHeader("X-Token"));
        final Order[] order={null};
        orderDTOList.forEach(orderDTO -> {
            order[0]= BeanUtil.copyProperties(orderDTO,Order.class);
            order[0].setUpdateBy(id);
            order[0].setUpdateName(token.getName(httpServletRequest.getHeader("X-Token")));
            logger.debug("订单取消");
            if (order[0].getService()==0){
                logger.debug("修改库存");
                goodService.updateGoodAmount(order[0].getGood(),order[0].getAmount(),false);
            }
            orderMapper.updateById(order[0]);
        });
        return new Result(ResultEnum.SUCCESS,"删除成功");
    }

    @Override
    public Result getFamilyOrder(List<Long> list,PageOrderDTO pageOrderDTO) {
        QueryWrapper<Order> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq(!"null".equals(String.valueOf(pageOrderDTO.getOrderDTO().getStatus())),"status",pageOrderDTO.getOrderDTO().getStatus());
        if (pageOrderDTO.getOrderDTO().getName()!="null"){
            queryWrapper.like("name",pageOrderDTO.getOrderDTO().getName());
        }
        logger.debug(pageOrderDTO.getOrderDTO().toString());
        if (pageOrderDTO.getOrderDTO().getGood()==0){
            queryWrapper.eq("good",0);
        } else if (pageOrderDTO.getOrderDTO().getService()==0) {
            queryWrapper.eq("service",0);
        }
        queryWrapper.and(orderQueryWrapper -> {
            list.forEach(id ->{
                orderQueryWrapper.or().eq("record_by",id);
            });
        });
        Page<Order> page=new Page<>(pageOrderDTO.getCurrentPage(), pageOrderDTO.getPageSize());
        Page<Order> orderPage=orderMapper.selectPage(page,queryWrapper);
        return new Result(ResultEnum.SUCCESS,orderPage);
    }

    @Override
    public Result getOrderList(PageOrderDTO pageOrderDTO) {
        Page<Order> page=new Page<>(pageOrderDTO.getCurrentPage(), pageOrderDTO.getPageSize());
        QueryWrapper<Order> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq(!"null".equals(String.valueOf(pageOrderDTO.getOrderDTO().getStatus())),"status",pageOrderDTO.getOrderDTO().getStatus());
        if (pageOrderDTO.getOrderDTO().getName()!="null"){
            queryWrapper.like("name",pageOrderDTO.getOrderDTO().getName());
        }
        if (pageOrderDTO.getOrderDTO().getGood()==0){
            queryWrapper.eq("good",0);
        } else if (pageOrderDTO.getOrderDTO().getService()==0) {
            queryWrapper.eq("service",0);
        }
        Page<Order> orderPage=orderMapper.selectPage(page,queryWrapper);
        return new Result(ResultEnum.SUCCESS,orderPage);
    }

    /**
     * Computes a result, or throws an exception if unable to do so.
     *
     * @return computed result
     * @throws Exception if unable to compute a result
     */
    @Override
    public Objects call() throws Exception {
        return null;
    }

    public Boolean serviceBuy(Long serviceID,HttpServletRequest httpServletRequest){
        Long id = token.getId(httpServletRequest.getHeader("X-Token"));
        User user=userMapper.selectById(id);
        QueryWrapper<User> userQueryWrapper=new QueryWrapper<>();
        QueryWrapper<Order> orderQueryWrapper=new QueryWrapper<>();
        if (user.getHouseHolder()==0){
            userQueryWrapper.eq("house_holder",user.getId());
            userQueryWrapper.or().eq("id",user.getId());
        }else {
            userQueryWrapper.eq("house_holder",user.getHouseHolder());
            userQueryWrapper.or().eq("id",user.getHouseHolder());
        }
        List<User> userList=userMapper.selectList(userQueryWrapper);
        orderQueryWrapper.eq("status",1);
        orderQueryWrapper.eq("good",0);
        orderQueryWrapper.and(orderQueryWrapper1 -> {
            userList.forEach(user1 -> {
                orderQueryWrapper1.or().eq("record_by",user1.getId());
            });
        });
        List<Order> orderList=orderMapper.selectList(orderQueryWrapper);
        if (orderList.isEmpty()){
            return false;
        }
        Boolean isMatch=orderList.stream()
                .allMatch(order -> {
                    logger.debug(String.valueOf(order));
                    return order.getService().equals(serviceID);
                });
        logger.debug(isMatch.toString());
        logger.debug(serviceID.toString());
        return isMatch;
    }
}
