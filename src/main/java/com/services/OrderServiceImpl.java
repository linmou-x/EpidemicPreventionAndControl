package com.services;

import ch.qos.logback.classic.Logger;
import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.entity.Good;
import com.entity.Order;
import com.entity.OrderDTO;
import com.entity.PageOrderDTO;
import com.mapper.OrderMapper;
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
    Token token;

    Logger logger = (Logger) LoggerFactory.getLogger(Logger.class);
    /**
     * 获取订单列表
     * @param httpServletRequest
     */
    @Override
    public Result orderPageWithService(PageOrderDTO pageOrderDTO, HttpServletRequest httpServletRequest) {
        Long id=token.getId(httpServletRequest.getHeader("X-Token"));
        QueryWrapper<Order> queryWrapper=new QueryWrapper<>();
        queryWrapper.isNotNull("service");
        List<String> list=userService.getList(id);
        list.forEach(userId->{
            queryWrapper.or().eq("record_by",id);
        });
        return new Result(ResultEnum.SUCCESS,orderMapper.selectList(queryWrapper));
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
        Order order=BeanUtil.copyProperties(orderDTO,Order.class);
        if (orderDTO.getGood()==0){
            return new Result(ResultEnum.FAIL,"Service orderBatchInsert");
        } else if (orderDTO.getService()==0) {
            goodService.updateGoodAmount(order.getGood(), order.getAmount(), true);
        }
        /**
         * 调用商品查询，获取商品数量，数量如果大于订购数则完成订单，负责失败
         */
        order.setUpdateBy(token.getId(httpServletRequest.getHeader("X-Token")));
        order.setUpdateName(token.getName(httpServletRequest.getHeader("X-Token")));
        order.setRecordBy(token.getId(httpServletRequest.getHeader("X-Token")));
        logger.debug("AAAA"+order.getImage());
        orderMapper.insert(order);
        return new Result(ResultEnum.FAIL,"orderBatchInsert");
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
}
