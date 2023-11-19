package com.qiong.handshaker.moduie.order.inner;

import cn.hutool.json.JSONUtil;
import com.qiong.handshaker.vo.order.inner.VoInnerDiscount;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDiscount {
    private String type;
    // 優惠已扣除價格
    private BigDecimal discount_deduction;

    public BigDecimal mustGetDiscount() { return (discount_deduction != null) ? discount_deduction : new BigDecimal(0); }

    public static List<OrderDiscount> init(List<VoInnerDiscount> discounts) {
        List<OrderDiscount> res = new ArrayList<>();
        for (VoInnerDiscount vd: discounts) {
            res.add(JSONUtil.toBean(JSONUtil.toJsonStr(vd), OrderDiscount.class));
        }
        return res;
    }
}
