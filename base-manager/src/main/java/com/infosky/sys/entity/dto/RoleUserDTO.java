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

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.infosky.framework.entity.dto.DTO;

/**
 * 工程项目
 * 
 * @author  n004881
 * @version  [版本号, 2015年2月3日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class RoleUserDTO extends DTO<String> {

    /**
     * 注释内容
     */
    private static final long serialVersionUID = -1684133362669153672L;

    private RoleDTO role;

    @JsonIgnore
    private UserDTO user;

    /**
     * @return 返回 role
     */
    public RoleDTO getRole() {
        return role;
    }

    /**
     * @param 对role进行赋值
     */
    public void setRole(RoleDTO role) {
        this.role = role;
    }

    /**
     * @return 返回 user
     */
    public UserDTO getUser() {
        return user;
    }

    /**
     * @param 对user进行赋值
     */
    public void setUser(UserDTO user) {
        this.user = user;
    }
}
