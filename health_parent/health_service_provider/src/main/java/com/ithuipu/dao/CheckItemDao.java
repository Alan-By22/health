package com.ithuipu.dao;

import com.github.pagehelper.Page;
import com.ithuipu.pojo.CheckItem;

/**
 * @author 11752
 * @创建人 zby
 * @创建时间 2022/11/10---18:00
 * @描述信息
 */

public interface CheckItemDao {
    /**
     * 新增
     */
    void add(CheckItem checkItem);
    /**
     * 分页查询
     */
    Page<CheckItem> selectCheckItemByQuery(String queryString);
}
