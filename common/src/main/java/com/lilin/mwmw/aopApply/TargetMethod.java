package com.lilin.mwmw.aopApply;

import org.springframework.stereotype.Component;

@Component
public class TargetMethod {


    public String execute() {
        System.out.println("目标方法执行了");
        return "方法返回了";
    }
}
