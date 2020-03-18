package com.lilin.dubboserviceprovider.Redis;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "redis")
@Data
public class RedisProperties {

    private String address;
    private int port;
    private String password;
    private int maxActive;
    private int maxIdle;
    private int maxWait;
    private int timeout;
    private int defaultDatabase;

}
