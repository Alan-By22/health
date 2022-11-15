package com.ithuipu.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.ithuipu.dao.SetmealDao;
import com.ithuipu.pojo.Setmeal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

/**
 * @author 11752
 * @创建人 zby
 * @创建时间 2022/11/15---15:33
 * @描述信息
 */

@Service(interfaceClass = SetmealService.class)
@Transactional
public class SetmealServiceImpl implements SetmealService {

    /**
     * 注入dao
     */
    @Autowired
    private SetmealDao setmealDao;

    @Override
    public void add(Setmeal setmeal, Integer[] checkitemIds) {
        //1.添加套餐,回显id
        setmealDao.insert(setmeal);
        //2.维护关联关系
        if (checkitemIds != null && checkitemIds.length > 0) {
            setSetmealAndCheckGroup(setmeal.getId(), checkitemIds);
        }

    }

    /**
     * 维护中间表
     */
    private void setSetmealAndCheckGroup(Integer id, Integer[] checkitemIds) {
        for (Integer checkitemId : checkitemIds) {
            Map<String, Integer> map = new HashMap<>();
            map.put("checkgroup_id",checkitemId);
            map.put("setmeal_id",id);
            setmealDao.setSetmealAndCheckGroup(map);
        }
    }
}
