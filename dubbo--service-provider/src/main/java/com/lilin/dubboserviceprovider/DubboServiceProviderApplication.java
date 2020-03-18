package com.lilin.dubboserviceprovider;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

@SpringBootApplication
@MapperScan(basePackages ="com.lilin.dubboserviceprovider.mybatis.mapper")
public class DubboServiceProviderApplication {

    public static void main(String[] args) {
        SpringApplication.run(DubboServiceProviderApplication.class, args);
    }

}
