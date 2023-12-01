package com.qiong.handshaker.worker.security.handie;

import com.qiong.handshaker.utils.utils.result.QHttpResponseResultUtil;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class SecurityAuthFailureHandier implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        // System.out.println("authException = " + authException.getLocalizedMessage());
        QHttpResponseResultUtil.authFailure(response, "认证 TOKEN 失败，请登录。");
    }
}
