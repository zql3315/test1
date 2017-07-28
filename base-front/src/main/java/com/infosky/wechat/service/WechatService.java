package com.infosky.wechat.service;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import com.infosky.common.mapper.BeanMapper;
import com.infosky.wechat.common.MessageUtil;
import com.infosky.wechat.entity.dto.WeiXinKeywordDTO;
import com.infosky.wechat.entity.po.WeiXinKeyword;
import com.infosky.wechat.service.common.Constant;

/**
 * 微信核心服务
 * 
 * @author n004881
 *
 */
@Service
public class WechatService {

    protected final static Log logger = LogFactory.getLog(WechatService.class);

    @Resource
    WeiXinUtilService weiXinUtilService;

    /**
     * 处理微信发来的请求
     * 
     * @param request
     * @return
     */
    public String processRequest(HttpServletRequest request) {
        String respMessage = null;
        String respContent = "系统繁忙，请稍后再试！";
        try {
            // 解析微信服务器请求xml
            Map<String, String> requestMap = MessageUtil.parseXml(request);
            logger.info("微信请求消息" + JSONObject.fromObject(requestMap));
            // 消息类型
            String msgType = requestMap.get("MsgType");

            if (msgType.equals(Constant.MsgType.EVENT)) {
                // 接收事件推送
                respMessage = this.handleEvent(requestMap);
            } else {
                // 接收普通消息
                respMessage = this.ordinaryMessage(requestMap);
            }

        } catch (Exception e) {
            e.printStackTrace();
            return respContent;
        }
        return respMessage;
    }

    /**
     * 处理普通消息
     * 
     * @param requestMap  请求消息集合
     * @return String 响应消息的xml
     */
    private String ordinaryMessage(Map<String, String> requestMap) {

        // 消息类型
        String msgType = requestMap.get("MsgType");
        // 文本消息
        if (msgType.toUpperCase().equals("TEXT")) {
            String keyWord = requestMap.get("Content");
            return this.getKeyWordMessage(requestMap, keyWord);
        }
        // 图片消息
        else if (msgType.toUpperCase().equals("IMAGE")) {
            // PicUrl 图片链接
            // MediaId 图片消息媒体id，可以调用多媒体文件下载接口拉取数据。
            // MsgId 消息id，64位整型
        }
        // 语音消息
        else if (msgType.toUpperCase().equals("VOICE")) {
            // MediaId 语音消息媒体id，可以调用多媒体文件下载接口拉取数据。
            // Format 语音格式，如amr，speex等
            // MsgID 消息id，64位整型

            // Recognition 语音识别结果，UTF8编码 只有开启语音识别功能才有这个字段

            if (null != requestMap.get("Recognition")) {
                String recognition = requestMap.get("Recognition");
                return this.getKeyWordMessage(requestMap, recognition);
            }
            // 没有语音识别功能 取出语音文件
            else {

            }
        }
        // 视频消息
        else if (msgType.toUpperCase().equals("VIDEO")) {
            // MediaId 视频消息媒体id，可以调用多媒体文件下载接口拉取数据。
            // ThumbMediaId 视频消息缩略图的媒体id，可以调用多媒体文件下载接口拉取数据。
            // MsgId 消息id，64位整型
        }
        // 地理位置消息
        else if (msgType.toUpperCase().equals("LOCATION")) {

            // 发送消息用户id
            String fromUserName = requestMap.get("FromUserName");
            // 将消息发往的用户id
            String toUserName = requestMap.get("ToUserName");
            // 消息创建事件
            int createTime = Integer.parseInt(requestMap.get("CreateTime"));
            // Location_X 地理位置维度
            // Location_Y 地理位置经度
            // Scale 地图缩放大小
            // Label 地理位置信息
            // MsgId 消息id，64位整型
            String Location_X = requestMap.get("Location_X");
            String Location_Y = requestMap.get("Location_Y");
            String content = "您的地理位置：";
            content += "维度：" + Location_X + "";
            content += "经度：" + Location_Y + "";

            return RespService.setTextMessage(createTime, toUserName, fromUserName, content);
        }
        // 链接消息
        else if (msgType.toUpperCase().equals("LINK")) {
            // Title 消息标题
            // Description 消息描述
            // Url 消息链接
            // MsgId 消息id，64位整型
        }
        return "";
    }

    /**
     * 根据关键字获取回复消息
     * 
     * @param requestMap
     *            请求消息集合
     * @param keyWord
     *            关键字
     * @return String 响应消息的xml
     */
    private String getKeyWordMessage(Map<String, String> requestMap, String keyWord) {
        // 发送消息用户id
        String fromUserName = requestMap.get("FromUserName");
        // 将消息发往的用户id
        String toUserName = requestMap.get("ToUserName");
        // 消息创建事件
        int createTime = Integer.parseInt(requestMap.get("CreateTime"));

        if (keyWord == null) {
            return "";
        }
        logger.info("========用户输入的关键字=====" + keyWord + "========");

        // 接入客服信息
        String accessCustomerServiceKeyWord = "接入客服";
        if (accessCustomerServiceKeyWord != null && (accessCustomerServiceKeyWord.equals(keyWord) || keyWord.toUpperCase().equals("JRKF"))) {
            return RespService.setCustomerMessage(createTime, toUserName, fromUserName);
        }

        // 获取回复消息信息默认回复
        WeiXinKeywordDTO weixinKeyword = weiXinUtilService.getWeiXinKeyword(keyWord);
        // 如果用户发送的消息内容不是关键字，则调用默认回复
        if (null != weixinKeyword) {
            return ReqService.handleReqText(requestMap, BeanMapper.map(weixinKeyword, WeiXinKeyword.class));
        } else {
            weixinKeyword = weiXinUtilService.getWeiXinKeyword("默认回复");
            return ReqService.handleReqText(requestMap, BeanMapper.map(weixinKeyword, WeiXinKeyword.class));
        }
    }

    /**
     * 处理推送事件 (均按照处理文本消息请求方法处理推荐事件)
     * 
     * @param requestMap    请求消息集合
     * @return String 响应消息的xml
     */
    private String handleEvent(Map<String, String> requestMap) {
        String respContent = "";
        String eventType = requestMap.get("Event");

        /**
         * 判断推送事件类型 1.自定义菜单事件 2.关注/取消关注事件 3.扫描带参数二维码事件 4.上报地理位置事件
         */
        if (Constant.Event.SUBSCRIBE.equals(eventType)) {
            logger.debug("==============关注公众号:首次关注=================");
            // 关注公众号
            // 获取二维码的key(qrscene_为前缀，后面为二维码的参数值)
            String eventKey = requestMap.get("EventKey");
            String keyWord = "首次关注";// 首次关注关键字

            // 获取回复消息信息
            WeiXinKeywordDTO weixinKeyword = weiXinUtilService.getWeiXinKeyword(keyWord);
            logger.debug("==============首次关注=================" + weixinKeyword);

            // 如果二维码的key为空，则为普通关注事件
            if (null != eventKey || !"".equals(eventKey)) {

                // 扫描带参数二维码事件: 用户未关注时，进行关注后的事件推送

                // 解析二维码的KEY,获取参数(关键字：qrscene_为前缀，后面为二维码的参数值)

                // 解析二位码

                // 事件处理

            }

            return ReqService.handleReqText(requestMap, BeanMapper.map(weixinKeyword, WeiXinKeyword.class));
        } else if (Constant.Event.UNSUBSCRIBE.equals(eventType))// 取消关注公众号
        {
            // 取消订阅
        } else if (Constant.Event.SCAN.equals(eventType)) {
            // 扫描带参数二维码事件:用户已关注时的事件推送
        } else if (Constant.Event.LOCATION.equals(eventType)) {
            // 上报地理位置事件
        } else if (Constant.Event.CLICK.equals(eventType)) {
            // 自定义菜单事件
            // 获取响应消息关键字
            String keyWord = requestMap.get("EventKey");
            // 获取回复消息信息
            return this.getKeyWordMessage(requestMap, keyWord);
            // 获取回复消息信息
        }
        return respContent;
    }

}
