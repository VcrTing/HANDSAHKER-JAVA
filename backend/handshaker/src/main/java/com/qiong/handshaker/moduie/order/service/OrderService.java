package com.qiong.handshaker.moduie.order.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qiong.handshaker.entity.define.enums.EnumOrderStatus;
import com.qiong.handshaker.utils.define.exception.vaiid.QLogicException;
import com.qiong.handshaker.utils.define.result.QPager;
import com.qiong.handshaker.entity.moduie.base.Storehouse;
import com.qiong.handshaker.moduie.base.mapper.StorehouseMapper;
import com.qiong.handshaker.entity.moduie.custom.Customer;
import com.qiong.handshaker.entity.moduie.custom.MemberLevel;
import com.qiong.handshaker.moduie.custom.mapper.CustomerMapper;
import com.qiong.handshaker.moduie.custom.mapper.MemberLevelMapper;
import com.qiong.handshaker.entity.moduie.order.Order;
import com.qiong.handshaker.entity.moduie.order.inner.OrderProduct;
import com.qiong.handshaker.moduie.order.mapper.OrderMapper;
import com.qiong.handshaker.entity.moduie.product.Product;
import com.qiong.handshaker.entity.moduie.product.Variation;
import com.qiong.handshaker.moduie.product.mapper.ProductMapper;
import com.qiong.handshaker.moduie.product.mapper.VariationMapper;
import com.qiong.handshaker.entity.moduie.sys.User;
import com.qiong.handshaker.moduie.sys.mapper.UserMapper;
import com.qiong.handshaker.entity.view.order.ViewOrderDetailForm;
import com.qiong.handshaker.entity.view.order.ViewOrderResultForm;
import com.qiong.handshaker.entity.view.product.ViewProductResultForm;
import com.qiong.handshaker.entity.vo.order.refund.VoInnerRefundedInfo;
import com.qiong.handshaker.utils.utils.basic.QTypedUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    StorehouseMapper storehouseMapper;

    @Autowired
    MemberLevelMapper memberLevelMapper;

    @Autowired
    ProfitService profitService;

    /**
    * 深度 查询 列表
    * @params
    * @return
    */
    public QPager<ViewOrderResultForm> pageDeep(IPage<Order> ip, QueryWrapper<Order> qw) {
        ip.setRecords(mapper.pageList(ip, qw));
        return QPager.ofPage(ip, ViewOrderResultForm.initList(ip.getRecords()));
    }


    /**
    * 根据 STRING ID 或 LONG ID 查询 订单
    * @params
    * @return
    */
    public Order byOrderId(String orderID, boolean isSqlID) {
        LambdaUpdateWrapper<Order> qw = new LambdaUpdateWrapper<>();
        qw.eq(Order::getOrder_id, orderID);
        return isSqlID ? mapper.selectById(QTypedUtil.serLong(orderID)) : mapper.selectOne(qw);
    }

    /**
    * 深层 查询 订单 详情
    * @params
    * @return
    */
    public ViewOrderDetailForm detail(Object orderId) {
        ViewOrderDetailForm res;

        // 查询 订单数据
        Order order = byOrderId(orderId.toString(), QTypedUtil.isLong(orderId));

        // 查询 相应的 数据
        User user = userMapper.selectById(order.getCashier_sql_id());
        Storehouse storehouse = storehouseMapper.selectById(order.getStorehouse_sql_id());

        // 组装 结果
        // 如果 存在 购买 用户的 数据
        if (order.getMember_sql_id() != null) {
            Customer customer = customerMapper.selectById(order.getMember_sql_id());
            MemberLevel memberLevel = memberLevelMapper.selectById(order.getMember_level_sql_id());
            res = ViewOrderDetailForm.init(order, storehouse, user, customer, memberLevel);
        } else {
            res = ViewOrderDetailForm.init(order, storehouse, user, null, null);
        }

        // 将 OrderProduct 列表 返回为 深层的 OrderProduct 列表数据
        List<OrderProduct> ops = res.getOrdered_product();
        for (OrderProduct op: ops) {
            Product product = productMapper.selectById(op.getProduct_sql_id());
            op.setProduct(
                    ViewProductResultForm.init(product, null)
            );
            Variation variation = variationMapper.selectById(op.getVariation_sql_id());
            op.setVariation(variation);
        }
        res.setOrdered_product(ops);

        // 返回
        return res;
    }

    /**
    * 退货
    * @params
    * @return
    */
    @Transactional
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

        /**
        * 修改 订单 数据
        * @params
        * @return
        */
        LambdaUpdateWrapper<Order> uw = new LambdaUpdateWrapper<>();
        uw.eq(Order::getId, order.getId());

        // 更新 退款 数量
        uw.set(Order::getOrdered_product, order.getOrdered_product());

        // 更新 退款 备注
        uw.set(Order::getRefunded_remarks, remark);

        // 更新 订单 状态
        EnumOrderStatus status = EnumOrderStatus.partially_refunded;
        if (refundedNum.equals(aiiNum)) status = EnumOrderStatus.refunded;
        order.setOrder_status(status);

        // 率先更新 利率
        profitService.asyncOrderStatus(order);

        // 更改订单状态
        uw.set(Order::getOrder_status, status);
        return this.update(uw);
    }
}
