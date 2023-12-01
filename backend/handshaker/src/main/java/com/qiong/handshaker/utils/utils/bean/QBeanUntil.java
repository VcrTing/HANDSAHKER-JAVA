package com.qiong.handshaker.utils.utils.bean;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Optional;
public abstract class QBeanUntil {

    // 不夠用 自己 加
    // 只能 適用於 包裝 類 哈
    // static final Class<?>[] TYPES = { String.class, Integer.class, Long.class, Boolean.class, BigDecimal.class, Date.class };

    /**
    * 判斷 object 的 類型
    * @params
    * @return

    public static Optional<Class<?>> typeAsset(Object obj) {
        Class<?> src = obj.getClass();
        return Arrays.stream(TYPES).filter(cis -> cis.equals( src )).findFirst();
    }*/

    /**
    * 執行 set 方法，只能傳 一個 值 的 例如 setName("AAA") 方法
    * @params
    * @return
    */
    public static <T> void invokeBeanSet(T src, String methodName, Object v) {
        try {
            Method method = src.getClass().getMethod(methodName, v.getClass());
            method.invoke(src, v);
        } catch (Exception ignored) { }
    }

    /**
    * 獲取 set/get 方法
    * @params
    * @return
    */
    public static Optional<String> groupSetMethod(Field f, boolean isGet) {
        return Optional.ofNullable(f)
                .map(Field::getName)
                .filter(s-> s.length() > 1)
                .map(s -> (isGet ? "get" : "set") + s.substring(0, 1).toUpperCase() + s.substring(1));
    }

    /**
    * Bean 轉換
    * @params
    * @return
    */
    public static <T, R> T convert(R src, Class<T> resClass) {
        try {
            T res = resClass.newInstance();
            for (Field f: src.getClass().getDeclaredFields()) {
                f.setAccessible(true);
                Object v = f.get(src);
                if (v != null) {
                    // 執行 值 搬運
                    String funcName = groupSetMethod(f, false).orElse("");
                    if (!funcName.isEmpty()) invokeBeanSet(res, funcName, v);
                }
            }
            return res;
        } catch (Exception ignored) { } return null;
    }
}
