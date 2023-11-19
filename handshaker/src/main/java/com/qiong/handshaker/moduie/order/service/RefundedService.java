package com.qiong.handshaker.moduie.order.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qiong.handshaker.define.exception.vaiid.QLogicException;
import com.qiong.handshaker.moduie.order.Order;
import com.qiong.handshaker.moduie.order.OrderProfit;
import com.qiong.handshaker.moduie.order.Refunded;
import com.qiong.handshaker.moduie.order.mapper.RefundedMapper;
import com.qiong.handshaker.moduie.product.VariationAndStorehouseAndProduct;
import com.qiong.handshaker.moduie.product.service.VariationAndStorehouseAndProductService;
import com.qiong.handshaker.vo.order.VoRefundOperaForm;
import com.qiong.handshaker.vo.order.refund.VoInnerRefundedInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RefundedService extends ServiceImpl<RefundedMapper, Refunded> {

    @Autowired
    ProfitService profitService;

    @Autowired
    OrderService orderService;

    @Autowired
    VariationAndStorehouseAndProductService variationAndStorehouseAndProductService;

    public Refunded refunded(VoRefundOperaForm form, Order order, OrderProfit profit) {
        // 生成 退款 实体
        Refunded refund = Refunded.init(form, order.getId(), profit.getId());
        System.out.println("REFUNDED = " + refund);
        // 更新 退款金额 到 利率表
        profitService.refunded(profit, refund.getRefunded_price());

        // 更改 订单 退货 数量
        boolean can = orderService.refundedQuantity(order, form.getRefunded_info(), refund.getRefunded_remarks());

        // 返还 库存
        // 获取 库存 表
        for(VoInnerRefundedInfo ri : form.getRefunded_info()) {
            // 执行加货
            variationAndStorehouseAndProductService.insertQuantity(ri.getProduct(), ri.getVariation(), form.getStorehouse(), ri.mustGetQuantity());
        }

        // 储存 退款 表
        this.save(refund);

        // 返回
        return refund;
    }
}
