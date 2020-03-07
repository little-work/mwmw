package com.lilin.mwmw.FactoryBean;

import com.lilin.mwmw.service.impl.UserServiceConsumeImpl;
import org.springframework.beans.factory.FactoryBean;

/**
 * MyFactoryBean
 * a.getBean(“MyFactoryBean”);  获取的是他返回的在spring中的Bean
 * a.getBean(“$MyFactoryBean”);  获取的是他自己本身
 */
public class MyFactoryBean implements FactoryBean<UserServiceConsumeImpl> {
    @Override
    public UserServiceConsumeImpl getObject() throws Exception {
        return new UserServiceConsumeImpl();
    }

    @Override
    public Class<?> getObjectType() {
        return UserServiceConsumeImpl.class;
    }

    /**
     * true返回单例
     * false返回多实例  其实是调用上面的getObject()方法
     * @return
     */
    @Override
    public boolean isSingleton() {
        return false;
    }
}
