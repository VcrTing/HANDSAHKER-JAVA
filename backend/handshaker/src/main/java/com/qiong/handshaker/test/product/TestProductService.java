package com.qiong.handshaker.test.product;

import com.qiong.handshaker.moduie.product.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TestProductService {

    @Autowired
    TestProductMapper mapper;

    public List<Product> pageDeep() {
        return mapper.pageDeep();
    }
}
