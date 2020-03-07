package com.lilin.mynetty.IO.netty.lineBaseFrameDecoder;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.CharsetUtil;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TimeClientHandler extends ChannelHandlerAdapter {

    private int count;


    public TimeClientHandler(){
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
        //释放资源
        ctx.close();
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
        log.info("------请求服务器开始-----");
        /**
         * 在我们创建ByteBuf对象后，它的引用计数是1，当你释放（release）引用计数对象时，它的引用计数减1，
         * 如果引用计数为0，这个引用计数对象会被释放（deallocate）,并返回对象池。
         * 当尝试访问引用计数为0的引用计数对象会抛出IllegalReferenceCountException异常：
         * 或者在该Bytebuf 在复用前需要调用retain(),将计数器置为1
         * firstMessage.retain();
         *
         */
        for(int i=0;i<50;i++){
            ctx.writeAndFlush(Unpooled.copiedBuffer(("nowTime"+System.getProperty("line.separator")).getBytes()));
        }
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
         //新增了LineBasedFrameDecoder类所以不用这么复杂了
        /*log.info("收到服务端的回复："+((ByteBuf) msg).toString(CharsetUtil.UTF_8)
                +"服务器返回次数"+(++count));*/

        String body=(String) msg;
        log.info("收到服务端的回复："+msg+";服务器返回次数:"+(++count));
    }



}
