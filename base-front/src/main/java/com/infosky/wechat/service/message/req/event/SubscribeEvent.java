package com.infosky.wechat.service.message.req.event;

import com.infosky.wechat.service.message.req.BaseMessage;

/**
 * 关注/取消关注事件
 * @author 004881
 *
 */
public class SubscribeEvent extends BaseMessage {

    /** 事件类型，subscribe(订阅)、unsubscribe(取消订阅)*/
    private String Event;

    public String getEvent() {
        return Event;
    }

    public void setEvent(String event) {
        Event = event;
    }

}
