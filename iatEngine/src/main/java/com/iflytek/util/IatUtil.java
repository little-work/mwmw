package com.iflytek.util;

import java.util.UUID;

public class IatUtil {


    public static void main(String[] args) {
        System.out.println(getUUID());
    }

    public static String getUUID(){
        return UUID.randomUUID().toString().replaceAll("-","");
    }
}
