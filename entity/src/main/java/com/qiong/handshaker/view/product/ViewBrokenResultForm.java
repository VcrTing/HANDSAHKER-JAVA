package com.qiong.handshaker.view.product;

import com.qiong.handshaker.moduie.base.Storehouse;
import com.qiong.handshaker.moduie.product.Broken;
import com.qiong.handshaker.moduie.product.Product;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ViewBrokenResultForm {
    private Long id;

    private Date date;
    private String remarks;
    private Integer quantity;

    private String product_id;
    private String product_name;
    private String storehouse_name;

    private Product product;
    private Storehouse storehouse;

    public static List<ViewBrokenResultForm> initList(List<Broken> src) {
        return src.stream().map(ViewBrokenResultForm::init).collect(Collectors.toList());
    }

    public static ViewBrokenResultForm init(Broken broken) {

        Product p = broken.getProduct();
        Storehouse s = broken.getStorehouse();

        ViewBrokenResultForm res = new ViewBrokenResultForm();
        res.setDate(broken.getDate());
        res.setRemarks(broken.getRemarks());
        res.setQuantity(broken.getQuantity());
        res.setStorehouse(s);
        res.setProduct(p);
        res.setId(broken.getId());

        if (p != null) {
            res.setProduct_id(p.getProduct_id());
            res.setProduct_name(p.getName());
        }
        if (s != null) {
            res.setStorehouse_name(s.getName());
        }


        return res;
    }
}
