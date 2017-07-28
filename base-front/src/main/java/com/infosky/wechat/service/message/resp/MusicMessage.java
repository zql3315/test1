package com.infosky.wechat.service.message.resp;

import com.infosky.wechat.service.common.Constant;

/**
 * 音乐消息内容
 * @author 004881
 *
 */
public class MusicMessage extends BaseMessage {

    private Music Music;

    public MusicMessage() {
        super.setMsgType(Constant.MsgType.MUSIC);
    }

    public Music getMusic() {
        return Music;
    }

    public void setMusic(Music music) {
        this.Music = music;
    }

}
