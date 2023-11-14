package com.qiong.handshaker.moduie.base.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.qiong.handshaker.moduie.base.Storehouse;
import com.qiong.handshaker.moduie.base.Supplier;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface SupplierMapper extends BaseMapper<Supplier> {

    Supplier one(@Param("id") Long id);
}
