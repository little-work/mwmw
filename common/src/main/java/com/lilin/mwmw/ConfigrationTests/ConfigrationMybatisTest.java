package com.lilin.mwmw.ConfigrationTests;

import com.lilin.mwmw.configrations.ConfigurationMybatis;
import com.lilin.mwmw.springMybatisTheory.SelectService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class ConfigrationMybatisTest {



    public static void main(String[] args) {

        AnnotationConfigApplicationContext a = new AnnotationConfigApplicationContext(ConfigurationMybatis.class);

        SelectService selectService=a.getBean(SelectService.class);
        selectService.query();
    }
}
