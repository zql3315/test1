package com.infosky.wechat.service.message.resp;

import com.infosky.wechat.service.common.Constant;

/**
 * 图片消息内容
 * @author 004881
 *
 */
public class ImageMessage extends BaseMessage {

    // 图片
    private ImageObj Image;

    public ImageMessage() {
        super.setMsgType(Constant.MsgType.IMAGE);
    }

    public ImageObj getImage() {
        return Image;
    }

    public void setImage(ImageObj image) {
        Image = image;
    }

}
