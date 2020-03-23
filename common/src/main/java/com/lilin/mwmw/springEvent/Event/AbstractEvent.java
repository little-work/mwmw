package com.lilin.mwmw.springEvent.Event;

import java.util.EventObject;

public class AbstractEvent implements Event{

    private EventObject eventObject;

    public AbstractEvent(EventObject eventObject){
        this.setEventObject(eventObject);
    }

    @Override
    public EventObject getEventObject() {
        return eventObject;
    }

    @Override
    public void setEventObject(EventObject eventObject) {
        this.eventObject=eventObject;
    }
}
