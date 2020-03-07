package com.lilin.mynetty.IO.AIO;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.util.Date;

@Slf4j
public class ReadCompletionHandler implements CompletionHandler<Integer, ByteBuffer> {

    private AsynchronousSocketChannel channel;

    public ReadCompletionHandler(AsynchronousSocketChannel channel){
        if(this.channel == null)
            this.channel = channel;
    }
    @Override
    public void completed(Integer result, ByteBuffer attachment) {
        attachment.flip();
        byte [] body = new byte[attachment.remaining()];
        attachment.get(body);
        try {
            String req = new String(body, "UTF-8");
            log.info("收到客户端请求:" + req );
            String currentTime = "query time order".equals(req) ? new Date(System.currentTimeMillis()).toString(): "bad order";
            dowrite(currentTime);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    /**
     * 往客户端的写操作
     * @param currentTime
     */

    public  void dowrite(String currentTime){
        if(currentTime != null && currentTime.trim().length() > 0){
            byte[] bytes = currentTime.getBytes();
            //分配一个写缓存
            ByteBuffer write = ByteBuffer.allocate(bytes.length);
            log.info("响应客户端请求:" + currentTime);
            //将返回数据写入缓存
            write.put(bytes);
            write.flip();
            //将缓存写进channel
            log.info("响应客户端的消息");
            channel.write(write, write, new CompletionHandler<Integer, ByteBuffer>() {
                @Override
                public void completed(Integer result, ByteBuffer buffer) {
                    //如果发现还有数据没写完，继续写
                    if(buffer.hasRemaining()) {
                        channel.write(buffer, buffer, this);
                    }
                }

                @Override
                public void failed(Throwable exc, ByteBuffer attachment) {
                    try {
                        //写失败，关闭channel，并释放与channel相关联的一切资源
                        channel.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
        }

    }
    @Override
    public void failed(Throwable exc, ByteBuffer attachment) {
        try {
            //读，关闭channel，并释放与channel相关联的一切资源
            this.channel.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
