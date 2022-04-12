package com.example.userservice.controller;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RefreshScope
@RequestMapping("/user")
public class userController {
    @Value("${user.username}")
    private String username;
    @Value("${user.password}")
    private String password;
    @RequestMapping("/getMessage")
    public String getMessage() {
        return username+"  "+password;
    }
}
