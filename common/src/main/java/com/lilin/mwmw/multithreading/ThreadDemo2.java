package com.lilin.mwmw.multithreading;

public class ThreadDemo2 implements Runnable{

    @sun.misc.Contended("tlr")
    int q=0;

    @Override
    public void run() {
        while (q<10){
            System.out.println(Thread.currentThread().getName() +":"+ q++);
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


    public static void main(String[] args) throws InterruptedException {
        Thread t1=new Thread(new ThreadDemo2());
        Thread t2=new Thread(new ThreadDemo2());
        Thread t3=new Thread(new ThreadDemo2());

        /**
         * 无序执行
         */
        t1.start();
        t2.start();
        t3.start();

        t2.join();
        t3.join();
        t1.join();

        /**
         * 有序执行
         */
        /*t2.start();
        System.out.println(Thread.currentThread().getName());
        //Thread.sleep(5000);
        t2.join();
        t1.start();
        System.out.println(Thread.currentThread().getName());
        //Thread.sleep(5000);
        //t1.join();
        t3.start();
        System.out.println(Thread.currentThread().getName());
        //Thread.sleep(5000);
        //t3.join();*/
        System.out.println("主线程完毕");
    }
}
