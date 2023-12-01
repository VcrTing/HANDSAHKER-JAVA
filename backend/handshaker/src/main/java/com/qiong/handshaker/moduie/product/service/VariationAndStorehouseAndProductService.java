package com.qiong.handshaker.moduie.product.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qiong.handshaker.define.exception.vaiid.QLogicException;
import com.qiong.handshaker.moduie.base.Storehouse;
import com.qiong.handshaker.moduie.base.mapper.StorehouseMapper;
import com.qiong.handshaker.moduie.product.Variation;
import com.qiong.handshaker.moduie.product.VariationAndStorehouseAndProduct;
import com.qiong.handshaker.moduie.product.mapper.VariationAndStorehouseAndProductMapper;
import com.qiong.handshaker.moduie.product.mapper.VariationMapper;
import com.qiong.handshaker.view.product.inner.ViewInnerStorehouseInfo;
import com.qiong.handshaker.view.product.inner.ViewInnerVariation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class VariationAndStorehouseAndProductService extends ServiceImpl<VariationAndStorehouseAndProductMapper, VariationAndStorehouseAndProduct> {

    @Autowired
    VariationAndStorehouseAndProductMapper mapper;

    @Autowired
    VariationMapper variationMapper;

    @Autowired
    StorehouseMapper storehouseMapper;

    /**
    * 列表
    * @params
    * @return
    */
    public List<VariationAndStorehouseAndProduct> listDeep() {
        return mapper.listDeep();
    }


    /**
    * 新增 產品 和 各個倉庫的 各個樣式的 庫存信息
    * @params
    * @return
    */
    public void connectionVariationForProduct(Long pid, Variation entity) {
        if (variationMapper.insert(entity) > 0) {
            List<Storehouse> storehouses = storehouseMapper.selectList(null);
            for (Storehouse s: storehouses) {
                // 每一个仓库，均新增一个 库存 数据
                mapper.insert(new VariationAndStorehouseAndProduct(pid, entity.getId(), s.getId()));
            }
        }
    }

    /**
    * 查询 一个 根据 产品 ID
    * @params
    * @return
    */
    // 某 产品的 浅样式 数据
    public List<ViewInnerVariation> productVariations(Long pid) {
        List<ViewInnerVariation> res = new ArrayList<>();
        List<VariationAndStorehouseAndProduct> src = mapper.productVariations(pid);
        if (src != null) {
            List<Variation> cen = new ArrayList<>();
            // 从 VariationAndStorehouseAndProduct 中 提取 Variation
            for (VariationAndStorehouseAndProduct vp: src) {
                cen.add(vp.getVariation());
            }
            // 样式 去重复
            cen = new ArrayList<>(new HashSet<>(cen));
            // 构建 ViewInnerVariation
            for (Variation v: cen) {
                res.add(ViewInnerVariation.init(v, null, 0));
            }
        }
        return res;
    }

    /**
    * 根据产品 ID，深度查询 库存仓库样式 数据
    * @params
    * @return
    */
    public List<VariationAndStorehouseAndProduct> byProduct(Long pid) {
        return mapper.productVariationDeep(pid);
    }

    /**
    * 根據 产品/样式/仓库 ID 獲取 庫存 數據
    * @params
    * @return
    */
    public VariationAndStorehouseAndProduct one(Long productID, Long variationID, Long storehouseID) {
        LambdaQueryWrapper<VariationAndStorehouseAndProduct> qw = new LambdaQueryWrapper<>();
        qw.eq(VariationAndStorehouseAndProduct::getProduct_sql_id, productID);
        qw.eq(VariationAndStorehouseAndProduct::getVariation_sql_id, variationID);
        qw.eq(VariationAndStorehouseAndProduct::getStorehouse_sql_id, storehouseID);
        VariationAndStorehouseAndProduct res = mapper.selectOne(qw);
        if (res == null) throw new QLogicException("仓库原数量数据 查询异常，请检查传来的数据是否有问题");
        return res;
    }

    /**
     * 庫存 修改
     * @params
     * @return
     */
    public Boolean updateQuantity(VariationAndStorehouseAndProduct vsp, Integer quantity) {
        LambdaUpdateWrapper<VariationAndStorehouseAndProduct> uw = new LambdaUpdateWrapper<>();
        uw.set(VariationAndStorehouseAndProduct::getQuantity, quantity);
        uw.eq(VariationAndStorehouseAndProduct::getId, vsp.getId());
        try { return (mapper.update(vsp, uw) > 0); } catch (Exception err) { throw new QLogicException("更改仓库库存信息失败！！！"); }
    }

    /**
    * 加货
    * @params
    * @return
    */
    public Boolean insertQuantity(Long productID, Long variationID, Long storehouseID, Integer quantity) {
        VariationAndStorehouseAndProduct vsp = one(productID, variationID, storehouseID);
        // int err = 1 / 0; 測試 回滾，能
        // throw new QLogicException("測試 調貨 數據回滾"); 測試 回滾，能
        return updateQuantity(vsp, vsp.mustGetQuantity() + quantity);
    }

    /**
    * 减货
    * @params
    * @return
    */
    public Boolean removeQuantity(Long productID, Long variationID, Long storehouseID, Integer quantity) {
        VariationAndStorehouseAndProduct vsp = one(productID, variationID, storehouseID);
        int res = vsp.mustGetQuantity() - quantity;
        if (res < 0) throw new QLogicException("仓库的库存数量，少于 要减去的数量，此仓库的剩余庫存 = " + vsp.mustGetQuantity());
        return updateQuantity(vsp, res);
    }
}
