package com.infosky.sys.entity.po;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.infosky.framework.annotation.Comment;
import com.infosky.framework.entity.po.PO;

@Entity
@Table(name = "sys_role_user")
@Comment("用户角色关联关系表")
public class RoleUser extends PO<String> {

    /**
     * 注释内容
     */
    private static final long serialVersionUID = -1684133362669153672L;

    /**
     * 角色编码
     */
    @ManyToOne
    @JoinColumn(name = "RID")
    private Role role;

    /**
     * 用户编码
     */
    @ManyToOne
    @JoinColumn(name = "user_ID")
    private User user;

    /**
     * @return 返回 role
     */
    public Role getRole() {
        return role;
    }

    /**
     * @param 对role进行赋值
     */
    public void setRole(Role role) {
        this.role = role;
    }

    /**
     * @return 返回 user
     */
    public User getUser() {
        return user;
    }

    /**
     * @param 对user进行赋值
     */
    public void setUser(User user) {
        this.user = user;
    }

}
