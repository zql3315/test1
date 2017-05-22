package com.infosky.wechat.service.message.resp;

import com.infosky.wechat.service.common.Constant;

/**
 * 视频消息内容
 * @author 004881
 *
 */
public class VideoMessage extends BaseMessage {

    private VideoObj Video;

    public VideoMessage() {
        super.setMsgType(Constant.MsgType.VIDEO);
    }

    public VideoObj getVideo() {
        return Video;
    }

    public void setVideo(VideoObj video) {
        Video = video;
    }

}
