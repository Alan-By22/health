package com.ithuipu.security;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ithuipu.pojo.Permission;
import com.ithuipu.pojo.Role;
import com.ithuipu.pojo.User;
import com.ithuipu.service.UserService;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @author 11752
 * @创建人 zby
 * @创建时间 2022/11/30---15:59
 * @描述信息
 */
@Component
public class SpringSecurityUserService implements UserDetailsService {

    /**引入service*/
    @Reference
    private UserService userService;
    /**认证和用户授权*/
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //1.根据用户名来查询用户的信息
        User user = userService.findByUserName(username);
        //2.判断
        if (user == null){
            return null;
        }
        //3.密码的校验和授权
        List<GrantedAuthority> list = new ArrayList<>();
        //注意在业务层进行的数据的封装--取出来
        Set<Role> roles = user.getRoles();
        for (Role role : roles) {
            //授予角色
            list.add(new SimpleGrantedAuthority(role.getKeyword()));
            //根据角色查询权限
            Set<Permission> permissions = role.getPermissions();
            for (Permission permission : permissions) {
                //授权
                list.add(new SimpleGrantedAuthority(permission.getKeyword()));
            }
        }
        UserDetails user1 = new org.springframework.security.core.userdetails.User(username,user.getPassword(),list);
        return user1;
    }
}
