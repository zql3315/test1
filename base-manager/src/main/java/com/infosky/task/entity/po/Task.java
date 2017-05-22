package com.infosky.task.entity.po;

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
@Table(name = "sys_task")
public class Task extends PO<String> {

    /**
    * 注释内容
    */
    private static final long serialVersionUID = 1564030471979242335L;

    /**
     * 名称
     */
    @Column(name = "NAME")
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
     * 类型  1 Excel 导出  2 Excel 导入
     */
    @Column(name = "TYPE")
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
     * 状态 1 初始  2 正在执行   3 完成  4 失败
     */
    @Column(name = "STATUS")
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
    @Column(name = "REQUESTBODY")
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
    @Column(name = "RESPONSEBODY")
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
    @Column(name = "CREATOR")
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
    @Column(name = "LASTMODTIME")
    private java.util.Date lastmodtime;

    /**
     * @return 返回 lastmodtime
     */
    public java.util.Date getLastmodtime() {
        return lastmodtime;
    }

    /**
     * @param 对lastmodtime进行赋值
     */
    public void setLastmodtime(java.util.Date lastmodtime) {
        this.lastmodtime = lastmodtime;
    }
}
