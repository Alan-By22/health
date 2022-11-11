package com.ithuipu.service;

import com.ithuipu.entity.PageResult;
import com.ithuipu.entity.QueryPageBean;
import com.ithuipu.pojo.CheckItem;

/**
 * @author 11752
 * @创建人 zby
 * @创建时间 2022/11/10---17:10
 * @描述信息
 */

public interface CheckItemService {
    /**
     * 新增
     */
    void add(CheckItem checkItem);

    /**
     * 分页查询
     */
    PageResult findByPageAndQuery(QueryPageBean queryPageBean);
}
