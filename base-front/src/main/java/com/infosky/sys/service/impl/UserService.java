package com.infosky.sys.service.impl;

import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.infosky.common.mapper.BeanMapper;
import com.infosky.common.util.Encodes;
import com.infosky.common.util.encrypt.Digests;
import com.infosky.framework.annotation.Description;
import com.infosky.framework.dao.DAO;
import com.infosky.framework.service.JpaService;
import com.infosky.framework.web.PageResult;
import com.infosky.sys.dao.UserDAO;
import com.infosky.sys.entity.dto.UserDTO;
import com.infosky.sys.entity.po.RoleUser;
import com.infosky.sys.entity.po.User;

/**
 * <一句话功能简述>
 * <功能详细描述>
 * 
 * @author  zan
 * @version  [版本号, 2015年2月9日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
@Service
@Transactional
public class UserService extends JpaService<User, UserDTO, PageResult<UserDTO>, String> {

    @Autowired
    private UserDAO dao;

    /** {@inheritDoc} */

    @Override
    public UserDTO save(UserDTO t) {
        User user = BeanMapper.map(t, User.class);

        Set<RoleUser> roles = user.getRoles();

        for (RoleUser roleUser : roles) {
            roleUser.setUser(user);
        }

        user.setRoles(roles);
        byte[] salt = null;
        if (StringUtils.isBlank(user.getSalt())) {
            salt = Digests.generateSalt(8);
            user.setSalt(Encodes.encodeHex(salt));
        } else {
            salt = user.getSalt().getBytes();
            user.setSalt(Encodes.encodeHex(salt));
        }

        //新增密码默认
        if (user.getPassword() == null) {
            user.setPassword("123456");
        }

        byte[] hashPassword = Digests.sha1(user.getPassword().getBytes(), user.getSalt().getBytes(), 1024);
        user.setPassword(Encodes.encodeHex(hashPassword));

        user = dao.save(user);
        UserDTO dto = BeanMapper.map(user, UserDTO.class);

        return dto;
    }

    @Description(value = "更新用户数据")
    public void editUser(User b) {

        User user = dao.findOne(b.getId());
        user.setName(b.getName());
        user.setTelephone(b.getTelephone());
        user.setEmail(b.getEmail());
        if (b.getRoles() != null) {
            user.getRoles().clear();//先删除原有的用户和角色关联关系，再进行添加
            user.getRoles().addAll(b.getRoles());
        }
    }

    @Override
    public UserDTO update(UserDTO t) {
        User user = BeanMapper.map(t, User.class);

        Set<RoleUser> roles = user.getRoles();

        for (RoleUser roleUser : roles) {
            roleUser.setUser(user);
        }

        user.setRoles(roles);

        user = dao.save(user);
        UserDTO dto = BeanMapper.map(user, UserDTO.class);

        return dto;
    }

    /** {@inheritDoc} */

    @Override
    protected DAO<User, String> getDAO() {
        return dao;
    }

    public UserDTO findByLoginname(String loginname) {
        User user = dao.findByLoginname(loginname);
        if (user != null) {
            return BeanMapper.map(user, UserDTO.class);
        }
        return null;
    }

    public int findLoginnameCount(String loginname, String id) {
        if (id == null) {
            return dao.findLoginnameCount(loginname);
        }
        return dao.findLoginnameCount(loginname, id);
    }

    public int getIdByName(String name) {
        return dao.getID(name);
    }

}
