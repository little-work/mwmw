package com.lilin;

import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;


/**
 * 事件监听器
 * 1、 写一个事件监听器  要是Applica及其子类tionEvent
 * 2、把这个监听器加到spring容器中
 * 3、只要容器中有相关是事件发布  就能监听这个事件：比如
 *              ContextRefreshedEvent  容器刷新完成所有的bean创建完成  就会发布这个事件
 *              ContextClosedEvent 关闭容器会发布这个事件
 *
 */
public class MyApplicationListener implements ApplicationListener {

    @Override
    public void onApplicationEvent(ApplicationEvent event) {

        System.out.println("收到spring容器中发布的事件"+event.getSource());
    }
}
