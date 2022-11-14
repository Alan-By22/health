package com.ithuipu.dao;

import com.ithuipu.pojo.CheckGroup;

import java.util.Map;

/**
 * @author 11752
 * @创建人 zby
 * @创建时间 2022/11/13---18:54
 * @描述信息
 */

public interface CheckGroupDao {
    /**
     * 添加组
     */
    void add(CheckGroup checkGroup);

    /**
     * 添加组后,建立关联关系
     */
    void checkGroupAndCheckItem(Map<String, Integer> map);


}
