package com.qiong.handshaker.data.security;

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
}
