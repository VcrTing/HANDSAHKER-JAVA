package com.qiong.handshaker.moduie.order.controller;


import com.qiong.handshaker.anno.result.QResponseAdvice;
import com.qiong.handshaker.data.router.DataRouterOrder;
import com.qiong.handshaker.define.exception.vaiid.QLogicException;
import com.qiong.handshaker.define.result.QResponse;
import com.qiong.handshaker.moduie.base.Storehouse;
import com.qiong.handshaker.moduie.base.service.StorehouseService;
import com.qiong.handshaker.moduie.order.Order;
import com.qiong.handshaker.moduie.order.OrderProfit;
import com.qiong.handshaker.moduie.order.Refunded;
import com.qiong.handshaker.moduie.order.service.OrderService;
import com.qiong.handshaker.moduie.order.service.ProfitService;
import com.qiong.handshaker.moduie.order.service.RefundedService;
import com.qiong.handshaker.moduie.product.VariationAndStorehouseAndProduct;
import com.qiong.handshaker.moduie.product.service.VariationAndStorehouseAndProductService;
import com.qiong.handshaker.tool.result.QResponseTool;
import com.qiong.handshaker.vo.order.VoRefundOperaForm;
import com.qiong.handshaker.vo.order.refund.VoInnerRefundedInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@QResponseAdvice
@RequestMapping(DataRouterOrder.REFUND)
public class RefundController {

    @Autowired
    RefundedService service;

    @Autowired
    OrderService orderService;

    @Autowired
    StorehouseService storehouseService;

    @Autowired
    ProfitService profitService;


    @PatchMapping("/{id}")
    public QResponse<Object> refund(@PathVariable Long id, @RequestBody VoRefundOperaForm form) {

        // 获取 订单
        Order order = orderService.getById(id);
        if (order == null) throw new QLogicException("无法找到要退款的订单");

        // 获取 利率
        OrderProfit profit = profitService.getById(order.getProfit_sql_id());
        if (profit == null) throw new QLogicException("无法找到要退款的利率数据");

        // 检测 仓库
        Storehouse storehouse = storehouseService.getById(form.getStorehouse());
        if (storehouse == null) throw new QLogicException("无法找到要退款的仓库");

        // 调用 退款 功能
        Refunded refunded = service.refunded(form, order, profit);

        return QResponseTool.restfull(true, refunded);
    }
}
