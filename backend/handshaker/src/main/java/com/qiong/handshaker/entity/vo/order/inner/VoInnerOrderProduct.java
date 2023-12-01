package com.qiong.handshaker.entity.vo.order.inner;


import com.qiong.handshaker.entity.moduie.product.Product;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VoInnerOrderProduct {
    // 产品 ID
    private Long product;
    // 样式 ID
    private Long variation;
    // 购买 数量
    private Integer quantity;
    // 本產品 折扣
    private BigDecimal discount_deduction;

    // 退货 数量
    private Integer refunded_quantity;

    private Product productEntity;

    public Integer mustGetQuantity() { return this.quantity == null ? 0 : this.quantity; }
}
