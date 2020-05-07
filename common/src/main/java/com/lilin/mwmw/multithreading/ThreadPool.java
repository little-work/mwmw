package com.lilin.mwmw.multithreading;

import lombok.Data;

import java.awt.image.SinglePixelPackedSampleModel;
import java.util.concurrent.*;

@Data
public class ThreadPool implements Runnable, Comparable {


    private int priority;


    private static ExecutorService pool;


    public ThreadPool() {
    }

    public ThreadPool(int priority) {
        this.priority = priority;
    }

    public static void main(String[] args) {

        /** 直接提交队列:
         * 可以看到，当任务队列为SynchronousQueue，创建的线程数大于maximumPoolSize时，
         * 直接执行了拒绝策略抛出异常。使用SynchronousQueue队列，提交的任务不会被保存，
         * 总是会马上提交执行。如果用于执行任务的线程数量小于maximumPoolSize，
         * 则尝试创建新的进程，如果达到maximumPoolSize设置的最大值，则根据你设置的handler执行拒绝策略。
         * 因此这种方式你提交的任务不会被缓存起来，而是会被马上执行，在这种情况下，你需要对你程序的并发量有个准确的评估，
         * 才能设置合适的maximumPoolSize数量，否则很容易就会执行拒绝策略
         *
         *
         * 有界的任务队列:
         * 若等待队列已满，即超过ArrayBlockingQueue初始化的容量，则继续创建线程，
         * 直到线程数量达到maximumPoolSize设置的最大线程数量，若大于maximumPoolSize，则执行拒绝策略。
         *
         *
         * 无界的任务队列:
         * 使用无界任务队列，线程池的任务队列可以无限制的添加新的任务，
         * 而线程池创建的最大线程数量就是你corePoolSize设置的数量，
         * 也就是说在这种情况下maximumPoolSize这个参数是无效的，哪怕你的任务队列中缓存了很多未执行的任务，
         * 当线程池的线程数达到corePoolSize后，就不会再增加了；若后续有新的任务加入，则直接进入队列等待，
         * 当使用这种任务队列模式时，一定要注意你任务提交与处理之间的协调与控制，
         * 不然会出现队列中的任务由于无法及时处理导致一直增长，直到最后资源耗尽的问题。
         *
         * 优先任务队列：
         * 优先任务队列通过PriorityBlockingQueue实现
         */
        //队列展示
        /*pool = new ThreadPoolExecutor(1, 2, 3000,
                TimeUnit.MILLISECONDS,
                //new SynchronousQueue<Runnable>(),   //直接提交队列
                //new ArrayBlockingQueue<Runnable>(5),  //有界的任务队列
                //new LinkedBlockingQueue<Runnable>(),   //无界的任务队列
                new PriorityBlockingQueue<Runnable>(),    //优先任务队列
                Executors.defaultThreadFactory(),
                new ThreadPoolExecutor.AbortPolicy());*/



        /**
         *  自定义拒绝策略(RejectedExecutionHandler)
         *
         *  自定义线程工厂（线程池中线程就是通过ThreadPoolExecutor中的ThreadFactory，线程工厂创建的）
         */
        //自定展示
        //ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(4);
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




        for (int i = 0; i < 20; i++) {
            //pool.execute(new ThreadPool());
            pool.execute(new ThreadPool(i));
        }

    }

    //当前对象和其他对象做比较，当前优先级大就返回-1，优先级小就返回1,值越小优先级越高
    @Override
    public int compareTo(Object o) {
        if (o instanceof ThreadPool) {
            ThreadPool other = (ThreadPool) o;
            return this.priority > other.priority ? -1 : 1;
        }
        return 0;
    }

    @Override
    public void run() {
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("priority:" + this.priority + ",ThreadName:" + Thread.currentThread().getName());
    }

}
