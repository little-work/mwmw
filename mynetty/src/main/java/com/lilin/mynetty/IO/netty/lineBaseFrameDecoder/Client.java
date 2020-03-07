package com.lilin.mynetty.IO.netty.lineBaseFrameDecoder;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;

public class Client {

    public void connect(int port, String host) throws InterruptedException {
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap();
            b.group(group).channel(NioSocketChannel.class)
                    .option(ChannelOption.TCP_NODELAY, true)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            /**
                             * 针对换行符\n或者\r\n就以此为结束为止，就是按行切换解码器  解决TCP粘包问题
                             */
                            socketChannel.pipeline().addLast(new LineBasedFrameDecoder(1024));
                            socketChannel.pipeline().addLast(new StringDecoder());
                            socketChannel.pipeline().addLast(new TimeClientHandler());
                        }
                    });
            //发起异步连接操作
            ChannelFuture f=b.connect(host,port);
            //等待客户端链路关闭
            f.channel().closeFuture().sync();
        } finally {
            //优雅的退出，释放NIO线程
            group.shutdownGracefully();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        int port=8080;
        String host="127.0.0.1";
        new Client().connect(port,host);
    }
}
