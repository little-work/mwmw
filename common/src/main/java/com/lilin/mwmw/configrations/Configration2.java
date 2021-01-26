package com.lilin.mwmw.configrations;


import com.lilin.mwmw.bo.Login;
import com.lilin.mwmw.bo.User;
import com.lilin.mwmw.initbean.AnnotationInit;
import com.lilin.mwmw.initbean.InitBean;
import com.lilin.mwmw.postprocess.MyBeanPostProcess;
import com.lilin.mwmw.bo.SpringValue;
import org.springframework.context.annotation.*;

import javax.annotation.Resource;

//@Profile("test")  //可以加载类级别的  这个类中所有的注解可以根据环境控制
@Configuration
//@ComponentScan("com.lilin.mwmw.bo")
//加载外部配置文件  保存到运行的环境变量中
//@PropertySource(value = {"classpath:/application-dev.properties"})
public class Configration2 {

    //@Qualifier("initBean")
    //@Autowired    //spring注解

    /*@Resource     //java注解
    private InitBean initBean;*/


    //@Profile("test")   //添加了Profile注解的Bean  Spring容器启动的时候时不会添加到容器中的  除非添加了default默认值
    @Bean
    public AnnotationInit getAnnotationInit() {
        return new AnnotationInit();
    }

    @Bean
    public MyBeanPostProcess getMyPostProcess() {
        return new MyBeanPostProcess();
    }

    @Bean
    public SpringValue getSpringValue() {
        return new SpringValue();
    }


    @Bean
    public User user() {
        User user = new User();
        user.setUsername("lilin");
        user.setAge(28);
        return user;
    }

    @Bean
    public User user22222() {
        User user = new User();
        user.setUsername("yjm");
        user.setAge(28);
        return user;
    }

    /**
     * 可以看出login需要注入user但是上面有两个user  那么根据ID注入 方法名就是ID
     * @param user
     * @return
     */
    @Bean
    public Login login(User user /*或者 User user22222*/) {
        Login login = new Login();
        login.setUser(user);
        return login;
    }

}
