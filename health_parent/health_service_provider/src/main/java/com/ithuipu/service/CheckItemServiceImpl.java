package com.ithuipu.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.ithuipu.dao.CheckItemDao;
import com.ithuipu.pojo.CheckItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author 11752
 * @创建人 zby
 * @创建时间 2022/11/10---17:58
 * @描述信息
 */

@Service(interfaceClass = CheckItemService.class)
@Transactional
public class CheckItemServiceImpl implements CheckItemService{

    /**注入dao*/
    @Autowired
    private CheckItemDao checkItemDao;

    @Override
    public void add(CheckItem checkItem) {
        checkItemDao.add(checkItem);
    }
}
