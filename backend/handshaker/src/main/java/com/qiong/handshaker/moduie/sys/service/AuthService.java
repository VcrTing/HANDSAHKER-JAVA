package com.qiong.handshaker.moduie.sys.service;

import com.qiong.handshaker.entity.moduie.sys.User;
import com.qiong.handshaker.moduie.sys.auth.AuthUser;
import com.qiong.handshaker.moduie.sys.mapper.UserMapper;
import com.qiong.handshaker.utils.tool.security.QSecurityTool;
import com.qiong.handshaker.utils.utils.security.QJwtUtil;
import com.qiong.handshaker.worker.security.dataset.QSecurityOfMysqlUtil;
import com.qiong.handshaker.worker.security.dataset.QSecurityOfRedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    UserMapper userMapper;

    @Autowired
    AuthenticationManager manager;

    @Autowired
    QSecurityOfMysqlUtil mysqlUtil;

    // @Autowired
    // QSecurityOfRedisUtil redisUtil;

    @Autowired
    PasswordEncoder passwordEncoder;

    // 储存 用户 到 缓存 内
    protected AuthUser storeUser(AuthUser user) {
        // REDIS
        // return redisUtil.setAuthUserToRedis(user);
        // MYSQL
        return mysqlUtil.setAuthUserToMysql(user);
    }

    public AuthUser groupLoginUser(AuthUser user) {
        // 组装 JWT
        user.setJwt(QJwtUtil.genJwt(user.getId(), user.getUsername()));
        // 删除密码
        user.getUser().setPassword("");
        // 缓存 用户
        return storeUser(user);
    }

    /**
    * 用户 登录
    * @params
    * @return
    */
    public Object login(String name, String pass) {
        UsernamePasswordAuthenticationToken authenticationToken = QSecurityTool.genNamePassToken(name, pass);
        Authentication authentication = manager.authenticate(authenticationToken);
        // System.out.println("驗證通过 = " + authenticationToken);
        if (authentication == null) return "NAME OR PASS 错误，认证失败";
        Object authUser = authentication.getPrincipal();
        return (authUser == null) ? "NAME OR PASS 错误，认证失败" : groupLoginUser((AuthUser) authUser);
    }

    /**
    * 用戶註冊
    * @params
    * @return
    */
    public Object register(String email, String pass) {
        User user = User.init(email, passwordEncoder.encode(pass));
        return userMapper.insert(user) > 0 ? "註冊成功" : null;
    }
}
