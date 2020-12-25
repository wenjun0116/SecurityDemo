package com.qiufeng.security.controller;

import com.qiufeng.security.domain.User;
import com.qiufeng.security.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Resource
    private UserService userService;

    @Resource
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @PostMapping("/registry")
    public String registerUser(@RequestBody Map<String,String> registerUser) {
        User user = new User();
        user.setUserName(registerUser.get("userName"));
        user.setPassword(bCryptPasswordEncoder.encode(registerUser.get("password")));
        user.setRole("ROLE_USER");
        userService.saveUser(user);
        return "success";
    }

    @PostMapping("/login")
    public String login(@RequestBody Map<String,String> user) {
        System.out.println(user);
        User userName = userService.getUserByUserName(user.get("userName"));
        if (userName == null) {
            return "error";
        }
        return "success";
    }
}
