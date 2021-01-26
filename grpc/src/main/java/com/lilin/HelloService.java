package com.lilin;


import com.acupt.grpc.proto.HelloServiceGrpc;
import com.acupt.grpc.proto.InvokeRequest;
import com.acupt.grpc.proto.InvokeResponse;
import io.grpc.stub.StreamObserver;

public class HelloService extends HelloServiceGrpc.HelloServiceImplBase {


    //普通的RPC调用
    @Override
    public void hello4(InvokeRequest request, StreamObserver<InvokeResponse> responseObserver) {
        System.out.println("request -> " + request);
        String name = request.getName();//自定义的字段名 name
        //构建response
        InvokeResponse response = InvokeResponse.newBuilder()
                .setMsg("服务器接受参数并返回：" + name)//自定义的字段名 msg
                .build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    //服务流返回形式
    @Override
    public void hello2(InvokeRequest request, StreamObserver<InvokeResponse> responseObserver) {
        System.out.println("request -> " + request);
        String name = request.getName();//自定义的字段名 name

        if (name.equals("服务流")) {
            for (int i = 0; i < 10; i++) {
                InvokeResponse response = InvokeResponse.newBuilder()
                        .setMsg(i + "号服务返回流")//自定义的字段名 msg
                        .build();
                responseObserver.onNext(response);
            }
        }

        responseObserver.onCompleted();
    }

    //客户流返回形式
    @Override
    public StreamObserver<InvokeRequest> hello3(StreamObserver<InvokeResponse> responseObserver) {
        return new StreamObserver<InvokeRequest>() {

            String requestParam;

            @Override
            public void onNext(InvokeRequest invokeRequest) {
                //这个会打印很多次，因为客户端调用很多次
                requestParam=invokeRequest.getName();
                System.out.println("获取客户端请求参数:"+invokeRequest.getName());
                System.out.println("服务器处理。。。。");
            }

            @Override
            public void onError(Throwable throwable) {

            }

            @Override
            public void onCompleted() {
                responseObserver.onNext(InvokeResponse.newBuilder()
                        .setMsg("我是服务器，我已经处理了你的请求:"+requestParam)//自定义的字段名 msg
                        .build());
                responseObserver.onCompleted();
            }
        };
    }


    //双向流返回形式
    @Override
    public StreamObserver<InvokeRequest> hello(StreamObserver<InvokeResponse> responseObserver) {
        return new StreamObserver<InvokeRequest>() {

            @Override
            public void onNext(InvokeRequest invokeRequest) {
                //接受请求
                System.out.println("收到请求:" + invokeRequest.getName());
                InvokeResponse response = InvokeResponse.newBuilder()
                        .setMsg("ok处理了" + invokeRequest.getName())//自定义的字段名 msg
                        .build();
                //每接受一次请求就处理一次请求
                responseObserver.onNext(response);
            }

            @Override
            public void onError(Throwable throwable) {
                throwable.getMessage();
            }

            @Override
            public void onCompleted() {
                responseObserver.onCompleted();
            }
        };
    }

}
