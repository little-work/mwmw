package com.lilin.mynetty.IO.netty.http;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;
import io.netty.handler.codec.http.multipart.Attribute;
import io.netty.handler.codec.http.multipart.HttpPostRequestDecoder;
import io.netty.handler.codec.http.multipart.InterfaceHttpData;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author lilin
 * @version 1.0
 * @date 2020-07-17 15:04
 */
public class HttpServerHandler extends SimpleChannelInboundHandler<FullHttpRequest> {


    @Override
    protected void messageReceived(ChannelHandlerContext ctx, FullHttpRequest fullReq) throws Exception {


        HttpMethod method = fullReq.getMethod();
        Map<String, String> parmMap = new HashMap<>();

        if (fullReq instanceof HttpRequest) {
            //post请求参数

            //GET请求
            if(HttpMethod.GET==method){
                System.out.println("get请求");
                // 是GET请求
                QueryStringDecoder decoder = new QueryStringDecoder(fullReq.getUri());
                decoder.parameters().entrySet().forEach( entry -> {
                    // entry.getValue()是一个List, 只取第一个元素
                    parmMap.put(entry.getKey(), entry.getValue().get(0));
                });


            }else if(HttpMethod.POST == method){

                System.out.println("post请求");
                /*HttpPostRequestDecoder decoder = new HttpPostRequestDecoder(fullReq);

                decoder.offer(fullReq);
                List<InterfaceHttpData> parmList = decoder.getBodyHttpDatas();

                for (InterfaceHttpData parm : parmList) {
                    Attribute data = (Attribute) parm;
                    parmMap.put(data.getName(), data.getValue());
                }*/

                //用这个接受参数吧  通用
                ByteBuf in = fullReq.content();
                byte[] data = new byte[in.capacity()];
                in.readBytes(data,0,in.capacity());
                String resultJson=new String(data,"UTF-8");
                System.out.println(format1075Json(resultJson));


            }else{
                System.out.println("未知请求类型");
            }

            System.out.println("打印参数："+parmMap);

            // 响应HTML
            String responseHtml = "服务器正常接收参数并处理返回";
            byte[] responseBytes = responseHtml.getBytes("UTF-8");
            int contentLength = responseBytes.length;

            // 构造FullHttpResponse对象，FullHttpResponse包含message body
            FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK, Unpooled.wrappedBuffer(responseBytes));
            response.headers().set("Content-Type", "text/html; charset=utf-8");
            response.headers().set("Content-Length", Integer.toString(contentLength));

            ctx.writeAndFlush(response);
        }
    }

    public static String format1075Json(String json) {
        StringBuilder sb = new StringBuilder();
        JSONObject root = JSON.parseObject(json);
        String body = root.getString("body");
        JSONObject bodyObject = JSON.parseObject(body);
        JSONArray rtArray = bodyObject.getJSONArray("lattices");
        for (Object rt : rtArray) {
            JSONObject ws = (JSONObject) rt;
            String stence = ws.getString("onebest");
            sb.append(stence);
        }

        return sb.toString();
    }
}
