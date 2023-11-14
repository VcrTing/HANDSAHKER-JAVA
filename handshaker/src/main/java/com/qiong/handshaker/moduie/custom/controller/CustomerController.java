package com.qiong.handshaker.moduie.custom.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qiong.handshaker.anno.result.QResponseAdvice;
import com.qiong.handshaker.data.router.DataRouterCustom;
import com.qiong.handshaker.define.query.QLikes;
import com.qiong.handshaker.define.query.QPage;
import com.qiong.handshaker.define.query.QSort;
import com.qiong.handshaker.define.result.QResponse;
import com.qiong.handshaker.moduie.custom.Customer;
import com.qiong.handshaker.moduie.custom.MemberLevel;
import com.qiong.handshaker.moduie.custom.service.CustomerService;
import com.qiong.handshaker.moduie.custom.service.MemberLevelService;
import com.qiong.handshaker.moduie.sys.User;
import com.qiong.handshaker.tool.result.QResponseTool;
import com.qiong.handshaker.vo.custom.VoCustomOptionForm;
import com.qiong.handshaker.vo.custom.VoMemberLevelForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@QResponseAdvice
@RequestMapping(DataRouterCustom.CUSTOMER)
public class CustomerController {

    @Autowired
    CustomerService service;

    @GetMapping("/aii")
    public QResponse<List<Customer>> aii() {
        return QResponseTool.genSuccess("查询成功", service.list());
    }

    @GetMapping
    public QResponse<IPage<Customer>> page(@RequestParam HashMap<String, Object> qry) {
        System.out.println("查询 = " + qry);

        QueryWrapper<Customer> qw = new QueryWrapper<>();
        QSort qs = QSort.ofMap(qry);
        qw.orderBy(QSort.hasSort(qry), qs.isAsc(), "me.id");

        QLikes likes = QLikes.ofMap(qry, new String[] { "search" });
        String search = likes.one("search");
        if (!search.isEmpty()) {
            qw
                    .like("me.member_id", search).or()
                    .like("me.phone_no", search).or()
                    .like("me.name", search).or()
                    .like("me.email", search).or();
        }

        QPage qp = QPage.ofMap(qry);
        IPage<Customer> ipage = new Page<>(qp.getPage(), qp.getSize());

        return QResponseTool.restfull(true, service.pageDeep(ipage, qw));
    }

    @GetMapping("/{id}")
    public QResponse<Customer> one(@PathVariable Long id) {
        return QResponseTool.restfull(id != null, service.oneDeep(id));
    }

    @PostMapping
    public QResponse<Object> pos(@RequestBody @Validated VoCustomOptionForm form) {
        return service.posCustom(Customer.init(form));
    }

    @PatchMapping("/{id}")
    public QResponse<Customer> upd(@PathVariable Long id, @RequestBody @Validated VoCustomOptionForm form) {
        Customer entity = Customer.init(form);
        entity.setId(id);
        return QResponseTool.restfull(service.updateById(entity), entity);
    }

    @DeleteMapping("/{id}")
    public QResponse<Customer> dei(@PathVariable Long id) {
        return QResponseTool.restfull(service.removeById(id), service.getById(id));
    }
}