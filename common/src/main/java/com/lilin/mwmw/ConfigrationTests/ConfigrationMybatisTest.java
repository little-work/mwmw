package com.lilin.mwmw.ConfigrationTests;

import com.lilin.mwmw.bo.Login;
import com.lilin.mwmw.bo.SpringValue;
import com.lilin.mwmw.bo.User;
import com.lilin.mwmw.configrations.Configration2;
import com.lilin.mwmw.configrations.ConfigrationTX;
import com.lilin.mwmw.configrations.ConfigurationMybatis;
import com.lilin.mwmw.springMybatisTheory.SelectService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class ConfigrationMybatisTest {



    public static void main(String[] args) {

        /*AnnotationConfigApplicationContext a = new AnnotationConfigApplicationContext(ConfigurationMybatis.class);

        SelectService selectService=a.getBean(SelectService.class);
        selectService.query();*/
        AnnotationConfigApplicationContext a =
                new AnnotationConfigApplicationContext(Configration2.class);

        Login sv = a.getBean(Login.class);
        //User user=a.getBean(User.class);

        System.out.println(sv.getUser().getUsername());

    }
}
