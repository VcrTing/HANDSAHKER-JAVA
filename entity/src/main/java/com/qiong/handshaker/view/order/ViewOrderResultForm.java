package com.qiong.handshaker.view.order;


import com.qiong.handshaker.moduie.base.Storehouse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.ibatis.javassist.compiler.ast.Member;

import java.math.BigDecimal;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ViewOrderResultForm {
    private String order_id;
    private Date order_date;
    private Member member;

    private String status;

    private BigDecimal total_price;

    private Storehouse order_shop;
}
