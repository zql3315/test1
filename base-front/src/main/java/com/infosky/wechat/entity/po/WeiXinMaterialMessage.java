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
 * 微信素材消息对象
 * @author 003552
 * 
 */
@Entity
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Table(name = "weixin_material_message")
public class WeiXinMaterialMessage extends PO<String> {

    private static final long serialVersionUID = -2989167861016565896L;

    @Column(name = "title")
    @Comment(value = "自动回复消息标题")
    private String title;//自动回复消息标题

    @Column(name = "picurl")
    @Comment(value = "图片链接地址")
    private String picurl;//图片链接地址

    @Column(name = "content")
    @Comment(value = "图文消息页面的内容，支持HTML标签")
    private String content;//图文消息页面的内容，支持HTML标签

    @Column(name = "author")
    @Comment(value = "图文消息的作者")
    private String author;//图文消息的作者

    @Column(name = "show_cover_pic")
    @Comment(value = "是否显示封面，1为显示，0为不显示")
    private String show_cover_pic;//是否显示封面，1为显示，0为不显示

    @Column(name = "digest")
    @Comment(value = "图文消息的描述")
    private String digest;//图文消息的描述

    @Column(name = "content_source_url")
    @Comment(value = "在图文消息页面点击“阅读原文”后的页面")
    private String content_source_url;//在图文消息页面点击“阅读原文”后的页面

    @Column(name = "failtime")
    @Comment(value = "过期时间")
    private String failtime;//过期时间

    @Column(name = "thumb_media_id")
    @Comment(value = "微信文件标识")
    private String thumb_media_id;//微信文件标识

    @ManyToOne
    @JoinColumn(name = "materialid")
    @Comment(value = "微信多媒体文件")
    private WeiXinMaterial weiXinMaterial;

    public WeiXinMaterial getWeiXinMaterial() {
        return weiXinMaterial;
    }

    public void setWeiXinMaterial(WeiXinMaterial weiXinMaterial) {
        this.weiXinMaterial = weiXinMaterial;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getShow_cover_pic() {
        return show_cover_pic;
    }

    public void setShow_cover_pic(String show_cover_pic) {
        this.show_cover_pic = show_cover_pic;
    }

    public String getDigest() {
        return digest;
    }

    public void setDigest(String digest) {
        this.digest = digest;
    }

    public String getContent_source_url() {
        return content_source_url;
    }

    public void setContent_source_url(String content_source_url) {
        this.content_source_url = content_source_url;
    }

    public String getThumb_media_id() {
        return thumb_media_id;
    }

    public void setThumb_media_id(String thumb_media_id) {
        this.thumb_media_id = thumb_media_id;
    }

    public String getFailtime() {
        return failtime;
    }

    public void setFailtime(String failtime) {
        this.failtime = failtime;
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

}
