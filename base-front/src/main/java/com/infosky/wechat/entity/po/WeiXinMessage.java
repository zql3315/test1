package com.infosky.wechat.entity.po;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.infosky.framework.annotation.Comment;
import com.infosky.framework.entity.po.PO;

/**
 * 微信自动回复消息对象
 * 
 * @author 003552
 */
@Entity
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Table(name = "weixin_message")
public class WeiXinMessage extends PO<String> {

    private static final long serialVersionUID = -3627724633084737499L;

    @Column(name = "title")
    @Comment(value = "自动回复消息标题")
    private String title;// 自动回复消息标题

    @Column(name = "picurl")
    @Comment(value = "图片链接地址")
    private String picurl;// 图片链接地址

    @Column(name = "description")
    @Comment(value = "描述信息")
    private String description;// 描述信息

    @Column(name = "linkurl")
    @Comment(value = "跳转链接地址")
    private String linkurl;// 跳转链接地址

    @ManyToOne
    @JoinColumn(name = "keywordid")
    @Comment(value = "微信关键字")
    private WeiXinKeyword weiXinKeyword;

    public WeiXinKeyword getWeiXinKeyword() {
        return weiXinKeyword;
    }

    public void setWeiXinKeyword(WeiXinKeyword weiXinKeyword) {
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
