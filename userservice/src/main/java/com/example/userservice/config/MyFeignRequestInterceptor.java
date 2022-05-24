package com.example.userservice.config;

import com.example.userservice.util.RedisUtil;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;


@Configuration
public class MyFeignRequestInterceptor implements RequestInterceptor {
    @Autowired
    RedisUtil redisUtil;

    @Override
    public void apply(RequestTemplate requestTemplate) {
        requestTemplate.header("from", redisUtil.get("from").toString());
    }
}

