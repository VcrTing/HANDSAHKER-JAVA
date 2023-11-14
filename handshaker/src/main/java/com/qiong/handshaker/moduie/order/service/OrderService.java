package com.qiong.handshaker.moduie.order.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qiong.handshaker.moduie.order.Order;
import com.qiong.handshaker.moduie.order.mapper.OrderMapper;
import org.springframework.stereotype.Service;

@Service // extends ServiceImpl<OrderMapper, >
public class OrderService extends ServiceImpl<OrderMapper, Order> {
}
