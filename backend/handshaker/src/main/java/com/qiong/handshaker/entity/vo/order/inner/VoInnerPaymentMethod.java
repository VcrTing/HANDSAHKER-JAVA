package com.qiong.handshaker.entity.vo.order.inner;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VoInnerPaymentMethod {
    private String name;
    private BigDecimal price;
}
