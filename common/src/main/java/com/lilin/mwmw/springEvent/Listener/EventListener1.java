package com.lilin.mwmw.springEvent.Listener;

import com.alibaba.fastjson.JSONObject;
import com.lilin.mwmw.springEvent.Event.Event;

import java.util.EventObject;

public class EventListener1 extends AbstractEventListener{

    private int order;

    public EventListener1(int order){
        super(order);
    }

    @Override
    public void onEvent(Event event) {
        System.out.println("事件监听器1执行");
        EventObject eventObject=event.getEventObject();
        System.out.println(JSONObject.toJSONString(eventObject));


    }
}
