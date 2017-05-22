package com.infosky.sys.entity.po;

import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.infosky.framework.annotation.Comment;
import com.infosky.framework.entity.po.PO;

@Entity
@Table(name = "sys_user")
@Comment("用户表")
public class User extends PO<String> {

    /**
     * 注释内容
     */
    private static final long serialVersionUID = -1684133362669153672L;

    /**
     * 登录名
     */
    @Column(name = "LOGINNAME", updatable = false, nullable = false, unique = true)
    @Comment("登录名")
    private String loginname;

    /**
     * 用户名称
     */
    @Column(name = "NAME")
    @Comment("用户名称")
    private String name;

    /**
     * 用户头像
     */
    @Comment("用户头像")
    private String headpic;

    /**
     * 用户密码
     */
    @Column(name = "PASSWORD", nullable = false)
    @Comment("用户密码")
    @JsonIgnore
    // springmvc生成json不包含此字段
    private String password;

    /**
     * 加密盐
     */
    @Column(name = "SALT")
    @Comment("加密盐")
    private String salt;

    /**
     * 邮件
     */
    @Column(name = "EMAIL")
    @Comment("邮件")
    private String email;

    /**
     * 用户状态 0表示正常用户，1表示锁定
     */
    @Column(nullable = false)
    @Comment("-1表示不可删除 用户状态 0表示正常用户，1表示锁定")
    private int status;

    @OneToMany(mappedBy = "user", cascade = {
            CascadeType.PERSIST, CascadeType.REMOVE
    }, orphanRemoval = true, fetch = FetchType.EAGER)
    @NotFound(action = NotFoundAction.IGNORE)
    private Set<RoleUser> roles = Sets.newHashSet();

    @Column(name = "telephone")
    @Comment("用户手机号码")
    private String telephone;

    @Comment("登录类型：0表示网页登录，1表示手机登录,其他待扩展")
    private int loginType;

    @Comment("最后登录时间")
    private Date lastLogin;

    public int getLoginType() {
        return loginType;
    }

    public void setLoginType(int loginType) {
        this.loginType = loginType;
    }

    public Date getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(Date lastLogin) {
        this.lastLogin = lastLogin;
    }

    /**
     * 创建者id
     */
    @ManyToOne
    @JoinColumn(name = "pid")
    private User parent;

    @OneToMany(mappedBy = "parent")
    private List<User> children = Lists.newArrayList();

    public String getHeadpic() {
        return headpic;
    }

    public void setHeadpic(String headpic) {
        this.headpic = headpic;
    }

    public List<User> getChildren() {
        return children;
    }

    public void setChildren(List<User> children) {
        this.children = children;
    }

    public User getParent() {
        return parent;
    }

    public void setParent(User parent) {
        this.parent = parent;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getLoginname() {
        return loginname;
    }

    public void setLoginname(String loginname) {
        this.loginname = loginname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Set<RoleUser> getRoles() {
        return roles;
    }

    public void setRoles(Set<RoleUser> roles) {
        this.roles = roles;
    }

}
