package com.ithuipu.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.ithuipu.dao.CheckItemDao;
import com.ithuipu.entity.PageResult;
import com.ithuipu.entity.QueryPageBean;
import com.ithuipu.pojo.CheckItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author 11752
 * @创建人 zby
 * @创建时间 2022/11/10---17:58
 * @描述信息
 */

@Service(interfaceClass = CheckItemService.class)
@Transactional
public class CheckItemServiceImpl implements CheckItemService {

    /**
     * 注入dao
     */
    @Autowired
    private CheckItemDao checkItemDao;

    /**
     * 新增
     */
    @Override
    public void add(CheckItem checkItem) {
        checkItemDao.add(checkItem);
    }

    /**
     * 分页查询
     */
    @Override
    public PageResult findByPageAndQuery(QueryPageBean queryPageBean) {
        //1.设置分页
        PageHelper.startPage(queryPageBean.getCurrentPage(), queryPageBean.getPageSize());
        //2.查询
        Page<CheckItem> page = checkItemDao.selectCheckItemByQuery(queryPageBean.getQueryString());
        return new PageResult(page.getTotal(), page.getResult());
    }

    /**
     * 删除
     */
    @Override
    public void delete(Integer id) {
        //1.该表中数据可能与检查组有关联,如有关联的话---不能删除
        long count = checkItemDao.findCountByCheckItemId(id);
        //2.
        if (count > 0) {
            //有关联
            throw new RuntimeException("当前检查项有被引用,不能删除!!");
        }
        //3
        checkItemDao.deleteById(id);
    }

    /**
     * 编辑
     */
    @Override
    public void edit(CheckItem checkItem) {
        checkItemDao.edit(checkItem);
    }

    /**
     * 查询所有
     */
    @Override
    public List<CheckItem> findAll() {
        return checkItemDao.findAll();
    }
}
