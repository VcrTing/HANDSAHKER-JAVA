package com.qiong.handshaker.entity.view.order.inner_payment;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ViewInnerPaymentMethod {
    private String name;
    private BigDecimal price;
}
