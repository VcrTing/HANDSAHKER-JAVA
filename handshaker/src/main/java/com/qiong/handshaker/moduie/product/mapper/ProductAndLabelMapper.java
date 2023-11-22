package com.qiong.handshaker.moduie.product.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.qiong.handshaker.moduie.product.Product;
import com.qiong.handshaker.moduie.product.ProductAndLabel;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ProductAndLabelMapper extends BaseMapper<ProductAndLabel> {

}
