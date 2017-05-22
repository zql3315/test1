package com.infosky.wechat.service.message.req;

/**
 * 视频消息内容
 * @author 004881
 *
 */
public class VideoMessgae extends ReqBaseMessage {

    // 视频消息媒体id，可以调用多媒体文件下载接口拉取数据。
    private String MediaId;

    // 视频消息缩略图的媒体id，可以调用多媒体文件下载接口拉取数据
    private String ThumbMediaId;

    public String getThumbMediaId() {
        return ThumbMediaId;
    }

    public void setThumbMediaId(String thumbMediaId) {
        this.ThumbMediaId = thumbMediaId;
    }

    public String getMediaId() {
        return MediaId;
    }

    public void setMediaId(String mediaId) {
        this.MediaId = mediaId;
    }

}
