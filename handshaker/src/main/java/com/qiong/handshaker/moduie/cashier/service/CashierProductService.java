package com.qiong.handshaker.moduie.cashier.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qiong.handshaker.define.result.QPager;
import com.qiong.handshaker.moduie.cashier.mapper.CashierProductMapper;
import com.qiong.handshaker.moduie.product.Product;
import com.qiong.handshaker.moduie.product.VariationAndStorehouseAndProduct;
import com.qiong.handshaker.moduie.product.mapper.ProductMapper;
import com.qiong.handshaker.moduie.product.service.VariationAndStorehouseAndProductService;
import com.qiong.handshaker.view.cashier.ViewCashierProductResultForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CashierProductService  extends ServiceImpl<CashierProductMapper, Product> {

    @Autowired
    ProductMapper productMapper;

    @Autowired
    VariationAndStorehouseAndProductService variationAndStorehouseAndProductService;

    public QPager<ViewCashierProductResultForm> pageCashier(IPage<Product> ip, QueryWrapper<Product> qw) {

        // INIT
        List<ViewCashierProductResultForm> res = new ArrayList<>();

        // 獲取 產品
        ip = productMapper.selectPage(ip, qw);
        List<Product> ps = ip.getRecords();

        // 獲取 所有 樣式 庫存
        List<VariationAndStorehouseAndProduct> vsps = variationAndStorehouseAndProductService.listDeep();

        // 產品 構建 數據
        for (Product p: ps) {
            ViewCashierProductResultForm one = ViewCashierProductResultForm.init(p, vsps);
            res.add(one);
        }

        // 構建 QPager
        return QPager.ofPage(ip, res);
    }
}
