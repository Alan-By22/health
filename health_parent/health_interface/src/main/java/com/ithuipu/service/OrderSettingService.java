package com.ithuipu.service;

import com.ithuipu.pojo.OrderSetting;

import java.util.List;
import java.util.Map;

/**
 * @author 11752
 * @创建人 zby
 * @创建时间 2022/11/17---16:25
 * @描述信息
 */

public interface OrderSettingService {

    /**
     * 添加
     */
    void add(List<OrderSetting> list);

    /**
     * 查询
     */
    List<Map> getOrderSettingByMonth(String date);

    /**
     * 设置预约
     */
    void editOrderSettingNumber(OrderSetting orderSetting);

}
