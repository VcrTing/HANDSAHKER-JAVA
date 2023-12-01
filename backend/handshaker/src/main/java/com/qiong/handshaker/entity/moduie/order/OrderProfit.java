package com.qiong.handshaker.entity.moduie.order;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.qiong.handshaker.entity.define.enums.EnumOrderStatus;
import com.qiong.handshaker.entity.moduie.BaseEntity;
import com.qiong.handshaker.entity.moduie.custom.Customer;
import com.qiong.handshaker.entity.moduie.order.inner.OrderDiscount;
import com.qiong.handshaker.entity.moduie.order.inner.OrderProduct;
import com.qiong.handshaker.entity.moduie.sys.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

// 總計金額 = 單價 * 數量 - 優惠
// 毛利率 = (單價 - 平均價) * 數量 - 優惠
// 總計金額 = 以上所有產品的總計金額之和 - 在優惠類別裏的全單優惠之和
// 總毛利率 = 以上所有產品的總計毛利率之和 - 在優惠類別裏的全單優惠之和

@TableName("order_profit")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderProfit extends BaseEntity {

    private Long order_sql_id;
    private Long member_sql_id;
    private Long cashier_sql_id;

    @TableField(exist = false)
    private User cashier;
    @TableField(exist = false)
    private Customer member;

    private String order_id;
    private Date order_date;
    private EnumOrderStatus order_status;

    // 订单产品 的 总 金额
    private BigDecimal products_all_price;
    // 订单产品 的 总 毛利率 之和
    private BigDecimal products_all_profit;
    // 订单 的 全单 优惠 之和
    private BigDecimal order_all_discount;


    // 订单 最开始的 利率
    private BigDecimal origin_total_profit;
    // 订单 最开始的 金额
    private BigDecimal origin_total_price;

    // 订单 当前利率，会根据退款 进行 变动
    private BigDecimal new_total_profit;
    // 退款 金额
    private BigDecimal refund_price;

    // 执行 一个退款
    public boolean doRefunded(BigDecimal price) {
        BigDecimal limit = products_all_price.add(order_all_discount);
        BigDecimal fut = price;
        if (this.refund_price != null) { fut = refund_price.subtract(price); }
        // 退款 金额 大于 最初的 收款 金额

        if (limit.compareTo(fut) < 0) return false;
        refund_price = fut;
        return true;
    }

    // 计算 金额
    public BigDecimal computedPrice() {
        BigDecimal disc = order_all_discount == null ? new BigDecimal(0) : order_all_discount;
        BigDecimal pris = products_all_price == null ? new BigDecimal(0) : products_all_price;
        return pris.subtract(disc);
    }

    // 计算 毛利率
    public BigDecimal computedProfit() {
        BigDecimal disc = order_all_discount == null ? new BigDecimal(0) : order_all_discount;
        BigDecimal prof = products_all_profit == null ? new BigDecimal(0) : products_all_profit;
        return prof.subtract(disc);
    }

    public static OrderProfit init(Order order, List<OrderProduct> ordered_product, List<OrderDiscount> discounts) {

        OrderProfit res = new OrderProfit();

        res.setOrder_id(order.getOrder_id());
        res.setOrder_date(order.getOrder_date());
        res.setOrder_status(order.getOrder_status());

        res.setOrder_sql_id(order.getId());
        res.setCashier_sql_id(order.getCashier_sql_id());
        res.setMember_sql_id(order.getMember_sql_id());

        BigDecimal products_all_price = new BigDecimal(0);
        BigDecimal products_all_profit = new BigDecimal(0);

        // 总额，总毛利率
        for (OrderProduct op: ordered_product) {
            products_all_profit = products_all_profit.add(op.getProfit());
            products_all_price = products_all_price.add(op.getTotal_price());
        }
        res.setProducts_all_price(products_all_price);
        res.setProducts_all_profit(products_all_profit);

        // 总 优惠
        BigDecimal order_all_discount = new BigDecimal(0);
        for (OrderDiscount od: discounts) {
            order_all_discount = order_all_discount.add(od.mustGetDiscount());
        }
        res.setOrder_all_discount(order_all_discount);

        // 总 订单价格
        res.setOrigin_total_price(res.computedPrice());

        // 总 订单 毛利率
        res.setOrigin_total_profit(res.computedProfit());
        res.setNew_total_profit(res.getOrigin_total_profit());

        // 退款金额
        res.setRefund_price(new BigDecimal(0));

        return autoGenerate(res);
    }
}
