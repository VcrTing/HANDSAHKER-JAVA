package com.qiong.handshaker.moduie.cashier.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qiong.handshaker.anno.result.QResponseAdvice;
import com.qiong.handshaker.data.router.DataRouterCashier;
import com.qiong.handshaker.define.dataset.EntityDefineDataset;
import com.qiong.handshaker.define.query.QLikes;
import com.qiong.handshaker.define.query.QPage;
import com.qiong.handshaker.define.query.QSort;
import com.qiong.handshaker.define.result.QPager;
import com.qiong.handshaker.define.result.QResponse;
import com.qiong.handshaker.moduie.cashier.service.CashierProductService;
import com.qiong.handshaker.moduie.product.Product;
import com.qiong.handshaker.moduie.product.service.ProductService;
import com.qiong.handshaker.tool.result.QResponseTool;
import com.qiong.handshaker.view.cashier.ViewCashierProductResultForm;
import com.qiong.handshaker.view.product.ViewProductResultForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
@QResponseAdvice
@RequestMapping(DataRouterCashier.PRODUCT)
public class CashierProductController {

    @Autowired
    CashierProductService service;

    @GetMapping
    public QResponse<QPager<ViewCashierProductResultForm>> page(@RequestParam HashMap<String, Object> qry) {

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

        return QResponseTool.restfull(true, service.pageCashier(new Page<Product>(QPage.easyCurrent(qry), QPage.easySize(qry)), qw));
    }

}
