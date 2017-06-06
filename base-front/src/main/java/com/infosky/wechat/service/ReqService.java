package com.infosky.wechat.service;

import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.infosky.wechat.entity.po.WeiXinKeyword;
import com.infosky.wechat.service.common.Constant;

/**
 * 请求消息处理服务类
 *
 * @author n004881
 *
 */
public class ReqService {

    protected final static Log logger = LogFactory.getLog(ReqService.class);

    /**
     * 处理文本消息服务
     * 
     * @param keyWord    关键字
     * @param requestMap 消息集合
     * 
     * @return String XML
     */
    public static String handleReqText(Map<String, String> requestMap, WeiXinKeyword weixinKeyword) {
        // 发送消息用户id
        String fromUserName = requestMap.get("FromUserName");
        // 将消息发往的用户id
        String toUserName = requestMap.get("ToUserName");
        // 消息创建事件
        int createTime = Integer.parseInt(requestMap.get("CreateTime"));

        String respContent = "";

        // 根据msgType设置回复消息的类型
        String msgType = weixinKeyword.getMsgtype();

        if (Constant.MsgType.TEXT.equals(msgType)) {
            // 回复文本消息
            String content = weixinKeyword.getDescription();
            respContent = RespService.setTextMessage(createTime, toUserName, fromUserName, content);
        } else if (Constant.MsgType.NEWS.equals(msgType)) {
            // 回复图文消息
            respContent = RespService.setNewsMessage(createTime, toUserName, fromUserName, weixinKeyword);
        }

        return respContent;
    }

    /**
     * 处理图片消息服务
     * 
     * @param keyWord    关键字
     * @param requestMap 消息集合
     * 
     * @return String XML
     */
    public static String handleReqImage(Map<String, String> requestMap, String keyWord) {
        return null;
    }

    /**
     * 处理链接消息服务
     * 
     * @param requestMap 消息集合
     * @param keyWord    关键字
     * 
     * @return String XML
     */
    public static String handleReqLink(Map<String, String> requestMap, String keyWord) {
        return null;
    }

    /**
     * 处理地理位置消息服务
     * 
     * @param requestMap 消息集合
     * @param keyWord    关键字
     * 
     * @return String XML
     */
    public static String handleReqLocation(Map<String, String> requestMap, String keyWord) {
        return null;
    }

    /**
     * 处理视频消息服务
     * 
     * @param requestMap 消息集合
     * @param keyWord    关键字
     * 
     * @return String XML
     */
    public static String handleReqVideo(Map<String, String> requestMap, String keyWord) {
        return null;
    }

    /**
     * 处理语音消息服务
     * 
     * @param requestMap 消息集合
     * @param keyWord    关键字
     * 
     * @return String XML
     */
    public static String handleReqVoice(Map<String, String> requestMap, String keyWord) {
        return null;
    }

}