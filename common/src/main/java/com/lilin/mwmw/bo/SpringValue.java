package com.lilin.mwmw.bo;


import org.springframework.beans.factory.annotation.Value;

public class SpringValue {


    /**
     * @Value 可以是 基本类型
     *              SpEL表达式 #{}
     *                 配置文件中的值  ${}
     */

    @Value("lilin")
    private String name;
    @Value("#{30-2}")
    private String age;

    @Value("${spring.application.name}")
    private String properties;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getProperties() {
        return properties;
    }

    public void setProperties(String properties) {
        this.properties = properties;
    }

    @Override
    public String toString() {
        return "SpringValue{" +
                "name='" + name + '\'' +
                ", age='" + age + '\'' +
                ", properties='" + properties + '\'' +
                '}';
    }
}
