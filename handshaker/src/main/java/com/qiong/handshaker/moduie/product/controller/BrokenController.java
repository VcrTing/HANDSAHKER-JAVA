package com.qiong.handshaker.moduie.product.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qiong.handshaker.anno.result.QResponseAdvice;
import com.qiong.handshaker.define.query.*;
import com.qiong.handshaker.define.result.QResponse;
import com.qiong.handshaker.moduie.product.Broken;
import com.qiong.handshaker.moduie.product.serive.BrokenService;
import com.qiong.handshaker.tool.result.QResponseTool;
import com.qiong.handshaker.view.product.ViewBrokenResultForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
@QResponseAdvice
@RequestMapping("/broken-products")
public class BrokenController {

    @Autowired
    private BrokenService service;

    @GetMapping
    public QResponse<IPage<ViewBrokenResultForm>> page(@RequestParam HashMap<String, Object> qry) {

        QueryWrapper<Broken> qw = new QueryWrapper<>();
        QSort qs = QSort.ofMap(qry);
        qw.orderBy(QSort.hasSort(qry), qs.isAsc(), "me.id");

        // 模糊 搜寻
        /*
        QLikes likes = QLikes.ofMap(qry, new String[] { "search" });
        if (likes.has("search")) {
            qw.like(Supplier::getEmail, likes.one("search")).or();
            qw.like(Supplier::getPhone_no, likes.one("search")).or();
            qw.like(Supplier::getSupplier_id, likes.one("search")).or();
            qw.like(Supplier::getContact_person, likes.one("search")).or();
        }
        */
        QDate qd = QDate.ofMap(qry, "date");
        // 精确 搜寻
        QLikes likes = QLikes.ofMap(qry, new String[] { "storehouse", "product" });
        qw
            .gt(qd.has(), "me.date", qd.getDate()).or()

            .eq(likes.has("product"), "me.product_sql_id", likes.one("product")).or()
            .eq(likes.has("storehouse"), "me.storehouse_sql_id", likes.one("storehouse")).or();

        QPage qp = QPage.ofMap(qry);
        IPage<Broken> ipage = new Page<>(qp.getPage(), qp.getSize());

        return QResponseTool.restfull(true, service.pageDeep(ipage, qw));
    }

    // POST
    @PostMapping
    public QResponse<Broken> pos(@RequestBody @Validated Broken entity) {
        System.out.println("新增 ENTITY = " + entity);
        return QResponseTool.restfull(service.save(entity), entity);
    }

    // DELETE
    @DeleteMapping("/{id}")
    public QResponse<Broken> dei(@PathVariable Long id) {
        return QResponseTool.restfull(service.removeById(id), service.getById(id));
    }
}
