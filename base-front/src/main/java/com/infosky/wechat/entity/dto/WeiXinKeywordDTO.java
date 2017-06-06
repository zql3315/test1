package com.infosky.wechat.entity.dto;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.infosky.framework.entity.dto.DTO;

/**
 * 微信活动关键字对象
 * 
 */
public class WeiXinKeywordDTO extends DTO<String> {

    private static final long serialVersionUID = -7637874203890112861L;

    private int userid;// 商家id

    private String keyword;// 活动关键字

    private String title;// 活动标题

    private String picture;// 图片地址

    private String description;// 文字描述

    private String createtime;// 创建时间

    private int sequence;// 排序

    private String msgtype;// 消息类型（文本text;图片image;语音voice;视频video;音乐music;图文news）

    private int msgcount;// 消息回复数量（例如：1单图文;2~10多图文）

    private List<WeiXinKeywordMaterialDTO> weiXinKeywordMaterials = new ArrayList<WeiXinKeywordMaterialDTO>();

    @JsonIgnore
    private List<WeiXinMessageDTO> messageList = new ArrayList<WeiXinMessageDTO>();// 微信图文消息列表

    public String getMsgtype() {
        return msgtype;
    }

    public void setMsgtype(String msgtype) {
        this.msgtype = msgtype;
    }

    public int getMsgcount() {
        return msgcount;
    }

    public void setMsgcount(int msgcount) {
        this.msgcount = msgcount;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCreatetime() {
        return createtime;
    }

    public void setCreatetime(String createtime) {
        this.createtime = createtime;
    }

    public int getSequence() {
        return sequence;
    }

    public void setSequence(int sequence) {
        this.sequence = sequence;
    }

    public List<WeiXinKeywordMaterialDTO> getWeiXinKeywordMaterials() {
        return weiXinKeywordMaterials;
    }

    public void setWeiXinKeywordMaterials(List<WeiXinKeywordMaterialDTO> weiXinKeywordMaterials) {
        this.weiXinKeywordMaterials = weiXinKeywordMaterials;
    }

    public List<WeiXinMessageDTO> getMessageList() {
        return messageList;
    }

    public void setMessageList(List<WeiXinMessageDTO> messageList) {
        this.messageList = messageList;
    }

}
