package com.infosky.wechat.entity.dto;

import java.util.ArrayList;
import java.util.List;

import com.infosky.framework.entity.dto.DTO;

/**
 * 素材-图文消息
 * 
 * @author 004883
 * 
 */
public class WeiXinMaterialDTO extends DTO<String> {

    private static final long serialVersionUID = 3519470088733185972L;

    private String filename;//文件名称  

    private String filepath;//文件存储路径

    private String type;//文件类型（图片image;语音voice;视频video;缩略图thumb）   （文本text;news图文）

    private String createtime;//创建时间

    private String modifytime;//修改时间

    private String media_id;//文件上传微信成功后返回的文件标识

    private String failtime;//文件上传微信成功后失效截止时间(上传成功时间+有效期)

    private String description;//内容

    private int msgcount;//消息回复数量（例如：1单图文;2~10多图文）

    private List<WeiXinMaterialMessageDTO> messageList = new ArrayList<WeiXinMaterialMessageDTO>();//微信图文消息列表

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

    public List<WeiXinMaterialMessageDTO> getMessageList() {
        return messageList;
    }

    public void setMessageList(List<WeiXinMaterialMessageDTO> messageList) {
        this.messageList = messageList;
    }

}
