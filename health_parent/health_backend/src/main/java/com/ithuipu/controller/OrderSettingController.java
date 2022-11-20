package com.ithuipu.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ithuipu.constant.MessageConstant;
import com.ithuipu.entity.Result;
import com.ithuipu.pojo.OrderSetting;
import com.ithuipu.service.OrderSettingService;
import com.ithuipu.utils.POIUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author 11752
 * @创建人 zby
 * @创建时间 2022/11/17---15:46
 * @描述信息
 */

@RequestMapping("/ordersetting")
@RestController
public class OrderSettingController {

    /**
     * 从远程站点拉取服务
     */
    @Reference
    private OrderSettingService orderSettingService;

    /**
     * 1.upload--文件的上传
     */
    @RequestMapping("/upload")
    public Result upload(MultipartFile excelFile) {
        try {
            //1.解析Excel--工具类
            List<String[]> readExcel = POIUtils.readExcel(excelFile);
            //2.遍历-解析--封装List<OrderSetter>
            if (readExcel != null && readExcel.size() > 0) {
                //定义
                List<OrderSetting> list = new ArrayList<>();
                //遍历
                for (String[] strings : readExcel) {
                    OrderSetting orderSetting = new OrderSetting(new Date(strings[0]), Integer.parseInt(strings[1]));
                    list.add(orderSetting);
                }
                //调用service---添加
                orderSettingService.add(list);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.IMPORT_ORDERSETTING_FAIL);
        }
        return new Result(true, MessageConstant.IMPORT_ORDERSETTING_SUCCESS);
    }

    /**
     * 查询
     */
    @RequestMapping("/getOrderSettingByMonth")
    public Result getOrderSettingByMonth(String date) {

        try {
            //1.
            List<Map> list = orderSettingService.getOrderSettingByMonth(date);
            //2.
            return new Result(true, MessageConstant.QUERY_ORDER_SUCCESS, list);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Result(false, MessageConstant.QUERY_ORDER_FAIL);
    }

    /**
     * 修改
     */
    @RequestMapping("/editOrderSettingNumber")
    public Result editOrderSettingNumber(@RequestBody OrderSetting orderSetting) {
        try {
            orderSettingService.editOrderSettingNumber(orderSetting);
            return new Result(true, MessageConstant.ORDERSETTING_SUCCESS);
        } catch (Exception e) {
            return new Result(false, MessageConstant.ORDERSETTING_FAIL);
        }
    }

}
