package com.qiong.handshaker.moduie.custom.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qiong.handshaker.anno.result.QResponseAdvice;
import com.qiong.handshaker.data.router.DataRouterCustom;
import com.qiong.handshaker.define.query.QPage;
import com.qiong.handshaker.define.query.QSort;
import com.qiong.handshaker.define.result.QResponse;
import com.qiong.handshaker.moduie.custom.MemberLevel;
import com.qiong.handshaker.moduie.custom.service.MemberLevelService;
import com.qiong.handshaker.tool.result.QResponseTool;
import com.qiong.handshaker.vo.custom.VoMemberLevelForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@QResponseAdvice
@RequestMapping(DataRouterCustom.MEMBER_LEVEL)
public class MemberLevelController {

    @Autowired
    MemberLevelService service;

    @GetMapping("/aii")
    public QResponse<List<MemberLevel>> aii() {
        return QResponseTool.genSuccess("查询成功", service.list());
    }

    @GetMapping
    public QResponse<IPage<MemberLevel>> page(@RequestParam HashMap<String, Object> qry) {
        System.out.println("查询 = " + qry);

        LambdaQueryWrapper<MemberLevel> qw = new LambdaQueryWrapper<>();
        QSort qs = QSort.ofMap(qry);
        qw.orderBy(QSort.hasSort(qry), qs.isAsc(), MemberLevel::getId);
        QPage qp = QPage.ofMap(qry);
        IPage<MemberLevel> ipage = new Page<>(qp.getPage(), qp.getSize());

        return QResponseTool.restfull(true, service.page(ipage, qw));
    }


    @GetMapping("/{id}")
    public QResponse<MemberLevel> one(@PathVariable Long id) {
        return QResponseTool.restfull(id != null, service.getById(id));
    }


    @PostMapping
    public QResponse<MemberLevel> pos(@RequestBody @Validated VoMemberLevelForm memberLevelForm) {
        MemberLevel src = MemberLevel.init(memberLevelForm);
        return QResponseTool.restfull(service.save(src), src);
    }

    @PatchMapping("/{id}")
    public QResponse<MemberLevel> upd(@PathVariable Long id, @RequestBody @Validated VoMemberLevelForm memberLevelForm) {
        System.out.println(id + "  " + memberLevelForm);
        MemberLevel src = MemberLevel.init(memberLevelForm);
        src.setId(id);
        return QResponseTool.restfull(service.updateById(src), src);
    }

    @DeleteMapping("/{id}")
    public QResponse<MemberLevel> dei(@PathVariable Long id) {
        return QResponseTool.restfull(service.removeById(id), service.getById(id));
    }
}

// 如果 有 時間 區間 過濾
// QBetweenDate qbd = QBetweenDate.ofMap(qry, false);
// qw.ge(qbd.hasStar(), MemberLevel::getCreatedAt, qbd.getStarDate());
// qw.le(qbd.hasEnd(), MemberLevel::getCreatedAt, qbd.getEndDate());

// 多個 Like
// qw.like(qry.get("username") != null, MemberLevel::getUsername, qry.get("username")).or();
// qw.like(qry.get("email") != null, MemberLevel::getEmail, qry.get("email")).or();
