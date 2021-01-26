package com.lilin.mynetty.IO.netty.http;

import com.google.common.collect.Lists;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * @author lilin
 * @version 1.0
 * @date 2020-08-10 10:00
 */
public class Client {


    private static void doPost() throws UnsupportedEncodingException {
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();

        // 创建Get请求
        HttpPost doPost = new HttpPost("http://127.0.0.1:8887");

        // 准备参数
        List<NameValuePair> params = Lists.newArrayList();
        params.add(new BasicNameValuePair("seqNo", "23213213"));
        params.add(new BasicNameValuePair("content", "hello server"));
        params.add(new BasicNameValuePair("resDesc", "操作 成功"));
        params.add(new BasicNameValuePair("resCode", "0000"));

        UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(params, "UTF-8");

        formEntity.setContentType("application/x-www-form-urlencoded");
        doPost.setEntity(formEntity);

        // 响应模型
        CloseableHttpResponse response = null;
        try {
            // 由客户端执行(发送)Get请求
            response = httpClient.execute(doPost);
            // 从响应模型中获取响应实体
            HttpEntity responseEntity = response.getEntity();
            System.out.println("响应状态为:" + response.getStatusLine());
            if (responseEntity != null) {
                System.out.println("响应内容长度为:" + responseEntity.getContentLength());
                System.out.println("响应内容为:" + EntityUtils.toString(responseEntity));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                // 释放资源
                if (httpClient != null) {
                    httpClient.close();
                }
                if (response != null) {
                    response.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) throws UnsupportedEncodingException {
        doPost();
    }
}
