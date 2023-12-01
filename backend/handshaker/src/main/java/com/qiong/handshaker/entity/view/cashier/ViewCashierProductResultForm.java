package com.qiong.handshaker.entity.view.cashier;

import cn.hutool.json.JSONUtil;
import com.qiong.handshaker.entity.moduie.product.Product;
import com.qiong.handshaker.entity.moduie.product.VariationAndStorehouseAndProduct;
import com.qiong.handshaker.entity.view.product.inner.ViewInnerStorehouseInfo;
import com.qiong.handshaker.entity.view.product.inner.ViewInnerVariation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ViewCashierProductResultForm implements Serializable {

    private Long id;

    private String product_id;
    private String name;

    private Date new_restock_date;

    private BigDecimal new_restock_price;
    private BigDecimal new_selling_price;
    private BigDecimal new_lowest_price;

    // 入货价 平均 价
    private BigDecimal average_restock_price;

    private List<ViewInnerVariation> variations;

    // 库存详情
    private List<ViewInnerStorehouseInfo> storehouse_info;


    public static ViewCashierProductResultForm init(Product product, List<VariationAndStorehouseAndProduct> vsps) {
        ViewCashierProductResultForm res = JSONUtil.toBean(JSONUtil.toJsonStr(product), ViewCashierProductResultForm.class);
        if (vsps == null) return res;

        HashMap<Long, ViewInnerStorehouseInfo> map = new HashMap<>();
        HashMap<Long, ViewInnerVariation> vvmap = new HashMap<>();

        // 構建 Storehouse Info
        for (VariationAndStorehouseAndProduct vsp: vsps) {
            if (Objects.equals(vsp.getProduct_sql_id(), product.getId())) {

                Long __k = vsp.getStorehouse_sql_id();

                if (map.get(__k) == null) { map.put(__k, ViewInnerStorehouseInfo.init(vsp)); }
                // 没有则 加入 MAP
                ViewInnerStorehouseInfo vis = map.get(__k);
                if (vis != null) {
                    List<ViewInnerVariation> vs = vis.getVariation();
                    // 构建 ViewInnerVariation
                    vs.add( ViewInnerVariation.init(vsp, true) );
                    //
                    vis.setVariation(vs);
                    // 回头 MAP
                    map.put(__k, vis);
                }

                // 加入 VV MAP
                vvmap.put(vsp.getVariation_sql_id(), ViewInnerVariation.initEasy(vsp));
            }
        }

        // 构建 VIEW INNER STORE HOUSE INFO
        res.setStorehouse_info(new ArrayList<>(map.values()));
        // 构建 VARIATIONS
        res.setVariations(new ArrayList<>(vvmap.values()));

        return res;
    }
}
