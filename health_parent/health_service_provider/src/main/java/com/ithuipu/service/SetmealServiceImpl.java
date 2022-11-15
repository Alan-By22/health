package com.ithuipu.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.ithuipu.constant.RedisConstant;
import com.ithuipu.dao.SetmealDao;
import com.ithuipu.entity.PageResult;
import com.ithuipu.entity.QueryPageBean;
import com.ithuipu.pojo.Setmeal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import redis.clients.jedis.JedisPool;

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

    /**取jedis*/
    @Autowired
    private JedisPool jedisPool;

    @Override
    public void add(Setmeal setmeal, Integer[] checkitemIds) {
        //1.添加套餐,回显id
        setmealDao.insert(setmeal);
        //2.维护关联关系
        if (checkitemIds != null && checkitemIds.length > 0) {
            setSetmealAndCheckGroup(setmeal.getId(), checkitemIds);
        }
        //3.将添加的套餐的图片添加到redis中set集合
        jedisPool.getResource().sadd(RedisConstant.SETMEAL_PIC_DB_RESOURCES,setmeal.getImg());
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
    /**分页的条件查询--拦截器*/
    @Override
    public PageResult findByPage(QueryPageBean queryPageBean) {
        //设置初始条件
        PageHelper.startPage(queryPageBean.getCurrentPage(),queryPageBean.getPageSize());
        //2.查询
        Page<Setmeal> page = setmealDao.findPageByQuery(queryPageBean.getQueryString());
        return new PageResult(page.getTotal(),page.getResult());
    }
}
