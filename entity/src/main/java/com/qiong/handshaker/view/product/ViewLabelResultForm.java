package com.qiong.handshaker.view.product;

import com.qiong.handshaker.moduie.product.Label;
import com.qiong.handshaker.moduie.product.Product;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ViewLabelResultForm implements Serializable {

    private Long id;
    private String name;
    private List<HashMap<String, Object>> products;

    public static ViewLabelResultForm init(Label label, List<Product> products) {

        ViewLabelResultForm res = new ViewLabelResultForm();

        if (label == null) return res;

        res.setId(label.getId());
        res.setName(label.getName());

        if (products != null) {
            List<HashMap<String, Object>> pss = new ArrayList<>();

            for (Product p: products) {
                HashMap<String, Object> one = new HashMap<>();
                one.put("id", p.getId());
                one.put("product_id", p.getProduct_id());
                one.put("name", p.getName());
                one.put("product_name", p.getName());

                pss.add(one);
            }
            res.setProducts(pss);
        }

        return res;
    }
}
