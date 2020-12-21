package com.qiufeng.security.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private Integer id;

    /** 用户名 **/
    private String userName;

    /** 密码 **/
    private String password;

    /** 角色 **/
    private String role;
}
