package com.lilin.mynetty.IO.NIO;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

@Slf4j
public class NIOClient implements Runnable {//服务器端的ip
    private String host;
    //服务器端的端口号
    private int port;
    //多路服用选择器
    private Selector selector;

    private SocketChannel socketChannel;

    private volatile boolean stop;


    /**
     * 构造方法初始化连接
     * @param host
     * @param port
     */
    public NIOClient(String host, int port){
        this.host = host == null ? "127.0.0.1": host;
        this.port = port;
        try {
            //初始化一个Selector，工厂方法
            selector = Selector.open();
            //初始化一个SocketChannel，工厂方法
            socketChannel = SocketChannel.open();
            //设置非阻塞模式
            socketChannel.configureBlocking(false);
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }

    }


    @Override
    public void run() {
        try {
            log.info("开始连接服务器");
            doConnect();
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
        while (!stop){
            try {
                selector.select(1000);
                Set<SelectionKey> keys =  selector.selectedKeys();
                Iterator<SelectionKey> its =keys.iterator();
                SelectionKey key = null;
                while (its.hasNext()){
                    key = its.next();
                    its.remove();
                    try {
                        handle(key);
                    }catch (Exception e){
                        if(key != null){
                            key.cancel();
                            if(key.channel() != null){
                                key.channel().close();
                            }
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                System.exit(1);
            }
        }
        //释放所有与该多路复用器selector关联的资源
        if(selector != null){
            try {
                selector.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 客户端处理
     * @param key
     * @throws IOException
     */
    public  void handle(SelectionKey key) throws IOException {
        if(key.isValid()){
            SocketChannel sc = (SocketChannel) key.channel();
            if(key.isConnectable()){
                //如果连接成功，监听读请求
                if(sc.finishConnect()){
                    sc.register(this.selector, SelectionKey.OP_READ);
                    //像服务端发送数据
                    log.info("连接成功，开始向服务器发送消息");
                    doWrite(sc);
                }else{
                    System.exit(1);
                }
            }
            //监听到读请求，从服务器端接受数据
            if(key.isReadable()){
                ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
                int readBytes = sc.read(byteBuffer);
                if(readBytes > 0){
                    byteBuffer.flip();
                    byte []  bytes = new byte[byteBuffer.remaining()];
                    byteBuffer.get(bytes);
                    String body = new String(bytes,"UTF-8");
                    log.info("收到服务器的消息："+ body);
                    stop = true;
                }else if(readBytes < 0){
                    key.cancel();
                    sc.close();
                }
            }
        }
    }

    /**
     * 首先尝试连接服务端
     * @throws IOException
     */
    public void doConnect() throws IOException {
        //如果连接成功，像多路复用器selector监听读请求
        if(socketChannel.connect(new InetSocketAddress(this.host, this.port))){
            socketChannel.register(selector, SelectionKey.OP_READ);
            //执行写操作，像服务器端发送数据
            //doWrite(socketChannel);
        }else {
            //监听连接请求
            socketChannel.register(selector, SelectionKey.OP_CONNECT);
        }
    }

    /**
     * 请求服务器
     * @param sc
     * @throws IOException
     */
    public static void doWrite(SocketChannel sc) throws IOException {
        //构造请求消息体
        byte [] bytes = "你是我是客户端".getBytes();
        //构造ByteBuffer
        ByteBuffer write = ByteBuffer.allocate(bytes.length);
        //将消息体写入发送缓冲区
        write.put(bytes);
        write.flip();
        //调用channel的发送方法异步发送
        sc.write(write);
        //通过hasRemaining方法对发送结果进行判断，如果消息全部发送成功，则返回true
//        if(!write.hasRemaining()){
//           log.info("send order 2 server successd");
//        }
    }

}
