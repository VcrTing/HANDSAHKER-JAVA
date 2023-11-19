package com.qiong.handshaker.moduie.order.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qiong.handshaker.anno.result.QResponseAdvice;
import com.qiong.handshaker.data.router.DataRouterOrder;
import com.qiong.handshaker.define.query.QBetweenDate;
import com.qiong.handshaker.define.query.QLikes;
import com.qiong.handshaker.define.query.QPage;
import com.qiong.handshaker.define.query.QSort;
import com.qiong.handshaker.define.result.QPager;
import com.qiong.handshaker.define.result.QResponse;
import com.qiong.handshaker.moduie.order.Order;
import com.qiong.handshaker.moduie.order.OrderProfit;
import com.qiong.handshaker.moduie.order.service.ProfitService;
import com.qiong.handshaker.tool.result.QResponseTool;
import com.qiong.handshaker.utils.basic.QTypedUtil;
import com.qiong.handshaker.view.order.ViewOrderResultForm;
import com.qiong.handshaker.view.order.ViewProfitResultForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
@QResponseAdvice
@RequestMapping(DataRouterOrder.PROFIT)
public class ProfitController {

    @Autowired
    ProfitService service;

    // ADMIN 订单 列表
    @GetMapping
    public QResponse<QPager<ViewProfitResultForm>> page(@RequestParam HashMap<String, Object> qry) {

        QueryWrapper<OrderProfit> qw = new QueryWrapper<>();
        qw.orderBy(QSort.hasSort(qry), QSort.ofMap(qry).isAsc(), "me.id");
        /*
        QLikes likes = QLikes.ofMap(qry, new String[] { "search", "member", "status", "order_id", "time_period", "startDate", "endDate" });
        if (likes.has("search")) { qw.like("me.order_id", likes.one("search")).or(); }

        if (likes.has("order_id")) { qw.like("me.order_id", likes.one("order_id")).or(); }
        if (likes.has("status")) { qw.like("me.order_status", likes.one("status")).or(); }
        if (likes.has("member")) { qw.like("me.member_sql_id", likes.one("member")).or(); }

        // 计算 日期
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
        */
        return QResponseTool.restfull(true, service.pageList(new Page<OrderProfit>(QPage.easyCurrent(qry), QPage.easySize(qry)), qw));
    }
}
