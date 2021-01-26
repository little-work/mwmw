package com.lilin;

import io.grpc.Server;
import io.grpc.ServerBuilder;

import java.io.IOException;

public class MyServer {

    public static void main(String[] args) throws IOException, InterruptedException {
        int port = 50051;
        Server server = ServerBuilder.forPort(port)
                .addService(new HelloService())
                .build()
                .start();
        System.out.println("Server started...");
        Thread.sleep(1000 * 60 * 2);
        server.shutdown();
        System.out.println("shutdown");
    }
}
