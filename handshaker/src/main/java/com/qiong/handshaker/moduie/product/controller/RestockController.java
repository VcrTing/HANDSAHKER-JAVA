package com.qiong.handshaker.moduie.product.controller;

import com.qiong.handshaker.anno.result.QResponseAdvice;
import com.qiong.handshaker.data.router.DataRouterProduct;
import com.qiong.handshaker.define.result.QResponse;
import com.qiong.handshaker.moduie.product.Product;
import com.qiong.handshaker.moduie.product.RestockRecord;
import com.qiong.handshaker.tool.result.QResponseTool;
import com.qiong.handshaker.vo.product.VoProductPostForm;
import com.qiong.handshaker.vo.product.VoRestockPostForm;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@QResponseAdvice
@RequestMapping(DataRouterProduct.RESTOCK)
public class RestockController {

    // 注意加事物回滚
    @PostMapping()
    public QResponse<Object> pos(@RequestBody @Validated VoRestockPostForm form) {

        return QResponseTool.restfull(true, "SUCC");
    }
}
