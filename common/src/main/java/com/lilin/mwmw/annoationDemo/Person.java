package com.lilin.mwmw.annoationDemo;

import java.lang.reflect.Field;

public class Person {


    @MyAnnoation(max=10,min=6,describe = "请注意位数")
    private String userName;

    private String password;

    int age;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
