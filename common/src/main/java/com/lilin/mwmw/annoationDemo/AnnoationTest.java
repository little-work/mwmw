package com.lilin.mwmw.annoationDemo;

import java.lang.reflect.Field;

public class AnnoationTest {

    public static void main(String[] args) throws IllegalAccessException {
        Person p=new Person();
        p.setAge(23);
        p.setPassword("2432jj423432");
        p.setUserName("eqwe");


        Class<?> clazz = p.getClass();
        //获取每一个字段
        Field[] fields = clazz.getDeclaredFields();
        //遍历字段
        for (Field field:fields){
            MyAnnoation myAnnoation=field.getAnnotation(MyAnnoation.class);
            if(myAnnoation!=null){
                field.setAccessible(true);//设置属性可访问(取消权限，暴力访问  )
                if("class java.lang.String".equals(field.getGenericType().toString())){
                    String value = (String)field.get(p);
                    if(value != null && ((value.length() > myAnnoation.max()) ||
                            value.length() < myAnnoation.min())){
                        System.out.println(myAnnoation.describe());

                    }
                }
            }
        }



    }
}
