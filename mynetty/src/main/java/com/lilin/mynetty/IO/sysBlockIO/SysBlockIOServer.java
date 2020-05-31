package com.lilin.mynetty.IO.sysBlockIO;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class SysBlockIOServer {

    public static void main(String[] args) {
        try {
            /**
             * 这行代码其实就是调用openjdk中的bind0的本地native方法
             */
            ServerSocket serverSocket = new ServerSocket(8080);

            while (true){
                /**
                 * 单线程模式下面，接受一个请求后并处理
                 * 如果处理涉及io那么会阻塞 就不能处理其他并发请求
                 *
                 * 阻塞就是阻塞正在accept接受新的客户端中
                 */
                Socket socket=serverSocket.accept();
                /**
                 * 只是一个客户端请求过来 开启多线程进行处理请求，但是多个请求并发过来
                 * 就只能等待 因为一次只能accept一个客户请求
                 */
                new Thread(() -> {
                    BufferedReader in = null;
                    PrintWriter out = null;
                    try {
                        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                        String context;
                        while ((context = in.readLine()) != null) {
                            System.out.println("收到客户端的消息：" + context);
                        }
                        //this.socket.shutdownInput();
                        out = new PrintWriter(socket.getOutputStream(), true);
                        out.println("服务器返回给客户端的消息");
                        out.flush();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } finally {
                        try {
                            in.close();
                            if (socket != null) {
                                socket.close();
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        out.close();
                    }
                }).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
