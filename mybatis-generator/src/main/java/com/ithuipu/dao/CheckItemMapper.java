package com.ithuipu.dao;

import com.ithuipu.pojo.CheckItem;

public interface CheckItemMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(CheckItem record);

    int insertSelective(CheckItem record);

    CheckItem selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(CheckItem record);

    int updateByPrimaryKey(CheckItem record);
}