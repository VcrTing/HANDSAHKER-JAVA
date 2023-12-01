package com.qiong.handshaker.entity.view.order;

import cn.hutool.json.JSONUtil;
import com.qiong.handshaker.entity.moduie.base.Storehouse;
import com.qiong.handshaker.entity.moduie.custom.Customer;
import com.qiong.handshaker.entity.moduie.custom.MemberLevel;
import com.qiong.handshaker.entity.moduie.order.Order;
import com.qiong.handshaker.entity.moduie.order.inner.OrderProduct;
import com.qiong.handshaker.entity.moduie.sys.User;
import com.qiong.handshaker.entity.view.order.inner_order.ViewInnerDiscount;
import com.qiong.handshaker.entity.view.order.inner_payment.ViewInnerPaymentMethod;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ViewOrderDetailForm {

    private Long id;
    private String order_id;

    private Date order_date;

    // 收银员
    private User cashier;

    private Customer member;

    private MemberLevel member_level;

    private String status;

    // 统计金额
    private BigDecimal total_price;

    private Storehouse order_shop;

    //
    private List<OrderProduct> ordered_product;

    private List<ViewInnerDiscount> discount;

    private List<ViewInnerPaymentMethod> payment_method;


    public static ViewOrderDetailForm init(Order order, Storehouse storehouse, User cashier, Customer member, MemberLevel memberLevel) {
        ViewOrderDetailForm res = new ViewOrderDetailForm();

        res.setId(order.getId());
        res.setOrder_id(order.getOrder_id());
        res.setOrder_date(order.getOrder_date());
        res.setStatus(order.getOrder_status().getValue());

        res.setTotal_price(order.getTotal_price());

        cashier.doProtected();

        res.setOrder_shop( storehouse );

        res.setMember_level(memberLevel);
        res.setCashier(cashier);
        res.setMember(member);

        if (order.getOrdered_product() != null) {
            List<OrderProduct> ops = JSONUtil.toList(order.getOrdered_product(), OrderProduct.class);
            res.setOrdered_product(ops);
        }
        if (order.getPayment_method() != null) {
            List<ViewInnerPaymentMethod> pms = JSONUtil.toList(order.getPayment_method(), ViewInnerPaymentMethod.class);
            res.setPayment_method(pms);
        }
        if (order.getDiscount() != null) {
            List<ViewInnerDiscount> vds = JSONUtil.toList(order.getDiscount(), ViewInnerDiscount.class);
            res.setDiscount(vds);
        }

        return res;
    }
}
