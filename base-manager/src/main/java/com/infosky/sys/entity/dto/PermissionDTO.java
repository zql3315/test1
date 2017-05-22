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

import com.infosky.framework.entity.dto.DTO;

/**
 * 工程项目
 * 
 * @author  n004881
 * @version  [版本号, 2015年2月3日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class PermissionDTO extends DTO<String> {

    /**
     * 注释内容
     */
    private static final long serialVersionUID = -1684133362669153672L;

    /**
     * 角色与资源关系标识
     */
    private RoleDTO role;

    private ResourceDTO resource;

    /**
     * 操作
     */
    private OperationDTO operation;

    /**
     * @return 返回 operation
     */
    public OperationDTO getOperation() {
        return operation;
    }

    /**
     * @param 对operation进行赋值
     */
    public void setOperation(OperationDTO operation) {
        this.operation = operation;
    }

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
     * @return 返回 resource
     */
    public ResourceDTO getResource() {
        return resource;
    }

    /**
     * @param 对resource进行赋值
     */
    public void setResource(ResourceDTO resource) {
        this.resource = resource;
    }
}
