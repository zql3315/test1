package com.infosky.sys.web;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.infosky.common.query.jpa.Operator;
import com.infosky.common.query.jpa.SearchRequest;
import com.infosky.common.query.jpa.Searchable;
import com.infosky.common.util.DynamicSearchUtils;
import com.infosky.framework.annotation.Description;
import com.infosky.framework.service.PagingService;
import com.infosky.framework.web.CrudController;
import com.infosky.framework.web.PageResult;
import com.infosky.sys.entity.dto.RoleDTO;
import com.infosky.sys.service.impl.PermissionService;
import com.infosky.sys.service.impl.RoleService;
import com.infosky.sys.service.impl.UserService;

/**
 * 表操作
 * 
 * @author  n004881
 * @version  [版本号, 2015年2月9日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
@Controller
@RequestMapping("/role")
@Description(value = "角色管理")
public class RoleController extends CrudController<String, PageResult<RoleDTO>, RoleDTO> {

    @Autowired
    private RoleService service;

    @Autowired
    private PermissionService permissionService;

    @Autowired
    private UserService userService;

    private static final Logger logger = LoggerFactory.getLogger(CrudController.class);

    /**
     * 列表页面table分页数据Search
     * @param page
     * @return
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    @Description(value = "查询列表数据")
    @Override
    public PageResult<RoleDTO> list(@ModelAttribute PageResult<RoleDTO> page, @ModelAttribute("sort") String sorts, HttpServletRequest request) {
        Searchable s = DynamicSearchUtils.toSearchable(request);
        try {
            if (StringUtils.isNotBlank(sorts)) {
                Sort sort;
                if (JSONObject.fromObject(sorts).getString("sort").toUpperCase().equals("DESC")) {
                    sort = new Sort(Sort.Direction.DESC, JSONObject.fromObject(sorts).getString("fileName").toString());
                } else {
                    sort = new Sort(Sort.Direction.ASC, JSONObject.fromObject(sorts).getString("fileName").toString());
                }
                getService().findAll(s, page, sort);
            } else {
                getService().findAll(s, page);
            }
        } catch (Exception e) {
            logger.error("", e);
        }
        Subject subject = SecurityUtils.getSubject();
        if (!subject.getPrincipal().toString().equals("admin")) {//,管理员用户管理所有角色
            List<RoleDTO> newRoleList = Lists.newArrayList();
            String userName = subject.getPrincipal().toString();
            for (RoleDTO dto : page.getData()) {
                if (userName.equals(dto.getCreator())) {//只能查看自己创建的角色
                    newRoleList.add(dto);
                }
            }
            page.setData(newRoleList);
        }
        return page;
    }

    @RequestMapping(value = "addRole")
    @ResponseBody
    @Description(value = "添加数据")
    public Map<String, Object> addRole(@RequestBody RoleDTO b) {
        Map<String, Object> result = Maps.newHashMap();
        result.put("result", false);

        int count = service.getRoleCount(b.getName(), null);

        if (count != 0) {
            result.put("code", "0001"); //code = 0001代表角色名称重复
            return result;
        }
        b.setCreator(SecurityUtils.getSubject().getPrincipal().toString());
        try {
            getService().save(b);
            result.put("result", true);
        } catch (Exception e) {
            logger.error("", e);
        }

        return result;
    }

    @RequestMapping(value = "editRole")
    @ResponseBody
    @Description(value = "更新数据")
    public Map<String, Object> editRole(@RequestBody RoleDTO b) {
        Map<String, Object> result = Maps.newHashMap();
        result.put("result", false);

        int count = service.getRoleCount(b.getName(), b.getId());

        if (count != 0) {
            result.put("code", "0001"); //code = 0001代表角色名称重复
            return result;
        }
        Subject user = SecurityUtils.getSubject();
        b.setCreator(user.getPrincipal().toString());
        try {
            service.updateRole(b);
            result.put("result", true);
        } catch (Exception e) {
            logger.error("", e);
        }

        return result;
    }

    @RequestMapping(value = "delete")
    @ResponseBody
    @Description(value = "根据多个id批量删除用户")
    public Map<String, Object> delete(@RequestBody RoleDTO b) {
        Map<String, Object> result = Maps.newHashMap();
        result.put("result", false);
        try {
            //检查该角色下面是否有用户
            int count = service.findRoleCount(service.find(b.getId()));
            //校验用户登录名称是否重复
            if (count != 0) {
                result.put("code", "0001");
                return result;
            }
            //删除角色关联的资源权限
            Searchable s = new SearchRequest();
            s.addSearchParam("role.id", Operator.EQ, b.getId());
            permissionService.delete(s);
            service.delete(b);
            result.put("result", true);
        } catch (Exception e) {
            logger.error("", e);
        }

        return result;
    }

    @RequestMapping(value = "deleteRole")
    @ResponseBody
    @Description(value = "根据多个id批量删除用户")
    public Map<String, Object> deleteRole(@RequestBody List<RoleDTO> roles) {
        Map<String, Object> result = Maps.newHashMap();
        result.put("result", false);
        try {
            //检查该角色下面是否有用户
            for (RoleDTO role : roles) {
                int count = service.findRoleCount(service.find(role.getId()));
                if (count != 0) {
                    result.put("code", "0001");
                    return result;
                }
            }
            //删除角色关联的资源权限
            for (RoleDTO role : roles) {
                Searchable s = new SearchRequest();
                s.addSearchParam("role.id", Operator.EQ, role.getId());
                permissionService.delete(s);;
            }
            service.deleteAll(roles);
            result.put("result", true);
        } catch (Exception e) {
            logger.error("", e);
        }

        return result;
    }

    /** {@inheritDoc} */
    public PagingService<RoleDTO, PageResult<RoleDTO>, String> getService() {
        return service;
    }

}
