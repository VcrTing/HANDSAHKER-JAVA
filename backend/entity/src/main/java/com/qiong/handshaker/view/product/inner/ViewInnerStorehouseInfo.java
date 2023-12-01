package com.qiong.handshaker.view.product.inner;

import com.qiong.handshaker.moduie.base.Storehouse;
import com.qiong.handshaker.moduie.product.Variation;
import com.qiong.handshaker.moduie.product.VariationAndStorehouseAndProduct;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

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
            res.setStorehouse_id(storehouse.getId());
            res.setPhone_no(storehouse.getPhone_no());
            res.setStorehouse_name(storehouse.getName());
            res.setStorehouse_address(storehouse.getAddress());
        }

        res.variation = new ArrayList<>();

        return res;
    }

    public static List<ViewInnerStorehouseInfo> ofVsps(List<VariationAndStorehouseAndProduct> vsps) {
        List<ViewInnerStorehouseInfo> res = new ArrayList<>();
        if (vsps == null) return res;

        HashMap<Long, ViewInnerStorehouseInfo> mapStore = new HashMap<>();
        for (VariationAndStorehouseAndProduct vsp: vsps) {
            mapStore.put(vsp.getStorehouse_sql_id(), ViewInnerStorehouseInfo.init(vsp));
        }
        res = new ArrayList<>(mapStore.values());

        // 加入 库存 样式
        for (ViewInnerStorehouseInfo vsi: res) {

            List<ViewInnerVariation> vivs = new ArrayList<>();
            for (VariationAndStorehouseAndProduct __v: vsps) {
                if (Objects.equals(__v.getStorehouse_sql_id(), vsi.getStorehouse_id())) {
                    vivs.add(ViewInnerVariation.init(__v));
                }
            }
            vsi.setVariation(vivs);
        }

        return res;
    }
}
