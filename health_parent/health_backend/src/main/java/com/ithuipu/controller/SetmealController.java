package com.ithuipu.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ithuipu.constant.MessageConstant;
import com.ithuipu.entity.Result;
import com.ithuipu.pojo.Setmeal;
import com.ithuipu.service.SetmealService;
import com.ithuipu.utils.QiniuUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

/**
 * @author 11752
 * @创建人 zby
 * @创建时间 2022/11/15---14:42
 * @描述信息 套餐管理
 */

@RestController
@RequestMapping("/setmeal")
public class SetmealController {

    /**
     * 引入service
     */
    @Reference
    private SetmealService setmealService;

    /**
     * 图片的上传  /setmeal/upload.do
     */
    @RequestMapping("/upload")
    public Result upload(MultipartFile imgFile) {
        try {
            //1.解决重名问题
            String originalFilename = imgFile.getOriginalFilename();
            //获得后缀
            int i = originalFilename.lastIndexOf(".");
            //切割        获得.jpg
            String substring = originalFilename.substring(i - 1);
            //使用uuid
            String fileName = UUID.randomUUID().toString() + substring;
            //2.使用七牛云工具类
            QiniuUtils.upload2Qiniu(imgFile.getBytes(), fileName);
            //上传成功
            return new Result(true, MessageConstant.PIC_UPLOAD_SUCCESS, fileName);

        } catch (Exception e) {
            e.printStackTrace();
            //上传失败
            return new Result(false, MessageConstant.PIC_UPLOAD_FAIL);
        }
    }

    /**
     * 套餐的添加
     */
    @RequestMapping("/add")
    public Result add(@RequestBody Setmeal setmeal, Integer[] checkgroupIds) {
        try {
            setmealService.add(setmeal, checkgroupIds);
        } catch (Exception e) {
            e.printStackTrace();
            //失败
            return new Result(false, MessageConstant.ADD_SETMEAL_FAIL);
        }
        //成功
        return new Result(true, MessageConstant.ADD_SETMEAL_SUCCESS);
    }
}
