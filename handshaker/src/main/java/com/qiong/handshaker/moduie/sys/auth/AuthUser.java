package com.qiong.handshaker.moduie.sys.auth;

import com.qiong.handshaker.data.security.DataSecurityRoleConf;
import com.qiong.handshaker.moduie.sys.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthUser implements UserDetails {

    private String jwt;
    private User user;
    private Collection<? extends GrantedAuthority> permissions;

    public Long getId() { return user.getId(); }

    /**
    * 生成 权限 的 方法
    * @params
    * @return
    */
    public static Collection<? extends GrantedAuthority> genAuthorities(Integer isAdmin) {
        List<SimpleGrantedAuthority> res = new ArrayList<>();
        // 用户 ADMIN 权限
        res.add(new SimpleGrantedAuthority((Objects.equals(isAdmin, 1)) ? DataSecurityRoleConf.ADMIN : DataSecurityRoleConf.CASHIER));
        return res;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        if (permissions != null)
            permissions.forEach(s -> System.out.println(s.getAuthority()));

        if (permissions != null) return permissions;

        permissions = genAuthorities(this.user == null ? 0 : this.user.getIsAdmin());
        return permissions;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
