package com.infosky.log.entity.po;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.infosky.framework.annotation.Comment;
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
@Table(name = "sys_visit_log")
public class VisitLog extends PO<String> {

    /**
     * 注释内容
     */
    private static final long serialVersionUID = 7348880374058677986L;

    /**
     * 访问者
     */
    @Column(name = "VISITOR")
    private String visitor;

    /**
     * 客户端ip
     */
    @Comment("客户端ip")
    private String ip;

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
    @Column(name = "VISIT_TIME")
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
    @Column(name = "VISIT_MODULE")
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
    @Column(name = "OPERATE")
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
