package com.qiong.handshaker.moduie.product.service;

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

    public List<ViewBrokenResultForm> serListBroken(List<Broken> list) {
        if (list == null) list = new ArrayList<>();
        List<ViewBrokenResultForm> res = new ArrayList<>();
        list.forEach(s -> res.add(ViewBrokenResultForm.init(s)));
        return res;
    }

    /**
     * 自定義 深度 分頁
     * @params
     * @return
     */
    public QPager<ViewBrokenResultForm> pageDeep(IPage<Broken> ip, QueryWrapper<Broken> iqw) {
        ip.setRecords(mapper.pageDeep(ip, iqw));
        return QPager.ofPage( ip, serListBroken(ip.getRecords()) );
    }

    /**
    * 新增 壞貨
    * @params
    * @return
    */
    public Boolean broken(Broken broken) {
        // 產品 庫存 減去 數量
        boolean isOk = variationAndStorehouseAndProductService.removeQuantity(
                broken.getProduct_sql_id(), broken.getVariation_sql_id(), broken.getStorehouse_sql_id(),
                broken.getQuantity());
        // 新增 壞貨 紀錄
        return isOk && this.save(broken);
    }

    /**
    * 移除 壞貨
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
