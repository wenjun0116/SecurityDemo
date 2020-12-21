package com.qiufeng.security.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    @GetMapping("/getList")
    public String list() {
        return "返回集合";
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public String task() {
        return "添加任务";
    }

}
