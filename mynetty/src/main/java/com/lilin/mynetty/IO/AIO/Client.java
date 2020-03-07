package com.lilin.mynetty.IO.AIO;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.util.concurrent.CountDownLatch;

@Slf4j
public class Client implements CompletionHandler<Void, Client>, Runnable {

    private AsynchronousSocketChannel client;

    private String host;

    private int port;

    private CountDownLatch latch;


    public Client(String host, int port) {
        this.host = host;
        this.port = port;
        try {
            //初始化一个AsynchronousSocketChannel
            client = AsynchronousSocketChannel.open();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        latch = new CountDownLatch(1);
        //连接服务端，并将自身作为连接成功时的回调handler
        log.info("开始连接远程服务器");
        client.connect(new InetSocketAddress(host, port), this, this);
        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 连接服务端成功时的回调
     * @param result
     * @param attachment
     */
    @Override
    public void completed(Void   result, Client attachment) {
        //请求参数
        byte [] req = "你好，我是客户端".getBytes();
        //分配写缓存区
        ByteBuffer write = ByteBuffer.allocate(req.length);
        //往写缓存区写请求body
        write.put(req);
        write.flip();
        //将缓存中的数据写到channel，同时使用匿名内部类做完成后回调
        log.info("向服务器发送消息");
        client.write(write, write, new CompletionHandler<Integer, ByteBuffer>() {
            @Override
            public void completed(Integer result, ByteBuffer byteBuffer) {
                //如果缓存数据中还有数据，接着写
                if(byteBuffer.hasRemaining()){
                    client.write(byteBuffer, byteBuffer, this);
                }else{
                    ByteBuffer readBuffer = ByteBuffer.allocate(1024);
                    //读取服务端的返回到缓存，采用匿名内部类做写完缓存后的回调handler
                    log.info("接受服务器响应消息");
                    client.read(readBuffer, readBuffer, new CompletionHandler<Integer, ByteBuffer>() {
                        /**
                         * 从缓存中读取数据，做业务处理
                         * @param result
                         * @param buffer
                         */
                        @Override
                        public  void completed(Integer result, ByteBuffer buffer) {
                            buffer.flip();
                            byte [] bytes = new byte[buffer.remaining()];
                            buffer.get(bytes);
                            String body;
                            try {
                                body =  new String(bytes, "UTF-8");
                                System.out.println("收到服务器的请求:" + body);
                                latch.countDown();
                            } catch (UnsupportedEncodingException e) {
                                e.printStackTrace();
                            }
                        }


                        /**
                         * 从缓存读取数据失败
                         * 关闭client，释放channel相关联的一切资源
                         * @param exc
                         * @param attachment
                         */
                        @Override
                        public void failed(Throwable exc, ByteBuffer attachment) {
                            try {
                                client.close();
                                latch.countDown();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                }

            }

            /**
             * 缓存写入channel失败
             * 关闭client，释放channel相关联的一切资源
             * @param exc
             * @param attachment
             */
            @Override
            public void failed(Throwable exc, ByteBuffer attachment) {
                {
                    try {
                        client.close();
                        latch.countDown();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    @Override
    public void failed(Throwable exc, Client attachment) {
        {
            try {
                client.close();
                latch.countDown();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        new Thread(new Client("127.0.0.1",8010),"dasdas").start();
    }
}
