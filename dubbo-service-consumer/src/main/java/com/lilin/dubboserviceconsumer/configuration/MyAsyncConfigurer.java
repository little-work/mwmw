package com.lilin.dubboserviceconsumer.configuration;


import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.concurrent.*;

@Configuration
//@EnableAsync
public class MyAsyncConfigurer implements AsyncConfigurer {


    private static ExecutorService pool;

    @Override
    public Executor getAsyncExecutor() {
        return pool = new ThreadPoolExecutor(1, 2, 3000,
                TimeUnit.MILLISECONDS,
                new ArrayBlockingQueue<Runnable>(5),  //有界的任务队列
                new ThreadFactory() {
                    @Override
                    public Thread newThread(Runnable r) {
                        System.out.println("自定义线程工厂" + r.hashCode() + "创建");
                        //线程命名
                        Thread th = new Thread(r, "threadPool" + r.hashCode());
                        return th;
                    }
                },
                new RejectedExecutionHandler() {
                    @Override
                    public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
                        System.out.println(r.toString() + "执行了拒绝策略");
                    }
                });
    }

    @Override
    public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
        return new CustomAsyncExceptionHandler();
    }

    /**
     * 处理异步方法中未捕获的异常
     */
    class CustomAsyncExceptionHandler implements AsyncUncaughtExceptionHandler {

        @Override
        public void handleUncaughtException(Throwable throwable, Method method, Object... obj) {

            System.out.println("Exception message - " + throwable.getMessage());
            System.out.println("Method name - " + method.getName());
            System.out.println("Parameter values - " + Arrays.toString(obj));
            // do something...

        }

    }
}
