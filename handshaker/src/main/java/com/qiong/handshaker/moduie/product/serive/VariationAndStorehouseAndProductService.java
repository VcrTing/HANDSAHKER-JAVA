package com.qiong.handshaker.moduie.product.serive;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
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
    // 浅数据
    public List<ViewInnerVariation> productVariations(Long pid) {
        List<ViewInnerVariation> res = new ArrayList<>();
        List<Variation> cen = new ArrayList<>();
        List<VariationAndStorehouseAndProduct> src = mapper.productVariations(pid);
        if (src != null) {
            for (VariationAndStorehouseAndProduct vp: src) {
                cen.add(vp.getVariation());
            }
            cen = new ArrayList<>(new HashSet<>(cen));
            for (Variation v: cen) {
                res.add(ViewInnerVariation.init(v, null, 0));
            }
        }
        return res;
    }

    // 深度 数据 包括 数量
    public List<ViewInnerVariation> productVariationDeep(Long pid) {
        List<ViewInnerVariation> res = new ArrayList<>();
        List<VariationAndStorehouseAndProduct> src = mapper.productVariationDeep(pid);
        for (VariationAndStorehouseAndProduct vp: src) {
            res.add(ViewInnerVariation.init(vp.getVariation(), vp.getStorehouse(), vp.getQuantity()));
        }
        return res;
    }
}
