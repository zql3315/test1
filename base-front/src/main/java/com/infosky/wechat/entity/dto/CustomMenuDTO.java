package com.infosky.wechat.entity.dto;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.infosky.framework.entity.dto.DTO;

/**
 * 微信自定义菜单
 * 
 * @author n004881
 * 
 */
public class CustomMenuDTO extends DTO<String> {

    /**
     * 
     */
    private static final long serialVersionUID = -8452238839251588872L;

    /**
     * 菜单名称
     */
    private String menuName;

    /**
     * 触发类型
     */
    private String triggerType;

    /**
     * 触发内容
     */
    private String triggerContent;

    /**
     * 子菜单
     */
    private List<CustomMenuDTO> children;

    /**
     * 
     */
    @JsonIgnore
    private CustomMenuDTO parent;

    /**
     * 状态
     */
    private String state;

    /**
     * 排序号
     */
    private Integer orderNum;

    /**
     * 创建时间
     */
    private Date createTime;

    public CustomMenuDTO getParent() {
        return parent;
    }

    public void setParent(CustomMenuDTO parent) {
        this.parent = parent;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getTriggerType() {
        return triggerType;
    }

    public void setTriggerType(String triggerType) {
        this.triggerType = triggerType;
    }

    public String getTriggerContent() {
        return triggerContent;
    }

    public void setTriggerContent(String triggerContent) {
        this.triggerContent = triggerContent;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    public List<CustomMenuDTO> getChildren() {
        return children;
    }

    public void setChildren(List<CustomMenuDTO> children) {
        this.children = children;
    }

    public Integer getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(Integer orderNum) {
        this.orderNum = orderNum;
    }

}
