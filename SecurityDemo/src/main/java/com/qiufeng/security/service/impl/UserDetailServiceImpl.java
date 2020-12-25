package com.qiufeng.security.service.impl;

import com.qiufeng.security.domain.User;
import com.qiufeng.security.service.UserService;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

//自定义验证类UserDetailsService 实现Security框架UserDetailsService的接口
public class UserDetailServiceImpl implements UserDetailsService {
    //修改UserDetailsServiceImpl.java，添加属性和setter方法，修改loadUserByUsername方法
    @Resource
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String userName){
        UserDetails userDetails = null;
        try {
            User user = userService.getUserByUserName(userName);
            userDetails = new org.springframework.security.core.userdetails.User(user.getUserName(),
                    user.getPassword(),true,true,true,true,getAuthorities());
        }
        catch (Exception e) {
            e.printStackTrace();
            System.out.println("认证错误=======================>");
            return null;
        }
        return userDetails;
    }

    /**
     * 获取用户所有权限
     * @return
     */
    private Collection<GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authList = new ArrayList<GrantedAuthority>();
        authList.add(new SimpleGrantedAuthority("ROLE_USER"));
        authList.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
        return authList;
    }
}
