package com.qiong.handshaker.view.order;

import com.qiong.handshaker.moduie.custom.Customer;
import com.qiong.handshaker.moduie.order.OrderProfit;
import com.qiong.handshaker.moduie.sys.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ViewProfitResultForm {

    private Long id;

    private String order_id;
    private Date order_date;

    private Customer member;
    private User cashier;

    // 订单产品 的 总 金额
    private BigDecimal total_price;
    // 订单产品 的 总 毛利率 之和
    private BigDecimal total_profit;

    public static ViewProfitResultForm init(OrderProfit profit, User user, Customer customer) {
        ViewProfitResultForm res = new ViewProfitResultForm();

        user.doProtected();
        res.setCashier(user);
        res.setMember(customer);

        res.setId(profit.getId());

        res.setTotal_profit(profit.getNew_total_profit());
        res.setTotal_price(profit.getOrigin_total_price());

        res.setOrder_id(profit.getOrder_id());
        res.setOrder_date(profit.getOrder_date());

        return res;
    }
}
