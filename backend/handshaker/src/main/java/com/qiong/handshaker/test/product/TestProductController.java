package com.qiong.handshaker.test.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestProductController {

    @Autowired
    TestProductService service;

    @RequestMapping("/products_many_to_many")
    public Object manyToMany() {


        return service.pageDeep();
    }
}
