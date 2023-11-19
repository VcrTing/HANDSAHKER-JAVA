package com.qiong.handshaker.view.product.inner;

import com.qiong.handshaker.moduie.base.Storehouse;
import com.qiong.handshaker.moduie.product.Variation;
import com.qiong.handshaker.moduie.product.VariationAndStorehouseAndProduct;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ViewInnerStorehouseInfo implements Serializable {
    private Long storehouse_id;
    private String storehouse_name;
    private String phone_no;
    private String storehouse_address;

    private List<ViewInnerVariation> variation;

    public static ViewInnerStorehouseInfo init(VariationAndStorehouseAndProduct vsp) {
        ViewInnerStorehouseInfo res = new ViewInnerStorehouseInfo();

        Storehouse storehouse = vsp.getStorehouse();

        res.setStorehouse_id(vsp.getStorehouse_sql_id());

        if (storehouse != null) {
            res.setStorehouse_name(storehouse.getName());
            res.setStorehouse_address(storehouse.getAddress());
            res.setPhone_no(storehouse.getPhone_no());
        }

        res.variation = new ArrayList<>();

        return res;
    }
}
