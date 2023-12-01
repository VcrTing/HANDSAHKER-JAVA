package com.qiong.handshaker.moduie.base.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qiong.handshaker.utils.anno.result.QResponseAdvice;
import com.qiong.handshaker.data.router.DataRouterBase;
import com.qiong.handshaker.data.security.DataSecurityRoleConf;
import com.qiong.handshaker.utils.define.query.QLikes;
import com.qiong.handshaker.utils.define.query.QPage;
import com.qiong.handshaker.utils.define.query.QSort;
import com.qiong.handshaker.utils.define.result.QResponse;
import com.qiong.handshaker.entity.moduie.base.Supplier;
import com.qiong.handshaker.moduie.base.service.SupplierService;
import com.qiong.handshaker.utils.tool.result.QResponseTool;
import com.qiong.handshaker.utils.utils.basic.QTypedUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

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
    @PreAuthorize(DataSecurityRoleConf.AUTH_CASHIER)
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
    @PreAuthorize(DataSecurityRoleConf.AUTH_ADMIN_ONLY)
    @GetMapping("/{id}")
    public QResponse<Supplier> one(@PathVariable Long id) {
        return QResponseTool.restfull(QTypedUtil.serLong(id) != null, service.one(id));
    }

    /**
     * 常规 新增
     * @params
     * @return
     */
    @PreAuthorize(DataSecurityRoleConf.AUTH_ADMIN_ONLY)
    @PostMapping
    public QResponse<Supplier> pos(@RequestBody @Validated Supplier entity) {
        return QResponseTool.restfull(service.save(Supplier.autoGenerate(entity)), entity);
    }

    /**
     * 常规 修改
     * @params
     * @return
     */
    @PreAuthorize(DataSecurityRoleConf.AUTH_ADMIN_ONLY)
    @PatchMapping("/{id}")
    public QResponse<Supplier> upd(@PathVariable Long id, @RequestBody @Validated Supplier entity) {
        entity.setId(id);
        return QResponseTool.restfull(service.updateById(entity), service.getById(id));
    }

    /**
     * 常规 删除
     * @params
     * @return
     */
    @PreAuthorize(DataSecurityRoleConf.AUTH_ADMIN_ONLY)
    @DeleteMapping("/{id}")
    public QResponse<Supplier> dei(@PathVariable Long id) {
        return QResponseTool.restfull(service.removeById(id), service.getById(id));
    }
}
