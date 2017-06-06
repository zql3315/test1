package com.infosky.wechat.service;

import java.util.ArrayList;
import java.util.List;

import com.infosky.common.util.PropertiesConfig;
import com.infosky.wechat.common.MessageUtil;
import com.infosky.wechat.entity.po.WeiXinKeyword;
import com.infosky.wechat.entity.po.WeiXinMessage;
import com.infosky.wechat.service.common.Constant;
import com.infosky.wechat.service.message.resp.Article;
import com.infosky.wechat.service.message.resp.CustomerMessage;
import com.infosky.wechat.service.message.resp.NewsMessage;
import com.infosky.wechat.service.message.resp.TextMessage;

/**
 * 响应服务类
 * 处理对应的响应消息为微信服务器可识别的格式
 * 
 * @author n004881
 *
 */
public class RespService {

    /**
     * 设置客服文本信息
     * @param createTime
     * @param toUserName
     * @param fromUserName
     * 
     * @return String(Xml)
     */
    public static String setCustomerMessage(int createTime, String toUserName, String fromUserName) {
        CustomerMessage customerMessage = new CustomerMessage();
        customerMessage.setCreateTime(createTime);
        customerMessage.setFromUserName(toUserName);
        customerMessage.setToUserName(fromUserName);
        customerMessage.setMsgType(Constant.MsgType.TRANSFER_CUSTOMER_SERVICE);
        return MessageUtil.messageToXML(customerMessage);
    }

    /**
     * 设置响应文本信息
     * @param createTime
     * @param toUserName
     * @param fromUserName
     * @param content
     * 
     * @return String(Xml)
     */
    public static String setTextMessage(int createTime, String toUserName, String fromUserName, String content) {
        TextMessage textM = new TextMessage();
        textM.setCreateTime(createTime);
        textM.setFromUserName(toUserName);
        textM.setToUserName(fromUserName);
        textM.setContent(content);
        textM.setMsgType(Constant.MsgType.TEXT);
        return MessageUtil.messageToXML(textM);
    }

    /**
     * 设置响应图文信息
     * 
     * @param createTime
     * @param toUserName
     * @param fromUserName
     * 
     * @return String(Xml)
     */
    public static String setNewsMessage(int createTime, String appId, String openId, WeiXinKeyword weixinKeyword) {
        NewsMessage news = new NewsMessage();
        news.setCreateTime(createTime);
        news.setFromUserName(appId);
        news.setToUserName(openId);
        news.setMsgType(Constant.MsgType.NEWS);

        int msgCount = weixinKeyword.getMsgcount();
        List<WeiXinMessage> messageList = weixinKeyword.getMessageList();

        System.out.println("=============messageList==========" + messageList.size());

        if (messageList.size() <= 0) {
            return "";
        }

        List<Article> artList = new ArrayList<Article>();

        for (int i = 0; i < msgCount; i++) {
            Article art = new Article();
            art.setDescription(messageList.get(i).getDescription());
            art.setPicUrl(PropertiesConfig.readValue("imagePath") + "/" + messageList.get(i).getPicurl());
            art.setTitle(messageList.get(i).getTitle());
            //针对服务号修改连接

            String linkurl = messageList.get(i).getLinkurl();

            if (linkurl.indexOf("?") < 0) {
                linkurl = linkurl + "?" + "need_oauth2_url=true";
            } else {
                linkurl = linkurl + "&" + "need_oauth2_url=true";
            }

            art.setUrl(linkurl);
            System.out.println("=============art==========" + art.getDescription() + "," + art.getPicUrl());
            artList.add(art);
        }

        news.setArticleCount(artList.size());
        news.setArticles(artList);

        return MessageUtil.messageToXML(news);
    }

}