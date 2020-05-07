package com.lilin.dubboserviceprovider;

import com.lilin.dubboserviceprovider.Redis.AutoConfigurationRedis;
import com.lilin.dubboserviceprovider.Redis.RedisUtils;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class MainTest {


    public static void main(String[] args) {
        AnnotationConfigApplicationContext a =
                new AnnotationConfigApplicationContext(AutoConfigurationRedis.class);

        RedisUtils redisUtils=(RedisUtils) a.getBean(RedisUtils.class);
        //System.out.println(redisUtils.getJedis().get("mykey"));
    }

}
