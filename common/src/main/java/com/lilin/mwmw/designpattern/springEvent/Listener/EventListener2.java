package com.lilin.mwmw.designpattern.springEvent.Listener;

import com.alibaba.fastjson.JSONObject;
import com.lilin.mwmw.designpattern.springEvent.Event.Event;

import java.util.EventObject;

public class EventListener2 extends AbstractEventListener{

    private int order;

    public EventListener2(int order){
        super(order);
    }

    @Override
    public void onEvent(Event event) {
        System.out.println("事件监听器2执行");
        EventObject eventObject=event.getEventObject();
        System.out.println(JSONObject.toJSONString(eventObject));


    }
}
