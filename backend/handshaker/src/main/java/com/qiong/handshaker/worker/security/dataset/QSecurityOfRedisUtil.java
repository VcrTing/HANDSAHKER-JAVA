package com.qiong.handshaker.worker.security.dataset;

import cn.hutool.json.JSONUtil;
import com.qiong.handshaker.data.properties.PropertiesMy;
import com.qiong.handshaker.moduie.sys.User;
import com.qiong.handshaker.moduie.sys.auth.AuthUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class QSecurityOfRedisUtil {

    static String NAMESPACE = "HANDSHAKE_AUTH_USER_";

    @Autowired
    RedisTemplate<String, Object> redisTemplate;

    @Autowired
    PropertiesMy propertiesMy;

    public String genSaveKey(Object k) { return NAMESPACE.concat(k.toString()); }

    // 获取 存活 时间
    public Long getLiveTime() {
        // System.out.println("propertiesMy = " + propertiesMy);
        // System.out.println("存活时间" + propertiesMy.getSecurity().getTokenSurvivalTime());
        return propertiesMy.getSecurity().getTokenSurvivalTime(); }

    /**
    * 储存 登录 用户: User 对象，设定储存时间为 TokenSurvivalTime 小时
    * @params
    * @return
    */
    public AuthUser setAuthUserToRedis(AuthUser authUser) {
        User u = authUser.getUser();
        if (u == null) return null;
        ValueOperations<String, Object> ops = redisTemplate.opsForValue();
        ops.set(genSaveKey(u.getId()), JSONUtil.toJsonStr(u), getLiveTime(), TimeUnit.HOURS);
        return authUser;
    }

    /**
    * 获取 登录 用户: User 对象
    * @params
    * @return
    */
    public AuthUser getAuthUserFromRedis(Long uid) {
        ValueOperations<String, Object> ops = redisTemplate.opsForValue();
        Object authUser = ops.get(genSaveKey(uid));
        if (authUser instanceof String) {
            User u = JSONUtil.toBean(authUser.toString(), User.class);
            return new AuthUser("", u, AuthUser.genAuthorities(u.getIsAdmin()));
        }
        return null;
    }
}
