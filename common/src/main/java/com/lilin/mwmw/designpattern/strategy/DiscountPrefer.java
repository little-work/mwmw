package com.lilin.mwmw.designpattern.strategy;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class DiscountPrefer implements Preferential{

    private double discount;


    @Override
    public double calculation(double cash) {
        return cash*discount;
    }
}
