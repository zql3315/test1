package com.infosky.wechat.entity.dto;

import java.util.Date;

import com.infosky.framework.entity.dto.DTO;

/**
 * 微信号配置列表页面对象
 * 
 * @author n004881
 * 
 */
public class WechatListAdapterDTO extends DTO<String> {

    /**
     * 
     */
    private static final long serialVersionUID = -3808025367034164947L;

    /**
     * 名称
     */
    private String name;

    /**
     * 应用ID
     */
    private String appid;

    /**
     * 回调地址
     */
    private String notifyurl;

    /**
     * 商户编号
     */
    private String mchid;

    /**
     * 创建时间
     */
    private Date createTime;

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

    public String getNotifyurl() {
        return notifyurl;
    }

    public void setNotifyurl(String notifyurl) {
        this.notifyurl = notifyurl;
    }

    public String getMchid() {
        return mchid;
    }

    public void setMchid(String mchid) {
        this.mchid = mchid;
    }

}
