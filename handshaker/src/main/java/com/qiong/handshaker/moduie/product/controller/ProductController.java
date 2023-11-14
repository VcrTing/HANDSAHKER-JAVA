package com.qiong.handshaker.moduie.product.controller;

import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qiong.handshaker.anno.result.QResponseAdvice;
import com.qiong.handshaker.data.router.DataRouterProduct;
import com.qiong.handshaker.define.dataset.EntityDefineDataset;
import com.qiong.handshaker.define.query.QLikes;
import com.qiong.handshaker.define.query.QPage;
import com.qiong.handshaker.define.query.QSort;
import com.qiong.handshaker.define.result.QPager;
import com.qiong.handshaker.define.result.QResponse;
import com.qiong.handshaker.moduie.base.Supplier;
import com.qiong.handshaker.moduie.base.service.SupplierService;
import com.qiong.handshaker.moduie.custom.Customer;
import com.qiong.handshaker.moduie.product.Product;
import com.qiong.handshaker.moduie.product.ProductAndLabel;
import com.qiong.handshaker.moduie.product.Variation;
import com.qiong.handshaker.moduie.product.VariationAndStorehouseAndProduct;
import com.qiong.handshaker.moduie.product.serive.ProductAndLabelService;
import com.qiong.handshaker.moduie.product.serive.ProductService;
import com.qiong.handshaker.moduie.product.serive.VariationAndStorehouseAndProductService;
import com.qiong.handshaker.tool.result.QResponseTool;
import com.qiong.handshaker.utils.basic.QTypedUtil;
import com.qiong.handshaker.view.product.ViewProductResultForm;
import com.qiong.handshaker.vo.product.VoProductPatchForm;
import com.qiong.handshaker.vo.product.VoProductPostForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;

@RestController
@QResponseAdvice
@RequestMapping(DataRouterProduct.PRODUCT)
public class ProductController {


    @Autowired
    ProductService service;

    @Autowired
    ProductAndLabelService productAndLabelService;

    @Autowired
    VariationAndStorehouseAndProductService variationAndStorehouseAndProductService;

    @GetMapping
    public QResponse<QPager<ViewProductResultForm>> page(@RequestParam HashMap<String, Object> qry) {

        System.out.println("查询 = " + qry);

        QueryWrapper<Product> qw = new QueryWrapper<>();
        qw.lambda().orderBy(QSort.hasSort(qry), QSort.ofMap(qry).isAsc(), Product::getId);

        QLikes likes = QLikes.ofMap(qry, new String[] { "search", "label", "supplier", "new_restock_date" });
        String search = likes.one("search");
        if (!search.isEmpty()) {
            qw.lambda().like(Product::getProduct_id, search).or().like(Product::getName, search).or();
        }

        // 标签，记得 拼接 ID数据 前缀
        if (likes.has("label")) qw.lambda().like(Product::getLabels, EntityDefineDataset.ID_JSON_PREFIX + likes.one("label")).or();
        if (likes.has("supplier")) qw.lambda().like(Product::getNew_supplier_sql_id, likes.one("supplier")).or();
        // 入货 时间
        if (likes.has("new_restock_date")) qw.lambda().gt(Product::getNew_restock_date, likes.one("new_restock_date")).or();

        return QResponseTool.restfull(true, service.pageDeep(new Page<Product>(QPage.easyCurrent(qry), QPage.easySize(qry)), qw));
    }

    @GetMapping("/{id}")
    public QResponse<ViewProductResultForm> one(@PathVariable Object id) {
        return id != null ? QResponseTool.restfull(true, service.oneDeep( id )) : QResponseTool.genBad("", null);
    }

    @GetMapping("/aii")
    public QResponse<List<Product>> aii() {
        return QResponseTool.genSuccess("查询成功", service.list());
    }

    // 注意加事物回滚
    @PostMapping
    public QResponse<Product> pos(@RequestBody @Validated VoProductPostForm form) {
        Product entity = Product.initPost(form);
        if (service.save(entity)) {
            /**
            * 我们改用 JSON List<String> 处理 多对多
            * @params
            * @return
            */
            // 为 产品 连接 Labels
            // productAndLabelService.afterPostProduct(entity.getId(), form.getLabels());
            // 为 产品 添加 默认的 样式，名字叫 origin
            variationAndStorehouseAndProductService.connectionVariationForProduct(entity.getId(), new Variation(EntityDefineDataset.VARIATION_NAME_DEF));
        }
        return QResponseTool.restfull(entity.getId() != null, entity);
    }

    @PatchMapping("/{id}")
    public QResponse<Product> upd(@PathVariable Long id, @RequestBody @Validated VoProductPatchForm form) {
        return QResponseTool.restfull(service.update(form.genUpdateWrapper(id)), service.getById(id));
    }

    @DeleteMapping("/{id}")
    public QResponse<Product> dei(@PathVariable Long id) {
        return QResponseTool.restfull(service.removeById(id), service.getById(id));
    }
}
