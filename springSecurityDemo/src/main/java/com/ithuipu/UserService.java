package com.ithuipu;

import com.ithuipu.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 11752
 * @创建人 zby
 * @创建时间 2022/11/29---18:19
 * @描述信息
 */

public class UserService implements UserDetailsService {


    /**
     * 声明密码
     */
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;


    /**
     * 调用dao获取数据源
     */
    public Map<String, User> map = new HashMap<>();

    /**
     * 方法
     */
    public void initMap() {
        User user1 = new User();
        user1.setUsername("admin");
        user1.setPassword(passwordEncoder.encode("admin"));

        User user2 = new User();
        user2.setUsername("root");
        user2.setPassword(passwordEncoder.encode("root"));

        map.put(user1.getUsername(), user1);
        map.put(user2.getUsername(), user2);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //初始化假数据
        initMap();
        //2.查询,根据用户名查询
        User user = map.get(username);
        //3.判断
        if (user == null) {
            return null;
        }
        //获取密码
        String password = user.getPassword();
        System.out.println(password);
        //校验+赋权限
        List<GrantedAuthority> list = new ArrayList<>();
        if ("root".equals(username)){
            list.add(new SimpleGrantedAuthority("add"));
        }
        list.add(new SimpleGrantedAuthority("update"));
        list.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
        UserDetails user1 = new org.springframework.security.core.userdetails.User(username, password, list);
        return user1;
    }
}
