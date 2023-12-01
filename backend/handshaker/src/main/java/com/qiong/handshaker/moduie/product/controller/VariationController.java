package com.qiong.handshaker.moduie.product.controller;

import com.qiong.handshaker.utils.anno.result.QResponseAdvice;
import com.qiong.handshaker.data.router.DataRouterProduct;
import com.qiong.handshaker.data.security.DataSecurityRoleConf;
import com.qiong.handshaker.utils.define.result.QResponse;
import com.qiong.handshaker.entity.moduie.product.Variation;
import com.qiong.handshaker.moduie.product.service.VariationAndStorehouseAndProductService;
import com.qiong.handshaker.moduie.product.service.VariationService;
import com.qiong.handshaker.utils.tool.result.QResponseTool;
import com.qiong.handshaker.entity.vo.product.VoVariationOperaForm;
import com.qiong.handshaker.utils.utils.basic.QTypedUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
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

    /**
    * 新建样式，
    * @params
    * @return
    */
    @PreAuthorize(DataSecurityRoleConf.AUTH_ADMIN_ONLY)
    @PostMapping
    public QResponse<Variation> pos(@RequestBody @Validated VoVariationOperaForm form) {
        Variation entity = Variation.init(form);
        variationAndStorehouseAndProductService.connectionVariationForProduct(form.getProduct(), entity);
        return QResponseTool.restfull(QTypedUtil.hasLong(form.getProduct()), entity);
    }

    /**
    * 修改 样式名称
    * @params
    * @return
    */
    @PreAuthorize(DataSecurityRoleConf.AUTH_ADMIN_ONLY)
    @PatchMapping("/{id}")
    public QResponse<Variation> put(@PathVariable Long id, @RequestBody @Validated VoVariationOperaForm form) {
        Variation entity = Variation.init(form);
        entity.setId(id);
        return QResponseTool.restfull(service.updateById(entity), entity);
    }
}
