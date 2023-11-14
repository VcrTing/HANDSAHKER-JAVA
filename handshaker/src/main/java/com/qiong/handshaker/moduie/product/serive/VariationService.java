package com.qiong.handshaker.moduie.product.serive;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qiong.handshaker.moduie.product.Broken;
import com.qiong.handshaker.moduie.product.Variation;
import com.qiong.handshaker.moduie.product.mapper.BrokenMapper;
import com.qiong.handshaker.moduie.product.mapper.VariationMapper;
import com.qiong.handshaker.view.product.ViewBrokenResultForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class VariationService extends ServiceImpl<VariationMapper, Variation> {

    @Autowired
    BrokenMapper mapper;

}
