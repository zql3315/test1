package com.infosky.sys.entity.po;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import com.google.common.collect.Lists;
import com.infosky.framework.annotation.Comment;
import com.infosky.framework.entity.po.PO;

@Entity
@Table(name = "sys_resource")
@Comment("资源表")
public class Resource extends PO<String> {

    /**
     * 注释内容
     */
    private static final long serialVersionUID = -1684133362669153672L;

    /**
     * 资源名称
     */
    @Column(name = "NAME")
    @Comment("资源名称")
    private String name;

    /**
     * 资源类型 1 目录 2 页面 3 元素
     */
    @Column(name = "TYPE")
    @Comment("资源类型 1 目录 2 页面 3 元素")
    private String type;

    /**
     * 资源URL
     */
    @Column(name = "URL")
    @Comment("资源URL")
    private String url;

    /**
     * 资源图标
     */
    @Column(name = "ICONS")
    @Comment("资源图标")
    private String icons;

    /**
     * 描述
     */
    @Column(name = "DESCRIPTION")
    @Comment("描述")
    private String description;

    @ManyToOne
    @JoinColumn(name = "pid")
    private Resource parent;

    // fetch=FetchType.EAGER 非懒加载,而是直接加载
    @OneToMany(mappedBy = "parent", fetch = FetchType.LAZY)
    @NotFound(action = NotFoundAction.IGNORE)
    @OrderBy(value = "priority ASC")
    private List<Resource> children = Lists.newArrayList();

    /**
     * 资源简称
     */
    @Column(name = "SN")
    private String sn;

    /**
     * 资源优先级
     */
    @Column(name = "PRIORITY")
    private int priority;

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
     * @return 返回 url
     */
    public String getUrl() {
        return url;
    }

    /**
     * @param 对url进行赋值
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * @return 返回 icons
     */
    public String getIcons() {
        return icons;
    }

    /**
     * @param 对icons进行赋值
     */
    public void setIcons(String icons) {
        this.icons = icons;
    }

    /**
     * @return 返回 priority
     */
    public int getPriority() {
        return priority;
    }

    /**
     * @param 对priority进行赋值
     */
    public void setPriority(int priority) {
        this.priority = priority;
    }

    /**
     * @return 返回 desciption
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param 对desciption进行赋值
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return 返回 parent
     */
    public Resource getParent() {
        return parent;
    }

    /**
     * @param 对parent进行赋值
     */
    public void setParent(Resource parent) {
        this.parent = parent;
    }

    /**
     * @return 返回 children
     */
    public List<Resource> getChildren() {
        return children;
    }

    /**
     * @param 对children进行赋值
     */
    public void setChildren(List<Resource> children) {
        this.children = children;
    }

}
