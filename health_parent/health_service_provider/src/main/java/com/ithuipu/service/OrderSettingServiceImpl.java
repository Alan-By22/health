package com.ithuipu.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.ithuipu.dao.OrderSettingDao;
import com.ithuipu.pojo.OrderSetting;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author 11752
 * @创建人 zby
 * @创建时间 2022/11/17---16:28
 * @描述信息
 */

@Service(interfaceClass = OrderSettingService.class)
@Transactional
public class OrderSettingServiceImpl implements OrderSettingService {
    /**
     * 注入dao
     */
    @Autowired
    private OrderSettingDao orderSettingDao;

    /**
     * 添加
     */
    @Override
    public void add(List<OrderSetting> list) {
        //批量添加
        if (list != null && list.size() > 0) {
            for (OrderSetting orderSetting : list) {
                //1.判断---预约的时间是否添加了
                long count = orderSettingDao.findCountByOrderDate(orderSetting.getOrderDate());
                //2.判断
                if (count>0){
                    //提交过预约时间---修改
                    orderSettingDao.editNumberByOrderDate(orderSetting);
                }else {
                    orderSettingDao.add(orderSetting);
                }
            }
        }
    }
}
