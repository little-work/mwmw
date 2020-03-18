package com.lilin.mwmw.configrations;


import com.lilin.mwmw.bo.Login;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConfigrationTX {



    @Bean
    @ConditionalOnMissingClass("com.lilin.mwmw.configrations.Configration")
    public Login getLogin(){
        return new Login();
    }

}
