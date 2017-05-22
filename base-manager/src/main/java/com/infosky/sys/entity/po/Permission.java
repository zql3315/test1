package com.infosky.sys.entity.po;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.infosky.framework.annotation.Comment;
import com.infosky.framework.entity.po.PO;

@Entity
@Table(name = "sys_permission")
@Comment("权限表")
public class Permission extends PO<String> {

    /**
     * 注释内容
     */
    private static final long serialVersionUID = -1684133362669153672L;

    /**
     * 角色
     */
    @ManyToOne
    @JoinColumn(name = "rid")
    private Role role;

    /**
     * 资源
     */
    @ManyToOne
    @JoinColumn(name = "reid")
    private Resource resource;

    /**
     * 操作
     */
    @ManyToOne
    @JoinColumn(name = "oid")
    private Operation operation;

    /**
     * @return 返回 operation
     */
    public Operation getOperation() {
        return operation;
    }

    /**
     * @param 对operation进行赋值
     */
    public void setOperation(Operation operation) {
        this.operation = operation;
    }

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
     * @return 返回 resource
     */
    public Resource getResource() {
        return resource;
    }

    /**
     * @param 对resource进行赋值
     */
    public void setResource(Resource resource) {
        this.resource = resource;
    }

}
