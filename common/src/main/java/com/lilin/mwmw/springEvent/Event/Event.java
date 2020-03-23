package com.lilin.mwmw.springEvent.Event;

import java.io.Serializable;
import java.util.EventObject;

/**
 * 事件接口
 */
public interface Event extends Serializable {

    /**
     * 获取事件持有对象
     * @return
     */
    EventObject getEventObject();

    /**
     * 设置事件持有对象
     * @param eventObject
     */
    void setEventObject(EventObject eventObject);
}
