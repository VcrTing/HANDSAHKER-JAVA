package com.qiong.handshaker.entity.moduie.order;

import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.annotation.TableName;
import com.qiong.handshaker.entity.vo.order.VoRefundOperaForm;
import com.qiong.handshaker.entity.vo.order.refund.VoInnerRefundedInfo;
import com.qiong.handshaker.entity.moduie.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@TableName("order_refunded")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Refunded extends BaseEntity {

    private Long order_sql_id;
    private Long profit_sql_id;
    private Long storehouse_sql_id;

    private String refunded_remarks;

    // 退款金额、数量
    private BigDecimal refunded_price;
    private Integer refunded_quantity;

    // 退款详情
    private String refunded_info;

    //

    public static Refunded init(VoRefundOperaForm form, Long orderID, Long profitID) {
        Refunded res = new Refunded();

        res.setStorehouse_sql_id(form.getStorehouse());
        res.setOrder_sql_id(orderID);
        res.setProfit_sql_id(profitID);

        res.setRefunded_remarks(res.getRefunded_remarks());

        res.setRefunded_info(JSONUtil.toJsonStr(form.getRefunded_info()));

        Integer num = 0;
        BigDecimal prs = new BigDecimal(0);
        for (VoInnerRefundedInfo ri: form.getRefunded_info()) {
            num += ri.getRefunded_quantity();
            prs = prs.add(ri.getRefunded_price());
        }

        // 计算 总 价格，总 数量
        res.setRefunded_quantity(num);
        res.setRefunded_price(prs);

        return res;
    }
}
