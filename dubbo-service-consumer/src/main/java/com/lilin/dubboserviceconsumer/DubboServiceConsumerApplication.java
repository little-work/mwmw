package com.lilin.dubboserviceconsumer;

import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;
import com.googlecode.jsonrpc4j.spring.AutoJsonRpcClientProxyCreator;
import com.googlecode.jsonrpc4j.spring.AutoJsonRpcServiceImpl;
import com.googlecode.jsonrpc4j.spring.AutoJsonRpcServiceImplExporter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.Date;

@SpringBootApplication
@EnableDubbo
@EnableScheduling
@EnableAsync
public class DubboServiceConsumerApplication {

    public static void main(String[] args) {
        SpringApplication.run(DubboServiceConsumerApplication.class, args);
    }


    //使用JDK自带的定时任务调度  展开下面的注解和配置@EnableScheduling
    //@Scheduled(cron = "0/5 * * * * *")
    public void cron() {
        System.out.println("task1");
        System.out.println(new Date());
    }

    //@Scheduled(cron = "0/5 * * * * *")
    public void cron2() {
        System.out.println("task2");
        cron();
    }

    @Bean
    public static AutoJsonRpcServiceImplExporter autoJsonRpcServiceImplExporter(){
        return new AutoJsonRpcServiceImplExporter();
    }
}
