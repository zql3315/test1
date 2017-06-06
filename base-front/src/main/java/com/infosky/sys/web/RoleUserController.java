package com.infosky.sys.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.infosky.framework.annotation.Description;
import com.infosky.framework.service.PagingService;
import com.infosky.framework.web.CrudController;
import com.infosky.framework.web.PageResult;
import com.infosky.sys.entity.dto.RoleUserDTO;
import com.infosky.sys.service.impl.RoleUserService;

/**
 * 表操作
 * 
 * @author  n004881
 * @version  [版本号, 2015年2月9日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
@Controller
@RequestMapping("/roleUser")
@Description(value = "角色管理")
public class RoleUserController extends CrudController<String, PageResult<RoleUserDTO>, RoleUserDTO> {

    @Autowired
    private RoleUserService service;

    /** {@inheritDoc} */
    public PagingService<RoleUserDTO, PageResult<RoleUserDTO>, String> getService() {
        return service;
    }

}
