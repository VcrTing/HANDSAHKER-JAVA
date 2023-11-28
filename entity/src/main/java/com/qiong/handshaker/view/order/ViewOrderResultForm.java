package com.qiong.handshaker.view.order;


import com.qiong.handshaker.define.enums.EnumOrderStatus;
import com.qiong.handshaker.moduie.base.Storehouse;
import com.qiong.handshaker.moduie.custom.Customer;
import com.qiong.handshaker.moduie.order.Order;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.ibatis.javassist.compiler.ast.Member;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ViewOrderResultForm {
    private Long id;
    private String order_id;
    private Date order_date;
    private Customer member;

    private EnumOrderStatus status;

    private BigDecimal total_price;

    private Storehouse order_shop;

    public static List<ViewOrderResultForm> initList(List<Order> src) {
        if (src == null) return new ArrayList<>();
        return src.stream().map(o -> ViewOrderResultForm.init(o, o.getStorehouse())).collect(Collectors.toList());
    }

    public static ViewOrderResultForm init(Order order, Storehouse storehouse) {
        ViewOrderResultForm res = new ViewOrderResultForm();

        res.setOrder_shop(storehouse);

        res.setMember(order.getMember());

        res.setId(order.getId());
        res.setOrder_id(order.getOrder_id());
        res.setStatus(order.getOrder_status());
        res.setOrder_date(order.getOrder_date());
        res.setTotal_price(order.getTotal_price());

        return res;
    }
}
