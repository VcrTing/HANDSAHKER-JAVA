package com.qiong.handshaker.anno.result;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.METHOD, ElementType.TYPE })
public @interface QResponseAdvice {
    boolean value() default true;
}
