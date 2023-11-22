package com.qiong.handshaker.moduie.product.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.qiong.handshaker.moduie.product.Product;
import com.qiong.handshaker.moduie.product.inner.LabelsJson;
import com.qiong.handshaker.moduie.product.mapper.ProductAndLabelMapper;
import com.qiong.handshaker.view.product.ViewLabelResultForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductAndLabelService {

    @Autowired
    LabelService labelService;

    @Autowired
    ProductService productService;

    /**
    * 搜寻 含 该 LABEL 的 产品，使用 LabelsJson
    * @params
    * @return
    */
    public List<Product> productsByLabel(Long lid) {
        LambdaQueryWrapper<Product> qw = new LambdaQueryWrapper<>();
        qw.like(Product::getLabels, LabelsJson.idToString(lid));
        return productService.list(qw);
    }

    /**
    * 查询一個 標籤 里的 所有 產品
    * @params
    * @return
    */
    public ViewLabelResultForm oneByLabel(Long lid) {
        return ViewLabelResultForm.init(labelService.getById(lid), productsByLabel(lid));
    }
}
