package com.lilin.dubboserviceprovider.Redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(RedisProperties.class) //使用了ConfigurationProperties注解的类进行注册并使用
@ConditionalOnClass(RedisUtils.class)
@ConditionalOnProperty(name = "EnableRedisPooled", havingValue = "true")
public class AutoConfigurationRedis {

    @Autowired
    private RedisProperties redisProperties;

    @Bean(initMethod = "getJedis")
    @ConditionalOnMissingBean
    public RedisUtils getReditUtil(){
        return new RedisUtils(redisProperties);
    }


}
