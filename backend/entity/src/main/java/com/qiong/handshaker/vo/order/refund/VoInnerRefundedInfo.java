package com.qiong.handshaker.vo.order.refund;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VoInnerRefundedInfo {
    private Long product;

    private BigDecimal refunded_price;
    private Integer refunded_quantity;

    private Long variation;

    public Integer mustGetQuantity() { return Math.abs(this.refunded_quantity == null ? 0 : this.refunded_quantity); }
}
