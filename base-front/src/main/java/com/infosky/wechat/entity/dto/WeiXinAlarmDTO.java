package com.infosky.wechat.entity.dto;

import com.infosky.framework.entity.dto.DTO;

/**
 * 微信支付 系统告警信息
 * 
 * @author n003598
 * 
 */
public class WeiXinAlarmDTO extends DTO<Integer> {

    /**
     * 
     */
    private static final long serialVersionUID = 7740194483021172905L;

    // 公众号id
    private String appId;

    // 告警类型
    private String errorType;

    // 告警描述
    private String desc;

    // 告警内容
    private String content;

    // 时间戳
    private String timeStamp;

    // 签名
    private String appsignature;

    // 签名类型
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
