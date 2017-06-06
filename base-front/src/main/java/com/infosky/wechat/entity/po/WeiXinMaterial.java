package com.infosky.wechat.entity.po;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.infosky.framework.annotation.Comment;
import com.infosky.framework.entity.po.PO;

/**
 * 微信素材管理
 */
@Entity
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Table(name = "weixin_material")
@Comment(value = "微信素材管理对象")
public class WeiXinMaterial extends PO<String> {

    private static final long serialVersionUID = -6083341541887534125L;

    @Column(name = "filename")
    @Comment(value = "文件名称")
    private String filename;//文件名称  

    @Column(name = "filepath")
    @Comment(value = "文件存储路径")
    private String filepath;//文件存储路径

    @Column(name = "type")
    @Comment(value = "文件类型（图片image;语音voice;视频video;缩略图thumb）   （文本text;news图文）")
    private String type;//文件类型（图片image;语音voice;视频video;缩略图thumb）   （文本text;news图文）

    @Column(name = "createtime")
    @Comment(value = "创建时间")
    private String createtime;//创建时间

    @Column(name = "modifytime")
    @Comment(value = "修改时间")
    private String modifytime;//修改时间

    @Column(name = "media_id")
    @Comment(value = "文件上传微信成功后返回的文件标识")
    private String media_id;//文件上传微信成功后返回的文件标识

    @Column(name = "failtime")
    @Comment(value = "文件上传微信成功后失效截止时间(上传成功时间+有效期)")
    private String failtime;//文件上传微信成功后失效截止时间(上传成功时间+有效期)

    @Column(name = "description")
    @Comment(value = "内容")
    private String description;//内容

    @Column(name = "msgcount")
    @Comment(value = "消息回复数量（例如：1单图文;2~10多图文）")
    private int msgcount;//消息回复数量（例如：1单图文;2~10多图文）

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true, mappedBy = "weiXinMaterial")
    @OrderBy(" id asc ")
    private List<WeiXinMaterialMessage> messageList = new ArrayList<WeiXinMaterialMessage>();//微信图文消息列表

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getFilepath() {
        return filepath;
    }

    public void setFilepath(String filepath) {
        this.filepath = filepath;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCreatetime() {
        return createtime;
    }

    public void setCreatetime(String createtime) {
        this.createtime = createtime;
    }

    public String getModifytime() {
        return modifytime;
    }

    public void setModifytime(String modifytime) {
        this.modifytime = modifytime;
    }

    public String getMedia_id() {
        return media_id;
    }

    public void setMedia_id(String media_id) {
        this.media_id = media_id;
    }

    public String getFailtime() {
        return failtime;
    }

    public void setFailtime(String failtime) {
        this.failtime = failtime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getMsgcount() {
        return msgcount;
    }

    public void setMsgcount(int msgcount) {
        this.msgcount = msgcount;
    }

    public List<WeiXinMaterialMessage> getMessageList() {
        return messageList;
    }

    public void setMessageList(List<WeiXinMaterialMessage> messageList) {
        this.messageList = messageList;
    }

}
