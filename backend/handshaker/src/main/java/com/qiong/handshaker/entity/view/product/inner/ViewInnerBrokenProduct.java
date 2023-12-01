package com.qiong.handshaker.entity.view.product.inner;

import com.qiong.handshaker.entity.moduie.base.Storehouse;
import com.qiong.handshaker.entity.moduie.product.Broken;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ViewInnerBrokenProduct {
    private Integer quantity;
    private Date date;
    private String remarks;

    private Object variation;
    private Storehouse storehouse;

    public static ViewInnerBrokenProduct init(Broken broken) {
        ViewInnerBrokenProduct res = new ViewInnerBrokenProduct();
        res.setQuantity(broken.getQuantity());
        res.setRemarks(broken.getRemarks());
        res.setDate(broken.getDate());

        res.setVariation(broken.getVariation());
        res.setStorehouse(broken.getStorehouse());

        return res;
    }

    public static List<ViewInnerBrokenProduct> ofBroken(List<Broken> brokens) {
        List<ViewInnerBrokenProduct> res = new ArrayList<>();
        if (brokens == null) return res;
        for (Broken bk: brokens) { res.add(ViewInnerBrokenProduct.init(bk)); }
        return res;
    }
}
