package com.infosky.sys.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.infosky.framework.annotation.Description;
import com.infosky.framework.service.PagingService;
import com.infosky.framework.web.CrudController;
import com.infosky.framework.web.PageResult;
import com.infosky.sys.entity.dto.OperationDTO;
import com.infosky.sys.service.impl.OperationService;

/**
 * 表操作
 * 
 * @author  n004881
 * @version  [版本号, 2015年2月9日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
@Controller
@RequestMapping("/operation")
@Description(value = "操作权限管理")
public class OperationController extends CrudController<String, PageResult<OperationDTO>, OperationDTO> {

    @Autowired
    private OperationService service;

    /** {@inheritDoc} */
    public PagingService<OperationDTO, PageResult<OperationDTO>, String> getService() {
        return service;
    }

}
