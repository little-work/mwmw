package com.lilin.mwmw;

import com.lilin.mwmw.bo.User;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class OutOfMemoryDump {


    /**
     * JVM参数
     * -Xmx20m -Xms5m -XX:+PrintGCDetails -XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath=
     * @param args
     */
    public static void main(String[] args) {
        //OutOfMemory
        /*List<User> list=new ArrayList<>();
        while (true){
            User user=new User();
            user.setUsername(UUID.randomUUID().toString());
            list.add(user);
        }*/
        new Thread(()->{
            while (true){
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                byte[] bytes=new byte[1024*30];
            }
        }).start();


    }
}
