package com.qiong.handshaker.view.product.inner;


import cn.hutool.json.JSONUtil;
import com.qiong.handshaker.moduie.base.Storehouse;
import com.qiong.handshaker.moduie.base.Supplier;
import com.qiong.handshaker.moduie.product.RestockRecord;
import com.qiong.handshaker.moduie.product.Variation;
import com.qiong.handshaker.vo.product.inner.VoInnerRestockDistribute;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ViewInnerRestock {

    private Long id;
    private Date restock_date;
    private BigDecimal restock_price;
    private BigDecimal lowest_price;
    private BigDecimal selling_price;
    private Integer quantity;
    private Supplier supplier;
    private String supplier_name;

    List<VoInnerRestockDistribute> restock_distribute;

    public static ViewInnerRestock init(RestockRecord rr, List<ViewInnerVariation> variations, List<Storehouse> storehouses, List<Supplier> suppliers) {
        ViewInnerRestock res = new ViewInnerRestock();
        res.setRestock_date(rr.getRestock_date());
        res.setRestock_price(rr.getRestock_price());
        res.setSelling_price(rr.getSelling_price());
        res.setLowest_price(rr.getLowest_price());
        res.setId(rr.getId());

        Long sID = rr.getSupplier_sql_id();
        for (Supplier sp: suppliers) {
            if (Objects.equals(sp.getId(), sID)) {
                res.setSupplier(sp);
                res.setSupplier_name(sp.getName());
            }
        }

        Integer num = 0;
        List<VoInnerRestockDistribute> irds = JSONUtil.toList(rr.getRestock_distribute(), VoInnerRestockDistribute.class);
        /*
        List<ViewInnerRestockDistribute> vrds = new ArrayList<>();

        for (VoInnerRestockDistribute rd: irds) {
            num += rd.mustGetQuantity();

            ViewInnerRestockDistribute view = new ViewInnerRestockDistribute();
            view.setQuantity(rd.mustGetQuantity());

            // 插入 样式
            for (ViewInnerVariation v: variations) {
                if (Objects.equals(v.getId(), rd.getVariation())) {
                    view.setVariation(v);
                }
            }

            // 插入 库存
            for (Storehouse s: storehouses) {
                if (Objects.equals(s.getId(), rd.getStorehouse())) {
                    view.setStorehouse(s);
                }
            }

            vrds.add(view);
        }
         */
        res.setQuantity( num );

        res.setRestock_distribute(irds);
        return res;
    }

    public static List<ViewInnerRestock> fromRestockRecord(List<RestockRecord> rrs, List<ViewInnerVariation> variations, List<Storehouse> storehouses, List<Supplier> suppliers) {
        List<ViewInnerRestock> res = new ArrayList<>();
        if (rrs == null) return res;
        for (RestockRecord rr: rrs) {
            ViewInnerRestock one = ViewInnerRestock.init(rr, variations, storehouses, suppliers);
            res.add(one);
        }
        return res;
    }
}
