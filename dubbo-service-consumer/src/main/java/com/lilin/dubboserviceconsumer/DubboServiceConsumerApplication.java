package com.lilin.dubboserviceconsumer;

import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableDubbo
public class DubboServiceConsumerApplication {

    public static void main(String[] args) {
        SpringApplication.run(DubboServiceConsumerApplication.class, args);
    }

}
