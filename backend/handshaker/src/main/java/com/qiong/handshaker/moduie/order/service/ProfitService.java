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
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class ProfitService extends ServiceImpl<ProfitMapper, OrderProfit> {

    @Autowired
    ProfitMapper mapper;

    /**
    * 深度 分页 列表
    * @params
    * @return
    */
    public QPager<ViewProfitResultForm> pageDeep(IPage<OrderProfit> ip, QueryWrapper<OrderProfit> qw) {

        List<ViewProfitResultForm> res = new ArrayList<>();
        ip.setRecords(mapper.pageList(ip, qw));

        // 计算 总 毛利率
        // 以及 生成 ResultForm
        BigDecimal profit = new BigDecimal(0);
        if (ip.getRecords() != null) {
            for(OrderProfit o: ip.getRecords()) {
                ViewProfitResultForm pr = ViewProfitResultForm.init(o, o.getCashier(), o.getMember());
                // 累加 总 毛利率
                profit = profit.add(pr.getTotal_profit());
                res.add(pr);
            }
        }
        // 组成 返回 数据
        QPager<ViewProfitResultForm> qPager = QPager.ofPage(ip, res);
        qPager.setExtra(profit);
        return qPager;
    }

    /**
    * 订单 退款，更新 退款 金额
    * @params
    * @return
    */
    @Transactional
    public void refunded(OrderProfit profit, BigDecimal refund_price) {
        // 是否 能 更新退款 金额
        if (profit.getId() == null) throw new QLogicException("找不到退款利率数据");
        boolean can = profit.doRefunded(refund_price);
        if (!can) throw new QLogicException("退款总金额对大于订单总金额");

        // 更改 退款 金额
        LambdaUpdateWrapper<OrderProfit> uw = new LambdaUpdateWrapper<>();
        uw.eq(OrderProfit::getId, profit.getId());
        uw.set(OrderProfit::getRefund_price, profit.getRefund_price());

        this.update(uw);
    }

    /**
    * 同步 订单的 订单状态 到 利率表的 订单状态
    * @params
    * @return
    */
    public boolean asyncOrderStatus(Order order) {
        LambdaUpdateWrapper<OrderProfit> opuw = new LambdaUpdateWrapper<>();
        opuw.eq(OrderProfit::getOrder_id, order.getOrder_id());
        opuw.set(OrderProfit::getOrder_status, order.getOrder_status());
        return this.update(opuw);
    }
}
