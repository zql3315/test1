/*
 * 文 件 名: Project.java 版 权: Ltd. Copyright YYYY-YYYY, All rights reserved 描 述: <描述> 修 改 人: zan 修改时间: 2015年2月3日 跟踪单号: <跟踪单号> 修改单号: <修改单号> 修改内容: <修改内容>
 */
package com.infosky.sys.entity.dto;

import java.util.Set;

import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;

import com.google.common.collect.Sets;
import com.infosky.framework.entity.dto.DTO;

/**
 * 工程项目
 * 
 * @author n004881
 * @version [版本号, 2015年2月3日]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class UserDTO extends DTO<String> {

    /**
     * 注释内容
     */
    private static final long serialVersionUID = -1684133362669153672L;

    /**
     * 用户名
     */
    @NotBlank(message = "用户名不能为空")
    @Pattern(regexp = "[a-zA-Z0-9_]{4,10}", message = "只能输入4到10位的字母或数字")
    private String loginname;

    /**
     * 用户名称
     */
    private String name;

    /**
     * 用户头像
     */
    private String headpic;

    /**
     * 用户密码
     */
    @NotBlank(message = "密码不能为空")
    private String password;

    /**
     * 加密盐
     */
    @Pattern(regexp = "[a-zA-Z0-9_]{4,20}", message = "只能输入4到20位的字母或数字")
    private String salt;

    /**
     * 邮件
     */
    private String email;

    private UserDTO parent;

    private String role;

    private Set<RoleUserDTO> roles = Sets.newHashSet();

    private String telephone;

    private String roleArr;

    /**
     * 用户状态 0表示正常用户，1表示锁定
     */
    private int status;

    public String getHeadpic() {
        return headpic;
    }

    public void setHeadpic(String headpic) {
        this.headpic = headpic;
    }

    public UserDTO getParent() {
        return parent;
    }

    public void setParent(UserDTO parent) {
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

    public Set<RoleUserDTO> getRoles() {
        return roles;
    }

    public void setRoles(Set<RoleUserDTO> roles) {
        this.roles = roles;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getRoleArr() {
        return roleArr;
    }

    public void setRoleArr(String roleArr) {
        this.roleArr = roleArr;
    }

}
