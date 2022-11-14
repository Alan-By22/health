package com.ithuipu.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.ithuipu.dao.CheckGroupDao;
import com.ithuipu.entity.PageResult;
import com.ithuipu.entity.QueryPageBean;
import com.ithuipu.pojo.CheckGroup;
import com.ithuipu.pojo.CheckItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author 11752
 * @创建人 zby
 * @创建时间 2022/11/13---18:51
 * @描述信息
 */
@Service(interfaceClass = CheckGroupService.class)
@Transactional
public class CheckGroupServiceImpl implements CheckGroupService {

    @Autowired
    private CheckGroupDao checkGroupDao;

    @Override
    public void add(CheckGroup checkGroup, Integer[] checkitemIds) {
        //1.检查组的添加
        checkGroupDao.add(checkGroup);
        //2.关联关系的建立
        //问题:1:组id(数据库自增,使用自增主键的返回这种方式)2:项ids(有)
        setCheckGroup_CheckItem(checkGroup.getId(), checkitemIds);
    }

    /**
     * 建立关联关系
     */
    private void setCheckGroup_CheckItem(Integer checkgroup_id, Integer[] checkitemIds) {
        if (checkitemIds != null && checkitemIds.length > 0) {
            for (Integer checkitemId : checkitemIds) {
                Map<String, Integer> map = new HashMap<>();
                map.put("checkgroup_id", checkgroup_id);
                map.put("checkitem_id", checkitemId);
                //添加
                checkGroupDao.checkGroupAndCheckItem(map);
            }
        }
    }

    /**
     * 分页查询
     */
    @Override
    public PageResult findByPage(QueryPageBean queryPageBean) {
        //1.设置分页
        PageHelper.startPage(queryPageBean.getCurrentPage(), queryPageBean.getPageSize());
        //2.查询
        Page<CheckGroup> page = checkGroupDao.findByPage(queryPageBean.getQueryString());
        return new PageResult(page.getTotal(), page.getResult());
    }

    @Override
    public List<Integer> findCheckItemIdsByCheckGroupId(Integer id) {
        return checkGroupDao.findCheckItemIdsByCheckGroupId(id);
    }

    @Override
    public void edit(CheckGroup checkGroup, Integer[] checkItemIds) {
        //1.先修改组数据
        checkGroupDao.edit(checkGroup);
        //2.清理关联关系
        checkGroupDao.deleteByGroupId(checkGroup);
        //3.建立关联联系
        setCheckGroup_CheckItem(checkGroup.getId(),checkItemIds);
    }
}
