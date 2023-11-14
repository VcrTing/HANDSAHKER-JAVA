package com.qiong.handshaker.moduie.product;

import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.qiong.handshaker.define.dataset.EntityDefineDataset;
import com.qiong.handshaker.moduie.BaseEntity;
import com.qiong.handshaker.moduie.base.Supplier;
import com.qiong.handshaker.moduie.product.inner.LabelsJson;
import com.qiong.handshaker.moduie.product.inner.ProductRemark;
import com.qiong.handshaker.vo.product.VoProductPatchForm;
import com.qiong.handshaker.vo.product.VoProductPostForm;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;


@TableName("prod_product")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product extends BaseEntity {

    private String product_id;
    private Date create_date;
    private String name;

    private String remarks;

    // 入貨 時 更新 為 最新的數據
    private Date new_restock_date;
    private BigDecimal new_restock_price;
    private BigDecimal new_selling_price;
    private BigDecimal new_lowest_price;

    // 最新的 供應商 是:
    private Long new_supplier_sql_id;
    private String new_supplier;

    // 标签 JSON 数据
    private String labels;

    public static Product initPost(VoProductPostForm postForm) {
        Product res = new Product();

        res.setProduct_id(postForm.getProduct_id());
        res.setName(postForm.getName());
        res.setCreate_date(postForm.getCreate_date());

        // Remark 转 JSON
        if (postForm.getRemarks() != null) res.setRemarks(JSONUtil.toJsonStr(postForm.getRemarks()));

        // 变化 labels ID 为 String 且 转 JSON
        res.setLabels(LabelsJson.init(postForm.getLabels()).getJsonStr());

        return autoGenerate( res );
    }

    public static Product changePatch(VoProductPatchForm form, Product product) {
        product.setProduct_id(form.getProduct_id());
        product.setName(form.getName());
        product.setCreate_date(form.getCreate_date());
        return product;
    }
}
