package com.lilin.mwmw.designpattern.strategy;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class FullReduction implements Preferential{


    private double full;

    private double disCash;

    @Override
    public double calculation(double cash) {
        if(cash>full){
            return cash-disCash;
        }
        return cash;
    }



}
