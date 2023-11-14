package com.qiong.handshaker.moduie.base.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qiong.handshaker.anno.result.QResponseAdvice;
import com.qiong.handshaker.data.router.DataRouterBase;
import com.qiong.handshaker.define.query.QPage;
import com.qiong.handshaker.define.query.QSort;
import com.qiong.handshaker.define.result.QResponse;
import com.qiong.handshaker.moduie.base.Storehouse;
import com.qiong.handshaker.moduie.base.Supplier;
import com.qiong.handshaker.moduie.base.service.StorehouseService;
import com.qiong.handshaker.tool.result.QResponseTool;
import com.qiong.handshaker.utils.basic.QTypedUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@QResponseAdvice
@RequestMapping(DataRouterBase.STOREHOUSE)
public class StorehouseController {

    @Autowired
    StorehouseService service;

    @GetMapping
    @QResponseAdvice
    public QResponse<IPage<Storehouse>> page(@RequestParam HashMap<String, Object> qry) {

        LambdaQueryWrapper<Storehouse> qw = new LambdaQueryWrapper<>();
        QSort qs = QSort.ofMap(qry);
        qw.orderBy(QSort.hasSort(qry), qs.isAsc(), Storehouse::getId);
        QPage qp = QPage.ofMap(qry);
        IPage<Storehouse> ipage = new Page<>(qp.getPage(), qp.getSize());

        return QResponseTool.restfull(true, service.page(ipage, qw));
    }


    @GetMapping("/{id}")
    public QResponse<Storehouse> one(@PathVariable Long id) {
        return QResponseTool.restfull(QTypedUtil.serLong(id) != null, service.getById(id));
    }

    @GetMapping("/aii")
    public QResponse<List<Storehouse>> aii() {
        return QResponseTool.genSuccess("查询成功", service.list());
    }

    @PostMapping
    public QResponse<Storehouse> pos(@RequestBody @Validated Storehouse entity) {
        System.out.println("新增 ENTITY = " + entity);
        return QResponseTool.restfull(service.save(entity), entity);
    }

    @PatchMapping("/{id}")
    public QResponse<Storehouse> upd(@PathVariable Long id, @RequestBody @Validated Storehouse entity) {
        System.out.println(id + "  " + entity);
        entity.setId(id);
        return QResponseTool.restfull(service.updateById(entity), entity);
    }

    @DeleteMapping("/{id}")
    public QResponse<Storehouse> dei(@PathVariable Long id) {
        return QResponseTool.restfull(service.removeById(id), service.getById(id));
    }
}
