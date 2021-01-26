package com.lilin.dubboserviceconsumer.jsonRpc;

import com.googlecode.jsonrpc4j.JsonRpcParam;
import com.googlecode.jsonrpc4j.JsonRpcService;

import java.util.HashMap;

/**
 * @author lilin
 * @version 1.0
 * @date 2020-10-13 16:04
 */
@JsonRpcService(value = "jsonRpc")
public interface RpcService {


    String test(@JsonRpcParam(value = "map") HashMap<String,String> map);
}
