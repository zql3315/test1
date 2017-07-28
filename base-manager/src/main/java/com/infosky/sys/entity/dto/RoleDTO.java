package com.infosky.sys.entity.dto;

import org.springframework.format.annotation.DateTimeFormat;

import com.infosky.framework.entity.dto.DTO;

/**
 * 工程项目
 * 
 * @author n004881
 * @version [版本号, 2015年2月3日]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class RoleDTO extends DTO<String> {

    /**
     * 注释内容
     */
    private static final long serialVersionUID = -1684133362669153672L;

    /**
     * 名称
     */
    private String name;

    /**
     * @return 返回 name
     */
    public String getName() {
        return name;
    }

    /**
     * @param 对name进行赋值
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 简称
     */
    private String sn;

    /**
     * 状态
     */
    private int status;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    /**
     * @return 返回 sn
     */
    public String getSn() {
        return sn;
    }

    /**
     * @param 对sn进行赋值
     */
    public void setSn(String sn) {
        this.sn = sn;
    }

    /**
     * 描述
     */
    private String description;

    /**
     * @return 返回 description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param 对description进行赋值
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * 创建时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private java.util.Date createdDate;

    /**
     * @return 返回 createdDate
     */
    public java.util.Date getCreatedDate() {
        return createdDate;
    }

    /**
     * @param 对createdDate进行赋值
     */
    public void setCreatedDate(java.util.Date createdDate) {
        this.createdDate = createdDate;
    }

    /**
     * 创建者
     */
    private String creator;

    /**
     * @return 返回 creator
     */
    public String getCreator() {
        return creator;
    }

    /**
     * @param 对creator进行赋值
     */
    public void setCreator(String creator) {
        this.creator = creator;
    }
}
