package com.qiong.handshaker.moduie.product.controller;

import com.qiong.handshaker.anno.result.QResponseAdvice;
import com.qiong.handshaker.data.router.DataRouterProduct;
import com.qiong.handshaker.data.security.DataSecurityRoleConf;
import com.qiong.handshaker.define.exception.vaiid.QLogicException;
import com.qiong.handshaker.define.result.QResponse;
import com.qiong.handshaker.moduie.base.Supplier;
import com.qiong.handshaker.moduie.base.service.SupplierService;
import com.qiong.handshaker.moduie.product.Product;
import com.qiong.handshaker.moduie.product.RestockRecord;
import com.qiong.handshaker.moduie.product.VariationAndStorehouseAndProduct;
import com.qiong.handshaker.moduie.product.service.ProductService;
import com.qiong.handshaker.moduie.product.service.RestockService;
import com.qiong.handshaker.moduie.product.service.VariationAndStorehouseAndProductService;
import com.qiong.handshaker.tool.result.QResponseTool;
import com.qiong.handshaker.vo.product.VoRestockPostForm;
import com.qiong.handshaker.vo.product.VoTransferStockForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@QResponseAdvice
public class RestockController {

    @Autowired
    RestockService service;

    @Autowired
    SupplierService supplierService;

    @Autowired
    ProductService productService;

    @Autowired
    VariationAndStorehouseAndProductService variationAndStorehouseAndProductService;

    /**
     * 產品 入貨
     * @params
     * @return
     */
    @PreAuthorize(DataSecurityRoleConf.AUTH_ADMIN_ONLY)
    @PostMapping(DataRouterProduct.RESTOCK)
    @Transactional
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
        return QResponseTool.restfull(productService.updateProduct(product), rr);
    }

    /**
    * 調貨
    * @params
    * @return
    */
    @PreAuthorize(DataSecurityRoleConf.AUTH_ADMIN_ONLY)
    @PatchMapping(DataRouterProduct.TRANSFER + "/{id}")
    @Transactional
    public QResponse<Object> transfer(@PathVariable Long id, @RequestBody VoTransferStockForm form) {

        VariationAndStorehouseAndProduct vspFrom = variationAndStorehouseAndProductService.one(id, form.getVariation(), form.getStorehouse_from());
        if (vspFrom == null) throw new QLogicException("減貨倉庫 库存数据 为空");
        VariationAndStorehouseAndProduct vspTo = variationAndStorehouseAndProductService.one(id, form.getVariation(), form.getStorehouse_to());
        if (vspTo == null) throw new QLogicException("加貨倉庫 库存数据 为空");

        // 减货 FROM
        variationAndStorehouseAndProductService.removeQuantity(
                vspFrom.getProduct_sql_id(), vspFrom.getVariation_sql_id(), vspFrom.getStorehouse_sql_id(), form.getQuantity());
        // 加货 TO
        variationAndStorehouseAndProductService.insertQuantity(
                vspTo.getProduct_sql_id(), vspTo.getVariation_sql_id(), vspTo.getStorehouse_sql_id(), form.getQuantity());

        return QResponseTool.restfull(true, form);
    }
}
