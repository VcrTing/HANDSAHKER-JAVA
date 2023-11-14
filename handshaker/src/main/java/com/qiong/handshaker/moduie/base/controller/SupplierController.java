package com.qiong.handshaker.moduie.base.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qiong.handshaker.anno.result.QResponseAdvice;
import com.qiong.handshaker.data.router.DataRouterBase;
import com.qiong.handshaker.define.query.QLikes;
import com.qiong.handshaker.define.query.QPage;
import com.qiong.handshaker.define.query.QSort;
import com.qiong.handshaker.define.result.QResponse;
import com.qiong.handshaker.moduie.base.Supplier;
import com.qiong.handshaker.moduie.base.service.SupplierService;
import com.qiong.handshaker.tool.result.QResponseTool;
import com.qiong.handshaker.utils.basic.QTypedUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@QResponseAdvice
@RequestMapping(DataRouterBase.SUPPLIER)
public class SupplierController {

    @Autowired
    SupplierService service;

    /**
    * 無需聯表的 查詢
    * @params
    * @return
    */
    @GetMapping
    public QResponse<IPage<Supplier>> page(@RequestParam HashMap<String, Object> qry) {

        LambdaQueryWrapper<Supplier> qw = new LambdaQueryWrapper<>();
        QSort qs = QSort.ofMap(qry);
        qw.orderBy(QSort.hasSort(qry), qs.isAsc(), Supplier::getId);

        // 模糊 搜寻
        QLikes likes = QLikes.ofMap(qry, new String[] { "search" });
        if (likes.has("search")) {
            qw.like(Supplier::getEmail, likes.one("search")).or();
            qw.like(Supplier::getPhone_no, likes.one("search")).or();
            qw.like(Supplier::getSupplier_id, likes.one("search")).or();
            qw.like(Supplier::getContact_person, likes.one("search")).or();
        }

        QPage qp = QPage.ofMap(qry);
        IPage<Supplier> ipage = new Page<>(qp.getPage(), qp.getSize());

        return QResponseTool.restfull(true, service.page(ipage, qw));
    }

    @GetMapping("/{id}")
    public QResponse<Supplier> one(@PathVariable Long id) {
        return QResponseTool.restfull(QTypedUtil.serLong(id) != null, service.one(id));
    }

    @GetMapping("/aii")
    public QResponse<List<Supplier>> aii() {
        return QResponseTool.genSuccess("查询成功", service.list());
    }

    @PostMapping
    public QResponse<Supplier> pos(@RequestBody @Validated Supplier entity) {
        System.out.println("新增 ENTITY = " + entity);
        return QResponseTool.restfull(service.save(entity), entity);
    }

    @PatchMapping("/{id}")
    public QResponse<Supplier> upd(@PathVariable Long id, @RequestBody @Validated Supplier entity) {
        System.out.println(id + "  " + entity);
        entity.setId(id);
        return QResponseTool.restfull(service.updateById(entity), entity);
    }

    @DeleteMapping("/{id}")
    public QResponse<Supplier> dei(@PathVariable Long id) {
        return QResponseTool.restfull(service.removeById(id), service.getById(id));
    }
}
