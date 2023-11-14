package com.qiong.handshaker.view.product.inner;

import com.qiong.handshaker.moduie.base.Storehouse;
import com.qiong.handshaker.moduie.product.Variation;
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
}
