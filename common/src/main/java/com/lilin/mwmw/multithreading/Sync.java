package com.lilin.mwmw.multithreading;

import org.openjdk.jol.info.ClassLayout;

public class Sync {

    static Lock l=new Lock();

    public static void main(String[] args) {
        System.out.println(Integer.toHexString(l.hashCode()));
        System.out.println(ClassLayout.parseInstance(l).toPrintable());

        synchronized (l){
            System.out.println(ClassLayout.parseInstance(l).toPrintable());
        }
    }




}

class Lock{
    private int i=2;
}
