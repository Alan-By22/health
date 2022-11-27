package com.ithuipu.dao;

import com.ithuipu.pojo.OrderSetting;

import java.util.Date;
import java.util.List;

/**
 * @author 11752
 * @创建人 zby
 * @创建时间 2022/11/17---16:29
 * @描述信息
 */

public interface OrderSettingDao {

    /**
     * 查询是否已经预约
     */
    long findCountByOrderDate(Date orderDate);

    /**
     * 修改
     */
    void editNumberByOrderDate(OrderSetting orderSetting);

    /**
     * 添加
     */
    void add(OrderSetting orderSetting);

    /**
     * 查询
     */
    List<OrderSetting> getOrderSettingByMonth(String s);

    void editReservationsByOrderDate(OrderSetting orderSetting);

    OrderSetting findByOrderDate(Date date);
}
