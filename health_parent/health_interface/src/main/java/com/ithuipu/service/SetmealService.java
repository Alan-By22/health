package com.ithuipu.service;

import com.ithuipu.entity.PageResult;
import com.ithuipu.entity.QueryPageBean;
import com.ithuipu.pojo.Setmeal;

import java.util.List;

/**
 * @author 11752
 * @创建人 zby
 * @创建时间 2022/11/15---15:30
 * @描述信息
 */

public interface SetmealService {
    /**
     * 新增,添加
     */
    void add(Setmeal setmeal, Integer[] checkitemIds);

    /**分页的条件查询*/
    PageResult findByPage(QueryPageBean queryPageBean);

    List<Setmeal> findAll();
}
