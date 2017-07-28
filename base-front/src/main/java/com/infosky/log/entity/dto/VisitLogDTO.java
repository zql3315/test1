/*
 * 文 件 名:  VisitLog.java
 * 版    权:   Ltd. Copyright YYYY-YYYY,  All rights reserved
 * 描    述:  <描述>
 * 修 改 人:  zan
 * 修改时间:  2015年2月3日
 * 跟踪单号:  <跟踪单号>
 * 修改单号:  <修改单号>
 * 修改内容:  <修改内容>
 */
package com.infosky.log.entity.dto;

import com.infosky.framework.entity.dto.DTO;

/**
 * 工程项目
 * 
 * @author  n004881
 * @version  [版本号, 2015年2月3日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class VisitLogDTO extends DTO<String> {

    /**
     * 注释内容
     */
    private static final long serialVersionUID = 2496092745751401488L;

    /**
     * 唯一标识
     */
    private String id;

    /**
     * 客户端ip
     */
    private String ip;

    /**
     * @return 返回 id
     */
    public String getId() {
        return id;
    }

    /**
     * @param 对id进行赋值
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * 访问者
     */
    private String visitor;

    /**
     * @return 返回 visitor
     */
    public String getVisitor() {
        return visitor;
    }

    /**
     * @param 对visitor进行赋值
     */
    public void setVisitor(String visitor) {
        this.visitor = visitor;
    }

    /**
     * 访问时间
     */
    private java.util.Date visitTime;

    /**
     * @return 返回 visitTime
     */
    public java.util.Date getVisitTime() {
        return visitTime;
    }

    /**
     * @param 对visitTime进行赋值
     */
    public void setVisitTime(java.util.Date visitTime) {
        this.visitTime = visitTime;
    }

    /**
     * 访问模块
     */
    private String visitModule;

    /**
     * @return 返回 visitModule
     */
    public String getVisitModule() {
        return visitModule;
    }

    /**
     * @param 对visitModule进行赋值
     */
    public void setVisitModule(String visitModule) {
        this.visitModule = visitModule;
    }

    /**
     * 具体操作
     */
    private String operate;

    /**
     * @return 返回 operate
     */
    public String getOperate() {
        return operate;
    }

    /**
     * @param 对operate进行赋值
     */
    public void setOperate(String operate) {
        this.operate = operate;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

}
