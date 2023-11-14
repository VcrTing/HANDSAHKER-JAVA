package com.qiong.handshaker.moduie.product.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.qiong.handshaker.moduie.product.Variation;
import com.qiong.handshaker.moduie.product.VariationAndStorehouseAndProduct;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.security.core.parameters.P;

import java.util.List;

@Mapper
public interface VariationAndStorehouseAndProductMapper extends BaseMapper<VariationAndStorehouseAndProduct>
{
    List<VariationAndStorehouseAndProduct> productVariations(@Param("id") Long pid);

    List<VariationAndStorehouseAndProduct> productVariationDeep(@Param("id") Long pid);
}
