package com.qiufeng.security.mapper;

import com.qiufeng.security.domain.User;

public interface UserMapper {

    public User getUser(Integer id);

    public void saveUser(User user);

    public User getUserByUserName(String userName);

}
