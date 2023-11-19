package com.qiong.handshaker.moduie.product.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qiong.handshaker.moduie.product.Variation;
import com.qiong.handshaker.moduie.product.mapper.BrokenMapper;
import com.qiong.handshaker.moduie.product.mapper.VariationMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VariationService extends ServiceImpl<VariationMapper, Variation> {

    @Autowired
    BrokenMapper mapper;

}
