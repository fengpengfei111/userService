package com.example.userservice.service;

import com.example.userservice.config.MyFeignRequestInterceptor;
import com.example.userservice.service.Impl.UserServiceFallbackFactory;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
@FeignClient(name = "productservice",configuration = MyFeignRequestInterceptor.class,fallbackFactory = UserServiceFallbackFactory.class)
public interface UserService {
    @RequestMapping("/product/getProductMessage")
    String getProductMessage();
}
