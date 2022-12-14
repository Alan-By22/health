package com.ithuipu.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ithuipu.constant.MessageConstant;
import com.ithuipu.entity.PageResult;
import com.ithuipu.entity.QueryPageBean;
import com.ithuipu.entity.Result;
import com.ithuipu.pojo.CheckGroup;
import com.ithuipu.pojo.CheckItem;
import com.ithuipu.service.CheckGroupService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author 11752
 * @创建人 zby
 * @创建时间 2022/11/13---18:42
 * @描述信息    检查组管理
 */
@RestController
@RequestMapping("/checkgroup")
public class CheckGroupController {

    /**
     * 从远程站点拉取service
     */
    @Reference
    private CheckGroupService checkGroupService;

    /**
     * 1.新增
     */
    @RequestMapping("/add")
    public Result add(@RequestBody CheckGroup checkGroup, Integer[] checkitemIds) {
        try {
            checkGroupService.add(checkGroup, checkitemIds);
        } catch (Exception e) {
            //新增失败
            return new Result(false, MessageConstant.ADD_CHECKGROUP_FAIL);
        }
        //新增成功
        return new Result(true, MessageConstant.ADD_CHECKGROUP_SUCCESS);
    }

    /**
     * 2.查询findByPage
     */
    @RequestMapping("/findByPage")
    public PageResult findByPageAndQuery(@RequestBody QueryPageBean queryPageBean) {
        //1.调用service---dao
        return checkGroupService.findByPage(queryPageBean);
    }

    /**
     * 3.查询ids
     */
    @RequestMapping("/findCheckItemIdsByCheckGroupId")
    public Result findCheckItemIdsByCheckGroupId(Integer id) {
        try {
            List<Integer> ids = checkGroupService.findCheckItemIdsByCheckGroupId(id);
            //新增成功
            return new Result(true, MessageConstant.QUERY_CHECKITEM_FAIL, ids);
        } catch (Exception e) {
            //新增失败
            return new Result(false, MessageConstant.QUERY_CHECKITEM_SUCCESS);
        }
    }

    /**
     * 4.编辑
     */
    @RequestMapping("/edit")
    public Result edit(@RequestBody CheckGroup checkGroup, Integer[] checkItemIds) {
        try {
            checkGroupService.edit(checkGroup, checkItemIds);
        } catch (Exception e) {
            //编辑失败
            return new Result(false, MessageConstant.EDIT_CHECKGROUP_FAIL);
        }
        //编辑成功
        return new Result(true, MessageConstant.EDIT_CHECKGROUP_SUCCESS);
    }

    /**
     * 1.新增
     */
    @RequestMapping("/findAll")
    public Result findAll() {
        List<CheckGroup> checkGroupList = checkGroupService.findAll();
        if (checkGroupList != null && checkGroupList.size() > 0) {
            return new Result(true, MessageConstant.QUERY_CHECKGROUP_SUCCESS, checkGroupList);
        }
        return new Result(true, MessageConstant.QUERY_CHECKGROUP_FAIL);

    }

}
