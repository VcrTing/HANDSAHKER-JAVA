package com.qiong.handshaker.moduie.product.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qiong.handshaker.define.exception.vaiid.QLogicException;
import com.qiong.handshaker.moduie.base.Supplier;
import com.qiong.handshaker.moduie.product.Product;
import com.qiong.handshaker.moduie.product.RestockRecord;
import com.qiong.handshaker.moduie.product.mapper.RestockMapper;
import com.qiong.handshaker.utils.basic.QTypedUtil;
import com.qiong.handshaker.vo.product.VoRestockPostForm;
import com.qiong.handshaker.vo.product.inner.VoInnerRestockDistribute;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RestockService extends ServiceImpl<RestockMapper, RestockRecord> {

    @Autowired
    VariationAndStorehouseAndProductService variationAndStorehouseAndProductService;

    /**
    * 一個產品 的 入貨紀錄
    * @params
    * @return
    */
    public List<RestockRecord> manyRecordByProduct(Long pid) {
        LambdaQueryWrapper<RestockRecord> qw = new LambdaQueryWrapper<>();
        qw.eq(RestockRecord::getProduct_sql_id, pid);
        return this.list(qw);
    }

    /**
    * 正常 入货
    * @params
    * @return
    */
    public RestockRecord restockNormal(VoRestockPostForm form, Supplier supplier, Product product) {
        if (product == null || product.getId() == null) throw new QLogicException("产品查询 异常，请检查产品 ID");

        // 构建 入货 记录
        RestockRecord res = RestockRecord.init(form, supplier, product); // supplier 可以为 空

        // 循環 入货
        for (VoInnerRestockDistribute rd: form.getRestock_distribute()) {
            variationAndStorehouseAndProductService.insertQuantity(product.getId(), rd.getVariation(), rd.getStorehouse(), rd.mustGetQuantity());
        }

        // 新增 入货 记录
        return this.save(res) ? res : null;
    }
}
