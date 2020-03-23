package com.lilin.mwmw.springEvent;

import com.lilin.mwmw.springEvent.Event.Event;
import com.lilin.mwmw.springEvent.Listener.AbstractEventListener;
import com.lilin.mwmw.springEvent.Listener.EventListener;

import java.util.List;

public class OrderEventMUlticaster {


    private List<AbstractEventListener> listeners;

    public OrderEventMUlticaster(List<AbstractEventListener> listeners){
        this.listeners=listeners;
    }


    public void multicaster(Event event){
        for(EventListener listener:listeners){
            listener.onEvent(event);
        }
    }
}
