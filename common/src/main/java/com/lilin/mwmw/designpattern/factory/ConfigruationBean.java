package com.lilin.mwmw.designpattern.factory;

public class ConfigruationBean implements Bean {


    @Override
    public void getBean() {
        System.out.println("ConfigruationBean::getBean");
    }
}
