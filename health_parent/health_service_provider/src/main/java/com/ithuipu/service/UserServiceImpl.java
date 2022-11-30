package com.ithuipu.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.ithuipu.dao.PermissionDao;
import com.ithuipu.dao.RoleDao;
import com.ithuipu.dao.UserDao;
import com.ithuipu.pojo.Permission;
import com.ithuipu.pojo.Role;
import com.ithuipu.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

/**
 * @author 11752
 * @创建人 zby
 * @创建时间 2022/11/30---16:12
 * @描述信息
 */

@Service(interfaceClass = UserService.class)
@Transactional
public class UserServiceImpl implements UserService {
    /**
     * 注入roleDao,permissionDao
     */
    @Autowired
    private UserDao userDao;
    @Autowired
    private RoleDao roleDao;
    @Autowired
    private PermissionDao permissionDao;

    @Override
    public User findByUserName(String username) {
        //1.查询user
        User user = userDao.findByUserName(username);
        if (user == null) {
            return null;
        }
        //2.根据用户的id--查询角色的role
        Set<Role> roles = roleDao.findAllByUserId(user.getId());
        //3.根据roleId来查询对应的权限
        if (roles != null && roles.size() > 0) {
            for (Role role : roles) {
                //根据roleId来查询对应的权限
                Set<Permission> permissions = permissionDao.findByRoleId(role.getId());
                if (permissions != null && permissions.size() > 0) {
                    //封装
                    role.setPermissions(permissions);
                }
            }

            //封装
            user.setRoles(roles);
        }
        return user;
    }
}
