package com.qiong.handshaker.moduie.product.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qiong.handshaker.utils.anno.result.QResponseAdvice;
import com.qiong.handshaker.data.router.DataRouterProduct;
import com.qiong.handshaker.data.security.DataSecurityRoleConf;
import com.qiong.handshaker.utils.define.query.QPage;
import com.qiong.handshaker.utils.define.query.QSort;
import com.qiong.handshaker.utils.define.result.QResponse;
import com.qiong.handshaker.entity.moduie.product.Label;
import com.qiong.handshaker.moduie.product.service.LabelService;
import com.qiong.handshaker.moduie.product.service.ProductAndLabelService;
import com.qiong.handshaker.utils.tool.result.QResponseTool;
import com.qiong.handshaker.entity.view.product.ViewLabelResultForm;
import com.qiong.handshaker.entity.vo.product.VoLabelForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
@QResponseAdvice
@RequestMapping(DataRouterProduct.LABEL)
public class LabelController {

    @Autowired
    LabelService service;

    @Autowired
    ProductAndLabelService pnlService;

    /**
    * 浅层 分页 列表
    * @params
    * @return
    */
    @PreAuthorize(DataSecurityRoleConf.AUTH_CASHIER)
    @GetMapping
    public QResponse<IPage<Label>> page(@RequestParam HashMap<String, Object> qry) {
        LambdaQueryWrapper<Label> qw = new LambdaQueryWrapper<>();
        qw.orderBy(QSort.hasSort(qry), QSort.isAsc(qry), Label::getId);
        return QResponseTool.restfull(true, service.page(new Page<Label>(QPage.easyCurrent(qry), QPage.easySize(qry)), qw));
    }

    /**
    * 查询一个标签，附带他的产品信息
    * @params
    * @return
    */
    @PreAuthorize(DataSecurityRoleConf.AUTH_ADMIN_ONLY)
    @GetMapping("/{id}")
    public QResponse<ViewLabelResultForm> one(@PathVariable Long id) {
        return QResponseTool.restfull(id != null, pnlService.oneByLabel(id));
    }

    /**
    * 新增 标签
    * @params
    * @return
    */
    @PreAuthorize(DataSecurityRoleConf.AUTH_ADMIN_ONLY)
    @PostMapping
    public QResponse<Label> pos(@RequestBody @Validated VoLabelForm form) {
        Label entity = Label.init(form);
        return QResponseTool.restfull(service.save(entity), entity);
    }

    /**
    * 修改 标签 名称
    * @params
    * @return
    */
    @PreAuthorize(DataSecurityRoleConf.AUTH_ADMIN_ONLY)
    @PatchMapping("/{id}")
    public QResponse<Label> upd(@PathVariable Long id, @RequestBody @Validated VoLabelForm form) {
        Label entity = Label.init(form);
        entity.setId(id);
        return QResponseTool.restfull(service.updateById(entity), entity);
    }

    // 删除 标签，不需要 遍历 产品 ，不需要修改 他的 labels json string
    // 只需要 product 查询 label 时，过滤掉 status = 0 的 label
    /**
    * 删除某标签
    * @params
    * @return
    */
    @PreAuthorize(DataSecurityRoleConf.AUTH_ADMIN_ONLY)
    @DeleteMapping("/{id}")
    public QResponse<Label> dei(@PathVariable Long id) {
        return QResponseTool.restfull(service.removeById(id), service.getById(id));
    }
}
