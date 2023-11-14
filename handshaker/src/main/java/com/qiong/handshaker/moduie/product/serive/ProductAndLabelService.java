package com.qiong.handshaker.moduie.product.serive;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.qiong.handshaker.data.config.DataConfigDataset;
import com.qiong.handshaker.moduie.product.Label;
import com.qiong.handshaker.moduie.product.Product;
import com.qiong.handshaker.moduie.product.ProductAndLabel;
import com.qiong.handshaker.moduie.product.inner.LabelsJson;
import com.qiong.handshaker.moduie.product.mapper.ProductAndLabelMapper;
import com.qiong.handshaker.view.product.ViewLabelResultForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class ProductAndLabelService {

    @Autowired
    ProductAndLabelMapper mapper;

    @Autowired
    LabelService labelService;

    @Autowired
    ProductService productService;

    // 搜寻 包含 该 LABEL 的 产品
    public List<Product> productsByLabel(Long lid) {
        String pk = LabelsJson.idToString(lid);
        LambdaQueryWrapper<Product> qw = new LambdaQueryWrapper<>();
        qw.like(Product::getLabels, pk);
        return productService.list(qw);
    }

    // LABEL 包装 结果
    public ViewLabelResultForm oneByLabel(Long lid) {
        return ViewLabelResultForm.init(labelService.getById(lid), productsByLabel(lid));
    }

    /**
     * 下面是 老 方法
     *
     *
     *
     public Label serOneByLabel(List<ProductAndLabel> list, Long labelId) {
     Label label = null;
     if (!list.isEmpty()) { label = list.get(0).getLabel(); }
     if (label == null) return null;

     List<Product> res = new ArrayList<>();
     for (ProductAndLabel pal: list) {
     if (Objects.equals(pal.getLabel_sql_id(), labelId)) res.add(pal.getProduct());
     }

     label.setProducts(res);
     return label;
     }

     // 查看 是否 存在
    public List<ProductAndLabel> same(Long pid, Long lid) {
        QueryWrapper<ProductAndLabel> qw = new QueryWrapper<>();
        qw.lambda().eq(ProductAndLabel::getLabel_sql_id, lid);
        qw.lambda().eq(ProductAndLabel::getProduct_sql_id, pid);
        return mapper.selectList(qw);
    }
    // 产品 新加一个 标签
    public ProductAndLabel productAddLabel(Long pid, Long lid) {
        // 查看是否 存在 标签
        List<ProductAndLabel> list = same(pid, lid);
        // 新增 具体的
        if (list.isEmpty()) {
            ProductAndLabel entity = new ProductAndLabel(pid, lid);
            if (mapper.insert(entity) > 0) return entity;
        }
        return list.get(0);
    }

    // 产品 删除一个 标签
    public ProductAndLabel productDeiLabel(Long pid, Long lid) {
        // 查看 存在
        List<ProductAndLabel> list = same(pid, lid);
        // 删除 具体
        if (!list.isEmpty()) if (mapper.deleteById(list.get(0)) > 0) return list.get(0);
        return null;
    }
     */
}
