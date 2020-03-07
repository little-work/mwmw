package com.lilin.mynetty.IO.netty.lineBaseFrameDecoder;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;

@Slf4j
public class TimeServerHandler extends ChannelHandlerAdapter {


    private int count;

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        /*
        新增了 LineBasedFrameDecoder类所以不用这么复杂(按行切换解码器)
        ByteBuf buf = (ByteBuf) msg;
        byte[] req = new byte[buf.readableBytes()];
        buf.readBytes(req);
        //String body = new String(req, "UTF-8").substring(0,req.length-System.getProperty("line.separator").length());
        String body = new String(req, "UTF-8");*/

        String body = (String) msg;
        log.info("接受到客户端命令是：" + body + ";计数器值；" + (++count));
        String currentTimme = "nowTime".equalsIgnoreCase(body) ?
                new Date(System.currentTimeMillis()).toString() : "bad request";
        ByteBuf resp = Unpooled.copiedBuffer((currentTimme+System.getProperty("line.separator")).getBytes());
        ctx.writeAndFlush(resp);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }
}
