package com.infosky.wechat.entity.po;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import com.infosky.framework.annotation.Comment;
import com.infosky.framework.entity.po.PO;

/**
 * 微信自定义菜单
 * 
 * @author n004881
 * 
 */
@Entity
@Table(name = "weixin_custom_menu")
public class CustomMenu extends PO<String> {

    /**
     * 
     */
    private static final long serialVersionUID = -2552579041904431314L;

    /**
     * 菜单名称
     */
    @Column(name = "menuName")
    private String menuName;

    /**
     * 触发类型
     */
    @Column(name = "triggerType")
    private String triggerType;

    /**
     * 触发内容
     */
    @Column(name = "triggerContent")
    private String triggerContent;

    /**
     * 状态
     */
    @Column(name = "state")
    private String state;

    /**
     * 排序号
     */
    @Column(name = "orderNum")
    private Integer orderNum;

    /**
     * 父节点
     */
    @ManyToOne
    @NotFound(action = NotFoundAction.IGNORE)
    @JoinColumn(name = "pid")
    private CustomMenu parent;

    /**
     * 子菜单
     */
    @OneToMany(mappedBy = "parent", cascade = {
            CascadeType.PERSIST, CascadeType.REMOVE
    })
    @NotFound(action = NotFoundAction.IGNORE)
    @OrderBy(value = "orderNum ASC")
    private List<CustomMenu> children;

    /**
     * 创建时间
     */
    @Comment(value = "创建时间")
    @Column(name = "createTime", updatable = false)
    private Date createTime = new Date();

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = new Date();
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

    public Integer getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(Integer orderNum) {
        this.orderNum = orderNum;
    }

    public CustomMenu getParent() {
        return parent;
    }

    public void setParent(CustomMenu parent) {
        this.parent = parent;
    }

    public List<CustomMenu> getChildren() {
        return children;
    }

    public void setChildren(List<CustomMenu> children) {
        this.children = children;
    }

}
