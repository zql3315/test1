/*
 * 文 件 名:  Project.java
 * 版    权:   Ltd. Copyright YYYY-YYYY,  All rights reserved
 * 描    述:  <描述>
 * 修 改 人:  zan
 * 修改时间:  2015年2月3日
 * 跟踪单号:  <跟踪单号>
 * 修改单号:  <修改单号>
 * 修改内容:  <修改内容>
 */
package com.infosky.sys.entity.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.collect.Lists;
import com.infosky.framework.entity.dto.DTO;

/**
 * 工程项目
 * 
 * @author  n004881
 * @version  [版本号, 2015年2月3日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class ResourceDTO extends DTO<String> {

    /**
     * 注释内容
     */
    private static final long serialVersionUID = -1684133362669153672L;

    /**
     * 资源名称
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
     * 资源简称
     */
    private String sn;

    /**
     * @return 返回 name
     */
    public String getSn() {
        return sn;
    }

    /**
     * @param 对name进行赋值
     */
    public void setSn(String sn) {
        this.sn = sn;
    }

    /**
     * 资源类型
     */
    private String type;

    /**
     * @return 返回 name
     */
    public String getType() {
        return type;
    }

    /**
     * @param 对name进行赋值
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * 资源URL
     */
    private String url;

    /**
     * @return 返回 name
     */
    public String getUrl() {
        return url;
    }

    /**
     * @param 对name进行赋值
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * 资源图标
     */
    private String icons;

    /**
     * @return 返回 name
     */
    public String getIcons() {
        return icons;
    }

    /**
     * @param 对name进行赋值
     */
    public void setIcons(String icons) {
        this.icons = icons;
    }

    /**
     * 资源优先级
     */
    private int priority;

    /**
     * @return 返回 name
     */
    public int getPriority() {
        return priority;
    }

    /**
     * @param 对name进行赋值
     */
    public void setPriority(int priority) {
        this.priority = priority;
    }

    /**
     * 描述
     */
    private String description;

    /**
     * @return 返回 name
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param 对name进行赋值
     */
    public void setDescription(String description) {
        this.description = description;
    }

    private ResourceDTO parent;

    @JsonIgnore
    private List<ResourceDTO> children = Lists.newArrayList();

    /**
     * @return 返回 parent
     */
    public ResourceDTO getParent() {
        return parent;
    }

    /**
     * @param 对parent进行赋值
     */
    public void setParent(ResourceDTO parent) {
        this.parent = parent;
    }

    /**
     * @return 返回 children
     */
    public List<ResourceDTO> getChildren() {
        return children;
    }

    /**
     * @param 对children进行赋值
     */
    public void setChildren(List<ResourceDTO> children) {
        this.children = children;
    }
}
