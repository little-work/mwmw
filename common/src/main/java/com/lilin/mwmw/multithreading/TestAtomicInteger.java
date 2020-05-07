package com.lilin.mwmw.multithreading;

import lombok.extern.slf4j.Slf4j;
import sun.misc.Unsafe;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
public class TestAtomicInteger {
    /**
     * 单线程下面 count是等于200的 但是在高并发情况下面 count的值不确定
     */
    public static int count = 0;
    //private static final Unsafe unsafe = Unsafe.getUnsafe();
    /**
     * 多线程下面使用,保证原子性，因为自增有三个操作,从主内存中拷贝值到自己 的工作内存，修改
     * 然后再从工作内存中拷贝到主内存中
     */
    public static AtomicInteger atomicInteger = new AtomicInteger(0);


    private static int a = 0;
    private static int b = 0;


    public static void main(String[] args) throws InterruptedException {
        //可见性
        List<Thread> lists = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            Thread t = new Thread(() -> {
                for (int j = 0; j < 1000; j++) {
                    synchronized (lists){
                        count++;
                    }
                }
            });
            lists.add(t);
        }
        for (Thread thread : lists) {
            thread.start();
        }
        for (Thread thread : lists) {
            thread.join();
        }
        System.out.println(count);
        log.info(String.valueOf(count));
    }
}
