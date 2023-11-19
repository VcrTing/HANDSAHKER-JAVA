package com.qiong.handshaker.moduie.product.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qiong.handshaker.anno.result.QResponseAdvice;
import com.qiong.handshaker.data.router.DataRouterProduct;
import com.qiong.handshaker.define.query.QPage;
import com.qiong.handshaker.define.query.QSort;
import com.qiong.handshaker.define.result.QResponse;
import com.qiong.handshaker.moduie.product.Label;
import com.qiong.handshaker.moduie.product.service.LabelService;
import com.qiong.handshaker.moduie.product.service.ProductAndLabelService;
import com.qiong.handshaker.tool.result.QResponseTool;
import com.qiong.handshaker.view.product.ViewLabelResultForm;
import com.qiong.handshaker.vo.product.VoLabelForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@QResponseAdvice
@RequestMapping(DataRouterProduct.LABEL)
public class LabelController {

    @Autowired
    LabelService service;

    @Autowired
    ProductAndLabelService pnlService;

    @GetMapping
    public QResponse<IPage<Label>> page(@RequestParam HashMap<String, Object> qry) {

        LambdaQueryWrapper<Label> qw = new LambdaQueryWrapper<>();
        QSort qs = QSort.ofMap(qry);
        qw.orderBy(QSort.hasSort(qry), qs.isAsc(), Label::getId);
        QPage qp = QPage.ofMap(qry);
        IPage<Label> ipage = new Page<>(qp.getPage(), qp.getSize());

        return QResponseTool.restfull(true, service.page(ipage, qw));
    }

    // GET ONE 获取 一个 标签
    @GetMapping("/{id}")
    public QResponse<ViewLabelResultForm> one(@PathVariable Long id) {
        return QResponseTool.restfull(id != null, pnlService.oneByLabel(id));
    }

    @GetMapping("/aii")
    public QResponse<List<Label>> aii() {
        return QResponseTool.genSuccess("查询成功", service.list());
    }

    // POST
    @PostMapping
    public QResponse<Label> pos(@RequestBody @Validated VoLabelForm form) {
        Label entity = Label.init(form);
        System.out.println("新增 ENTITY = " + entity);
        return QResponseTool.restfull(service.save(entity), entity);
    }

    @PatchMapping("/{id}")
    public QResponse<Label> upd(@PathVariable Long id, @RequestBody @Validated VoLabelForm form) {
        Label entity = Label.init(form);
        entity.setId(id);
        System.out.println(id + "  " + entity);
        return QResponseTool.restfull(service.updateById(entity), entity);
    }

    // 删除 标签，不需要 遍历 产品 ，不需要修改 他的 labels json string
    // 只需要 product 查询 label 时，过滤掉 status = 0 的 label
    @DeleteMapping("/{id}")
    public QResponse<Label> dei(@PathVariable Long id) {
        return QResponseTool.restfull(service.removeById(id), service.getById(id));
    }
}
