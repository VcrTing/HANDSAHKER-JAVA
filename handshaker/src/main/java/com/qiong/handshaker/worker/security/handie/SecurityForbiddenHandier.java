package com.qiong.handshaker.worker.security.handie;

import com.qiong.handshaker.utils.result.QHttpResponseResultUtil;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class SecurityForbiddenHandier implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        QHttpResponseResultUtil.forbidden(response, "您没有权限访问该链接。");
    }
}
