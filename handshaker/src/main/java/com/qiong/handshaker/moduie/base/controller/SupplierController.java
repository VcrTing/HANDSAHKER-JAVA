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
import com.qiong.handshaker.moduie.base.Storehouse;
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
    * 常规 查詢
    * @params
    * @return
    */
    @GetMapping
    public QResponse<IPage<Supplier>> page(@RequestParam HashMap<String, Object> qry) {

        LambdaQueryWrapper<Supplier> qw = new LambdaQueryWrapper<>();
        qw.orderBy(QSort.hasSort(qry), QSort.isAsc(qry), Supplier::getId);

        // 根据 search 进行 模糊 搜寻
        QLikes likes = QLikes.ofMap(qry, new String[] { "search" });
        if (likes.has("search")) {
            qw.like(Supplier::getEmail, likes.one("search")).or();
            qw.like(Supplier::getPhone_no, likes.one("search")).or();
            qw.like(Supplier::getSupplier_id, likes.one("search")).or();
            qw.like(Supplier::getContact_person, likes.one("search")).or();
        }

        return QResponseTool.restfull(true, service.page(new Page<Supplier>(QPage.easyCurrent(qry), QPage.easySize(qry)), qw));
    }

    /**
     * 常规 查詢 单个，使用了 mapper.xml
     * @params
     * @return
     */
    @GetMapping("/{id}")
    public QResponse<Supplier> one(@PathVariable Long id) {
        return QResponseTool.restfull(QTypedUtil.serLong(id) != null, service.one(id));
    }

    /**
     * 常规 新增
     * @params
     * @return
     */
    @PostMapping
    public QResponse<Supplier> pos(@RequestBody @Validated Supplier entity) {
        return QResponseTool.restfull(service.save(entity), entity);
    }

    /**
     * 常规 修改
     * @params
     * @return
     */
    @PatchMapping("/{id}")
    public QResponse<Supplier> upd(@PathVariable Long id, @RequestBody @Validated Supplier entity) {
        entity.setId(id);
        return QResponseTool.restfull(service.updateById(entity), entity);
    }

    /**
     * 常规 删除
     * @params
     * @return
     */
    @DeleteMapping("/{id}")
    public QResponse<Supplier> dei(@PathVariable Long id) {
        return QResponseTool.restfull(service.removeById(id), service.getById(id));
    }
}
