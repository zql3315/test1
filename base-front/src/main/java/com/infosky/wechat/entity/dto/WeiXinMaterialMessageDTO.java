package com.infosky.wechat.entity.dto;

import com.infosky.framework.entity.dto.DTO;

public class WeiXinMaterialMessageDTO extends DTO<String> {

    private static final long serialVersionUID = -2038800560560758316L;

    private String title;//自动回复消息标题

    private String picurl;//图片链接地址

    private String content;//图文消息页面的内容，支持HTML标签

    private String author;//图文消息的作者

    private String show_cover_pic;//是否显示封面，1为显示，0为不显示

    private String digest;//图文消息的描述

    private String content_source_url;//在图文消息页面点击“阅读原文”后的页面

    private String failtime;//过期时间

    private String thumb_media_id;//微信文件标识

    private WeiXinMaterialDTO weiXinMaterial;//关联的素材

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

    public String getFailtime() {
        return failtime;
    }

    public void setFailtime(String failtime) {
        this.failtime = failtime;
    }

    public String getThumb_media_id() {
        return thumb_media_id;
    }

    public void setThumb_media_id(String thumb_media_id) {
        this.thumb_media_id = thumb_media_id;
    }

    public WeiXinMaterialDTO getWeiXinMaterial() {
        return weiXinMaterial;
    }

    public void setWeiXinMaterial(WeiXinMaterialDTO weiXinMaterial) {
        this.weiXinMaterial = weiXinMaterial;
    }

}
