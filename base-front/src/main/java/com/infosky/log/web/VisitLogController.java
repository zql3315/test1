package com.infosky.log.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.infosky.framework.service.PagingService;
import com.infosky.framework.web.CrudController;
import com.infosky.framework.web.PageResult;
import com.infosky.log.entity.dto.VisitLogDTO;
import com.infosky.log.service.impl.VisitLogService;

/**
 * 表操作
 * 
 * @author  n004881
 * @version  [版本号, 2015年2月9日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
@Controller
@RequestMapping("/visitLog")
public class VisitLogController extends CrudController<String, PageResult<VisitLogDTO>, VisitLogDTO> {

    @Autowired
    private VisitLogService service;

    /** {@inheritDoc} */
    public PagingService<VisitLogDTO, PageResult<VisitLogDTO>, String> getService() {
        return service;
    }

}
