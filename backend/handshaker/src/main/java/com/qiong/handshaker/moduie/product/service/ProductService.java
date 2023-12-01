package com.qiong.handshaker.moduie.product.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qiong.handshaker.define.exception.vaiid.QLogicException;
import com.qiong.handshaker.define.result.QPager;
import com.qiong.handshaker.moduie.base.Storehouse;
import com.qiong.handshaker.moduie.base.Supplier;
import com.qiong.handshaker.moduie.base.service.StorehouseService;
import com.qiong.handshaker.moduie.base.service.SupplierService;
import com.qiong.handshaker.moduie.product.*;
import com.qiong.handshaker.moduie.product.mapper.ProductMapper;
import com.qiong.handshaker.utils.basic.QTypedUtil;
import com.qiong.handshaker.view.product.ViewProductResultForm;
import com.qiong.handshaker.view.product.inner.ViewInnerBrokenProduct;
import com.qiong.handshaker.view.product.inner.ViewInnerRestock;
import com.qiong.handshaker.view.product.inner.ViewInnerStorehouseInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductService extends ServiceImpl<ProductMapper, Product> {

    @Autowired
    ProductMapper mapper;

    @Autowired
    LabelService labelService;

    @Autowired
    SupplierService supplierService;

    @Autowired
    BrokenService brokenService;

    @Autowired
    RestockService restockService;

    @Autowired
    StorehouseService storehouseService;

    @Autowired
    VariationAndStorehouseAndProductService variationAndStorehouseAndProductService;


    /**
     * 自定義 深度 分頁，更换 IPage 为 QPager
     * @params
     * @return
     */
    public QPager<ViewProductResultForm> pageDeep( IPage<Product> ip, QueryWrapper<Product> qw) {
        ip = mapper.selectPage(ip, qw); // 率先 查询
        return QPager.ofPage(ip, serProductList(ip.getRecords())); // 更換 PAGER
    }
    // 组装 产品列表 结果
    public List<ViewProductResultForm> serProductList(List<Product> src) {
        List<ViewProductResultForm> res = new ArrayList<>();
        // 全部的 可用的 标签
        List<Label> allLabels = labelService.list();
        // 全部的 库存数据，浅层连表
        List<VariationAndStorehouseAndProduct> vsps = variationAndStorehouseAndProductService.listDeep();

        for (Product p: src) {
            // 组装 标签 和 基础 数据
            ViewProductResultForm one = ViewProductResultForm.init(p, allLabels);
            // 组装 样式 与 库存，且加入列表
            res.add(one.groupVariations(vsps));
        }
        return res;
    }

    /**
    * 深度 查询 一个 产品 的 数据
    * @params
    * @return
    */
    public ViewProductResultForm oneDeep(Object pid) {

        // 基础 查询
        Product entity = oneByProductID( pid.toString() );
        Long prodID = entity.getId();

        // 构建 ResultForm 结果
        ViewProductResultForm res = ViewProductResultForm.init(entity, labelService.list());

        // 加入 多对多 样式 信息
        res.setVariations( variationAndStorehouseAndProductService.productVariations(prodID) );

        // 查询 入货列表，根据 产品 ID
        List<RestockRecord> rrs = restockService.manyRecordByProduct( prodID );
        if (rrs != null) {
            res.setRestocks( ViewInnerRestock.fromRestockRecord(rrs, res.getVariations(), storehouseService.list(), supplierService.list()) );
            res.asyncNewRestock(); // 提取 最新 入货 记录
        }

        // 查询 坏货，组装 坏货列表结果
        res.setBroken_products(ViewInnerBrokenProduct.ofBroken( brokenService.byProduct( prodID ) ));

        // 查询 库存，组装 库存信息结果
        res.setStorehouse_info(ViewInnerStorehouseInfo.ofVsps( variationAndStorehouseAndProductService.byProduct( prodID ) ));

        return res;
    }

    /**
    * 查询 产品，通过产品的 Long ID 或 String ID
    * @params
    * @return
    */
    public Product oneByProductID(String pID) {
        if (pID == null) throw new QLogicException("产品 ID 异常: " + pID);
        LambdaQueryWrapper<Product> qw = new LambdaQueryWrapper<>();
        // 是 ID 还是 Product_id，通过 isLong 判断
        qw.eq(QTypedUtil.isLong(pID) ? Product::getId : Product::getProduct_id, pID);
        Product product = mapper.selectOne(qw);
        if (product == null) throw new QLogicException("未找到该 产品 ID: " + pID);
        return product;
    }

    /**
    * 更新 产品 数据
    * @params
    * @return
    */
    public boolean updateProduct(Product product) {
        if (product.getId() == null) throw new QLogicException("产品 ID 必须存在");
        return this.updateById(product);
    }
}
