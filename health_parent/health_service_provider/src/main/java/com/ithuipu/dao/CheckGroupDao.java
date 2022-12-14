package com.ithuipu.dao;

import com.github.pagehelper.Page;
import com.ithuipu.pojo.CheckGroup;

import java.util.List;
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

    /**
     * 分页查询
     */
    Page<CheckGroup> findByPage(String queryString);

    /**
     * 查询ids
     */
    List<Integer> findCheckItemIdsByCheckGroupId(Integer id);

    /**
     * 修改
     */
    void edit(CheckGroup checkGroup);

    /**
     * 清理
     */
    void deleteByGroupId(CheckGroup checkGroup);

    /**
     * 查询所有
     */
    List<CheckGroup> findAll();

    /**根据sid查询组信息*/
    List<CheckGroup> findGroupBySid(Integer sid);
}
