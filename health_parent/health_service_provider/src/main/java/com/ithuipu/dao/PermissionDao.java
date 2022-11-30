package com.ithuipu.dao;

import com.ithuipu.pojo.Permission;

import java.util.Set;

/**
 * @author 11752
 * @创建人 zby
 * @创建时间 2022/11/30---16:16
 * @描述信息
 */

public interface PermissionDao {
    Set<Permission> findByRoleId(Integer id);
}
