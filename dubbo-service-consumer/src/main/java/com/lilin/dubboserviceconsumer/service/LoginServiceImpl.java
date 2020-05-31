package com.lilin.dubboserviceconsumer.service;


import com.alibaba.dubbo.config.annotation.Reference;
import com.lilin.interfaces.LoginService;
import com.lilin.interfaces.UserService;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.concurrent.*;

@Service
public class LoginServiceImpl implements LoginService {

    private static ExecutorService pool;


    @PostConstruct
    public  void init(){
        pool = new ThreadPoolExecutor(1, 2, 3000,
                TimeUnit.MILLISECONDS,
                new ArrayBlockingQueue<Runnable>(5),  //有界的任务队列
                new ThreadFactory() {
                    @Override
                    public Thread newThread(Runnable r) {
                        System.out.println("线程"+r.hashCode()+"创建");
                        //线程命名
                        Thread th = new Thread(r,"threadPool"+r.hashCode());
                        return th;
                    }
                },
                new RejectedExecutionHandler(){
                    @Override
                    public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
                        System.out.println(r.toString()+"执行了拒绝策略");
                    }
                });

    }

    /**
     *  1、服务调用5秒没有返回值，会等待超时报错  默认值是1秒
     *  2、retries重试次数，不包括第一次调用所以会调用4次，
     *     幂等【可以设置重试次数】（查询，删除，修改），非幂等【不能设置重试次数】retries=0（新增）
     *  3、version用于灰度发布，如果设置为*的话那么 就是新旧版本随机调用
     *  4、可以采用直连的方式 绕过注册中心url = "127.0.0.1:20880"
     *  5、多台服务器提供服务的时候，消费者请求服务的策略
     *      【默认random（随机），roundrobin（轮询），leastactive】
     */
    @Reference(version = "1.0.0",check = false,timeout = 1000,retries = 3,loadbalance = "roundrobin")
    private UserService userService;


    @Override
    public String getName(){
        return userService.findUser().get(0).getUsername();
    }


    @Override
    public void testSync(){
        task();
    }

    @Override
    public void testAsync(){
        pool.execute(() -> task());

    }



    @Override
    @Async
    public void task(){
        for(int i=0;i<5;i++){
            try {
                Thread.sleep(1000);
                System.out.println("当前获取的number为:"+i);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


}
