package com.lilin.mynetty.IO.netty.serializeTransfer;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

public class SerizlizerClient {

    public void connect(int port,String host) throws InterruptedException {
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap();
            b.group(group).channel(NioSocketChannel.class)
                    .option(ChannelOption.TCP_NODELAY, true)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            socketChannel.pipeline().addLast("msgpack decoder",new MsgPackDecoder())
                                    .addLast("msgpack encoder",new MsgPackEncoder())
                                    .addLast(new SerializerClientHander());
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
        new SerizlizerClient().connect(port,host);
    }
}
