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
    * 新增
    * @params
    * @return
    */
    // 给 产品，新增一个 样式，以及执行 新增后的 后续
    public List<VariationAndStorehouseAndProduct> connectionVariationForProduct(Long pid, Variation entity) {
        // 查询 所有 storehouse;
        List<Storehouse> storehouses = storehouseMapper.selectList(null);
        // 执行 新增 样式，以及 后续
        return (variationMapper.insert(entity) > 0) ? afterVariationPost(pid, entity.getId(), storehouses) : null;
    }

    // 给一个产品 和 样式，绑定 很多仓库的 库存
    public List<VariationAndStorehouseAndProduct> afterVariationPost(Long productId, Long variationId, List<Storehouse> storehouses) {
        // INIT
        List<VariationAndStorehouseAndProduct> res = new ArrayList<>();

        // 每个 Storehouse 都要有 一个 VariationAndStorehouseAndProduct 数据
        for (Storehouse s: storehouses) {
            res.add( new VariationAndStorehouseAndProduct(productId, variationId, s.getId()) );
        }

        // 执行 新增
        for (VariationAndStorehouseAndProduct vp: res) { mapper.insert(vp); }

        // 返回
        return res;
    }

    /**
    * 查询 一个 根据 产品 ID
    * @params
    * @return
    */
    // 某 产品的 浅样式 数据
    public List<ViewInnerVariation> productVariations(Long pid) {
        List<ViewInnerVariation> res = new ArrayList<>();
        List<Variation> cen = new ArrayList<>();
        List<VariationAndStorehouseAndProduct> src = mapper.productVariations(pid);
        if (src != null) {
            // VariationAndStorehouseAndProduct 提取 Variation
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

    // 某 产品的 深度 样式 数据 包括 数量
    public List<ViewInnerVariation> productVariationDeep(Long pid) {
        List<ViewInnerVariation> res = new ArrayList<>();
        List<VariationAndStorehouseAndProduct> src = mapper.productVariationDeep(pid);
        for (VariationAndStorehouseAndProduct vp: src) {
            res.add(ViewInnerVariation.init(vp.getVariation(), vp.getStorehouse(), vp.getQuantity()));
        }
        return res;
    }

    /**
    * 入货，这里是 入货了
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

    public Boolean modifyNum(VariationAndStorehouseAndProduct vsp, Integer quantity) {
        LambdaUpdateWrapper<VariationAndStorehouseAndProduct> uw = new LambdaUpdateWrapper<>();
        uw.set(VariationAndStorehouseAndProduct::getQuantity, quantity);
        uw.eq(VariationAndStorehouseAndProduct::getId, vsp.getId());
        try {
            return (mapper.update(vsp, uw) > 0);
        } catch (Exception err) { throw new QLogicException("更改仓库库存信息失败！！！"); }
    }

    // 加货
    public Boolean insertQuantity(Long productID, Long variationID, Long storehouseID, Integer quantity) {
        VariationAndStorehouseAndProduct vsp = one(productID, variationID, storehouseID);
        Integer origin = (vsp.getQuantity() == null) ? 0 : vsp.getQuantity();
        return modifyNum(vsp, origin + quantity);
    }
    // 减货
    public Boolean removeQuantity(Long productID, Long variationID, Long storehouseID, Integer quantity) {
        VariationAndStorehouseAndProduct vsp = one(productID, variationID, storehouseID);
        Integer origin = (vsp.getQuantity() == null) ? 0 : vsp.getQuantity();
        int res = origin - quantity;
        if (res < 0) throw new QLogicException("仓库 库存 数量，少于 要减去的 数量，刚才仓库的剩余数量 = " + origin);
        return modifyNum(vsp, res);
    }
}
