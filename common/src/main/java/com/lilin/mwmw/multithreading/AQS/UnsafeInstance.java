package com.lilin.mwmw.multithreading.AQS;

import sun.misc.Unsafe;

import java.lang.reflect.Field;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.LockSupport;

public class UnsafeInstance {


    private static long valueOffset = 0;
    private volatile int value=8;

    public static Unsafe getUnsafe() throws IllegalAccessException {
        Field unsafeField = Unsafe.class.getDeclaredFields()[0];
        unsafeField.setAccessible(true);
        Unsafe unsafe = (Unsafe) unsafeField.get(null);
        return unsafe;
    }


    public static void main(String[] args) throws Exception {
        UnsafeInstance unsafeInstance=new UnsafeInstance();
        Unsafe unsafe= getUnsafe();
        //valueOffset属性相当于UnsafeInstance对象中value相当于UnsafeInstance地址的偏移地址
        valueOffset = unsafe.objectFieldOffset(UnsafeInstance.class.getDeclaredField("value"));
        //从对象的指定偏移地址处读取一个int
        //System.out.println("偏移地址:"+valueOffset+"处的值为："+unsafe.getInt(unsafeInstance,valueOffset));
        //System.out.println("偏移地址:"+valueOffset+"处的值为："+unsafe.getIntVolatile(new UnsafeInstance(),valueOffset));
        //-----------------------------------------------
        System.out.println(unsafeInstance.value);
        /**
         * 第一个参数是哪个对象中的值，第二个参数是成员变量在对象内存地址中偏移值
         * 第三个参数是改成员变量在内存中的偏移值可以实际取到的值
         * 第四个参数要修改的值
         * 当第二和第三个参数一样 才会修改为要修改的值
         */
        boolean falg=unsafe.compareAndSwapInt(unsafeInstance,valueOffset,8,6);
        System.out.println(falg+":"+unsafeInstance.value);


        Thread t1=new Thread(()->{

            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("我是t1");
        });
        t1.start();
        LockSupport.park();
        Thread.sleep(1000);
        LockSupport.unpark(t1);

    }

}
