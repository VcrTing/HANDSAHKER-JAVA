package com.qiong.handshaker.utils.utils.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

public class QJwtUtil {
    final static String SAIT = "QIONG";
    final static Integer DEF_EXPIRE_HOUR = 24*3; // 3 天


    final static String KEY_ID = "id";
    final static String KEY_USERNAME = "username";

    /**
     * 计算 过期 时间
     * @params
     * @return
     */
    // 过期 时间
    public static Date expireTime(Integer hour) {
        Calendar ex = Calendar.getInstance();
        ex.add(Calendar.HOUR, hour);
        return ex.getTime();
    }

    /**
     * 加密 JWT
     * @params
     * @return
     */
    public static String genJwt(Long id, String username, Integer hour) {
        HashMap<String, Object> map = new HashMap<>();
        return JWT.create().withHeader(map)
                .withClaim(KEY_ID, id).withClaim(KEY_USERNAME, username)
                .withExpiresAt(expireTime(hour))
                .sign(Algorithm.HMAC256(SAIT));
    }
    public static String genJwt(Long id, String username) { return genJwt(id, username, DEF_EXPIRE_HOUR); }

    /**
     * 解密
     * @params
     * @return
     */
    public static Long decodeJwt(String jwt) {
        try {
            JWTVerifier verifier = JWT.require(Algorithm.HMAC256(SAIT)).build();
            DecodedJWT dj = verifier.verify(jwt);
            return dj.getClaim(KEY_ID).asLong();
        } catch (Exception e) { return 0L; }
    }
}