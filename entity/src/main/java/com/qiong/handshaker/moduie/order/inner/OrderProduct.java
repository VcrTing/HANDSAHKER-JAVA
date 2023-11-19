package com.qiong.handshaker.moduie.order.inner;

import com.qiong.handshaker.moduie.product.Product;
import com.qiong.handshaker.moduie.product.Variation;
import com.qiong.handshaker.vo.order.inner.VoInnerOrderProduct;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Objects;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderProduct {
    private Long product_sql_id;
    private Product product;

    private Long variation_sql_id;
    private Variation variation;

    // 平均价
    private BigDecimal average_price;
    // 單價
    private BigDecimal selling_price;
    // 购买 数量
    private Integer quantity;


    // 记录 单品 優惠 已扣除 價格
    private BigDecimal discount_deduction;

    // 单个产品的 总毛利率
    private BigDecimal profit;

    // 单个产品的 總金額
    private BigDecimal total_price;

    // 已 退貨 數目
    private Integer refunded_quantity;

    // 增加 退款数量
    public Boolean addRefundedQuantity(Integer num) {
        if (refunded_quantity == null) { refunded_quantity = 0; }
        if (quantity < (num + refunded_quantity)) return false;
        refunded_quantity += num; return true;
    }

    // 是否 已经 满了 退款 数量
    public Boolean isFullRefundedQuantity() {
        return Objects.equals(refunded_quantity, quantity);
    }


    // 计算 OrderProduct 的 total_price
    public BigDecimal computedTotalPrice(BigDecimal selling_price, Integer quantity, BigDecimal discount) {
        selling_price.setScale(BigDecimal.ROUND_HALF_UP);
        return selling_price.multiply(new BigDecimal(quantity)).subtract(discount);
    }
    // 计算 OrderProduct 的 profit
    public BigDecimal computedProfit(BigDecimal selling_price, BigDecimal average_price, Integer quantity, BigDecimal discount) {
        selling_price.setScale(BigDecimal.ROUND_HALF_UP);
        return (selling_price.subtract(average_price)).multiply(new BigDecimal(quantity)).subtract(discount);
    }

    public static OrderProduct init(VoInnerOrderProduct op, Product product) {

        OrderProduct res = new OrderProduct();

        res.setProduct_sql_id(product.getId());
        res.setVariation_sql_id(op.getVariation());

        res.setQuantity(op.mustGetQuantity());

        res.setSelling_price(product.getNew_selling_price());
        res.setAverage_price(product.getNew_restock_price());
        res.setDiscount_deduction(op.getDiscount_deduction());

        // 总价
        res.setTotal_price(
                res.computedTotalPrice(product.getNew_selling_price(), op.mustGetQuantity(), op.getDiscount_deduction()));
        // 毛利率
        res.setProfit(
                res.computedProfit(product.getNew_selling_price(), product.getNew_restock_price(), op.mustGetQuantity(), op.getDiscount_deduction()));

        return res;
    }
}
