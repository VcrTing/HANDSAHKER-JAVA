package com.qiong.handshaker.moduie.order.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.qiong.handshaker.entity.moduie.order.OrderProfit;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ProfitMapper extends BaseMapper<OrderProfit> {


    // ADMIN PAGE LIST
    <T, P extends IPage<T>> List<T> pageList(P page, @Param(Constants.WRAPPER) Wrapper<T> queryWrapper);
}
