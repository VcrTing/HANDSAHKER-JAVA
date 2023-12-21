package com.qiong.handshaker.moduie.sys.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qiong.handshaker.utils.anno.result.QResponseAdvice;
import com.qiong.handshaker.data.router.DataRouterSys;
import com.qiong.handshaker.data.security.DataSecurityRoleConf;
import com.qiong.handshaker.utils.define.query.QLikes;
import com.qiong.handshaker.utils.define.query.QPage;
import com.qiong.handshaker.utils.define.query.QSort;
import com.qiong.handshaker.utils.define.result.QResponse;
import com.qiong.handshaker.entity.moduie.sys.User;
import com.qiong.handshaker.moduie.sys.service.UserService;
import com.qiong.handshaker.utils.tool.result.QResponseTool;
import com.qiong.handshaker.utils.tool.security.QSecurityMvcTool;
import com.qiong.handshaker.entity.vo.sys.VoUserOptionForm;
import com.qiong.handshaker.utils.utils.basic.QTypedUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
public class UserController {

    @Autowired
    UserService service;

    @Autowired
    QSecurityMvcTool securityMvcTool;

    /**
    * 深层 用户 分页 列表
    * @params
    * @return
    */
    @PreAuthorize(DataSecurityRoleConf.AUTH_CASHIER)
    @GetMapping(DataRouterSys.USER)
    @QResponseAdvice
    public QResponse<IPage<User>> page(@RequestParam HashMap<String, Object> qry) {

        QueryWrapper<User> qw = new QueryWrapper<>();
        qw.orderBy(QSort.hasSort(qry), QSort.isAsc(qry), "me.id");

        QLikes likes = QLikes.ofMap(qry, new String[] { "search" });

        // 根据 search 模糊 搜索
        String search = likes.one("search");
        if (!search.isEmpty()) {
            qw
                .like("me.phone_no", search).or()
                .like("me.name", search).or()
                .like("me.email", search).or();
        }

        return QResponseTool.restfull(true, service.pageDeep(new Page<User>(QPage.easyCurrent(qry), QPage.easySize(qry)), qw));
    }

    /**
    * 常规 获取 单个
    * @params
    * @return
    */
    @PreAuthorize(DataSecurityRoleConf.AUTH_ADMIN_ONLY)
    @GetMapping(DataRouterSys.USER + "/{id}")
    public QResponse<User> one(@PathVariable Long id) {
        return QResponseTool.restfull(QTypedUtil.hasLong(id), service.getById(id));
    }

    /**
     * 新增 用户
     * @params
     * @return
     */
    @PreAuthorize(DataSecurityRoleConf.AUTH_ADMIN_ONLY)
    @PostMapping(DataRouterSys.USER)
    @QResponseAdvice
    public QResponse<Object> pos(@RequestBody @Validated VoUserOptionForm form) {
        return service.posUser(User.init(form));
    }

    /**
     * 修改 用户
     * @params
     * @return
     */
    @PreAuthorize(DataSecurityRoleConf.AUTH_ADMIN_ONLY)
    @PatchMapping(DataRouterSys.USER_PATCH + "/{id}")
    @QResponseAdvice
    public QResponse<Object> upd(@PathVariable Long id, @RequestBody @Validated VoUserOptionForm form) {
        Object res = service.pacUser(User.init(form), id);
        return QResponseTool.restfull(!(res instanceof String), res);
    }

    /**
    * 常规 删除 用户
    * @params
    * @return
    */
    @PreAuthorize(DataSecurityRoleConf.AUTH_ADMIN_ONLY)
    @DeleteMapping(DataRouterSys.USER + "/{id}")
    public QResponse<User> dei(@PathVariable Long id) {
        return QResponseTool.restfull(service.removeById(id), service.getById(id));
    }
}
