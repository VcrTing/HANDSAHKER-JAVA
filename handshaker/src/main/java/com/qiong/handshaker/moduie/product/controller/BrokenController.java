package com.qiong.handshaker.moduie.product.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qiong.handshaker.anno.result.QResponseAdvice;
import com.qiong.handshaker.define.exception.vaiid.QLogicException;
import com.qiong.handshaker.define.query.*;
import com.qiong.handshaker.define.result.QPager;
import com.qiong.handshaker.define.result.QResponse;
import com.qiong.handshaker.moduie.product.Broken;
import com.qiong.handshaker.moduie.product.service.BrokenService;
import com.qiong.handshaker.tool.result.QResponseTool;
import com.qiong.handshaker.view.product.ViewBrokenResultForm;
import com.qiong.handshaker.vo.product.VoBrokenOperaForm;
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
    public QResponse<QPager<ViewBrokenResultForm>> page(@RequestParam HashMap<String, Object> qry) {

        QueryWrapper<Broken> qw = new QueryWrapper<>();
        QSort qs = QSort.ofMap(qry);
        qw.orderBy(QSort.hasSort(qry), qs.isAsc(), "me.id");
        qw.eq("me.status", "1");

        QDate qd = QDate.ofMap(qry, "date");
        // 模糊 搜寻
        QLikes likes = QLikes.ofMap(qry, new String[] { "storehouse", "product" });
        qw
            .gt(qd.has(), "me.date", qd.getDate()).or()
            .eq(likes.has("product"), "me.product_sql_id", likes.one("product")).or()
            .eq(likes.has("storehouse"), "me.storehouse_sql_id", likes.one("storehouse")).or();

        return QResponseTool.restfull(true, service.pageDeep(new Page<>(QPage.easyCurrent(qry), QPage.easySize(qry)), qw));
    }

    @GetMapping("/{id}")
    public QResponse<Broken> one(@PathVariable Long id) {
        return QResponseTool.restfull(true, service.getById(id));
    }

    // POST
    @PostMapping
    public QResponse<Broken> pos(@RequestBody @Validated VoBrokenOperaForm form) {
        Broken entity = Broken.init(form);
        return QResponseTool.restfull(service.broken(entity), entity);
    }

    // DELETE
    @DeleteMapping("/{id}")
    public QResponse<Broken> dei(@PathVariable Long id) {
        System.out.println(
                "ID = " + id
        );
        Broken entity = service.getById(id);
        System.out.println(entity);
        if (entity == null) throw new QLogicException("壞貨數據未找到，請重試");
        return QResponseTool.restfull(service.revocation(entity), entity);
    }
}
