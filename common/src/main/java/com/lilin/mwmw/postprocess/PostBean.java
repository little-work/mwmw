package com.lilin.mwmw.postprocess;


import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("postBean")
public class PostBean implements InitializingBean {

    public DIBean diBean;

    private String str="postBeanProperty";

    {
        System.out.println("PostBean。。。代码块:::"+str);
    }

    @Autowired
    public PostBean(DIBean diBean) {
        System.out.println("开始属性注入");
        this.diBean = diBean;
        System.out.println("PostBean。parmerconstructor。。init");
    }

    /*public PostBean() {
        System.out.println("PostBean。。noconstructor。。init");
    }*/

    @Override
    public String toString() {
        return "PostBean{" +
                "str='" + str + '\'' +
                '}';
    }

    public String getDIBeanStr(){
        return diBean.getProtey();
    }

    public String getStr() {
        return str;
    }

    public void setStr(String str) {
        this.str = str;
    }

    public void init(){
        System.out.println("post_initMethod");
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("postBean......afterPropertiesSet");
    }
}
