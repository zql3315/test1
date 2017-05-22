package com.infosky.sys.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.infosky.common.mapper.BeanMapper;
import com.infosky.common.query.jpa.Operator;
import com.infosky.common.query.jpa.SearchRequest;
import com.infosky.common.query.jpa.Searchable;
import com.infosky.framework.dao.DAO;
import com.infosky.framework.service.JpaService;
import com.infosky.framework.web.PageResult;
import com.infosky.sys.dao.PermissionDAO;
import com.infosky.sys.dao.ResourceDAO;
import com.infosky.sys.entity.dto.OperationDTO;
import com.infosky.sys.entity.dto.PermissionDTO;
import com.infosky.sys.entity.dto.ResourceDTO;
import com.infosky.sys.entity.po.Permission;
import com.infosky.sys.entity.po.Resource;

/**
 * <一句话功能简述>
 * <功能详细描述>
 * 
 * @author  n004881
 * @version  [版本号, 2015年2月9日]
 */
@Service
@Transactional
public class PermissionService extends JpaService<Permission, PermissionDTO, PageResult<PermissionDTO>, String> {

    @Autowired
    private PermissionDAO dao;

    @Autowired
    private ResourceDAO resourceDAO;

    private static final Logger logger = LoggerFactory.getLogger(PermissionService.class);

    public void saveAll(List<PermissionDTO> entities) {
        //先清除已有权限
        PermissionDTO p = entities.get(0);
        Searchable searchable = new SearchRequest();
        searchable.addSearchParam("role.id", Operator.EQ, p.getRole().getId());
        this.delete(searchable);

        List<Permission> permissions = BeanMapper.mapList(entities, Permission.class);

        dao.save(permissions);

    }

    /**
     *  增加权限
     */
    public boolean addPermission(PermissionDTO d) {
        try {
            if (d != null) {
                // 先清除已有权限
                PermissionDTO p = d;
                this.deletePerssionById(p.getRole().getId());

                ResourceDTO resource = new ResourceDTO();
                OperationDTO operation = new OperationDTO();

                // 添加菜单权限
                String[] rsArray = null;
                rsArray = p.getResource().getId().split(",");
                for (int i = 0; i < rsArray.length; i++) {
                    Resource po = resourceDAO.findOne(rsArray[i]);
                    String pid = po.getParent().getId();
                    if (pid != null && ("0".equals(pid) || po.getType().equals("2") || (po.getChildren() != null && po.getChildren().size() > 0 && po.getType().equals("1")))) {
                        operation.setId("4");
                    } else {
                        operation.setId("5");
                    }
                    resource.setId(rsArray[i]);
                    d.setResource(resource);
                    d.setOperation(operation);
                    super.save(d);
                }
                return true;
            }
        } catch (Exception e) {
            logger.error("", e);
        }
        return false;
    }

    /**
     * 根据角色Id获得该角色拥有的操作权限及资源权限
     * @param rid
     * @return
     */
    public List<ResourceDTO> getResourceByRole(String rid) {
        return BeanMapper.mapList(dao.getResource(rid), ResourceDTO.class);
    }

    public List<OperationDTO> getOperationByRole(String rid) {
        return BeanMapper.mapList(dao.getOperation(rid), OperationDTO.class);
    }

    /**
     * 根据传入的角色id，删除权限
     * @param rid
     */
    public void deletePerssionById(String rid) {
        dao.deleteByRoleId(rid);
    }

    /**
     * 根据传入的角色id，删除权限
     * @param rid
     */
    public void deletePerssionByReId(String id) {
        dao.deleteResourceById(id);
    }

    /** {@inheritDoc} */

    @Override
    protected DAO<Permission, String> getDAO() {
        return dao;
    }

}
