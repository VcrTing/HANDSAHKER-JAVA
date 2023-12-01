package com.qiong.handshaker.moduie.sys.controller;

import com.qiong.handshaker.anno.result.QResponseAdvice;
import com.qiong.handshaker.data.router.DataRouterAuth;
import com.qiong.handshaker.define.exception.vaiid.QLogicException;
import com.qiong.handshaker.define.result.QResponse;
import com.qiong.handshaker.moduie.sys.User;
import com.qiong.handshaker.moduie.sys.auth.AuthUser;
import com.qiong.handshaker.moduie.sys.service.AuthService;
import com.qiong.handshaker.moduie.sys.service.UserService;
import com.qiong.handshaker.tool.result.QResponseTool;
import com.qiong.handshaker.tool.security.QSecurityMvcTool;
import com.qiong.handshaker.utils.basic.QTypedUtil;
import com.qiong.handshaker.vo.sys.VoUserLoginForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class AuthController {

    @Autowired
    AuthService authService;

    @Autowired
    UserService userService;

    @Autowired
    QSecurityMvcTool securityMvcTool;

    @GetMapping
    public String aii() {
        return "SUCC";
    }

    /**
    * 註冊
    * @params
    * @return
    */
    @QResponseAdvice
    @PostMapping(DataRouterAuth.REGISTER)
    public QResponse<Object> creatAdmin(@RequestBody VoUserLoginForm loginForm) {
        Object res;
        String emi = loginForm.getIdentifier();

        if (userService.sameUser(emi) == null) {
            res = authService.register(emi, loginForm.getPassword());
        } else {
            return QResponseTool.genBad("註冊失敗", "已存在相同的用戶");
        }
        return QResponseTool.genSuccess("註冊成功", res);
    }

    /**
    * 登录
    * @params
    * @return
    */
    @QResponseAdvice
    @PostMapping(DataRouterAuth.LOGIN)
    public QResponse<Object> login(@RequestBody VoUserLoginForm loginForm) {
        Object res = authService.login(loginForm.getIdentifier(), loginForm.getPassword());
        if (QTypedUtil.isString(res)) return QResponseTool.genBad("登录错误", res);
        return QResponseTool.genSuccess("登录成功", res);
    }

    /**
    * 登录后的 用户信息
    * @params
    * @return
    */
    @GetMapping(DataRouterAuth.USER_INFO)
    public QResponse<User> userinfo() {
        AuthUser authUser = securityMvcTool.nowUser();
        if (authUser == null || authUser.getUser() == null) throw new QLogicException("登录上下文错误");
        User user = userService.userInfo(authUser.getId());
        // System.out.println("用户信息 = " + user);
        return QResponseTool.genSuccess("获取数据成功", user);
    }
}
