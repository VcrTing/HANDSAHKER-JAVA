package com.qiong.handshaker.moduie.sys.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.qiong.handshaker.define.exception.vaiid.QLogicException;
import com.qiong.handshaker.moduie.sys.auth.AuthUser;
import com.qiong.handshaker.moduie.sys.mapper.UserMapper;
import com.qiong.handshaker.moduie.sys.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class AuthUserDetailService implements UserDetailsService {

    @Autowired
    UserMapper userMapper;

    public User userByName(String email) {
        LambdaQueryWrapper<User> qw = new LambdaQueryWrapper<>();
        qw.eq(User::getEmail, email);
        return userMapper.selectOne(qw);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if (!StringUtils.hasText(username)) throw new UsernameNotFoundException("用户名未找到");
        User user = userByName(username);
        if (user == null) return null;
        return new AuthUser("", user, AuthUser.genAuthorities(user.getIsAdmin()));
    }
}
