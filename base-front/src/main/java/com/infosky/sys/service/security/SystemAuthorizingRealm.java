package com.infosky.sys.service.security;

import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AccountException;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;
import com.infosky.common.query.jpa.Operator;
import com.infosky.common.query.jpa.SearchRequest;
import com.infosky.common.query.jpa.Searchable;
import com.infosky.common.util.DynamicSearchUtils;
import com.infosky.common.util.Encodes;
import com.infosky.servlet.CaptchaServlet;
import com.infosky.shiro.service.CaptchaException;
import com.infosky.shiro.service.UsernamePasswordCaptchaToken;
import com.infosky.sys.dao.UserDAO;
import com.infosky.sys.entity.po.Permission;
import com.infosky.sys.entity.po.Resource;
import com.infosky.sys.entity.po.RoleUser;
import com.infosky.sys.entity.po.User;

/**
 * 系统安全认证实现类
 * 
 */
@Service
@DependsOn({
    "userDAO"
})
public class SystemAuthorizingRealm extends AuthorizingRealm {

    private static final Logger logger = LoggerFactory.getLogger(SystemAuthorizingRealm.class);

    private static final String OR_OPERATOR = " or ";

    private static final String AND_OPERATOR = " and ";

    private static final String NOT_OPERATOR = "not ";

    @Autowired
    private UserDAO userDAO;

    /**
     * <p>
     * 通过该注解初始化默认的加密算法
     * <p>
     * 设定密码校验的Hash算法与迭代次数
     * <p>
     * 已经放到配置文件里面配置了
     */
    @PostConstruct
    public void initCredentialsMatcher() {
        // HashedCredentialsMatcher matcher = new HashedCredentialsMatcher(Sha1Hash.ALGORITHM_NAME);
        // matcher.setHashIterations(1024);
        // setCredentialsMatcher(matcher);
    }

    /** {@inheritDoc} */

    /**
     * 授权认可 获取用户角色列表以及拥有的权限列表
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        ShiroUser shiroUser = (ShiroUser) principals.getPrimaryPrincipal();
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();

        // 角色查询
        // List<Role> Roles = roleDAO.findAll();
        final String loginName = shiroUser.getLoginName();

        Searchable s = new SearchRequest();
        s.addSearchParam("loginname", Operator.EQ, loginName);
        Specification<User> specification = DynamicSearchUtils.toSpecification(s);

        User user = userDAO.findOne(specification);
        user.setLastLogin(new Date());
        userDAO.save(user);
        Set<RoleUser> roleUserSet = user.getRoles();
        //
        Collection<String> hasRoles = new HashSet<String>();
        Set<String> permissions = new HashSet<String>();

        for (RoleUser roleUser : roleUserSet) {
            hasRoles.add(roleUser.getRole().getName());

            Set<Permission> PermissionSet = roleUser.getRole().getPermissions();

            for (Permission permission : PermissionSet) {
                List<String> list = Lists.newArrayList();
                Resource resource = permission.getResource();
                list.add("view");
                this.getPermPattern(resource, list);
                Collections.reverse(list);
                String permissPattern = StringUtils.join(list, ":");
                permissions.add(permissPattern);
            }
        }
        if (user.getLoginname().equals("admin")) {
            permissions.add("home:*");
        }
        simpleAuthorizationInfo.addRoles(hasRoles);
        simpleAuthorizationInfo.addStringPermissions(permissions);

        logger.info("{}拥有的角色:{}", shiroUser.getLoginName(), hasRoles);
        logger.info("{}拥有的权限:{}", shiroUser.getLoginName(), permissions);
        return simpleAuthorizationInfo;
    }

    /**
     * 获取父资源列表简称
     * 
     * @param resource
     *            父类资源
     * @param list
     * @return
     */
    private List<String> getPermPattern(Resource resource, List<String> list) {
        list.add(resource.getSn());
        Resource parent = resource.getParent();
        if (parent != null) {
            list = getPermPattern(parent, list);
        }
        return list;
    }

    /** {@inheritDoc} */

    /**
     * 登陆认证
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken arg0) throws AuthenticationException {
        UsernamePasswordCaptchaToken token = (UsernamePasswordCaptchaToken) arg0;
        final String userName = token.getUsername();
        if (userName == null) {
            throw new AccountException("Null usernames are not allowed by this realm.");// 用户名不能为空
        }
        // 增加判断验证码逻辑
        String captcha = token.getCaptcha();
        String exitCode = (String) SecurityUtils.getSubject().getSession().getAttribute(CaptchaServlet.KEY_CAPTCHA);
        if (null == captcha || !captcha.equalsIgnoreCase(exitCode)) {
            throw new CaptchaException("验证码错误");
        }

        Searchable s = new SearchRequest();
        s.addSearchParam("loginname", Operator.EQ, userName);
        Specification<User> specification = DynamicSearchUtils.toSpecification(s);
        User user = null;
        try {
            user = userDAO.findOne(specification);
        } catch (Exception e) {
            e.printStackTrace();
            throw new UnknownAccountException();// 没找到帐号
        }

        if (null == user) {
            throw new UnknownAccountException();// 没找到帐号
        }
        if (user.getStatus() == 1) {
            throw new LockedAccountException(); // 帐号锁定
        }
        byte[] salt = Encodes.decodeHex(user.getSalt());
        // 交给AuthenticatingRealm使用CredentialsMatcher进行密码匹配，如果觉得不好可以自定义实现
        ShiroUser shiroUser = new ShiroUser(user.getLoginname(), user.getName(), user.getId());
        SimpleAuthenticationInfo simpleAuthenticationInfo = new SimpleAuthenticationInfo(shiroUser, user.getPassword(), ByteSource.Util.bytes(salt), getName());
        return simpleAuthenticationInfo;
    }

    /**
     * 支持or and not 关键词 不支持and or混用
     * 
     * @param principals
     * @param permission
     * @return
     */
    public boolean isPermitted(PrincipalCollection principals, String permission) {
        if (permission != null && permission.contains(OR_OPERATOR)) {
            String[] permissions = permission.split(OR_OPERATOR);
            for (String orPermission : permissions) {
                if (isPermittedWithNotOperator(principals, orPermission)) {
                    return true;
                }
            }
            return false;
        } else if (permission != null && permission.contains(AND_OPERATOR)) {
            String[] permissions = permission.split(AND_OPERATOR);
            for (String orPermission : permissions) {
                if (!isPermittedWithNotOperator(principals, orPermission)) {
                    return false;
                }
            }
            return true;
        } else {
            return isPermittedWithNotOperator(principals, permission);
        }
    }

    private boolean isPermittedWithNotOperator(PrincipalCollection principals, String permission) {
        if (permission != null && permission.startsWith(NOT_OPERATOR)) {
            return !super.isPermitted(principals, permission.substring(NOT_OPERATOR.length()));
        } else {
            return super.isPermitted(principals, permission);
        }
    }

    /**
     * 清除所有用户授权信息缓存.
     */
    public void clearAllCachedAuthorizationInfo() {
        getAuthorizationCache().clear();
    }

    public void clearAllCachedAuthenticationInfo() {
        getAuthenticationCache().clear();
    }

    public void clearAllCache() {
        clearAllCachedAuthenticationInfo();
        clearAllCachedAuthorizationInfo();
    }

    /**
     * 更新用户授权信息缓存.
     */
    public void clearCachedAuthorizationInfo(String principal) {
        SimplePrincipalCollection principals = new SimplePrincipalCollection(principal, getName());
        clearCachedAuthorizationInfo(principals);
    }

}
