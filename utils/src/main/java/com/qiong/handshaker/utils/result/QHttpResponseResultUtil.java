package com.qiong.handshaker.utils.result;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class QHttpResponseResultUtil {

    public static String UTF8 = "utf-8";

    public static void res(HttpServletResponse rep, HttpStatus code) {
        rep.setStatus(code.value());
        rep.setContentType(MediaType.APPLICATION_JSON_VALUE);
        rep.setCharacterEncoding(UTF8);
    }

    public static void success(HttpServletResponse rep, String msg) {
        try {
            res(rep, HttpStatus.OK);
            rep.getWriter().print(msg);
        } catch (IOException ignored) { }
    }

    public static void forbidden(HttpServletResponse rep, String msg) {
        try {
            res(rep, HttpStatus.FORBIDDEN);
            rep.getWriter().print(msg);
        } catch (IOException ignored) { }
    }

    public static void authFailure(HttpServletResponse rep, String msg) {
        try {
            res(rep, HttpStatus.UNAUTHORIZED);
            rep.getWriter().print(msg);
        } catch (IOException ignored) { }
    }
}
