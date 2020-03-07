package com.lilin.mwmw.multithreading;

public class ThreadDemo {


    private ThreadLocal<Integer> threadLocal = ThreadLocal.withInitial(() -> 100);


    public int getNextNum() {
        threadLocal.set(threadLocal.get()+1);
        return threadLocal.get();
    }


    public static void main(String[] args) {
        ThreadDemo threadDemo= new ThreadDemo();
        for (int i=0;i<3;i++)
            new Thread(() -> {
                for (int j=0;j<3;j++){
                    System.out.println(Thread.currentThread().getName()+"--->"+threadDemo.getNextNum());
                }
            }).start();
    }

}
