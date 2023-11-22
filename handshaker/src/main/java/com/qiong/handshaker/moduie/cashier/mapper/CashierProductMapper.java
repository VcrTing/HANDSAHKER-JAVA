package com.qiong.handshaker.moduie.cashier.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.qiong.handshaker.moduie.product.Product;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CashierProductMapper extends BaseMapper<Product> {
    // <T, P extends IPage<T>> List<T> pageCashier(P page, @Param(Constants.WRAPPER) Wrapper<T> queryWrapper);
}
