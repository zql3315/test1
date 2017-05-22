package com.infosky.notify.entity.po;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.infosky.framework.entity.po.PO;

/**
 * 
 * 
 * @author  n004881
 * @version  [版本号, 2015年2月3日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
@Entity
@Table(name = "sys_notification")
public class Notification extends PO<String> {

    /**
    * 注释内容
    */
    private static final long serialVersionUID = -6920584604588449542L;

    /**
     * 
     */
    @Column(name = "TARGET_ID")
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
    @Column(name = "SYSTEM")
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
    @Column(name = "CREATEDATE")
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
    @Column(name = "TITLE")
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
    @Column(name = "ATTACHMENT")
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
    @Column(name = "CONTENT")
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
    @Column(name = "IS_READ")
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
