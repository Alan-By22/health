package com.ithuipu.controller;

/**
 * @author 11752
 * @创建人 zby
 * @创建时间 2022/11/10---17:06
 * @描述信息
 */

import com.alibaba.dubbo.config.annotation.Reference;
import com.ithuipu.constant.MessageConstant;
import com.ithuipu.entity.PageResult;
import com.ithuipu.entity.QueryPageBean;
import com.ithuipu.entity.Result;
import com.ithuipu.pojo.CheckItem;
import com.ithuipu.service.CheckItemService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 11752
 * 检查项  json
 */
@RestController
@RequestMapping("/checkitem")
public class CheckItemController {

    /**
     * service---dubbo中找服务
     */
    @Reference
    private CheckItemService checkItemService;

    /**
     * 1.添加
     */
    @RequestMapping("/add")
    public Result add(@RequestBody CheckItem checkItem) {
        try {
            checkItemService.add(checkItem);
        } catch (Exception e) {
            return new Result(false, MessageConstant.ADD_CHECKITEM_FAIL);
        }
        return new Result(true, MessageConstant.ADD_CHECKITEM_SUCCESS);

    }

    /** 2.分页条件查询*/
    @RequestMapping("/findByPage")
    public PageResult findByPageAndQuery(@RequestBody QueryPageBean queryPageBean){
        //1.调用service---dao
        PageResult pageResult = checkItemService.findByPageAndQuery(queryPageBean);
        return pageResult;
    }
}
