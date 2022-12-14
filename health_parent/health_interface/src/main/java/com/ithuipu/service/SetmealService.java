package com.ithuipu.service;

import com.ithuipu.entity.PageResult;
import com.ithuipu.entity.QueryPageBean;
import com.ithuipu.pojo.Setmeal;

import java.util.List;
import java.util.Map;

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

    /**
     * 分页的条件查询
     */
    PageResult findByPage(QueryPageBean queryPageBean);

    /**
     * 查询所有
     */
    List<Setmeal> findAll();

    /**
     * 根据id查询
     */
    Setmeal findById(Integer id);

    /**
     * 套餐数量的统计
     */
    List<Map<String, Object>> findSetmealCount();

}
