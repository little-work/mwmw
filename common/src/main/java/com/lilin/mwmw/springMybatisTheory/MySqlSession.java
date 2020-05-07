package com.lilin.mwmw.springMybatisTheory;

import org.apache.ibatis.annotations.Select;

import java.lang.reflect.Proxy;

public class MySqlSession {

    public static Object getObject(Class clazz) {

        Class[] clazzs = new Class[]{clazz};

        Object obj = Proxy.newProxyInstance(MySqlSession.class.getClassLoader(), clazzs, (proxy, method, args) -> {
            Select annotation = method.getAnnotation(Select.class);
            String sql = annotation.value()[0];
            System.out.println(sql);
            return "Proxy.return";
        });
        return obj;
    }
}
