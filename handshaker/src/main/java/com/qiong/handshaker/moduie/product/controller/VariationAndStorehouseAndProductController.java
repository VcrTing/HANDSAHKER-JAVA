package com.qiong.handshaker.moduie.product.controller;

import com.qiong.handshaker.anno.result.QResponseAdvice;
import com.qiong.handshaker.define.result.QResponse;
import com.qiong.handshaker.moduie.product.Product;
import com.qiong.handshaker.moduie.product.inner.LabelsJson;
import com.qiong.handshaker.moduie.product.serive.ProductService;
import com.qiong.handshaker.tool.result.QResponseTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@QResponseAdvice
public class VariationAndStorehouseAndProductController {

    @Autowired
    ProductService productService;

    /*
    // 具体 新加 删除 标签 的 操作
    public QResponse<Product> operaProductLabel(Long pid, Long lid, boolean isAdd) {
        Product product = productService.getById(pid);
        return QResponseTool.restfull((product != null), product);
    }

    @PatchMapping("/products_add_label/{pid}/{lid}")
    public QResponse<Product> addLabel(@PathVariable Long pid, @PathVariable Long lid) {
        return operaProductLabel(pid, lid, true);
    }

    @PatchMapping("/products_delete_label/{pid}/{lid}")
    public QResponse<Product> deiLabel(@PathVariable Long pid, @PathVariable Long lid) {
        return operaProductLabel(pid, lid, false);
    }
     */
}
