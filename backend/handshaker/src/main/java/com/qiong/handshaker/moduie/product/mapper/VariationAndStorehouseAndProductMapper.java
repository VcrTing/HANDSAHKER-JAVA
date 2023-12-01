package com.qiong.handshaker.moduie.product.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.qiong.handshaker.entity.moduie.product.VariationAndStorehouseAndProduct;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface VariationAndStorehouseAndProductMapper extends BaseMapper<VariationAndStorehouseAndProduct>
{
    List<VariationAndStorehouseAndProduct> listDeep();

    List<VariationAndStorehouseAndProduct> productVariations(@Param("id") Long pid);

    List<VariationAndStorehouseAndProduct> productVariationDeep(@Param("id") Long pid);
}
