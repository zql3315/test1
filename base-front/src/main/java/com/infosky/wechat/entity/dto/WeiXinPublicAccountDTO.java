package com.infosky.wechat.entity.dto;

import com.infosky.framework.entity.dto.DTO;

/**
 * 微信公共账号
 * @author n930177
 */
public class WeiXinPublicAccountDTO extends DTO<String> {

    private static final long serialVersionUID = -1755022463065616393L;

    private int userid;// 商家id

    private String accountname;//公共账号昵称

    private String accountwxid;//公共账号微信id

    private String token;//验证开发者模式下服务标识参数

    private String accesstoken;//获取用户信息群发消息等用到的token保存

    private String accesstokencreatetime;//获取access_token创建时间

    private String accesstokenexpiresin;//access_token凭证有效时间

    private String createtime;//创建时间

    private String firstreplykeyword;//绑定首次关注关键字

    private String defaultreplykeyword;//绑定默认回复关键字

    private String appid;//公共账号开发者模式下，第三方用户唯一凭证

    private String appsecret;//公共账号开发者模式下，第三方用户唯一凭证密钥，即appsecret

    private String ticket;//公众号调用微信JS接口的临时票据

    private String ticketcreatetime;//ticket创建时间

    private String ticketexpiresin;//ticket凭证有效时间

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public String getAccountname() {
        return accountname;
    }

    public void setAccountname(String accountname) {
        this.accountname = accountname;
    }

    public String getAccountwxid() {
        return accountwxid;
    }

    public void setAccountwxid(String accountwxid) {
        this.accountwxid = accountwxid;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getAccesstoken() {
        return accesstoken;
    }

    public void setAccesstoken(String accesstoken) {
        this.accesstoken = accesstoken;
    }

    public String getAccesstokencreatetime() {
        return accesstokencreatetime;
    }

    public void setAccesstokencreatetime(String accesstokencreatetime) {
        this.accesstokencreatetime = accesstokencreatetime;
    }

    public String getAccesstokenexpiresin() {
        return accesstokenexpiresin;
    }

    public void setAccesstokenexpiresin(String accesstokenexpiresin) {
        this.accesstokenexpiresin = accesstokenexpiresin;
    }

    public String getCreatetime() {
        return createtime;
    }

    public void setCreatetime(String createtime) {
        this.createtime = createtime;
    }

    public String getFirstreplykeyword() {
        return firstreplykeyword;
    }

    public void setFirstreplykeyword(String firstreplykeyword) {
        this.firstreplykeyword = firstreplykeyword;
    }

    public String getDefaultreplykeyword() {
        return defaultreplykeyword;
    }

    public void setDefaultreplykeyword(String defaultreplykeyword) {
        this.defaultreplykeyword = defaultreplykeyword;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
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

    public String getTicket() {
        return ticket;
    }

    public void setTicket(String ticket) {
        this.ticket = ticket;
    }

    public String getTicketcreatetime() {
        return ticketcreatetime;
    }

    public void setTicketcreatetime(String ticketcreatetime) {
        this.ticketcreatetime = ticketcreatetime;
    }

    public String getTicketexpiresin() {
        return ticketexpiresin;
    }

    public void setTicketexpiresin(String ticketexpiresin) {
        this.ticketexpiresin = ticketexpiresin;
    }

}
