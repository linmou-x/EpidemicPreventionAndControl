package com.services;

import ch.qos.logback.classic.Logger;
import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.entity.Good;
import com.entity.Order;
import com.entity.OrderDTO;
import com.mapper.OrderMapper;
import com.services.Impl.GoodService;
import com.services.Impl.OrderService;
import com.utils.Result;
import com.utils.ResultEnum;
import com.utils.Token;
import org.apache.poi.util.StringUtil;
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
    Token token;

    Logger logger = (Logger) LoggerFactory.getLogger(Logger.class);
    /**
     * 获取订单列表
     * @param httpServletRequest
     */
    @Override
    public Result getOrderList(HttpServletRequest httpServletRequest) {
        String role=token.getRole(httpServletRequest.getHeader("token"));
        QueryWrapper<Order> queryWrapper=new QueryWrapper<>();
        if ("user".equals(role)){
            queryWrapper.eq("id",token.getId(httpServletRequest.getHeader("token")));
            return new Result(ResultEnum.SUCCESS,orderMapper.selectList(queryWrapper));
        }else{
            return new Result(ResultEnum.SUCCESS,orderMapper.selectALLOrder());
        }
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
        /**
         * 调用商品查询，获取商品数量，数量如果大于订购数则完成订单，负责失败
         */
        if(goodService.updateGoodAmount(order.getGood(),order.getAmount())){
            order.setRecordBy(1L);
            order.setUpdateBy(1L);
            orderMapper.insert(order);
            logger.debug("添加购买记录");
        }else{
            return new Result(ResultEnum.FAIL,"商品购买失败");
        }
        return new Result(ResultEnum.SUCCESS,"SUCCESS");
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
        Long id=token.getId(httpServletRequest.getHeader("token"));
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
        return new Result(ResultEnum.SUCCESS,"插入成功");
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
        Long id=token.getId(httpServletRequest.getHeader("token"));
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
