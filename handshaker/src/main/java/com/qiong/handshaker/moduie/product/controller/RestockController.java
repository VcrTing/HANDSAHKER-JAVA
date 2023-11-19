package com.qiong.handshaker.moduie.product.controller;

import com.qiong.handshaker.anno.result.QResponseAdvice;
import com.qiong.handshaker.data.router.DataRouterProduct;
import com.qiong.handshaker.define.result.QResponse;
import com.qiong.handshaker.moduie.base.Supplier;
import com.qiong.handshaker.moduie.base.service.SupplierService;
import com.qiong.handshaker.moduie.product.Product;
import com.qiong.handshaker.moduie.product.RestockRecord;
import com.qiong.handshaker.moduie.product.service.ProductService;
import com.qiong.handshaker.moduie.product.service.RestockService;
import com.qiong.handshaker.tool.result.QResponseTool;
import com.qiong.handshaker.vo.product.VoRestockPostForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@QResponseAdvice
@RequestMapping(DataRouterProduct.RESTOCK)
public class RestockController {

    @Autowired
    RestockService service;

    @Autowired
    SupplierService supplierService;

    @Autowired
    ProductService productService;

    // 注意加事物回滚
    // 新增 入货
    @PostMapping
    public QResponse<Object> pos(@RequestBody VoRestockPostForm form) {
        // 获取 supplier
        Supplier supplier = form.hasSupplier() ? supplierService.getById(form.getSupplier()) : null;
        // 获取 数据库 里面的 产品
        Product product = productService.oneByProductID(form.getProduct());
        // 加货 普通 加货
        RestockRecord rr = service.restockNormal(form, supplier, product);
        // 加货成功，更新 产品的 最新价格
        product.modifyNewStock(product, rr, supplier);
        // 储存 产品 数据
        return QResponseTool.restfull(productService.modifyProduct(product), rr);
    }
}
