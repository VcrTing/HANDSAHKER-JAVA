package com.qiong.handshaker.entity.view.product.inner;

import com.qiong.handshaker.entity.moduie.base.Storehouse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ViewInnerRestockDistribute {

    private Integer quantity;

    private ViewInnerVariation variation;
    private Storehouse storehouse;

    public Integer mustGetQuantity() {
        return Math.abs(quantity != null ? quantity : 0);
    }
}
