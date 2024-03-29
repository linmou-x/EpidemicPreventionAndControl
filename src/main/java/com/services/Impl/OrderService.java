package com.services.Impl;

import com.entity.OrderDTO;
import com.entity.PageOrderDTO;
import com.entity.PageServiceDTO;
import com.utils.Result;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.Callable;

public interface OrderService extends Callable<Objects> {
    /**
     * 获取订单列表
     */
    Result orderPageWithService(HttpServletRequest httpServletRequest);

    Result orderPageWitchGood(PageOrderDTO pageOrderDTO, HttpServletRequest httpServletRequest);
    /**
     * 订单增加
     */
    Result batchInsert(OrderDTO orderDTO,HttpServletRequest httpServletRequest );
    /**
     * 订单更新
     */
    Result batchUpdate(List<OrderDTO>orderDTOList,HttpServletRequest httpServletRequest );
    /**
     * 订单取消
     */
    Result batchDelete(List<OrderDTO>orderDTOList,HttpServletRequest httpServletRequest );

    Result getFamilyOrder(List<Long> list,PageOrderDTO pageOrderDTO);

    Result getOrderList(PageOrderDTO pageOrderDTO);
}
