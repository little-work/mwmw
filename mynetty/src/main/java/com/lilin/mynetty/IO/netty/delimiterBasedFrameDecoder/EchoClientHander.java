package com.lilin.mynetty.IO.netty.delimiterBasedFrameDecoder;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

public class EchoClientHander extends ChannelHandlerAdapter {

    private int count=0;
    static final String ECHO_REQ="I am client.$_";



    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        for(int i=0;i<50;i++){
            ctx.writeAndFlush(Unpooled.copiedBuffer(ECHO_REQ.getBytes()));
        }
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("接受服务器返回消息"+(++count)+"次，内容是："+msg);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
        //释放资源
        ctx.close();
    }
}
