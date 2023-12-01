package com.qiong.handshaker.utils.tool.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class QSecurityMvcTool {

    public UsernamePasswordAuthenticationToken getTokenFromContext() { return (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication(); }

    // 獲取 當前 登錄用戶 信息
    public <T extends UserDetails> T nowUser() {
        UsernamePasswordAuthenticationToken token = getTokenFromContext();
        Object obj = token.getPrincipal();
        if (obj != null) {
            return (T) obj;
        }
        return null;
    }

    @Autowired
    BCryptPasswordEncoder encoder;

    // 加密密码
    public String encodePass(String src) {
        return StringUtils.hasText(src) ? encoder.encode(src) : null;
    }
}
