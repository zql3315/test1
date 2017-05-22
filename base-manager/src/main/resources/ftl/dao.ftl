package ${project.rPackage}.${project.mName}.dao;

import org.springframework.stereotype.Repository;

import ${project.rPackage}.${project.mName}.entity.po.${table.eName};
import com.infosky.framework.dao.DAO;

/**
 * <一句话功能简述>
 * <功能详细描述>
 * 
 * @author  xx
 * @version  [版本号, xx年xx月xx日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
@Repository
public interface ${table.eName}DAO extends DAO<${table.eName}, String>
{
    
}