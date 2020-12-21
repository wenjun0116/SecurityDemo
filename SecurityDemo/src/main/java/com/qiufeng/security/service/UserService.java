package com.qiufeng.security.service;

import com.qiufeng.security.domain.User;

public interface UserService {

    public User getUser(Integer id);

    public void saveUser(User user);

    public User getUserByUserName(String userName);
}
