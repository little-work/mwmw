package com.lilin.mwmw.designpattern.strategy;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ActivityContext {



    private Preferential preferential;

    public double preferentialAlthm(double cash){
        return preferential.calculation(cash);
    }




}
