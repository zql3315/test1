package com.infosky.wechat.service.message.req;

public class ReqBaseMessage extends BaseMessage {

    // 消息id，64位整型
    private long MsgId;

    public long getMsgId() {
        return MsgId;
    }

    public void setMsgId(long msgId) {
        this.MsgId = msgId;
    }

}
