package com.ithuipu.service;

import com.ithuipu.pojo.CheckGroup;
import com.ithuipu.pojo.CheckItem;

/**
 * @author 11752
 * @创建人 zby
 * @创建时间 2022/11/13---18:44
 * @描述信息
 */

public interface CheckGroupService {

    /**新增*/
    void add(CheckGroup checkGroup, Integer[] checkitemIds);
}
