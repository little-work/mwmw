package com.lilin.mwmw.configrations;


import com.lilin.mwmw.springMybatisTheory.MyCompanscan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan("com.lilin.mwmw.springMybatisTheory")
//自定义注解
@MyCompanscan(basePackages="com.lilin.mwmw.springMybatisTheory.mappers")
public class ConfigurationMybatis {

    /*@Autowired
    MySqlSession mySqlSession;

    @Bean
    public SelectDao getBean(){
        return  (SelectDao) mySqlSession.getObject(SelectDao.class);
    }*/
}
