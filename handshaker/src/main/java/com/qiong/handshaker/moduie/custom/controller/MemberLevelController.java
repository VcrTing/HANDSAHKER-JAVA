package com.qiong.handshaker.moduie.custom.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qiong.handshaker.anno.result.QResponseAdvice;
import com.qiong.handshaker.data.router.DataRouterCustom;
import com.qiong.handshaker.data.security.DataSecurityRoleConf;
import com.qiong.handshaker.define.query.QPage;
import com.qiong.handshaker.define.query.QSort;
import com.qiong.handshaker.define.result.QResponse;
import com.qiong.handshaker.moduie.base.Supplier;
import com.qiong.handshaker.moduie.custom.MemberLevel;
import com.qiong.handshaker.moduie.custom.service.MemberLevelService;
import com.qiong.handshaker.tool.result.QResponseTool;
import com.qiong.handshaker.vo.custom.VoMemberLevelForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
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

    /**
    * 常规 分页 列表
    * @params
    * @return
    */
    @PreAuthorize(DataSecurityRoleConf.AUTH_ADMIN_ONLY)
    @GetMapping
    public QResponse<IPage<MemberLevel>> page(@RequestParam HashMap<String, Object> qry) {
        LambdaQueryWrapper<MemberLevel> qw = new LambdaQueryWrapper<>();
        qw.orderBy(QSort.hasSort(qry), QSort.isAsc(qry), MemberLevel::getId);
        return QResponseTool.restfull(true, service.page(new Page<MemberLevel>(QPage.easyCurrent(qry), QPage.easySize(qry)), qw));
    }

    /**
     * 常规 查詢 单个
     * @params
     * @return
     */
    @PreAuthorize(DataSecurityRoleConf.AUTH_ADMIN_ONLY)
    @GetMapping("/{id}")
    public QResponse<MemberLevel> one(@PathVariable Long id) {
        return QResponseTool.restfull(id != null, service.getById(id));
    }

    /**
     * 常规 新增
     * @params
     * @return
     */
    @PreAuthorize(DataSecurityRoleConf.AUTH_ADMIN_ONLY)
    @PostMapping
    public QResponse<MemberLevel> pos(@RequestBody @Validated VoMemberLevelForm memberLevelForm) {
        MemberLevel src = MemberLevel.init(memberLevelForm, null);
        return QResponseTool.restfull(service.save(src), src);
    }

    /**
     * 常规 修改
     * @params
     * @return
     */
    @PreAuthorize(DataSecurityRoleConf.AUTH_ADMIN_ONLY)
    @PatchMapping("/{id}")
    public QResponse<MemberLevel> upd(@PathVariable Long id, @RequestBody @Validated VoMemberLevelForm memberLevelForm) {
        MemberLevel src = MemberLevel.init(memberLevelForm, id);
        return QResponseTool.restfull(service.updateById(src), src);
    }

    /**
     * 常规 删除
     * @params
     * @return
     */
    @PreAuthorize(DataSecurityRoleConf.AUTH_ADMIN_ONLY)
    @DeleteMapping("/{id}")
    public QResponse<MemberLevel> dei(@PathVariable Long id) {
        return QResponseTool.restfull(service.removeById(id), service.getById(id));
    }
}
