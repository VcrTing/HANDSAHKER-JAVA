package com.qiong.handshaker.conf.web;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConf implements WebMvcConfigurer {
    static String[] ALLOW_METHODS = { "GET", "POST", "DELETE", "PATCH", "PUT", "OPTIONS" };

    // 新增 拦截器
    // 没有 拦截器

    // 跨域
    @Override
    public void addCorsMappings(CorsRegistry cors) {
        cors.addMapping("/**")
                .allowedOriginPatterns("*")
                .allowCredentials(true)
                .allowedMethods(ALLOW_METHODS)
                .allowedHeaders("*")
                .maxAge(60*60);
    }
}