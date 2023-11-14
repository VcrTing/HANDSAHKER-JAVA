package com.qiong.handshaker.view.order;

import com.qiong.handshaker.moduie.custom.MemberLevel;
import com.qiong.handshaker.moduie.product.Product;
import com.qiong.handshaker.moduie.sys.User;
import com.qiong.handshaker.view.order.inner_order.ViewInnerDiscount;
import com.qiong.handshaker.view.order.inner_payment.ViewInnerPaymentMethod;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.ibatis.javassist.compiler.ast.Member;

import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ViewOrderDetailForm {

    private String order_id;

    // 收银员
    private User cashier;

    private Member member;

    private MemberLevel member_level;

    private String status;

    // 统计金额
    private BigDecimal total_price;

    //
    private List<Product> ordered_product;

    private List<ViewInnerDiscount> discount;

    private List<ViewInnerPaymentMethod> payment_method;
}
