package com.example.userservice.controller;


import com.example.userservice.redis.AquiredLockWorker;
import com.example.userservice.redis.DistributedLocker;
import com.example.userservice.service.UserService;
import com.example.userservice.util.RedisUtil;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RefreshScope
@RequestMapping("/user")

public class UserController {
    @Autowired
    RedisUtil redisUtil;
    @Autowired
    UserService userService;
    @Value("${user.username}")
    private String username;
    @Value("${user.password}")
    private String password;
    @Autowired
    private DistributedLocker distributedLocker;

    @RequestMapping("/getMessage")
    public String getMessage(@RequestHeader HttpHeaders headers) throws Exception {
      //  return username+"  " +password;
        distributedLocker.lock("user", new AquiredLockWorker<Object>() {
            @Override
            public Object invokeAfterLockAquire() {
                try {
                    System.out.println("To Do！");
                    Thread.sleep(5000);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return null;
            }
        });
        return "01!";
    }
    @HystrixCommand(fallbackMethod="fallback")
    @RequestMapping("/getProductMessage")
    public Object getProductMessage(@RequestHeader HttpHeaders headers) throws Exception {
        distributedLocker.lock("user", new AquiredLockWorker<Object>() {
            @Override
            public Object invokeAfterLockAquire() {
                try {
                    System.out.println("To Do！");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return null;
            }
        });
        return  userService.getProductMessage();
    }
    private Object fallback(HttpHeaders headers) {

        Map<String, Object> hashMap = new HashMap<>();
        hashMap.put("code", "500");
        hashMap.put("msg", "服务器错误");
        return hashMap;
    }
}
