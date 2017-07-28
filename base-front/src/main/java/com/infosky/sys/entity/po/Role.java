package com.infosky.sys.entity.po;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import com.google.common.collect.Sets;
import com.infosky.framework.annotation.Comment;
import com.infosky.framework.entity.po.PO;

@Entity
@Table(name = "sys_role")
@Comment("角色表")
public class Role extends PO<String> {

    /**
     * 注释内容
     */
    private static final long serialVersionUID = -1684133362669153672L;

    /**
     * 角色名称
     */
    @Column(name = "NAME")
    @Comment("角色名称")
    private String name;

    /**
     * 创建者
     */
    @Column(name = "CREATOR")
    @Comment("创建者名称")
    private String creator;

    /**
     * 简称
     */
    @Column(name = "SN")
    @Comment("简称")
    private String sn;

    /**
     * 状态
     */
    @Column(name = "status")
    @Comment("状态:-1表示不能删除0表示正常")
    private int status;

    /**
     * 权限表外键
     */
    @OneToMany(mappedBy = "role", cascade = {
            CascadeType.REMOVE, CascadeType.PERSIST
    }, orphanRemoval = true, fetch = FetchType.EAGER)
    @NotFound(action = NotFoundAction.IGNORE)
    private Set<Permission> permissions = Sets.newHashSet();

    /**
     * 描述
     */
    @Column(name = "DESCRIPTION")
    @Comment("描述")
    private String description;

    /**
     * 创建时间
     */
    @Column(name = "CREATED_DATE")
    @Comment("创建时间")
    private java.util.Date createdDate;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

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
     * @return 返回 permissions
     */
    public Set<Permission> getPermissions() {
        return permissions;
    }

    /**
     * @param 对permissions进行赋值
     */
    public void setPermissions(Set<Permission> permissions) {
        this.permissions = permissions;
    }

}
