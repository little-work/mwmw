package com.lilin.mynetty.IO.sysBlockIO;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class SysBlockIOClient {

    Socket socket = null;
    BufferedReader in = null;
    PrintWriter out = null;

    public void client() {
        try {
            socket = new Socket("127.0.0.1", 8080);
            out = new PrintWriter(socket.getOutputStream());
            out.write("hello server!i am client!");
            out.flush();
            socket.shutdownOutput();

            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String context;
            while ((context = in.readLine()) != null) {
                System.out.println(context);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                socket.close();
                out.close();
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        new SysBlockIOClient().client();
    }
}
