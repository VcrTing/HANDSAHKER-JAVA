package com.qiong.handshaker.entity.define.enums;

import com.baomidou.mybatisplus.annotation.IEnum;
import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.STRING)
public enum EnumOrderStatus implements IEnum<String> {

    paid("paid"), // 已付款
    done("done"), // 已完成
    not_paid("not_paid"), // 未付款
    canceled("canceled"), // 已取消
    refunded("refunded"), // 全單已退貨
    partially_refunded("partially_refunded"); // 部份商品已退貨

    EnumOrderStatus(String v) { this.value = v; }

    private String value;

    @Override
    public String getValue() {
        return this.value;
    }
}