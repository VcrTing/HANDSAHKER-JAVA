package com.qiong.handshaker.vo.order.inner;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class VoInnerDiscount {
    private String type;
    // 優惠已扣除價格
    private BigDecimal discount_deduction;
}
