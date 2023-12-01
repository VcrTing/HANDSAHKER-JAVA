package com.qiong.handshaker.entity.view.product.inner;

import com.qiong.handshaker.entity.moduie.base.Storehouse;
import com.qiong.handshaker.entity.moduie.product.Variation;
import com.qiong.handshaker.entity.moduie.product.VariationAndStorehouseAndProduct;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ViewInnerVariation {
    private Long id;
    private String name;
    private Integer quantity;

    private Storehouse storehouse;

    public static ViewInnerVariation init(Variation v, Storehouse s, Integer quantity) {
        return new ViewInnerVariation(v.getId(), v.getName(), quantity, s);
    }

    public static ViewInnerVariation init(VariationAndStorehouseAndProduct vsp) {
        String variationName = (vsp.getVariation() != null) ? vsp.getVariation().getName() : null;
        return new ViewInnerVariation(vsp.getVariation_sql_id(), variationName, vsp.mustGetQuantity(), vsp.getStorehouse());
    }

    public static ViewInnerVariation init(VariationAndStorehouseAndProduct vsp, boolean noStorehouse) {
        String variationName = (vsp.getVariation() != null) ? vsp.getVariation().getName() : null;
        return new ViewInnerVariation(vsp.getVariation_sql_id(), variationName, vsp.mustGetQuantity(), noStorehouse ? null : vsp.getStorehouse());
    }

    public static ViewInnerVariation initEasy(VariationAndStorehouseAndProduct vsp) {
        String variationName = (vsp.getVariation() != null) ? vsp.getVariation().getName() : null;
        return new ViewInnerVariation(vsp.getVariation_sql_id(), variationName);
    }
    public ViewInnerVariation(Long id, String name) { this.id = id; this.name = name; }
}
