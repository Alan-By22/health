package com.ithuipu.dao;

import com.ithuipu.pojo.CheckGroup;

public interface CheckGroupMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(CheckGroup record);

    int insertSelective(CheckGroup record);

    CheckGroup selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(CheckGroup record);

    int updateByPrimaryKey(CheckGroup record);
}