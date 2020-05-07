package com.lilin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class Main {


    public static void main(String[] args) {
        HashMap<String,String> map=new HashMap<>(10);
        map.put("eqwe","eqwe");

        LinkedHashMap lhm=new LinkedHashMap();
        lhm.put("d","d");
        lhm.get("d");

        ArrayList al=new ArrayList();
        al.add("d");


        System.out.println(tableSizeFor(6));
        System.out.println(tableSizeFor(7));
        System.out.println(tableSizeFor(8));
        System.out.println(tableSizeFor(9));
        System.out.println(tableSizeFor(10));

    }

    static final int tableSizeFor(int cap) {
        int n = cap - 1;
        n |= n >>> 1;
        n |= n >>> 2;
        n |= n >>> 4;
        n |= n >>> 8;
        n |= n >>> 16;
        return (n < 0) ? 1 : (n >= (1 << 30)) ? (1 << 30) : n + 1;
    }

}
