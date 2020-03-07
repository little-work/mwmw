package com.lilin.mwmw.configrations;


import com.lilin.mwmw.applicationContextAware.SpringApplicationContext;
import com.lilin.mwmw.postprocess.PostBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan("com.lilin.mwmw.postprocess")
public class ConfigrationPostProcessor {


    @Bean("springApplicationContext")
    public SpringApplicationContext getContext() {
        return new SpringApplicationContext();
    }

    /*@Bean(value = "postBean",initMethod = "init")
    public PostBean getPostBean(){
        return new PostBean();
    }*/

}
