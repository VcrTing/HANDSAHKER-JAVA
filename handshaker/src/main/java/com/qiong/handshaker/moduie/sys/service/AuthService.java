package com.qiong.handshaker.moduie.sys.service;

import com.qiong.handshaker.moduie.sys.auth.AuthUser;
import com.qiong.handshaker.moduie.sys.mapper.UserMapper;
import com.qiong.handshaker.tool.security.QSecurityDatasetTool;
import com.qiong.handshaker.tool.security.QSecurityTool;
import com.qiong.handshaker.worker.security.dataset.QSecurityOfMysqlUtil;
import com.qiong.handshaker.worker.security.dataset.entity.QToken;
import com.qiong.handshaker.utils.security.QJwtUtil;
import com.qiong.handshaker.utils.usefull.QJsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    UserMapper userMapper;

    @Autowired
    AuthenticationManager manager;

    // @Autowired
    // QSecurityDatasetTool securityDatasetTool;

    @Autowired
    QSecurityOfMysqlUtil mysqlUtil;

    private AuthUser killPassword(AuthUser authUser) {
        authUser.getUser().setPassword(""); return authUser;
    }

    // 储存 用户
    protected AuthUser storeUser(AuthUser user) {
        // REDIS
        // securityDatasetTool.setUserToRedis(user.getId(), user);

        // MYSQL
        return mysqlUtil.setAuthUserToMysql(user);
    }

    public AuthUser groupLoginUser(AuthUser user) {
        user.setJwt(QJwtUtil.genJwt(user.getId(), user.getUsername()));
        killPassword(user);
        return storeUser(user);
    }

    public Object login(String name, String pass) {
        // if (!StringUtils.hasText(name) || !StringUtils.hasText(pass)) return "用户名或密码为空";
        System.out.println(name + " " + pass);
        UsernamePasswordAuthenticationToken authenticationToken = QSecurityTool.genNamePassToken(name, pass);
        System.out.println("驗證 = " + authenticationToken);
        Authentication authentication = manager.authenticate(authenticationToken);
        System.out.println("驗證登錄通過");
        if (authentication == null) return "NAME OR PASS 错误，认证失败";
        Object authUser = authentication.getPrincipal();
        return (authUser == null) ? "NAME OR PASS 错误，认证失败" : groupLoginUser((AuthUser) authUser);
    }
}
