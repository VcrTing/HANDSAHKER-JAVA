package com.qiong.handshaker.utils.basic;

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
}
