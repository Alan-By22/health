package com.ithuipu.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ithuipu.constant.MessageConstant;
import com.ithuipu.entity.Result;
import com.ithuipu.pojo.Setmeal;
import com.ithuipu.service.SetmealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author 11752
 * @创建人 zby
 * @创建时间 2022/11/20---16:55
 * @描述信息
 */

@RestController
@RequestMapping("/setmeal")
public class SetmealController {

    /**
     * 注入service
     */
    @Reference
    private SetmealService setmealService;

    /**
     * 查询
     */
    @RequestMapping("/getSetmeal")
    public Result findAll() {
        try {
            List<Setmeal> list = setmealService.findAll();
            return new Result(true, MessageConstant.QUERY_SETMEAL_SUCCESS, list);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.QUERY_SETMEAL_FAIL);
        }
    }
}
