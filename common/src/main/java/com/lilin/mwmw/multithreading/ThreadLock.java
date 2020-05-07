package com.lilin.mwmw.multithreading;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ThreadLock {


    //通过构造函数为我们创建公平锁
    //Lock lock2 = new ReentrantLock(true);
    //通过构造方法  为我们默认创建的是非公平锁
    Lock lock = new ReentrantLock();
    //排他锁
    ReentrantReadWriteLock lock2 = new ReentrantReadWriteLock();

    private ArrayList<Integer> arrayList = new ArrayList<Integer>();

    public static void main(String[] args) throws Exception {
        ThreadLock threadLock = new ThreadLock();

        /**
         * 普通lock锁
         */
        /*new Thread(() -> {
            threadLock.deqd(Thread.currentThread());
        }).start();
        new Thread(() -> {
            try {
                threadLock.fas(Thread.currentThread());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();*/

        /**
         * 读写锁 测试
         */
        /*new Thread(() -> {
            threadLock.uoo(Thread.currentThread());
            //threadLock.jjk(Thread.currentThread());
        }).start();
        new Thread(() -> {
            //threadLock.uoo(Thread.currentThread());
            threadLock.jjk(Thread.currentThread());
        }).start();*/

        /**
         * lockInterruptibly测试
         */
        final Lock lock = new ReentrantLock();
        lock.lock();
        Thread t = new Thread(() -> {
            System.out.println("正在获取锁");
            //lock.lock();  //执着的尝试获取锁，如果执行中断方法  还是在等待获取锁
            try {
                /**
                 * 允许在等待时由其它线程调用等待线程的Thread.interrupt方法来中断等待线程的等待而直接返回，
                 * 这时不用获取锁，而会抛出一个InterruptedException。
                 * ReentrantLock.lock方法不允许Thread.interrupt中断,
                 * 即使检测到Thread.isInterrupted,一样会继续尝试获取锁，失败则继续休眠。
                 * 只是在最后获取锁成功后再把当前线程置为interrupted状态,然后再中断线程。
                 *
                 */
                lock.lockInterruptibly(); //如果在获取锁期间执行了中断方法 那么会抛异常
            } catch (InterruptedException e) {
                System.out.println(Thread.currentThread().getName() + " interrupted.");
            }
            System.out.println(Thread.currentThread().getName() + " interrupted.");
        }, "child thread -1");

        System.out.println("启动子线程");
        t.start();
        Thread.sleep(1000);
        System.out.println("打断子线程");
        t.interrupt();
        System.out.println("主线程睡眠10000");
        Thread.sleep(10000);
        System.out.println(t.isInterrupted());
    }

    /**
     * 普通锁
     *
     * @param thread
     */
    public void deqd(Thread thread) {
        lock.lock();
        try {
            System.out.println(thread.getName() + "得到了锁");
            for (int i = 0; i < 5; i++) {
                arrayList.add(i);
            }
            Thread.sleep(2000);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
            System.out.println(thread.getName() + "释放了锁!");
        }
    }

    /**
     * 尝试锁
     *
     * @param thread
     * @throws InterruptedException
     */
    public void fas(Thread thread) throws InterruptedException {
        if (lock.tryLock(3000, TimeUnit.MILLISECONDS)) {
            try {
                System.out.println(thread.getName() + "得到了锁");
                for (int i = 0; i < 5; i++) {
                    arrayList.add(i);
                }
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } catch (Exception e) {
                // TODO: handle exception
            } finally {
                System.out.println(thread.getName() + "释放了锁");
                lock.unlock();
            }
        } else {
            System.out.println(thread.getName() + "获取锁失败");
        }
    }


    /**
     * 读读共享  同时进行
     *
     * @param thread
     */
    public void uoo(Thread thread) {
        try {
            lock2.readLock().lock();
            System.out.println(Thread.currentThread().getName() + " start");
            Thread.sleep(2000);
            System.out.println(Thread.currentThread().getName() + " end");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock2.readLock().unlock();
        }
    }

    /**
     * 写写互斥
     */
    public void jjk(Thread thread) {
        try {
            lock2.writeLock().lock();
            System.out.println(Thread.currentThread().getName() + " start");
            Thread.sleep(2000);
            System.out.println(Thread.currentThread().getName() + " end");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock2.writeLock().unlock();
        }
    }
}
