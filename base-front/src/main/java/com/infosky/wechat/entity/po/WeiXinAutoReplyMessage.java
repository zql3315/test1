package com.infosky.wechat.entity.po;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.infosky.framework.annotation.Comment;
import com.infosky.framework.entity.po.PO;

/**
 * 微信自动回复消息对象
 * 
 * 
 */
@Entity
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Table(name = "weixin_auto_replym_essage")
@Comment(value = "微信自动回复消息对象")
public class WeiXinAutoReplyMessage extends PO<String> {

    private static final long serialVersionUID = -6083341541887534125L;

    @Column(name = "title")
    private String title;// 自动回复消息标题

    @Column(name = "picurl")
    private String picurl;// 图片链接地址

    @Column(name = "description")
    private String description;// 描述信息

    @Column(name = "linkurl")
    private String linkurl;// 跳转链接地址

    @Column(name = "keywordid")
    private int keywordid;// 关联关键字id

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

    public int getKeywordid() {
        return keywordid;
    }

    public void setKeywordid(int keywordid) {
        this.keywordid = keywordid;
    }

}
