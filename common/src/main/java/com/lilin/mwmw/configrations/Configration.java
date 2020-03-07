package com.lilin.mwmw.configrations;

import com.lilin.mwmw.FactoryBean.MyFactoryBean;
import com.lilin.mwmw.bo.User;
import com.lilin.mwmw.conditionals.MyConditional;
import com.lilin.mwmw.filter.MyTypeFilter;
import org.springframework.context.annotation.*;

//@Configuration
//@Import({Login.class})   //要导入到容器中组件  容器中就会自动注册这个组件  id默认是全类名
//@Import({MySelector.class})    //要导入到容器中组件  自定义选择器  里面定义类的全类名
//@Import({MyImportBeanDefinitionRegisiter.class})   //手动注册Bena到IOC容器中
//@Conditional(MyConditional.class)   //放在类上面上面的话  控制整个Bean的创建
//@ComponentScan
@ComponentScans(value = {
        @ComponentScan(basePackages = "com.lilin.mwmw",useDefaultFilters = false,includeFilters = {
                //@ComponentScan.Filter(type = FilterType.ANNOTATION,classes = {Controller.class}),
                @ComponentScan.Filter(type = FilterType.CUSTOM,classes = MyTypeFilter.class)
        })
})
public class Configration {

   /* @Bean("da")
   // @Lazy   //容器启动不会创建对象而是第一次获取的时候回创建一个对象
    @Scope("prototype")    //IOC容器并不会在启动的时候创建对象 而是每次获取的时候创建一个对象
    //@Scope("singleton")  //IOC容器启动的时候就创建一个对象放到容器中 以后每次使用就从容器中拿一个
    public UserServiceConsumeImpl UserServiceConsumeImpl(){
        return new UserServiceConsumeImpl();
    }*/

    @Conditional(MyConditional.class)   //放在方法上面的话  控制这个Bean的创建
    @Bean("user01")
    public User user01(){
        User user=new User();
        user.setUsername("lilin");
        return user;
    }

    /**
     * 控制Bean的生命周期  单实例Bean是在容器创建的时候帮我们创建这个Bean  容器关闭销毁这个Bean， 多实例的时候
     * 是在我们获取Bean 的时候创建Bean和init  容器关闭不会帮我们销毁Bean
     *
     * @return
     */
    @Bean(value = "user02",initMethod = "init",destroyMethod = "destory")
    public User user02(){
        User user=new User();
        user.setUsername("yejiamei");
        return user;
    }

    /**
     * 通过Spring  FactoryBean注册Bean到IOC容器中
     * @return
     */
    @Bean
    public MyFactoryBean myFactoryBean(){
        return new MyFactoryBean();
    }
}
