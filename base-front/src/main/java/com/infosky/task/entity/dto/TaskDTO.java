/*
 * 文 件 名:  Task.java
 * 版    权:   Ltd. Copyright YYYY-YYYY,  All rights reserved
 * 描    述:  <描述>
 * 修 改 人:  zan
 * 修改时间:  2015年2月3日
 * 跟踪单号:  <跟踪单号>
 * 修改单号:  <修改单号>
 * 修改内容:  <修改内容>
 */
package com.infosky.task.entity.dto;

import com.infosky.framework.entity.dto.DTO;

/**
 * 工程项目
 * 
 * @author  n004881
 * @version  [版本号, 2015年2月3日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class TaskDTO extends DTO<String> {

    /**
    * 注释内容
    */
    private static final long serialVersionUID = 2138719510095833213L;

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
     * 类型
     */
    private String type;

    /**
     * @return 返回 type
     */
    public String getType() {
        return type;
    }

    /**
     * @param 对type进行赋值
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * 状态
     */
    private String status;

    /**
     * @return 返回 status
     */
    public String getStatus() {
        return status;
    }

    /**
     * @param 对status进行赋值
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * 请求消息
     */
    private String requestbody;

    /**
     * @return 返回 requestbody
     */
    public String getRequestbody() {
        return requestbody;
    }

    /**
     * @param 对requestbody进行赋值
     */
    public void setRequestbody(String requestbody) {
        this.requestbody = requestbody;
    }

    /**
     * 响应消息
     */
    private String responsebody;

    /**
     * @return 返回 responsebody
     */
    public String getResponsebody() {
        return responsebody;
    }

    /**
     * @param 对responsebody进行赋值
     */
    public void setResponsebody(String responsebody) {
        this.responsebody = responsebody;
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

    /**
     * 最后更新时间
     */
    private String lastmodtime;

    /**
     * @return 返回 lastmodtime
     */
    public String getLastmodtime() {
        return lastmodtime;
    }

    /**
     * @param 对lastmodtime进行赋值
     */
    public void setLastmodtime(String lastmodtime) {
        this.lastmodtime = lastmodtime;
    }

}
