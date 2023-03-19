package com.services.Impl;

import com.entity.OrderDTO;
import com.utils.Result;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

public interface OrderService {
    /**
     * 获取订单列表
     */
    Result getOrderList(HttpServletRequest httpServletRequest);
    /**
     * 订单增加
     */
    Result batchInsert(List<OrderDTO>orderDTOList,HttpServletRequest httpServletRequest );
    /**
     * 订单更新
     */
    Result batchUpdate(List<OrderDTO>orderDTOList,HttpServletRequest httpServletRequest );
    /**
     * 订单取消
     */
    Result batchDelete(List<OrderDTO>orderDTOList,HttpServletRequest httpServletRequest );
}
