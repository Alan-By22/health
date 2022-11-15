package com.ithuipu.dao;

import com.github.pagehelper.Page;
import com.ithuipu.pojo.Setmeal;

import java.util.Map;

public interface SetmealDao {

    int deleteByPrimaryKey(Integer id);

    /**
     * 新增,添加
     */
    int insert(Setmeal record);

    int insertSelective(Setmeal record);

    Setmeal selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Setmeal record);

    int updateByPrimaryKey(Setmeal record);

    void setSetmealAndCheckGroup(Map<String, Integer> map);

    /**
     * 分页的条件查询
     */
    Page<Setmeal> findPageByQuery(String queryString);

}