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
import com.qiong.handshaker.moduie.product.Label;
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

    /**
    * 仓库 分页 列表
    * @params
    * @return
    */
    @GetMapping
    @QResponseAdvice
    public QResponse<IPage<Storehouse>> page(@RequestParam HashMap<String, Object> qry) {
        LambdaQueryWrapper<Storehouse> qw = new LambdaQueryWrapper<>();
        qw.orderBy(QSort.hasSort(qry), QSort.isAsc(qry), Storehouse::getId);
        return QResponseTool.restfull(true, service.page(new Page<Storehouse>(QPage.easyCurrent(qry), QPage.easySize(qry)), qw));
    }

    /**
    * 查询一个 仓库
    * @params
    * @return
    */
    @GetMapping("/{id}")
    public QResponse<Storehouse> one(@PathVariable Long id) {
        return QResponseTool.restfull(QTypedUtil.hasLong(id), service.getById(id));
    }

    /**
    * 常规 新增
    * @params
    * @return
    */
    @PostMapping
    public QResponse<Storehouse> pos(@RequestBody @Validated Storehouse entity) {
        return QResponseTool.restfull(service.save(entity), entity);
    }

    /**
     * 常规 修改
     * @params
     * @return
     */
    @PatchMapping("/{id}")
    public QResponse<Storehouse> upd(@PathVariable Long id, @RequestBody @Validated Storehouse entity) {
        entity.setId(id);
        return QResponseTool.restfull(service.updateById(entity), entity);
    }

    /**
     * 常规 删除
     * @params
     * @return
     */
    @DeleteMapping("/{id}")
    public QResponse<Storehouse> dei(@PathVariable Long id) {
        return QResponseTool.restfull(service.removeById(id), service.getById(id));
    }
}
