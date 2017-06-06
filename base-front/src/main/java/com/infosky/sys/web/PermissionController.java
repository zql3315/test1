package com.infosky.sys.web;

import java.util.List;
import java.util.Map;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.collect.Maps;
import com.infosky.common.query.jpa.Operator;
import com.infosky.common.query.jpa.SearchRequest;
import com.infosky.common.query.jpa.Searchable;
import com.infosky.framework.annotation.Description;
import com.infosky.framework.service.PagingService;
import com.infosky.framework.web.CrudController;
import com.infosky.framework.web.PageResult;
import com.infosky.sys.entity.dto.OperationDTO;
import com.infosky.sys.entity.dto.PermissionDTO;
import com.infosky.sys.entity.dto.ResourceDTO;
import com.infosky.sys.entity.dto.RoleDTO;
import com.infosky.sys.service.impl.OperationService;
import com.infosky.sys.service.impl.PermissionService;
import com.infosky.sys.service.impl.RoleService;

/**
 * 表操作
 * 
 * @author  n004881
 * @version  [版本号, 2015年2月9日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
@Controller
@RequestMapping("/permission")
@Description(value = "权限管理")
public class PermissionController extends CrudController<String, PageResult<PermissionDTO>, PermissionDTO> {

    private static final Logger logger = LoggerFactory.getLogger(PermissionController.class);

    @Autowired
    private PermissionService service;

    @Autowired
    private RoleService roleService;

    @Autowired
    private OperationService operationService;

    /**
     * 
     * @param model
     * @param rid
     * @return
     */
    @RequestMapping(value = "/right/{rid}")
    public String getRight(Model model, @PathVariable String rid) {
        PermissionDTO d = new PermissionDTO();
        RoleDTO roleDTO = roleService.find(rid);
        d.setRole(roleDTO);
        try {
            Subject subject = SecurityUtils.getSubject();
            if (!subject.getPrincipal().toString().equals("admin")) {
                SecurityUtils.getSubject().checkRole(roleDTO.getName());
                model.addAttribute("message", "不能给自己授权");
                return "unauthorized";
            }
        } catch (AuthorizationException e) {

        }

        //获得当前角色拥有的操作权限及菜单权限
        List<ResourceDTO> resourcelist = service.getResourceByRole(rid);
        StringBuffer resourceId = new StringBuffer();
        StringBuffer resourceName = new StringBuffer();

        if (resourcelist.size() > 0) {
            for (ResourceDTO resource : resourcelist) {
                resourceId.append(resource.getId());
                resourceId.append(",");
                resourceName.append(resource.getName());
                resourceName.append(",");
            }
            resourceId = resourceId.deleteCharAt(resourceId.length() - 1);
            resourceName = resourceName.deleteCharAt(resourceName.length() - 1);
        }

        List<OperationDTO> operationList = service.getOperationByRole(rid);
        StringBuffer oper = new StringBuffer();

        if (operationList.size() > 0) {
            for (OperationDTO operation : operationList) {
                oper.append(operation.getId());
                oper.append(",");
            }
            oper = oper.deleteCharAt(oper.length() - 1);
        }

        ResourceDTO resourceDTO = new ResourceDTO();
        OperationDTO operationDTO = new OperationDTO();

        resourceDTO.setId(resourceId.toString());
        resourceDTO.setName(resourceName.toString());
        operationDTO.setId(oper.toString());
        d.setResource(resourceDTO);
        d.setOperation(operationDTO);

        model.addAttribute("model", d);
        model.addAttribute("operations", operationService.findAll(null));

        return "/role/right";
    }

    @RequestMapping(value = "findPermission")
    @ResponseBody
    public List<PermissionDTO> findPermission(@RequestBody PermissionDTO permission) {
        Searchable searchable = new SearchRequest();
        searchable.addSearchParam("role.id", Operator.EQ, permission.getRole().getId());
        searchable.addSearchParam("resource.id", Operator.EQ, permission.getResource().getId());

        List<PermissionDTO> permissionList = (List<PermissionDTO>) service.findAll(searchable);

        return permissionList;
    }

    @RequestMapping(value = "addALL")
    @ResponseBody
    public Map<String, Object> add(@RequestBody List<PermissionDTO> d) {
        Map<String, Object> result = Maps.newHashMap();
        result.put("result", false);

        try {
            if (!d.isEmpty()) {
                service.saveAll(d);
            }

            result.put("result", true);
        } catch (Exception e) {
            logger.error("", e);
        }

        return result;
    }

    //    /**
    //     * @param d
    //     * @return
    //     */
    //    @RequestMapping(value = "addALL")
    //    @ResponseBody
    //    public Map<String, Object> add(@RequestBody  PermissionDTO d)
    //    {
    //        Map<String, Object> result = Maps.newHashMap();
    //        result.put("result", false);
    //        boolean b = service.addPermission(d);
    //        if(b) result.put("result", true);
    //        return result;
    //    }

    /**
     * 清除用户权限
     * @param d
     * @return
     */
    @RequestMapping(value = "clear")
    @ResponseBody
    public Map<String, Object> clearPermission(@RequestBody PermissionDTO d) {
        Map<String, Object> result = Maps.newHashMap();
        result.put("result", false);

        try {
            Searchable searchable = new SearchRequest();
            searchable.addSearchParam("role.id", Operator.EQ, d.getRole().getId());
            searchable.addSearchParam("resource.id", Operator.EQ, d.getResource().getId());

            service.delete(searchable);

            result.put("result", true);
        } catch (Exception e) {
            logger.error("", e);
        }

        return result;
    }

    /**
     * 清除用户所有权限
     * @param d
     * @return
     */
    @RequestMapping(value = "clearAll")
    @ResponseBody
    public Map<String, Object> clearAllPermission(@RequestBody String rid) {
        System.out.println(rid);
        Map<String, Object> result = Maps.newHashMap();
        result.put("result", false);

        try {
            service.deletePerssionByReId(rid);
            result.put("result", true);
        } catch (Exception e) {
            logger.error("", e);
        }

        return result;
    }

    /** {@inheritDoc} */
    public PagingService<PermissionDTO, PageResult<PermissionDTO>, String> getService() {
        return service;
    }

}
