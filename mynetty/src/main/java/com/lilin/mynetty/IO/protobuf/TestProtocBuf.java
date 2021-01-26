package com.lilin.mynetty.IO.protobuf;

import com.google.protobuf.InvalidProtocolBufferException;

public class TestProtocBuf {





    /**
     * 编码---其中SubscribeReqProto.SubscribeRequest就是proto文件中的哪个message
     * @param req
     * @return
     */
    private static byte[] encode(SubscribeReqProto.SubscribeRequest req) {
        return req.toByteArray();
    }

    /**
     * 解码
     * @param body
     * @return
     * @throws InvalidProtocolBufferException
     */
    private static SubscribeReqProto.SubscribeRequest decode(byte[] body) throws InvalidProtocolBufferException {
        return SubscribeReqProto.SubscribeRequest.parseFrom(body);
    }

    /**
     * 生成proto文件中SubscribeRequest实体类
     * @return
     */
    private static SubscribeReqProto.SubscribeRequest createSubscribeReq() {
        SubscribeReqProto.SubscribeRequest.Builder builder = SubscribeReqProto.SubscribeRequest.newBuilder();
        builder.setSubReqID(1);
        builder.setUserName("JhonRain");
        //builder.setProductName("Netty Learn");
        builder.setAddress("saSasAS");
        return builder.build();
    }

    public static void main(String[] args) throws InvalidProtocolBufferException {

        //编码
        SubscribeReqProto.SubscribeRequest req = createSubscribeReq();
        System.out.println("Before encode :" + req.toString());

        //解码
        SubscribeReqProto.SubscribeRequest req2 = decode(encode(req));
        System.out.println("After decode : " + req2.toString());

        System.out.println("Assert equal : --> " + req2.equals(req));




        //System.out.println(System.getProperty("user.dir"));  //d:\mwmw
    }
}
