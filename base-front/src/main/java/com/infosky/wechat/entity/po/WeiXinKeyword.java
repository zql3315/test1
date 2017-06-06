package com.infosky.wechat.entity.po;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.OrderBy;

import com.infosky.framework.annotation.Comment;
import com.infosky.framework.entity.po.PO;

/**
 * 微信关键字对象
 */
@Entity
@Table(name = "weixin_keyword")
@Comment(value = "微信关键字对象")
public class WeiXinKeyword extends PO<String> {

    private static final long serialVersionUID = -2214256772478218067L;

    @Column(name = "userid")
    @Comment(value = "商家id")
    private int userid;

    @Column(name = "keyword")
    @Comment(value = "关键字")
    private String keyword;

    @Column(name = "description", columnDefinition = "TEXT")
    @Comment(value = "文字描述")
    private String description;

    @Column(name = "createtime")
    @Comment(value = "创建时间")
    private String createtime;

    @Column(name = "sequence")
    @Comment(value = "排序")
    private int sequence;

    @Column(name = "msgtype")
    @Comment(value = "消息类型（文本text;图片image;语音voice;视频video;音乐music;图文news）")
    private String msgtype;

    @Column(name = "msgcount")
    @Comment(value = "消息回复数量（例如：1单图文;2~10多图文）")
    private int msgcount;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true, mappedBy = "weiXinKeyword")
    @OrderBy(clause = "id asc")
    private List<WeiXinMessage> messageList = new ArrayList<WeiXinMessage>();// 微信图文消息列表

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

    public List<WeiXinMessage> getMessageList() {
        return messageList;
    }

    public void setMessageList(List<WeiXinMessage> messageList) {
        this.messageList = messageList;
    }

}
