package com.lilin.mwmw.designpattern.factory;

public class BeanFactory {


    public static <T> T getBean(Class<? extends T> clazz) {
        try {
            return (T) Class.forName(clazz.getName()).newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) {
        getBean(XmlBean.class).getBean();
    }
}
