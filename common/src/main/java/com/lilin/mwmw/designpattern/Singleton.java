package com.lilin.mwmw.designpattern;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Singleton {


    //private static final Singleton singleton = new Singleton();

    private static Map<String, Object> ioc = new ConcurrentHashMap<>();
    private static Singleton singleton = null;

    private Singleton() {

    }

    public static Singleton getSingleton() {
        if (singleton == null) {
            synchronized (Singleton.class) {
                singleton = new Singleton();
            }
        }
        return singleton;
    }


    /**
     * spring容器单例设计模式
     * @param beanName
     * @return
     * @throws Exception
     */
    public static Object getBean(String beanName) throws Exception {
        synchronized (ioc) {
            if (ioc.containsKey(beanName)) {
                System.out.println("直接拿"+Thread.currentThread().getName()+ioc);
                return ioc.get(beanName);
            } else {
                Object obj = Class.forName(beanName).newInstance();
                ioc.put(beanName, obj);
                System.out.println("创建bean"+Thread.currentThread().getName()+ioc);
                return obj;
            }
        }
    }


    public static void main(String[] args) {
        //singleton1 = Singleton.getSingleton();
        //singleton2 = Singleton.getSingleton();
        //System.out.println(singleton1 == singleton2);


        /*new Thread(() -> {
            System.out.println(Singleton.getSingleton());
        }).start();

        new Thread(() -> {
            System.out.println(Singleton.getSingleton());
        }).start();*/


        new Thread(() -> {
            try {
                System.out.println(Singleton.getBean("com.lilin.mwmw.designpattern.Singleton"));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();

        new Thread(() -> {
            try {
                System.out.println(Singleton.getBean("com.lilin.mwmw.designpattern.Singleton"));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }
}
