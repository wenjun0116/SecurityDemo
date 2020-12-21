package com.qiufeng.security.service.impl;

import com.qiufeng.security.domain.User;
import com.qiufeng.security.service.UserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

//自定义验证类UserDetailsService 实现Security框架UserDetailsService的接口
public class UserDetailServiceImpl implements UserDetailsService {
    //修改UserDetailsServiceImpl.java，添加属性和setter方法，修改loadUserByUsername方法
    private UserService userService;

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        User user = userService.getUserByUserName(userName);
        if (user != null ) {
            return (UserDetails) user;
        }
        return null;
    }
}
