package com.lilin.mynetty.IO.AIO;


import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.util.concurrent.CountDownLatch;

@Slf4j
public class Server implements Runnable {


    private int port;
    CountDownLatch latch;
    AsynchronousServerSocketChannel asynchronousServerSocketChannel;


    public Server(int port) {
        this.port = port;
        try {
            asynchronousServerSocketChannel = AsynchronousServerSocketChannel.open();
            asynchronousServerSocketChannel.bind(new InetSocketAddress(port));
            log.info("服务器启动占用" + port + "端口");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        latch = new CountDownLatch(1);
        doAccept();
    }

    private void doAccept() {
        log.info("开始接受客户端请求");
        asynchronousServerSocketChannel.accept(this, new AcceptCompletionHandler());
    }

    /**
     * 启动服务端，采用异步非阻塞模式
     *
     * @param args
     */
    public static void main(String[] args) {
        int port = 8010;
        new Thread(new Server(8010), "AIO-AsyncTimeServerHandler-001").start();
    }
}
