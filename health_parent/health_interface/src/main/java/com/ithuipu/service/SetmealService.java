package com.ithuipu.service;

import com.ithuipu.pojo.Setmeal;

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
}
