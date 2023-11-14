package com.qiong.handshaker.define.enums;

import com.baomidou.mybatisplus.annotation.IEnum;
import com.fasterxml.jackson.annotation.JsonFormat;
import jdk.nashorn.internal.ir.IdentNode;

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
