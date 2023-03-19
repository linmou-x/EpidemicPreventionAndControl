package com.services;

import ch.qos.logback.classic.Logger;
import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.entity.Good;
import com.entity.GoodDTO;
import com.entity.Order;
import com.entity.OrderDTO;
import com.mapper.OrderMapper;
import com.services.Impl.GoodService;
import com.services.Impl.OrderService;
import com.utils.Result;
import com.utils.ResultEnum;
import com.utils.Token;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

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
        if (role.equals("user")){
            queryWrapper.eq("id",token.getId(httpServletRequest.getHeader("token")));
            return new Result(ResultEnum.SUCCESS,orderMapper.selectList(queryWrapper));
        }else{
            return new Result(ResultEnum.SUCCESS,orderMapper.selectALLOrder());
        }
    }

    /**
     * 订单增加
     * @param orderDTOList
     * @param httpServletRequest
     */
    @Override
    public Result batchInsert(List<OrderDTO> orderDTOList, HttpServletRequest httpServletRequest) {
        if(orderDTOList.isEmpty()){
            return new Result(ResultEnum.FAIL,"禁止空数组");
        }
        Long id=token.getId(httpServletRequest.getHeader("token"));
        final Order[] order={null};
        final Good[] good={null};
        final GoodDTO[] goodDTOS={null};
        List<GoodDTO> goodDTOList=new ArrayList<>();
        orderDTOList.forEach(orderDTO -> {
            order[0]= BeanUtil.copyProperties(orderDTO,Order.class);
            if (!order[0].getGood().equals(null)){
                logger.debug("商品ID"+order[0].getGood());
                good[0]= (Good) goodService.getGoodDetail(order[0].getGood()).getData();
                good[0].setAmount((good[0].getAmount()-order[0].getAmount()));
                goodDTOS[0]=BeanUtil.copyProperties(good[0],GoodDTO.class);
                logger.debug(goodDTOS[0].toString());
                goodDTOList.add(goodDTOS[0]);
                logger.debug(goodDTOList.toString());
                goodService.batchUpdate(goodDTOList,httpServletRequest);
            }
            order[0].setRecordBy(id);
            order[0].setUpdateBy(id);
            logger.debug(order[0].toString());
            orderMapper.insert(order[0]);
        });
        return new Result(ResultEnum.SUCCESS,"插入成功");
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
            orderMapper.insert(order[0]);
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
        for (OrderDTO orderDTO:orderDTOList){
            order[0]= BeanUtil.copyProperties(orderDTO,Order.class);
            order[0].setUpdateBy(id);
            orderMapper.updateById(order[0]);
        }
        return new Result(ResultEnum.SUCCESS,"删除成功");
    }
}
