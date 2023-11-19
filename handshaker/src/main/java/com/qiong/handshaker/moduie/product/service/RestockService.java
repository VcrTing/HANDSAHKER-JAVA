package com.qiong.handshaker.moduie.product.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qiong.handshaker.define.exception.vaiid.QLogicException;
import com.qiong.handshaker.moduie.base.Supplier;
import com.qiong.handshaker.moduie.product.Product;
import com.qiong.handshaker.moduie.product.RestockRecord;
import com.qiong.handshaker.moduie.product.mapper.RestockMapper;
import com.qiong.handshaker.vo.product.VoRestockPostForm;
import com.qiong.handshaker.vo.product.inner.VoInnerRestockDistribute;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RestockService extends ServiceImpl<RestockMapper, RestockRecord> {

    @Autowired
    RestockMapper mapper;

    @Autowired
    VariationAndStorehouseAndProductService variationAndStorehouseAndProductService;

    public Integer insertQuantity(List<VoInnerRestockDistribute> rds, Long pid) {
        Integer res = 0;
        for (VoInnerRestockDistribute rd: rds) {
            // 正式 入货
            if (variationAndStorehouseAndProductService.insertQuantity(
                    pid, rd.getVariation(), rd.getStorehouse(), rd.mustGetQuantity()
            )) res += rd.mustGetQuantity();
        }
        return res;
    }

    public RestockRecord restockNormal(VoRestockPostForm form, Supplier supplier, Product product) {
        if (product == null) throw new QLogicException("产品查询 异常，请检查产品 ID");

        // 构建 入货 记录
        RestockRecord res = RestockRecord.init(form, supplier, product); // supplier 可以为 空

        // 新增 库存 数量
        Integer resQuantity = insertQuantity(form.getRestock_distribute(), product.getId());

        // 新增 入货 记录
        return mapper.insert(res) > 0 ? res : null;
    }
}
