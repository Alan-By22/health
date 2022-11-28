package com.ithuipu.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ithuipu.constant.MessageConstant;
import com.ithuipu.constant.RedisMessageConstant;
import com.ithuipu.entity.Result;
import com.ithuipu.pojo.Order;
import com.ithuipu.service.OrderService;
import com.ithuipu.utils.SMSUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.JedisPool;

import javax.swing.*;
import java.util.Map;
import java.util.Set;

/**
 * @author 11752
 * @创建人 zby
 * @创建时间 2022/11/23---20:17
 * @描述信息
 */

@RestController
@RequestMapping("/order")
public class OrderController {

    /**
     * 注入service
     */
    @Reference
    private OrderService orderService;
    /**
     * 验证码的校验
     */
    @Autowired
    private JedisPool jedisPool;

    @RequestMapping("/submit")
    public Result submitOrder(@RequestBody Map map) {
        //验证码的校验
        String telephone = (String) map.get("telephone");
        //从jedis中取出来
        String code_redis = jedisPool.getResource().get(telephone + RedisMessageConstant.SENDTYPE_ORDER);
        System.out.println(code_redis +"redis --");
        //获取前台传入的code
        String validateCode = (String)   map.get("validateCode");
        System.out.println(validateCode + "前台 --");
        //判断
        if (code_redis == null || !code_redis.equals(validateCode)) {
            //验证码输入错误
            return new Result(false, MessageConstant.VALIDATECODE_ERROR);
        }
        /**2.调用预约服务*/
        Result result = null;
        try {
            map.put("orderType", Order.ORDERTYPE_WEIXIN);
            map.put("orderStatus", Order.ORDERSTATUS_NO);
            result = orderService.order(map);
        } catch (Exception e) {
            e.printStackTrace();
            return result;
        }
        /**3.预约成功,发送预约成功短信*/
        if (result.isFlag()) {
            String orderDate = (String) map.get("orderDate");
            try {
                SMSUtils.sendShortMessage(SMSUtils.TEMPLATE_SUCCESS_ID, telephone, orderDate);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return result;
    }


    /**
     * 根据预约的id查询,会员信息,套餐信息,预约信息
     */
    @RequestMapping("/findById")
    public Result findById(Integer id) {
        try {
            Map map= orderService.findById(id);
            return new Result(true,MessageConstant.QUERY_ORDER_SUCCESS,map);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.QUERY_ORDER_FAIL);
        }
    }
}
