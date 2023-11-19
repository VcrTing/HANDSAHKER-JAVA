package com.qiong.handshaker.moduie.order.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qiong.handshaker.define.exception.vaiid.QLogicException;
import com.qiong.handshaker.define.result.QPager;
import com.qiong.handshaker.moduie.order.Order;
import com.qiong.handshaker.moduie.order.OrderProfit;
import com.qiong.handshaker.moduie.order.Refunded;
import com.qiong.handshaker.moduie.order.mapper.ProfitMapper;
import com.qiong.handshaker.view.order.ViewOrderResultForm;
import com.qiong.handshaker.view.order.ViewProfitResultForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class ProfitService extends ServiceImpl<ProfitMapper, OrderProfit> {

    @Autowired
    ProfitMapper mapper;

    public QPager<ViewProfitResultForm> pageList(IPage<OrderProfit> ip, QueryWrapper<OrderProfit> qw) {

        List<ViewProfitResultForm> res = new ArrayList<>();

        ip.setRecords(mapper.pageList(ip, qw));

        if (ip.getRecords() != null) {
            for(OrderProfit o: ip.getRecords()) {
                res.add(ViewProfitResultForm.init(o, null, null));
            }
        }
        return QPager.ofPage(ip, res);
    }

    // 单次 退款
    public void refunded(OrderProfit profit, BigDecimal refund_price) {
        if (profit.getId() == null) throw new QLogicException("找不到退款利率数据");
        System.out.println("PROFIT = " + profit);
        boolean can = profit.doRefunded(refund_price);
        if (!can) throw new QLogicException("退款总金额对大于订单总金额");

        LambdaUpdateWrapper<OrderProfit> uw = new LambdaUpdateWrapper<>();
        uw.eq(OrderProfit::getId, profit.getId());
        uw.set(OrderProfit::getRefund_price, profit.getRefund_price());

        this.update(uw);
    }
}
