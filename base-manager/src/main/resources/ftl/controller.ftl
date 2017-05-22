package ${project.rPackage}.${project.mName}.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import ${project.rPackage}.${project.mName}.entity.dto.${table.eName}DTO;
import ${project.rPackage}.${project.mName}.service.impl.${table.eName}Service;
import com.infosky.framework.web.PageResult;
import com.infosky.framework.service.PagingService;
import com.infosky.framework.web.CrudController;
/**
 * 表操作
 * 
 * @author  xx
 * @version  [版本号, xx年xx月xx日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
@Controller
@RequestMapping("/${table.sn}")
public class ${table.eName}Controller extends CrudController<String, PageResult<${table.eName}DTO>, ${table.eName}DTO>
{
    @Autowired
    private ${table.eName}Service service;
    
    /** {@inheritDoc} */
    public PagingService<${table.eName}DTO, PageResult<${table.eName}DTO>, String> getService()
    {
        return service;
    }
    
}
