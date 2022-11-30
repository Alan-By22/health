package com.ithuipu.service;

import com.ithuipu.pojo.User;

/**
 * @author 11752
 * @创建人 zby
 * @创建时间 2022/11/30---16:02
 * @描述信息
 */

public interface UserService {
    User findByUserName(String username);
}
