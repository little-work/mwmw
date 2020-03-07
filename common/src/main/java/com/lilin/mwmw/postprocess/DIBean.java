package com.lilin.mwmw.postprocess;

import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

@Component("dibean")
//控制bean的加载顺序
//@DependsOn(value = "postBean")
public class DIBean {


    private String protey= "IDBean";


    {
        System.out.println("dibean...代码块::::"+protey);

    }

    public DIBean() {
        System.out.println("IDBean..init");
    }

    @Override
    public String toString() {
        return "DIBean{" +
                "protey='" + protey + '\'' +
                '}';
    }

    public String getProtey() {
        return protey;
    }

    public void setProtey(String protey) {
        this.protey = protey;
    }
}
