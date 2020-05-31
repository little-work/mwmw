package com.lilin.mwmw.designpattern.factory;

public class XmlBean implements Bean {


    @Override
    public void getBean() {
        System.out.println("XmlBean::getBean");
    }
}
