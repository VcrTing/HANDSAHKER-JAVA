package com.qiong.handshaker.moduie.order.controller;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.qiong.handshaker.anno.result.QResponseAdvice;
import com.qiong.handshaker.data.router.DataRouterOrder;
import com.qiong.handshaker.data.security.DataSecurityRoleConf;
import com.qiong.handshaker.define.enums.EnumOrderStatus;
import com.qiong.handshaker.define.exception.vaiid.QLogicException;
import com.qiong.handshaker.define.result.QResponse;
import com.qiong.handshaker.moduie.order.Order;
import com.qiong.handshaker.moduie.order.OrderProfit;
import com.qiong.handshaker.moduie.order.service.OrderService;
import com.qiong.handshaker.moduie.order.service.ProfitService;
import com.qiong.handshaker.tool.result.QResponseTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
@QResponseAdvice
@RequestMapping(DataRouterOrder.STATUS)
public class OrderStatusController {

    @Autowired
    OrderService orderService;

    @Autowired
    ProfitService profitService;

    // 更新 订单 状态
    public boolean updateOrderStatus(Long id, EnumOrderStatus status) {
        LambdaUpdateWrapper<Order> uw = new LambdaUpdateWrapper<>();
        uw.set(Order::getOrder_status, status);
        uw.eq(Order::getId, id);
        return orderService.update(uw);
    }

    /**
    * 更改订单状态
    * @params
    * @return
    */
    @PreAuthorize(DataSecurityRoleConf.AUTH_CASHIER)
    @PatchMapping("/{id}")
    @Transactional
    public QResponse<Object> updStatus(@RequestBody HashMap<String, String> statusMap, @PathVariable Long id) {

        Order order = orderService.getById(id);
        String newS = statusMap.get("status");

        if (newS != null) {
            // 不应该 与 旧 状态 相同
            if (!newS.equals( order.getOrder_status().getValue() )) {

                // 新 状态
                order.setOrder_status(EnumOrderStatus.valueOf(newS));

                // 更改 订单表的 订单状态
                if (!updateOrderStatus(id, order.getOrder_status())) throw new QLogicException("订单状态修改失败，请重试！！！");

                // 更改 利率表的 订单状态
                if (!profitService.asyncOrderStatus(order)) throw new QLogicException("订单状态同步失败，请重试！！！");

                return QResponseTool.restfull(true, order);
            }
        }
        return QResponseTool.restfull(false, "新订单状态相同或查找不到！！！");
    }
}
