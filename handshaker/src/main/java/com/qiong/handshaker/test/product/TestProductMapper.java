package com.qiong.handshaker.test.product;

import com.qiong.handshaker.moduie.product.Product;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface TestProductMapper {

    List<Product> pageDeep();
}
