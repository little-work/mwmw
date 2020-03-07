package com.lilin.mynetty.IO.sysBlockIO;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class SocketHander implements Runnable {

    private Socket socket = null;

    public SocketHander(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        BufferedReader in = null;
        PrintWriter out = null;
        try {
            in = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
            String context;
            while ((context = in.readLine()) != null) {
                System.out.println("client msg:" + context);
            }
            //this.socket.shutdownInput();
            out = new PrintWriter(socket.getOutputStream(), true);
            out.println("hello client!i am server!");
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                in.close();
                if (this.socket != null) {
                    this.socket.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            out.close();
        }
    }
}
