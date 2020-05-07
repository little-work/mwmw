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
            /**
             * 这行代码其实就是调用openjdk中的bind0的本地native方法
             */
            ServerSocket serverSocket = new ServerSocket(8080);
            ServerSocketThreadPool pool=new ServerSocketThreadPool(5,10);
            while (true){
                /**
                 * 单线程模式下面，接受一个请求后并处理
                 * 如果处理涉及io那么会阻塞 就不能处理其他并发请求
                 *
                 * 阻塞就是阻塞正在accept接受新的客户端中
                 */
                Socket socket=serverSocket.accept();
                /**
                 * 只是一个客户端请求过来 开启多线程进行处理请求，但是多个请求并发过来
                 * 就只能等待 因为一次只能accept一个客户请求
                 */
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