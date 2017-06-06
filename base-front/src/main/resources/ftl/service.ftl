package ${project.rPackage}.${project.mName}.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ${project.rPackage}.${project.mName}.dao.${table.eName}DAO;
import ${project.rPackage}.${project.mName}.entity.dto.${table.eName}DTO;
import ${project.rPackage}.${project.mName}.entity.po.${table.eName};
import com.infosky.framework.dao.DAO;
import com.infosky.framework.web.PageResult;
import com.infosky.framework.service.JpaService;

/**
 * <一句话功能简述>
 * <功能详细描述>
 * 
 * @author  xx
 * @version  [版本号, xx年xx月xx日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
@Service
@Transactional
public class ${table.eName}Service extends JpaService<${table.eName},${table.eName}DTO,PageResult<${table.eName}DTO>,String>
{
    @Autowired
    private ${table.eName}DAO dao;
    
    /** {@inheritDoc} */
     
    @Override
    protected DAO<${table.eName}, String> getDAO()
    {
        return dao;
    }
}
