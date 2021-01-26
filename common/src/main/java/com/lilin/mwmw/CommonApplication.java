package com.lilin.mwmw;

import com.lilin.mwmw.springMVC.MyDispatcherServlet;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
//@SpringBootApplication
@MapperScan("com.lilin.mwmw.mapper")   //开启mybatis
//@EnableTransactionManagement
public class CommonApplication {

    public static void main(String[] args) {
        SpringApplication.run(CommonApplication.class, args);
    }


    /*@Bean(initMethod = "init")
    public MyDispatcherServlet getMyDispatcherServlet(){
        return new MyDispatcherServlet();
    }*/




}
