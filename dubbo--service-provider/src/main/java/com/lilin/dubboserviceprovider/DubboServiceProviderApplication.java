package com.lilin.dubboserviceprovider;

import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableDubbo
//如果需要xml配置dubbo
//@ImportResource(locations = "classpath:provider.xml")
@MapperScan(basePackages ="com.lilin.dubboserviceprovider.mybatis.mapper")
public class DubboServiceProviderApplication {

    public static void main(String[] args) {
        SpringApplication.run(DubboServiceProviderApplication.class, args);
    }

}
