package com.lilin.mynetty.IO.sysBlockIO;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class SysBlockIOServer {

    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(8080);
            ServerSocketThreadPool pool=new ServerSocketThreadPool(5,10);
            while (true){
                Socket socket=serverSocket.accept();
                pool.execute(new SocketHander(socket));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}

class ServerSocketThreadPool {

    private ExecutorService executor;

    public ServerSocketThreadPool(int core,int max) {
        this.executor = new ThreadPoolExecutor(core, max, 60L,
                TimeUnit.SECONDS, new LinkedBlockingQueue<>());
    }

    public void execute(Runnable task) {
        executor.execute(task);
    }
}