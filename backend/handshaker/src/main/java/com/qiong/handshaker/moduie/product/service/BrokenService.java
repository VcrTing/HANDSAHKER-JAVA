package com.qiong.handshaker.moduie.product.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qiong.handshaker.define.result.QPager;
import com.qiong.handshaker.moduie.product.Broken;
import com.qiong.handshaker.moduie.product.mapper.BrokenMapper;
import com.qiong.handshaker.view.product.ViewBrokenResultForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BrokenService extends ServiceImpl<BrokenMapper, Broken> {

    @Autowired
    BrokenMapper mapper;

    @Autowired
    VariationAndStorehouseAndProductService variationAndStorehouseAndProductService;

    /**
    * 通过 产品 查 坏货，深度查詢
    * @params
    * @return
    */
    public List<Broken> byProduct(Long pid) {
        LambdaQueryWrapper<Broken> qw = new LambdaQueryWrapper<>();
        qw.eq(Broken::getProduct_sql_id, pid);
        return mapper.listDeep(qw);
    }


    /**
     * 自定義 深度 分頁
     * @params
     * @return
     */
    public QPager<ViewBrokenResultForm> pageDeep(IPage<Broken> ip, QueryWrapper<Broken> iqw) {
        ip.setRecords(mapper.pageDeep(ip, iqw));
        return QPager.ofPage( ip, ViewBrokenResultForm.initList(ip.getRecords()) );
    }

    /**
    * 新增 壞貨，倉庫 庫存 減去
    * @params
    * @return
    */
    public Boolean broken(Broken broken) {
        // 產品 庫存 減去 數量
        boolean isOk = variationAndStorehouseAndProductService.removeQuantity(
                broken.getProduct_sql_id(), broken.getVariation_sql_id(), broken.getStorehouse_sql_id(),
                broken.mustGetQuantity());
        // 新增 壞貨 紀錄
        return isOk && this.save(broken);
    }

    /**
    * 移除 壞貨，倉庫 庫存 加回
    * @params
    * @return
    */
    public Boolean revocation(Broken broken) {
        boolean isOk = variationAndStorehouseAndProductService.insertQuantity(
                broken.getProduct_sql_id(), broken.getVariation_sql_id(), broken.getStorehouse_sql_id(),
                broken.getQuantity());
        return isOk && this.removeById(broken.getId());
    }
}
