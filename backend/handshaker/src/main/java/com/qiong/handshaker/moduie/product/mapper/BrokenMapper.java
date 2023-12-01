package com.qiong.handshaker.moduie.product.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.qiong.handshaker.entity.moduie.product.Broken;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface BrokenMapper extends BaseMapper<Broken> {

    <T, P extends IPage<T>> List<T> pageDeep(P page, @Param(Constants.WRAPPER) Wrapper<T> queryWrapper);
    // <T, P extends IPage<T>> Long pageDeepCount(P page, @Param(Constants.WRAPPER) Wrapper<T> queryWrapper);

    <T> List<T> listDeep(@Param(Constants.WRAPPER) Wrapper<T> queryWrapper);
}
