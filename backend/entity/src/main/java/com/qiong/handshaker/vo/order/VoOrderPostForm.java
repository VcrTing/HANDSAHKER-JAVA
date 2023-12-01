package com.qiong.handshaker.vo.order;

import com.qiong.handshaker.define.enums.EnumOrderStatus;
import com.qiong.handshaker.vo.order.inner.VoInnerDiscount;
import com.qiong.handshaker.vo.order.inner.VoInnerOrderProduct;
import com.qiong.handshaker.vo.order.inner.VoInnerPaymentMethod;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VoOrderPostForm {
    private Long member;

    private EnumOrderStatus status;

    private List<VoInnerOrderProduct> ordered_product;
    private List<VoInnerDiscount> discount;
    private List<VoInnerPaymentMethod> payment_method;
}
