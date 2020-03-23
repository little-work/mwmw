package com.lilin.mwmw.springEvent;

import com.lilin.mwmw.springEvent.Event.OrderCallBackEvent;
import com.lilin.mwmw.springEvent.Listener.AbstractEventListener;
import com.lilin.mwmw.springEvent.Listener.EventListener;
import com.lilin.mwmw.springEvent.Listener.EventListener1;
import com.lilin.mwmw.springEvent.Listener.EventListener2;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.EventObject;
import java.util.List;

public class MainTest {


    public static void main(String[] args) {

        List<AbstractEventListener>  list=new ArrayList<>();
        list.add(new EventListener2(2));
        list.add(new EventListener1(1));

        list.stream().sorted(Comparator.comparingInt(AbstractEventListener::getOrder));


        OrderCallBackEvent orderCallBackEvent=
                new OrderCallBackEvent(new EventObject("订单事件"));

        OrderEventMUlticaster orderEventMUlticaster=new OrderEventMUlticaster(list);

        orderEventMUlticaster.multicaster(orderCallBackEvent);

    }
}
