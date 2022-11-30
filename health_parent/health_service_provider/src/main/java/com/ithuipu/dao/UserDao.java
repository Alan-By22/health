package com.ithuipu.dao;

import com.ithuipu.pojo.User;

/**
    * @author 11752
    * @创建人   zby
    * @创建时间 2022/11/30---16:15
    * @描述信息
*/

public interface UserDao {
    User findByUserName(String username);
}
