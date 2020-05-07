package com.lilin.mynetty.IO.netty.serializeTransfer;

import com.lilin.mynetty.IO.netty.po.User;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class SerializerClientHander extends ChannelHandlerAdapter {


    private int count;

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        List<User> list=userInfo();
        for(User user:list){
            ctx.write(user);
        }
        ctx.flush();

    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("this is client receive msg【  "+ ++count +"  】times:【"+msg+"】");
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }

    private List<User> userInfo(){
        List list=new ArrayList();
        for(int i=0;i<5;i++){
            User user=new User();
            user.setAge(i);
            user.setName("lilin"+i);
            list.add(user);
        }
        return list;
    }
}
