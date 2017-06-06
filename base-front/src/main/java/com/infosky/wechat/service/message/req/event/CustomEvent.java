package com.infosky.wechat.service.message.req.event;

import com.infosky.wechat.service.message.req.BaseMessage;

/**
 * 自定义菜单事件
 * @author 004881
 *
 */
public class CustomEvent extends BaseMessage {

    /**　事件类型，CLICK*/
    private String Event;

    /** 事件KEY值，与自定义菜单接口中KEY值对应*/
    private String EventKey;

    public String getEvent() {
        return Event;
    }

    public void setEvent(String event) {
        Event = event;
    }

    public String getEventKey() {
        return EventKey;
    }

    public void setEventKey(String eventKey) {
        EventKey = eventKey;
    }

}
