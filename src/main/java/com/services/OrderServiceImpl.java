package com.services;

import ch.qos.logback.classic.Logger;
import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
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
        if (orderDTO.getGood()==null){
            return new Result(ResultEnum.FAIL,"Service orderBatchInsert");
        } else if (orderDTO.getService()==null) {
            goodService.updateGoodAmount(order.getGood(),order.getAmount(),true);
        }
        /**
         * 调用商品查询，获取商品数量，数量如果大于订购数则完成订单，负责失败
         */
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
        final Good[] goods={null};
        orderDTOList.forEach(orderDTO -> {
            order[0]= BeanUtil.copyProperties(orderDTO,Order.class);
            if (!order[0].getGood().equals(null)||order[0].getStatus().equals(-1)){
                goods[0].setId(order[0].getGood());
                goods[0]= (Good) goodService.getGoodDetail(order[0].getGood()).getData();
                goods[0].setAmount((goods[0].getAmount()+order[0].getAmount()));
            }
            order[0].setUpdateBy(id);
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
        final Good[] goods={null};
        orderDTOList.forEach(orderDTO -> {
            order[0]= BeanUtil.copyProperties(orderDTO,Order.class);
            if (!order[0].getGood().equals(null)||order[0].getStatus().equals(-1)){
                goods[0].setId(order[0].getGood());
                goods[0]= (Good) goodService.getGoodDetail(order[0].getGood()).getData();
                goods[0].setAmount((goods[0].getAmount()+order[0].getAmount()));
            }
            order[0].setUpdateBy(id);
            orderMapper.updateById(order[0]);
        });
        return new Result(ResultEnum.SUCCESS,"删除成功");
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
