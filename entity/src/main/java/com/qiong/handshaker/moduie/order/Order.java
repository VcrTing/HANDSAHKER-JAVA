package com.qiong.handshaker.moduie.order;

import com.baomidou.mybatisplus.annotation.TableName;
import com.qiong.handshaker.moduie.BaseEntity;
import com.qiong.handshaker.moduie.custom.MemberLevel;
import com.qiong.handshaker.moduie.sys.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.ibatis.javassist.compiler.ast.Member;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@TableName("order_order")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Order extends BaseEntity {

    private String order_id;
    private Date order_date;
    private String order_status;
    private BigDecimal total_price;
    private String refunded_remarks;

    private Long cashier_sql_id;
    private User cashier;

    private Long member_sql_id;
    private Member member;

    private Long member_level_sql_id;
    private MemberLevel member_level;

    // JSON 装 ARRAY
    private String discount;
    // JSON 装 ARRAY
    private String payment_method;

    //
    private List<Object> ordered_product;
}
