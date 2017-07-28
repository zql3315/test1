package com.infosky.wechat.entity.dto;

import com.infosky.framework.entity.dto.DTO;

/**
 * 微信自动回复消息对象
 * 
 * @author 930177
 * 
 */
public class WeiXinMessageDTO extends DTO<String> {

    private static final long serialVersionUID = 1396827034246362901L;

    private String title;// 自动回复消息标题

    private String picurl;// 图片链接地址

    private String description;// 描述信息

    private String linkurl;// 跳转链接地址

    private WeiXinKeywordDTO weiXinKeyword;

    public WeiXinKeywordDTO getWeiXinKeyword() {
        return weiXinKeyword;
    }

    public void setWeiXinKeyword(WeiXinKeywordDTO weiXinKeyword) {
        this.weiXinKeyword = weiXinKeyword;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPicurl() {
        return picurl;
    }

    public void setPicurl(String picurl) {
        this.picurl = picurl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLinkurl() {
        return linkurl;
    }

    public void setLinkurl(String linkurl) {
        this.linkurl = linkurl;
    }
}
