package com.iflytek.client;

import com.iflytek.iat.sdk.core.IatResult;

public interface SessionResponse {


    /**
     * server 返回结果回调方法
     *
     */
    public void onCallback(IatResult iatResult);

    /**
     * server 返回出错回调方法
     *
     * @param throwable
     */
    public void onError(Throwable throwable);

    /**
     * server 完成回调方法
     */
    public void onCompleted();
}

