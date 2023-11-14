package com.qiong.handshaker.anno.vaiid.impi;

import com.qiong.handshaker.anno.vaiid.QPhone;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class QPhoneAnnoImpl implements ConstraintValidator<QPhone, String> {

    // 中国 号码
    private static final Pattern PHONE_PATTERN = Pattern.compile(
            "^((13[0-9])|(15[^4])|(18[0,2,3,5-9])|(17[0-8])|(147))\\d{8}$"
    );

    // 数字
    private static final Pattern ALL_NUMBER = Pattern.compile(
            "\\d+"
    );

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {

        // 空则 放行
        if (value == null || value.isEmpty()) return true;

        // 长度
        if (value.length() > 6 && value.length() < 12) return true;

        // 非空 则校验 数字
        Matcher m = ALL_NUMBER.matcher(value);
        return m.matches();
    }
}
