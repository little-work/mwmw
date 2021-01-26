package com.lilin;

import com.acupt.grpc.proto.HelloServiceGrpc;
import com.acupt.grpc.proto.InvokeRequest;
import com.acupt.grpc.proto.InvokeResponse;
import io.grpc.Channel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class MyClient {


    public static void main(String[] args) {
        //blockingStub4();
        //blockingStub2();
        //stub3();
        stub();

    }


    //普通rpc
    public static void blockingStub4() {
        //构建请求数据
        InvokeRequest request = InvokeRequest.newBuilder().setName("hello4").build();
        //channel
        Channel channel = ManagedChannelBuilder.forAddress("localhost", 50051).usePlaintext(true).build();
        //连接
        HelloServiceGrpc.HelloServiceBlockingStub blockingStub = HelloServiceGrpc.newBlockingStub(channel);
        //获取返回结果
        InvokeResponse response = blockingStub.hello4(request);
        System.out.println(response.getMsg());
    }


    //服务端流的形式返回
    public static void blockingStub2() {

        List<InvokeResponse> result=new ArrayList();

        //构建请求数据
        InvokeRequest request = InvokeRequest.newBuilder().setName("服务流").build();
        //channel
        Channel channel = ManagedChannelBuilder.forAddress("localhost", 50051).usePlaintext(true).build();
        //连接
        HelloServiceGrpc.HelloServiceBlockingStub blockingStub = HelloServiceGrpc.newBlockingStub(channel);
        //获取返回结果
        Iterator<InvokeResponse> responses = blockingStub.hello2(request);

        while (responses.hasNext()){
            result.add(responses.next());
        }
        //打印服务端返回的请求流
        for(InvokeResponse response:result){
            System.out.println(response.getMsg());
        }
    }


    //客户流
    public static void stub3() {
        //channel
        Channel channel = ManagedChannelBuilder.forAddress("localhost", 50051).usePlaintext(true).build();

        HelloServiceGrpc.HelloServiceStub stub = HelloServiceGrpc.newStub(channel);

        //判断调用状态。在内部类中被访问，需要加final修饰
        final CountDownLatch countDownLatch = new CountDownLatch(1);

        StreamObserver<InvokeRequest> streamRequest=stub.hello3(new StreamObserver<InvokeResponse>() {
            @Override
            public void onNext(InvokeResponse response) {
                //此处直接打印结果，其他也可用回调进行复杂处理
                System.out.println("客户端打印服务器返回结果：" + response.getMsg());
            }

            @Override
            public void onError(Throwable t) {
                System.out.println("onError " + t.getMessage());
                countDownLatch.countDown();
            }

            @Override
            public void onCompleted() {
                System.out.println("onCompleted");
                countDownLatch.countDown();
            }
        });


        for(int i=0;i<10;i++){
            //构建请求数据
            InvokeRequest request = InvokeRequest.newBuilder().setName(i+"号请求参数流").build();
            streamRequest.onNext(request);
            //判断调用结束状态。如果整个调用已经结束，继续发送数据不会报错，但是会被舍弃
            if(countDownLatch.getCount() == 0){
                return;
            }
        }
        //异步请求，无法确保onNext与onComplete的完成先后顺序
        streamRequest.onCompleted();

        try {
            //如果在规定时间内没有请求完，则让程序停止
            if(!countDownLatch.await(5,TimeUnit.MINUTES)){
                System.out.println("not complete in time");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }


    //客户端服务端双流返回
    public static void stub() {
        //channel
        Channel channel = ManagedChannelBuilder.forAddress("localhost", 50051).usePlaintext(true).build();

        HelloServiceGrpc.HelloServiceStub stub = HelloServiceGrpc.newStub(channel);


        //判断调用状态。在内部类中被访问，需要加final修饰
        final CountDownLatch countDownLatch = new CountDownLatch(1);

        StreamObserver<InvokeRequest> streamRequest=stub.hello(new StreamObserver<InvokeResponse>() {

            private int count = 0;

            @Override
            public void onNext(InvokeResponse value) {
                //此处直接打印结果，其他也可用回调进行复杂处理
                System.out.println("第"+count+"次处理了客户端请求："+ value.getMsg());
            }

            @Override
            public void onError(Throwable t) {
                System.out.println("onError " + t.getMessage());
                countDownLatch.countDown();
            }

            @Override
            public void onCompleted() {
                System.out.println("onCompleted");
                countDownLatch.countDown();
            }
        });

        for(int i=0;i<10;i++){
            //构建请求数据
            InvokeRequest request = InvokeRequest.newBuilder().setName(i+"号请求参数流").build();
            streamRequest.onNext(request);
            //判断调用结束状态。如果整个调用已经结束，继续发送数据不会报错，但是会被舍弃
            if(countDownLatch.getCount() == 0){
                return;
            }
        }
        //调用结束  断开连接
        streamRequest.onCompleted();

        try {
            //如果在规定时间内没有请求完，则让程序停止
            if(!countDownLatch.await(5,TimeUnit.MINUTES)){
                System.out.println("not complete in time");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }




}
