package com.infosky.wechat.entity.po;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.infosky.framework.annotation.Comment;
import com.infosky.framework.entity.po.PO;

/**
 * 微信支付 系统告警信息
 * 
 */
@Entity
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Table(name = "weixin_alarm")
@Comment(value = "微信支付  系统告警信息")
public class WeiXinAlarm extends PO<String> {

    /**
     * 
     */
    private static final long serialVersionUID = 7740194483021172905L;

    // 公众号id
    @Column(name = "AppId")
    private String appId;

    // 告警类型
    @Column(name = "errorType")
    private String errorType;

    // 告警描述
    @Column(name = "description")
    private String desc;

    // 告警内容
    @Column(name = "alarmContent")
    private String content;

    // 时间戳
    @Column(name = "timeStamp")
    private String timeStamp;

    // 签名
    @Column(name = "appsignature")
    private String appsignature;

    // 签名类型
    @Column(name = "signMethod")
    private String signMethod;

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getErrorType() {
        return errorType;
    }

    public void setErrorType(String errorType) {
        this.errorType = errorType;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getAppsignature() {
        return appsignature;
    }

    public void setAppsignature(String appsignature) {
        this.appsignature = appsignature;
    }

    public String getSignMethod() {
        return signMethod;
    }

    public void setSignMethod(String signMethod) {
        this.signMethod = signMethod;
    }

}
