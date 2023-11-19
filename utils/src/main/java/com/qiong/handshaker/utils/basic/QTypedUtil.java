package com.qiong.handshaker.utils.basic;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public abstract class QTypedUtil {

    public static boolean isString(Object obj) {
        if (obj == null) return false;
        return obj.getClass().equals(String.class);
    }

    public static boolean isNoNull(Object obj) {
        return (obj == null);
    }

    /**
    * 数字类
    * @params
    * @return
    */

    public static Long serLong(Object src, Long def) {
        try {
            if (src == null) return def;
            if (src instanceof Long) return (long) src;
            if (src instanceof String || src instanceof Integer) return Long.valueOf(src.toString());
        } catch (Exception ignored) {  } return def;
    }
    public static Long serLong(Object src) {
        return serLong(src, null);
    }

    public static Integer serInt(Object src, Integer def) {
        try {
            if (src == null) return def;
            if (src instanceof Integer) return (Integer) src;
            if (src instanceof String || src instanceof Long) return Integer.valueOf(src.toString());
        } catch (Exception ignored) {  } return def;
    }
    public static Integer serInt(Object src) {
        return serInt(src, null);
    }
    /**
    * 多重 不为空
    * @params
    * @return
    */
    public static Boolean has(Object ...src) {
        for (Object o: src) { if (o == null) return false; }
        return true;
    }

    public static Boolean hasLong(Long ...src) {
        for (Long o: src) { if (o == null || serLong(o) == null) return false; }
        return true;
    }

    public static Boolean isLong(Object src) {
        try {
            Long.valueOf(src.toString()); return true;
        } catch (Exception ignored) {  } return false;
    }

    /**
    * 时间
    * @params
    * @return
    */
    static String DEF_FMT = "yyyy-MM-dd";
    static String DEF_FMT_LONG = DEF_FMT + " HH:mm:ss";
    public static Date serDate(Object dateString, boolean isExactDate) {
        if (dateString == null) return null; String src = dateString.toString().trim();
        if (src.isEmpty()) return null;
        try { return new SimpleDateFormat(isExactDate ? DEF_FMT_LONG : DEF_FMT).parse(src); }
        catch (ParseException ignored) { } return null;
    }
}
