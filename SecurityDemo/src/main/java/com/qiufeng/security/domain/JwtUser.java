package com.qiufeng.security.domain;

import com.qiufeng.security.domain.User;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

/**
 * 登录用户信息
 */
public class JwtUser implements UserDetails {

    private Integer id;

    private String userName;

    private String password;

    private Collection<? extends GrantedAuthority> authorities;

    /**
     * 创建JwtUser
     * @param user
     */
    public JwtUser(User user) {
        id = user.getId();
        userName = user.getUserName();
        password = user.getPassword();
        authorities = Collections.singleton(new SimpleGrantedAuthority(user.getRole()));
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return userName;
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
