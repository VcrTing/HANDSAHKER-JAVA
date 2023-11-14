package com.qiong.handshaker.moduie.order.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qiong.handshaker.anno.result.QResponseAdvice;
import com.qiong.handshaker.data.router.DataRouterBase;
import com.qiong.handshaker.define.query.QLikes;
import com.qiong.handshaker.define.query.QPage;
import com.qiong.handshaker.define.query.QSort;
import com.qiong.handshaker.define.result.QResponse;
import com.qiong.handshaker.moduie.order.Order;
import com.qiong.handshaker.moduie.order.service.OrderService;
import com.qiong.handshaker.tool.result.QResponseTool;
import com.qiong.handshaker.utils.basic.QTypedUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@QResponseAdvice
@RequestMapping(DataRouterBase.SUPPLIER)
public class OrderController {

    @Autowired
    OrderService service;
    /*
    @GetMapping
    public QResponse<IPage<Order>> page(@RequestParam HashMap<String, Object> qry) {

        LambdaQueryWrapper<Order> qw = new LambdaQueryWrapper<>();
        QSort qs = QSort.ofMap(qry);
        qw.orderBy(QSort.hasSort(qry), qs.isAsc(), Order::getId);

        // 模糊 搜寻
        QLikes likes = QLikes.ofMap(qry, new String[] { "search" });
        if (likes.has("search")) {
            qw.like(Order::getOrder_date, likes.one("search")).or();
        }

        QPage qp = QPage.ofMap(qry);
        IPage<Order> ipage = new Page<>(qp.getPage(), qp.getSize());

        return QResponseTool.restfull(true, service.page(ipage, qw));
    }

    @GetMapping("/{id}")
    public QResponse<Order> one(@PathVariable Long id) {
        return QResponseTool.restfull(QTypedUtil.serLong(id) != null, service.getById(id));
    }

    @GetMapping("/aii")
    public QResponse<List<Order>> aii() {
        return QResponseTool.genSuccess("查询成功", service.list());
    }

    @PostMapping
    public QResponse<Order> pos(@RequestBody @Validated Order entity) {
        System.out.println("新增 ENTITY = " + entity);
        return QResponseTool.restfull(service.save(entity), entity);
    }

    @PatchMapping("/{id}")
    public QResponse<Order> upd(@PathVariable Long id, @RequestBody @Validated Order entity) {
        System.out.println(id + "  " + entity);
        entity.setId(id);
        return QResponseTool.restfull(service.updateById(entity), entity);
    }

    @DeleteMapping("/{id}")
    public QResponse<Order> dei(@PathVariable Long id) {
        return QResponseTool.restfull(service.removeById(id), service.getById(id));
    }
     */
}
