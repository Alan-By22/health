package com.ithuipu.dao;

import com.ithuipu.pojo.Setmeal;

import java.util.Map;

public interface SetmealDao {

    int deleteByPrimaryKey(Integer id);

    int insert(Setmeal record);

    int insertSelective(Setmeal record);

    Setmeal selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Setmeal record);

    int updateByPrimaryKey(Setmeal record);

    void setSetmealAndCheckGroup(Map<String, Integer> map);
}