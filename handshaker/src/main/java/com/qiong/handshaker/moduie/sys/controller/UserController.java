package com.qiong.handshaker.moduie.sys.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qiong.handshaker.anno.result.QResponseAdvice;
import com.qiong.handshaker.data.router.DataRouterSys;
import com.qiong.handshaker.define.query.QLikes;
import com.qiong.handshaker.define.query.QPage;
import com.qiong.handshaker.define.query.QSort;
import com.qiong.handshaker.define.result.QResponse;
import com.qiong.handshaker.moduie.base.Supplier;
import com.qiong.handshaker.moduie.sys.User;
import com.qiong.handshaker.moduie.sys.service.UserService;
import com.qiong.handshaker.tool.result.QResponseTool;
import com.qiong.handshaker.tool.security.QSecurityMvcTool;
import com.qiong.handshaker.utils.basic.QTypedUtil;
import com.qiong.handshaker.vo.sys.VoUserOptionForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
public class UserController {
    @Autowired
    UserService service;

    @Autowired
    QSecurityMvcTool securityMvcTool;

    @GetMapping(DataRouterSys.USER)
    @QResponseAdvice
    public QResponse<IPage<User>> page(@RequestParam HashMap<String, Object> qry) {

        QueryWrapper<User> qw = new QueryWrapper<>();
        QSort qs = QSort.ofMap(qry);
        qw.orderBy(QSort.hasSort(qry), qs.isAsc(), "me.id");

        QLikes likes = QLikes.ofMap(qry, new String[] { "search" });
        String search = likes.one("search");
        if (!search.isEmpty()) {
            qw
                    .like("me.phone_no", search).or()
                    .like("me.name", search).or()
                    .like("me.email", search).or();
        }

        QPage qp = QPage.ofMap(qry);
        IPage<User> ipage = new Page<>(qp.getPage(), qp.getSize());

        return QResponseTool.restfull(true, service.pageDeep(ipage, qw));
    }

    @GetMapping(DataRouterSys.USER + "/{id}")
    public QResponse<User> one(@PathVariable Long id) {
        return QResponseTool.restfull(QTypedUtil.serLong(id) != null, service.getById(id));
    }

    @GetMapping(DataRouterSys.USER + "/aii")
    public QResponse<List<User>> aii() {
        return QResponseTool.genSuccess("查询成功", service.list());
    }

    // POST
    @PostMapping(DataRouterSys.USER)
    @QResponseAdvice
    public QResponse<Object> pos(@RequestBody @Validated VoUserOptionForm form) {
        return service.posUser(User.init(form));
    }

    // PATCH
    @PatchMapping(DataRouterSys.USER_PATCH + "/{id}")
    @QResponseAdvice
    public QResponse<Object> upd(@PathVariable Long id, @RequestBody @Validated VoUserOptionForm form) {
        return service.pacUser(User.init(form), id);
    }

    @DeleteMapping(DataRouterSys.USER + "/{id}")
    public QResponse<User> dei(@PathVariable Long id) {
        return QResponseTool.restfull(service.removeById(id), service.getById(id));
    }
}
