package com.qiong.handshaker.moduie.order.controller;


import com.qiong.handshaker.utils.anno.result.QResponseAdvice;
import com.qiong.handshaker.data.router.DataRouterOrder;
import com.qiong.handshaker.data.security.DataSecurityRoleConf;
import com.qiong.handshaker.utils.define.exception.vaiid.QLogicException;
import com.qiong.handshaker.utils.define.result.QResponse;
import com.qiong.handshaker.entity.moduie.base.Storehouse;
import com.qiong.handshaker.moduie.base.service.StorehouseService;
import com.qiong.handshaker.entity.moduie.order.Order;
import com.qiong.handshaker.entity.moduie.order.OrderProfit;
import com.qiong.handshaker.entity.moduie.order.Refunded;
import com.qiong.handshaker.moduie.order.service.OrderService;
import com.qiong.handshaker.moduie.order.service.ProfitService;
import com.qiong.handshaker.moduie.order.service.RefundedService;
import com.qiong.handshaker.utils.tool.result.QResponseTool;
import com.qiong.handshaker.entity.vo.order.VoRefundOperaForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

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

    /**
    * 退款退货
    * @params
    * @return
    */
    @PreAuthorize(DataSecurityRoleConf.AUTH_CASHIER)
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

        // 返回
        return QResponseTool.restfull(true, refunded);
    }
}
