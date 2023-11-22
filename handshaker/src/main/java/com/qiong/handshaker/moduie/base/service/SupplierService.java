package com.qiong.handshaker.moduie.base.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qiong.handshaker.moduie.base.Storehouse;
import com.qiong.handshaker.moduie.base.Supplier;
import com.qiong.handshaker.moduie.base.mapper.StorehouseMapper;
import com.qiong.handshaker.moduie.base.mapper.SupplierMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SupplierService extends ServiceImpl<SupplierMapper, Supplier> {

    @Autowired
    SupplierMapper mapper;

    public Supplier one(Long id) {
        return mapper.one(id);
    }

}
