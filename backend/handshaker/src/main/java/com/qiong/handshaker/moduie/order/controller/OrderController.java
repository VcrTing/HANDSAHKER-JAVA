package com.qiong.handshaker.moduie.order.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qiong.handshaker.utils.anno.result.QResponseAdvice;
import com.qiong.handshaker.data.router.DataRouterOrder;
import com.qiong.handshaker.data.security.DataSecurityRoleConf;
import com.qiong.handshaker.utils.define.exception.vaiid.QLogicException;
import com.qiong.handshaker.utils.define.query.QBetweenDate;
import com.qiong.handshaker.utils.define.query.QLikes;
import com.qiong.handshaker.utils.define.query.QPage;
import com.qiong.handshaker.utils.define.query.QSort;
import com.qiong.handshaker.utils.define.result.QPager;
import com.qiong.handshaker.utils.define.result.QResponse;
import com.qiong.handshaker.entity.moduie.order.Order;
import com.qiong.handshaker.moduie.order.service.OrderService;
import com.qiong.handshaker.utils.tool.result.QResponseTool;
import com.qiong.handshaker.entity.view.order.ViewOrderDetailForm;
import com.qiong.handshaker.entity.view.order.ViewOrderResultForm;
import com.qiong.handshaker.utils.utils.basic.QTypedUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
@QResponseAdvice
@RequestMapping(DataRouterOrder.ORDER)
public class OrderController {

    @Autowired
    OrderService service;

    /**
    * 深层 查询 ADMIN 订单 列表
    * @params
    * @return
    */
    @PreAuthorize(DataSecurityRoleConf.AUTH_ADMIN_ONLY)
    @GetMapping
    public QResponse<QPager<ViewOrderResultForm>> page(@RequestParam HashMap<String, Object> qry) {

        QueryWrapper<Order> qw = new QueryWrapper<>();
        qw.orderBy(QSort.hasSort(qry), QSort.ofMap(qry).isAsc(), "me.id");

        QLikes likes = QLikes.ofMap(qry, new String[] { "search", "member", "status", "order_id", "time_period", "startDate", "endDate" });

        // search 模糊 查询
        if (likes.has("search")) { qw.like("me.order_id", likes.one("search")).or(); }

        // 模糊 查询
        if (likes.has("order_id")) { qw.like("me.order_id", likes.one("order_id")).or(); }
        if (likes.has("status")) { qw.like("me.order_status", likes.one("status")).or(); }
        if (likes.has("member")) { qw.like("me.member_sql_id", likes.one("member")).or(); }

        // 日期 区间 过滤
        QBetweenDate qbd = null;
        if (likes.has("time_period")) {
            qbd = QBetweenDate.ofWhenDay( - QTypedUtil.serInt(likes.one("time_period"), 0), false);
        } else {
            if (likes.has("startDate") && likes.has("endDate")) {
                qbd = QBetweenDate.init(likes.one("startDate"), likes.one("endDate"), false);
            }
        }

        if (qbd != null) {
            qw.lt("me.order_date", qbd.starDate(false));
            qw.gt("me.order_date", qbd.endDate(false));
        }

        return QResponseTool.restfull(true, service.pageDeep(new Page<Order>(QPage.easyCurrent(qry), QPage.easySize(qry)), qw));
    }

    /**
    * 深度 查询一个 订单 详情
    * @params
    * @return
    */
    @PreAuthorize(DataSecurityRoleConf.AUTH_ADMIN_ONLY)
    @GetMapping("/{id}")
    public QResponse<ViewOrderDetailForm> detail(@PathVariable Object id) {
        if (id == null) throw new QLogicException("未找到 订单 ID");
        return QResponseTool.restfull(true, service.detail(id));
    }
}
