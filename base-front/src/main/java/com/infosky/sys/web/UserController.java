package com.infosky.sys.web;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.subject.support.DefaultSubjectContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.collect.Maps;
import com.infosky.common.mapper.BeanMapper;
import com.infosky.common.query.jpa.Operator;
import com.infosky.common.query.jpa.SearchRequest;
import com.infosky.common.query.jpa.Searchable;
import com.infosky.common.util.DynamicSearchUtils;
import com.infosky.common.util.Encodes;
import com.infosky.common.util.date.DateUtils;
import com.infosky.common.util.encrypt.Digests;
import com.infosky.framework.annotation.Description;
import com.infosky.framework.service.PagingService;
import com.infosky.framework.web.CrudController;
import com.infosky.framework.web.PageResult;
import com.infosky.sys.entity.dto.ChangePasswordDTO;
import com.infosky.sys.entity.dto.RoleUserDTO;
import com.infosky.sys.entity.dto.UserDTO;
import com.infosky.sys.entity.po.User;
import com.infosky.sys.service.impl.UserService;
import com.infosky.sys.service.security.ShiroUser;

/**
 * 用户管理
 * 
 * @author  n004881
 * @version  [版本号, 2015年2月9日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
@Controller
@RequestMapping("/user")
@Description(value = "用户管理")
public class UserController extends CrudController<String, PageResult<UserDTO>, UserDTO> {

    @Autowired
    private UserService service;

    @Autowired
    private SessionDAO sessionDAO;

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    /**
     * 列表页面table分页数据Search
     * @param page
     * @return
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping(value = "/listUser")
    @ResponseBody
    @Description(value = "查询列表数据")
    public PageResult<UserDTO> listUser(@ModelAttribute PageResult<UserDTO> page, @ModelAttribute("sort") String sorts, HttpServletRequest request) {
        Searchable s = DynamicSearchUtils.toSearchable(request);
        Sort sort = parseSort(sorts);
        getService().findAll(s, page, sort);
        List<UserDTO> list = page.getData();
        Subject subject = SecurityUtils.getSubject();
        ShiroUser shiroUser = (ShiroUser) subject.getPrincipal();
        Iterator<UserDTO> iterator = list.iterator();
        while (iterator.hasNext() && !shiroUser.getLoginName().equals("admin")) {
            UserDTO userDTO = iterator.next();
            if (userDTO.getParent() != null && !userDTO.getParent().getId().equals(shiroUser.getId()) && !userDTO.getId().equals(shiroUser.getId())) {
                iterator.remove();
            }
            if (userDTO.getParent() == null && !shiroUser.getLoginName().equals("admin")) {
                iterator.remove();
            }
        }
        StringBuffer sb = new StringBuffer();
        for (UserDTO userDTO : list) {
            Set<RoleUserDTO> roles = userDTO.getRoles();
            for (RoleUserDTO roleUser : roles) {
                sb.append(roleUser.getRole().getName());
                sb.append(",");
            }
            if ((sb.toString().length() > 0)) {
                userDTO.setRole((sb.toString()).substring(0, (sb.toString()).length() - 1));
            }
            sb.delete(0, sb.length());
        }

        return page;
    }

    /**
     * 修改用户密码
     * @param model
     * @return
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping(value = "changePassword/{id}")
    @Description(value = "修改用户密码")
    public String changePaswordView(@PathVariable String id, Model model) {
        UserDTO userDTO = service.find(id);
        ChangePasswordDTO d = new ChangePasswordDTO();
        d.setLoginName(userDTO.getLoginname());
        d.setUserName((userDTO.getName()));
        d.setUserId(userDTO.getId());
        RoleUserDTO roleDTO = new RoleUserDTO();
        Iterator<RoleUserDTO> iter = userDTO.getRoles().iterator();
        while (iter.hasNext()) {
            roleDTO = iter.next();
        }

        d.setRoleName(roleDTO.getRole().getName());

        model.addAttribute("model", d);

        return getView("changePassword");
    }

    /**
     * 修改用户密码
     * @param model
     * @return
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping(value = "changePassword")
    @Description(value = "修改用户密码")
    public String changePaswordView(Model model) {
        ChangePasswordDTO d = new ChangePasswordDTO();

        Subject operator = SecurityUtils.getSubject();
        d.setLoginName((((ShiroUser) operator.getPrincipal()).getLoginName()));

        UserDTO userDTO = service.findByLoginname(d.getLoginName());
        d.setUserName((userDTO.getName()));
        d.setUserId(userDTO.getId());
        RoleUserDTO roleDTO = new RoleUserDTO();
        Iterator<RoleUserDTO> iter = userDTO.getRoles().iterator();
        while (iter.hasNext()) {
            roleDTO = iter.next();
        }

        d.setRoleName(roleDTO.getRole().getName());

        model.addAttribute("model", d);

        return getView("changePassword");
    }

    @RequestMapping(value = "saveChangePassword")
    @ResponseBody
    @Transactional
    @Description(value = "保存修改的密码")
    public Map<String, Object> saveChangePassword(@RequestBody ChangePasswordDTO b) {
        Map<String, Object> result = Maps.newHashMap();
        result.put("result", false);

        UserDTO oldUserDTO = service.find(b.getUserId());

        String oldPassword = oldUserDTO.getPassword();
        // 得到老密码的加密盐
        byte[] soltNew = Encodes.decodeHex(oldUserDTO.getSalt());

        // 将新密码根据老加密盐加密
        byte[] newPassBytes = Digests.sha1(b.getPassword().getBytes(), soltNew, 1024);

        byte[] oldPassBytes = Digests.sha1(b.getOldPassword().getBytes(), soltNew, 1024);

        // 得到加密过的新密码
        String newPasswordMD5 = Encodes.encodeHex(newPassBytes);

        String oldPasswordMD5 = Encodes.encodeHex(oldPassBytes);

        if (!oldPassword.equals(oldPasswordMD5)) {
            result.put("code", "0001"); // code = 0001代表旧密码输入错误
            return result;
        }

        if (oldPassword.equals(newPasswordMD5)) {
            result.put("code", "0002"); // code=0002代表新密码与旧密码相同
            return result;
        }

        try {
            // 1.更新用户密码 2.更新操作员密码
            oldUserDTO.setPassword(b.getPassword());
            byte[] salt = Digests.generateSalt(8);
            byte[] hashPassword = Digests.sha1(b.getPassword().getBytes(), salt, 1024);
            oldUserDTO.setPassword(Encodes.encodeHex(hashPassword));
            oldUserDTO.setSalt(Encodes.encodeHex(salt));

            service.update(oldUserDTO);

            result.put("result", true);
        } catch (Exception e) {
            logger.error("", e);
        }

        return result;
    }

    /**
     * 预览列表页面
     * @param model
     * @return
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping(value = "resetPassword")
    @Description(value = "预览列表页面")
    public String resetPasswordView(Model model) {
        return getView("resetPassword");
    }

    @RequestMapping(value = "saveResetPassword")
    @ResponseBody
    @Transactional
    @Description(value = "保存重置的密码")
    public Map<String, Object> saveResetPassword(@RequestBody ChangePasswordDTO b) {
        Map<String, Object> result = Maps.newHashMap();
        result.put("result", false);

        UserDTO userDTO = service.findByLoginname(b.getLoginName());

        if (b.getLoginName().equals("admin")) {
            result.put("code", "0002"); // code = 0002代表admin账户无法重置
            return result;
        }

        // 得到老密码的加密盐
        byte[] soltNew = Encodes.decodeHex(userDTO.getSalt());

        // 将新密码根据老加密盐加密
        byte[] newPassBytes = Digests.sha1(b.getPassword().getBytes(), soltNew, 1024);
        // 得到加密过的新密码
        String newPasswordMD5 = Encodes.encodeHex(newPassBytes);

        try {
            userDTO.setPassword(newPasswordMD5);

            service.update(userDTO);

            result.put("result", true);
        } catch (Exception e) {
            logger.error("", e);
        }

        return result;
    }

    @RequestMapping(value = "addUser")
    @ResponseBody
    @Description(value = "添加用户数据")
    public Map<String, Object> addUser(@Valid @RequestBody UserDTO b, Errors errors) {
        Map<String, Object> result = Maps.newHashMap();
        result.put("result", false);
        if (errors.hasErrors()) {
            result.put("message", errors.getFieldErrors().get(0).getDefaultMessage());
            return result;
        }
        int count = service.findLoginnameCount(b.getLoginname(), null);
        Subject subject = SecurityUtils.getSubject();
        ShiroUser ShiroUser = (com.infosky.sys.service.security.ShiroUser) subject.getPrincipal();
        UserDTO UserDTO = new UserDTO();
        UserDTO.setId(ShiroUser.getId());
        b.setParent(UserDTO);
        // 校验用户登录名是否重复
        if (count != 0) {
            result.put("code", "0001");
            return result;
        }

        try {
            getService().save(b);
            result.put("result", true);
        } catch (Exception e) {
            logger.error("", e);
        }

        return result;
    }

    /**
     * 编辑页面
     * @param model
     * @return
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping(value = "toEditUser/{id}")
    @Description(value = "打开编辑页面")
    public String toEditUser(@PathVariable String id, Model model) {
        UserDTO d = this.getService().find(id);

        Set<RoleUserDTO> roles = d.getRoles();

        StringBuffer role = new StringBuffer();

        for (RoleUserDTO roleUser : roles) {
            role.append(roleUser.getRole().getId());
            role.append(",");
        }

        if ((role.toString().length() > 0)) {
            d.setRole((role.toString()).substring(0, (role.toString()).length() - 1));
        }

        model.addAttribute("model", d);

        return getView("edit");
    }

    @RequestMapping(value = "editUser")
    @ResponseBody
    @Description(value = "更新用户数据")
    public Map<String, Object> editUser(@RequestBody UserDTO b) {
        Map<String, Object> result = Maps.newHashMap();
        result.put("result", false);
        int count = service.findLoginnameCount(b.getLoginname(), b.getId());
        // 校验用户登录名称是否重复
        if (count != 0) {
            result.put("code", "0001");
            return result;
        }
        if (b.getRoles().size() <= 0) {
            result.put("code", "0002");
            return result;
        }
        try {
            Iterator<RoleUserDTO> i = b.getRoles().iterator();
            while (i.hasNext()) {
                RoleUserDTO roleUserDTO = i.next();
                UserDTO userDTO = new UserDTO();
                userDTO.setId(b.getId());
                roleUserDTO.setUser(userDTO);
            }
            service.editUser(BeanMapper.map(b, User.class));
            result.put("result", true);
            ShiroUser user = (ShiroUser) SecurityUtils.getSubject().getPrincipal();
            user.name = b.getName();
        } catch (Exception e) {
            logger.error("", e);
        }

        return result;
    }

    @RequestMapping(value = "deleteUser")
    @ResponseBody
    @Description(value = "根据多个id批量删除用户")
    public Map<String, Object> deleteUser(@RequestBody List<UserDTO> b) {
        Map<String, Object> result = Maps.newHashMap();
        result.put("result", false);

        // admin账户无法删除，选中删除时，需要提示错误
        for (UserDTO userDTO : b) {
            String name = service.find(userDTO.getId()).getName();
            if (("admin").equals(name)) {
                result.put("code", "0002");
                result.put("msg", "admin账号为系统超级管理员，无法删除！");
                return result;
            }
        }
        try {
            Subject subject = SecurityUtils.getSubject();
            ShiroUser shirUser = (ShiroUser) subject.getPrincipal();
            int flag = 0;
            Searchable s = null;
            for (UserDTO userDTO : b) {
                if (userDTO.getId() != null && userDTO.getId().equals(shirUser.getId())) {
                    flag = 1;
                    break;
                }
                s = new SearchRequest();
                s.addSearchParam("parent.id", Operator.EQ, userDTO.getId());
                if (service.find(s) != null) {
                    flag = 2;
                    break;
                }

            }
            if (flag == 1) {
                result.put("msg", "不能删除自己");
                return result;
            } else if (flag == 2) {
                result.put("msg", "删除的用户下面有子用户，请先删除子用户");//
                return result;
            }
            for (UserDTO userDTO : b) {
                service.delete(service.find(userDTO.getId()));
            }
            result.put("result", true);
        } catch (Exception e) {
            logger.error("", e);
        }

        return result;
    }

    /**
     * 在线人数
     * @param request
     * @return
     */
    @RequestMapping(value = "online")
    public String online(HttpServletRequest request) {
        Collection<Session> sessions = sessionDAO.getActiveSessions();
        List<Map<String, Object>> sessionList = new ArrayList<Map<String, Object>>();
        if (sessions != null && sessions.size() > 0) {
            for (Session session : sessions) {
                if (session.getAttribute(DefaultSubjectContext.AUTHENTICATED_SESSION_KEY) != null) {
                    Map<String, Object> map = new HashMap<String, Object>();
                    map.put("id", session.getId());
                    map.put("startTimestamp", DateUtils.date2Str(session.getStartTimestamp(), "yyyy-MM-dd HH:mm:ss"));
                    map.put("host", session.getHost());
                    map.put("username", session.getAttribute(DefaultSubjectContext.PRINCIPALS_SESSION_KEY));
                    map.put("lastAccessTime", DateUtils.date2Str(session.getLastAccessTime(), "yyyy-MM-dd HH:mm:ss"));
                    map.put("status", session.getAttribute(DefaultSubjectContext.AUTHENTICATED_SESSION_KEY));
                    sessionList.add(map);
                }
            }
        }
        request.setAttribute("sessionList", sessionList);
        return getView("online");
    }

    /**
     * 踢人下线
     * @param request
     * @return
     */
    @RequestMapping(value = "offline")
    @ResponseBody
    public Boolean offline(HttpServletRequest request) {
        Session session = getSessionByUsername(request.getParameter("sessionid"));
        if (session != null) {
            sessionDAO.delete(session);
            return true;
        }
        return false;
    }

    /**
     * @param username
     * @return
     */
    private Session getSessionByUsername(String id) {
        Collection<Session> sessions = sessionDAO.getActiveSessions();
        for (Session session : sessions) {
            if (session.getAttribute(DefaultSubjectContext.AUTHENTICATED_SESSION_KEY) != null) {
                if (null != session && StringUtils.equals(String.valueOf(session.getId()), id)) {
                    return session;
                }
            }
        }
        return null;
    }

    /** {@inheritDoc} */
    public PagingService<UserDTO, PageResult<UserDTO>, String> getService() {
        return service;
    }

}
