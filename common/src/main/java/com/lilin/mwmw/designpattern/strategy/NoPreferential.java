package com.lilin.mwmw.designpattern.strategy;

public class NoPreferential implements Preferential{

    @Override
    public double calculation(double cash) {
        return cash;
    }
}
