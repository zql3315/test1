/*
 * 文 件 名:  Notification.java
 * 版    权:   Ltd. Copyright YYYY-YYYY,  All rights reserved
 * 描    述:  <描述>
 * 修 改 人:  zan
 * 修改时间:  2015年2月3日
 * 跟踪单号:  <跟踪单号>
 * 修改单号:  <修改单号>
 * 修改内容:  <修改内容>
 */
package com.infosky.notify.entity.dto;

import org.springframework.format.annotation.DateTimeFormat;

import com.infosky.framework.entity.dto.DTO;

/**
 * 工程项目
 * 
 * @author  n004881
 * @version  [版本号, 2015年2月3日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class NotificationDTO extends DTO<String> {

    /**
    * 注释内容
    */
    private static final long serialVersionUID = -672684154915333501L;

    /**
     * 
     */
    private String targetId;

    /**
     * @return 返回 targetId
     */
    public String getTargetId() {
        return targetId;
    }

    /**
     * @param 对targetId进行赋值
     */
    public void setTargetId(String targetId) {
        this.targetId = targetId;
    }

    /**
     * 
     */
    private String system;

    /**
     * @return 返回 system
     */
    public String getSystem() {
        return system;
    }

    /**
     * @param 对system进行赋值
     */
    public void setSystem(String system) {
        this.system = system;
    }

    /**
     * 
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private java.util.Date date;

    /**
     * @return 返回 date
     */
    public java.util.Date getDate() {
        return date;
    }

    /**
     * @param 对date进行赋值
     */
    public void setDate(java.util.Date date) {
        this.date = date;
    }

    /**
     * 
     */
    private String title;

    /**
     * @return 返回 title
     */
    public String getTitle() {
        return title;
    }

    /**
     * @param 对title进行赋值
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * 
     */
    private String attachment;

    /**
     * @return 返回 attachment
     */
    public String getAttachment() {
        return attachment;
    }

    /**
     * @param 对attachment进行赋值
     */
    public void setAttachment(String attachment) {
        this.attachment = attachment;
    }

    /**
     * 
     */
    private String content;

    /**
     * @return 返回 content
     */
    public String getContent() {
        return content;
    }

    /**
     * @param 对content进行赋值
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * 
     */
    private String isRead;

    /**
     * @return 返回 isRead
     */
    public String getIsRead() {
        return isRead;
    }

    /**
     * @param 对isRead进行赋值
     */
    public void setIsRead(String isRead) {
        this.isRead = isRead;
    }

}
