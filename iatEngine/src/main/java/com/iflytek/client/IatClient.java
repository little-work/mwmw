package com.iflytek.client;

import com.google.common.base.Strings;
import com.google.protobuf.ByteString;
import com.iflytek.iat.sdk.core.IatGrpc;
import com.iflytek.iat.sdk.core.IatRequest;
import com.iflytek.iat.sdk.core.IatResult;
import com.iflytek.util.IatUtil;
import io.grpc.Channel;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

@Slf4j
public class IatClient {


    private String url;
    private IatSessionParam iatSessionParam;
    private StreamObserver<IatRequest> streamRequest;

    public IatClient(String url,IatSessionParam iatSessionParam) {
        this.url=url;
        this.iatSessionParam=iatSessionParam;
    }
    /**
     * 初始化连接参数
     */
    public void connect(SessionResponse sessionResponse) {

        //channel
        ManagedChannel channel = ManagedChannelBuilder.forTarget(this.url).usePlaintext().build();
        //stub
        IatGrpc.IatStub stub = IatGrpc.newStub(channel);

        this.streamRequest = stub.createRec(new StreamObserver<IatResult>() {
            @Override
            public void onNext(IatResult iatResult) {
                sessionResponse.onCallback(iatResult);
            }

            @Override
            public void onError(Throwable throwable) {
                sessionResponse.onError(throwable);
                System.out.println("onError :" + throwable.getMessage());
                throwable.printStackTrace();
            }

            @Override
            public void onCompleted() {
                sessionResponse.onCompleted();
                System.out.println("onCompleted");
            }
        });


        IatRequest iatRequest = IatRequest.newBuilder()
                .putAllSessionParam(getSessionap())
                .setSamplesInfo(iatSessionParam.getSamplesInfo())
                .setEndFlag(false).build();

        streamRequest.onNext(iatRequest);
    }

    /**
     * 发送音频数据
     * @param data
     */
    public void postAudioData(byte[] data){
        ByteString samples = ByteString.copyFrom(data);
        IatRequest iatRequest = IatRequest.newBuilder()
                .setSamples(samples)
                .setSamplesInfo(iatSessionParam.getSamplesInfo())
                .setEndFlag(false).build();
        streamRequest.onNext(iatRequest);
    }

    /**
     * 断开连接
     */
    public void disConnect(){
        IatRequest iatRequest = IatRequest.newBuilder()
                .setSamplesInfo(iatSessionParam.getSamplesInfo())
                .setEndFlag(true).build();
        streamRequest.onNext(iatRequest);
        streamRequest.onCompleted();

    }


    private Map<String,String> getSessionap(){
        // 创建会话参数,只要求传一次,后续持续向服务端写音频时可以忽略（服务也不再解析）
        Map<String, String> sessionParam = new HashMap<String, String>();
        sessionParam.put("sid", iatSessionParam.getSid()); // 会话id,这个参数一定要传,方便以后检索日志
        sessionParam.put("rate", iatSessionParam.getRate()); // 8k,16k 采样率,如果传入的不是16k,则按照16k重采样
        if(!Strings.isNullOrEmpty(iatSessionParam.getAue())) {
            sessionParam.put("aue", iatSessionParam.getAue()); // raw(pcm音频),speex,speex-wb,opus,音频编码格式,未传入默认speex-web
        }
        if(!Strings.isNullOrEmpty(iatSessionParam.getRst())) {
            sessionParam.put("rst", iatSessionParam.getRst()); // json,plain 返回结果的格式,未传入默认json
        }
        if(!Strings.isNullOrEmpty(iatSessionParam.getRse())) {
            sessionParam.put("rse", iatSessionParam.getRse()); // utf8,gbk 转写结果的编码,未传入默认utf8
        }
        if(!Strings.isNullOrEmpty(iatSessionParam.getEos())) {
            sessionParam.put("eos", iatSessionParam.getEos()); // [0~600000] 引擎参数,vad后断点值,整数,毫秒,取值范围[0-60000]
        }
        /*if(!Strings.isNullOrEmpty(iatSessionParam.getBos())) {
            sessionParam.put("bos", iatSessionParam.getBos());
        }*/
        if(!Strings.isNullOrEmpty(iatSessionParam.getDwa())) {
            sessionParam.put("dwa", iatSessionParam.getDwa()); // wpgs 引擎参数,是否获取中间结果
        }
        if(!Strings.isNullOrEmpty(iatSessionParam.getPgs_mode())) {
            sessionParam.put("pgs_mode", iatSessionParam.getPgs_mode());
        }
        if(!Strings.isNullOrEmpty(iatSessionParam.getOpenwp())) {
            sessionParam.put("openwp", iatSessionParam.getOpenwp());
        }
        if(!Strings.isNullOrEmpty(iatSessionParam.getWbest())) {
            sessionParam.put("wbest", iatSessionParam.getWbest()); // [0~5] 引擎参数,多候选（句子级）
        }
        if(!Strings.isNullOrEmpty(iatSessionParam.getWbestonlyper())) {
            sessionParam.put("wbestonlyper", iatSessionParam.getWbestonlyper()); // true,false 引擎参数,是否只有人名多候选
        }

        if(!Strings.isNullOrEmpty(iatSessionParam.getHotword())) {
            sessionParam.put("hotword", iatSessionParam.getHotword()); // word1;word2;... 采用;分割的热词，为utf-8编码
        }
        /*if(!Strings.isNullOrEmpty(iatSessionParam.getDomain())) {
            sessionParam.put("domain", iatSessionParam.getDomain());
        }
        if(!Strings.isNullOrEmpty(iatSessionParam.getAppId())) {
            sessionParam.put("appId", iatSessionParam.getAppId());
        }
        if(!Strings.isNullOrEmpty(iatSessionParam.getBizId())) {
            sessionParam.put("bizId", iatSessionParam.getBizId());
        }
        if(!Strings.isNullOrEmpty(iatSessionParam.getResIdList())) {
            sessionParam.put("resIdList", iatSessionParam.getResIdList());
        }
        if(!Strings.isNullOrEmpty(iatSessionParam.getPersonal())) {
            sessionParam.put("personal", iatSessionParam.getPersonal());
        }*/
        if(!Strings.isNullOrEmpty(iatSessionParam.getEngine_param())) {
            sessionParam.put("engine_param", iatSessionParam.getEngine_param());
        }

        return sessionParam;
    }

}
