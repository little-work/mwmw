package com.lilin.mwmw.springMybatisTheory;

import org.springframework.beans.factory.FactoryBean;


/**
 * <bean id="cityMapper" class="org.mabatis.spring.mapper.MapperFactoryBean">
 * <property name="mapperInterface" value="com.lilin.mwmw.dao.cityMapper"></property>
 * <property name="sqlSessionFactory" ref="sqlsession"> </property>
 * </bean>
 * <p>
 * 用FactoryBean将指定包下面的mapper加入到spring容器中   注意接口不能被实例化  所以这里使用的是动态代理生成一个接口
 * mapper的代理类
 */
//@Component
public class MyMapperFactoryBean implements FactoryBean {


    private Class clazz;

    MyMapperFactoryBean(Class clazz) {
        this.clazz = clazz;
    }

    @Override
    public Object getObject() {
        return MySqlSession.getObject(clazz);
    }

    @Override
    public Class<?> getObjectType() {
        return clazz;
    }
}
