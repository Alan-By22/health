package com.ithuipu.dao;

import com.github.pagehelper.Page;
import com.ithuipu.pojo.CheckItem;

import java.util.List;

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

    /**
     * 查询有没有关联
     */
    long findCountByCheckItemId(Integer id);

    /**
     * 根据id来删除有没有检查项
     */
    void deleteById(Integer id);
    /**
     * 编辑
     */
    void edit(CheckItem checkItem);

    /**查询所有*/
    List<CheckItem> findAll();

}
