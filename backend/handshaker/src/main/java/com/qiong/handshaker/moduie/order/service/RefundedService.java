package com.qiong.handshaker.moduie.order.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qiong.handshaker.entity.moduie.order.Order;
import com.qiong.handshaker.entity.moduie.order.OrderProfit;
import com.qiong.handshaker.entity.moduie.order.Refunded;
import com.qiong.handshaker.moduie.order.mapper.RefundedMapper;
import com.qiong.handshaker.moduie.product.service.VariationAndStorehouseAndProductService;
import com.qiong.handshaker.entity.vo.order.VoRefundOperaForm;
import com.qiong.handshaker.entity.vo.order.refund.VoInnerRefundedInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RefundedService extends ServiceImpl<RefundedMapper, Refunded> {

    @Autowired
    ProfitService profitService;

    @Autowired
    OrderService orderService;

    @Autowired
    VariationAndStorehouseAndProductService variationAndStorehouseAndProductService;

    /**
    * 执行 退货 退款
    * @params
    * @return
    */
    @Transactional
    public Refunded refunded(VoRefundOperaForm form, Order order, OrderProfit profit) {
        // 生成 退款 实体
        Refunded refund = Refunded.init(form, order.getId(), profit.getId());

        // 更新 退款金额 到 利率表
        profitService.refunded(profit, refund.getRefunded_price());

        // 更改 订单 退货 数量
        orderService.refundedQuantity(order, form.getRefunded_info(), refund.getRefunded_remarks());

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
