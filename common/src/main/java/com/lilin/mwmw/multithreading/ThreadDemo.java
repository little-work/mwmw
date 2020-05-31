package com.lilin.mwmw.multithreading;


/**
 * 启动3个线程打印递增的数字, 线程1先打印1,2,3,4,5
 * 然后是线程2打印6,7,8,9,10然后是线程3打印11,12,13,14,15.
 * 接着再由线程1打印16,17,18,19,20....以此类推, 直到打印到75
 */
public class ThreadDemo implements Runnable {


    // static修饰 ，为了共享,(static不能和this连用)
    private static volatile int num = 1;// 要打印的数
    private static volatile int count = 0;// 计数 三次一轮回
    private int n;// 表示线程的编号
    private Object object;// 定义锁对象
    private int max;// 要打印的最大数
    private int threadsNum;// 线程个数
    private int times;// 每个线程每回打印次数


    public ThreadDemo(Object object, int n, int max, int threadsNum, int times) {
        this.object = object;
        this.n = n;
        this.max = max;
        this.threadsNum = threadsNum;
        this.times = times;
    }

    @Override
    public void run() {
        synchronized (object) {// object 表示同步监视器,是同一个同步对象
            System.out.println(Thread.currentThread().getName()+"获得了锁");
            while (num <= max) {
                /**
                 * 线程1执行获取了锁  执行 notifyAll montior对象中的_WaitSet列表中没有
                 * ObjectWaiter对象
                 * 1、如果当前_WaitSet为空，即没有正在等待的线程，则直接返回；
                 * 2、通过for循环取出_WaitSet的ObjectWaiter节点，并根据不同策略，加入到_EntryList或则进行自旋操作。
                 */
                object.notifyAll();
                try {
                    while (count % threadsNum != (n - 1)) {// 找出下一个线程 不正确的线程等待
                        System.out.println(Thread.currentThread().getName()+"等待被唤醒");
                        /**
                         *   object.wait()方法最终通过ObjectMonitor的 wait(jlong millis, bool interruptable, TRAPS)实现
                         *
                         * 1、将当前线程封装成ObjectWaiter对象node
                         *
                         * 2、通过ObjectMonitor::AddWaiter方法将node添加到_WaitSet列表中
                         *
                         * 3、通过ObjectMonitor::exit方法释放当前的ObjectMonitor对象，这样其它竞争线程就可以获取该ObjectMonitor对象
                         */
                        object.wait();// 此线程让出资源，等待,(sleep()释放资源不释放锁，而wait()释放资源释放锁；)
                    }
                    System.out.print(Thread.currentThread().getName() + "\t");//输出线程名字
                    while (num <= max) {
                        System.out.print(num++);
                        if ((num - 1) % times == 0) {// 打印了相应次数之后
                            count++;// 计数器+1,下次循环切换线程
                            break;//跳出当前while循环
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                System.out.println();
            }
            System.exit(0);// 终止程序，(停止寻找锁对象)
        }
        // (同步方法的同步监听器其实的是 this)
    }

    public static void main(String[] args) {
        Object object = new Object();// 创建一个锁对象
        int max = 75;// 要打印的最大数
        int threadsNum = 3;// 线程个数
        int times = 5;// 每个线程每回打印次数

        for (int i = 1; i <= threadsNum; i++) {
            new Thread(new ThreadDemo(object, i, max, threadsNum, times),
                    "线程" + i).start();// 开启三个线程，都处理这个对象，并给线程添加序号和命名
        }

    }
}
