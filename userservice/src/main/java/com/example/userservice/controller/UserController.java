package com.example.userservice.controller;


import com.example.userservice.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RefreshScope
@RequestMapping("/user")
public class UserController {
    @Autowired
    RedisUtil redisUtil;
    @Value("${user.username}")
    private String username;
    @Value("${user.password}")
    private String password;

    @RequestMapping("/getMessage")
    public String getMessage(@RequestHeader HttpHeaders headers) {
        return "01";
//        System.out.println(headers.getFirst("from"));
//        System.out.println(redisUtil.get("fromGateway"));
//        return username + "  " + password;
    }
}
