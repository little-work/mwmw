package com.lilin.mynetty.IO.NIO;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

@Slf4j
public class NIO implements Runnable {
    private ServerSocketChannel serverSocketChannel;

    private Selector selector;

    private volatile boolean stop;


    public NIO(int port) {

        try {
            /**
             * 1.linux网络编程是两个进程之间的通信，跨集群合网络
             * 2.开启一个socket线程，在linux系统上任何操作均以文件句柄数表示，默认情况下
             *  一个线程可以打开1024个句柄，也就说最多同时支持1024个网络连接请求。阿里云默认打开65535个文件
             *  句柄，通常情况下，1G内存最多可以打开10w个句柄数
             */

            // 通过open()方法找到Selector
            //底层： 开启epoll，为当前socket服务创建epoll服务，epoll_create
            selector = Selector.open();
            //底层: 在linux上面开启socket服务，启动一个线程。绑定ip地址和端口号
            serverSocketChannel = ServerSocketChannel.open();
            //绑定ip和端口号，默认的IP=127.0.0.1，对连接的请求最大队列长度设置为backlog=1024，
            // 如果队列满时收到连接请求，则拒绝连接
            serverSocketChannel.socket().bind(new InetSocketAddress(port), 1024);
            /**
             * 设置非阻塞方式   传统的BIO是单线程下面的阻塞  1、接受客户请求阻塞。2、read阻塞
             * 先将serverSocket设置为非阻塞，客户请求进来就处理，不用等到上一个客户处理完才能连接
             */
            serverSocketChannel.configureBlocking(false);
            /**
             * 注册到selector，等待连接
             *
             *  底层：
             *  1.将当前的epoll,服务器地址，端口号绑定,如果有连接请求，直接添加到epoll中，epoll的底层是红黑树，
             * 可以快速的实现连接的查找和状态更新。如果有新的连接过来，直接存放到epoll中。如果有连接过期，中断，
             *  会从epoll中删除。
             * 2.通过epoll_ctl添加到epoll的同时，会注册一个回调函数给内核，当网卡有数据来的时候，会通知内核，内核
             * 调用回调函数，将当前内核数据的事件状态添加到list链表中
             */
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
            log.info("the server is start port = " + port);
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    public void stop() {
        this.stop = true;
    }

    @Override
    public void run() {
        //如果server没有停止
        while (!stop) {
            try {
                /**
                 * 选择一组键，并且相应的通道已经打开
                 *
                 *  epoll底层维护一个链表，rdlist，基于事件驱动模式，当网卡有数据请求过来，会发起硬件中断，
                 *  通知内核已经有来了。内核调用回调函数，将当前的事件添加到rdlist中，
                 *  将当前可用的rdlist列表发送给用户态，用户去遍历rdlist中的事件，进行处理
                 */
                selector.select(1000);
                //找到所有准备接续的key
                Set<SelectionKey> selectionKeys = selector.selectedKeys();
                Iterator<SelectionKey> it = selectionKeys.iterator();
                SelectionKey key = null;
                while (it.hasNext()) {
                    key = it.next();
                    // 获得当前epoll的rdlist复制到用户态，遍历，同时删除当前rdlist中的事件
                    it.remove();
                    try {
                        //处理准备就绪的key
                        handle(key);
                    } catch (Exception e) {
                        if (key != null) {
                            //请求取消此键的通道到其选择器的注册
                            key.cancel();
                            //关闭这个通道
                            if (key.channel() != null) {
                                key.channel().close();
                            }
                        }
                    }
                }
            } catch (Throwable e) {
                e.printStackTrace();
            }
        }
        if (selector != null) {
            try {
                selector.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void handle(SelectionKey key) throws IOException {
        //如果key是有效的
        if (key.isValid()) {
            //测试此键的通道是否已准备好接受新的套接字连接。
            if (key.isAcceptable()) {
                /**
                 * 当server socket channel通道已经准备好，就可以从server socket channel中获取socketchannel了
                 *  拿到socket channel后，要做的事情就是马上到selector注册这个socket channel感兴趣的事情。
                 *   否则无法监听到这个socket channel到达的数据
                 */
                ServerSocketChannel ssc = (ServerSocketChannel) key.channel();
                SocketChannel sc = (SocketChannel) ssc.accept();
                //设置客户端链路为非阻塞模式
                sc.configureBlocking(false);
                /**
                 * 在server socket channel接收到/准备好 一个新的 TCP连接后。
                 *  就会向程序返回一个新的socketChannel
                 * 但是这个新的socket channel并没有在selector“选择器/代理器”中注册，
                 * 所以程序还没法通过selector通知这个socket channel的事件。
                 * 于是我们拿到新的socket channel后，要做的第一个事情就是到selector“选择器/代理器”中注册这个
                 */
                sc.register(selector, SelectionKey.OP_READ);
            }
            //监听到客户端的读请求
            if (key.isReadable()) {
                //获得通道对象
                SocketChannel sc = (SocketChannel) key.channel();
                ByteBuffer readBuffer = ByteBuffer.allocate(1024);
                //从channel读数据到缓冲区
                int readBytes = sc.read(readBuffer);
                /**
                 * readBytes > 0 判断客户端是发送了数据
                 */
                if (readBytes > 0) {
                    //Flips this buffer.  The limit is set to the current position and then
                    // the position is set to zero，就是表示要从起始位置开始读取数据
                    readBuffer.flip();
                    //eturns the number of elements between the current position and the  limit.
                    // 要读取的字节长度
                    byte[] bytes = new byte[readBuffer.remaining()];
                    //将缓冲区的数据读到bytes数组
                    readBuffer.get(bytes);
                    String body = new String(bytes, "UTF-8");
                    log.info("收到客户端的消息：" + body);
                    log.info("开始响应客户端");
                    doWrite(sc);
                } else if (readBytes < 0) {
                    key.channel();
                    sc.close();
                }
            }
        }
    }

    /**
     * 响应客户端的请求
     * @param channel
     * @throws IOException
     */
    public static void doWrite(SocketChannel channel) throws IOException {
        byte[] bytes = "你好我是服务端".getBytes();
        //分配一个bytes的length长度的ByteBuffer
        ByteBuffer write = ByteBuffer.allocate(bytes.length);
        //将返回数据写入缓冲区
        write.put(bytes);
        write.flip();
        //将缓冲数据写入渠道，返回给客户端
        channel.write(write);
    }

    public static void main(String[] args) {
        new Thread(new NIO(8080)).start();
    }
}
