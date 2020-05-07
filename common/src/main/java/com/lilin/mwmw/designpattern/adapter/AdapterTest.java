package com.lilin.mwmw.designpattern.adapter;

public class AdapterTest {


    public static void main(String[] args) {
        Adapter adapter=new Adapter(){
            @Override
            public int m1() {
                return 212212;
            }
        };

        adapter.m1();
    }
}
