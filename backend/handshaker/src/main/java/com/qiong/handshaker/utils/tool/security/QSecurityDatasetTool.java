package com.qiong.handshaker.utils.tool.security;


import com.qiong.handshaker.utils.tool.dataset.QRedisTool;
import org.springframework.beans.factory.annotation.Autowired;

public class QSecurityDatasetTool {

}
/*
import com.qiong.handshaker.utils.tool.dataset.QRedisTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class QSecurityDatasetTool {

    @Autowired
    QRedisTool redisTool;

    final static String REDIS_KEY = "AUTH_";

    /**
    * REDIS 存 TOKEN
    * @params
    * @return
    public <T> boolean setUserToRedis(Long uid, T data) { redisTool.sot(REDIS_KEY + uid, data); return true; }
    public <T> T getUserFromRedis(Long uid) {
        return redisTool.got(REDIS_KEY + uid);
    }

}

 */

