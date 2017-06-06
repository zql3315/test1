package com.infosky.wechat.service.message.req;

/**
 * 语音信息内容
 * @author 004881
 *
 */
public class VoiceMessage extends ReqBaseMessage {

    // 语音消息媒体id，可以调用多媒体文件下载接口拉取数据
    private String MediaId;

    // 语音格式，如amr，speex等
    private String Format;

    public String getMediaId() {
        return MediaId;
    }

    public void setMediaId(String mediaId) {
        this.MediaId = mediaId;
    }

    public String getFormat() {
        return Format;
    }

    public void setFormat(String format) {
        this.Format = format;
    }
}
