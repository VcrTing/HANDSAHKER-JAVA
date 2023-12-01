package com.qiong.handshaker.moduie.cashier.controller;

import com.qiong.handshaker.utils.anno.result.QResponseAdvice;
import com.qiong.handshaker.data.router.DataRouterOrder;
import com.qiong.handshaker.data.security.DataSecurityRoleConf;
import com.qiong.handshaker.utils.define.exception.vaiid.QLogicException;
import com.qiong.handshaker.utils.define.result.QResponse;
import com.qiong.handshaker.entity.moduie.base.Storehouse;
import com.qiong.handshaker.moduie.base.service.StorehouseService;
import com.qiong.handshaker.moduie.cashier.service.CheckoutService;
import com.qiong.handshaker.entity.moduie.custom.Customer;
import com.qiong.handshaker.entity.moduie.custom.MemberLevel;
import com.qiong.handshaker.moduie.custom.service.CustomerService;
import com.qiong.handshaker.moduie.custom.service.MemberLevelService;
import com.qiong.handshaker.entity.moduie.order.Order;
import com.qiong.handshaker.moduie.order.service.OrderService;
import com.qiong.handshaker.entity.moduie.sys.User;
import com.qiong.handshaker.moduie.sys.auth.AuthUser;
import com.qiong.handshaker.utils.tool.result.QResponseTool;
import com.qiong.handshaker.utils.tool.security.QSecurityMvcTool;
import com.qiong.handshaker.entity.vo.order.VoOrderPostForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@QResponseAdvice
@RequestMapping(DataRouterOrder.ORDER)
public class CheckoutController {

    @Autowired
    CheckoutService service;

    @Autowired
    OrderService orderService;

    @Autowired
    StorehouseService storehouseService;

    @Autowired
    CustomerService customerService;

    @Autowired
    MemberLevelService memberLevelService;

    @Autowired
    QSecurityMvcTool securityMvcTool;

    /**
    * 结算 金额
    * @params
    * @return
    */
    @PreAuthorize(DataSecurityRoleConf.AUTH_CASHIER)
    @PostMapping
    public QResponse<Object> checkout(@RequestBody @Validated VoOrderPostForm form) {

        // 是否有 收银员
        AuthUser authUser = securityMvcTool.nowUser();
        User user = authUser.getUser();
        if (user.getStorehouse_id() == null) throw new QLogicException("未找到收银员以及仓库的信息，请重试 结算！！！");

        // 是否有  收银员 所属 仓库
        Storehouse storehouse = storehouseService.getById(user.getStorehouse_id());

        // 是否有 客人
        Customer customer = null;
        MemberLevel memberLevel = null;
        if (form.getMember() != null) {
            customer = customerService.getById(form.getMember());
            memberLevel = memberLevelService.getById(customer.getMember_level_id());
        }

        /**
        * 结算 金钱
        * @params
        * @return
        */
        Order entity = service.checkout(form, user, customer, memberLevel, storehouse);

        return QResponseTool.restfull(true, entity);
    }
}
