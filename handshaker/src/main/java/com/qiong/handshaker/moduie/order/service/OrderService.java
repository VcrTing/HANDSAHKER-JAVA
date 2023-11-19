package com.qiong.handshaker.moduie.order.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qiong.handshaker.define.enums.EnumOrderStatus;
import com.qiong.handshaker.define.exception.vaiid.QLogicException;
import com.qiong.handshaker.define.result.QPager;
import com.qiong.handshaker.moduie.custom.Customer;
import com.qiong.handshaker.moduie.custom.MemberLevel;
import com.qiong.handshaker.moduie.custom.mapper.CustomerMapper;
import com.qiong.handshaker.moduie.custom.mapper.MemberLevelMapper;
import com.qiong.handshaker.moduie.custom.service.CustomerService;
import com.qiong.handshaker.moduie.order.Order;
import com.qiong.handshaker.moduie.order.OrderProfit;
import com.qiong.handshaker.moduie.order.inner.OrderProduct;
import com.qiong.handshaker.moduie.order.mapper.OrderMapper;
import com.qiong.handshaker.moduie.product.Product;
import com.qiong.handshaker.moduie.product.Variation;
import com.qiong.handshaker.moduie.product.mapper.ProductMapper;
import com.qiong.handshaker.moduie.product.mapper.VariationMapper;
import com.qiong.handshaker.moduie.product.service.VariationService;
import com.qiong.handshaker.moduie.sys.User;
import com.qiong.handshaker.moduie.sys.mapper.UserMapper;
import com.qiong.handshaker.view.order.ViewOrderDetailForm;
import com.qiong.handshaker.view.order.ViewOrderResultForm;
import com.qiong.handshaker.view.product.ViewProductResultForm;
import com.qiong.handshaker.vo.order.refund.VoInnerRefundedInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service // extends ServiceImpl<OrderMapper, >
public class OrderService extends ServiceImpl<OrderMapper, Order> {


    @Autowired
    OrderMapper mapper;

    @Autowired
    UserMapper userMapper;

    @Autowired
    ProductMapper productMapper;

    @Autowired
    VariationMapper variationMapper;

    @Autowired
    CustomerMapper customerMapper;

    @Autowired
    MemberLevelMapper memberLevelMapper;

    public QPager<ViewOrderResultForm> pageList(IPage<Order> ip, QueryWrapper<Order> qw) {

        List<ViewOrderResultForm> res = new ArrayList<>();

        ip.setRecords(mapper.pageList(ip, qw));

        if (ip.getRecords() != null) {
            for(Order o: ip.getRecords()) {
                System.out.println(o);
                res.add(ViewOrderResultForm.init(o, o.getStorehouse()));
            }
        }
        return QPager.ofPage(ip, res);
    }

    public ViewOrderDetailForm detail(Long orderId) {
        ViewOrderDetailForm res;

        Order order = this.getById(orderId);

        User user = userMapper.selectById(order.getCashier_sql_id());

        if (order.getMember_sql_id() != null) {
            Customer customer = customerMapper.selectById(order.getMember_sql_id());
            MemberLevel memberLevel = memberLevelMapper.selectById(order.getMember_level_sql_id());
            res = ViewOrderDetailForm.init(order, user, customer, memberLevel);
        }
        res = ViewOrderDetailForm.init(order, user, null, null);

        List<OrderProduct> ops = res.getOrdered_product();

        for (OrderProduct op: ops) {
            Product product = productMapper.selectById(op.getProduct_sql_id());
            op.setProduct(product.simpleResult());
            Variation variation = variationMapper.selectById(op.getVariation_sql_id());
            op.setVariation(variation);
        }

        res.setOrdered_product(ops);

        return res;
    }

    /**
    * 退货
    * @params
    * @return
    */
    // 更新 退款
    public Boolean refundedQuantity(Order order, List<VoInnerRefundedInfo> iris, String remark) {
        List<OrderProduct> ops = order.jsonGetOrdered_product();

        // 订单 状态 不为 取消
        if (order.getOrder_status() == EnumOrderStatus.canceled) throw new QLogicException("退款订单的状态为已取消");

        if (ops == null) throw new QLogicException("退款订单的购物数据为空");

        Integer aiiNum = 0;
        Integer refundedNum = 0;

        // 更新 退款 数量
        for (OrderProduct op: ops) {
            for (VoInnerRefundedInfo ir: iris) {
                if (op.getProduct_sql_id().equals(ir.getProduct())) {

                    boolean can = op.addRefundedQuantity(ir.mustGetQuantity());
                    if (!can) throw new QLogicException("单件商品的退款总数量大于购买的总数量");

                    aiiNum += op.getQuantity();

                    // 退款 数量 是否 等于 购买 数量
                    if (op.isFullRefundedQuantity()) {
                        refundedNum += op.getQuantity();
                    } else {
                        refundedNum += ir.mustGetQuantity();
                    }
                }
            }
        }

        order.jsonSetOrdered_product(ops);

        LambdaUpdateWrapper<Order> uw = new LambdaUpdateWrapper<>();
        uw.eq(Order::getId, order.getId());

        // 更新 退款 数量
        uw.set(Order::getOrdered_product, order.getOrdered_product());

        // 更新 退款 备注
        uw.set(Order::getRefunded_remarks, remark);

        // 更新 订单 状态
        uw.set(Order::getOrder_status, EnumOrderStatus.partially_refunded);
        if (refundedNum.equals(aiiNum)) {
            uw.set(Order::getOrder_status, EnumOrderStatus.refunded);
        }

        return this.update(uw);
    }
}
