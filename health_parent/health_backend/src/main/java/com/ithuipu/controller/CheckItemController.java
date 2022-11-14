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

import java.util.List;

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

    /**
     * 2.分页条件查询
     */
    @RequestMapping("/findByPage")
    public PageResult findByPageAndQuery(@RequestBody QueryPageBean queryPageBean) {
        //1.调用service---dao
        PageResult pageResult = checkItemService.findByPageAndQuery(queryPageBean);
        return pageResult;
    }

    /**
     * 3.删除
     * //checkitem/deleteById.do?id=28
     */
    @RequestMapping("/deleteById")
    public Result delete(Integer id) {
        try {
            checkItemService.delete(id);
        } catch (RuntimeException e) {
            return new Result(false, e.getMessage());
        } catch (Exception e) {
            return new Result(false, MessageConstant.DELETE_CHECKITEM_FAIL);
        }
        return new Result(true, MessageConstant.DELETE_CHECKITEM_SUCCESS);
    }

    /**
     * 4.修改
     * /checkitem/edit.do
     */
    @RequestMapping("/edit")
    public Result edit(@RequestBody CheckItem checkItem) {
        try {
            checkItemService.edit(checkItem);
        } catch (Exception e) {
            return new Result(false, MessageConstant.EDIT_CHECKITEM_FAIL);
        }
        return new Result(true, MessageConstant.EDIT_CHECKITEM_SUCCESS);
    }

    /**
     * 5.查询所有
     */
    @RequestMapping("/findAll")
    public Result findAll() {

        List<CheckItem> checkItemList = checkItemService.findAll();
        if (checkItemList != null && checkItemList.size() > 0) {
            return new Result(true,MessageConstant.QUERY_CHECKITEM_SUCCESS,checkItemList);
        }
        return new Result(true,MessageConstant.QUERY_CHECKITEM_FAIL);

    }

}
