package com.qiong.handshaker.moduie.product.serive;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qiong.handshaker.moduie.product.RestockRecord;
import com.qiong.handshaker.moduie.product.mapper.RestockMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RestockService extends ServiceImpl<RestockMapper, RestockRecord> {

    @Autowired
    RestockMapper mapper;


}
