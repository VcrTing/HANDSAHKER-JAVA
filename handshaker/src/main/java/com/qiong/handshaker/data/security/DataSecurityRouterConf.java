package com.qiong.handshaker.data.security;

import com.qiong.handshaker.data.router.DataRouterAuth;

public interface DataSecurityRouterConf {

    // 白名单
    String[] WHITE_LIST = new String[] {
            DataRouterAuth.ROOT,
            DataRouterAuth.ANY
    };
}
