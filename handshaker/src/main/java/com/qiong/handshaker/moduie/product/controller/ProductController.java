package com.qiong.handshaker.moduie.product.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qiong.handshaker.anno.result.QResponseAdvice;
import com.qiong.handshaker.data.router.DataRouterProduct;
import com.qiong.handshaker.data.security.DataSecurityRoleConf;
import com.qiong.handshaker.define.dataset.EntityDefineDataset;
import com.qiong.handshaker.define.query.QLikes;
import com.qiong.handshaker.define.query.QPage;
import com.qiong.handshaker.define.query.QSort;
import com.qiong.handshaker.define.result.QPager;
import com.qiong.handshaker.define.result.QResponse;
import com.qiong.handshaker.moduie.product.Product;
import com.qiong.handshaker.moduie.product.Variation;
import com.qiong.handshaker.moduie.product.service.ProductAndLabelService;
import com.qiong.handshaker.moduie.product.service.ProductService;
import com.qiong.handshaker.moduie.product.service.VariationAndStorehouseAndProductService;
import com.qiong.handshaker.moduie.sys.auth.AuthUser;
import com.qiong.handshaker.tool.result.QResponseTool;
import com.qiong.handshaker.tool.security.QSecurityMvcTool;
import com.qiong.handshaker.tool.security.QSecurityTool;
import com.qiong.handshaker.view.product.ViewProductResultForm;
import com.qiong.handshaker.vo.product.VoProductPatchForm;
import com.qiong.handshaker.vo.product.VoProductPostForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

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

    @Autowired
    QSecurityMvcTool securityMvcTool;

    /**
    * 深层 分页 列表，查询 深层产品 数据
    * @params
    * @return
    */
    @PreAuthorize(DataSecurityRoleConf.AUTH_ADMIN_ONLY)
    @GetMapping
    public QResponse<QPager<ViewProductResultForm>> page(@RequestParam HashMap<String, Object> qry) {

        QueryWrapper<Product> qw = new QueryWrapper<>();
        qw.lambda().orderBy(QSort.hasSort(qry), QSort.ofMap(qry).isAsc(), Product::getId);

        QLikes likes = QLikes.ofMap(qry, new String[] { "search", "label", "supplier", "new_restock_date" });
        // 根据 search
        String search = likes.one("search");
        if (!search.isEmpty()) {
            qw.lambda().like(Product::getProduct_id, search).or().like(Product::getName, search).or();
        }

        // 根据 某标签，记得 拼接 ID数据 前缀
        if (likes.has("label")) qw.lambda().like(Product::getLabels, EntityDefineDataset.ID_JSON_PREFIX + likes.one("label")).or();
        // 根据 某 供应商
        if (likes.has("supplier")) qw.lambda().like(Product::getNew_supplier_sql_id, likes.one("supplier")).or();
        // 根据 入货 时间 区间
        if (likes.has("new_restock_date")) qw.lambda().gt(Product::getNew_restock_date, likes.one("new_restock_date")).or();

        return QResponseTool.restfull(true, service.pageDeep(new Page<Product>(QPage.easyCurrent(qry), QPage.easySize(qry)), qw));
    }

    /**
    * 深度 查询 一个 产品
    * @params
    * @return
    */
    @PreAuthorize(DataSecurityRoleConf.AUTH_CASHIER)
    @GetMapping("/{id}")
    public QResponse<ViewProductResultForm> one(@PathVariable Object id) {
        return id != null ? QResponseTool.restfull(true, service.oneDeep( id )) : QResponseTool.genBad("", null);
    }

    /**
    * 新增产品，新增完，需要给 产品 加一个 默认样式，名字固定
    * @params
    * @return
    */
    @PreAuthorize(DataSecurityRoleConf.AUTH_ADMIN_ONLY)
    @PostMapping
    @Transactional
    public QResponse<Product> pos(@RequestBody @Validated VoProductPostForm form) {
        Product entity = Product.initPost(form);
        if (service.save(entity)) {
            // 为 产品 添加 默认的 样式，名字在 EntityDefineDataset.VARIATION_NAME_DEF 里
            variationAndStorehouseAndProductService.connectionVariationForProduct(entity.getId(), new Variation(EntityDefineDataset.VARIATION_NAME_DEF));
        }
        return QResponseTool.restfull(entity.getId() != null, entity);
    }

    /**
    * 修改 产品，修改的 字段 在 VoProductPatchForm.genUpdateWrapper 里决定
    * @params
    * @return
    */
    @PreAuthorize(DataSecurityRoleConf.AUTH_ADMIN_ONLY)
    @PatchMapping("/{id}")
    @Transactional
    public QResponse<Product> upd(@PathVariable Long id, @RequestBody @Validated VoProductPatchForm form) {
        return QResponseTool.restfull(service.update(form.genUpdateWrapper(id)), service.getById(id));
    }

    /**
    * 删除 产品
    * @params
    * @return
    */
    @PreAuthorize(DataSecurityRoleConf.AUTH_ADMIN_ONLY)
    @DeleteMapping("/{id}")
    @Transactional
    public QResponse<Product> dei(@PathVariable Long id) {
        return QResponseTool.restfull(service.removeById(id), service.getById(id));
    }
}
