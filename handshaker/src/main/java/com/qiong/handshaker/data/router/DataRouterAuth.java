package com.qiong.handshaker.data.router;

public interface DataRouterAuth {

    String ROOT = "/auth";

    String LOGIN = ROOT + "/local";

    String ANY = ROOT + "/**";

    String USER_INFO = "/users-permissions/current_user";
}
