package com.qiong.handshaker.data.security;

import java.util.Objects;

public interface DataSecurityRoleConf {

    // ADMIN
    String ADMIN = "ADMIN";

    // CASHIER
    String CASHIER = "CASHIER";

    // ANONYMOUS
    String ANONYMOUS = "ANONYMOUS";

    // 需要 admin 权限
    String AUTH_ADMIN_ONLY = "hasAuthority('" + ADMIN + "')";
    String AUTH_CASHIER = "hasAnyAuthority('" + CASHIER + "', '" + ADMIN + "')";
    String HAS_AUTH_ANONYMOUS = "hasAnyAuthority('" + ANONYMOUS + "', '" + CASHIER + "', '" + ADMIN + "')";

    /**
    * 工具方法
    * @params
    * @return
    */
    static boolean isAdmin(String src) { return Objects.equals(src, ADMIN); }
    default boolean isAdminAuth(String src) { return Objects.equals(src, AUTH_ADMIN_ONLY); }
}
