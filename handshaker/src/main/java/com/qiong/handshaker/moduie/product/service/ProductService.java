package com.qiong.handshaker.moduie.product.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qiong.handshaker.define.exception.vaiid.QLogicException;
import com.qiong.handshaker.define.result.QPager;
import com.qiong.handshaker.moduie.product.Label;
import com.qiong.handshaker.moduie.product.Product;
import com.qiong.handshaker.moduie.product.VariationAndStorehouseAndProduct;
import com.qiong.handshaker.moduie.product.mapper.ProductMapper;
import com.qiong.handshaker.utils.basic.QTypedUtil;
import com.qiong.handshaker.view.product.ViewProductResultForm;
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
    VariationAndStorehouseAndProductService variationAndStorehouseAndProductService;

    public List<ViewProductResultForm> serProducts(List<Product> src) {
        List<ViewProductResultForm> res = new ArrayList<>();
        /**
        * 这里 已经 过滤 掉了 status = 0 的 label 了
        * @params
        * @return
        */
        List<Label> allLabels = labelService.list();
        /**
        * 搜索 全部 样式 与 库存 数据 很多条，但是是 浅层的 数据
        * @params
        * @return
        */
        List<VariationAndStorehouseAndProduct> vsps = variationAndStorehouseAndProductService.listDeep();


        // 循环 组装 产品
        for (Product p: src) {
            // 组装 标签 和 基础 数据
            ViewProductResultForm one = ViewProductResultForm.initList(p, allLabels);
            // 组装 样式 与 库存
            one.groupVariations(vsps);
            res.add(one);
        }
        return res;
    }

    /**
     * 自定義 深度 分頁，更换 IPage 为 QPager
     * @params
     * @return
     */
    public QPager<ViewProductResultForm> pageDeep( IPage<Product> ip, QueryWrapper<Product> qw) {
        ip = mapper.selectPage(ip, qw); // 查询
        return QPager.ofPage(ip, serProducts(ip.getRecords())); // 換 PAGER
    }

    /**
    * 深度 查询 一个 产品 的 数据
    * @params
    * @return
    */
    public Product oneByProductID(String pID) {
        if (pID == null) throw new QLogicException("产品 ID 异常: " + pID);
        LambdaQueryWrapper<Product> qw = new LambdaQueryWrapper<>();
        // 是 ID 还是 Product_id
        qw.eq(QTypedUtil.isLong(pID) ? Product::getId : Product::getProduct_id, pID);
        Product product = mapper.selectOne(qw);
        if (product == null) throw new QLogicException("未找到该 产品 ID: " + pID);
        return product;
    }

    public ViewProductResultForm oneDeep(Object pid) {

        // 基础 查询
        Product entity = oneByProductID( pid.toString());
        List<Label> allLabels = labelService.list();

        // 构建 结果
        ViewProductResultForm res = ViewProductResultForm.initList(entity, allLabels);

        // 加入 多对多 样式 信息
        res.setVariations( variationAndStorehouseAndProductService.productVariations(entity.getId()) );

        return res;
    }

    /**
    * 更新 产品 数据
    * @params
    * @return
    */
    public boolean modifyProduct(Product product) {
        if (product.getId() == null) throw new QLogicException("产品 ID 必须存在");
        return this.updateById(product);
    }
}
