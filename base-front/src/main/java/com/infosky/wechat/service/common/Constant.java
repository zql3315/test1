package com.infosky.wechat.service.common;

public interface Constant {

    /**
     * 微信消息类型
     * @author 004881
     *
     */
    public interface MsgType {

        /** 链接消息 */
        public static final String LINK = "link";

        /** 地理位置消息 */
        public static final String LOCATION = "location";

        /** 视频消息 */
        public static final String VIDEO = "video";

        /** 语音消息 */
        public static final String VOICE = "voice";

        /** 图片消息 */
        public static final String IMAGE = "image";

        /** 文本消息 */
        public static final String TEXT = "text";

        /** 音乐消息 */
        public static final String MUSIC = "music";

        /** 图文消息 */
        public static final String NEWS = "news";

        /** 推送事件 */
        public static final String EVENT = "event";

        /** 客服 */
        public static final String TRANSFER_CUSTOMER_SERVICE = "transfer_customer_service";
    }

    /**
     * 微信推送事件类型
     * @author 004881
     *
     */
    public interface Event {

        /** 订阅(关注)*/
        public static final String SUBSCRIBE = "subscribe";

        /** 取消订阅(取消关注)*/
        public static final String UNSUBSCRIBE = "unsubscribe";

        /** 用户已关注时的事件推送(扫描带参数二维码事件)*/
        public static final String SCAN = "SCAN";

        /** 上报地理位置事件*/
        public static final String LOCATION = "LOCATION";

        /** 自定义菜单*/
        public static final String CLICK = "CLICK";

    }

}
