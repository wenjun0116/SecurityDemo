package com.qiufeng.security.controller;

import com.qiufeng.security.utils.JwtUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
public class IndexController {

    @GetMapping("/")
    public String index(HttpServletRequest request) {
        System.out.println("token:" + request.getHeader(JwtUtils.TOKEN_HEADER));
        return "success";
    }

}
