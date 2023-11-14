package com.qiong.handshaker.tool.security;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;

@Component
public class QSecurityTool {

    final static String BEARER = "Bearer ";

    /**
     * 获取 登录时 前端 传过来的 BearToken
     * @params
     * @return
     */
    public String getBearToken(HttpServletRequest req) {
        String auth = req.getHeader("Authorization");
        if (StringUtils.hasText(auth) && auth.startsWith(BEARER)) return auth.substring(BEARER.length());
        return StringUtils.hasText(req.getHeader("Token")) ? req.getHeader("Token") : req.getHeader("token");
    }

    /**
    * 生成 登录前 验证 令牌
    * @params
    * @return
    */
    public static UsernamePasswordAuthenticationToken genNamePassToken(String username, String password) {
        if (StringUtils.hasText(username) && StringUtils.hasText(password)) return new UsernamePasswordAuthenticationToken(username, password);
        return new UsernamePasswordAuthenticationToken("", "");
    }

    /**
     * 生成 登录 后的 用户 和 权限 令牌
     * @params
     * @return
     */
    public <T> UsernamePasswordAuthenticationToken genAuthSuccessToken(T loginUser, Collection<? extends GrantedAuthority> authories) {
        return new UsernamePasswordAuthenticationToken(loginUser, null, authories);
    }

    /**
    * 令牌 存入 上下文
    * @params
    * @return
    */
    public void setTokenToContext(UsernamePasswordAuthenticationToken src) {

        SecurityContextHolder.getContext().setAuthentication(src); }
    public UsernamePasswordAuthenticationToken getTokenFromContext() { return (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication(); }
}
