package com.qiong.handshaker.moduie.product.controller;

import com.qiong.handshaker.anno.result.QResponseAdvice;
import com.qiong.handshaker.data.router.DataRouterProduct;
import com.qiong.handshaker.define.result.QResponse;
import com.qiong.handshaker.moduie.product.Variation;
import com.qiong.handshaker.moduie.product.service.VariationAndStorehouseAndProductService;
import com.qiong.handshaker.moduie.product.service.VariationService;
import com.qiong.handshaker.tool.result.QResponseTool;
import com.qiong.handshaker.utils.basic.QTypedUtil;
import com.qiong.handshaker.vo.product.VoVariationOperaForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@QResponseAdvice
@RequestMapping(DataRouterProduct.VARIATION)
public class VariationController {

    @Autowired
    VariationService service;

    @Autowired
    VariationAndStorehouseAndProductService variationAndStorehouseAndProductService;

    // 新建 和 产品 有关 的 样式
    @PostMapping
    public QResponse<Variation> pos(@RequestBody @Validated VoVariationOperaForm form) {
        System.out.println("进来了 提交 产品 样式 新增");
        Variation entity = Variation.init(form);
        // boolean isOk = service.save(entity);
        // if (isOk) variationAndStorehouseAndProductService.afterVariationPost(form.getProduct(), entity.getId());
        variationAndStorehouseAndProductService.connectionVariationForProduct(form.getProduct(), entity);
        return QResponseTool.restfull(QTypedUtil.serLong(form.getProduct()) == null, entity);
    }

    // 修改 样式名称
    @PatchMapping("/{id}")
    public QResponse<Variation> put(@PathVariable Long id, @RequestBody @Validated VoVariationOperaForm form) {
        Variation entity = Variation.init(form);
        entity.setId(id);
        return QResponseTool.restfull(service.updateById(entity), entity);
    }

    // 删除 和 产品 有关 的 样式
    // 暂时 不需要
}
