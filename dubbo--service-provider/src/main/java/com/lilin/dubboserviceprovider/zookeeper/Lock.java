package com.lilin.dubboserviceprovider.zookeeper;

import lombok.Data;

@Data
public class Lock {


    private String lockId;
    private String path;
    private boolean active;



}
