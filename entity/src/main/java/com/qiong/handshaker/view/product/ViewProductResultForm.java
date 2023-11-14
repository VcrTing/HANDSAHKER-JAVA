package com.qiong.handshaker.view.product;


import cn.hutool.json.JSON;
import cn.hutool.json.JSONUtil;
import com.qiong.handshaker.define.dataset.EntityDefineDataset;
import com.qiong.handshaker.moduie.product.Label;
import com.qiong.handshaker.moduie.product.Product;
import com.qiong.handshaker.moduie.product.Variation;
import com.qiong.handshaker.moduie.product.inner.LabelsJson;
import com.qiong.handshaker.moduie.product.inner.ProductRemark;
import com.qiong.handshaker.view.product.inner.ViewInnerBrokenProduct;
import com.qiong.handshaker.view.product.inner.ViewInnerRestock;
import com.qiong.handshaker.view.product.inner.ViewInnerStorehouseInfo;
import com.qiong.handshaker.view.product.inner.ViewInnerVariation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.util.StringUtils;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ViewProductResultForm implements Serializable {

    private Long id;

    private String product_id;
    private Date create_date;
    private String name;

    private Date new_restock_date;

    private BigDecimal new_restock_price;
    private BigDecimal new_selling_price;
    private BigDecimal new_lowest_price;

    // 入货价 平均 价
    private BigDecimal average_restock_price;

    private String new_supplier;

    private List<Label> labels;

    private List<ViewInnerVariation> variations;

    private List<ProductRemark> remarks;

    // 本月坏货
    private BigDecimal total_broken_products;

    // 坏货详情
    private List<ViewInnerBrokenProduct> broken_products;

    // 库存详情
    private List<ViewInnerStorehouseInfo> storehouse_info;

    // 入貨紀錄
    private List<ViewInnerRestock> restock;


    public static ViewProductResultForm initEasy(Product product, List<Label> allLabels) {

        ViewProductResultForm res = new ViewProductResultForm();
        if (product == null) return res;

        res.setId(product.getId());
        res.setName(product.getName());
        res.setProduct_id(product.getProduct_id());
        res.setCreate_date(product.getCreate_date());

        // 序列化 备注
        if (product.getRemarks() != null) res.setRemarks(JSONUtil.toList(product.getRemarks(), ProductRemark.class));

        // 序列化 标签
        res.setLabels(LabelsJson.init(product).idsToEntity(allLabels));

        return res;
    }
}
