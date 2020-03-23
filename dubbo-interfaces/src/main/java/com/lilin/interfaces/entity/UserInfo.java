package com.lilin.interfaces.entity;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

@Data
@ToString
public class UserInfo implements Serializable {

    //这个min  max不是位数  是数字在4-20之间
    private int id;

    private int age;

    private String username;
    private String password;
    private String address;

    private Integer userType;
    private Date createTime;
    private Date lastUpdateTime;


    private void init() {
        System.out.println("调用User类的初始化方法 ");
    }

    private void destory() {
        System.out.println("容器关闭的时候");
    }
}
