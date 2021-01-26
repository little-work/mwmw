package com.lilin.mynetty.IO.netty.http;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.HttpClientUtils;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

/**
 * @author lilin
 * @version 1.0
 * @date 2021-01-25 15:24
 */

@Slf4j
public class BrowserHttp {


    public static String sendGet(String url) {
        log.info("开始访问：{}"+url);
        String html = "";
        //1.生成httpclient，相当于该打开一个浏览器
        CloseableHttpClient httpClient = HttpClients.createDefault();
        CloseableHttpResponse response = null;
        //2.创建get请求，相当于在浏览器地址栏输入 网址
        HttpGet request = new HttpGet(url);
        //设置请求头
        request.setHeader("accept", "*/*");
        request.setHeader("connection", "Keep-Alive");
        request.setHeader("user-agent",
                "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/86.0.4240.75 Safari/537.36");
        //配置代理
        //HttpHost proxy = new HttpHost("112.85.168.223", 9999);
        //RequestConfig config = RequestConfig.custom().setProxy(proxy).build();
        //request.setConfig(config);
        try {
            //3.执行get请求，相当于在输入地址栏后敲回车键
            response = httpClient.execute(request);
            //4.判断响应状态为200，进行处理
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                //5.获取响应内容
                HttpEntity httpEntity = response.getEntity();
                html = EntityUtils.toString(httpEntity, "utf-8");
                log.info("返回的页面是：{}"+html);
            } else {
                //如果返回状态不是200，比如404（页面不存在）等，根据情况做处理，这里略
                log.error("返回状态不是200");
                log.info(EntityUtils.toString(response.getEntity(), "utf-8"));
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            //6.关闭
            HttpClientUtils.closeQuietly(response);
            HttpClientUtils.closeQuietly(httpClient);
        }
        return html;
    }


    public static void parseHtml(String html) {
        log.info("开始解析html");
        Document document = Jsoup.parse(html);
        Elements span = document.getElementsByClass("RichText");
        for (Element spanItem : span) {
            Elements ps=spanItem.getElementsByTag("p");
            for(Element p:ps){
                System.out.println(p.text());
            }
        }
    }

    public static void main(String[] args) {
        String url="https://www.zhihu.com/question/293676302/answer/1621244460";
        parseHtml(BrowserHttp.sendGet(url));
    }


}
