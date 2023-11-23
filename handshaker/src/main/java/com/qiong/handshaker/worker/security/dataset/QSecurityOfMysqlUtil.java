package com.qiong.handshaker.worker.security.dataset;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.qiong.handshaker.moduie.sys.User;
import com.qiong.handshaker.moduie.sys.auth.AuthUser;
import com.qiong.handshaker.utils.usefull.QJsonUtil;
import com.qiong.handshaker.worker.security.dataset.entity.QToken;
import com.qiong.handshaker.worker.security.dataset.mapper.QTokenMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class QSecurityOfMysqlUtil {

    /**
     * MYSQL 存 TOKEN，必须依赖 SPRING BOOT 环境
     * @params
     * @return
     */
    @Autowired
    QTokenMapper tokenMapper;

    private QToken oneByUid(Long uid) {
        LambdaQueryWrapper<QToken> qw = new LambdaQueryWrapper<>();
        qw.eq(QToken::getUserId, uid);
        QToken qToken = tokenMapper.selectOne(qw);
        return qToken;
    }

    private boolean setToMysql(QToken qToken) {
        if (qToken == null) return false;
        // 储存 或 新增
        QToken src = oneByUid(qToken.getUserId());
        try {
            if (src == null) {
                tokenMapper.insert(qToken);
            } else {
                LambdaUpdateWrapper<QToken> uw = new LambdaUpdateWrapper<>();
                uw.eq(QToken::getUserId, qToken.getUserId());
                tokenMapper.update(qToken, uw);
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    /**
    * 这里是 与 AUTTH USER + USER 的 交互
    * @params
    * @return
    */

    public AuthUser getAuthUserFromMysql(Long uid) {
        try {
            // 从 MYSQL
            QToken qToken = oneByUid(uid);
            // System.out.println("<--- MYSQL 里面的 USER STRING = " + qToken.getAuthUserString());
            // System.out.println(QJsonUtil.serToObject(qToken.getAuthUserString(), User.class));
            User u = QJsonUtil.serToObject(qToken.getAuthUserString(), User.class);
            // System.out.println(">--- MYSQL 里面的 用户 = " + u);
            // 查询 USER 后去 构建 AuthUser，因为 AuthUser 很难
            return new AuthUser(qToken.getToken(), u, AuthUser.genAuthorities(u.getIsAdmin()));
        } catch (Exception e) { return null; }
    }

    public AuthUser setAuthUserToMysql(AuthUser user) {
        try {
            QToken qToken = new QToken();
            // 把 AuthUser 里面的 User 转 JSON
            String userString = QJsonUtil.serToString(user.getUser());
            qToken.setAuthUserString(userString); // User 转 STRING
            qToken.setUserId(user.getId());
            qToken.setToken(user.getJwt());
            //
            setToMysql(qToken);
            return user;
        } catch (Exception ignored) { return null; }
    }
}
