package com.qiong.handshaker.entity.define.enums;

import com.baomidou.mybatisplus.annotation.IEnum;
import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.STRING)
public enum EnumSex implements IEnum<Integer> {

    M(1),
    F(0);

    EnumSex(Integer sex) { this.value = sex; }

    private Integer value;

    @Override
    public Integer getValue() {
        return this.value;
    }
}
