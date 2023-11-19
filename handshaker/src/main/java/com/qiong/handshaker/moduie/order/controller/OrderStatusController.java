package com.qiong.handshaker.moduie.order.controller;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.qiong.handshaker.anno.result.QResponseAdvice;
import com.qiong.handshaker.data.router.DataRouterOrder;
import com.qiong.handshaker.define.enums.EnumOrderStatus;
import com.qiong.handshaker.define.result.QResponse;
import com.qiong.handshaker.moduie.order.Order;
import com.qiong.handshaker.moduie.order.service.OrderService;
import com.qiong.handshaker.tool.result.QResponseTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
@QResponseAdvice
@RequestMapping(DataRouterOrder.STATUS)
public class OrderStatusController {

    @Autowired
    OrderService orderService;

    /**
    * 更改订单状态
    * @params
    * @return
    */
    @PatchMapping("/{id}")
    public QResponse<Object> setStatus(@RequestBody HashMap<String, String> statusMap, @PathVariable Long id) {

        Order order = orderService.getById(id);
        String newS = statusMap.get("status");

        if (newS != null) {
            if (!newS.equals( order.getOrder_status().getValue() )) {

                LambdaUpdateWrapper<Order> uw = new LambdaUpdateWrapper<>();
                uw.set(Order::getOrder_status, EnumOrderStatus.valueOf(newS));
                uw.eq(Order::getId, id);
                return QResponseTool.restfull(orderService.update(uw), order);
            }
        }
        return QResponseTool.restfull(false, "新订单状态相同或查找不到");
    }
}
