package com.infosky.wechat.service.message.resp;

import com.infosky.wechat.service.common.Constant;

/**
 * 语音信息内容
 * @author 004881
 *
 */
public class VoiceMessage extends BaseMessage {

    private VoiceObj Voice;

    public VoiceMessage() {
        super.setMsgType(Constant.MsgType.VOICE);
    }

    public VoiceObj getVoice() {
        return Voice;
    }

    public void setVoice(VoiceObj voice) {
        Voice = voice;
    }

}
