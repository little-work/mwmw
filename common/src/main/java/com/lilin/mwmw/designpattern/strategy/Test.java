package com.lilin.mwmw.designpattern.strategy;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class Test {


    private double full;
    private double discash;
    private double discouunt;


    public static void main(String[] args) {
        Test test = new Test(300, 20, 0.8);
        System.out.println(test.calculation(1,500));
    }

    public double calculation(int type, double cash) {
        ActivityContext activityContext;
        switch (type) {
            case 1:
                activityContext = new ActivityContext(new FullReduction(full, discash));
                break;
            case 2:
                activityContext = new ActivityContext(new DiscountPrefer(discouunt));
                break;
            default:
                activityContext = new ActivityContext(new NoPreferential());
        }
        return activityContext.preferentialAlthm(cash);
    }


}
