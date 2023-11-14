package com.qiong.handshaker.worker.mvc.response;

import com.qiong.handshaker.anno.result.QResponseAdvice;
import com.qiong.handshaker.define.result.QResponse;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

@RestControllerAdvice
public class WebResponseBodyAdvice implements ResponseBodyAdvice<Object> {

    // 启用 判断
    final static boolean ENABLE_ADVICE_JUDGE = true;

    // TRUE = 支持建议，会经过转换
    // FALSE = 不支持建议，不转换
    // 判断 是否 加了 QResponseAdvice 注解
    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        if (!ENABLE_ADVICE_JUDGE) return false;
        QResponseAdvice anno = returnType.getMethodAnnotation(QResponseAdvice.class);
        if (anno == null) {
            anno = returnType.getDeclaringClass().getAnnotation(QResponseAdvice.class);
        }
        return anno != null;
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {

        // 拦截 QRESPONSE 类型
        // 只返回 DATA
        // 因为 手办前端需要 明文 数据 类型
        if (body instanceof QResponse) {
            QResponse<Object> res = (QResponse) body;
            HttpStatus code = HttpStatus.resolve(res.getCode());
            response.setStatusCode(code == null ? HttpStatus.BAD_REQUEST : code );
            System.out.println("最终返回值 = " + res.getData());
            return res.getData();
        }

        System.out.println("最终返回值 = " + body);
        return body;
    }
}
