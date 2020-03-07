package com.lilin.mwmw.loadProperties;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
//加载项目中的配置文件，需要配合@Value注解使用
@PropertySource(value = "classpath:test.properties")
//读取配置文件中的属性 并封装成一个实体类
@ConfigurationProperties(prefix = "parmeter")
@Data
public class PropertiesBean {


   // @Value("${parmeter.key1}")
    private String key1;
    //@Value("${parmeter.key2}")
    private String key2;
    //@Value("${parmeter.key3}")
    private String key3;

}
