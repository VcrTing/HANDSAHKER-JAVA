package com.qiong.handshaker.moduie.custom.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.qiong.handshaker.moduie.custom.Customer;
import com.qiong.handshaker.moduie.custom.MemberLevel;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CustomerMapper extends BaseMapper<Customer> {

    // 查询 最后 一个 用户
    Customer lastEntity();

    Customer oneDeep(@Param("id") Long id);

    <T, P extends IPage<T>> List<T> pageDeep(P page, @Param(Constants.WRAPPER) Wrapper<T> queryWrapper);
    <T, P extends IPage<T>> Long pageDeepCount(P page, @Param(Constants.WRAPPER) Wrapper<T> queryWrapper);

}
