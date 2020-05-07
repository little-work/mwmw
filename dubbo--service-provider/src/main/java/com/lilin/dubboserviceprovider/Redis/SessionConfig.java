package com.lilin.dubboserviceprovider.Redis;


import org.springframework.context.annotation.Configuration;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

@Configuration
//设置session的默认在redis中的存活时间
@EnableRedisHttpSession(maxInactiveIntervalInSeconds = 60 * 60 * 8)
public class SessionConfig {
}
