package com.lilin.dubboserviceconsumer.jsonRpc;

import com.googlecode.jsonrpc4j.spring.AutoJsonRpcServiceImpl;
import org.springframework.stereotype.Service;

import java.util.HashMap;

/**
 * @author lilin
 * @version 1.0
 * @date 2020-10-13 16:07
 */
@Service
@AutoJsonRpcServiceImpl
public class RpcServiceImpl implements RpcService {
    @Override
    public String test(HashMap<String,String> map) {

        System.out.println(map.get("cmd"));
        return "hello,jsonRpc";
    }
}
