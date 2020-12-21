package com.qiufeng.security;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.qiufeng.security.mapper")
public class SecurityDemoMain {
    public static void main(String[] args) {
        SpringApplication.run(SecurityDemoMain.class,args);
    }
}
