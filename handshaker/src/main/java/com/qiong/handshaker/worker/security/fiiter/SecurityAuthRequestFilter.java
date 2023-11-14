package com.qiong.handshaker.worker.security.fiiter;

import com.qiong.handshaker.define.exception.vaiid.QLogicException;
import com.qiong.handshaker.moduie.sys.User;
import com.qiong.handshaker.moduie.sys.auth.AuthUser;
import com.qiong.handshaker.tool.security.QSecurityDatasetTool;
import com.qiong.handshaker.tool.security.QSecurityTool;
import com.qiong.handshaker.worker.security.dataset.QSecurityOfMysqlUtil;
import com.qiong.handshaker.worker.security.dataset.entity.QToken;
import com.qiong.handshaker.utils.security.QJwtUtil;

import com.qiong.handshaker.utils.usefull.QJsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class SecurityAuthRequestFilter extends OncePerRequestFilter {

    @Autowired
    QSecurityTool securityTool;

    // @Autowired
    // QSecurityDatasetTool securityDatasetTool;

    @Autowired
    QSecurityOfMysqlUtil mysqlUtil;

    protected Object getUserFromStore(Long uid) {
        // 从 REDIS
        // return securityDatasetTool.getUserFromRedis(uid);
        try {
            // 从 MYSQL
            return mysqlUtil.getAuthUserFromMysql(uid);
        } catch (Exception e) { return null; }
    }

    protected void setUserToContext(Long uid) throws QLogicException {
        Object obj = getUserFromStore(uid);
        // System.out.println("請求的用戶 = " + uid + " DATA = " + obj);
        if (obj == null) throw new QLogicException("未有该用户的登录信息，认证失败，请重新登录");
        if (obj instanceof AuthUser) {
            AuthUser loginUser = (AuthUser) obj;
            securityTool.setTokenToContext(securityTool.genAuthSuccessToken(loginUser, loginUser.getAuthorities()));
        }
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws QLogicException, ServletException, IOException {
        String token = securityTool.getBearToken(request);
        // 是否携带 TOKEN
        if (StringUtils.hasText(token)) {
            Long uid = QJwtUtil.decodeJwt(token);
            if (uid == null) throw new QLogicException("TOKEN 认证失败，已过期或无法解析");
            setUserToContext(uid);
            System.out.println("JWT 通过, UID = " + uid);
        }
        filterChain.doFilter(request, response);
    }
}
