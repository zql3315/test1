package com.infosky.wechat.service.message.req;

/**
 * 图片消息内容
 * @author 004881
 *
 */
public class ImageMessage extends ReqBaseMessage {

    // 图片链接
    private String PicUrl;

    // 图片消息媒体id，可以调用多媒体文件下载接口拉取数据
    private String MediaId;

    public String getPicUrl() {
        return PicUrl;
    }

    public void setPicUrl(String picUrl) {
        this.PicUrl = picUrl;
    }

    public String getMediaId() {
        return MediaId;
    }

    public void setMediaId(String mediaId) {
        this.MediaId = mediaId;
    }

}
