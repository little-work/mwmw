package com.lilin.mwmw.initbean;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.HashMap;
import java.util.Map;


/**
 * 通过注解的方式初始化这个类
 */
public class AnnotationInit {

   public  Map<String,String> map=new HashMap<>();


    public AnnotationInit(){
        System.out.println("AnnotationInit。。。constructor");
    }


    /**
     * Bean创建完成 并赋好值 调用初始化方法
     */
    @PostConstruct
    public void init(){
        map.put("eqw","dwewq");
        System.out.println("AnnotationInit...@PostConstruct");
    }

    /**
     * 容器关闭的时候
     */
    @PreDestroy
    public void destory(){
        System.out.println("AnnotationInit....@PreDestroy");
    }

}
