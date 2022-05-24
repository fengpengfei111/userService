package com.example.userservice.service.Impl;

import com.example.userservice.service.UserService;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;
@Component
public class UserServiceFallbackFactory implements FallbackFactory<UserService> {
    @Override
    public UserService create(Throwable cause) {
        System.out.println(cause.getMessage());
        return new UserService() {
            @Override
            public String getProductMessage() {
                return "出错了";
            }
        };
    }
}
