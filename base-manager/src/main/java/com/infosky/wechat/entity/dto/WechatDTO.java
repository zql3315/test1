package com.infosky.wechat.entity.dto;

import java.util.Date;

import com.infosky.framework.entity.dto.DTO;

/**
 * 微信号配置表
 * 
 * @author n004881
 * 
 */
public class WechatDTO extends DTO<String> {

    /**
     * 
     */
    private static final long serialVersionUID = -3808025367034164947L;

    /**
     * 应用ID
     */
    private String appid;

    /**
     * 应用密钥
     */
    private String appsecret;

    /**
     * 回调地址
     */
    private String notifyurl;

    /**
     * 支付密钥
     */
    private String partnerkey;

    /**
     * 商户编号
     */
    private String mchid;

    /**
     * 描述
     */
    private String description;

    /**
     * jsapi_ticket
     */
    private String jsapi_ticket;

    /**
     * 令牌
     */
    private String access_token;

    /**
     * access_token生成时间
     */
    private String atUpdatetime;

    /**
     * jsapi_ticket生成时间
     */
    private String jtUpdatetime;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 名称
     */
    private String name;

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getAppsecret() {
        return appsecret;
    }

    public void setAppsecret(String appsecret) {
        this.appsecret = appsecret;
    }

    public String getPartnerkey() {
        return partnerkey;
    }

    public void setPartnerkey(String partnerkey) {
        this.partnerkey = partnerkey;
    }

    public String getMchid() {
        return mchid;
    }

    public void setMchid(String mchid) {
        this.mchid = mchid;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public String getAtUpdatetime() {
        return atUpdatetime;
    }

    public void setAtUpdatetime(String atUpdatetime) {
        this.atUpdatetime = atUpdatetime;
    }

    public String getNotifyurl() {
        return notifyurl;
    }

    public void setNotifyurl(String notifyurl) {
        this.notifyurl = notifyurl;
    }

    public String getJsapi_ticket() {
        return jsapi_ticket;
    }

    public void setJsapi_ticket(String jsapi_ticket) {
        this.jsapi_ticket = jsapi_ticket;
    }

    public String getJtUpdatetime() {
        return jtUpdatetime;
    }

    public void setJtUpdatetime(String jtUpdatetime) {
        this.jtUpdatetime = jtUpdatetime;
    }
}
