package com.qiong.handshaker.entity.moduie.order;

import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.qiong.handshaker.entity.define.enums.EnumOrderStatus;
import com.qiong.handshaker.entity.moduie.BaseEntity;
import com.qiong.handshaker.entity.moduie.custom.Customer;
import com.qiong.handshaker.entity.moduie.custom.MemberLevel;
import com.qiong.handshaker.entity.moduie.order.inner.OrderProduct;
import com.qiong.handshaker.entity.vo.order.VoOrderPostForm;
import com.qiong.handshaker.entity.vo.order.inner.VoInnerPaymentMethod;
import com.qiong.handshaker.entity.moduie.base.Storehouse;
import com.qiong.handshaker.entity.moduie.sys.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@TableName("order_order")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Order extends BaseEntity {


    private String order_id;
    private Date order_date;
    private EnumOrderStatus order_status;

    private BigDecimal total_price;
    private Long profit_sql_id;

    // 聯表
    private Long storehouse_sql_id;
    private Long cashier_sql_id;
    private Long member_sql_id;
    private Long member_level_sql_id;

    @TableField(exist = false)
    private Storehouse storehouse;
    @TableField(exist = false)
    private User cashier;
    @TableField(exist = false)
    private Customer member;
    @TableField(exist = false)
    private MemberLevel member_level;


    // JSON 装 ARRAY
    private String discount;
    // JSON 装 ARRAY
    private String payment_method;

    // JSON 裝 產品 購買 信息
    private String ordered_product;
    // private List<OrderProduct> ordered_product;

    // 退貨 備註
    private String refunded_remarks;


    public void jsonSetOrdered_product(List<OrderProduct> src) { this.ordered_product = JSONUtil.toJsonStr(src); }
    public List<OrderProduct> jsonGetOrdered_product() { return JSONUtil.toList(this.ordered_product, OrderProduct.class); }

    public static Order init(VoOrderPostForm form, User user, Customer member, MemberLevel memberLevel, Storehouse storehouse) {
        Order res = new Order();
        res.setMember_sql_id(form.getMember());
        res.setOrder_status(form.getStatus());

        // 产品
        res.setDiscount(JSONUtil.toJsonStr(form.getDiscount()));
        res.setPayment_method(JSONUtil.toJsonStr(form.getPayment_method()));

        // 构建 其他
        res.setCashier_sql_id(user.getId());

        if (member != null) res.setMember_sql_id(member.getId());
        if (memberLevel != null) res.setMember_level_sql_id(memberLevel.getId());
        res.setStorehouse_sql_id(storehouse.getId());

        // 总 金额
        res.setTotal_price(computedTotal( form.getPayment_method() ));

        // 时间
        res.setOrder_date(new Date());
        // ID
        res.setOrder_id(genOrderID());

        return autoGenerate(res);
    }

    // 计算 订单 总金额
    public static BigDecimal computedTotal(List<VoInnerPaymentMethod> paymentMethods) {
        BigDecimal res = new BigDecimal(0);
        res.setScale(BigDecimal.ROUND_HALF_UP);

        for (VoInnerPaymentMethod method: paymentMethods) {
            if (method.getPrice() != null) {
                res = res.add(method.getPrice());
            }
        }

        return res;
    }

    // 生成 订单 ID
    public static String genOrderID() {
        int num = (int) (Math.random() * 100 + 1);
        String tim = new SimpleDateFormat("yyyyMMddHHmm").format(new Date());
        // int num2 = (int) (Math.random() * 100 + 1);
        return "ORDER_" + tim + num;
    }
}
