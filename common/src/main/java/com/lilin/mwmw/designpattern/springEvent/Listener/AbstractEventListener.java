package com.lilin.mwmw.designpattern.springEvent.Listener;

import com.lilin.mwmw.designpattern.springEvent.Event.Event;

public class AbstractEventListener implements EventListener{

    private int order;


    public AbstractEventListener(int order){
        this.order=order;
    }

    public int getOrder(){
        return order;
    }


    @Override
    public void onEvent(Event event) {

    }
}
