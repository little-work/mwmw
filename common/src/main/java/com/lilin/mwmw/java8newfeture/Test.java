package com.lilin.mwmw.java8newfeture;

import java.util.ArrayList;
import java.util.List;

public class Test {


    public static void main(String[] args) {
        /**
         * 必须是@FunctionalInterface函数  只能有一个方法
         *
         * 首先FuncitonInterface确定类型，在引用这个类型的new关键去创建这个类型
         * 调用接口的方法返回这个类型  实际是new T();
         *
         *
         */
//        FuncitonInterface<Person> f=Person::new;
//        Person p=f.getObject();
//
//        FuncitonInterface<User> f2=()->new User();
//        User user=f2.getObject();


        /**
         * 方法引用-引用静态方法
         */
//        RefInterface refInterface=RefClass::jian;
//        System.out.println(refInterface.jian(2,4));


        /**
         * 普通方法引用
         */
//        FuncitonInterface<RefClass> f3=RefClass::new;
//        RefClass refClass=f3.getObject();
       /* RefClass refClass=new RefClass();
        RefInterface refInterface2=refClass::add;
        System.out.println(refInterface2.add(3,7));*/


        /**
         *
         */
        //adddddd(refClass::add);



        voidfwfqw(()->{
            Person p=new Person();
            p.setName("lilin");
            return p;
        });
    }


    public static int adddddd(RefInterface refInterface){
        return refInterface.add(2,5);
    }



    public static void voidfwfqw(FuncitonInterface<?> obj ){
        System.out.println(obj.getObject());
    }

}
