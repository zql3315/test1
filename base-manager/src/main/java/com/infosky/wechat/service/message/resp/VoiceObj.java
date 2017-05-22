package com.infosky.wechat.service.message.resp;

public class VoiceObj {

    // 语音消息媒体id，可以调用多媒体文件下载接口拉取数据
    private String MediaId;

    public String getMediaId() {
        return MediaId;
    }

    public void setMediaId(String mediaId) {
        this.MediaId = mediaId;
    }

}
