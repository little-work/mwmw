package com.lilin.mynetty.IO.netty.po;


import lombok.Data;
import lombok.ToString;
import org.msgpack.annotation.Message;

@Data
@ToString
@Message
public class User {

    private String name;

    private int age;

}
